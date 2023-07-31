package com.aliat.alm.controller;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ManualMethodController {

	@Autowired
	ALMSessions almsessions;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/ManualMethod", method = RequestMethod.GET)
	public String ManualMethod(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Welcome home! The client locale is {}.", locale);
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		} else {
			System.out.println("Mr. Bassam Eid.");
			return "ManualMethod";
		}
	}
	
	
	@RequestMapping(value = "/CalculateDepreciation", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> CalculateDepreciation(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		List<FixedAssetRegistry> FAR = new ArrayList<FixedAssetRegistry>();
		
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		
		Query q = session.createQuery("SELECT farID, farcreatedDate, iniCost, usefulLifeMon FROM FixedAssetRegistry");
		FAR = q.list();
		
		Iterator itr = FAR.iterator();
		while(itr.hasNext()){
			   Object[] obj = (Object[]) itr.next();
			   
			   String farID = String.valueOf(obj[0]);
			   String farcreatedDate = String.valueOf(obj[1]); 
			   Float iniCost = Float.parseFloat(String.valueOf(obj[2]));
			   Float usefulLifeMon = Float.parseFloat(String.valueOf(obj[3]));
			  
			   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
			   TemporalAccessor temporalAccessor = formatter.parse(farcreatedDate);
			   LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
			   ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
			   Instant lastDate = Instant.from(zonedDateTime);
			   
			   
			   
			   
			   long diffInSeconds = Duration.between(lastDate,
                       Instant.now()).getSeconds();
			   
			   long diffInDays = diffInSeconds / 86400;
			   
			   Float usefulLifeDays = usefulLifeMon * 30;
			   
			   Float dailyDepreciation = iniCost / usefulLifeDays;
			   
			   Float accumDepreciation = diffInDays * dailyDepreciation;
			   
			   Float netCost = iniCost - accumDepreciation;
			   
			   Float depPercentage = accumDepreciation / 100;
			   
			   Float monthlyDepreciation = dailyDepreciation * 30;
			   
			   // Update Depreciation In Fixed Asset Registry
			   Configuration cfg2 = new Configuration().configure();
			   StandardServiceRegistryBuilder builder2 = new StandardServiceRegistryBuilder().applySettings(cfg2.getProperties());
			   SessionFactory sf2 = cfg2.buildSessionFactory(builder2.build());
			   Session session2 = sf2.openSession();
			   Transaction tx2 = session2.beginTransaction();
				
			   Query q2 = session2.createQuery("UPDATE FixedAssetRegistry SET netCost =:p_netCost, deprPer =:p_deprPer,"
			   		+ "dailyPercent =:p_dailyPercent, accumPer =:p_accumPer WHERE farID =:p_farID");
			   
			   q2.setFloat("p_netCost", netCost);
			   q2.setFloat("p_deprPer", depPercentage);
			   q2.setFloat("p_dailyPercent", dailyDepreciation);
			   q2.setFloat("p_accumPer", depPercentage);
			   q2.setParameter("p_farID", farID);
			   q2.executeUpdate();

			   tx2.commit();
			   session2.close();
			   
			}

		
		tx.commit();
		session.close();

		ObjectMapper mapper = new ObjectMapper();
//		model.addAttribute("ListItemCategory", mapper.writeValueAsString(listItem));
//		System.out.println("end good " + mapper.writeValueAsString(listItem));
		rtn.put("FAR", FAR);

		return rtn;
	}

}
