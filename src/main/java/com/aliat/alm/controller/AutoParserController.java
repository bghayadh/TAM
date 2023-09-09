package com.aliat.alm.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import com.aliat.alm.DM.FinancialDM;
import com.aliat.alm.Parser.CheckBoardMovement;
import com.aliat.alm.Parser.CheckBoardMovement_WIN;
import com.aliat.alm.Parser.CheckNodeMovement;
import com.aliat.alm.Parser.CheckNodeMovement_WIN;
import com.aliat.alm.Parser.CheckAntennaMovement;
import com.aliat.alm.Parser.CheckAntennaMovement_WIN;
import com.aliat.alm.Parser.CheckCabinetMovement;
import com.aliat.alm.Parser.CheckCabinetMovement_WIN;
import com.aliat.alm.Parser.CheckHostVersionMovement;
import com.aliat.alm.Parser.CheckHostVersionMovement_WIN;
import com.aliat.alm.Parser.NewAttribute;
import com.aliat.alm.Parser.NewAttribute_WIN;
import com.aliat.alm.Parser.CopyParsingDataToALM;
import com.aliat.alm.Parser.CopyParsingDataToALM_WIN;
import com.aliat.alm.Parser.FirstParsing;
import com.aliat.alm.Parser.FirstParsing_WIN;
import com.aliat.alm.Parser.LoadFilesAOSS;
import com.aliat.alm.Parser.LoadFilesAOSS_WIN;
import com.aliat.alm.Parser.LoadFilesNokia;
import com.aliat.alm.services.LoginServices;

import com.aliat.alm.telkom.Parser.LoadFileDWDMHuawei;
import com.aliat.alm.telkom.Parser.LoadFileDWDMTejas;
import com.aliat.alm.telkom.Parser.LoadFileIPHuawei;
import com.aliat.alm.telkom.Parser.LoadFileIPNokia;
import com.aliat.alm.telkom.Parser.LoadFileMWEricsson;
import com.aliat.alm.telkom.Parser.LoadFileSDHAlcatel;
import com.aliat.alm.telkom.Parser.LoadFileSDHHuawei;
import com.aliat.alm.telkom.Parser.LoadFilesEntHuawei;
import com.aliat.alm.telkom.Parser.LoadFilesRANZTE;
import com.aliat.alm.telkom.Parser.LoadFilesRanHuawei;


