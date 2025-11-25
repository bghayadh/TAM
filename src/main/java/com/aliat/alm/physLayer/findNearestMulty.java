package com.aliat.alm.physLayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Properties;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
//import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.AccessCableJunction;
import com.aliat.alm.models.AttachmentUpload;
import com.aliat.alm.models.DistributionBoard;
import com.aliat.alm.models.DistributionBoardMapping;
import com.aliat.alm.models.DistributionBoardSurvey;
import com.aliat.alm.models.Duct;
import com.aliat.alm.models.DuctAuxPoints;
import com.aliat.alm.models.FiberAuxPoints;
import com.aliat.alm.models.FiberCable;
import com.aliat.alm.models.FiberCableSurvey;
import com.aliat.alm.models.FiberStrands;
import com.aliat.alm.models.FiberStrandsSurvey;
import com.aliat.alm.models.FiberTubes;
import com.aliat.alm.models.FiberTubesSurvey;
import com.aliat.alm.models.HandholeSurvey;
import com.aliat.alm.models.ManholeSurvey;
import com.aliat.alm.models.NodeActive;
import com.aliat.alm.models.NodeSurvey;
import com.aliat.alm.models.PhysicalLayerActivity;
import com.aliat.alm.models.StrandAuxPoints;
import com.aliat.alm.models.Survey;
import com.aliat.alm.models.Trench;
import com.aliat.alm.models.TrenchAuxPoints;
import com.aliat.alm.models.TubeAuxPoints;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

@Controller
public class findNearestMulty {

	private static final Logger logger = Logger.getLogger(findNearestMulty.class.getName());

	private Transaction tx = null;
	private ObjectMapper mapper = new ObjectMapper();
	private Query query = null;
	private StringWriter sw;
	private String exceptionAsString;

//	private EntityManagerFactory emf =null;

//	private EntityManager entityManager=null;

	/*
	 * @Autowired ALMSessions almsessions;
	 */

	@Autowired
	Notify notifications;
	@Autowired
	Permissions permissions;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/findNearestMulty", method = RequestMethod.GET)

