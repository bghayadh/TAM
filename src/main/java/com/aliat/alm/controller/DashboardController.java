package com.aliat.alm.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.common.RptDbSession;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DashboardController {
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	private static String dbDateFrom,dbDateTo;
	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@Autowired
	Notify notification;
	
	
	private static Session sessionALM = null;
	private static Session sessionRPT = null;
	private static Transaction tx = null;
	private static Transaction txRPT = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	
// Loading of dashboard
	@RequestMapping(value = "/Dashboard", method = RequestMethod.GET)
	public String Dashboard(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		else {
			
			try {
				
				sessionALM = AlmDbSession.getInstance().getSession();
				System.out.println("HashCode Dashboard: "+AlmDbSession.getInstance().hashCode());
				sessionRPT=RptDbSession.getInstance().getSession();
			
				if (sessionALM != null && sessionRPT.isOpen()&&sessionRPT != null && sessionALM.isOpen()) {
					tx = sessionALM.beginTransaction();
					txRPT=sessionRPT.beginTransaction();
					notification.headerNotifications(sessionALM, model);
					LoadData(sessionALM,sessionRPT,model);
					tx.commit();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				if(sessionALM.isOpen() && sessionALM != null) {
					sessionALM.close();
				}
			}
			
		}
		
		return "Dashboard";
	}

	
	public void LoadData(Session sessionALM,Session sessionRPT, Model model) {
	// Initialize Variables
	    List<Integer> technologyCount = new ArrayList<Integer>();
	    float poPercent = 0;
		float prPercent = 0;
		float poCount=0;
		float poCompleteCount=0;
		float prCount=0;
		float prCompleteCount=0;
	     mapper = new ObjectMapper();
	    // defaultPieRev:for the piechart of services per Revenue
	    // revenueperregions:Revenue Per Regions 
	    //Total Revenue:Revenue Total for each Technology
		
		
		 List<Float> serviceTechnology = new ArrayList<Float>();
	
	
		
	try {
		
		technologyCount.add(Integer.parseInt( sessionALM.createNativeQuery(
				"select coalesce (count(*),0) from WAREHOUSE where   TECH_2G = '1' and TECH_4G = '0' and TECH_3G = '0'").uniqueResult().toString()));
		technologyCount.add(Integer.parseInt(sessionALM.createNativeQuery(
				"select coalesce (count(*),0) from WAREHOUSE where TECH_4G = '0' and TECH_3G = '1' and TECH_2G = '0'").uniqueResult().toString()));
		technologyCount.add(Integer.parseInt( sessionALM.createNativeQuery(
				"select coalesce (count(*),0) from WAREHOUSE where TECH_4G = '1' and TECH_3G = '0' and TECH_2G = '0'").uniqueResult().toString()));
		technologyCount.add(Integer.parseInt(sessionALM.createNativeQuery(
				"select coalesce (count(*),0) from WAREHOUSE where  TECH_3G = '1' and TECH_2G = '1'and TECH_4G = '0'").uniqueResult().toString()));
		technologyCount.add(Integer.parseInt(sessionALM.createNativeQuery(
				"select coalesce (count(*),0) from WAREHOUSE where TECH_4G = '1' and TECH_3G = '1' and TECH_2G = '1'").uniqueResult().toString()));
		technologyCount.add(Integer.parseInt(sessionALM.createNativeQuery(
				"select coalesce (count(*),0) from WAREHOUSE where TECH_4G = '0' and TECH_3G = '0' and TECH_2G = '0'").uniqueResult().toString()));
		
		model.addAttribute("TechnologyCount", technologyCount);
		 poCount = Float
					.parseFloat(sessionALM.createNativeQuery("select count(*) from Purchase_Order").uniqueResult().toString());
			 poCompleteCount = Float.parseFloat(sessionALM
					.createNativeQuery("select count(*) from Purchase_Order where ( STATUS ='completed' OR STATUS ='closed' )")
					.uniqueResult().toString());

			 prCount = Float
					.parseFloat(sessionALM.createNativeQuery("select count(*) from Purchase_Request").uniqueResult().toString());
		prCompleteCount = Float.parseFloat(sessionALM
					.createNativeQuery(
							"select count(*) from Purchase_Request where ( STATUS ='completed' OR STATUS ='closed' )")
					.uniqueResult().toString());
			// **************************** easy pie chart ****************************
						if (poCount == 0) {
							poPercent = 0;
						} else {
							poPercent = ((poCompleteCount / poCount) * 100);
						}

						if (prCount == 0) {
							prPercent = 0;
						} else {
							prPercent = ((prCompleteCount / prCount) * 100);
						}
						
						model.addAttribute("POCount", poCount);
						model.addAttribute("POCompleteCount", poCompleteCount);
						model.addAttribute("POPercent", poPercent);
						model.addAttribute("PRCount", prCount);
						model.addAttribute("PRCompleteCount", prCompleteCount);
						model.addAttribute("PRPercent", prPercent);
						query = sessionRPT.createNativeQuery("select coalesce(sum(voice_revenue),0) as voice_revenue,  coalesce(sum(sms_revenue),0) as sms_revenue, coalesce(sum(data_revenue),0) as data_revenue,  coalesce(sum(vas_revenue),0) as vas_revenue,  coalesce((sum(voice_revenue)+sum(sms_revenue)+sum(data_revenue)+sum(vas_revenue)),0) as tot_revenue from prepaid_payg_revenue ");
						model.addAttribute("pieRevenue", mapper.writeValueAsString(query.list()));
						
						
						serviceTechnology.add(Float.parseFloat(sessionRPT.createNativeQuery(
								"select coalesce(sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue),0) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE   a.combination_technology ='2G' ").uniqueResult().toString()));
							 serviceTechnology.add(Float.parseFloat(sessionRPT.createNativeQuery(
								"select coalesce(sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue),0) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE   a.combination_technology ='2G_3G' ").uniqueResult().toString()));
							 serviceTechnology.add(Float.parseFloat(sessionRPT.createNativeQuery(
								"select coalesce(sum(b.sms_revenue+b.data_revenue+b.voice_revenue+b.vas_revenue),0) as Total_REVENUE from alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE   a.combination_technology ='2G_3G_4G'")
						.uniqueResult().toString()));
		
							 model.addAttribute("RevenueTechnology", serviceTechnology);	 
							  query =  sessionRPT.createNativeQuery(
										"select distinct region_name ,sum(sms_revenue+data_revenue+voice_revenue+vas_revenue) as Total_REVENUE FROM  \r\n" + 
										"			        		PREPAID_PAYG_REVENUE  where region_name!='null' and region_name IS NOT NULL  group by REGION_NAME"
										);
							  model.addAttribute("RegionsRevenues",mapper.writeValueAsString(query.list()) );
							// Sites Per Regions
							 query = sessionALM.createNativeQuery(
										"select distinct region_name ,coalesce (count(ware_name),0) from warehouse where region_name IS NOT NULL and region_name!='null' group by region_name order by region_name asc"
										);
								//System.out.println("*//*/*Service is "+mapper.writeValueAsString(SitesPerRegions));
							 model.addAttribute("RegionsSites",mapper.writeValueAsString(query.list()) );
								query = sessionRPT.createNativeQuery("select a.combination_technology, SUM( b.VOICE_REVENUE ),SUM( b.SMS_REVENUE ),SUM( b.DATA_REVENUE ),SUM( b.VAS_REVENUE ) FROM  alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G')and b.REVENUE_DATE >= ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1)\r\n" + 
										"AND b.REVENUE_DATE <=  TRUNC(SYSDATE) group by a.combination_technology order by a.combination_technology asc ");			 
							model.addAttribute("TotalRevenue",mapper.writeValueAsString(query.list()));
                             query = sessionRPT.createNativeQuery("SELECT longitude,latitude, sum(sms_revenue+data_revenue+voice_revenue+vas_revenue),site_name, site_id FROM prepaid_payg_revenue where site_name!='null' GROUP BY site_name,longitude,latitude,site_id ORDER BY sum(sms_revenue+data_revenue+voice_revenue+vas_revenue)DESC FETCH NEXT 10 ROWS ONLY");
                             model.addAttribute("Top10SitesMap",mapper.writeValueAsString(query.list()));
							query = sessionRPT.createNativeQuery("SELECT longitude,latitude,sum(sms_revenue+data_revenue+voice_revenue+vas_revenue),site_name,site_id  FROM prepaid_payg_revenue where site_name!='null' GROUP BY site_name,longitude,latitude,site_id ORDER BY sum(sms_revenue+data_revenue+voice_revenue+vas_revenue)ASC FETCH NEXT 10 ROWS ONLY");
							model.addAttribute("Less10SitesMap",mapper.writeValueAsString(query.list()));
		
		
		//model.addAttribute("pieRevenue", mapper.writeValueAsString(defaultPieRev));
		
		
		
		
		
		
	}
	catch (Exception e) {		
	logger.info("Error in Retreiving data from tables for the charts while loading", e);
      } 
	
	 finally {


		 if (sessionRPT != null && sessionRPT.isOpen()) {

			 txRPT.commit();
			 sessionRPT.close();
		 }

		 }
	
	}
	
	// Submit Function
	
	@RequestMapping(value = "/SubmitLineChart", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> SubmitLineChart(HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("GetChartsDetails");
		Map<String, Object> map = new HashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", "redirect:/");
			return map;
		}
		// ******************* date *******************
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

			//sSystem.out.println(fromDate +" select "+toDate);
			
			String[] dateSelect = fromDate.split(" ");
			String[] dateSelectto = toDate.split(" ");
			dbDateFrom = dateSelect[0];
			dbDateTo = dateSelectto[0];

	  
	 sessionRPT = RptDbSession.getInstance().getSession();
		
		
		if(sessionRPT!=null) {
        	try {
        		txRPT=sessionRPT.beginTransaction();
				query = sessionRPT.createNativeQuery("select a.combination_technology, SUM( b.VOICE_REVENUE ),SUM( b.SMS_REVENUE ),SUM( b.DATA_REVENUE ),SUM( b.VAS_REVENUE ) FROM  alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE  a.combination_technology In ('2G_3G_4G' ,'2G_3G' ,'2G')and trunc(b.REVENUE_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(b.REVENUE_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY')" + 
						"group by a.combination_technology order by a.combination_technology asc ");
				
				map.put("totalRevenueSubmit", mapper.writeValueAsString(query.list()));
			}
			catch (Exception e) {
				
				
					
			logger.info("Error in Retreiving data From submit", e);
			
		      } 
			
			 finally {


				 if (sessionRPT != null && sessionRPT.isOpen()) {

					 txRPT.commit();
					 sessionRPT.close();
				 }

				 }
							
		}
		
			return map;

	}
	

}
