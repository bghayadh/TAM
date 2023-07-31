package com.aliat.alm.controller;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.FiscalYear;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class FiscalYearController {


	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;
	
	@Autowired
	Form form;
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	@SuppressWarnings("unused")
	private static String queryString = null;
	private static final Logger logger = LoggerFactory.getLogger(FiscalYearController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FiscalYearListView", method = RequestMethod.GET)
	public String FiscalYearListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {
			session = almsessions.getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				try {
					List<String[]> listFiscalYear = new ArrayList<String[]>(); // List of Array of String == Array of Array of String									
					query = session.createSQLQuery("select 1, YEAR_NAME, TO_CHAR(YEAR_START_DATE,'YYYY-MM-DD'), TO_CHAR(YEAR_END_DATE,'YYYY-MM-DD'), STATUS, IS_SHORT_YEAR, TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') from FISCAL_YEAR");
					listFiscalYear = query.list();						
					System.out.println("list fiscal year is " + mapper.writeValueAsString(listFiscalYear));					
					model.addAttribute("ListGridTable", mapper.writeValueAsString(session.createQuery("from FiscalYear").list()));
														
/*					List<FiscalYearListView> listFiscalYear = new ArrayList<FiscalYearListView>();
					query = session.createSQLQuery("select 1 as chkBox, YEAR_NAME as yearName, TO_CHAR(YEAR_START_DATE,'YYYY-MM-DD') as yearStartDate, TO_CHAR(YEAR_END_DATE,'YYYY-MM-DD') as yearEndDate, STATUS as status, IS_SHORT_YEAR as is_ShortYear, TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate from FISCAL_YEAR");					
					listFiscalYear = ((SQLQuery) query).addScalar("chkBox").addScalar("yearName").addScalar("yearStartDate")
							.addScalar("yearEndDate").addScalar("status").addScalar("is_ShortYear").addScalar("lastModifiedDate")
							.setResultTransformer(Transformers.aliasToBean(FiscalYearListView.class)).list();
*/												
					
/*					query = session.createQuery("from FiscalYear");
					model.addAttribute("ListGridTable", mapper.writeValueAsString(session.createQuery("from FiscalYear").list()));
*/					
					
				} catch (Exception e) {
					System.out.println("catch messsage is " + e.getMessage());
					e.printStackTrace();
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
		}
		return "FiscalYearListView";
	}

	@RequestMapping(value = "/FiscalYearFormView", method = RequestMethod.GET)
	public String FiscalYearFormView(Model model, HttpServletRequest request, HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		} else {

			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			String fiscalYearID = request.getParameter("fY_ID"), navAction = "2";
			System.out.println("fiscalYearID is " +fiscalYearID);
			FiscalYear fY;
			session = almsessions.getSession();
			notifications.headerNotifications(session, model);
			String result[] = new String[4];
			int SelectedIndex = 0;

			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {

					navAction = request.getParameter("NavAction");

					// to open fiscal year when click on ADD from fiscal year List View
					if (fiscalYearID == null) {
						model.addAttribute("screationDate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());
						model.addAttribute("slastModifieddate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());
						model.addAttribute("ListComp", "addNew");
						model.addAttribute("SelectedIndex", "addNew");
						model.addAttribute("supplierCount", "addNew");
						model.addAttribute("docStatus", "addNew");

						return "FiscalYearFormView";
					} else { // To load existed fiscal year from List View or when clicking on Next / Previous

						result = form.NavigationNP(session, "FISCAL_YEAR", "FY_ID", fiscalYearID,
								"LAST_MODIFIED_DATE", navAction);

						SelectedIndex = Integer.parseInt(result[1]);
						fiscalYearID = result[2];

						model.addAttribute("SelectedIndex", SelectedIndex);
						model.addAttribute("fiscalYearCount", Integer.parseInt(result[0]));
						fY = (FiscalYear) session.get(FiscalYear.class, fiscalYearID);

						model.addAttribute("yearName", fY.getYearName());
						
						model.addAttribute("yearStartDate", fY.getYearStartDate());
						model.addAttribute("yearEndDate", fY.getYearEndDate());
						model.addAttribute("is_ShortYear", fY.getIs_ShortYear());
						model.addAttribute("status", fY.getStatus());
						model.addAttribute("createdDate", formatter.format(fY.getCreatedDate()).toString());
						model.addAttribute("lastModifiedDate", formatter.format(fY.getLastModifiedDate()).toString()); 

					}
				} catch (Exception e) {
					model.addAttribute("fiscalYearID", "");
					model.addAttribute("creationDate", "");
					model.addAttribute("lastModifieddate", "");
					model.addAttribute("yearName", "");
					model.addAttribute("yearStartDate", "");
					model.addAttribute("yearEndDate", "");
					model.addAttribute("is_ShortYear", "");
					model.addAttribute("status", "");
					logger.info("Error in retreiving the data for Supplier form view with a message :" + e);
					e.printStackTrace();
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}

				}
			}
			return "FiscalYearFormView";
		}
	}

}