	public String NetworkPhysicalLayer(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			String originalUrl = request.getRequestURL().toString();
			String queryString = request.getQueryString();
			if (queryString != null) {
				originalUrl += "?" + queryString;
			}
			model.addAttribute("redirectUrl", originalUrl);
			return "Login";
		} else {

			
			Session findnearest = AlmDbSession.getInstance().getSession();
			Transaction txfind = null;
			if (findnearest != null && findnearest.isOpen()) {
				txfind = findnearest.beginTransaction();
				notifications.headerNotifications(findnearest, model);

				try {

					PhysicalLayerActivity PhyAct = new PhysicalLayerActivity();
					String ipAddress = getIpAddress(request);
					String updateModfUser = request.getParameter("updateModfUser");
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(new Date());
					int year = calendar.get(Calendar.YEAR);
					

					
					int filterFlag = 2;
					List<?> projectList = new ArrayList<Object[]>();
					List<Object[]> manholeList = new ArrayList<Object[]>();
					List<Object[]> handholeList = new ArrayList<Object[]>();
					List<Object[]> fiberList = new ArrayList<Object[]>();
					List<Object[]> fiberAuxiliary_Data = new ArrayList<Object[]>();
					List<Object[]> fiberTubes = new ArrayList<Object[]>();
					List<Object[]> tubesAuxiliaries = new ArrayList<Object[]>();
					List<Object[]> fiberStrands = new ArrayList<Object[]>();
					List<Object[]> strandsAuxiliaries = new ArrayList<Object[]>();
					List<Object[]> trenchList = new ArrayList<Object[]>();
					List<Object[]> trenchListPt = new ArrayList<Object[]>();
					List<Object[]> trenchAuxiliary_Data = new ArrayList<Object[]>();
					List<Object[]> trenchAuxiliary_DataPt = new ArrayList<Object[]>();
					List<Object[]> junctionManholeList = new ArrayList<Object[]>();
					List<Object[]> junctionHandholeList = new ArrayList<Object[]>();
					List<Object[]> distribBoardList = new ArrayList<Object[]>();
					List<Object[]> controllerList = new ArrayList<Object[]>();
					List<Object[]> ductList = new ArrayList<Object[]>();
					List<Object[]> ductListPt = new ArrayList<Object[]>();
					List<Object[]> ductAuxiliary_Data = new ArrayList<Object[]>();
					List<Object[]> ductAuxiliary_DataPt = new ArrayList<Object[]>();
					List<Object[]> NodeList = new ArrayList<Object[]>();
				
					// System.out.println("url is "+request.getParameter("selectedField"));
					String checkedOption = request.getParameter("Checked");
					System.out.println("url is " + request.getParameter("Checked"));
					
							String[] seqs = request.getParameterValues("seq");
							String[] siteIds = request.getParameterValues("name");
							String[] lngs = request.getParameterValues("lng");
							String[] lats = request.getParameterValues("lat");
							String[] locationNum = request.getParameterValues("locationNum");
							String[] circleDraw = request.getParameterValues("circleDraw");
							String[] squareDraw = request.getParameterValues("squareDraw");

							String noOfPoints = request.getParameter("nop");
							String closestDisRange = request.getParameter("closestDisRange");
							String getRelatedPoints = "0";

							// List initialization
							List<Double> borderCircleLatitudes = new ArrayList<>();
							List<Double> borderCircleLongitudes = new ArrayList<>();

							// Calculate border latitudes and longitudes
							for (int i = 0; i < lngs.length; i++) {
								double lat = Double.parseDouble(lats[i]);
								double lng = Double.parseDouble(lngs[i]);
								double disRange = Double.parseDouble(closestDisRange);

								double[] currentBorderCircleLatitudes = calculateBorderCircleLatitudes(lat, lng,
										disRange);
								double[] currentBorderCircleLongitudes = calculateBorderCircleLongitudes(lat, lng,
										disRange);

								for (double currentLat : currentBorderCircleLatitudes) {
									borderCircleLatitudes.add(currentLat);
								}
								for (double currentLng : currentBorderCircleLongitudes) {
									borderCircleLongitudes.add(currentLng);
								}
							}

							LinkedHashMap<String, List<?>> rowData = new LinkedHashMap<>();
							LinkedHashMap<String, LinkedHashMap<String, List<?>>> ptPhysicalLayerList = new LinkedHashMap<>();
							LinkedHashMap<String, List<?>> ptPhysicalListResult = new LinkedHashMap<>();
							LinkedHashMap<String, LinkedHashMap<String, List<?>>> ptPhysicalLayerData = new LinkedHashMap<>();
							LinkedHashMap<String, List<?>> ptPhysicalDataResult = new LinkedHashMap<>();
                     		try {
									for (int i = 0; i < seqs.length; i++) {
										String lng = lngs[i];
										String lat = lats[i];

										Map<String, Object> row = new HashMap<>();
										row.put("seq", seqs[i]);
										row.put("siteId", siteIds[i]);
										row.put("lat", lat);
										row.put("lng", lng);
										rowData.put("row" + i, new ArrayList<>(row.values()));

										
										System.out.println("Processing: " + seqs[i]);
										HashMap<String, List<Object[]>> Result = circleRangeData(noOfPoints,
												closestDisRange, lats[i], lngs[i], getRelatedPoints, findnearest,
												txfind);
										if (Result.get("fiberAuxiliary_Data") != null) {
											fiberAuxiliary_Data.addAll(Result.get("fiberAuxiliary_Data"));
										}
										
										if (Result.get("fiberList") != null) {
											for (Object item : Result.get("fiberList")) {
												Object[] newItem = (Object[]) item; 
												boolean found = false;

												for (Object existingItem : fiberList) {
													Object[] existingArray = (Object[]) existingItem; 
													if (existingArray[4].equals(newItem[4])) { 
														found = true;
														break;
													}
												}

												if (!found) {
												
													fiberList.add(newItem);
												}
											}
										}

										if (Result.get("fiberTubes") != null) {
											for (Object item : Result.get("fiberTubes")) {
												Object[] newItem = (Object[]) item; 
												boolean found = false;

												for (Object existingItem : fiberTubes) {
													Object[] existingArray = (Object[]) existingItem; 
													if (existingArray[0].equals(newItem[0])) { 
														found = true;
														break;
													}
												}

												if (!found) {
												
													
													fiberTubes.add(newItem);
												}
											}
										}

										if (Result.get("tubesAuxiliaries") != null) {
											tubesAuxiliaries.addAll(Result.get("tubesAuxiliaries"));
										}

										if (Result.get("fiberStrands") != null) {
											for (Object item : Result.get("fiberStrands")) {
												Object[] newItem = (Object[]) item; 
												boolean found = false;

												for (Object existingItem : fiberStrands) {
													Object[] existingArray = (Object[]) existingItem; 
													if (existingArray[0].equals(newItem[0])) { 
														found = true;
														break;
													}
												}

												if (!found) {
													
													fiberStrands.add(newItem);
												}
											}
										}

										if (Result.get("strandsAuxiliaries") != null) {
											strandsAuxiliaries.addAll(Result.get("strandsAuxiliaries"));
										}

										if (Result.get("manholeList") != null) {
											for (Object item : Result.get("manholeList")) {
												Object[] newItem = (Object[]) item; 
												boolean found = false;

												for (Object existingItem : manholeList) {
													Object[] existingArray = (Object[]) existingItem; 
													if (existingArray[0].equals(newItem[0])) { 
														found = true;
														break;
													}
												}

												if (!found) {
												
													manholeList.add(newItem);
												}
											}
										}

										if (Result.get("junctionManholeList") != null) {
											for (Object item : Result.get("junctionManholeList")) {
												Object[] newItem = (Object[]) item; 
												boolean found = false;

												for (Object existingItem : junctionManholeList) {
													Object[] existingArray = (Object[]) existingItem; 
													if (existingArray[0].equals(newItem[0])) { 
														found = true;
														break;
													}
												}

												if (!found) {
													
													junctionManholeList.add(newItem);
												}
											}
										}

										if (Result.get("handholeList") != null) {
											for (Object item : Result.get("handholeList")) {
												Object[] newItem = (Object[]) item; 

												boolean found = false;

												for (Object existingItem : handholeList) {
													Object[] existingArray = (Object[]) existingItem; 
													if (existingArray[0].equals(newItem[0])) { 
														found = true;
														break;
													}
												}

												if (!found) {
											
													handholeList.add(newItem);
												}
											}
										}

										if (Result.get("junctionHandholeList") != null) {
											for (Object item : Result.get("junctionHandholeList")) {
												Object[] newItem = (Object[]) item; 
												boolean found = false;

												for (Object existingItem : junctionHandholeList) {
													Object[] existingArray = (Object[]) existingItem; 
													if (existingArray[0].equals(newItem[0])) { 
														found = true;
														break;
													}
												}

												if (!found) {
													
													junctionHandholeList.add(newItem);
												}
											}
										}

										if (Result.get("distribBoardList") != null) {
											for (Object item : Result.get("distribBoardList")) {
												Object[] newItem = (Object[]) item; 
												boolean found = false;

												for (Object existingItem : distribBoardList) {
													Object[] existingArray = (Object[]) existingItem; 
													if (existingArray[0].equals(newItem[0])) { 
														found = true;
														break;
													}
												}

												if (!found) {
										
													distribBoardList.add(newItem);
												}
											}
										}
										if (Result.get("controllerList") != null) {
											for (Object item : Result.get("controllerList")) {
												Object[] newItem = (Object[]) item; 
												boolean found = false;

												for (Object existingItem : controllerList) {
													Object[] existingArray = (Object[]) existingItem; 
													if (existingArray[0].equals(newItem[0])) { 
														found = true;
														break;
													}
												}

												if (!found) {
										
													controllerList.add(newItem);
												}
											}
										}
										if (Result.get("NodeList") != null) {
											for (Object item : Result.get("NodeList")) {
												Object[] newItem = (Object[]) item; 
												boolean found = false;

												for (Object existingItem : NodeList) {
													Object[] existingArray = (Object[]) existingItem; 
													if (existingArray[0].equals(newItem[0])) { 
														found = true;
														break;
													}
												}

												if (!found) {
												
													NodeList.add(newItem);
												}
											}
										}

										// Prepare physical layer lists for output
										ptPhysicalListResult.put("Junction_Manhole",
												Result.get("junctionHandholeList"));
										ptPhysicalListResult.put("Manhole", Result.get("manholeList"));
										ptPhysicalListResult.put("Junction_Handhole",
												Result.get("junctionHandholeList"));
										ptPhysicalListResult.put("Handhole", Result.get("handholeList"));
										ptPhysicalListResult.put("fiber", Result.get("fiberList"));
										ptPhysicalListResult.put("Distribution_Board", Result.get("distribBoardList"));
										ptPhysicalListResult.put("controllerList", Result.get("controllerList"));
										System.out.println("zein");
										System.out.println(Result.get("controllerList"));
										ptPhysicalListResult.put("NodeList", Result.get("NodeList"));
										ptPhysicalDataResult.put("fiber_Strands", Result.get("fiberStrands"));
										ptPhysicalDataResult.put("fiber_Tubes", Result.get("fiberTubes"));
										ptPhysicalLayerList.put("ptList" + locationNum[i],
												new LinkedHashMap<>(ptPhysicalListResult));
										ptPhysicalLayerData.put("ptData" + locationNum[i],
												new LinkedHashMap<>(ptPhysicalDataResult));

										// Clear ptPhysicalListResult and ptPhysicalDataResult for the next iteration
										ptPhysicalListResult.clear();
										ptPhysicalDataResult.clear();

									}
									model.addAttribute("rowMultyIndex",request.getParameter("rowMultyIndex"));

									model.addAttribute("squareDraw", mapper.writeValueAsString(squareDraw));
									model.addAttribute("circleDraw", mapper.writeValueAsString(circleDraw));
									model.addAttribute("locationNumber", mapper.writeValueAsString(locationNum));
									String lastLocation = locationNum[locationNum.length - 1];

									// Extract the numeric part by removing "location_"
									String numberPart = lastLocation.substring("location_".length()); // This will give
																										// you the
																										// number as a
																										// string

									// Convert the string to a number and increment it
									int numericValue = Integer.parseInt(numberPart) + 1; // Increment the numeric value

									// Now you can send this incremented numericValue to your model
									model.addAttribute("LastlocationNumber", numericValue);
									String PhyActID = "PHY_ACT_" + year + "_" + Integer.parseInt(
											findnearest.createNativeQuery("SELECT PHY_ACT_ID FROM SEQ_TABLE").uniqueResult().toString());
									query = findnearest.createNativeQuery("UPDATE SEQ_TABLE SET PHY_ACT_ID = PHY_ACT_ID + 1 ");
									query.executeUpdate();
									findnearest.createNativeQuery("commit").executeUpdate();

									PhyAct.setPhyActID(PhyActID);
									PhyAct.setScreenName("Physical Layer");
									PhyAct.setUsername(updateModfUser);
									PhyAct.setUserIP(ipAddress);
									PhyAct.setActivityDate(new Timestamp(System.currentTimeMillis()));
									PhyAct.setActivityDescription("Physical Layer Access");
									findnearest.saveOrUpdate(PhyAct);

									permissions.setPerms(model, permissions.getUserPermsWithSession(findnearest, request), "Physical Layer",
											"Tree");
									String searchPopup = ((Integer) model.asMap().get("srchPopupTree")).toString();
									String findConnedted = ((Integer) model.asMap().get("findConnectedTree")).toString();
									String projects = ((Integer) model.asMap().get("projectsTree")).toString();
									model.addAttribute("searchPopup", searchPopup);
									model.addAttribute("findConnedted", findConnedted);
									model.addAttribute("projects", projects);

									permissions.setPerms(model, permissions.getUserPermsWithSession(findnearest, request),
											"Physical Layer Manhole", "Tree");

									String readManhole = ((Integer) model.asMap().get("readTree")).toString();
									String writeManhole = ((Integer) model.asMap().get("writeTree")).toString();
									String addManhole = ((Integer) model.asMap().get("addTree")).toString();
									String delManhole = ((Integer) model.asMap().get("delTree")).toString();
									String saveManhole = ((Integer) model.asMap().get("saveTree")).toString();
									model.addAttribute("readManhole", readManhole);
									model.addAttribute("writeManhole", writeManhole);
									model.addAttribute("addManhole", addManhole);
									model.addAttribute("saveManhole", saveManhole);
									model.addAttribute("delManhole", delManhole);

									permissions.checkAndAddExceptions(model, readManhole, writeManhole, findnearest,
											"Physical Layer Manhole", request);

									String readExceptionMan = (String) model.asMap().get("readExceptionMan");
									String writeExceptionMan = (String) model.asMap().get("writeExceptionMan");

									permissions.setPerms(model, permissions.getUserPermsWithSession(findnearest, request),
											"Physical Layer Handhole", "Tree");

									String readHandhole = ((Integer) model.asMap().get("readTree")).toString();
									String writeHandhole = ((Integer) model.asMap().get("writeTree")).toString();
									String addHandhole = ((Integer) model.asMap().get("addTree")).toString();
									String delHandhole = ((Integer) model.asMap().get("delTree")).toString();
									String saveHandhole = ((Integer) model.asMap().get("saveTree")).toString();
									model.addAttribute("readHandhole", readHandhole);
									model.addAttribute("writeHandhole", writeHandhole);
									model.addAttribute("addHandhole", addHandhole);
									model.addAttribute("saveHandhole", saveHandhole);
									model.addAttribute("delHandhole", delHandhole);

									permissions.checkAndAddExceptions(model, readHandhole, writeHandhole, findnearest,
											"Physical Layer Handhole", request);

									String readExceptionHand = (String) model.asMap().get("readExceptionHand");
									String writeExceptionHand = (String) model.asMap().get("writeExceptionHand");

									permissions.setPerms(model, permissions.getUserPermsWithSession(findnearest, request),
											"Physical Layer Fiber", "Tree");

									String readFiber = ((Integer) model.asMap().get("readTree")).toString();
									String writeFiber = ((Integer) model.asMap().get("writeTree")).toString();
									String addFiber = ((Integer) model.asMap().get("addTree")).toString();
									String delFiber = ((Integer) model.asMap().get("delTree")).toString();
									String saveFiber = ((Integer) model.asMap().get("saveTree")).toString();
									model.addAttribute("readFiber", readFiber);
									model.addAttribute("writeFiber", writeFiber);
									model.addAttribute("addFiber", addFiber);
									model.addAttribute("delFiber", delFiber);
									model.addAttribute("saveFiber", saveFiber);

									permissions.checkAndAddExceptions(model, readFiber, writeFiber, findnearest, "Physical Layer Fiber",
											request);

									permissions.setPerms(model, permissions.getUserPermsWithSession(findnearest, request),
											"Physical Layer DB", "Tree");

									String readDB = ((Integer) model.asMap().get("readTree")).toString();
									String writeDB = ((Integer) model.asMap().get("writeTree")).toString();
									String addDB = ((Integer) model.asMap().get("addTree")).toString();
									String delDB = ((Integer) model.asMap().get("delTree")).toString();
									String saveDB = ((Integer) model.asMap().get("saveTree")).toString();
									model.addAttribute("readDB", readDB);
									model.addAttribute("writeDB", writeDB);
									model.addAttribute("addDB", addDB);
									model.addAttribute("delDB", delDB);
									model.addAttribute("saveDB", saveDB);

									permissions.checkAndAddExceptions(model, readDB, writeDB, findnearest, "Physical Layer DB", request);

									String readExceptionDB = (String) model.asMap().get("readExceptionDB");
									String writeExceptionDB = (String) model.asMap().get("writeExceptionDB");

								} catch (Exception e) {
									if (txfind != null) {
										txfind.rollback();
									}
									e.printStackTrace();
								} finally {
									if (findnearest != null && findnearest.isOpen()) {
										findnearest.clear();
										findnearest.close();
									}
								}
								findnearest = AlmDbSession.getInstance().getSession();
								if (findnearest != null && findnearest.isOpen()) {
									tx = findnearest.beginTransaction();
								}
								// Final model attributes
								model.addAttribute("rowData", mapper.writeValueAsString(rowData));
								model.addAttribute("getRelatedPoints", getRelatedPoints);
								model.addAttribute("noOfPoints", noOfPoints);
								model.addAttribute("closestDisRange", closestDisRange);
								model.addAttribute("borderCircleLatitudes",
										mapper.writeValueAsString(borderCircleLatitudes));
								model.addAttribute("borderCircleLongitudes",
										mapper.writeValueAsString(borderCircleLongitudes));
								model.addAttribute("ptList", mapper.writeValueAsString(ptPhysicalLayerList));
								model.addAttribute("ptData", mapper.writeValueAsString(ptPhysicalLayerData));

								// Reopen session for further operations

								// Add further logic if needed
					
					

					// HashMap<String,List<?>> hash_map = new HashMap<String, List<?>>();
						 LinkedHashMap<String, List<?>> physicalLayerData = new LinkedHashMap<String, List<?>>();

							/* linkedHashmap instead of HashMap to return values */
							LinkedHashMap<String, List<?>> physicalLayerList = new LinkedHashMap<String, List<?>>();
							
							removeDuplicatesByIndex(fiberAuxiliary_Data, 7);
					        removeDuplicatesByIndex(tubesAuxiliaries, 8);
					        removeDuplicatesByIndex(strandsAuxiliaries, 8);	
                  	physicalLayerData.clear();
					physicalLayerList.clear();
					physicalLayerList.put("Project", projectList);
					physicalLayerList.put("Junction_Manhole", junctionManholeList);
					physicalLayerList.put("Manhole", manholeList);
					physicalLayerList.put("Junction_Handhole", junctionHandholeList);
					physicalLayerList.put("Handhole", handholeList);
					physicalLayerList.put("fiber", fiberList);
					physicalLayerList.put("Distribution_Board", distribBoardList);
					physicalLayerList.put("controllerList", controllerList);
					
					
					physicalLayerList.put("Trench", trenchList);
					physicalLayerList.put("Node", NodeList);
					physicalLayerList.put("duct", ductList);
					physicalLayerData.put("trench_Auxiliary", trenchAuxiliary_Data);
					physicalLayerData.put("strands_Auxiliaries", strandsAuxiliaries);
					physicalLayerData.put("fiber_Strands", fiberStrands);
					physicalLayerData.put("tubes_Auxiliaries", tubesAuxiliaries);
					physicalLayerData.put("fiber_Tubes", fiberTubes);
					physicalLayerData.put("fiber_Auxiliary", fiberAuxiliary_Data);
					physicalLayerData.put("ductAuxiliary", ductAuxiliary_Data);

					model.addAttribute("physicalLayerList", mapper.writeValueAsString(physicalLayerList));
					model.addAttribute("physicalLayerData", mapper.writeValueAsString(physicalLayerData));
				//	System.out.println(mapper.writeValueAsString(physicalLayerList));
					model.addAttribute("filterFlag", filterFlag);
					model.addAttribute("checkedOption", checkedOption);
					findnearest.flush();
					findnearest.clear();
					tx.commit();


				} catch (Exception e) {
					tx.rollback();
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in NetworkPhysicalLayer due to \n " + exceptionAsString);
					logger.info("Error in NetworkPhysicalLayer due to \n " + exceptionAsString);
					e.printStackTrace();
				} finally {
					if (findnearest != null && findnearest.isOpen()) {
						findnearest.close();
					}
				}
			}
			System.out.println("Loading NetworkPhysicalLayer Controller Finished");
			return "Network/NetworkPhysicalLayer";
		}
	}
	private String getIpAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-Forwarded-For");
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_X_FORWARDED");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_FORWARDED_FOR");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_FORWARDED");
		}
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}

		// Handle IPv6 local address
		if (ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.equals("::1")) {
			ipAddress = "127.0.0.1"; // This is the IPv4 loopback address
		}

		// In some cases, IP addresses may come in IPv6 format, so you might need to
		// convert it to IPv4
		if (ipAddress != null && ipAddress.contains(":")) {
			ipAddress = ipAddress.split(":")[0];
		}

		return ipAddress;
	}
	private static double[] calculateBorderCircleLatitudes(double centerLat, double centerLon, double distanceRange) {

		// Calculate circle radius
		// double circleRadius = distanceRange * 1.609344 * 1.609344;
		double circleRadius = distanceRange;

		final double EARTH_RADIUS = 6371.0;

		// Convert latitude to radians
		double centerLatRad = Math.toRadians(centerLat);

		// Calculate delta latitude (angular distance)
		double deltaLat = circleRadius / EARTH_RADIUS;

		// Calculate latitudes of points on the circle's border
		double northLatitude = Math.toDegrees(centerLatRad + deltaLat);
		double southLatitude = Math.toDegrees(centerLatRad - deltaLat);

		// Return lat in descending order
		return new double[] { Math.min(northLatitude, southLatitude), Math.max(northLatitude, southLatitude) };

	}

	private static double[] calculateBorderCircleLongitudes(double centerLat, double centerLon, double distanceRange) {
		// Calculate circle radius
		// double circleRadius = distanceRange * 1.609344 * 1.609344;
		double circleRadius = distanceRange;

		final double EARTH_RADIUS = 6371.0;

		// Convert latitude to radians
		double centerLatRad = Math.toRadians(centerLat);

		// Calculate angular distance (in radians) covered by the circle's border
		double angularDistance = circleRadius / EARTH_RADIUS;

		// Calculate longitude difference based on the latitude
		double deltaLon = Math.asin(Math.sin(angularDistance) / Math.cos(centerLatRad));

		// Convert longitude to radians
		double centerLonRad = Math.toRadians(centerLon);

		// Calculate longitudes of points on the circle's border
		double westLongitude = Math.toDegrees(centerLonRad - deltaLon);
		double eastLongitude = Math.toDegrees(centerLonRad + deltaLon);

		// Return longitudes in descending order
		return new double[] { Math.min(westLongitude, eastLongitude), Math.max(westLongitude, eastLongitude) };
	}
	@SuppressWarnings("unchecked")
	public HashMap<String, List<Object[]>> circleRangeData(String noOfPoints, String closestDisRange,
			String closestLatPoint, String closestLongPoint, String getRelatedPoints, Session findnearest,
			Transaction txfind) throws JsonProcessingException {
		HashMap<String, List<Object[]>> resultMap = new HashMap<>();

		List<Object[]> manholeList = new ArrayList<Object[]>();
		List<Object[]> handholeList = new ArrayList<Object[]>();
		List<Object[]> fiberList = new ArrayList<Object[]>();
		List<Object[]> fiberAuxiliary_Data = new ArrayList<Object[]>();
		List<Object[]> fiberTubes = new ArrayList<Object[]>();
		List<Object[]> tubesAuxiliaries = new ArrayList<Object[]>();
		List<Object[]> fiberStrands = new ArrayList<Object[]>();
		List<Object[]> strandsAuxiliaries = new ArrayList<Object[]>();
		List<Object[]> junctionManholeList = new ArrayList<Object[]>();
		List<Object[]> junctionHandholeList = new ArrayList<Object[]>();
		List<Object[]> distribBoardList = new ArrayList<Object[]>();
		List<Object[]> controllerList = new ArrayList<Object[]>();
		
		List<Object[]> newList = new ArrayList<Object[]>();
		List<Object[]> NodeList = new ArrayList<Object[]>();
		List<String> mhFilteredIDs = new ArrayList<>();
		List<String> hhFilteredIDs = new ArrayList<>();
		List<String> dbFilteredIDs = new ArrayList<>();
		List<String> combinedTubeList = new ArrayList<>();
		List<String> combinedCablesList = new ArrayList<>();
		List<Object[]> manholeTempList = new ArrayList<Object[]>();
		List<Object[]> handholeTempList = new ArrayList<Object[]>();
		List<Object[]> dbTempList = new ArrayList<Object[]>();
		List<Object[]> tempDataList = new ArrayList<Object[]>();

		double[] bordeCircleLatitudes = calculateBorderCircleLatitudes(Double.valueOf(closestLatPoint),
				Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange));
		double[] borderCircleLongitudes = calculateBorderCircleLongitudes(Double.valueOf(closestLatPoint),
				Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange));

		fiberList = findnearest.createNativeQuery(
				"SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR "
						+ "FROM FIBER_CABLES A LEFT JOIN FIBER_AUXILIARY_POINTS D ON A.FIBER_CABLE_ID=D.FIBER_CABLE_ID "
						+ "WHERE ( to_number(SUBSTR(A.SOURCE_LNG,1,6)) > " + borderCircleLongitudes[0]
						+ "and  to_number(SUBSTR(A.SOURCE_LAT,1,6)) >  " + bordeCircleLatitudes[0]
						+ " and to_number (SUBSTR(A.SOURCE_LNG,1,6)) < " + borderCircleLongitudes[1]
						+ " AND to_number (SUBSTR(A.SOURCE_LAT,1,6)) < " + bordeCircleLatitudes[1] + ") "
						+ "OR ( to_number(SUBSTR(A.DESTINATION_LNG,1,6)) > " + borderCircleLongitudes[0]
						+ "and  to_number(SUBSTR(A.DESTINATION_LAT,1,6)) >  " + bordeCircleLatitudes[0]
						+ " and to_number (SUBSTR(A.DESTINATION_LNG,1,6)) < " + borderCircleLongitudes[1]
						+ " AND to_number (SUBSTR(A.DESTINATION_LAT,1,6)) < " + bordeCircleLatitudes[1] + ") "
						+ "OR ( to_number(SUBSTR(D.LONGITUDE,1,6)) > " + borderCircleLongitudes[0]
						+ "and  to_number(SUBSTR(D.LATITUDE,1,6)) >  " + bordeCircleLatitudes[0]
						+ " and to_number (SUBSTR(D.LONGITUDE,1,6)) < " + borderCircleLongitudes[1]
						+ " AND to_number (SUBSTR(D.LATITUDE,1,6)) < " + bordeCircleLatitudes[1] + ") ")
				.getResultList();

		List<String> cablesIDs = Arrays
				.asList((findListId(fiberList, "FiberCable")).length > 0 ? findListId(fiberList, "FiberCable")
						: new String[] { "" });
		combinedCablesList.addAll(cablesIDs);// used in get related points
		findIDsSrcDest(fiberList, "Cables", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs, borderCircleLongitudes[0],
				bordeCircleLatitudes[0], borderCircleLongitudes[1], bordeCircleLatitudes[1]); // Add the cables src/dest
																								// points that are
																								// within the range &
																								// areMH,HH,DB to array

		// Split the array into sub-arrays( each of 1000 elements )
		if (fiberList.size() > 0) {
			int subArraySize = 1000;
			int listSize = cablesIDs.size();

			for (int i = 0; i < listSize; i += subArraySize) {
				int remaining = listSize - i;
				int currentListSize = Math.min(subArraySize, remaining);
				List<String> sublist = cablesIDs.subList(i, i + currentListSize);

				// Get all auxiliaries of each cable
				query = findnearest.createNativeQuery(
						"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A,FIBER_AUXILIARY_POINTS B WHERE A.FIBER_CABLE_ID=B.FIBER_CABLE_ID "
								+ " AND B.FIBER_CABLE_ID IN (:param) ORDER BY B.SEQ_SORTING ASC")
						.setParameter("param", sublist);
				fiberAuxiliary_Data.addAll(query.getResultList());
				
			}
		}

		
		fiberTubes = findnearest.createNativeQuery(
				"SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER,b.TUBE_COLOR FROM FIBER_TUBES b "
						+ "LEFT JOIN TUBE_AUXILIARY_POINTS a ON a.TUBE_ID=b.TUBE_ID LEFT JOIN FIBER_STRANDS E ON E.TUBE_ID=b.TUBE_ID "
						+ "WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) > " + borderCircleLongitudes[0]
						+ "and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >  " + bordeCircleLatitudes[0]
						+ " and to_number (SUBSTR(b.SOURCE_LONGITUDE,1,6)) < " + borderCircleLongitudes[1]
						+ " AND to_number (SUBSTR(b.SOURCE_LATITUDE,1,6)) < " + bordeCircleLatitudes[1] + ") "
						+ "OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > " + borderCircleLongitudes[0]
						+ "and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >  " + bordeCircleLatitudes[0]
						+ " and to_number (SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < " + borderCircleLongitudes[1]
						+ " AND to_number (SUBSTR(b.DESTINATION_LATITUDE,1,6)) < " + bordeCircleLatitudes[1] + ") "
						+ "OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > " + borderCircleLongitudes[0]
						+ "and  to_number(SUBSTR(a.LATITUDE,1,6)) >  " + bordeCircleLatitudes[0]
						+ " and to_number (SUBSTR(a.LONGITUDE,1,6)) < " + borderCircleLongitudes[1]
						+ " AND to_number (SUBSTR(a.LATITUDE,1,6)) < " + bordeCircleLatitudes[1] + ") ")
				.getResultList();

		List<String> tubesIDs = Arrays.asList(
				(findListId(fiberTubes, "Tube")).length > 0 ? findListId(fiberTubes, "Tube") : new String[] { "" });
		combinedTubeList.addAll(tubesIDs);// used in get related points
		findIDsSrcDest(fiberTubes, "Tubes", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs, borderCircleLongitudes[0],
				bordeCircleLatitudes[0], borderCircleLongitudes[1], bordeCircleLatitudes[1]);

		// Split the array into sub-arrays( each of 1000 elements )
		if (fiberTubes.size() > 0) {

			int subArraySize = 1000;
			int listSize = tubesIDs.size();

			for (int i = 0; i < listSize; i += subArraySize) {
				int remaining = listSize - i;
				int currentListSize = Math.min(subArraySize, remaining);
				List<String> sublist = tubesIDs.subList(i, i + currentListSize);

				// Get all auxiliaries of each tube
				query = findnearest.createNativeQuery(
						"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON  b.TUBE_ID=c.TUBE_ID "
								+ " WHERE c.TUBE_ID IN (:param) ORDER BY c.SEQ_SORTING ASC")
						.setParameter("param", sublist);
				tubesAuxiliaries.addAll(query.getResultList());
			}
		}

		
		fiberStrands = findnearest.createNativeQuery(
				"SELECT DISTINCT b.STRAND_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,b.TUBE_ID,b.FIBER_CABLE_ID,b.STRAND_NAME,b.DRAWING_TYPE,b.STRAND_NUMBER,b.STRAND_COLOR FROM FIBER_STRANDS b LEFT JOIN STRAND_AUXILIARY_POINTS a ON b.STRAND_ID=a.STRAND_ID "
						+ "WHERE ( to_number(SUBSTR(b.SOURCE_LONGITUDE,1,6)) > " + borderCircleLongitudes[0]
						+ "and  to_number(SUBSTR(b.SOURCE_LATITUDE,1,6)) >  " + bordeCircleLatitudes[0]
						+ " and to_number (SUBSTR(b.SOURCE_LONGITUDE,1,6)) < " + borderCircleLongitudes[1]
						+ " AND to_number (SUBSTR(b.SOURCE_LATITUDE,1,6)) < " + bordeCircleLatitudes[1] + " ) "
						+ "OR ( to_number(SUBSTR(b.DESTINATION_LONGITUDE,1,6)) > " + borderCircleLongitudes[0]
						+ "and  to_number(SUBSTR(b.DESTINATION_LATITUDE,1,6)) >  " + bordeCircleLatitudes[0]
						+ " and to_number (SUBSTR(b.DESTINATION_LONGITUDE,1,6)) < " + borderCircleLongitudes[1]
						+ " AND to_number (SUBSTR(b.DESTINATION_LATITUDE,1,6)) < " + bordeCircleLatitudes[1] + ") "
						+ "OR ( to_number(SUBSTR(a.LONGITUDE,1,6)) > " + borderCircleLongitudes[0]
						+ "and  to_number(SUBSTR(a.LATITUDE,1,6)) >  " + bordeCircleLatitudes[0]
						+ " and to_number (SUBSTR(a.LONGITUDE,1,6)) < " + borderCircleLongitudes[1]
						+ " AND to_number (SUBSTR(a.LATITUDE,1,6)) < " + bordeCircleLatitudes[1] + ") ")
				.getResultList();
		List<String> strandsIDs = Arrays
				.asList((findListId(fiberStrands, "Strand")).length > 0 ? findListId(fiberStrands, "Strand")
						: new String[] { "" });

		findIDsSrcDest(fiberStrands, "Strands", mhFilteredIDs, hhFilteredIDs, dbFilteredIDs, borderCircleLongitudes[0],
				bordeCircleLatitudes[0], borderCircleLongitudes[1], bordeCircleLatitudes[1]);// Add the strands src/dest
																								// points that are
																								// within the range &
																								// are MH,HH,DB to array

		// Split the array into sub-arrays( each of 1000 elements )
		if (fiberStrands.size() > 0) {
			int subArraySize = 1000;
			int listSize = strandsIDs.size();

			for (int i = 0; i < listSize; i += subArraySize) {
				int remaining = listSize - i;
				int currentListSize = Math.min(subArraySize, remaining);
				List<String> sublist = strandsIDs.subList(i, i + currentListSize);

				// Get all auxiliaries of each strand
				query = findnearest.createNativeQuery(
						"SELECT DISTINCT c.STRAND_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM STRAND_AUXILIARY_POINTS c,FIBER_STRANDS b WHERE b.STRAND_ID=c.STRAND_ID "
								+ " AND c.STRAND_ID IN (:param) ORDER BY c.SEQ_SORTING ASC ")
						.setParameter("param", sublist);
				strandsAuxiliaries.addAll(query.getResultList());

				// Check and Select if there is tubes that are outside the range but have
				// strands in range and not selected before
				query = findnearest.createNativeQuery(
						"SELECT DISTINCT b.TUBE_ID,b.SOURCE_LONGITUDE,b.SOURCE_LATITUDE,b.DESTINATION_LONGITUDE,b.DESTINATION_LATITUDE,b.SOURCE_WARE_ID,b.SOURCE_ID,b.SOURCE_NAME,b.DESTINATION_WARE_ID,b.DESTINATION_ID,b.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.TUBE_ID=b.TUBE_ID),b.FIBER_CABLE_ID,b.TUBE_NAME,b.DRAWING_TYPE,b.TUBE_NUMBER,b.TUBE_COLOR "
								+ "FROM FIBER_TUBES b LEFT JOIN FIBER_STRANDS E ON E.TUBE_ID=b.TUBE_ID "
								+ "WHERE E.STRAND_ID in (:param) ")
						.setParameter("param", strandsIDs);
				tempDataList.addAll(query.getResultList());

			}
		}
		// Check and Select if there is tubes that are outside the range but have
		// strands in range and not selected before
		if (tempDataList.size() > 0) {

			List<Object[]> tmprList = filterDataList(tempDataList, tubesIDs, "Tubes");

			List<String> outOfRangeTubeIDs = Arrays.asList(
					(findListId(tmprList, "Tube")).length > 0 ? findListId(tmprList, "Tube") : new String[] { "" });
			combinedTubeList.addAll(outOfRangeTubeIDs);// used in get related points

			if (fiberTubes.size() > 0) {
				fiberTubes.addAll(tmprList);
			} else {
				fiberTubes = tmprList;
			}

			// Split the array into sub-arrays( each of 1000 elements )
			if (tmprList.size() > 0) {
				int subArraySize = 1000;
				int listSize = outOfRangeTubeIDs.size();

				for (int i = 0; i < listSize; i += subArraySize) {
					int remaining = listSize - i;
					int currentListSize = Math.min(subArraySize, remaining);
					List<String> sublist = outOfRangeTubeIDs.subList(i, i + currentListSize);

					query = findnearest.createNativeQuery(
							"SELECT DISTINCT c.TUBE_ID,c.LONGITUDE,c.LATITUDE,c.WARE_ID,c.AUXILIARY_POINT_ID,c.AUXILIARY_POINT_NAME,c.DISTANCE_FROM_SOURCE,c.SEQ_SORTING,c.AUXILIARY_ID,c.DRIVING_DISTANCE, c.GEO_DISTANCE FROM TUBE_AUXILIARY_POINTS c LEFT JOIN FIBER_TUBES b ON  b.TUBE_ID=c.TUBE_ID  "
									+ " WHERE c.TUBE_ID IN (:param) ORDER BY c.SEQ_SORTING ASC")
							.setParameter("param", sublist);

					tubesAuxiliaries.addAll(query.getResultList());
				}
			}
		}

		tempDataList.clear();
		// Check and Select if there is cables that are outside the range but have tubes
		// in range and not selected before
		if (fiberTubes.size() > 0) {

			int subArraySize = 1000;
			int listSize = combinedTubeList.size();

			for (int i = 0; i < listSize; i += subArraySize) {
				int remaining = listSize - i;
				int currentListSize = Math.min(subArraySize, remaining);
				List<String> sublist = combinedTubeList.subList(i, i + currentListSize);

				query = findnearest.createNativeQuery(
						"SELECT distinct A.SOURCE_LNG,A.SOURCE_LAT,A.DESTINATION_LNG,A.DESTINATION_LAT, A.FIBER_CABLE_ID,A.SOURCE_WARE_ID,A.SOURCE_ID,A.SOURCE_NAME,A.DESTINATION_WARE_ID,A.DESTINATION_ID,A.DESTINATION_NAME,(SELECT COUNT(*) FROM FIBER_TUBES B WHERE B.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Tube_Count,(SELECT COUNT(*) FROM FIBER_STRANDS C WHERE C.FIBER_CABLE_ID=A.FIBER_CABLE_ID) AS Strand_Count,A.FIBER_CABLE_NAME,A.PROJECT_ID,A.SOURCE_CITY,A.DESTINATION_CITY,A.NUMBER_OF_TUBES,A.NUMBER_OF_STRANDS,A.LENGTH,A.DRAWING_TYPE,A.FIBER_NETWORK_LEVEL,A.FIBER_OWNER,(select B.FIBER_COLOR_OWNER from FIBER_OWNER_COLOR B WHERE B.FIBER_OWNER=A.FIBER_OWNER) AS FIBER_CABLE_COLOR "
								+ "FROM FIBER_CABLES A LEFT JOIN FIBER_TUBES E ON E.FIBER_CABLE_ID=A.FIBER_CABLE_ID "
								+ "WHERE E.TUBE_ID in (:param) ")
						.setParameter("param", sublist);

				tempDataList.addAll(query.getResultList());
			}

			List<Object[]> tmprList = filterDataList(tempDataList, cablesIDs, "Cables");

			List<String> outOfRangeCableIDs = Arrays
					.asList((findListId(tmprList, "FiberCable")).length > 0 ? findListId(tmprList, "FiberCable")
							: new String[] { "" });
			combinedCablesList.addAll(outOfRangeCableIDs);// used in get related points

			if (fiberList.size() > 0) {
				fiberList.addAll(tmprList);
			} else {
				fiberList = tmprList;
			}

			int subArrayLngth = 1000;
			int lstSize = outOfRangeCableIDs.size();

			for (int i = 0; i < lstSize; i += subArrayLngth) {
				int remaining = lstSize - i;
				int currentListSize = Math.min(subArrayLngth, remaining);
				List<String> sublist = outOfRangeCableIDs.subList(i, i + currentListSize);

				query = findnearest.createNativeQuery(
						"SELECT B.LONGITUDE,B.LATITUDE,B.DISTANCE_FROM_SOURCE,B.WARE_ID,B.AUXILIARY_POINT_ID,B.AUXILIARY_POINT_NAME,B.FIBER_CABLE_ID,B.AUXILIARY_ID FROM FIBER_CABLES A LEFT JOIN FIBER_AUXILIARY_POINTS B ON A.FIBER_CABLE_ID=B.FIBER_CABLE_ID "
								+ " WHERE B.FIBER_CABLE_ID IN (:param) ORDER BY B.SEQ_SORTING ASC")
						.setParameter("param", sublist);

				fiberAuxiliary_Data.addAll(query.getResultList());
			}
		}

		// Select all MH details that are within the range

		List<Object[]> manholeListQuery = findnearest.createNativeQuery(
				"SELECT DISTINCT MANHOLE_ID,MANHOLE_NAME,trim(replace(LONGITUDE,'�','')) as LONGITUDE, trim(replace(LATITUDE,'�','')) as LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=MANHOLE_ID),DM_NAME FROM MANHOLE"
						+ " WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > " + borderCircleLongitudes[0]
						+ "and  to_number(SUBSTR(LATITUDE,1,6)) >  " + bordeCircleLatitudes[0]
						+ " and to_number (SUBSTR(LONGITUDE,1,6)) < " + borderCircleLongitudes[1]
						+ " AND to_number (SUBSTR(LATITUDE,1,6)) < " + bordeCircleLatitudes[1] + ")")
				.getResultList();

		// Filter the MH that have distance < distanceRange or MH that exists in
		// mhFilteredIDs (mhFilteredIDs contains MH that are src/dst/aux of
		// cables,tubes,strands)
		manholeList = findLinearDistance(mhFilteredIDs, manholeListQuery, Double.valueOf(closestLatPoint),
				Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "Manhole", noOfPoints);

		// Select all HH details that are within the range

		List<Object[]> handholeListQuery = findnearest.createNativeQuery(
				"SELECT DISTINCT HANDHOLE_ID,HANDHOLE_NAME,trim(replace(LONGITUDE,'�','')) as LONGITUDE,trim(replace(LATITUDE,'�','')) as LATITUDE,PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION B WHERE B.PHYSICAL_LAYER_ID=HANDHOLE_ID),DM_NAME FROM HANDHOLE"
						+ " WHERE ( to_number(SUBSTR(LONGITUDE,1,6)) > " + borderCircleLongitudes[0]
						+ "and  to_number(SUBSTR(LATITUDE,1,6)) >  " + bordeCircleLatitudes[0]
						+ " and to_number (SUBSTR(LONGITUDE,1,6)) < " + borderCircleLongitudes[1]
						+ " AND to_number (SUBSTR(LATITUDE,1,6)) < " + bordeCircleLatitudes[1] + ")")
				.getResultList();

		// Filter the HH that have distance<distanceRange or HH that exists in
		// hhFilteredIDs (hhFilteredIDs contains HH that are src/dst/aux of
		// cables,tubes,strands)
		handholeList = findLinearDistance(hhFilteredIDs, handholeListQuery, Double.valueOf(closestLatPoint),
				Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "Handhole", noOfPoints);

		// Select all DB details that are within the range
		List<Object[]> distribBoardListQuery = findnearest.createNativeQuery(
				"SELECT DISTINCT DB_ID,trim(replace(DB_LONGITUDE,'�','')) as DB_LONGITUDE,trim(replace(DB_LATITUDE,'�','')) as DB_LATITUDE,DB_NAME,MAX_CAPACITY,SITE,PROJECT_ID ,CITY,DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD "
						+ " WHERE ( to_number(SUBSTR(DB_LONGITUDE,1,6)) > " + borderCircleLongitudes[0]
						+ "and  to_number(SUBSTR(DB_LATITUDE,1,6)) >  " + bordeCircleLatitudes[0]
						+ " and to_number (SUBSTR(DB_LONGITUDE,1,6)) < " + borderCircleLongitudes[1]
						+ " AND to_number (SUBSTR(DB_LATITUDE,1,6)) < " + bordeCircleLatitudes[1] + ")")
				.getResultList();

		
		// Filter the DB that have distance<distanceRange or DB that exists in
		// dbFilteredIDs (dbFilteredIDs contains HH that are src/dst/aux of
		// cables/tubes/strands)
		distribBoardList = findLinearDistance(dbFilteredIDs, distribBoardListQuery, Double.valueOf(closestLatPoint),
				Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "DistribBoard", noOfPoints);

