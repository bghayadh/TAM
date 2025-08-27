package com.aliat.alm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.DM.Appliance2Item;
import com.aliat.alm.DM.AuxiliaryInstallation2Item;
import com.aliat.alm.DM.CivilElements2Item;
import com.aliat.alm.DM.Cloudservices2Item;
import com.aliat.alm.DM.DMAppliance;
import com.aliat.alm.DM.DMAuxiliaryInstallationMaterials;
import com.aliat.alm.DM.DMCivilElements;
import com.aliat.alm.DM.DMCloudservices;
import com.aliat.alm.DM.DMCumulIt;
import com.aliat.alm.DM.DMCumulPassive;
import com.aliat.alm.DM.DMDeviceinStack;
import com.aliat.alm.DM.DMEsim;
import com.aliat.alm.DM.DMFROEquipment;
import com.aliat.alm.DM.DMFireFightingSystem;
import com.aliat.alm.DM.DMFirewall;
import com.aliat.alm.DM.DMFixedFiberNetworkDARKFIBER;
import com.aliat.alm.DM.DMFurniture;
import com.aliat.alm.DM.DMHayat;
import com.aliat.alm.DM.DMHypervisor;
import com.aliat.alm.DM.DMITEquipment;
import com.aliat.alm.DM.DMLayer3Switch;
import com.aliat.alm.DM.DMMonitors;
import com.aliat.alm.DM.DMNOPO;
import com.aliat.alm.DM.DMNetworkInfrastructure;
import com.aliat.alm.DM.DMNotClassified;
import com.aliat.alm.DM.DMOfficeEquipment;
import com.aliat.alm.DM.DMPowerElements;
import com.aliat.alm.DM.DMRealEstate;
import com.aliat.alm.DM.DMSANSwitchSwitch;
import com.aliat.alm.DM.DMSecuritySystem;
import com.aliat.alm.DM.DMStorage;
import com.aliat.alm.DM.DMSwitch;
import com.aliat.alm.DM.DMSwitchSANSwitch;
import com.aliat.alm.DM.DMTransmission;
import com.aliat.alm.DM.DMVASservices;
import com.aliat.alm.DM.DMWindowsDesktop;
import com.aliat.alm.DM.DMWindowsServer;
import com.aliat.alm.DM.DM_AR_FROM_DM_INVENTORY;
import com.aliat.alm.DM.DM_AR_FROM_DM_IT;
import com.aliat.alm.DM.DM_AR_FROM_DM_PASSIVE;
import com.aliat.alm.DM.DM_FAR_FROM_FINACIAL;
import com.aliat.alm.DM.DM_POITEM_FROM_DM_FINANCIAL;
import com.aliat.alm.DM.DM_POITEM_TO_PO_ITEM;
import com.aliat.alm.DM.DM_PO_FROM_PURCHASE_ORDER_ITEM;
import com.aliat.alm.DM.DeviceinStack2Item;
import com.aliat.alm.DM.FROEquipment2Item;
import com.aliat.alm.DM.FinancialDM;
import com.aliat.alm.DM.FireFightingSystem2Item;
import com.aliat.alm.DM.Firewall2Item;
import com.aliat.alm.DM.FixedFiberNetworkDARKFIBER2Item;
import com.aliat.alm.DM.Furniture2Item;
import com.aliat.alm.DM.GetCategoriesFromFinancialDM;
import com.aliat.alm.DM.GetCategoriesFromITDM;
import com.aliat.alm.DM.GetCategoriesFromInventoryDM;
import com.aliat.alm.DM.GetCategoriesFromPassiveDM;
import com.aliat.alm.DM.Hayat2Item;
import com.aliat.alm.DM.Hypervisor2ITEM;
import com.aliat.alm.DM.ITEquipment2Item;
import com.aliat.alm.DM.Inventory2ITEM;
import com.aliat.alm.DM.Inventory_DM;
import com.aliat.alm.DM.Layer3Switch2Item;
import com.aliat.alm.DM.Monitors2ITEM;
import com.aliat.alm.DM.Move2ITemNodeType;
import com.aliat.alm.DM.Move2Supplier;
import com.aliat.alm.DM.Move2WareHouse;
import com.aliat.alm.DM.MoveFin2Itemexclude_IT_PSV_INV;
import com.aliat.alm.DM.NOPO2ITEM;
import com.aliat.alm.DM.NetworkInfrastructure2Item;
import com.aliat.alm.DM.NotClassified2Item;
import com.aliat.alm.DM.OfficeEquipment2Item;
import com.aliat.alm.DM.PowerElements2Item;
import com.aliat.alm.DM.RealEstate2Item;
import com.aliat.alm.DM.SANSwitchSwitch2Item;
import com.aliat.alm.DM.SecuritySystem2Item;
import com.aliat.alm.DM.Storage2Item;
import com.aliat.alm.DM.SupplierDM;
import com.aliat.alm.DM.Switch2Item;
import com.aliat.alm.DM.SwitchSANSwitch2Item;
import com.aliat.alm.DM.Transmission2Item;
import com.aliat.alm.DM.UpdItemManufacturer_nodeType;
import com.aliat.alm.DM.UpdateItemCodeName;
import com.aliat.alm.DM.VASservices2Item;
import com.aliat.alm.DM.WarehouseDM;
import com.aliat.alm.DM.WindowsDesktop2Item;
import com.aliat.alm.DM.WindowsServer2Item;
import com.aliat.alm.DM.moveToALC;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.ReadXlsUsingPOI;
import com.aliat.alm.services.LoginServices;
import com.aliat.mobile.restapi.AirtimeRequest;
import com.aliat.mobile.restapi.PassFileAsPayload;
import com.aliat.mobile.restapi.getClientCredentials;

