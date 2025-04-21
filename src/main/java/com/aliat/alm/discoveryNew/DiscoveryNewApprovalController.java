package com.aliat.alm.discoveryNew;


import com.aliat.alm.models.ArNode;
import com.aliat.alm.models.ArPartNumber;
import com.aliat.alm.models.ArSerialNumber;
import com.aliat.alm.models.ArSite;
import com.aliat.alm.models.AssetRegistry;
import com.aliat.alm.models.DNIFormView;
import com.aliat.alm.models.DiscoveryNewItemNode;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.models.FarNode;
import com.aliat.alm.models.FarPartNumber;
import com.aliat.alm.models.FarSerialNumber;
import com.aliat.alm.models.FarSite;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Arrays;
import org.hibernate.type.FloatType;
import org.hibernate.type.StringType;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.Permissions;
import com.aliat.alm.models.DiscoveryNew;
import com.aliat.alm.models.DiscoveryNewItem;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DiscoveryNewApprovalController {
	@Autowired
	Form form;

	
	
	@Autowired
	Notify notifications;
	
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static final Logger logger = LoggerFactory.getLogger(DiscoveryController.class);
	
    Calendar calendar = new GregorianCalendar();
	int year = calendar.get(Calendar.YEAR);
	
	@Autowired
	Permissions permissions;
	@RequestMapping(value = "/DiscoveryNewApprovalListView", method = RequestMethod.GET)
	public String DiscoveryNewListView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		
		session = AlmDbSession.getInstance().getSession();
		
		if (session != null && session.isOpen()) {
			notifications.headerNotifications(session, model);
			
			try {
				String str = "select a.DN_ID as dnID,a.DN_ID as dnewID,CAST((a.TOTAL_AMOUNT) AS VARCHAR(10)) as dnTotalAmount,"
						+ "CAST((a.TOTAL_QTY) AS VARCHAR(10)) as dnTotalQty,a.STATUS as dnStatus,"
						+ "(Select COUNT(*) from DISCOVERY_NEW_ITEM WHERE (APPROVAL='Project Manager' OR APPROVAL ='Operation Manager' OR APPROVAL IS NULL)"
						+ " AND DN_ID=a.DN_ID) as pendingPM,"
						+ "(Select COUNT(*) from DISCOVERY_NEW_ITEM WHERE (APPROVAL='Asset Unit') AND DN_ID=a.DN_ID) as pendingAM,"
						+ "(Select COUNT(*) from DISCOVERY_NEW_ITEM WHERE (APPROVAL='Finance') AND DN_ID=a.DN_ID) as pendingFM,"
						+ "TO_CHAR(LAST_MODIF_DATE,'YYYY-MM-DD HH24:MI:SS') as dnlastmodifDate "
						+ "from DISCOVERY_NEW a order by LAST_MODIF_DATE DESC";
				
		model.addAttribute("ListGridTable", mapper.writeValueAsString(session.createNativeQuery(str).list()));		
	
				permissions.setPerms(model, permissions.getUserPermsWithSession(session, request), "Discovery New Project Manger",
						"Tree");		
				String readProjectM = ((Integer) model.asMap().get("readTree")).toString();
				model.addAttribute("readProjectM", readProjectM);
				
				permissions.setPerms(model, permissions.getUserPermsWithSession(session, request), "Discovery New Asset Manger",
						"Tree");		
				String readAssetM = ((Integer) model.asMap().get("readTree")).toString();
				model.addAttribute("readAssetM", readAssetM);
				
				permissions.setPerms(model, permissions.getUserPermsWithSession(session, request), "Discovery New Finance Manger",
						"Tree");		
				String readFinanceM = ((Integer) model.asMap().get("readTree")).toString();
				model.addAttribute("readFinanceM", readFinanceM);
				
                System.out.println(readProjectM+""+readAssetM+""+readFinanceM);
						
		} catch (Exception e) {
			logger.info("Error at DiscoveryNew ListView with a message: "+ e);
			e.printStackTrace();
			model.addAttribute("ListGridTable","");
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
			
		}

		return "DiscoveryNewApprovalListView";
	}
	
	
}