@Controller
public class AutoParserController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	@RequestMapping(value = "/AutoParser", method = RequestMethod.GET)
	public String AutoParser(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Welcome home! The client locale is {}.", locale);
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		} else {
			return "AutoParser";
		}
	}
	
	@RequestMapping(value = "/runScript1", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runScript1(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/loadFilesAOSS", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadFilesAOSS(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

		Map<String, Object> rtn = new LinkedHashMap<>();
//		LoadFilesAOSS_WIN myClass = new LoadFilesAOSS_WIN();
		LoadFilesAOSS myClass = new LoadFilesAOSS();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	@ResponseBody
	public Map<String, Object> loadFilesRanHuawei(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException{

		Map<String, Object> rtn = new LinkedHashMap<>();
		LoadFilesRanHuawei myClass = new LoadFilesRanHuawei();
		try {
			myClass.main(null,request.getParameter("vendor"),request.getParameter("domain"),request.getParameter("sub_domain"),request.getParameter("type"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/loadFilenokia", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadFilenokia(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		System.out.println("hello nokia loader");
//		LoadFilesAOSS_WIN myClass = new LoadFilesAOSS_WIN();
		LoadFilesNokia myClass = new LoadFilesNokia();
		try {
			myClass.main(null);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/loadFilesEntHW", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadFilesEntHW(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

		Map<String, Object> rtn = new LinkedHashMap<>();
//		LoadFilesAOSS_WIN myClass = new LoadFilesAOSS_WIN();
		System.out.println("loader huawei ent ");
		System.out.println("loader huawei param "+request.getParameter("vendor")+", "+request.getParameter("domain")+", "+request.getParameter("sub_domain"));
		LoadFilesEntHuawei myClass = new LoadFilesEntHuawei();
		myClass.main(null, request.getParameter("vendor"), request.getParameter("domain"), request.getParameter("sub_domain") );
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/loadFileDWDMhuawei", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadFileDWDMhuawei(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException{

		Map<String, Object> rtn = new LinkedHashMap<>();
		System.out.println("hello nokia loader");
//		LoadFilesAOSS_WIN myClass = new LoadFilesAOSS_WIN();
		LoadFileDWDMHuawei myClass = new LoadFileDWDMHuawei();
		try {
			myClass.main(null,request.getParameter("vendor"),request.getParameter("domain"),request.getParameter("sub_domain"),request.getParameter("type"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/loadFileSDHhuawei", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadFileSDHhuawei(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException{

		Map<String, Object> rtn = new LinkedHashMap<>();
		System.out.println("hello nokia loader");
//		LoadFilesAOSS_WIN myClass = new LoadFilesAOSS_WIN();
		LoadFileSDHHuawei myClass = new LoadFileSDHHuawei();
		try {
			myClass.main(null,request.getParameter("vendor"),request.getParameter("domain"),request.getParameter("sub_domain"),request.getParameter("type"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/loadFileSDHalcatel", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadFileSDHalcatel(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException{

		Map<String, Object> rtn = new LinkedHashMap<>();
		System.out.println("hello nokia loader");
//		LoadFilesAOSS_WIN myClass = new LoadFilesAOSS_WIN();
		LoadFileSDHAlcatel myClass = new LoadFileSDHAlcatel();
		try {
			myClass.main(null,request.getParameter("vendor"),request.getParameter("domain"),request.getParameter("sub_domain"),request.getParameter("type"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/loadFileDWDMtejas", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadFileDWDMtejas(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException{

		Map<String, Object> rtn = new LinkedHashMap<>();
		System.out.println("hello nokia loader");
//		LoadFilesAOSS_WIN myClass = new LoadFilesAOSS_WIN();
		LoadFileDWDMTejas myClass = new LoadFileDWDMTejas();
		try {
			myClass.main(null,request.getParameter("vendor"),request.getParameter("domain"),request.getParameter("sub_domain"),request.getParameter("type"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/loadFileMWERICSSON", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadFileMWERICSSON(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException{

		Map<String, Object> rtn = new LinkedHashMap<>();
		System.out.println("hello ERC loader");
//		LoadFilesAOSS_WIN myClass = new LoadFilesAOSS_WIN();
		LoadFileMWEricsson myClass = new LoadFileMWEricsson();
		try {
			myClass.main(null,request.getParameter("vendor"),request.getParameter("domain"),request.getParameter("sub_domain"),request.getParameter("type"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/loadFileIPHuawei", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadFileIPHuawei(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException{

		Map<String, Object> rtn = new LinkedHashMap<>();
		System.out.println("hello ERC loader");
		LoadFileIPHuawei myClass = new LoadFileIPHuawei();
		try {
			myClass.main(null,request.getParameter("vendor"),request.getParameter("domain"),request.getParameter("sub_domain"),request.getParameter("type"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/loadFileIPNokia", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadFileIPNokia(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException{

		Map<String, Object> rtn = new LinkedHashMap<>();
		System.out.println("hello ERC loader");
		LoadFileIPNokia myClass = new LoadFileIPNokia();
		try {
			myClass.main(null,request.getParameter("vendor"),request.getParameter("domain"),request.getParameter("sub_domain"),request.getParameter("type"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/loadFilesRANZTE", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadFilesRANZTE(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		System.out.println("loader ZTE RAN ");
		System.out.println("loader ZTE RAN "+request.getParameter("vendor")+", "+request.getParameter("domain")+", "+request.getParameter("sub_domain"));
		LoadFilesRANZTE myClass = new LoadFilesRANZTE();
		myClass.main(null, request.getParameter("vendor"), request.getParameter("domain"), request.getParameter("sub_domain") );
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/copyParsingDataToALM", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> copyParsingDataToALM(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

		Map<String, Object> rtn = new LinkedHashMap<>();
//		CopyParsingDataToALM_WIN myClass = new CopyParsingDataToALM_WIN();
		CopyParsingDataToALM myClass = new CopyParsingDataToALM();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/firstParsing", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> firstParsing(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

		Map<String, Object> rtn = new LinkedHashMap<>();
//		FirstParsing_WIN myClass = new FirstParsing_WIN();
		FirstParsing myClass = new FirstParsing();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/checkNodeMovement", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkNodeMovement(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

		Map<String, Object> rtn = new LinkedHashMap<>();
//		CheckNodeMovement_WIN myClass = new CheckNodeMovement_WIN();
		CheckNodeMovement myClass = new CheckNodeMovement();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/checkCabinetMovement", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkCabinetMovement(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

		Map<String, Object> rtn = new LinkedHashMap<>();
//		CheckCabinetMovement_WIN myClass = new CheckCabinetMovement_WIN();
		CheckCabinetMovement myClass = new CheckCabinetMovement();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/checkBoardMovement", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkBoardMovement(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

		Map<String, Object> rtn = new LinkedHashMap<>();
//		CheckBoardMovement_WIN myClass = new CheckBoardMovement_WIN();
		CheckBoardMovement myClass = new CheckBoardMovement();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/checkAntennaMovement", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkAntennaMovement(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

		Map<String, Object> rtn = new LinkedHashMap<>();
//		CheckAntennaMovement_WIN myClass = new CheckAntennaMovement_WIN();
		CheckAntennaMovement myClass = new CheckAntennaMovement();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/checkHostVersionMovement", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkHostVersionMovement(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

		Map<String, Object> rtn = new LinkedHashMap<>();
//		CheckHostVersionMovement_WIN myClass = new CheckHostVersionMovement_WIN();
		CheckHostVersionMovement myClass = new CheckHostVersionMovement();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/NewAttribute", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> NewAttribute(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {

		Map<String, Object> rtn = new LinkedHashMap<>();
//		NewAttribute_WIN myClass = new NewAttribute_WIN();
		NewAttribute myClass = new NewAttribute();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
}
