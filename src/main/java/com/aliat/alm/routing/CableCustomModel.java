package com.aliat.alm.routing;

import static com.graphhopper.json.Statement.Else;
import static com.graphhopper.json.Statement.ElseIf;
import static com.graphhopper.json.Statement.If;
import static com.graphhopper.json.Statement.Op.LIMIT;
import static com.graphhopper.json.Statement.Op.MULTIPLY;

import com.graphhopper.util.CustomModel;

/**
 * Routing rules for the drop_cable profile, written for CABLE routing, not for
 * pedestrians. Unlike the stock foot.json it does NOT block roads a pedestrian may not
 * use (for example motorways), because a cable can run along them.
 *
 * How it decides: every road segment gets a "priority" between 0 and 1 from its road
 * class. GraphHopper's weight of a segment is roughly
 *     cost = length_time / priority + DISTANCE_INFLUENCE * length
 * so a segment with priority 0.5 costs about twice as much as one with priority 1.0.
 * The router accepts a detour over footways only while the detour costs less than the
 * extra cost of the road. So the ratio between the tiers below IS the "how much longer
 * may the route be to stay on a footway" setting.
 *
 * REQUIREMENTS (graph.encoded_values must contain them, see GraphHopperSpringConfig):
 *   road_class, road_environment, road_access, foot_access, car_access
 * Adding car_access means the graph must be rebuilt (delete graph-cache-foot once),
 * because it is what makes motorways and other car-only roads exist in the graph.
 */
public final class CableCustomModel {

	// ---- Tuning knobs (priority per road-class tier, 1.0 = most preferred) ----

	/** Footways, pedestrian streets, paths: the sidewalk-like ways. */
	private static final String WALKWAY = "1.0";
	/** Cycleways: similar to a sidewalk, slightly less preferred. */
	private static final String CYCLEWAY = "0.9";
	/** Small streets, service roads, tracks. */
	private static final String LOCAL_STREET = "0.8";
	private static final String TERTIARY = "0.7";
	private static final String SECONDARY = "0.6";
	private static final String PRIMARY = "0.5";
	/** Trunks and motorways are allowed but only used when nothing else is reasonable. */
	private static final String TRUNK = "0.4";
	private static final String MOTORWAY = "0.3";
	/** Stairs: possible, but a poor place for cable. */
	private static final String STEPS = "0.1";
	/** Any road class not listed above. */
	private static final String OTHER = "0.2";
	/** Private roads: allowed only as a last resort. */
	private static final String PRIVATE_ROAD = "0.5";

	/**
	 * How much plain length matters, on top of the priority. Higher = shorter routes,
	 * weaker footway preference. Lower = more willing to detour. 70 is GraphHopper's
	 * default.
	 */
	private static final double DISTANCE_INFLUENCE = 70;

	private CableCustomModel() {
	}

	public static CustomModel build() {
		CustomModel m = new CustomModel();
		m.setDistanceInfluence(DISTANCE_INFLUENCE);

		// 1. Usable at all: a way that neither pedestrians nor cars can use is skipped.
		m.addToPriority(If("!(foot_access || car_access)", MULTIPLY, "0"));

		// 2. Never route a cable over a ferry.
		m.addToPriority(If("road_environment == FERRY", MULTIPLY, "0"));

		// 3. Private roads: strongly discouraged.
		m.addToPriority(If("road_access == PRIVATE", MULTIPLY, PRIVATE_ROAD));

		// 4. Preference by road class. This is one if / else-if chain: exactly one branch applies.
		m.addToPriority(If("road_class == FOOTWAY || road_class == PEDESTRIAN || road_class == PATH", MULTIPLY, WALKWAY));
		m.addToPriority(ElseIf("road_class == CYCLEWAY", MULTIPLY, CYCLEWAY));
		m.addToPriority(ElseIf("road_class == STEPS", MULTIPLY, STEPS));
		m.addToPriority(ElseIf("road_class == LIVING_STREET || road_class == RESIDENTIAL || road_class == SERVICE"
				+ " || road_class == UNCLASSIFIED || road_class == TRACK || road_class == ROAD", MULTIPLY, LOCAL_STREET));
		m.addToPriority(ElseIf("road_class == TERTIARY", MULTIPLY, TERTIARY));
		m.addToPriority(ElseIf("road_class == SECONDARY", MULTIPLY, SECONDARY));
		m.addToPriority(ElseIf("road_class == PRIMARY", MULTIPLY, PRIMARY));
		m.addToPriority(ElseIf("road_class == TRUNK", MULTIPLY, TRUNK));
		m.addToPriority(ElseIf("road_class == MOTORWAY", MULTIPLY, MOTORWAY));
		m.addToPriority(Else(MULTIPLY, OTHER));

		// Speed: one constant speed for everything, so "time" is simply proportional to length
		// and only the priorities above decide the preference. (Not a real walking speed.)
		m.addToSpeed(If("true", LIMIT, "5"));

		return m;
	}
}