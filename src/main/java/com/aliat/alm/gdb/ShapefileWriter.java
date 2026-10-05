package com.aliat.alm.gdb;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataUtilities;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class ShapefileWriter {

    private static final GeometryFactory GEOM_FACTORY = new GeometryFactory();

    /**
     * Writes one Shapefile of Point geometries.
     * @param typeName     shapefile name, e.g. "Manhole" -> Manhole.shp
     * @param attrSchema   GeoTools type spec, e.g. "MH_ID:String,MH_NAME:String,OWNER:String"
     * @param rows         each map: attribute name -> value, plus "lon"/"lat" as Double
     */
    public static void writePoints(File outputDir, String typeName, String attrSchema,
            List<Map<String, Object>> rows) throws Exception {

        if (rows.isEmpty()) return; // nothing to write, skip creating an empty shapefile

        String fullSchema = "the_geom:Point:srid=4326," + attrSchema;
        SimpleFeatureType type = DataUtilities.createType(typeName, fullSchema);

        DefaultFeatureCollection collection = new DefaultFeatureCollection();
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder(type);

        for (Map<String, Object> row : rows) {
            Double lon = (Double) row.get("lon");
            Double lat = (Double) row.get("lat");
            if (lon == null || lat == null) continue; // skip rows with unparsable coordinates

            Point point = GEOM_FACTORY.createPoint(new Coordinate(lon, lat));
            builder.add(point);
            for (String attrName : type.getAttributeDescriptors().stream()
                    .skip(1).map(d -> d.getLocalName()).toArray(String[]::new)) {
                builder.add(row.get(attrName));
            }
            collection.add(builder.buildFeature(null));
        }

        writeShapefile(outputDir, typeName, type, collection);
    }

    /**
     * Writes one Shapefile of LineString geometries.
     * @param rows each map must contain "points" -> List<double[]{lon,lat}> in order (source -> aux points -> destination)
     */
    public static void writeLines(File outputDir, String typeName, String attrSchema,
            List<Map<String, Object>> rows) throws Exception {

        if (rows.isEmpty()) return;

        String fullSchema = "the_geom:LineString:srid=4326," + attrSchema;
        SimpleFeatureType type = DataUtilities.createType(typeName, fullSchema);

        DefaultFeatureCollection collection = new DefaultFeatureCollection();
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder(type);

        for (Map<String, Object> row : rows) {
            @SuppressWarnings("unchecked")
            List<double[]> pts = (List<double[]>) row.get("points");
            if (pts == null || pts.size() < 2) continue; // need at least 2 points for a line

            Coordinate[] coords = pts.stream()
                    .map(p -> new Coordinate(p[0], p[1]))
                    .toArray(Coordinate[]::new);
            LineString line = GEOM_FACTORY.createLineString(coords);

            builder.add(line);
            for (String attrName : type.getAttributeDescriptors().stream()
                    .skip(1).map(d -> d.getLocalName()).toArray(String[]::new)) {
                builder.add(row.get(attrName));
            }
            collection.add(builder.buildFeature(null));
        }

        writeShapefile(outputDir, typeName, type, collection);
    }

    private static void writeShapefile(File outputDir, String typeName, SimpleFeatureType type,
            DefaultFeatureCollection collection) throws Exception {

        File shpFile = new File(outputDir, typeName + ".shp");
        ShapefileDataStoreFactory dsFactory = new ShapefileDataStoreFactory();
        ShapefileDataStore dataStore = (ShapefileDataStore) dsFactory.createNewDataStore(
                java.util.Collections.singletonMap("url", shpFile.toURI().toURL()));
        dataStore.createSchema(type);
        dataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);

        SimpleFeatureStore featureStore = (SimpleFeatureStore) dataStore.getFeatureSource(typeName);
        featureStore.addFeatures(collection);
        dataStore.dispose();
    }
}