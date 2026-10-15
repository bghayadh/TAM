package com.aliat.alm.routing;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.GHUtility;
import com.graphhopper.util.TurnCostsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.List;

/**
 * Runs GraphHopper INSIDE the TAM JVM (no separate server, no HTTP).
 *
 * NOTE: this class is named GraphHopperSpringConfig on purpose, so it does not
 * clash with com.graphhopper.GraphHopperConfig, which is used below.
 *
 * Requirement: the package com.tam.routing must be covered by component scanning
 * (@ComponentScan or <context:component-scan>), otherwise Spring will not see
 * this class, the service, or the controller.
 */
@Configuration
// Loads the properties file from the classpath (src/main/resources).
// ignoreResourceNotFound = true -> if the file is missing, the defaults written in
// the @Value annotations below are used instead of failing.
// To keep the file outside the WAR (so each server can have its own paths) use:
//   @PropertySource(value = "file:C:/tam/graphhopper.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:graphhopper.properties", ignoreResourceNotFound = true)
public class GraphHopperSpringConfig {

    private static final Logger log = LoggerFactory.getLogger(GraphHopperSpringConfig.class);

    /**
     * Spring MVC WITHOUT Spring Boot does not resolve ${...} placeholders in @Value
     * by itself; without this bean the code would receive the literal text "${...}".
     *
     * It must be static so it is created before the other beans.
     * If TAM already defines a PropertySourcesPlaceholderConfigurer (or uses
     * <context:property-placeholder>), DELETE this method and keep only @PropertySource.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    // Text after the colon is the default when the property is not defined.
    @Value("${graphhopper.osm.file:C:/GraphHopper/gcc-states-260919.osm.pbf}")
    private String osmFile;

    @Value("${graphhopper.graph.location:C:/GraphHopper/graph-cache-foot}")
    private String graphLocation;

    @Value("${graphhopper.profile:drop_cable}")
    private String profileName;

    /**
     * The single GraphHopper instance shared by all requests.
     *
     * @Lazy -> NOT created when TAM starts. It is created the first time somebody
     * asks for it (the first route request). Result:
     *   - TAM starts even if the PBF file or cache folder is missing/broken.
     *   - If loading fails, only that request fails (and the next request tries again,
     *     because Spring does not keep a failed singleton). Fixing the path or copying
     *     the files does not need a restart.
     *
     * destroyMethod = "close" -> releases the graph files when TAM shuts down.
     *
     * IMPORTANT: build the graph cache BEFORE going live (see notes in the properties
     * file). Loading an existing cache takes seconds; a fresh import of the PBF takes
     * minutes and needs a big heap, so the first user request would time out.
     */
    @Bean(destroyMethod = "close")
    @Lazy
    public GraphHopper graphHopper() {
        log.info("Loading GraphHopper. osm={}, cache={}", osmFile, graphLocation);
        long start = System.currentTimeMillis();

        // Fully qualified because of the name similarity with this class.
        com.graphhopper.GraphHopperConfig cfg = new com.graphhopper.GraphHopperConfig();

        // Source OSM data; only read when the cache folder is empty (import).
        cfg.putObject("datareader.file", osmFile);

        // Folder of the prepared graph. GraphHopper creates it (and parents) if missing.
        // To rebuild from scratch, delete this folder (not the PBF) and let it re-import.
        cfg.putObject("graph.location", graphLocation);

        // Whole graph kept in RAM (fast). Use MMAP instead to let the OS manage memory.
        cfg.putObject("graph.dataaccess.default_type", "RAM_STORE");

        // Ignore tiny disconnected road fragments, so snapping does not land on them.
        cfg.putObject("prepare.min_network_size", 200);

        // Keep all highway types (footways, paths, steps...). Empty = ignore nothing.
        cfg.putObject("import.osm.ignored_highways", "");

        // Properties the profile/custom model needs. MUST match the existing cache,
        // because they are stored inside it. If you change this list, delete the cache.
        cfg.putObject("graph.encoded_values",
                "road_class,road_environment,road_access,foot_access,car_access,foot_average_speed,"
                        + "foot_priority,foot_road_access,hike_rating,mtb_rating,average_slope,country");
        
        // Routing rules. This loads the foot.json bundled inside graphhopper-core, i.e.
        // the same rules your standalone server uses now. To use your own rules, build a
        // CustomModel in Java or read your own JSON file (changing the model does NOT
        // require rebuilding the cache).
        
//        CustomModel model = GHUtility.loadCustomModelFromJar("foot.json");
        CustomModel model = CableCustomModel.build();

        // Same as the yml profile "drop_cable": foot turn costs, u-turn cost 0.
        // In GraphHopper 10/11 there is no CustomProfile class any more: a plain Profile
        // with a CustomModel is the custom profile.
        Profile profile = new Profile(profileName)
                .setCustomModel(model)
                .setTurnCostsConfig(new TurnCostsConfig(List.of("foot"), 0));
        cfg.setProfiles(List.of(profile));

        GraphHopper hopper = new GraphHopper();
        hopper.init(cfg);
        // Loads the cache if it exists, otherwise imports the PBF (slow, memory hungry).
        hopper.importOrLoad();

        log.info("GraphHopper ready in {} ms", System.currentTimeMillis() - start);
        return hopper;
    }
}