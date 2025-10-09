package com.aliat.alm.dandemutande.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.aliat.alm.common.AlmDbSession;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KmlService {
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private String str = null;

	@SuppressWarnings("rawtypes")
	private static Query query = null;

	private static final Logger logger = LoggerFactory.getLogger(KmlService.class);

	/**
	 * Main method to extract and display KML data
	 */
	public void extractKmlData() {
		System.out.println("=== KML PARSER STARTED ===");

		// Your file path - update if needed
		String kmlFilePath = "D:/0Bilal/0ALMENSAAH/Projects/0HusseinRashad/Dande Fibre Coverage  Harare/doc.kml";

		try {
			File kmlFile = new File(kmlFilePath);

			// Check if file exists
			if (!kmlFile.exists()) {
				System.out.println("❌ ERROR: File not found: " + kmlFilePath);
				System.out.println("Absolute path: " + kmlFile.getAbsolutePath());
				return;
			}

			System.out.println("✅ File found: " + kmlFile.getAbsolutePath());
			System.out.println("📁 File size: " + kmlFile.length() + " bytes");

			// Parse the KML file
			List<Map<String, Object>> placemarks = parseKmlFile(kmlFile);

			System.out.println("\n=== PARSING COMPLETE ===");
			System.out.println("✅ Total placemarks found: " + placemarks.size());

			// Display all placemarks
			displayAllPlacemarks(placemarks);
			fillingTAM(placemarks);

		} catch (Exception e) {
			System.out.println("❌ ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Parse KML file and extract all placemarks
	 */
	private List<Map<String, Object>> parseKmlFile(File kmlFile) throws Exception {
		List<Map<String, Object>> placemarks = new ArrayList<>();

		System.out.println("\n🔄 Parsing KML file...");

		// Create XML parser
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(kmlFile);

		// Normalize XML structure
		document.getDocumentElement().normalize();

		// Find all Placemark elements
		NodeList placemarkNodes = document.getElementsByTagName("Placemark");
		System.out.println("📊 Found " + placemarkNodes.getLength() + " placemark elements");

		// Process each placemark
		for (int i = 0; i < placemarkNodes.getLength(); i++) {
			Node node = placemarkNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element placemarkElement = (Element) node;
				Map<String, Object> placemarkData = parsePlacemark(placemarkElement);
				placemarks.add(placemarkData);
			}
		}

		return placemarks;
	}

	/**
	 * Parse individual placemark element
	 */
	private Map<String, Object> parsePlacemark(Element placemarkElement) {
		Map<String, Object> placemark = new HashMap<>();

		// Extract basic information
		String name = getElementText(placemarkElement, "name");
		String description = getElementText(placemarkElement, "description");

		placemark.put("name", name.isEmpty() ? "Unnamed" : name);
		placemark.put("description", description);

		// Check geometry type
		if (hasElement(placemarkElement, "Point")) {
			parsePointGeometry(placemarkElement, placemark);
		} else if (hasElement(placemarkElement, "LineString")) {
			parseLineStringGeometry(placemarkElement, placemark);
		} else {
			placemark.put("type", "UNKNOWN");
			placemark.put("geometryType", "Unknown");
		}

		// Extract extended data
		parseExtendedData(placemarkElement, placemark);

		return placemark;
	}

	/**
	 * Parse Point geometry (LOCATION)
	 */
	private void parsePointGeometry(Element placemarkElement, Map<String, Object> placemark) {
		Element pointElement = getFirstElement(placemarkElement, "Point");
		if (pointElement != null) {
			placemark.put("type", "LOCATION");
			placemark.put("geometryType", "Point");

			String coordinates = getElementText(pointElement, "coordinates");
			placemark.put("coordinates", coordinates.trim());

			// Parse individual coordinate values
			if (!coordinates.trim().isEmpty()) {
				parseCoordinates(coordinates, placemark);
			}
		}
	}

	/**
	 * Parse LineString geometry (PATH)
	 */
	private void parseLineStringGeometry(Element placemarkElement, Map<String, Object> placemark) {
		Element lineStringElement = getFirstElement(placemarkElement, "LineString");
		if (lineStringElement != null) {
			placemark.put("type", "PATH");
			placemark.put("geometryType", "LineString");

			String coordinates = getElementText(lineStringElement, "coordinates");
			placemark.put("coordinates", coordinates.trim());

			// Count points and get samples
			if (!coordinates.trim().isEmpty()) {
				String[] points = coordinates.trim().split("\\s+");
				placemark.put("pointCount", points.length);

				// Store the cable points

				List<String> cablePts = new ArrayList<>();
				// int cablePtsCount = Math.min(3, points.length);
				for (int i = 0; i < points.length; i++) {
					cablePts.add(points[i]);
				}
				placemark.put("samplePoints", cablePts);

				// Store first few points as samples
				/*
				 * List<String> samples = new ArrayList<>(); int sampleCount = Math.min(3,
				 * points.length); for (int i = 0; i < sampleCount; i++) {
				 * samples.add(points[i]); } placemark.put("samplePoints", samples);
				 */
			}
		}
	}

	/**
	 * Parse coordinates string into individual values
	 */
	private void parseCoordinates(String coordinates, Map<String, Object> placemark) {
		try {
			String[] parts = coordinates.trim().split(",");
			if (parts.length >= 2) {
				placemark.put("longitude", Double.parseDouble(parts[0].trim()));
				placemark.put("latitude", Double.parseDouble(parts[1].trim()));
				if (parts.length >= 3) {
					placemark.put("altitude", Double.parseDouble(parts[2].trim()));
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("⚠  Warning: Could not parse coordinates: " + coordinates);
		}
	}

	/**
	 * Parse extended data from SchemaData
	 */
	private void parseExtendedData(Element placemarkElement, Map<String, Object> placemark) {
		Element extendedDataElement = getFirstElement(placemarkElement, "ExtendedData");
		if (extendedDataElement != null) {
			Element schemaDataElement = getFirstElement(extendedDataElement, "SchemaData");
			if (schemaDataElement != null) {
				Map<String, String> extendedData = new HashMap<>();
				NodeList simpleDataNodes = schemaDataElement.getElementsByTagName("SimpleData");

				for (int i = 0; i < simpleDataNodes.getLength(); i++) {
					Element simpleData = (Element) simpleDataNodes.item(i);
					String key = simpleData.getAttribute("name");
					String value = simpleData.getTextContent().trim();
					if (!key.isEmpty() && !value.isEmpty()) {
						extendedData.put(key, value);
					}
				}

				if (!extendedData.isEmpty()) {
					placemark.put("extendedData", extendedData);
				}
			}
		}
	}

	/**
	 * Display all placemarks in a formatted way
	 * @throws JsonProcessingException 
	 */
	private void displayAllPlacemarks(List<Map<String, Object>> placemarks) throws JsonProcessingException {
		int locationCount = 0;
		int pathCount = 0;

		for (int i = 0; i < placemarks.size(); i++) {
			Map<String, Object> placemark = placemarks.get(i);


			System.out.println("\n" + "═".repeat(70));
			System.out.println("📍 PLACEMARK #" + (i + 1) + ": " + placemark.get("name"));
			System.out.println("═".repeat(70));
			System.out.println("placemark as Map is " +mapper.writeValueAsString(placemark));

			// Basic info
			String desc = (String) placemark.get("description");
			if (!desc.isEmpty()) {
				System.out.println("📝 Description: " + desc);
			}

			String type = (String) placemark.get("type");
			System.out.println("🔧 Type: " + type);
			System.out.println("📐 Geometry: " + placemark.get("geometryType"));

			// Type-specific information
			if ("LOCATION".equals(type)) {
				locationCount++;
				displayLocationInfo(placemark);
			} else if ("PATH".equals(type)) {
				pathCount++;
				displayPathInfo(placemark);
			}

			// Extended data
			displayExtendedData(placemark);
		}

		// Summary
		System.out.println("\n" + "⭐".repeat(50));
		System.out.println("📊 SUMMARY:");
		System.out.println("⭐ Total Placemarks: " + placemarks.size());
		System.out.println("📍 Locations (Points): " + locationCount);
		System.out.println("🛣  Paths (LineStrings): " + pathCount);
		System.out.println("⭐".repeat(50));
	}

	/**
	 * Display location (Point) information
	 */
	private void displayLocationInfo(Map<String, Object> placemark) {
		System.out.println("🎯 COORDINATES:");
		System.out.println("   Raw: " + placemark.get("coordinates"));

		if (placemark.containsKey("longitude")) {
			System.out.printf("   Parsed: Longitude=%.6f, Latitude=%.6f", placemark.get("longitude"),
					placemark.get("latitude"));
			if (placemark.containsKey("altitude")) {
				System.out.printf(", Altitude=%.1f", placemark.get("altitude"));
			}
			System.out.println();
		}
	}

	/**
	 * Display path (LineString) information
	 */
	private void displayPathInfo(Map<String, Object> placemark) {
		System.out.println("🛣  PATH INFO:");
		System.out.println("   Total Points: " + placemark.get("pointCount"));

		if (placemark.containsKey("samplePoints")) {
			@SuppressWarnings("unchecked")
			List<String> samples = (List<String>) placemark.get("samplePoints");
			System.out.println("   Sample Points:");
			for (int j = 0; j < samples.size(); j++) {
				System.out.println("     " + (j + 1) + ". " + samples.get(j));
			}
		}

		String coords = (String) placemark.get("coordinates");
		if (coords.length() > 100) {
			System.out.println("   Coordinates (first 100 chars):");
			System.out.println("     " + coords.substring(0, 100) + "...");
		}
	}

	/**
	 * Display extended data if available
	 */
	private void displayExtendedData(Map<String, Object> placemark) {
		if (placemark.containsKey("extendedData")) {
			System.out.println("📋 EXTENDED DATA:");
			@SuppressWarnings("unchecked")
			Map<String, String> extendedData = (Map<String, String>) placemark.get("extendedData");

			for (Map.Entry<String, String> entry : extendedData.entrySet()) {
				System.out.println("   " + entry.getKey() + ": " + entry.getValue());
			}
		}
	}

	// ==================== HELPER METHODS ====================

	/**
	 * Get text content of an element
	 */
	private String getElementText(Element parent, String tagName) {
		NodeList nodes = parent.getElementsByTagName(tagName);
		if (nodes.getLength() > 0) {
			Node node = nodes.item(0);
			return node.getTextContent() != null ? node.getTextContent().trim() : "";
		}
		return "";
	}

	/**
	 * Check if element exists
	 */
	private boolean hasElement(Element parent, String tagName) {
		return parent.getElementsByTagName(tagName).getLength() > 0;
	}

	/**
	 * Get first element by tag name
	 */
	private Element getFirstElement(Element parent, String tagName) {
		NodeList nodes = parent.getElementsByTagName(tagName);
		if (nodes.getLength() > 0) {
			return (Element) nodes.item(0);
		}
		return null;
	}

	/**
	 * Get all placemarks as structured data (for programmatic use)
	 */
	public List<Map<String, Object>> getAllPlacemarksData() {
		String kmlFilePath = "D:/0Bilal/0ALMENSAAH/Projects/0HusseinRashad/Dande Fibre Coverage  Harare/doc.kml";
		try {
			File kmlFile = new File(kmlFilePath);
			if (kmlFile.exists()) {
				return parseKmlFile(kmlFile);
			} else {
				System.out.println("File not found: " + kmlFilePath);
			}
		} catch (Exception e) {
			System.out.println("Error reading KML file: " + e.getMessage());
		}
		return new ArrayList<>();
	}

	private void fillingTAM(List<Map<String, Object>> placemarks) {
		int locationCount = 0;
		int pathCount = 0;
		String strQry = "";
		List<String> cablePts = new ArrayList<String>();
		String sourcePt = "";
		String [] sourceParts = new String[0];
		String sourcePtLat = "";
		String sourcePtLong = "";
		String destPt = "";
		String [] destParts = new String[0];
		String destPtLat = "";
		String destPtLong = "";				
		String cableID = "FIBER2025_";
		String auxID = "AUXILIARY_PT_2025_";
		int auxCount = 0;
		String mhID = "MH_2025_";
		int mhCount = 0;
		String wareID = "WARE_2025_";
		int wareCount = 0;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
		}

		try {
			for (int i = 0; i < placemarks.size(); i++) {
				Map<String, Object> placemark = placemarks.get(i);
				String type = (String) placemark.get("type");
				if ("LOCATION".equals(type)) {
					if (placemark.get("name") != null && (placemark.get("name").toString().contains("MH"))) {
						strQry = "INSERT INTO MANHOLE (MANHOLE_ID,MANHOLE_NAME,LONGITUDE,LATITUDE,CREATION_DATE,LAST_MODIFIED_DATE,"
								+ "PROJECT_ID, DM_NAME) "
								+ "values ('" + mhID+mhCount+1 +"','" + placemark.get("name") + "','" + placemark.get("longitude")
								+ "','" + placemark.get("latitude") + "', sysdate, sysdate, 'CurrentPhysicalLayer','"
								+ placemark.get("name") + "')";
						System.out.println("The Manhole String Query is " + strQry);
						query = session.createNativeQuery(strQry);
						query.executeUpdate();
						mhCount++; 
					}
					else if (placemark.get("name") != null) {
						strQry = "INSERT INTO WAREHOUSE (WARE_ID,CREATION_DATE,LAST_MODIFY_DATE,WARE_NAME,SITE_ID,LONGITUDE,LATITUDE,"
								+ "SITE,TECH_2G,TECH_3G,TECH_4G,TECH_5G) "
								+ "values (?,sysdate, sysdate,?,?,?,?,'1','0','0','0','0')";
						System.out.println("The Warehouse strQuery is " +strQry);
						query = session.createNativeQuery(strQry);
						query.setParameter(1, wareID+wareCount+1);
						query.setParameter(2, placemark.get("name"));
						query.setParameter(3, placemark.get("name"));
						query.setParameter(4, placemark.get("longitude"));
						query.setParameter(5, placemark.get("latitude"));
						query.executeUpdate();
						wareCount++; 
					}
				}
/*				
				else if ("PATH".equals(type)) {
					System.out.println("The type is Path");
					cablePts = (List<String>) placemark.get("samplePoints");
					if (cablePts.size() >= 2) {
						System.out.println("Count Points is more than 2 points");
						sourcePt = cablePts.get(0);
						sourceParts = sourcePt.split(",");
						sourcePtLong = sourceParts[0];
						sourcePtLat = sourceParts[1];					
						destPt = cablePts.get(cablePts.size()-1);
						destParts = destPt.split(",");
						destPtLong = destParts[0];
						destPtLat = destParts[1];
					}
					pathCount++;
					cableID = "FIBER2025_" + pathCount;
					System.out.println("the cableID is " +cableID + " cable name is " +placemark.get("name") + " sourceLong is " +sourcePtLong + " sourceLat is " + sourcePtLat + " destLong is " +sourcePtLat + " destLat is " +destPtLat + "\n");
					
					strQry = "INSERT INTO FIBER_CABLES (FIBER_CABLE_ID,FIBER_CABLE_NAME,SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,PROJECT_ID,CREATION_DATE,"
							+ "LAST_MODIFIED_DATE,CREATED_BY,LAST_MODIFIED_BY) values ('"+ cableID + "','" + placemark.get("name") + "','"
							+ sourcePtLong + "','" + sourcePtLat+ "','" + destPtLong + "','" + destPtLat + "','CurrentPhysicalLayer',sysdate,"
							+ "sysdate,'admin','admin')";
					System.out.println("str is " + strQry + "\n");					
					strQry = "INSERT INTO FIBER_CABLES (FIBER_CABLE_ID,FIBER_CABLE_NAME,SOURCE_LNG,SOURCE_LAT,DESTINATION_LNG,DESTINATION_LAT,PROJECT_ID,"
							+ "FIBER_NETWORK_LEVEL,FIBER_OWNER,CREATION_DATE,LAST_MODIFIED_DATE,CREATED_BY,LAST_MODIFIED_BY) "
							+ "values (?,?,?,?,?,?,'CurrentPhysicalLayer','backbone','dandimutandi',sysdate,sysdate,'admin','admin')";
					
					query = session.createNativeQuery(strQry);
					query.setParameter(1, cableID);
					query.setParameter(2, placemark.get("name"));
					query.setParameter(3, sourcePtLong);
					query.setParameter(4, sourcePtLat);
					query.setParameter(5, destPtLong);
					query.setParameter(6, destPtLat);
										
					query.executeUpdate();
					session.flush();
					if (cablePts.size() > 2) {
						System.out.println("Filling the auxiliary points, the cablePt size is " +cablePts.size());
						for (int j=0; j < cablePts.size()-2; j++) {
							auxID = "AUXILIARY_PT_2025_" + (auxCount + j + 1);
							System.out.print("The auxID is " +auxID);
							System.out.print("\n Long is " + cablePts.get(j+1).split(",")[0] + " and Lat is " + cablePts.get(j+1).split(",")[1] + "\n");
							strQry = "INSERT INTO FIBER_AUXILIARY_POINTS (AUXILIARY_ID,FIBER_CABLE_ID,LONGITUDE,LATITUDE,CREATION_DATE,LAST_MODIFIED_DATE,SEQ_SORTING) "
							       + "values ('"+ auxID + "','"+ cableID + "','" + cablePts.get(j+1).split(",")[0] + "','"
								   + cablePts.get(j+1).split(",")[1] + "',sysdate,sysdate,'"+ (j+1) +"')";
							System.out.println("the qry is " +strQry + "\n");
									
							strQry = "INSERT INTO FIBER_AUXILIARY_POINTS (AUXILIARY_ID,FIBER_CABLE_ID,LONGITUDE,LATITUDE,CREATION_DATE,LAST_MODIFIED_DATE,SEQ_SORTING) "
									+ "values (?,?,?,?,sysdate,sysdate,'"+ (j+1) +"')";
							System.out.println("the qry with question mark is " +strQry + "\n");
 
							query = session.createNativeQuery(
									"INSERT INTO FIBER_AUXILIARY_POINTS (AUXILIARY_ID,FIBER_CABLE_ID,LONGITUDE,LATITUDE,CREATION_DATE,"
									+ "LAST_MODIFIED_DATE,SEQ_SORTING) "
									+ "values ('"+ auxID + "','"+ cableID + "','" + cablePts.get(j+1).split(",")[0] + "','"
									+ cablePts.get(j+1).split(",")[1] + "',sysdate,sysdate,'"+ (j+1) +"')");
							query.executeUpdate();							
						}
						auxCount = auxCount + cablePts.size()-2;
					}					
					//displayPathInfo(placemark);
				}
*/
			}


/*
				System.out.println("\n" + "═".repeat(70));
				System.out.println("📍 PLACEMARK #" + (i + 1) + ": " + placemark.get("name"));
				System.out.println("═".repeat(70));

				// Basic info
				String desc = (String) placemark.get("description");
				if (!desc.isEmpty()) {
					System.out.println("📝 Description: " + desc);
				}

				System.out.println("🔧 Type: " + type);
				System.out.println("📐 Geometry: " + placemark.get("geometryType"));

				// Type-specific information
				if ("LOCATION".equals(type)) {
					locationCount++;
					displayLocationInfo(placemark);
				} else if ("PATH".equals(type)) {
					pathCount++;
					displayPathInfo(placemark);
				}

				// Extended data
				displayExtendedData(placemark);
			}

			// Summary
			System.out.println("\n" + "⭐".repeat(50));
			System.out.println("📊 SUMMARY:");
			System.out.println("⭐ Total Placemarks: " + placemarks.size());
			System.out.println("📍 Locations (Points): " + locationCount);
			System.out.println("🛣  Paths (LineStrings): " + pathCount);
			System.out.println("⭐".repeat(50));
*/			

		}

		catch (Exception e) {
			logger.info("Error in filling kml parsed data in the TAM database with a message :" + e);
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			}
		}
	}
}