package com.aliat.alm.controller;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.Region;
import com.aliat.alm.models.RegionBorder;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RegionController {
	
	private static final String Latitude = "Latitude";
	private static final String Longitude = "Longitude";
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;
	private static final Logger logger = Logger.getLogger(RegionController.class.getName());

	@Autowired
	Form form;
	
	@Autowired
	Notify notification;
	
	@Autowired
	ALMSessions almsessions;
	
	@RequestMapping(value = "/RegionListView", method = RequestMethod.GET)
	public String RegionListView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		
 		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} 
 		
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			
			tx = session.beginTransaction();
			notification.headerNotifications(session, model);	
			
	try {

		    query = session.createQuery("SELECT t.regionId AS ID, t.regionId, t.regionName, t.regionCode,TO_CHAR(t.creationDate, 'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(t.lastModifieddate, 'YYYY-MM-DD HH24:MI:SS') from Region t ORDER BY lastModifieddate DESC");
		    model.addAttribute("ListGridTable", mapper.writeValueAsString(query.list()));
		
		    
	}catch(Exception e) {
		sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		exceptionAsString = sw.toString();
		logger.finest("Error in RegionListView due to \n "+ exceptionAsString);
		logger.info("Error in RegionListView due to \n "+ exceptionAsString);
		} finally {
	      if (session != null && session.isOpen()) {
		         tx.commit();
		         session.close();
		      }
	        }
		}
		return "RegionListView";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FilteredRegionListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredRegionListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;	
		}
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);

			
			try {
				String startdate, enddate,  regionid, regionname,regioncode;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				regionid = request.getParameter("regionid");
				regionname = request.getParameter("regionname");
				regioncode = request.getParameter("regioncode");
			
				List<String> listRegion = new ArrayList<String>();
				String str = " select 1 as chkBox,REGION_ID as regionId,REGION_NAME as regionName,REGION_CODE as regionCode,TO_CHAR(CREATION_DATE,'YYYY-MM-DD HH24:MI:SS') as creationDate,TO_CHAR(LAST_MODIFICATION_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifieddate  from REGION";

				if (startdate != null && enddate != null) {
					str = str + " where CREATION_DATE between TO_DATE('" + startdate + "','YYYY-MM-DD') and TO_DATE('"
							+ enddate + "','YYYY-MM-DD')";
				}

				if (regionid != null && !regionid.equalsIgnoreCase("")) {

					str = str + " and upper(REGION_ID) LIKE upper('%" + regionid + "%')";
				}

				if (regionname != null && !regionname.equalsIgnoreCase("")) {

					str = str + " and upper(REGION_NAME) LIKE upper('%" + regionname + "%')";
				}
				if (regioncode != null && !regioncode.equalsIgnoreCase("")) {

					str = str + " and upper(REGION_CODE) LIKE upper('%" + regioncode + "%')";
				}

				
				str = str + " ORDER BY LAST_MODIFICATION_DATE DESC ";

				
				Query query = session.createSQLQuery(str);

				listRegion = query.list();
			
				rtn.put("listRegion",listRegion);
			
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in showing the filtered Region list view due to \n "+ exceptionAsString);
				logger.info("Error in showing the filtered Region list view due to \n "+ exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;
	}

	@RequestMapping(value = "/RegionFormView", method = RequestMethod.GET)
	public String RegionFormView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		
 		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} 
		
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			String regionID,navAction = "2";
	        String result [] =new String[3];
	        int SelectedIndex = 0;

			Region region = new Region();
			session = almsessions.getSession();

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notification.headerNotifications(session, model);	

		try {

			regionID = request.getParameter("RegionID");			
			navAction= request.getParameter("NavAction");

			query = session.createSQLQuery("SELECT REGION_ID FROM REGION");
			
		
		if(regionID==null) {
						
			model.addAttribute("creationDate", formatter.format(new Timestamp(System.currentTimeMillis())).toString());
			model.addAttribute("lastModifiedDate", formatter.format( new Timestamp(System.currentTimeMillis())).toString());
			model.addAttribute("listRegionBorder", "addNew");	
			model.addAttribute("docStatus", "addNew");	
			model.addAttribute("SelectedIndex", "addNew");
			model.addAttribute("AreasCount", "addNew");

			return "RegionFormView";
		}
		//NavigateNP: Navigate for Next or Previous 

		result = form.NavigationNP(session,"Region","REGION_ID",regionID,"LAST_MODIFICATION_DATE",navAction);		
		SelectedIndex= Integer.parseInt(result[1]);
		regionID=result[2];

		region = (Region) session.get(Region.class, regionID);// used session.get instead of appConfig.find

		if(region!=null) {
			query =  session.createQuery("from RegionBorder where regionId like :param1 ORDER BY sequence + 0 ASC");
			query.setParameter( "param1", regionID); // edited (remove appConfig)
			model.addAttribute("listRegionBorder", mapper.writeValueAsString(query.list()));
		}
			
	}catch(Exception e) {
		sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		exceptionAsString = sw.toString();
		logger.finest("Error in RegionFormView due to \n "+ exceptionAsString);
		logger.info("Error in RegionFormView due to \n "+ exceptionAsString);
	}
		 finally {

		      if (session != null && session.isOpen()) {
		         tx.commit();
		         session.close();

		      }

		 }
			}
    		model.addAttribute("creationDate", formatter.format(region.getCreationDate()).toString());
    		model.addAttribute("lastModifiedDate", formatter.format( region.getLastModifieddate()).toString());
			model.addAttribute("RegionId", region.getRegionId());
			model.addAttribute("regionName", region.getRegionName());
			model.addAttribute("regionCode", region.getRegionCode());
			model.addAttribute("ordStatus", region.getStatus());
			model.addAttribute("AreasCount", Integer.parseInt(result[0]));
			model.addAttribute("SelectedIndex", SelectedIndex);

		return "RegionFormView";
	}	

	
	@RequestMapping(value = "/RegionDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> RegionDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
			
		Map<String, Object> rtn = new LinkedHashMap<>();
		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {			
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		
		String [] idList = null;
		
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				idList = request.getParameterValues("RegionId[]");	  	
				if ( idList != null) {
					
					for(int i=0;i<idList.length;i++) {
						
						query = session.createSQLQuery("delete REGION_BORDER where REGION_ID='"+idList[i]+"'");
						query.executeUpdate();
						
						query = session.createSQLQuery("delete REGION where REGION_ID='"+idList[i]+"'");
						query.executeUpdate();
					}
					
				}
			}catch(Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in RegionDelete due to \n "+ exceptionAsString);
				logger.info("Error in RegionDelete due to \n "+ exceptionAsString);
			}
			finally {
				if (session != null && session.isOpen()) {
		        tx.commit();
		        session.close();
				}
	        }
		}
		rtn.put("BassamTest", "DeleteDone");
		return rtn;
	}
	
	
	@RequestMapping(value = "/RegionFormSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> RegionFormSave(Locale locale, Model model, HttpServletRequest request,
		@ModelAttribute ItemParameters itemParameters, HttpServletResponse response){
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		
		String regionBorderID, regionName, regionCode, streched, latitude = "", longitude="", regionId="";
		Calendar calendar = new GregorianCalendar();
		Region region = new Region();  
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Timestamp CreationDate;
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
/*			
///////////////////////////////////////////////////////// SEND EMAIL BUTTON  //////////////////////////////////////////////////////////
			String	email=request.getParameter("email");
			String	emailTo=request.getParameter("emailTo");
			String	password=request.getParameter("password");
			String	message=request.getParameter("message");
			String	subject=request.getParameter("subject");
			String	ccmail=request.getParameter("ccmail");


if(StringUtils.equalsIgnoreCase(request.getParameter("email"), "") && StringUtils.equalsIgnoreCase(request.getParameter("password"), "") && StringUtils.equalsIgnoreCase(request.getParameter("emailTo"), "") && StringUtils.equalsIgnoreCase(request.getParameter("message"), "")  && StringUtils.equalsIgnoreCase(request.getParameter("subject"), "") && StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), "")  ) 
{
System.out.println("NO EMAIL TO SEND!");

}
else if(StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), ""))
{

JavaMailUtil.SendEmails(email,password,emailTo,subject,message);

} 
else {
	
JavaMailUtil.SendccEmails(email,password,emailTo,ccmail,subject,message);

} 

///////////////////////////////////////////// END OF SEND EMAIL BUTTON  //////////////////////////////////////////////////////////
 */

		if (StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNew"))
		{
			synchronized (this) {						
				//regionId= "REGION_"+calendar.get(Calendar.YEAR)+"_" +appConfig.getSequenceNbr(33);
				regionId= "REGION_"+calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(session.createSQLQuery("SELECT REGION FROM SEQ_TABLE").uniqueResult().toString());	
				query = session.createSQLQuery("UPDATE SEQ_TABLE SET REGION = REGION + 1 ");
				query.executeUpdate();
				session.createSQLQuery("commit").executeUpdate();
				}
		} else  {
			
			regionId = request.getParameter("RegionID");
			
		}
				
		// delete 1 or more coord(s) for this region			
		if(request.getParameterValues("slctDelRegion[]")!=null) {
			
	    	query = session.createQuery("delete RegionBorder t where t.Id IN (:param1) ");
	        query.setParameterList("param1", request.getParameterValues("slctDelRegion[]"));
	        query.executeUpdate();
						
		}
		
		if (request.getParameter("creationDate") == "") {
			
			CreationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
			
		} else {
			
			CreationDate = new Timestamp((formatter.parse(request.getParameter("creationDate"))).getTime());
			
		}
		
		regionName=request.getParameter("RegionName");
		regionCode=request.getParameter("RegionCode");
		region.setRegionId(regionId);
		region.setRegionName(regionName);
		region.setRegionCode(regionCode);		
		region.setCreationDate(CreationDate);
		region.setLastModifieddate(new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));
		region.setStatus(request.getParameter("status"));
	    session.saveOrUpdate(region);
	    
	    // End of saving Region Form without Region Border Boq.
	    
	    streched = request.getParameter("streched")+"";
		
		//when polygon or Polyline streched
		if(streched.equals("1")) {
			query = session.createQuery("delete RegionBorder t where t.regionId IN (:param1) ");
	        query.setParameter("param1", regionId);
	        query.executeUpdate();

		}
		//No Added rows
		for (int i = 0; i <itemParameters.getDictParameterRegion().size(); i++) {
				
			// Initialized here cz of primary key problem
			RegionBorder regionBorder = new RegionBorder();  
			if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameterRegion().get(i).get(Latitude), "")) {
				latitude="0"; // if wrong, waiting a request/modification to be changed 
			} else {
				latitude = itemParameters.getDictParameterRegion().get(i).get(Latitude);
			}
				 
			if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameterRegion().get(i).get(Longitude), "")) {
				longitude="0"; // if wrong, waiting a request/modification to be changed
			} else {
				longitude = itemParameters.getDictParameterRegion().get(i).get(Longitude);
			}
				 
			if ( StringUtils.equalsIgnoreCase(itemParameters.getDictParameterRegion().get(i).get("RegionBorderID") , null)) {
				synchronized (this) {						
					//regionBorderID= "REGION_BORDER_ID_"+calendar.get(Calendar.YEAR)+"_" +appConfig.getSequenceNbr(68);
					regionBorderID= "REGION_BORDER_ID_"+calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(session.createSQLQuery("SELECT REGION_BORDER FROM SEQ_TABLE").uniqueResult().toString());	
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET REGION_BORDER = REGION_BORDER + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
					}
			}
			else {
				regionBorderID = itemParameters.getDictParameterRegion().get(i).get("RegionBorderID");
			}
				regionBorder.setId(regionBorderID);
				regionBorder.setSequence(itemParameters.getDictParameterRegion().get(i).get("sequence"));
				regionBorder.setRegionId(regionId);
				regionBorder.setLat(latitude);
				regionBorder.setLng(longitude);
				session.saveOrUpdate(regionBorder);
		}
			rtn.put("REGIONID",regionId);
		
		} catch(Exception e) {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in RegionFormSave due to \n "+ exceptionAsString);
			logger.info("Error in RegionFormSave due to \n "+ exceptionAsString);		} finally {
	      if (session != null && session.isOpen()) {
	    	  tx.commit();
		      session.close();
	      }
		}
	}
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllRegions", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllRegions(Locale locale, Model model, HttpServletRequest request){
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		List<Region> listRegions = new ArrayList<Region>();

		String Region = "%" + request.getParameter("regionID") + "%";
		
		session = almsessions.getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {				
					query = session.createSQLQuery("SELECT REGION_ID,REGION_NAME,REGION_CODE FROM REGION where UPPER(REGION_NAME)like UPPER(:param1) OR UPPER(REGION_CODE)like UPPER(:param1) OR UPPER(REGION_ID)like UPPER(:param1) ORDER BY LAST_MODIFICATION_DATE DESC");
					query.setString("param1", Region);
					listRegions = query.list();
		
			}catch(Exception e) {
				
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GetAllRegions due to \n "+ exceptionAsString);
				logger.info("Error in GetAllRegions due to \n "+ exceptionAsString);				
			}
			
		    finally {
		      if (session != null && session.isOpen()) {
		    	  
		         tx.commit();
		         session.close();
		         
		      }
		    }
		}
		
		rtn.put("listRegions", listRegions);
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSelectedRegion", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSelectedRegion(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			rtn.put("Login", "redirect:/");
			return rtn;
		}
		List<RegionBorder> listRegionBorder = null;
		String RegionId;
		session = almsessions.getSession();

		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				RegionId = request.getParameter("regionID");
				query = session.createSQLQuery(
						"SELECT a.REGION_ID,b.REGION_NAME,b.REGION_CODE, LISTAGG(a.LATITUDE || ',' || a.LONGTITUDE, ':') WITHIN GROUP (ORDER BY a.SEQ_SORTING + 0 ASC) AS borders"
								+ " FROM REGION_BORDER a Inner Join REGION b on  a.REGION_ID=b.REGION_ID"
								+ " WHERE (a.REGION_ID LIKE UPPER('%" + RegionId + "%') OR b.REGION_NAME LIKE UPPER('%"
								+ RegionId + "%') OR b.REGION_CODE LIKE UPPER('%" + RegionId + "%'))"
								+ " GROUP BY a.REGION_ID,b.REGION_NAME,b.REGION_CODE");

				listRegionBorder = query.list();
				rtn.put("listRegions", listRegionBorder);
			}catch(Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getSelectedRegion due to \n "+ exceptionAsString);
				logger.info("Error in getSelectedRegion due to \n "+ exceptionAsString);
			}
			
			
			
			finally {

				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}

			}

		}

		return rtn;
	}	
   
}