package com.aliat.alm.routing;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.PointList;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.linearref.LengthIndexedLine;
import org.locationtech.jts.linearref.LinearLocation;
import org.locationtech.jts.linearref.LocationIndexedLine;
import org.locationtech.jts.operation.buffer.OffsetCurve;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Routes with the embedded GraphHopper, then shifts the returned centerline
 * sideways so the cable runs on the sidewalk side of the street instead of the
 * middle of it.
 *
 * Why the shift is needed: GraphHopper only knows the road centerline (OSM
 * ways) and always snaps to it. It has no concept of "side of the street", and
 * a custom model can only change weights, never geometry. So we post-process
 * the geometry ourselves.
 */
@Service
public class SidewalkRouteServiceShift {

	private static final double EARTH_RADIUS = 6371008.8; // meters

	private final GeometryFactory gf = new GeometryFactory();

	/**
	 * ObjectProvider instead of a direct GraphHopper field, because the bean
	 * is @Lazy. A direct @Autowired field would force the bean to be created at TAM
	 * startup (or would inject a proxy). With ObjectProvider, GraphHopper is only
	 * created when getObject() is called in route(), so loading failures happen at
	 * the request.
	 */
	@Autowired
	private ObjectProvider<GraphHopper> hopperProvider;

	@Value("${graphhopper.profile:drop_cable}")
	private String profile;

	/**
	 * Distance in meters from the road centerline to the cable. A TAM setting, not
	 * something the client knows. About half the road width plus a little gives a
	 * position on the shoulder/sidewalk.
	 */
	@Value("${graphhopper.cable.offset.meters:3.0}")
	private double offsetMeters;

	/**
	 * Last-resort side (1 = left, -1 = right, in travel direction), used only when
	 * neither the handhole nor the destination gives a usable side (both on the
	 * centerline).
	 */
	@Value("${graphhopper.cable.default.side:1}")
	private int defaultSide;

	/**
	 * Simple latitude/longitude holder (public fields so Jackson/JSP can read
	 * them).
	 */
	public static class LatLon {
		public final double lat;
		public final double lon;

		public LatLon(double lat, double lon) {
			this.lat = lat;
			this.lon = lon;
		}
	}

	/**
	 * Offset and side are decided here (see the properties above), so callers only
	 * need the two coordinates.
	 *
	 * @param src handhole (source) position
	 * @param dst destination position
	 * @return waypoints from src to dst; the first and last points are exactly src
	 *         and dst
	 * @throws IllegalStateException                    if no route exists between
	 *                                                  the points
	 * @throws org.springframework.beans.BeansException if GraphHopper fails to load
	 *                                                  (missing PBF, bad cache, not
	 *                                                  enough memory...)
	 */
	public List<LatLon> route(LatLon src, LatLon dst) {
		// First call: loads GraphHopper (slow if the cache is missing). Later calls:
		// instant.
		GraphHopper hopper = hopperProvider.getObject();

		System.out.println("src lat is: " + src.lat + " src long is: " + src.lon + " dst lat is: " + dst.lat
				+ " dst long is: " + dst.lon);

		// ---- 1. Ask GraphHopper for the route along the road centerline ----
		GHRequest req = new GHRequest(src.lat, src.lon, dst.lat, dst.lon).setProfile(profile)
				// Same as routing.snap_preventions_default in the yml. That setting belongs to
				// the web server layer and is ignored when embedding, so it is set per request.
				.setSnapPreventions(List.of("tunnel", "bridge", "ferry")).putHint("instructions", false); // turn-by-turn
																											// text is
																											// not
																											// needed
		GHResponse rsp = hopper.route(req);
		if (rsp.hasErrors()) {
			throw new IllegalStateException("GraphHopper: " + rsp.getErrors());
		}
		ResponsePath best = rsp.getBest();
		PointList pl = best.getPoints();
		
		System.out.println("pl size is: " +pl.size());

		// ---- 2. Convert to local meters so the offset distance is in real meters ----
		// (lat/lon are degrees; offsetting in degrees would be wrong and distorted)
		Projection proj = new Projection(src.lat, src.lon);
		List<Coordinate> roadCoords = new ArrayList<>();
		for (int i = 0; i < pl.size(); i++) {
			roadCoords.add(proj.toXY(pl.getLat(i), pl.getLon(i)));
		}
		Coordinate s = proj.toXY(src.lat, src.lon);
		Coordinate d = proj.toXY(dst.lat, dst.lon);

		List<Coordinate> result = new ArrayList<>();
		result.add(s); // always start exactly at the handhole

		if (roadCoords.size() >= 2) {
			LineString road = gf.createLineString(roadCoords.toArray(new Coordinate[0]));

			// ---- 3. Which side of the road is the handhole on? ----
			// Preferred: the handhole's own side. If it sits on the centerline (side 0),
			// use the destination's side. If that is also undecided, use the configured
			// default.
			int side = sideOf(road, s);
			if (side == 0) {
				side = sideOf(road, d);
			}
			if (side == 0) {
				side = defaultSide >= 0 ? 1 : -1;
			}

			// ---- 4. Shift the whole line to that side ----
			// JTS: positive distance = left of the line direction, negative = right.
			LineString shifted = longestLine(OffsetCurve.getCurve(road, side * offsetMeters));
			if (shifted == null) {
				shifted = road; // offset failed (degenerate/very short line): keep centerline
			}

			// ---- 5. Keep only the part between the handhole and the destination ----
			// The route starts/ends at the snapped road points, which can be slightly
			// before
			// the handhole or after the destination. Projecting both points onto the
			// shifted
			// line and cutting between them removes those overshoot stubs.
			LengthIndexedLine li = new LengthIndexedLine(shifted);
			Geometry part = li.extractLine(li.project(s), li.project(d));
			for (Coordinate c : part.getCoordinates()) {
				addIfFar(result, c);
			}
		}
		addIfFar(result, d); // always end exactly at the destination

		// ---- 6. Back to latitude/longitude ----
		List<LatLon> out = new ArrayList<>();
		for (Coordinate c : result) {
			out.add(proj.toLatLon(c));
		}
		// Remove tiny rounding differences from the projection round trip.
		out.set(0, src);
		out.set(out.size() - 1, dst);
		return out;
	}