@Controller
public class DataMigrationController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static Session session = null;
	private static Transaction tx = null;

	@Autowired
	Notify notifications;	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/SampleDataMigration", method = RequestMethod.GET)
	public String SampleDataMigration(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Welcome SampleDataMigration ! The client locale is {}.", locale);

		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}else {
			session = AlmDbSession.getInstance().getSession(); 
			System.out.println("HashCode Setup: "+AlmDbSession.getInstance().hashCode());
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "SampleDataMigration";
	}

	@RequestMapping(value = "/readfinance", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> readfinance(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		FinancialDM myClass = new FinancialDM();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	@RequestMapping(value = "/migrateCategories", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> migrateCategories(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		
		GetCategoriesFromFinancialDM myClass = new GetCategoriesFromFinancialDM();
		myClass.main(null);
		
		/*GetCategoriesFromInventoryDM myClass1 = new GetCategoriesFromInventoryDM();
		myClass1.main(null);
		
		GetCategoriesFromITDM myClass2 = new GetCategoriesFromITDM();
		myClass2.main(null);
		
		GetCategoriesFromPassiveDM myClass3 = new GetCategoriesFromPassiveDM();
		myClass3.main(null);*/
		
		rtn.put("Result", "Script is Done");
		return rtn;
		
	}
	


	@RequestMapping(value = "/readinventory", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> readinventory(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		Inventory_DM myClass = new Inventory_DM();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/readwarehouse", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> readwarehouse(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		WarehouseDM myClass = new WarehouseDM();
		myClass.main(null);

		Move2WareHouse myClass1 = new Move2WareHouse();
		myClass1.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/readsupplier", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> readsupplier(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		SupplierDM myClass = new SupplierDM();
		myClass.main(null);

		Move2Supplier myClass1 = new Move2Supplier();
		myClass1.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/updateitem", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> updateitem(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		UpdItemManufacturer_nodeType myClass2 = new UpdItemManufacturer_nodeType();
		myClass2.main(null);

		UpdateItemCodeName myClass3 = new UpdateItemCodeName();
		myClass3.main(null);

		Move2ITemNodeType myClass4 = new Move2ITemNodeType();
		myClass4.main(null);

		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/runitscripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runitscripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		DMAppliance myClass1 = new DMAppliance();
		myClass1.main(null);

		DMDeviceinStack myClass2 = new DMDeviceinStack();
		myClass2.main(null);

		DMFirewall myClass3 = new DMFirewall();
		myClass3.main(null);

		DMHypervisor myClass4 = new DMHypervisor();
		myClass4.main(null);

		DMLayer3Switch myClass5 = new DMLayer3Switch();
		myClass5.main(null);

		DMMonitors myClass6 = new DMMonitors();
		myClass6.main(null);

		DMNetworkInfrastructure myClass7 = new DMNetworkInfrastructure();
		myClass7.main(null);

		DMSANSwitchSwitch myClass8 = new DMSANSwitchSwitch();
		myClass8.main(null);

		DMStorage myClass9 = new DMStorage();
		myClass9.main(null);

		DMSwitch myClass10 = new DMSwitch();
		myClass10.main(null);

		DMSwitchSANSwitch myClass11 = new DMSwitchSANSwitch();
		myClass11.main(null);

		DMWindowsDesktop myClass12 = new DMWindowsDesktop();
		myClass12.main(null);

		DMWindowsServer myClass13 = new DMWindowsServer();
		myClass13.main(null);

		rtn.put("Result", "Script is Done");
		return rtn;

	}

	@RequestMapping(value = "/cumulitscripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> cumulitscripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		DMCumulIt myClass = new DMCumulIt();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/runalmitscripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runalmitscripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		Appliance2Item myClass2 = new Appliance2Item();
		myClass2.main(null);

		DeviceinStack2Item myClass3 = new DeviceinStack2Item();
		myClass3.main(null);

		Firewall2Item myClass4 = new Firewall2Item();
		myClass4.main(null);

		Hypervisor2ITEM myClass5 = new Hypervisor2ITEM();
		myClass5.main(null);

		Layer3Switch2Item myClass6 = new Layer3Switch2Item();
		myClass6.main(null);

		Monitors2ITEM myClass7 = new Monitors2ITEM();
		myClass7.main(null);

		NetworkInfrastructure2Item myClass8 = new NetworkInfrastructure2Item();
		myClass8.main(null);

		SANSwitchSwitch2Item myClass9 = new SANSwitchSwitch2Item();
		myClass9.main(null);

		Storage2Item myClass10 = new Storage2Item();
		myClass10.main(null);

		Switch2Item myClass11 = new Switch2Item();
		myClass11.main(null);

		SwitchSANSwitch2Item myClass12 = new SwitchSANSwitch2Item();
		myClass12.main(null);

		WindowsDesktop2Item myClass13 = new WindowsDesktop2Item();
		myClass13.main(null);

		WindowsServer2Item myClass14 = new WindowsServer2Item();
		myClass14.main(null);

		rtn.put("Result", "Script is Done");
		return rtn;

	}

	@RequestMapping(value = "/runpassivescripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runpassivescripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		DMAuxiliaryInstallationMaterials myClass1 = new DMAuxiliaryInstallationMaterials();
		myClass1.main(null);

		DMCivilElements myClass2 = new DMCivilElements();
		myClass2.main(null);

		DMCloudservices myClass3 = new DMCloudservices();
		myClass3.main(null);

		DMEsim myClass4 = new DMEsim();
		myClass4.main(null);

		DMFireFightingSystem myClass5 = new DMFireFightingSystem();
		myClass5.main(null);

		DMFixedFiberNetworkDARKFIBER myClass6 = new DMFixedFiberNetworkDARKFIBER();
		myClass6.main(null);

		DMFROEquipment myClass7 = new DMFROEquipment();
		myClass7.main(null);

		DMFurniture myClass8 = new DMFurniture();
		myClass8.main(null);

		DMHayat myClass9 = new DMHayat();
		myClass9.main(null);

		DMITEquipment myClass10 = new DMITEquipment();
		myClass10.main(null);

		DMNOPO myClass11 = new DMNOPO();
		myClass11.main(null);

		DMNotClassified myClass12 = new DMNotClassified();
		myClass12.main(null);

		DMOfficeEquipment myClass13 = new DMOfficeEquipment();
		myClass13.main(null);

		DMPowerElements myClass14 = new DMPowerElements();
		myClass14.main(null);

		DMRealEstate myClass15 = new DMRealEstate();
		myClass15.main(null);

		DMSecuritySystem myClass16 = new DMSecuritySystem();
		myClass16.main(null);

		DMTransmission myClass17 = new DMTransmission();
		myClass17.main(null);

		DMVASservices myClass18 = new DMVASservices();
		myClass18.main(null);

		rtn.put("Result", "Script is Done");
		return rtn;

	}

	@RequestMapping(value = "/cumulpassivescripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> cumulpassivescripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		DMCumulPassive myClass = new DMCumulPassive();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/runalmpassivescripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runalmpassivescripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		AuxiliaryInstallation2Item myClass1 = new AuxiliaryInstallation2Item();
		myClass1.main(null);

		CivilElements2Item myClass2 = new CivilElements2Item();
		myClass2.main(null);

		Cloudservices2Item myClass3 = new Cloudservices2Item();
		myClass3.main(null);

		FireFightingSystem2Item myClass4 = new FireFightingSystem2Item();
		myClass4.main(null);

		FROEquipment2Item myClass5 = new FROEquipment2Item();
		myClass5.main(null);

		Furniture2Item myClass6 = new Furniture2Item();
		myClass6.main(null);

		Hayat2Item myClass7 = new Hayat2Item();
		myClass7.main(null);

		ITEquipment2Item myClass8 = new ITEquipment2Item();
		myClass8.main(null);

		NOPO2ITEM myClass9 = new NOPO2ITEM();
		myClass9.main(null);

		NotClassified2Item myClass10 = new NotClassified2Item();
		myClass10.main(null);

		PowerElements2Item myClass11 = new PowerElements2Item();
		myClass11.main(null);

		RealEstate2Item myClass12 = new RealEstate2Item();
		myClass12.main(null);

		SecuritySystem2Item myClass13 = new SecuritySystem2Item();
		myClass13.main(null);

		Transmission2Item myClass14 = new Transmission2Item();
		myClass14.main(null);

		VASservices2Item myClass15 = new VASservices2Item();
		myClass15.main(null);

		FixedFiberNetworkDARKFIBER2Item myClass16 = new FixedFiberNetworkDARKFIBER2Item();
		myClass16.main(null);

		OfficeEquipment2Item myClass17 = new OfficeEquipment2Item();
		myClass17.main(null);

		rtn.put("Result", "Script is Done");
		return rtn;

	}

	@RequestMapping(value = "/runinv2itemscripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runinv2itemscripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		Inventory2ITEM myClass = new Inventory2ITEM();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/runfinancialexcl2itemscripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runfinancialexcl2itemscripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		MoveFin2Itemexclude_IT_PSV_INV myClass = new MoveFin2Itemexclude_IT_PSV_INV();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/runAR2itemscripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runAR2itemscripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		DM_AR_FROM_DM_IT myClass1 = new DM_AR_FROM_DM_IT();
		myClass1.main(null);

		 DM_AR_FROM_DM_PASSIVE myClass2 = new DM_AR_FROM_DM_PASSIVE();
		myClass2.main(null);

		DM_AR_FROM_DM_INVENTORY myClass3 = new DM_AR_FROM_DM_INVENTORY();
		myClass3.main(null); 
		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/runFAR2itemscripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runFAR2itemscripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		DM_FAR_FROM_FINACIAL myClass1 = new DM_FAR_FROM_FINACIAL();
		myClass1.main(null);

		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	
	@RequestMapping(value = "/runALCscripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runALCscripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		moveToALC myClass1 = new moveToALC();
		myClass1.main(null);

		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/runPOI2itemscripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runPOI2itemscripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		DM_POITEM_FROM_DM_FINANCIAL myClass1 = new DM_POITEM_FROM_DM_FINANCIAL();
		myClass1.main(null);

		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/runcompressPOI", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runcompressPOI(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		DM_POITEM_TO_PO_ITEM myClass1 = new DM_POITEM_TO_PO_ITEM();
		myClass1.main(null);

		rtn.put("Result", "Script is Done");
		return rtn;
	}

	@RequestMapping(value = "/runPOscripts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runPOscripts(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		DM_PO_FROM_PURCHASE_ORDER_ITEM myClass1 = new DM_PO_FROM_PURCHASE_ORDER_ITEM();
		myClass1.main(null);

		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/runTOKEN", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runTOKEN(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
       System.out.println("Start runTOKEN");
		Map<String, Object> rtn = new LinkedHashMap<>();
		//AirtimeRequest myClass1 = new AirtimeRequest();
		getClientCredentials myClass1 = new getClientCredentials();
		myClass1.main(null);
		System.out.println("End runTOKEN");
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	@RequestMapping(value = "/runAIRTIME", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> runAIRTIME(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
       System.out.println("Start runAIRTIME");
		Map<String, Object> rtn = new LinkedHashMap<>();
		AirtimeRequest myClass1 = new AirtimeRequest();
		myClass1.main(null);
		System.out.println("End runAIRTIME");
		rtn.put("Result", "Script is Done");
		return rtn;
	}

}
