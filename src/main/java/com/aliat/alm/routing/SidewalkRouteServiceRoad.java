package com.aliat.alm.routing;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.DistanceCalcEarth;
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
public class SidewalkRouteServiceRoad {

	private static final Logger log = LoggerFactory.getLogger(SidewalkRouteServiceRoad.class);

	/**
	 * ObjectProvider because the GraphHopper bean is @Lazy: it is created at the
	 * first call of getObject(), so loading failures happen at the request, not at
	 * TAM startup.
	 */
	@Autowired
	private ObjectProvider<GraphHopper> hopperProvider;

	@Value("${graphhopper.profile:drop_cable}")
	private String profile;

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

		GHRequest req = new GHRequest(src.lat, src.lon, dst.lat, dst.lon)
				.setProfile(profile)
				// Do not snap start/end onto motorways/trunks or tunnels/bridges/ferries.
				// (These are the only values GraphHopper accepts here: it cannot be told
				// to "snap to footways"; it snaps to the nearest allowed edge.)
				.setSnapPreventions(List.of("motorway", "trunk", "tunnel", "bridge", "ferry"))
				// Ask for the road class of every part of the route, for the diagnostic log
				.setPathDetails(List.of("road_class"))
				.putHint("instructions", false);

		GHResponse rsp = hopper.route(req);
		if (rsp.hasErrors()) {
			throw new IllegalStateException("GraphHopper: " + rsp.getErrors());
		}
		ResponsePath best = rsp.getBest();
		PointList pl = best.getPoints();

		List<LatLon> out = new ArrayList<>();
		out.add(src); // always start exactly at the handhole
		for (int i = 0; i < pl.size(); i++) {
			addIfFar(out, new LatLon(pl.getLat(i), pl.getLon(i)));
		}
		addIfFar(out, dst); // always end exactly at the destination

		logDiagnostics(src, dst, pl, best);
		return out;
	}

	/**
	 * Logs how far the real points are from the network and which road classes the
	 * route used. Read this to understand a bad result:
	 *  - mostly FOOTWAY/PATH/PEDESTRIAN -> good, the data has walkable ways
	 *  - mostly RESIDENTIAL/SERVICE/etc. -> OSM has no sidewalks there, nothing to prefer
	 *  - large snap distance -> the handhole/destination is far from any mapped way
	 */
	private void logDiagnostics(LatLon src, LatLon dst, PointList pl, ResponsePath best) {
		if (pl.size() == 0) {
			return;
		}
		double snapSrc = DistanceCalcEarth.DIST_EARTH.calcDist(src.lat, src.lon, pl.getLat(0), pl.getLon(0));
		int last = pl.size() - 1;
		double snapDst = DistanceCalcEarth.DIST_EARTH.calcDist(dst.lat, dst.lon, pl.getLat(last), pl.getLon(last));

		Map<String, Double> metersByClass = new LinkedHashMap<>();
		List<PathDetail> details = best.getPathDetails().get("road_class");
		if (details != null) {
			for (PathDetail d : details) {
				double meters = 0;
				for (int i = d.getFirst(); i < d.getLast() && i + 1 < pl.size(); i++) {
					meters += DistanceCalcEarth.DIST_EARTH.calcDist(pl.getLat(i), pl.getLon(i),
							pl.getLat(i + 1), pl.getLon(i + 1));
				}
				metersByClass.merge(String.valueOf(d.getValue()), meters, Double::sum);
			}
		}
		log.info("Route {} m, snap distance src={} m dst={} m, meters by road class: {}",
				Math.round(best.getDistance()), Math.round(snapSrc), Math.round(snapDst), metersByClass);
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