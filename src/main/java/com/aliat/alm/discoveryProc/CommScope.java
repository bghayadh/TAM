package com.aliat.alm.discoveryProc;

import java.util.LinkedHashMap;
import java.util.List;
//import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class CommScope {

	@Autowired
	Notify notifications;

	@Autowired
	private CommScopeService commScopeService;

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private String str = null;

	@SuppressWarnings("rawtypes")
	private static Query query = null;

	private static final Logger logger = LoggerFactory.getLogger(CommScope.class);

	@RequestMapping(value = "/CommScopeLogin", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeLogin(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcomg to Login Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getParameter("ipAddress");

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
			} catch (Exception e) {
				logger.info(
						"Error on the level of CommScope Login request with a message : " + e + "\n" + e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopeControllerX", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeControllerX(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcomg to Controller Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getParameter("ipAddress");

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.controllerxAPI((String) rtn.get("accessToken"), ipAddress);
					System.out.println("rtn returned from controllerxAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope ControllerX request with a message : " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopeGetPanel", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeGetPanel(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcomg to CommScope Get Panel Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getParameter("ipAddress");
		String rackID = request.getParameter("rackID");

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress
				+ " rackID is " + rackID);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.getPanelAPI((String) rtn.get("accessToken"), ipAddress, rackID);
					System.out.println("rtn returned from getPanelAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope Get Panel request with a message : " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopePatches", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopePatches(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcomg to CommScope Patches Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getParameter("ipAddress");
		String rackID = request.getParameter("rackID");

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress
				+ " rackID is " + rackID);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.patchesAPI((String) rtn.get("accessToken"), ipAddress, rackID);
					System.out.println("rtn returned from patchesAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope Patches request with a message : " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopeIncompletePatches", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeIncompletePatches(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcomg to CommScope Incomplete Patches Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getParameter("ipAddress");
		String rackID = request.getParameter("rackID");

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress
				+ " rackID is " + rackID);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.incompletePatchesAPI((String) rtn.get("accessToken"), ipAddress, rackID);
					System.out.println("rtn returned from patchesAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope Incomplete Patches request with a message : " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopeGetNetworkInterface", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeGetNetworkInterface(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcomg to CommScope CommScopeGetNetworkInterface Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getParameter("ipAddress");

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.getNetworkInterfaceAPI((String) rtn.get("accessToken"), ipAddress);
					System.out.println("rtn returned from patchesAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope Get Network Interface request with a message : " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopePortStatus", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopePortStatus(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcomg to CommScope Port Status Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getParameter("ipAddress");
		String rackID = request.getParameter("rackID");
		String kitID = request.getParameter("kitID");
		String moduleID = request.getParameter("moduleID");
		String portID = request.getParameter("portID");

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress
				+ " rackID is " + rackID + " kitID is " + kitID + " moduleID is " + moduleID + " portID is " + portID);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.portStatusAPI((String) rtn.get("accessToken"), ipAddress, rackID, kitID,
							moduleID, portID);
					System.out.println("rtn returned from portStatusAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope Port Status request with a message: " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopeEventNote", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeEventNote(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcomg to CommScope Port Status Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getParameter("ipAddress");
		String eventID = request.getParameter("eventID");
		String timeout = request.getParameter("timeout");

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress
				+ " eventID is " + eventID + " timeout is " + timeout);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.eventNoteAPI((String) rtn.get("accessToken"), ipAddress, eventID, timeout);
					System.out.println("rtn returned from eventNoteAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope Event Note request with a message: " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopeSetDateTime", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeSetDateTime(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcomg to CommScope Set Date Time Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getParameter("ipAddress");
		String dateTime = request.getParameter("dateTime");

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress
				+ " dateTime is " + dateTime);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.setDateTimeAPI((String) rtn.get("accessToken"), ipAddress, dateTime);
					System.out.println("rtn returned from setDateTimeAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope Set Date Time request with a message: " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopeSetCurrentDateTime", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeSetCurrentDateTime(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcome to CommScope Set Current Date Time Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getParameter("ipAddress");

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.setCurrentDateTimeAPI((String) rtn.get("accessToken"), ipAddress);
					System.out.println("rtn returned from setCurrentDateTimeAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope Set Current Date Time request with a message: " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/CommScopeGenWO", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> CommScopeGenWO(@RequestBody Map<String, Object> payload, Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Welcome to CommScope Generate Work Order Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String username = (String) payload.get("username");
		String password = (String) payload.get("password");
		String ipAddress = (String) payload.get("ipAddress");
		List<Map<String, Object>> woDetails = (List<Map<String, Object>>) payload.get("woDetails");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress);
		System.out.println("The work order details is " + gson.toJson(woDetails));
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.genWorkOrderAPI((String) rtn.get("accessToken"), ipAddress, woDetails);
					System.out.println("rtn returned from genWorkOrderAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope Generating Work Order request with a message: " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopeGetWO", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeGetWO(@RequestParam String username, @RequestParam String password,
			@RequestParam String ipAddress, @RequestParam Integer workOrderTaskId, Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Welcomg to CommScope GetWO Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress
				+ " workOrderTaskId is " + workOrderTaskId);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.getWorkOrderAPI((String) rtn.get("accessToken"), ipAddress, workOrderTaskId);
					System.out.println("rtn returned from getWorkOrderAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope Get Work Order request with a message: " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopeListWO", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeListWO(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcomg to CommScope GetWO Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getParameter("ipAddress");

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.listWorkOrderAPI((String) rtn.get("accessToken"), ipAddress);
					System.out.println("rtn returned from listWorkOrderAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope List Work Order request with a message: " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopeDelWO", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeDelWO(@RequestParam String username, @RequestParam String password,
			@RequestParam String ipAddress, @RequestParam Integer workOrderTaskId, Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Welcome to CommScope delWO Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress
				+ " workOrderTaskId is " + workOrderTaskId);
		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.deleteWorkOrderAPI((String) rtn.get("accessToken"), ipAddress,
							workOrderTaskId);
					System.out.println("rtn returned from deleteWorkOrderAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope Delete Work Order request with a message: " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/CommScopeDelAllWO", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CommScopeDelAllWO(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Welcome to CommScope delWO Request.");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getParameter("ipAddress");

		System.out.println("username is " + username + " password is " + password + " ipAddress is " + ipAddress);

		session = AlmDbSession.getInstance().getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				System.out.println("Just before calling loginAPI");
				rtn = commScopeService.loginAPI(ipAddress, username, password, 900);
				System.out.println("rtn returned from loginAPI is " + mapper.writeValueAsString(rtn));
				if (rtn.containsKey("accessToken") && "Success".equals(rtn.get("status"))) {
					rtn = commScopeService.deleteAllWorkOrderAPI((String) rtn.get("accessToken"), ipAddress);
					System.out.println("rtn returned from deleteAllWorkOrderAPI is " + mapper.writeValueAsString(rtn));
				}
			} catch (Exception e) {
				logger.info("Error on the level of CommScope Delete Work Orders request with a message: " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}
}