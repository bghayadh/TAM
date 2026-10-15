package com.aliat.alm.routing;

import com.aliat.alm.routing.SidewalkRouteServiceShift.LatLon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Exposes the sidewalk-side drop cable route as a GeoJSON LineString.
 *
 * Example:
 * GET /api/drop-cable/route?srcLat=24.78154139&srcLon=46.81100707&dstLat=24.782571&dstLon=46.81163844
 *
 * Requires Jackson on the classpath and <mvc:annotation-driven/> (or @EnableWebMvc),
 * so the returned Map is converted to JSON.
 */
@RestController
@RequestMapping("/api/drop-cable")
public class DropCableRouteController {

    private static final Logger log = LoggerFactory.getLogger(DropCableRouteController.class);

    @Autowired
    private SidewalkRouteServiceShift routeService;

    @GetMapping("/route")
    public ResponseEntity<Map<String, Object>> route(
            @RequestParam double srcLat,
            @RequestParam double srcLon,
            @RequestParam double dstLat,
            @RequestParam double dstLon) {
        // The client only sends the two coordinates. Offset and side are decided by the
        // service (offset from graphhopper.properties, side calculated from the geometry).

        try {
            List<LatLon> pts = routeService.route(
                    new LatLon(srcLat, srcLon),
                    new LatLon(dstLat, dstLon));

            // GeoJSON coordinate order is [longitude, latitude]
            List<double[]> coords = new ArrayList<>();
            for (LatLon p : pts) {
                coords.add(new double[]{p.lon, p.lat});
            }
            Map<String, Object> geometry = new LinkedHashMap<>();
            geometry.put("type", "LineString");
            geometry.put("coordinates", coords);
            return ResponseEntity.ok(geometry);

        } catch (BeansException e) {
            // GraphHopper could not be created: PBF/cache missing, mismatch, out of memory...
            // TAM itself keeps running; the next request tries to load GraphHopper again.
            log.error("GraphHopper failed to load", e);
            return error(HttpStatus.SERVICE_UNAVAILABLE,
                    "Routing engine is not available. Check the OSM file and graph cache paths.");

        } catch (IllegalStateException e) {
            // GraphHopper loaded but found no route (points too far from roads, disconnected area...)
            log.warn("No route: {}", e.getMessage());
            return error(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());

        } catch (Exception e) {
            log.error("Unexpected routing error", e);
            return error(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected routing error");
        }
    }

    private ResponseEntity<Map<String, Object>> error(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", message);
        return ResponseEntity.status(status).body(body);
    }
}