	/**
	 * Returns +1 if the point is on the left of the road (in travel direction), -1
	 * if on the right, 0 if it is practically on the centerline (side cannot be
	 * determined). Uses the cross product against the road segment closest to the
	 * point.
	 */
	private int sideOf(LineString road, Coordinate p) {
		LocationIndexedLine lil = new LocationIndexedLine(road);
		LinearLocation loc = lil.project(p);
		int seg = Math.min(loc.getSegmentIndex(), road.getNumPoints() - 2);
		Coordinate a = road.getCoordinateN(seg);
		Coordinate b = road.getCoordinateN(seg + 1);

		// Practically on the centerline (within 0.5 m): the side cannot be determined.
		if (loc.getCoordinate(road).distance(p) < 0.5) {
			return 0;
		}
		double cross = (b.x - a.x) * (p.y - a.y) - (b.y - a.y) * (p.x - a.x);
		return cross > 0 ? 1 : -1;
	}

	/** The offset operation can return several pieces; keep the longest one. */
	private LineString longestLine(Geometry g) {
		LineString best = null;
		for (int i = 0; i < g.getNumGeometries(); i++) {
			Geometry part = g.getGeometryN(i);
			if (part instanceof LineString && !part.isEmpty()
					&& (best == null || part.getLength() > best.getLength())) {
				best = (LineString) part;
			}
		}
		return best;
	}

	/**
	 * Adds a point unless it is within 10 cm of the previous one (avoids
	 * duplicates).
	 */
	private void addIfFar(List<Coordinate> list, Coordinate c) {
		if (list.isEmpty() || list.get(list.size() - 1).distance(c) > 0.1) {
			list.add(c);
		}
	}

	/**
	 * Local equirectangular projection around a reference point (the handhole). x =
	 * east in meters, y = north in meters. Accurate to well under a meter for
	 * routes of a few kilometers, and avoids needing a heavy GIS library
	 * (GeoTools/Proj4J).
	 */
	private static class Projection {
		private final double lat0;
		private final double lon0;
		private final double cosLat0;

		Projection(double lat0, double lon0) {
			this.lat0 = lat0;
			this.lon0 = lon0;
			this.cosLat0 = Math.cos(Math.toRadians(lat0));
		}

		Coordinate toXY(double lat, double lon) {
			return new Coordinate(Math.toRadians(lon - lon0) * EARTH_RADIUS * cosLat0,
					Math.toRadians(lat - lat0) * EARTH_RADIUS);
		}

		LatLon toLatLon(Coordinate c) {
			return new LatLon(lat0 + Math.toDegrees(c.y / EARTH_RADIUS),
					lon0 + Math.toDegrees(c.x / (EARTH_RADIUS * cosLat0)));
		}
	}
}