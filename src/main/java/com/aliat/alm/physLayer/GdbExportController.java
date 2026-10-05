package com.aliat.alm.physLayer;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.models.ExportEntry;
import com.aliat.alm.services.LoginServices;
import com.aliat.alm.gdb.GdbExportRegistry;
import com.aliat.alm.gdb.GdbFileUtils;
import com.aliat.alm.gdb.ShapefileWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class GdbExportController {

	private static final Logger logger = Logger.getLogger(GdbExportController.class.getName());

	@Autowired
	private GdbExportRegistry gdbExportRegistry;

	// ---------- Endpoint 1: generate the GDB, return a token ----------

	@RequestMapping(value = "/exportProjectGdb", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> exportProjectGdb(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		logger.log(Level.INFO, "Welcome to exportProjectGdb");
		Map<String, Object> rtn = new LinkedHashMap<>();
		Session session = AlmDbSession.getInstance().getSession();
		Transaction tx = null;

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		String projectId = request.getParameter("projectId");
		if (projectId == null || projectId.trim().isEmpty()) {
			rtn.put("success", false);
			rtn.put("message", "Missing projectId");
			return rtn;
		}
		if (!projectId.matches("[A-Za-z0-9_\\-]+")) {
			rtn.put("success", false);
			rtn.put("message", "Invalid projectId");
			return rtn;
		}

		File workDir = null;

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				// 1. Friendly name for the output file
				String projectName = fetchProjectName(session, projectId);
				if (projectName == null || projectName.trim().isEmpty()) {
					projectName = "PROJECT_" + projectId;
				}

				// 2. Isolated temp sandbox for this export
				String token = UUID.randomUUID().toString();
				File baseTempDir = new File(getGdbTempBasePath());
				if (!baseTempDir.exists())
					baseTempDir.mkdirs();
				workDir = new File(baseTempDir, token);
				workDir.mkdirs();

				File shapefileDir = new File(workDir, "shp_src");
				shapefileDir.mkdirs();

				// 3. Query project elements, write shapefiles (schema-specific — see below)
				collectProjectFeatures(session, projectId, shapefileDir);

				// 4. Shapefiles -> File Geodatabase
				File gdbDir = new File(workDir, GdbFileUtils.sanitizeFileName(projectName) + ".gdb");
				GdbFileUtils.runOgr2Ogr(shapefileDir, gdbDir);

				if (!gdbDir.exists()) {
					throw new RuntimeException("GDB generation failed - output directory not created");
				}

				// 5. Zip it (browsers can't download a folder)

				File zipFile = new File(workDir, GdbFileUtils.sanitizeFileName(projectName) + "_export.zip");
				GdbFileUtils.zipMultipleDirectories(zipFile, gdbDir, shapefileDir);

				// 6. Register token -> file so /downloadGdb can find it later
				gdbExportRegistry.registerExport(token, zipFile.getAbsolutePath(), zipFile.getName());

				rtn.put("success", true);
				rtn.put("downloadToken", token);
				rtn.put("fileName", zipFile.getName());

				tx.commit();
			} catch (Exception e) {
				if (tx != null)
					tx.rollback();
				logger.log(Level.SEVERE, "Error in exportProjectGdb due to", e);
				rtn.put("success", false);
				rtn.put("message", "Export failed");
				if (workDir != null)
					GdbFileUtils.deleteRecursively(workDir);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	// ---------- Endpoint 2: stream the already-built file ----------

	@RequestMapping(value = "/downloadGdb", method = RequestMethod.GET)
	public void downloadGdb(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		String token = request.getParameter("token");
		if (token == null || !token.matches("[a-fA-F0-9\\-]{36}")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token");
			return;
		}

		ExportEntry entry = gdbExportRegistry.lookupExport(token);
		if (entry == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Export not found or expired");
			return;
		}

		File file = new File(entry.getFilePath());
		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "File no longer available");
			gdbExportRegistry.removeExport(token);
			return;
		}

		String fileName = entry.getFileName();
		String encodedName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");

		response.setContentType("application/zip");
		response.setContentLengthLong(file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName.replaceAll("[^a-zA-Z0-9._\\-]", "_") + "\"; filename*=UTF-8''" + encodedName);

		try (InputStream in = new BufferedInputStream(new FileInputStream(file));
				OutputStream out = response.getOutputStream()) {
			byte[] buffer = new byte[8192];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			out.flush();
		} catch (IOException e) {
			logger.log(Level.WARNING, "Download interrupted for token " + token, e);
		} finally {
			gdbExportRegistry.removeExport(token);
			GdbFileUtils.deleteRecursively(file.getParentFile());
		}
	}

	// ---------- Helpers you need to finish ----------

	private String getGdbTempBasePath() {
		// Point this at a real writable path on your server, ideally from a
		// properties file rather than hardcoded, e.g.:
		// return env.getProperty("gdb.export.tempdir");
		// return "/var/tam/gdb_exports";

		String path = "D:/gdb_exports"; // TODO: replace with SystemSettings lookup
		File dir = new File(path);
		if (!dir.exists() && !dir.mkdirs()) {
			throw new RuntimeException("Cannot create or access GDB export temp directory: " + path);
		}
		return path;
	}

	private String fetchProjectName(Session session, String projectId) {
		return (String) session.createNativeQuery("SELECT PROJECT_NAME FROM DEMO.PROJECT WHERE PROJECT_ID = :pid")
				.setParameter("pid", projectId).uniqueResult();
	}

	private void collectProjectFeatures(Session session, String projectId, File outputDir) throws Exception {
		writeManholes(session, projectId, outputDir);
		writeHandholes(session, projectId, outputDir);
		writeJunctions(session, projectId, outputDir);
		writeDistributionBoards(session, projectId, outputDir);
		writeFiberCables(session, projectId, outputDir);
		writeDucts(session, projectId, outputDir);
		writeTrenches(session, projectId, outputDir);
	}

	@SuppressWarnings("unchecked")
	private void writeManholes(Session session, String projectId, File outputDir) throws Exception {
		List<Object[]> rows = session.createNativeQuery(
				"SELECT MANHOLE_ID, MANHOLE_NAME, MANHOLE_MODEL, LONGITUDE, MH_INSTALLER, MH_ENGINEER_NAME, "
						+ "OWNER, LATITUDE, CREATION_DATE, LAST_MODIFIED_DATE, CITY, PROJECT_ID, DM_NAME "
						+ "FROM MANHOLE WHERE PROJECT_ID = :pid")
				.setParameter("pid", projectId).list();

		List<Map<String, Object>> features = new ArrayList<>();
		for (Object[] r : rows) {
			Double lon = parseCoord(r[3]);
			Double lat = parseCoord(r[7]);
			if (lon == null || lat == null)
				continue;

			Map<String, Object> f = new LinkedHashMap<>();
			f.put("lon", lon);
			f.put("lat", lat);
			f.put("MH_ID", r[0]);
			f.put("MH_NAME", r[1]);
			f.put("MODEL", r[2]);
			f.put("LONGITUDE", r[3]);
			f.put("INSTALLER", r[4]);
			f.put("ENGINEER", r[5]);
			f.put("OWNER", r[6]);
			f.put("LATITUDE", r[7]);
			f.put("CR_DATE", r[8]);
			f.put("MOD_DATE", r[9]);
			f.put("CITY", r[10]);
			f.put("PROJ_ID", r[11]);
			f.put("DM_NAME", r[12]);
			features.add(f);
		}

		ShapefileWriter.writePoints(outputDir, "Manhole",
				"MH_ID:String,MH_NAME:String,MODEL:String,LONGITUDE:String,INSTALLER:String,ENGINEER:String,"
						+ "OWNER:String,LATITUDE:String,CR_DATE:java.util.Date,MOD_DATE:java.util.Date,"
						+ "CITY:String,PROJ_ID:String,DM_NAME:String",
				features);
	}

	@SuppressWarnings("unchecked")
	private void writeHandholes(Session session, String projectId, File outputDir) throws Exception {
		List<Object[]> rows = session.createNativeQuery(
				"SELECT HANDHOLE_ID, HANDHOLE_NAME, HANDHOLE_MODEL, LONGITUDE, LATITUDE, CITY, OWNER, "
						+ "HH_INSTALLER, HH_ENGINEER_NAME, CREATION_DATE, LAST_MODIFIED_DATE, PROJECT_ID, DM_NAME "
						+ "FROM HANDHOLE WHERE PROJECT_ID = :pid")
				.setParameter("pid", projectId).list();

		List<Map<String, Object>> features = new ArrayList<>();
		for (Object[] r : rows) {
			Double lon = parseCoord(r[3]);
			Double lat = parseCoord(r[4]);
			if (lon == null || lat == null)
				continue;

			Map<String, Object> f = new LinkedHashMap<>();
			f.put("lon", lon);
			f.put("lat", lat);
			f.put("HH_ID", r[0]);
			f.put("HH_NAME", r[1]);
			f.put("MODEL", r[2]);
			f.put("LONGITUDE", r[3]);
			f.put("LATITUDE", r[4]);
			f.put("CITY", r[5]);
			f.put("OWNER", r[6]);
			f.put("INSTALLER", r[7]);
			f.put("ENGINEER", r[8]);
			f.put("CR_DATE", r[9]);
			f.put("MOD_DATE", r[10]);
			f.put("PROJ_ID", r[11]);
			f.put("DM_NAME", r[12]);
			features.add(f);
		}

		ShapefileWriter.writePoints(outputDir, "Handhole",
				"HH_ID:String,HH_NAME:String,MODEL:String,LONGITUDE:String,LATITUDE:String,CITY:String,"
						+ "OWNER:String,INSTALLER:String,ENGINEER:String,CR_DATE:java.util.Date,MOD_DATE:java.util.Date,"
						+ "PROJ_ID:String,DM_NAME:String",
				features);
	}

	@SuppressWarnings("unchecked")
	private void writeJunctions(Session session, String projectId, File outputDir) throws Exception {
		List<Object[]> rows = session.createNativeQuery(
				"SELECT JUNCTION_ID, JUNCTION_NAME, PHYSICAL_LAYER_ID, PHYSICAL_LAYER_NAME, LONGITUDE, OWNER, "
						+ "JUNC_INSTALLER, JUNC_ENGINEER_NAME, LATITUDE, CREATION_DATE, LAST_MODIFIED_DATE, CAPACITY, "
						+ "JUNCTION_NUMBER, CITY, PROJECT_ID, JUNCTION_TYPE " + "FROM JUNCTION WHERE PROJECT_ID = :pid")
				.setParameter("pid", projectId).list();

		List<Map<String, Object>> features = new ArrayList<>();
		for (Object[] r : rows) {
			Double lon = parseCoord(r[4]);
			Double lat = parseCoord(r[8]);
			if (lon == null || lat == null)
				continue;

			Map<String, Object> f = new LinkedHashMap<>();
			f.put("lon", lon);
			f.put("lat", lat);
			f.put("JCT_ID", r[0]);
			f.put("JCT_NAME", r[1]);
			f.put("PL_ID", r[2]);
			f.put("PL_NAME", r[3]);
			f.put("LONGITUDE", r[4]);
			f.put("OWNER", r[5]);
			f.put("INSTALLER", r[6]);
			f.put("ENGINEER", r[7]);
			f.put("LATITUDE", r[8]);
			f.put("CR_DATE", r[9]);
			f.put("MOD_DATE", r[10]);
			f.put("CAPACITY", parseCoord(r[11]));
			f.put("JCT_NUM", parseCoord(r[12]));
			f.put("CITY", r[13]);
			f.put("PROJ_ID", r[14]);
			f.put("JCT_TYPE", r[15]);
			features.add(f);
		}

		ShapefileWriter.writePoints(outputDir, "Junction",
				"JCT_ID:String,JCT_NAME:String,PL_ID:String,PL_NAME:String,LONGITUDE:String,OWNER:String,"
						+ "INSTALLER:String,ENGINEER:String,LATITUDE:String,CR_DATE:java.util.Date,MOD_DATE:java.util.Date,"
						+ "CAPACITY:Double,JCT_NUM:Double,CITY:String,PROJ_ID:String,JCT_TYPE:String",
				features);
	}

	@SuppressWarnings("unchecked")
	private void writeDistributionBoards(Session session, String projectId, File outputDir) throws Exception {
		List<Object[]> rows = session.createNativeQuery(
				"SELECT DB_ID, DB_LONGITUDE, DB_NETWORK_LEVEL, DB_LATITUDE, DB_NAME, MAX_CAPACITY, DB_INSTALLER, "
						+ "DB_ENGINEER_NAME, DB_DEPLOYMENT_TYPE, DB_ADAPTOR_PANEL_TYPE, CREATION_DATE, LAST_MODIFIED_DATE, "
						+ "SITE, NUM_ROWS, NUM_COLUMNS, FRONT_PORTS_CONNECTED, BACK_PORTS_CONNECTED, CITY, PROJECT_ID, "
						+ "SITE_NAME, WAREHOUSE, SERIAL_NUMB, CONTROLLER_ID, CONTROLLER_NAME, DB_TYPE, ROW_COUNTING, "
						+ "ROW_PER_MODULE, TOTAL_NUM_MODULE, DB_SUBTYPE, IS_SPLITTER, SPLITTER_RATIO, SPLITTER_INPUT_PORT_COUNT "
						+ "FROM DISTRIBUTION_BOARD WHERE PROJECT_ID = :pid")
				.setParameter("pid", projectId).list();

		List<Map<String, Object>> features = new ArrayList<>();
		for (Object[] r : rows) {
			Double lon = parseCoord(r[1]);
			Double lat = parseCoord(r[3]);
			if (lon == null || lat == null)
				continue;

			Map<String, Object> f = new LinkedHashMap<>();
			f.put("lon", lon);
			f.put("lat", lat);
			f.put("DB_ID", r[0]);
			f.put("LONGITUDE", r[1]);
			f.put("NET_LEVEL", r[2]);
			f.put("LATITUDE", r[3]);
			f.put("DB_NAME", r[4]);
			f.put("MAX_CAP", parseCoord(r[5]));
			f.put("INSTALLER", r[6]);
			f.put("ENGINEER", r[7]);
			f.put("DEPLOY_TYP", r[8]);
			f.put("PANEL_TYPE", r[9]);
			f.put("CR_DATE", r[10]);
			f.put("MOD_DATE", r[11]);
			f.put("SITE", r[12]);
			f.put("NUM_ROWS", parseCoord(r[13]));
			f.put("NUM_COLS", parseCoord(r[14]));
			f.put("FP_CONN", parseCoord(r[15]));
			f.put("BP_CONN", parseCoord(r[16]));
			f.put("CITY", r[17]);
			f.put("PROJ_ID", r[18]);
			f.put("SITE_NAME", r[19]);
			f.put("WAREHOUSE", r[20]);
			f.put("SERIAL_NUM", r[21]);
			f.put("CTRL_ID", r[22]);
			f.put("CTRL_NAME", r[23]);
			f.put("DB_TYPE", r[24]);
			f.put("ROW_COUNT", r[25]);
			f.put("ROW_PER_M", parseCoord(r[26]));
			f.put("TOT_MOD", parseCoord(r[27]));
			f.put("DB_SUBTYP", r[28]);
			f.put("IS_SPLIT", r[29] != null ? r[29].toString() : null);
			f.put("SPLIT_RAT", r[30]);
			f.put("SPLIT_CNT", parseCoord(r[31]));
			features.add(f);
		}

		ShapefileWriter.writePoints(outputDir, "DistributionBoard",
				"DB_ID:String,LONGITUDE:String,NET_LEVEL:String,LATITUDE:String,DB_NAME:String,MAX_CAP:Double,"
						+ "INSTALLER:String,ENGINEER:String,DEPLOY_TYP:String,PANEL_TYPE:String,CR_DATE:java.util.Date,"
						+ "MOD_DATE:java.util.Date,SITE:String,NUM_ROWS:Double,NUM_COLS:Double,FP_CONN:Double,BP_CONN:Double,"
						+ "CITY:String,PROJ_ID:String,SITE_NAME:String,WAREHOUSE:String,SERIAL_NUM:String,CTRL_ID:String,"
						+ "CTRL_NAME:String,DB_TYPE:String,ROW_COUNT:String,ROW_PER_M:Double,TOT_MOD:Double,DB_SUBTYP:String,"
						+ "IS_SPLIT:String,SPLIT_RAT:String,SPLIT_CNT:Double",
				features);
	}

	@SuppressWarnings("unchecked")
	private void writeFiberCables(Session session, String projectId, File outputDir) throws Exception {
		// Field mapping (shapefile name = original column):
		// FBR_ID=FIBER_CABLE_ID, SRC_WAREID=SOURCE_WARE_ID,
		// LAST_AUX_D=LAST_AUXILIARY_TO_DESTINATION_DISTANCE,
		// LAST_AUXDD=LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE, SRC_ID=SOURCE_ID,
		// R_STRD_NUM=RELATED_STRAND_NUMBER, R_STRD_CLR=RELATED_STRAND_COLOR,
		// R_STRD_ID=RELATED_STRAND_ID,
		// R_STRD_NM=RELATED_STRAND_NAME, INSTALLER=FIBER_INSTALLER,
		// ENGINEER=FIBER_ENGINEER_NAME,
		// CABLE_SIZE=FIBER_CABLE_SIZE, R_TUBE_NUM=RELATED_TUBE_NUMBER,
		// R_TUBE_CLR=RELATED_TUBE_COLOR,
		// R_TUBE_ID=RELATED_TUBE_ID, R_TUBE_NM=RELATED_TUBE_NAME,
		// R_CABLEID=RELATED_CABLE_ID,
		// R_CABLENM=RELATED_CABLE_NAME, OS_LM_ID=OTHERSIDE_LASTMILE_ID,
		// OS_LM_NM=OTHERSIDE_LASTMILE_NAME,
		// OS_LOC_ID=OTHERSIDE_LOCATION_ID, OS_LOC_NM=OTHERSIDE_LOCATION_NAME,
		// OS_LOC_CTY=OTHERSIDE_LOCATION_CITY,
		// OS_LOC_TYP=OTHERSIDE_LOCATION_TYPE, JCT_ID=JUNCTION_ID,
		// JCT_NAME=JUNCTION_NAME,
		// JCT_PORT=JUNCTION_PORT_NUMBER, SRC_NAME=SOURCE_NAME,
		// DST_WAREID=DESTINATION_WARE_ID,
		// DST_ID=DESTINATION_ID, DST_NAME=DESTINATION_NAME, ITEM_CODE,
		// N_STRANDS=NUMBER_OF_STRANDS,
		// N_TUBES=NUMBER_OF_TUBES, LENGTH, CONDUIT_ID, CONDUIT_NM=CONDUIT_NAME,
		// SRC_LNG=SOURCE_LNG,
		// SRC_LAT=SOURCE_LAT, DST_LNG=DESTINATION_LNG, DST_LAT=DESTINATION_LAT,
		// CABLE_MODE,
		// FBR_NAME=FIBER_CABLE_NAME, SRC_CITY=SOURCE_CITY, DST_CITY=DESTINATION_CITY,
		// PROJ_ID=PROJECT_ID,
		// FBR_TYPE=FIBER_TYPE, FBR_DEPLOY=FIBER_DEPLOYMENT,
		// FBR_NETLVL=FIBER_NETWORK_LEVEL,
		// FBR_OWNER=FIBER_OWNER, CR_DATE=CREATION_DATE, MOD_DATE=LAST_MODIFIED_DATE,
		// CREATED_BY,
		// MOD_BY=LAST_MODIFIED_BY, TOT_DRV_DS=TOTAL_DRIVING_DISTANCE,
		// DRAW_TYPE=DRAWING_TYPE,
		// TOT_GEO_DS=TOTAL_GEO_DISTANCE, CABLE_ROLE=FTTH_CABLE_ROLE
		List<Object[]> cables = session
				.createNativeQuery("SELECT FIBER_CABLE_ID, SOURCE_WARE_ID, LAST_AUXILIARY_TO_DESTINATION_DISTANCE, "
						+ "LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE, SOURCE_ID, RELATED_STRAND_NUMBER, "
						+ "RELATED_STRAND_COLOR, RELATED_STRAND_ID, RELATED_STRAND_NAME, FIBER_INSTALLER, "
						+ "FIBER_ENGINEER_NAME, FIBER_CABLE_SIZE, RELATED_TUBE_NUMBER, RELATED_TUBE_COLOR, "
						+ "RELATED_TUBE_ID, RELATED_TUBE_NAME, RELATED_CABLE_ID, RELATED_CABLE_NAME, "
						+ "OTHERSIDE_LASTMILE_ID, OTHERSIDE_LASTMILE_NAME, OTHERSIDE_LOCATION_ID, OTHERSIDE_LOCATION_NAME, "
						+ "OTHERSIDE_LOCATION_CITY, OTHERSIDE_LOCATION_TYPE, JUNCTION_ID, JUNCTION_NAME, "
						+ "JUNCTION_PORT_NUMBER, SOURCE_NAME, DESTINATION_WARE_ID, DESTINATION_ID, DESTINATION_NAME, "
						+ "ITEM_CODE, NUMBER_OF_STRANDS, NUMBER_OF_TUBES, LENGTH, CONDUIT_ID, CONDUIT_NAME, "
						+ "SOURCE_LNG, SOURCE_LAT, DESTINATION_LNG, DESTINATION_LAT, CABLE_MODE, FIBER_CABLE_NAME, "
						+ "SOURCE_CITY, DESTINATION_CITY, PROJECT_ID, FIBER_TYPE, FIBER_DEPLOYMENT, "
						+ "FIBER_NETWORK_LEVEL, FIBER_OWNER, CREATION_DATE, LAST_MODIFIED_DATE, CREATED_BY, "
						+ "LAST_MODIFIED_BY, TOTAL_DRIVING_DISTANCE, DRAWING_TYPE, TOTAL_GEO_DISTANCE, FTTH_CABLE_ROLE "
						+ "FROM FIBER_CABLES WHERE PROJECT_ID = :pid")
				.setParameter("pid", projectId).list();

		List<Map<String, Object>> features = new ArrayList<>();

		for (Object[] c : cables) {
			String cableId = (String) c[0];
			Double srcLon = parseCoord(c[37]);
			Double srcLat = parseCoord(c[38]);
			Double dstLon = parseCoord(c[39]);
			Double dstLat = parseCoord(c[40]);
			if (srcLon == null || srcLat == null || dstLon == null || dstLat == null)
				continue;

			List<double[]> points = new ArrayList<>();
			points.add(new double[] { srcLon, srcLat });

			List<Object[]> auxPoints = session
					.createNativeQuery("SELECT LONGITUDE, LATITUDE FROM FIBER_AUXILIARY_POINTS "
							+ "WHERE FIBER_CABLE_ID = :cid ORDER BY SEQ_SORTING ASC")
					.setParameter("cid", cableId).list();
			for (Object[] a : auxPoints) {
				Double aLon = parseCoord(a[0]);
				Double aLat = parseCoord(a[1]);
				if (aLon != null && aLat != null)
					points.add(new double[] { aLon, aLat });
			}
			points.add(new double[] { dstLon, dstLat });

			Map<String, Object> f = new LinkedHashMap<>();
			f.put("points", points);
			f.put("FBR_ID", c[0]);
			f.put("SRC_WAREID", c[1]);
			f.put("LAST_AUX_D", parseCoord(c[2]));
			f.put("LAST_AUXDD", parseCoord(c[3]));
			f.put("SRC_ID", c[4]);
			f.put("R_STRD_NUM", c[5]);
			f.put("R_STRD_CLR", c[6]);
			f.put("R_STRD_ID", c[7]);
			f.put("R_STRD_NM", c[8]);
			f.put("INSTALLER", c[9]);
			f.put("ENGINEER", c[10]);
			f.put("CABLE_SIZE", c[11]);
			f.put("R_TUBE_NUM", c[12]);
			f.put("R_TUBE_CLR", c[13]);
			f.put("R_TUBE_ID", c[14]);
			f.put("R_TUBE_NM", c[15]);
			f.put("R_CABLEID", c[16]);
			f.put("R_CABLENM", c[17]);
			f.put("OS_LM_ID", c[18]);
			f.put("OS_LM_NM", c[19]);
			f.put("OS_LOC_ID", c[20]);
			f.put("OS_LOC_NM", c[21]);
			f.put("OS_LOC_CTY", c[22]);
			f.put("OS_LOC_TYP", c[23]);
			f.put("JCT_ID", c[24]);
			f.put("JCT_NAME", c[25]);
			f.put("JCT_PORT", c[26]);
			f.put("SRC_NAME", c[27]);
			f.put("DST_WAREID", c[28]);
			f.put("DST_ID", c[29]);
			f.put("DST_NAME", c[30]);
			f.put("ITEM_CODE", c[31]);
			f.put("N_STRANDS", parseCoord(c[32]));
			f.put("N_TUBES", parseCoord(c[33]));
			f.put("LENGTH", parseCoord(c[34]));
			f.put("CONDUIT_ID", c[35]);
			f.put("CONDUIT_NM", c[36]);
			f.put("SRC_LNG", c[37]);
			f.put("SRC_LAT", c[38]);
			f.put("DST_LNG", c[39]);
			f.put("DST_LAT", c[40]);
			f.put("CABLE_MODE", c[41]);
			f.put("FBR_NAME", c[42]);
			f.put("SRC_CITY", c[43]);
			f.put("DST_CITY", c[44]);
			f.put("PROJ_ID", c[45]);
			f.put("FBR_TYPE", c[46]);
			f.put("FBR_DEPLOY", c[47]);
			f.put("FBR_NETLVL", c[48]);
			f.put("FBR_OWNER", c[49]);
			f.put("CR_DATE", c[50]);
			f.put("MOD_DATE", c[51]);
			f.put("CREATED_BY", c[52]);
			f.put("MOD_BY", c[53]);
			f.put("TOT_DRV_DS", parseCoord(c[54]));
			f.put("DRAW_TYPE", c[55]);
			f.put("TOT_GEO_DS", parseCoord(c[56]));
			f.put("CABLE_ROLE", c[57]);
			features.add(f);
		}

		ShapefileWriter.writeLines(outputDir, "FiberCable",
				"FBR_ID:String,SRC_WAREID:String,LAST_AUX_D:Double,LAST_AUXDD:Double,SRC_ID:String,"
						+ "R_STRD_NUM:String,R_STRD_CLR:String,R_STRD_ID:String,R_STRD_NM:String,INSTALLER:String,"
						+ "ENGINEER:String,CABLE_SIZE:String,R_TUBE_NUM:String,R_TUBE_CLR:String,R_TUBE_ID:String,"
						+ "R_TUBE_NM:String,R_CABLEID:String,R_CABLENM:String,OS_LM_ID:String,OS_LM_NM:String,"
						+ "OS_LOC_ID:String,OS_LOC_NM:String,OS_LOC_CTY:String,OS_LOC_TYP:String,JCT_ID:String,"
						+ "JCT_NAME:String,JCT_PORT:String,SRC_NAME:String,DST_WAREID:String,DST_ID:String,"
						+ "DST_NAME:String,ITEM_CODE:String,N_STRANDS:Double,N_TUBES:Double,LENGTH:Double,"
						+ "CONDUIT_ID:String,CONDUIT_NM:String,SRC_LNG:String,SRC_LAT:String,DST_LNG:String,"
						+ "DST_LAT:String,CABLE_MODE:String,FBR_NAME:String,SRC_CITY:String,DST_CITY:String,"
						+ "PROJ_ID:String,FBR_TYPE:String,FBR_DEPLOY:String,FBR_NETLVL:String,FBR_OWNER:String,"
						+ "CR_DATE:java.util.Date,MOD_DATE:java.util.Date,CREATED_BY:String,MOD_BY:String,"
						+ "TOT_DRV_DS:Double,DRAW_TYPE:String,TOT_GEO_DS:Double,CABLE_ROLE:String",
				features);
	}

	@SuppressWarnings("unchecked")
	private void writeDucts(Session session, String projectId, File outputDir) throws Exception {
		// DUCTS has no PROJECT_ID directly — joins through TRENCH to filter by project.
		// Mapping: SRC_WAREID=SOURCE_WARE_ID, DST_WAREID=DESTINATION_WARE_ID,
		// TOT_DRV_DS=TOTAL_DRIVING_DISTANCE,
		// TOT_GEO_DS=TOTAL_GEO_DISTANCE, LAST_AUX_D/LAST_AUXDD=the two
		// LAST_AUXILIARY... columns,
		// N_FBRCBL=NUM_FIBERCABLES, N_FBRTUBE=NUM_FIBERTUBES,
		// N_FBRSTRD=NUM_FIBERSTRANDS,
		// MOD_BY=LAST_MODIFIED_BY, SUBD_CNT=SUB_DUCT_COUNT,
		// SUBD_DIAM=SUB_DUCT_DIAMETER_MM
		List<Object[]> ducts = session.createNativeQuery(
				"SELECT D.DUCT_ID, D.DUCT_NAME, D.SOURCE_WARE_ID, D.SOURCE_ID, D.SOURCE_NAME, D.OWNER, "
						+ "D.DUCT_INSTALLER, D.DUCT_ENGINEER_NAME, D.DESTINATION_WARE_ID, D.DESTINATION_ID, "
						+ "D.DESTINATION_NAME, D.SOURCE_LONGITUDE, D.SOURCE_LATITUDE, D.TOTAL_DRIVING_DISTANCE, "
						+ "D.TOTAL_GEO_DISTANCE, D.LAST_AUXILIARY_TO_DESTINATION_DISTANCE, "
						+ "D.LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE, D.DESTINATION_LONGITUDE, "
						+ "D.DESTINATION_LATITUDE, D.DRAWING_TYPE, D.SOURCE_CITY, D.DESTINATION_CITY, D.TRENCH_ID, "
						+ "D.NUM_FIBERCABLES, D.NUM_FIBERTUBES, D.NUM_FIBERSTRANDS, D.LENGTH, D.CREATION_DATE, "
						+ "D.LAST_MODIFIED_DATE, D.CREATED_BY, D.LAST_MODIFIED_BY, D.SUB_DUCT_COUNT, D.SUB_DUCT_DIAMETER_MM "
						+ "FROM DUCTS D JOIN TRENCH T ON D.TRENCH_ID = T.TRENCH_ID WHERE T.PROJECT_ID = :pid")
				.setParameter("pid", projectId).list();

		List<Map<String, Object>> features = new ArrayList<>();

		for (Object[] d : ducts) {
			String ductId = (String) d[0];
			Double srcLon = parseCoord(d[11]);
			Double srcLat = parseCoord(d[12]);
			Double dstLon = parseCoord(d[17]);
			Double dstLat = parseCoord(d[18]);
			if (srcLon == null || srcLat == null || dstLon == null || dstLat == null)
				continue;

			List<double[]> points = new ArrayList<>();
			points.add(new double[] { srcLon, srcLat });

			List<Object[]> auxPoints = session
					.createNativeQuery("SELECT LONGITUDE, LATITUDE FROM DUCT_AUXILIARY_POINTS "
							+ "WHERE DUCT_ID = :did ORDER BY SEQ_SORTING ASC")
					.setParameter("did", ductId).list();
			for (Object[] a : auxPoints) {
				Double aLon = parseCoord(a[0]);
				Double aLat = parseCoord(a[1]);
				if (aLon != null && aLat != null)
					points.add(new double[] { aLon, aLat });
			}
			points.add(new double[] { dstLon, dstLat });

			Map<String, Object> f = new LinkedHashMap<>();
			f.put("points", points);
			f.put("DUCT_ID", d[0]);
			f.put("DUCT_NAME", d[1]);
			f.put("SRC_WAREID", d[2]);
			f.put("SRC_ID", d[3]);
			f.put("SRC_NAME", d[4]);
			f.put("OWNER", d[5]);
			f.put("INSTALLER", d[6]);
			f.put("ENGINEER", d[7]);
			f.put("DST_WAREID", d[8]);
			f.put("DST_ID", d[9]);
			f.put("DST_NAME", d[10]);
			f.put("SRC_LNG", d[11]);
			f.put("SRC_LAT", d[12]);
			f.put("TOT_DRV_DS", parseCoord(d[13]));
			f.put("TOT_GEO_DS", parseCoord(d[14]));
			f.put("LAST_AUX_D", parseCoord(d[15]));
			f.put("LAST_AUXDD", parseCoord(d[16]));
			f.put("DST_LNG", d[17]);
			f.put("DST_LAT", d[18]);
			f.put("DRAW_TYPE", d[19]);
			f.put("SRC_CITY", d[20]);
			f.put("DST_CITY", d[21]);
			f.put("TRENCH_ID", d[22]);
			f.put("N_FBRCBL", parseCoord(d[23]));
			f.put("N_FBRTUBE", parseCoord(d[24]));
			f.put("N_FBRSTRD", parseCoord(d[25]));
			f.put("LENGTH", parseCoord(d[26]));
			f.put("CR_DATE", d[27]);
			f.put("MOD_DATE", d[28]);
			f.put("CREATED_BY", d[29]);
			f.put("MOD_BY", d[30]);
			f.put("SUBD_CNT", parseCoord(d[31]));
			f.put("SUBD_DIAM", parseCoord(d[32]));
			features.add(f);
		}

		ShapefileWriter.writeLines(outputDir, "Duct",
				"DUCT_ID:String,DUCT_NAME:String,SRC_WAREID:String,SRC_ID:String,SRC_NAME:String,OWNER:String,"
						+ "INSTALLER:String,ENGINEER:String,DST_WAREID:String,DST_ID:String,DST_NAME:String,"
						+ "SRC_LNG:String,SRC_LAT:String,TOT_DRV_DS:Double,TOT_GEO_DS:Double,LAST_AUX_D:Double,"
						+ "LAST_AUXDD:Double,DST_LNG:String,DST_LAT:String,DRAW_TYPE:String,SRC_CITY:String,"
						+ "DST_CITY:String,TRENCH_ID:String,N_FBRCBL:Double,N_FBRTUBE:Double,N_FBRSTRD:Double,"
						+ "LENGTH:Double,CR_DATE:java.util.Date,MOD_DATE:java.util.Date,CREATED_BY:String,MOD_BY:String,"
						+ "SUBD_CNT:Double,SUBD_DIAM:Double",
				features);
	}

	@SuppressWarnings("unchecked")
	private void writeTrenches(Session session, String projectId, File outputDir) throws Exception {
		List<Object[]> trenches = session.createNativeQuery(
				"SELECT TRENCH_ID, TRENCH_NAME, SOURCE_WARE_ID, SOURCE_ID, SOURCE_NAME, DESTINATION_WARE_ID, "
						+ "OWNER, TRENCH_INSTALLER, TRENCH_ENGINEER_NAME, DESTINATION_ID, DESTINATION_NAME, "
						+ "SOURCE_LATITUDE, SOURCE_LONGITUDE, DESTINATION_LONGITUDE, TOTAL_DRIVING_DISTANCE, "
						+ "TOTAL_GEO_DISTANCE, LAST_AUXILIARY_TO_DESTINATION_DISTANCE, "
						+ "LAST_AUXILIARY_TO_DESTINATION_DRIVING_DISTANCE, DRAWING_TYPE, DESTINATION_LATITUDE, "
						+ "SOURCE_CITY, DESTINATION_CITY, NUM_DUCTS, MAX_CAPACITY, LENGTH, PROJECT_ID, CREATION_DATE, "
						+ "LAST_MODIFIED_DATE, CREATED_BY, LAST_MODIFIED_BY " + "FROM TRENCH WHERE PROJECT_ID = :pid")
				.setParameter("pid", projectId).list();

		List<Map<String, Object>> features = new ArrayList<>();

		for (Object[] t : trenches) {
			String trenchId = (String) t[0];
			Double srcLat = parseCoord(t[11]);
			Double srcLon = parseCoord(t[12]);
			Double dstLon = parseCoord(t[13]);
			Double dstLat = parseCoord(t[19]);
			if (srcLon == null || srcLat == null || dstLon == null || dstLat == null)
				continue;

			List<double[]> points = new ArrayList<>();
			points.add(new double[] { srcLon, srcLat });

			List<Object[]> auxPoints = session
					.createNativeQuery("SELECT LONGITUDE, LATITUDE FROM TRENCH_AUXILIARY_POINTS "
							+ "WHERE TRENCH_ID = :tid ORDER BY SEQ_SORTING ASC")
					.setParameter("tid", trenchId).list();
			for (Object[] a : auxPoints) {
				Double aLon = parseCoord(a[0]);
				Double aLat = parseCoord(a[1]);
				if (aLon != null && aLat != null)
					points.add(new double[] { aLon, aLat });
			}
			points.add(new double[] { dstLon, dstLat });

			Map<String, Object> f = new LinkedHashMap<>();
			f.put("points", points);
			f.put("TR_ID", t[0]);
			f.put("TR_NAME", t[1]);
			f.put("SRC_WAREID", t[2]);
			f.put("SRC_ID", t[3]);
			f.put("SRC_NAME", t[4]);
			f.put("DST_WAREID", t[5]);
			f.put("OWNER", t[6]);
			f.put("INSTALLER", t[7]);
			f.put("ENGINEER", t[8]);
			f.put("DST_ID", t[9]);
			f.put("DST_NAME", t[10]);
			f.put("SRC_LAT", srcLat);
			f.put("SRC_LNG", srcLon);
			f.put("DST_LNG", dstLon);
			f.put("TOT_DRV_DS", parseCoord(t[14]));
			f.put("TOT_GEO_DS", parseCoord(t[15]));
			f.put("LAST_AUX_D", parseCoord(t[16]));
			f.put("LAST_AUXDD", parseCoord(t[17]));
			f.put("DRAW_TYPE", t[18]);
			f.put("DST_LAT", dstLat);
			f.put("SRC_CITY", t[20]);
			f.put("DST_CITY", t[21]);
			f.put("N_DUCTS", parseCoord(t[22]));
			f.put("MAX_CAP", parseCoord(t[23]));
			f.put("LENGTH", parseCoord(t[24]));
			f.put("PROJ_ID", t[25]);
			f.put("CR_DATE", t[26]);
			f.put("MOD_DATE", t[27]);
			f.put("CREATED_BY", t[28]);
			f.put("MOD_BY", t[29]);
			features.add(f);
		}

		ShapefileWriter.writeLines(outputDir, "Trench",
				"TR_ID:String,TR_NAME:String,SRC_WAREID:String,SRC_ID:String,SRC_NAME:String,DST_WAREID:String,"
						+ "OWNER:String,INSTALLER:String,ENGINEER:String,DST_ID:String,DST_NAME:String,SRC_LAT:Double,"
						+ "SRC_LNG:Double,DST_LNG:Double,TOT_DRV_DS:Double,TOT_GEO_DS:Double,LAST_AUX_D:Double,"
						+ "LAST_AUXDD:Double,DRAW_TYPE:String,DST_LAT:Double,SRC_CITY:String,DST_CITY:String,"
						+ "N_DUCTS:Double,MAX_CAP:Double,LENGTH:Double,PROJ_ID:String,CR_DATE:java.util.Date,"
						+ "MOD_DATE:java.util.Date,CREATED_BY:String,MOD_BY:String",
				features);
	}

	// Generic numeric parser — handles both BigDecimal/Number (from NUMBER columns)
	// and String (from VARCHAR2 columns storing numeric text). Used for coordinates
	// AND for every other numeric attribute below, despite the coordinate-sounding
	// name.
	private Double parseCoord(Object raw) {
		if (raw == null)
			return null;
		try {
			if (raw instanceof Number)
				return ((Number) raw).doubleValue();
			String s = raw.toString().trim();
			if (s.isEmpty())
				return null;
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}