// To select the data needed in show points/real points & are outside the range
		if (getRelatedPoints.equals("1")) {

			String[] manholesId = (findListId(manholeList, "all")).length > 0 ? findListId(manholeList, "all")
					: new String[] { "A" };
			String[] handholesId = (findListId(handholeList, "all")).length > 0 ? findListId(handholeList, "all")
					: new String[] { "A" };
			String[] dbsId = (findListId(distribBoardList, "all")).length > 0 ? findListId(distribBoardList, "all")
					: new String[] { "A" };

			int subArraySize = 1000;
			int listSize = combinedCablesList.size();

			for (int i = 0; i < listSize; i += subArraySize) {
				int remaining = listSize - i;
				int currentListSize = Math.min(subArraySize, remaining);
				List<String> sublist = combinedCablesList.subList(i, i + currentListSize);

				// MH that are outside the range
				query = findnearest.createNativeQuery(
						" SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.FIBER_CABLE_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.FIBER_CABLE_ID IN (:param1)  ) ");
				query.setParameter("param1", sublist);
				manholeTempList.addAll(query.getResultList());

				// HH that are outside the range
				query = findnearest.createNativeQuery(
						" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.FIBER_CABLE_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) "
								+ "UNION"
								+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.FIBER_CABLE_ID IN (:param1) )  ");
				query.setParameter("param1", sublist);
				handholeTempList.addAll(query.getResultList());

				query = findnearest.createNativeQuery(
						" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE,trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE, A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_CABLES C ON C.FIBER_CABLE_ID = B.FIBER_CABLE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.FIBER_CABLE_ID IN (:param1)   "
								+ " UNION "
								+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE,trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) "
								+ "UNION "
								+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE,trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_CABLES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.FIBER_CABLE_ID IN (:param1) )   ");
				query.setParameter("param1", sublist);
				dbTempList.addAll(query.getResultList());
			}

			subArraySize = 1000;
			listSize = combinedTubeList.size();

			for (int i = 0; i < listSize; i += subArraySize) {
				int remaining = listSize - i;
				int currentListSize = Math.min(subArraySize, remaining);
				List<String> sublist = combinedTubeList.subList(i, i + currentListSize);

				query = findnearest.createNativeQuery(
						"SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_TUBES C ON C.TUBE_ID = B.TUBE_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.TUBE_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.TUBE_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.TUBE_ID IN (:param1) ) ");

				query.setParameter("param1", sublist);
				manholeTempList.addAll(query.getResultList());

				query = findnearest.createNativeQuery(
						" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_TUBES C ON C.TUBE_ID = B.TUBE_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.TUBE_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.TUBE_ID IN (:param1) "
								+ "UNION"
								+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.TUBE_ID IN (:param1) ) ");
				query.setParameter("param1", sublist);
				handholeTempList.addAll(query.getResultList());

				query = findnearest.createNativeQuery(
						" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID, trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE, A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN TUBE_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_TUBES C ON C.TUBE_ID = B.TUBE_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.TUBE_ID IN (:param1)   "
								+ " UNION "
								+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.TUBE_ID IN (:param1) "
								+ "UNION "
								+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_TUBES B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.TUBE_ID IN (:param1) )  ");
				query.setParameter("param1", sublist);
				dbTempList.addAll(query.getResultList());
			}

			subArraySize = 1000;
			listSize = strandsIDs.size();

			for (int i = 0; i < listSize; i += subArraySize) {
				int remaining = listSize - i;
				int currentListSize = Math.min(subArraySize, remaining);
				List<String> sublist = strandsIDs.subList(i, i + currentListSize);

				query = findnearest.createNativeQuery(
						"SELECT * FROM (SELECT DISTINCT A.manhole_id as manhole_id ,A.manhole_name as manhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID) as totalCount,A.CITY as city FROM MANHOLE A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.manhole_id LEFT JOIN FIBER_STRANDS C ON C.STRAND_ID = B.STRAND_ID where B.AUXILIARY_POINT_ID LIKE '%MH%' AND C.STRAND_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.manhole_id where B.SOURCE_ID LIKE '%MH%' AND B.STRAND_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.manhole_id,A.manhole_name,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.MANHOLE_ID),A.CITY FROM MANHOLE A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.manhole_id where B.DESTINATION_ID LIKE '%MH%' AND B.STRAND_ID IN (:param1) ) ");

				query.setParameter("param1", sublist);
				manholeTempList.addAll(query.getResultList());

				query = findnearest.createNativeQuery(
						" SELECT * FROM (SELECT DISTINCT A.handhole_id as handhole_id ,A.handhole_name as handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID as PROJECT_ID ,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID) as totalCount,A.DM_NAME as DM_NAME FROM HANDHOLE A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.HANDHOLE_ID LEFT JOIN FIBER_STRANDS C ON C.STRAND_ID = B.STRAND_ID where B.AUXILIARY_POINT_ID LIKE '%HH%' AND C.STRAND_ID IN (:param1) "
								+ " UNION "
								+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.HANDHOLE_ID where B.SOURCE_ID LIKE '%HH%' AND B.STRAND_ID IN (:param1) "
								+ "UNION"
								+ " SELECT DISTINCT A.handhole_id,A.handhole_name, trim(replace(A.LONGITUDE,'�','')) as LONGITUDE, trim(replace(A.LATITUDE,'�','')) as LATITUDE, A.PROJECT_ID,(SELECT COUNT(*) FROM JUNCTION C WHERE C.PHYSICAL_LAYER_ID=A.HANDHOLE_ID),A.DM_NAME FROM HANDHOLE A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.HANDHOLE_ID where B.DESTINATION_ID LIKE '%HH%' AND B.STRAND_ID IN (:param1) )  ");
				query.setParameter("param1", sublist);
				handholeTempList.addAll(query.getResultList());

				query = findnearest.createNativeQuery(
						" SELECT * FROM ( SELECT DISTINCT A.DB_ID as DB_ID, trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE, A.DB_NAME as DB_NAME,A.MAX_CAPACITY as MAX_CAPACITY,A.SITE as SITE,A.PROJECT_ID as PROJECT_ID ,A.CITY as CITY,A.DB_NETWORK_LEVEL AS DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN STRAND_AUXILIARY_POINTS B  ON B.AUXILIARY_POINT_ID = A.DB_ID LEFT JOIN FIBER_STRANDS C ON C.STRAND_ID = B.STRAND_ID where B.AUXILIARY_POINT_ID LIKE '%DB%' AND C.STRAND_ID IN (:param1)   "
								+ " UNION "
								+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.SOURCE_ID = A.DB_ID where B.SOURCE_ID LIKE '%DB%' AND B.STRAND_ID IN (:param1) "
								+ "UNION "
								+ " SELECT DISTINCT A.DB_ID,trim(replace(A.DB_LONGITUDE,'�','')) as DB_LONGITUDE, trim(replace(A.DB_LATITUDE,'�','')) as DB_LATITUDE,A.DB_NAME,A.MAX_CAPACITY,A.SITE,A.PROJECT_ID ,A.CITY,A.DB_NETWORK_LEVEL FROM DISTRIBUTION_BOARD A LEFT JOIN FIBER_STRANDS B  ON B.DESTINATION_ID = A.DB_ID where B.DESTINATION_ID LIKE '%DB%' AND B.STRAND_ID IN (:param1) ) ");
				query.setParameter("param1", sublist);
				dbTempList.addAll(query.getResultList());
			}

			if (manholeList.size() > 0) {
				List<Object[]> tmprList = filterTempList(manholeTempList, manholesId);
				newList = findNearestArray(tmprList, Double.valueOf(closestLatPoint), Double.valueOf(closestLongPoint),
						Double.valueOf(closestDisRange), "ManHandhole_OutOfZone", noOfPoints);
				manholeList.addAll(newList);
			} else {
				manholeList = findNearestArray(manholeTempList, Double.valueOf(closestLatPoint),
						Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "ManHandhole_OutOfZone",
						noOfPoints);
			}

			if (handholeList.size() > 0) {
				List<Object[]> tmprList = filterTempList(handholeTempList, handholesId);
				newList = findNearestArray(tmprList, Double.valueOf(closestLatPoint), Double.valueOf(closestLongPoint),
						Double.valueOf(closestDisRange), "ManHandhole_OutOfZone", noOfPoints);
				handholeList.addAll(newList);
			} else {
				handholeList = findNearestArray(handholeTempList, Double.valueOf(closestLatPoint),
						Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "ManHandhole_OutOfZone",
						noOfPoints);
			}

			if (distribBoardList.size() > 0) {
				List<Object[]> tmprList = filterTempList(dbTempList, dbsId);
				newList = findNearestArray(tmprList, Double.valueOf(closestLatPoint), Double.valueOf(closestLongPoint),
						Double.valueOf(closestDisRange), "DB_OutOfZone", noOfPoints);
				distribBoardList.addAll(newList);
			} else {
				distribBoardList = findNearestArray(dbTempList, Double.valueOf(closestLatPoint),
						Double.valueOf(closestLongPoint), Double.valueOf(closestDisRange), "DB_OutOfZone", noOfPoints);
			}

		}
		junctionManholeList = findnearest.createNativeQuery(
				"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE,trim(replace(A.LATITUDE,'�','')) as LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN manhole B ON A.PHYSICAL_LAYER_ID = B.manhole_id ")
				.getResultList();

		junctionHandholeList = findnearest.createNativeQuery(
				"SELECT DISTINCT A.JUNCTION_ID, A.JUNCTION_NAME,A.PHYSICAL_LAYER_ID,A.PHYSICAL_LAYER_NAME,A.JUNCTION_NUMBER,A.CAPACITY,A.CITY,trim(replace(A.LONGITUDE,'�','')) as LONGITUDE,trim(replace(A.LATITUDE,'�','')) as LATITUDE,A.PROJECT_ID FROM JUNCTION A INNER JOIN handhole B ON A.PHYSICAL_LAYER_ID = b.handhole_id ")
				.getResultList();

		List<Object[]> nodeListQuery = findnearest.createNativeQuery(
				"SELECT DISTINCT NODE_PK,NODE_NAME,NODE_TYPE || ':'  || NODE_NAME,DOMAIN,SITE_ID,trim(replace(LONGITUDE,'�','')) as LONGITUDE,trim(replace(LATITUDE,'�','')) as LATITUDE,NODE_ID,SUB_DOMAIN_TYPE FROM NODE_ACTIVE WHERE (SUB_DOMAIN_TYPE='MSAN' OR SUB_DOMAIN_TYPE='SDH' OR SUB_DOMAIN_TYPE='DWDM' OR SUB_DOMAIN_TYPE='GPON' OR SUB_DOMAIN_TYPE='SWITCH' ) "
						+ " AND (LONGITUDE !='null' or LONGITUDE !=null ) AND (LATITUDE !='null' or LATITUDE !=null ) ")
				.getResultList();
		NodeList = findNearestArray(nodeListQuery, Double.valueOf(closestLatPoint), Double.valueOf(closestLongPoint),
				Double.valueOf(closestDisRange), "Nodes", noOfPoints);

		resultMap.put("fiberList", fiberList);
		resultMap.put("fiberAuxiliary_Data", fiberAuxiliary_Data);
		resultMap.put("fiberTubes", fiberTubes);
		resultMap.put("tubesAuxiliaries", tubesAuxiliaries);
		resultMap.put("strandsAuxiliaries", strandsAuxiliaries);
		resultMap.put("fiberStrands", fiberStrands);
		resultMap.put("manholeList", manholeList);
		resultMap.put("handholeList", handholeList);
		List<Object[]> updatedDistribBoardList = new ArrayList<>();

		for (Object[] row : distribBoardList) {
		    // Extract DB_ID (index 0)
		    String dbId = (String) row[0];

		    // Query TYPE for this DB_ID
		    String dbType = (String) findnearest.createNativeQuery(
		            "SELECT TYPE FROM DISTRIBUTION_BOARD WHERE DB_ID = :param1"
		        ).setParameter("param1", dbId)
		         .getSingleResult();
		    String controllerId = (String) findnearest.createNativeQuery(
		            "SELECT CONTROLLER_ID FROM DISTRIBUTION_BOARD WHERE DB_ID = :param1"
		        ).setParameter("param1", dbId)
		        .getSingleResult();

		    // Append TYPE at the end of the array
		    Object[] newRow = Arrays.copyOf(row, row.length + 2); // +2 for TYPE and CONTROLLER_ID
		    newRow[newRow.length - 2] = dbType;  // TYPE
		    newRow[newRow.length - 1] = controllerId;  // CONTROLLER_ID


		    updatedDistribBoardList.add(newRow);
		}
		
		List<String> controllerIdsList = new ArrayList<>();

		// Loop through the distribBoardList to get the DB_IDs
		for (Object[] row : distribBoardList) {
		    String dbId = (String) row[0];  // Assuming DB_ID is at index 0

		    // Create the query for each DB_ID
		    String query = "SELECT DISTINCT CONTROLLER_ID " +
		                   "FROM DISTRIBUTION_BOARD " +
		                   "WHERE CONTROLLER_ID IS NOT NULL " +
		                   "AND TYPE = 'active' " +
		                   "AND DB_ID = :dbId";  // Use :dbId as a parameter to avoid SQL injection

		    // Execute the query for the current DB_ID
		    List<String> result = findnearest.createNativeQuery(query)
		        .setParameter("dbId", dbId)  // Bind the current DB_ID to the query
		        .getResultList();

		    // Add the controller IDs to the controllerIdsList
		    if (result != null && !result.isEmpty()) {
		        controllerIdsList.addAll(result);  // Add the retrieved controller IDs to the list
		    }
		}
		System.out.println(mapper.writeValueAsString(controllerIdsList));
		// Convert the controller IDs list to an array if needed
		controllerIdsList = controllerIdsList.stream()
			    .distinct()
			    .collect(Collectors.toList());

			System.out.println(mapper.writeValueAsString(controllerIdsList));
		
	// Create query with WHERE condition
	
		
		

		// Loop through each controller ID in the controllerIdsList
		for (String controllerId : controllerIdsList) {
		    // Create query for each controller ID
		    String query = "SELECT C.CONTROLLER_ID, C.LONGITUDE, C.LATITUDE, C.CONTROLLER_NAME, C.NETWORK_LAYER, " +
		                   "COUNT(DB.DB_ID) AS DB_COUNT " +
		                   "FROM CONTROLLER C " +
		                   "LEFT JOIN DISTRIBUTION_BOARD DB ON C.CONTROLLER_ID = DB.CONTROLLER_ID " +
		                   "WHERE C.CONTROLLER_ID = :controllerId " +  // Use = instead of IN
		                   "GROUP BY C.CONTROLLER_ID, C.LONGITUDE, C.LATITUDE, C.CONTROLLER_NAME, C.NETWORK_LAYER";
		    
		    // Execute the query for the current controller ID
		    List<Object[]> result = findnearest.createNativeQuery(query)
		        .setParameter("controllerId", controllerId)  // Set the current controller ID as parameter
		        .getResultList();

		    // Add the result to the controllerDetailsList
		    if (result != null && !result.isEmpty()) {
		    	controllerList.addAll(result);  // Add the retrieved results
		    }
		}

		// Print the controller details (if needed)
		System.out.println(mapper.writeValueAsString(controllerList));
		
		
		
		resultMap.put("distribBoardList", updatedDistribBoardList);
		resultMap.put("controllerList", controllerList);
		
		resultMap.put("NodeList", NodeList);
		resultMap.put("junctionManholeList", junctionManholeList);
		resultMap.put("junctionHandholeList", junctionHandholeList);

		return resultMap;

	}
	public List<Object[]> findNearestArray(List<?> ListOfObjects, double closestLatPoint, double closestLongPoint,
			double closestDisRange, String Target, String noOfPoints) throws JsonProcessingException {
		List<Object[]> nearstPointsArray = new ArrayList<Object[]>();
		List<Object[]> nearstPointsArraySorted = new ArrayList<Object[]>();
		List<Object[]> result = new ArrayList<Object[]>();
		double pointDist = 0.0;
		for (int i = 0; i < ListOfObjects.size(); i++) {

			Object[] objectArray = (Object[]) ListOfObjects.get(i);

			if (Target == "Manhole" || Target == "Handhole" || Target == "ManHandhole_OutOfZone") {
				pointDist = haversine(closestLatPoint, closestLongPoint, Double.valueOf((String) objectArray[3]),
						Double.valueOf((String) objectArray[2]));
			} else if (Target == "Nodes") {
				pointDist = haversine(closestLatPoint, closestLongPoint, Double.valueOf((String) objectArray[6]),
						Double.valueOf((String) objectArray[5]));
			} else {
				pointDist = haversine(closestLatPoint, closestLongPoint, Double.valueOf((String) objectArray[2]),
						Double.valueOf((String) objectArray[1]));
			}
			// System.out.println("pointDist "+pointDist);
			if (pointDist < closestDisRange || Target == "ManHandhole_OutOfZone" || Target == "DB_OutOfZone") {
				objectArray = append(objectArray, (Object) pointDist);
				// System.out.println("pass if statment ");
				nearstPointsArray.add(objectArray);

			}
		}
		if (nearstPointsArray.size() > 0) {
			// Sorting using a single loop
			// nearstPointsArraySortedTemp= nearstPointsArray;
			double[] listofDistances = new double[nearstPointsArray.size()];
			for (int j = 0; j < nearstPointsArray.size(); j++) {

				if (Target == "DB_OutOfZone" || Target == "DistribBoard" || Target == "Nodes") {
					listofDistances[j] = Double.valueOf(String.valueOf(((Object[]) nearstPointsArray.get(j))[9]));
				} else {
					listofDistances[j] = Double.valueOf(String.valueOf(((Object[]) nearstPointsArray.get(j))[7]));
				}
			}
			System.out.println("listofDistances  " + mapper.writeValueAsString(listofDistances));
			Arrays.sort(listofDistances, 0, listofDistances.length);

			for (int j = 0; j < listofDistances.length; j++) {
				nearstPointsArraySorted.add(findUsingEnhancedForLoop(listofDistances[j], nearstPointsArray, Target));
				nearstPointsArray.remove(findUsingEnhancedForLoop(listofDistances[j], nearstPointsArray, Target));
			}
			// System.out.println("nearstPointsArraySorted
			// "+mapper.writeValueAsString(nearstPointsArraySorted));
		}
		// System.out.println("nearstPointsArray.size "+nearstPointsArray.size());
		if (nearstPointsArraySorted.size() > 0) {
			if (StringUtils.equalsIgnoreCase(noOfPoints, "") || StringUtils.equalsIgnoreCase(noOfPoints, null)) {
				result = nearstPointsArraySorted;

			} else {
				result = nearstPointsArraySorted.subList(0, Integer.valueOf(noOfPoints));
			}

		}
		return result;

	}
	public Object[] findUsingEnhancedForLoop(double distance, List<?> ListOfObjects, String Target) {
		double tempDistance = 0.0;

		for (int x = 0; x < ListOfObjects.size(); x++) {
			if (Target == "DB_OutOfZone" || Target == "DistribBoard" || Target == "Nodes") {
				tempDistance = Double.valueOf(String.valueOf(((Object[]) ListOfObjects.get(x))[9]));

			} else {
				tempDistance = Double.valueOf(String.valueOf(((Object[]) ListOfObjects.get(x))[7]));
			}
			if (distance == tempDistance) {
				return (Object[]) ListOfObjects.get(x);
			}
		}
		return null;
	}
	public void findIDsSrcDest(List<Object[]> listOfObjects, String target, List<String> mhIDs, List<String> hhIDs,
			List<String> dbIDs, double newStartLngPt, double newStartLatPt, double newEndLngPt, double newEndLatPt) {
		double sourceLng, sourceLat, destLng, destLat;

		for (Object[] row : listOfObjects) {

			if (target == "Cables") {
				sourceLng = Double.parseDouble(row[0].toString());
				sourceLat = Double.parseDouble(row[1].toString());
				destLng = Double.parseDouble(row[2].toString());
				destLat = Double.parseDouble(row[3].toString());

			} else {
				sourceLng = Double.parseDouble(row[1].toString());
				sourceLat = Double.parseDouble(row[2].toString());
				destLng = Double.parseDouble(row[3].toString());
				destLat = Double.parseDouble(row[4].toString());
			}

			String sourceId = row[6].toString();
			String destinationId = row[9].toString();

			if (sourceLng > newStartLngPt && sourceLat > newStartLatPt && sourceLng < newEndLngPt
					&& sourceLat < newEndLatPt) {

				if (sourceId.contains("MH") && !mhIDs.contains(sourceId)) {
					mhIDs.add(sourceId);
				} else if (sourceId.contains("HH") && !hhIDs.contains(sourceId)) {
					hhIDs.add(sourceId);
				} else if (sourceId.contains("DB") && !dbIDs.contains(sourceId)) {
					dbIDs.add(sourceId);
				}
			}

			if (destLng > newStartLngPt && destLat > newStartLatPt && destLng < newEndLngPt && destLat < newEndLatPt) {
				if (destinationId.contains("MH") && !mhIDs.contains(destinationId)) {
					mhIDs.add(destinationId);
				} else if (destinationId.contains("HH") && !hhIDs.contains(destinationId)) {
					hhIDs.add(destinationId);
				} else if (destinationId.contains("DB") && !dbIDs.contains(destinationId)) {
					dbIDs.add(destinationId);
				}

			}

		}
	}
	public String[] findListId(List<?> ListOfObjects, String Target) throws JsonProcessingException {

		Object[] objectIdArray = new Object[ListOfObjects.size()];

		for (int i = 0; i < ListOfObjects.size(); i++) {
			if (Target == "FiberCable") {
				objectIdArray[i] = (String) ((Object[]) ListOfObjects.get(i))[4];
			} else {
				objectIdArray[i] = (String) ((Object[]) ListOfObjects.get(i))[0];
			}

		}
		String[] stringArray = Arrays.copyOf(objectIdArray, objectIdArray.length, String[].class);
		// System.out.println("stringArray "+mapper.writeValueAsString(stringArray));

		return stringArray;
	}

	// function find nearest array for man/hand/db

	public List<Object[]> filterDataList(List<Object[]> listOfObjects, List<String> arrayIDs, String target) {

		List<Object[]> filteredList = new ArrayList<>();
		String Id = "";
		for (Object[] row : listOfObjects) {
			if (target == "Cables") {
				Id = row[4].toString();

			} else {
				Id = row[0].toString();
			}
			if (!arrayIDs.contains(Id)) {
				filteredList.add(row);
			}
		}
		return filteredList;
	}

	public List<Object[]> filterTempList(List<Object[]> listOfObjects, String[] IDsArray) {

		List<Object[]> filteredList = new ArrayList<>();
		List<String> idsList = Arrays.asList(IDsArray); // Convert array to List
		List<String> tempList = new ArrayList<>(); // to check if the point is repeated in listOfObjects

		for (Object[] row : listOfObjects) {

			String Id = row[0].toString();

			if (!idsList.contains(Id) && !tempList.contains(Id)) {
				filteredList.add(row);
				tempList.add(Id);
			}
		}
		return filteredList;
	}
	static double haversine(double lat1, double lon1, double lat2, double lon2) {
		// distance between latitudes and longitudes
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		// convert to radians
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// apply formulae
		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double rad = 6371; // This to calculate the distance in km.
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;

	}
	static <T> T[] append(T[] arr, T element) {
		final int N = arr.length;
		arr = Arrays.copyOf(arr, N + 1);
		arr[N] = element;
		return arr;
	}
	public List<Object[]> findLinearDistance(List<String> listIDs, List<?> ListOfObjects, double closestLatPoint,
			double closestLongPoint, double closestDisRange, String Target, String noOfPoints)
			throws JsonProcessingException {
		List<Object[]> nearstPointsArray = new ArrayList<Object[]>();
		List<Object[]> nearstPointsArraySorted = new ArrayList<Object[]>();
		List<Object[]> result = new ArrayList<Object[]>();
		double pointDist = 0.0;
		String ID = "";

		for (int i = 0; i < ListOfObjects.size(); i++) {

			Object[] objectArray = (Object[]) ListOfObjects.get(i);

			if (Target == "Manhole" || Target == "Handhole") {
				pointDist = haversine(closestLatPoint, closestLongPoint, Double.valueOf((String) objectArray[3]),
						Double.valueOf((String) objectArray[2]));
				//System.out.println("pointDist is " + pointDist);
			} else {
				pointDist = haversine(closestLatPoint, closestLongPoint, Double.valueOf((String) objectArray[2]),
						Double.valueOf((String) objectArray[1]));
				//System.out.println("pointDist is " + pointDist);
			}
			ID = (String) objectArray[0];

			if (pointDist < closestDisRange || listIDs.contains(ID) == true) {
				objectArray = append(objectArray, (Object) pointDist);
				nearstPointsArray.add(objectArray);
			}

		} // end of loop

		if (nearstPointsArray.size() > 0) {

			double[] listofDistances = new double[nearstPointsArray.size()];
			for (int j = 0; j < nearstPointsArray.size(); j++) {

				if (Target == "DistribBoard") {
					listofDistances[j] = Double.valueOf(String.valueOf(((Object[]) nearstPointsArray.get(j))[9]));
				} else {
					listofDistances[j] = Double.valueOf(String.valueOf(((Object[]) nearstPointsArray.get(j))[7]));
				}
			}
			Arrays.sort(listofDistances, 0, listofDistances.length);

			for (int j = 0; j < listofDistances.length; j++) {
				nearstPointsArraySorted.add(findUsingEnhancedForLoop(listofDistances[j], nearstPointsArray, Target));
				nearstPointsArray.remove(findUsingEnhancedForLoop(listofDistances[j], nearstPointsArray, Target));
			}
		}
		if (nearstPointsArraySorted.size() > 0) {
			if (StringUtils.equalsIgnoreCase(noOfPoints, "") || StringUtils.equalsIgnoreCase(noOfPoints, null)) {
				result = nearstPointsArraySorted;

			} else {
				result = nearstPointsArraySorted.subList(0, Integer.valueOf(noOfPoints));
			}

		}
		return result;

	}
	public static void removeDuplicatesByIndex(List<Object[]> dataList, int index) {
        HashSet<String> seenValues = new HashSet<>();
        
        for (int i = 0; i < dataList.size(); i++) {
            Object[] array = dataList.get(i);
            Object valueAtIndex = array[index];
            
            if (valueAtIndex instanceof String) {
                String valueAsString = (String) valueAtIndex;
                
                if (seenValues.contains(valueAsString)) {
                    dataList.remove(i);
                    i--; // Adjust index after removal
                } else {
                    seenValues.add(valueAsString);
                }
            }
        }
}
}