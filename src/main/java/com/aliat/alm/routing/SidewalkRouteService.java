package com.aliat.alm.routing;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.routing.ev.EnumEncodedValue;
import com.graphhopper.routing.ev.RoadClass;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.storage.index.Snap;
import com.graphhopper.util.DistanceCalcEarth;
import com.graphhopper.util.Parameters;
import com.graphhopper.util.PointList;
import com.graphhopper.util.details.PathDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Routes with the embedded GraphHopper and returns the path EXACTLY as GraphHopper
 * found it (no geometric shifting). The quality of the result is controlled by the
 * routing rules in CableCustomModel and by what OSM contains around the handholes.
 *
 * Output = handhole + GraphHopper path points + destination. The first and last
 * points are the real input points; between a real point and its snapped position on
 * the network there can be a short straight segment (see "snap distance" in the log).
 */
@Service
public class SidewalkRouteService {

	private static final Logger log = LoggerFactory.getLogger(SidewalkRouteService.class);

	/**
	 * ObjectProvider because the GraphHopper bean is @Lazy: it is created at the
	 * first call of getObject(), so loading failures happen at the request, not at
	 * TAM startup.
	 */
	@Autowired
	private ObjectProvider<GraphHopper> hopperProvider;

	@Value("${graphhopper.profile:drop_cable}")
	private String profile;

	/**
	 * If a footway/path/pedestrian way exists within this distance (meters) of the
	 * handhole or destination, the route starts/ends on it instead of on the nearest
	 * road. 0 disables the feature. Too large a value can pick a footway on the other
	 * side of a building; too small a value rarely triggers. 10-20 is a sensible range.
	 */
	@Value("${graphhopper.snap.footway.max.meters:15}")
	private double footwaySnapMaxMeters;

	/**
	 * DIAGNOSTIC. When true, GraphHopper is asked for up to 3 different routes between
	 * the two points and ALL of them are written to the log (labels ALT0, ALT1, ALT2).
	 * The route returned to TAM is still the best one, so behavior does not change.
	 * Use it to see what other connections exist and how much longer/costlier they are.
	 * Turn it off (false) when you are done: it is slower than a normal request.
	 */
	@Value("${graphhopper.debug.alternatives:false}")
	private boolean debugAlternatives;

	/** Simple latitude/longitude holder (public fields so Jackson/JSP can read them). */
	public static class LatLon {
		public final double lat;
		public final double lon;

		public LatLon(double lat, double lon) {
			this.lat = lat;
			this.lon = lon;
		}
	}

	/**
	 * @param src handhole (source) position
	 * @param dst destination position
	 * @return waypoints from src to dst
	 * @throws IllegalStateException if no route exists between the points
	 * @throws org.springframework.beans.BeansException if GraphHopper fails to load
	 */
	public List<LatLon> route(LatLon src, LatLon dst) {
		GraphHopper hopper = hopperProvider.getObject();

		// Prefer starting/ending on a footway when one is close (see preferWalkable).
		// The returned waypoints still start/end at the real src/dst.
		LatLon routeSrc = preferWalkable(hopper, src, "src");
		LatLon routeDst = preferWalkable(hopper, dst, "dst");

		GHRequest req = new GHRequest(routeSrc.lat, routeSrc.lon, routeDst.lat, routeDst.lon)
				.setProfile(profile)
				// Do not snap start/end onto tunnels, bridges or ferries. Motorway and trunk
				// are NOT listed, so a handhole next to them can snap there. (These are the
				// only kinds of values GraphHopper accepts here; it cannot be told to
				// "snap to footways" and only snaps to the nearest allowed edge.)
				// This affects only where the START and END snap, not which roads the route may use.
				.setSnapPreventions(List.of("tunnel", "bridge", "ferry"))
				// Ask for the road class of every part of the route, for the diagnostic log
				.setPathDetails(List.of("road_class"))
				.putHint("instructions", false);

		if (debugAlternatives) {
			// "alternative_route" finds routes that differ from the best one.
			// weight factor 2.0 = accept alternatives costing up to 2x the best;
			// share factor 0.8 = accept alternatives that share up to 80% with the best.
			// (Wider than GraphHopper's defaults, so more candidates show up.)
			req.setAlgorithm(Parameters.Algorithms.ALT_ROUTE)
					.putHint(Parameters.Algorithms.AltRoute.MAX_PATHS, 3)
					.putHint(Parameters.Algorithms.AltRoute.MAX_WEIGHT, 2.0)
					.putHint(Parameters.Algorithms.AltRoute.MAX_SHARE, 0.8);
		}

		GHResponse rsp = hopper.route(req);
		if (rsp.hasErrors()) {
			throw new IllegalStateException("GraphHopper: " + rsp.getErrors());
		}
		ResponsePath best = rsp.getBest();
		PointList pl = best.getPoints();

		if (debugAlternatives) {
			int n = 0;
			for (ResponsePath alt : rsp.getAll()) {
				logDiagnostics("ALT" + n, src, dst, alt.getPoints(), alt);
				n++;
			}
			if (n == 1) {
				log.info("ALT: GraphHopper found no alternative route (only one connection exists)");
			}
		}

		List<LatLon> out = new ArrayList<>();
		out.add(src); // always start exactly at the handhole
		for (int i = 0; i < pl.size(); i++) {
			addIfFar(out, new LatLon(pl.getLat(i), pl.getLon(i)));
		}
		addIfFar(out, dst); // always end exactly at the destination

		logDiagnostics("ROUTE", src, dst, pl, best);
		return out;
	}

