package com.aliat.alm.discoveryProc;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.io.IOException;
import java.math.BigDecimal; // Add this import statement
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.DiscoveryProcess;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class CommScopeService {

	@Autowired
	Notify notifications;

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private String str = null;

	@SuppressWarnings("rawtypes")
	private static Query query = null;

	private static final Logger logger = LoggerFactory.getLogger(CommScopeService.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> loginAPI(String ipAddress, String username, String password, int requestedDuration) {

		System.out.println("ipAddress is " + ipAddress + " username is " + username + " password is " + password
				+ " requestedDuration is " + requestedDuration);
		Map<String, Object> rtn = new LinkedHashMap<>();

		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/users/login";
		session = AlmDbSession.getInstance().getSession();

		Map<String, Object> credentials = new HashMap<>();
		credentials.put("username", username);
		credentials.put("password", password);
		credentials.put("requestedDuration", requestedDuration);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(credentials, headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
					Map<String, Object> body = response.getBody();
					String token = (String) body.get("accessToken");
					logger.info("Login successful. Token: {}", token);
					System.out.println("Token: " + token);
					rtn.put("responseBody", body);
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
				} else {
					System.out.println("Login failed: " + response.getStatusCode());
					logger.warn("Login failed: {}", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope login", ex);
				logger.info("Error on the level of CommScope Login API request with a message : " + ex + "\n"
						+ ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> newTokenAPI(String ipAddress, String oldToken) {

		System.out.println("ipAddress is " + ipAddress + " token is " + oldToken);
		Map<String, Object> rtn = new LinkedHashMap<>();

		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/users/token";
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(oldToken);
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
					Map<String, Object> body = response.getBody();
					String token = (String) body.get("accessToken");
					logger.info("Getting new toke is succeeded. Token: {}", token);
					System.out.println("Token: " + token);
					rtn.put("responseBody", body);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
				} else {
					System.out.println("Getting new token failed: " + response.getStatusCode());
					logger.warn("Getting new token failed: {}", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope getting new token", ex);
				logger.info("Error on the level of CommScope getting new token API request with a message : " + ex
						+ "\n" + ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> getRackAPI(String token, String ipAddress) {

		System.out.println("token is " + token + " IP Address is " + ipAddress);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/racks";
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
					Map<String, Object> body = response.getBody();
					logger.info("Getting rack information successfully executed.");
					rtn.put("responseBody", body);
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
				} else {
					System.out.println("Getting rack information failed: " + response.getStatusCode());
					logger.warn("Getting rack information failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope getting rack information", ex);
				logger.info("Error on the level of CommScope getting rack API request with a message : " + ex + "\n"
						+ ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> controllerxAPI(String token, String ipAddress) {

		System.out.println("token is " + token + " IP Address is " + ipAddress);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/controllers";
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
					Map<String, Object> body = response.getBody();
					String id = (String) body.get("networkManagerId");
					logger.info("Getting controllers information successfully executed. The network manager ID is", id);
					System.out.println("ID is: " + id);
					rtn.put("responseBody", body);
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
				} else {
					System.out.println("Getting controllers information failed: " + response.getStatusCode());
					logger.warn("Getting controllers information failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope getting controller information", ex);
				logger.info("Error on the level of CommScope Controllers API request with a message : " + ex + "\n"
						+ ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> getPanelAPI(String token, String ipAddress, String rackID) {

		System.out.println("token is " + token + " IP Address is " + ipAddress + " rackID is " + rackID);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/panels?rackId=" + rackID;
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
					rtn.put("responseBody", response.getBody());
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("Getting panels information successfully executed.");
				} else {
					System.out.println("Getting panels information failed: " + response.getStatusCode());
					logger.warn("Getting panels information failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API getting panels information", ex);
				logger.info("Error on the level of CommScope API getting panels information with a message : " + ex
						+ "\n" + ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> patchesAPI(String token, String ipAddress, String rackID) {

		System.out.println("token is " + token + " IP Address is " + ipAddress + " rackID is " + rackID);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/patches?rackId=" + rackID;
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
					rtn.put("responseBody", response.getBody());
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("Getting patches information successfully executed.");
				} else {
					System.out.println("Getting patches information failed: " + response.getStatusCode());
					logger.warn("Getting patches information failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API getting patches information", ex);
				logger.info("Error on the level of CommScope API getting patches information with a message : " + ex
						+ "\n" + ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> incompletePatchesAPI(String token, String ipAddress, String rackID) {

		System.out.println("token is " + token + " IP Address is " + ipAddress + " rackID is " + rackID);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/incompletePatches?rackId=" + rackID;
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
					rtn.put("responseBody", response.getBody());
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("Getting incomplete patches information successfully executed.");
				} else {
					System.out.println("Getting incomplete patches information failed: " + response.getStatusCode());
					logger.warn("Getting incomplete patches information failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API getting incomplete patches information", ex);
				logger.info(
						"Error on the level of CommScope API getting incomplete patches information with a message : "
								+ ex + "\n" + ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> getNetworkInterfaceAPI(String token, String ipAddress) {

		System.out.println("token is " + token + " IP Address is " + ipAddress);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/networkInterface";
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
					rtn.put("responseBody", response.getBody());
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("Getting network intefrace information successfully executed.");
				} else {
					System.out.println("Getting network interface information failed: " + response.getStatusCode());
					logger.warn("Getting network interface information failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API getting network interface information", ex);
				logger.info(
						"Error on the level of CommScope API getting network interface information with a message : "
								+ ex + "\n" + ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> portStatusAPI(String token, String ipAddress, String rackID, String kitID,
			String moduleID, String portID) {

		System.out.println("token is " + token + " IP Address is " + ipAddress + " rackID is " + rackID + " kitID is "
				+ kitID + " moduleID is " + moduleID + " portID is " + portID);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/portStatus?rackId=" + rackID + "&kitId=" + kitID + "&moduleId="
				+ moduleID + "&portId=" + portID;
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
					rtn.put("responseBody", response.getBody());
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("Getting port status information successfully executed.");
				} else {
					System.out.println("Getting port status information failed: " + response.getStatusCode());
					logger.warn("Getting port status information failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API getting port status information", ex);
				logger.info("Error on the level of CommScope API getting port status information with a message : " + ex
						+ "\n" + ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> eventNoteAPI(String token, String ipAddress, String eventID, String timeout) {

		System.out.println("token is " + token + " IP Address is " + ipAddress + " eventID is " + eventID
				+ " timeout is " + timeout);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/events?start-event-id=" + eventID + "&timeout=" + timeout;
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
					rtn.put("responseBody", response.getBody());
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("Getting event notifications successfully executed.");
				} else {
					System.out.println("Getting event notifications failed: " + response.getStatusCode());
					logger.warn("Getting event notification failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API getting event notification status ", ex);
				logger.info("Error on the level of CommScope API getting event notification with a message: " + ex
						+ "\n" + ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> setDateTimeAPI(String token, String ipAddress, String dateTime) {

		System.out.println("token is " + token + " IP Address is " + ipAddress + " dateTime is " + dateTime);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/controllers";
		session = AlmDbSession.getInstance().getSession();

		Map<String, Object> credentials = new HashMap<>();
		credentials.put("controllerTime", dateTime);
		credentials.put("stateTimeout", 30);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(credentials, headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PUT, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK) {
					// rtn.put("responseBody", response.getBody());
					rtn.put("controllerTime", dateTime);
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("Controller time successfully updated to {}", dateTime);
				} else {
					System.out.println("Setting Controller Date Time failed: " + response.getStatusCode());
					logger.warn("Setting Controller Date Time failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API Setting Controller Date Time ", ex);
				logger.info("Error on the level of CommScope API Setting Controller Date Time with a message: " + ex
						+ "\n" + ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> setCurrentDateTimeAPI(String token, String ipAddress) {

		System.out.println("token is " + token + " IP Address is " + ipAddress);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/controllers";
		session = AlmDbSession.getInstance().getSession();

		Map<String, Object> credentials = new HashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String controllerTime = LocalDateTime.now().format(formatter);
		credentials.put("controllerTime", controllerTime);
		credentials.put("stateTimeout", 30);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(credentials, headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PUT, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK) {
					// rtn.put("responseBody", response.getBody());
					rtn.put("controllerTime", controllerTime);
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("Controller time successfully updated to {}", controllerTime);
				} else {
					System.out.println("Setting Controller to Current Date Time failed: " + response.getStatusCode());
					logger.warn("Setting Controller to Current Date Time failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API Setting Controller to Current Date Time ", ex);
				logger.info(
						"Error on the level of CommScope API Setting Controller to Current Date Time with a message: "
								+ ex + "\n" + ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> genWorkOrderAPI(String token, String ipAddress, List<Map<String, Object>> woDetails) {

		System.out.println("token is " + token + " IP Address is " + ipAddress);
		Map<String, Object> rtn = new LinkedHashMap<>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println("The woDetails is " + gson.toJson(woDetails));
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/workOrderTasks";
		session = AlmDbSession.getInstance().getSession();

		Map<String, Object> credentials = new HashMap<>();
		credentials.put("workOrderTasks", woDetails);
		System.out.println("The credentials is " + gson.toJson(credentials));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(credentials, headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK) {
					rtn.put("responseBody", response.getBody());
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("Generating Work Order at Controller X successfully done");
				} else {
					System.out.println("Generating Work Order at ControllerX failed: " + response.getStatusCode());
					System.out.println("Response Body is " + response.getBody());
					logger.warn("Generating Work Order at ControllerX failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API Generating Work Order: ", ex);
				logger.info(
						"Error on the level of CommScope API Generating Work Order: " + ex + "\n" + ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> getWorkOrderAPI(String token, String ipAddress, int workOrderTaskId) {

		System.out.println(
				"token is " + token + " IP Address is " + ipAddress + " workOrderTaskId is " + workOrderTaskId);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/workOrderTasks?workOrderTaskId=" + workOrderTaskId;
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
					rtn.put("responseBody", response.getBody());
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("Getting Work Orders successfully executed.");
				} else {
					System.out.println("Getting Work Orders failed: " + response.getStatusCode());
					logger.warn("Getting Work Orders failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API getting Work Orders status ", ex);
				logger.info("Error on the level of CommScope API getting Work Orders with a message: " + ex + "\n"
						+ ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> listWorkOrderAPI(String token, String ipAddress) {

		System.out.println("token is " + token + " IP Address is " + ipAddress);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/workOrderTasks";
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
					rtn.put("responseBody", response.getBody());
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("List Work Orders successfully executed.");
				} else {
					System.out.println("List Work Orders failed: " + response.getStatusCode());
					logger.warn("List Work Orders failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API Listing Work Orders status ", ex);
				logger.info("Error on the level of CommScope API Listing Work Orders with a message: " + ex + "\n"
						+ ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> deleteWorkOrderAPI(String token, String ipAddress, int workOrderTaskId) {

		System.out.println(
				"token is " + token + " IP Address is " + ipAddress + " workOrderTaskId is " + workOrderTaskId);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/workOrderTasks?workOrderTaskId=" + workOrderTaskId;
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK) {
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("Work Order deleted successfully.");
				} else {
					System.out.println("Work Order deleting failed: " + response.getStatusCode());
					logger.warn("Work Order deleting failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API for deleting Work Order status ", ex);
				logger.info("Error on the level of CommScope API for deleting Work Order with a message: " + ex + "\n"
						+ ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> deleteAllWorkOrderAPI(String token, String ipAddress) {

		System.out.println("token is " + token + " IP Address is " + ipAddress);
		Map<String, Object> rtn = new LinkedHashMap<>();
		HttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries() // prevent retry issues
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// same error handler
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});

		String url = "http://" + ipAddress + "/api/v1/workOrderTasks";
		session = AlmDbSession.getInstance().getSession();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<>(headers);

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try (Socket socket = new Socket(ipAddress, 80)) {
				System.out.println("Port is opened.");
				logger.info("Port is opened.");
			} catch (IOException e) {
				System.out.println("Port is closed.");
				logger.info("Port is closed.");
				rtn.put("status", "Failed");
				rtn.put("reason", "Port is closed");
				System.out.println("Port is closed.");
				return rtn;
			}
			try {
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Map.class);
				System.out.println("response status code is " + response.getStatusCode());
				if (response.getStatusCode() == HttpStatus.OK) {
					rtn.put("accessToken", token);
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
					rtn.put("status", "Success");
					logger.info("Work Orders deleted successfully.");
				} else {
					System.out.println("Work Orders deleting failed: " + response.getStatusCode());
					logger.warn("Work Orders deleting failed: ", response.getStatusCode());
					rtn.put("status", "Failed");
					rtn.put("responseCode", response.getStatusCode());
					rtn.put("responseCodeValue", response.getStatusCodeValue());
				}
				tx.commit();
			} catch (HttpClientErrorException ex) {
				if (tx != null)
					tx.rollback();
				logger.error("Error during CommScope API for deleting Work Orders status ", ex);
				logger.info("Error on the level of CommScope API for deleting Work Orders with a message: " + ex + "\n"
						+ ex.getMessage());
				rtn.put("status", "Error");
				rtn.put("responseCode", ex.getStatusCode());
				rtn.put("responseCodeValue", ex.getRawStatusCode());
				rtn.put("message", ex.getMessage());

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}
}