	/**
	 * GraphHopper normally snaps a point to the NEAREST edge, which is often the road
	 * even when a sidewalk/footway runs a few meters away. Here we first look for the
	 * nearest FOOTWAY / PATH / PEDESTRIAN edge only; if one is within
	 * footwaySnapMaxMeters, its snapped position replaces the point for routing. The
	 * router then snaps that position to the same footway, so the path starts there.
	 * If none is close enough, the original point is used unchanged.
	 */
	private LatLon preferWalkable(GraphHopper hopper, LatLon p, String label) {
		if (footwaySnapMaxMeters <= 0) {
			return p;
		}
		EnumEncodedValue<RoadClass> rcEnc = hopper.getEncodingManager().getEnumEncodedValue(RoadClass.KEY,
				RoadClass.class);
		EdgeFilter walkableOnly = edge -> {
			RoadClass rc = edge.get(rcEnc);
			return rc == RoadClass.FOOTWAY || rc == RoadClass.PATH || rc == RoadClass.PEDESTRIAN;
		};
		Snap snap = hopper.getLocationIndex().findClosest(p.lat, p.lon, walkableOnly);
		if (snap.isValid() && snap.getQueryDistance() <= footwaySnapMaxMeters) {
			log.info("{}: using footway snap at {} m", label, Math.round(snap.getQueryDistance()));
			return new LatLon(snap.getSnappedPoint().getLat(), snap.getSnappedPoint().getLon());
		}
		return p;
	}

	/**
	 * Logs how far the real points are from the network and which road classes the
	 * route used. Read this to understand a bad result:
	 *  - mostly FOOTWAY/PATH/PEDESTRIAN -> good, the data has walkable ways
	 *  - mostly RESIDENTIAL/SERVICE/etc. -> OSM has no sidewalks there, nothing to prefer
	 *  - large snap distance -> the handhole/destination is far from any mapped way
	 */
	private void logDiagnostics(String label, LatLon src, LatLon dst, PointList pl, ResponsePath best) {
		if (pl.size() == 0) {
			return;
		}
		DistanceCalcEarth dc = DistanceCalcEarth.DIST_EARTH;
		double snapSrc = dc.calcDist(src.lat, src.lon, pl.getLat(0), pl.getLon(0));
		int last = pl.size() - 1;
		double snapDst = dc.calcDist(dst.lat, dst.lon, pl.getLat(last), pl.getLon(last));
		double straight = dc.calcDist(src.lat, src.lon, dst.lat, dst.lon);

		// Walk the route in order: one entry per stretch of the same road class,
		// e.g. "residential 120 m > footway 40 m > residential 30 m"
		Map<String, Double> metersByClass = new LinkedHashMap<>();
		StringBuilder sequence = new StringBuilder();
		List<PathDetail> details = best.getPathDetails().get("road_class");
		if (details != null) {
			for (PathDetail d : details) {
				double meters = 0;
				for (int i = d.getFirst(); i < d.getLast() && i + 1 < pl.size(); i++) {
					meters += dc.calcDist(pl.getLat(i), pl.getLon(i), pl.getLat(i + 1), pl.getLon(i + 1));
				}
				String cls = String.valueOf(d.getValue());
				metersByClass.merge(cls, meters, Double::sum);
				if (sequence.length() > 0) {
					sequence.append(" > ");
				}
				sequence.append(cls).append(' ').append(Math.round(meters)).append(" m");
			}
		}

		// "detour" = route length / straight-line length. Near 1.0 = direct; high values mean
		// the model made the route go around (preferred roads) or the network forces it.
		double detour = straight > 0 ? best.getDistance() / straight : 0;
		log.info("{} length={} m, straight={} m, detour=x{}, weight={}, points={}, snap src={} m dst={} m",
				label, Math.round(best.getDistance()), Math.round(straight), String.format("%.2f", detour),
				Math.round(best.getRouteWeight()), pl.size(), Math.round(snapSrc), Math.round(snapDst));
		log.info("{} sequence: {}", label, sequence);
		log.info("{} meters by road class: {}", label, roundValues(metersByClass));
	}

	private Map<String, Long> roundValues(Map<String, Double> in) {
		Map<String, Long> out = new LinkedHashMap<>();
		for (Map.Entry<String, Double> e : in.entrySet()) {
			out.put(e.getKey(), Math.round(e.getValue()));
		}
		return out;
	}

	/** Adds a point unless it is within 10 cm of the previous one (avoids duplicates). */
	private void addIfFar(List<LatLon> list, LatLon p) {
		if (!list.isEmpty()) {
			LatLon prev = list.get(list.size() - 1);
			if (DistanceCalcEarth.DIST_EARTH.calcDist(prev.lat, prev.lon, p.lat, p.lon) <= 0.1) {
				return;
			}
		}
		list.add(p);
	}
}