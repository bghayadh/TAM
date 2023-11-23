package com.aliat.alm.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.Cluster;
import com.aliat.alm.models.ClusterFinance;
import com.aliat.alm.models.ClusterFinanceBOQ;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ClusterController {

	private static final String ID = "Id";
	private static final String START_DATE = "StartDate";
	private static final String END_DATE = "EndDate";
	private static final String SITES2G = "2GSites";
	private static final String PROFIT2G = "ProfitLoss2G";
	private static final String SITES2G3G = "2G3GSites";
	private static final String PROFIT2G3G = "ProfitLoss2G3G";
	private static final String SITES2G3G4G = "2G3G4GSites";
	private static final String PROFIT2G3G4G = "ProfitLoss2G3G4G";
	private static final String TOTALSITES = "TotalSites";
	private static final String TOTALSUM = "SumProfitLoss";
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	private static Query query = null;

	@Autowired
	Form form;

	@Autowired
	Notify notification;

	@RequestMapping(value = "/ClusterListView", method = RequestMethod.GET)
	public String ClusterListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			return "redirect:/";
		}

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			notification.headerNotifications(session, model);
			try {
				query = session.createQuery(
						"select 1,t.id, t.name,TO_CHAR(t.creationDate, 'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(t.lastModifieddate, 'YYYY-MM-DD HH24:MI:SS') from Cluster t");
				model.addAttribute("ListGridTable", mapper.writeValueAsString(query.list()));
			} catch (Exception e) {

				System.out.println("catch messsage is " + e.getMessage());

			} finally {

				if (session != null && session.isOpen()) {
					session.close();
				}

			}

		}

		return "ClusterListView";
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/ClusterFormView", method = RequestMethod.GET)
	public String ClusterFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		String clusterId, navAction = "2";
		String result[] = new String[4];
		int SelectedIndex = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notification.headerNotifications(session, model);
			try {

				if (request.getParameter("clusterID") == null) {

					model.addAttribute("creationDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("lastModifiedDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());

					model.addAttribute("docStatus", "addNew");
					model.addAttribute("ListClusterFinance", "addNew");
					model.addAttribute("SelectedIndex", "addNew");
					model.addAttribute("ClustersCount", "addNew");

					return "ClusterFormView";
				}

				navAction = request.getParameter("NavAction");
				clusterId = request.getParameter("clusterID");

				result = form.NavigationNP(session, "CLUSTERS", "ID", clusterId, "LAST_MODIFICATION_DATE", navAction);

				SelectedIndex = Integer.parseInt(result[1]);
				clusterId = result[2];

				Cluster cluster = (Cluster) session.get(Cluster.class, clusterId);

				model.addAttribute("SelectedIndex", SelectedIndex);
				model.addAttribute("ClustersCount", Integer.parseInt(result[0]));

				if (cluster != null) {
					if (cluster.getCreationDate() == null) {

						model.addAttribute("creationDate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());

					} else {

						model.addAttribute("creationDate", formatter.format(cluster.getCreationDate()).toString());

					}

					if (cluster.getLastModifieddate() == null) {

						model.addAttribute("lastModifiedDate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());

					} else {

						model.addAttribute("lastModifiedDate",
								formatter.format(cluster.getLastModifieddate()).toString());

					}

					model.addAttribute("clusterId", cluster.getId());
					model.addAttribute("clusterName", cluster.getName());

					model.addAttribute("AreaID", cluster.getAreaID() + ":" + cluster.getAreaName());
					model.addAttribute("clusterLong", cluster.getLongitude());
					model.addAttribute("clusterLat", cluster.getLatitude());
					model.addAttribute("ordStatus", cluster.getStatus());

				}

				// add data in table WorkOrderSource

				query = session.createQuery(
						"select  TO_CHAR(t.startDate,'YYYY-MM-DD') as startDate , TO_CHAR(t.endDate,'YYYY-MM-DD') as endDate,t.number2gSites as number2gSites, t.number2g3gSites as number2g3gSites , t.number2g3g4gSites as number2g3g4gSites, t.pl2g as pl2g, t.pl2g3g as pl2g3g, t.pl2g3g4g as pl2g3g4g, t.totalSites as totalSites, t.sumProfitLoss as sumProfitLoss, t.id as id from ClusterFinance t where t.clusterID like :param1");
				query.setParameter("param1", request.getParameter("clusterID"));
				@SuppressWarnings("unchecked")
				List<ClusterFinanceBOQ> listClusterFin = (List<ClusterFinanceBOQ>) query
						.setResultTransformer(Transformers.aliasToBean(ClusterFinanceBOQ.class)).list();

				model.addAttribute("ListClusterFinance", mapper.writeValueAsString(listClusterFin));

				model.addAttribute("docStatus", "Existed");
			} catch (Exception e) {

				System.out.println("catch messsage is " + e.getMessage());

			} finally {

				if (session != null && session.isOpen()) {
					session.close();
				}

			}
		}

		return "ClusterFormView";
	}

	@RequestMapping(value = "/ClusterDelete", method = RequestMethod.GET)
	@ResponseBody
	public String ClusterDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		String rtn = "Succeeded";

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			return "redirect:/";
		}

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				String[] idList = request.getParameterValues("clusterID[]");

				if (idList != null) {

					query = session.createQuery("delete  Cluster t  where t.id IN (:param1)");
					query.setParameterList("param1", idList);
					query.executeUpdate();

					query = session.createQuery("delete ClusterFinance   where clusterID IN (:param1)");
					query.setParameterList("param1", idList);
					query.executeUpdate();
					tx.commit();
				}
			} catch (Exception e) {
				tx.rollback();
				System.out.println("catch messsage is " + e.getMessage());
				rtn = "Failed";

			} finally {

				if (session != null && session.isOpen()) {
					session.close();
				}

			}
		}
		return rtn;

	}

	@RequestMapping(value = "/ClusterFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ClusterFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String clusterID = "";
		String areaId = "";
		String areaName = "", Area;
		Calendar calendar = new GregorianCalendar();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Timestamp CreationDate;
		Timestamp lastModifiedDate;
		Cluster cluster = new Cluster();
		String clusterName = request.getParameter("clusterName");
		String longitude = request.getParameter("Longitude");
		String latitude = request.getParameter("Latitude");
		String clusterIdFinance = "";
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				if (StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNew")) {
					synchronized (this) {
						clusterID = "CLUSTER_" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(
								session.createNativeQuery("SELECT CLUSTERS FROM SEQ_TABLE").uniqueResult().toString());
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET CLUSTERS = CLUSTERS + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
					}

					model.addAttribute("clusterID", clusterID);

				} else {

					clusterID = request.getParameter("clusterID");

				}

				if (request.getParameter("creationDate") == "") {

					CreationDate = new Timestamp((new Timestamp(System.currentTimeMillis())).getTime());

				} else {

					CreationDate = new Timestamp((formatter.parse(request.getParameter("creationDate"))).getTime());
				}

				lastModifiedDate = new Timestamp((new Timestamp(System.currentTimeMillis())).getTime());
				Area = request.getParameter("Area");

				if (Area != "") {

					areaId = Area.substring(0, Area.indexOf(':'));
					areaName = Area.substring((Area.indexOf(':')) + 1, Area.length());

				}

				cluster.setId(clusterID);
				cluster.setName(clusterName);
				cluster.setAreaID(areaId);
				cluster.setAreaName(areaName);
				cluster.setStatus(request.getParameter("status"));

				if (longitude != "") {

					cluster.setLongitude((Float.parseFloat(longitude)));

				}
				cluster.setCreationDate(CreationDate);
				cluster.setLastModifieddate(lastModifiedDate);
				if (latitude != "") {

					cluster.setLatitude((Float.parseFloat(latitude)));

				}

				session.saveOrUpdate(cluster);
				System.out.println("slctDel " + Arrays.toString(request.getParameterValues("slctDel[]")));
				if (request.getParameterValues("slctDel[]") != null) {

					query = session.createQuery("delete ClusterFinance t where t.id IN (:param1)");
					query.setParameterList("param1", request.getParameterValues("slctDel[]"));
					query.executeUpdate();

				}

				for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
					ClusterFinance clusterFin = new ClusterFinance();

					if (StringUtils.equalsIgnoreCase(itemParameters.getDictParameter().get(i).get(ID), "0")) {

						synchronized (this) {
							// clusterIdFinance= "CLUSTER_FIN_"+calendar.get(Calendar.YEAR)+"_"
							// +appConfig.getSequenceNbr(22);
							clusterIdFinance = "CLUSTER_FIN_" + calendar.get(Calendar.YEAR) + "_"
									+ Integer
											.parseInt(session.createNativeQuery("SELECT CLUSTER_FINANCE FROM SEQ_TABLE")
													.uniqueResult().toString());
							query = session
									.createNativeQuery("UPDATE SEQ_TABLE SET CLUSTER_FINANCE = CLUSTER_FINANCE + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}

					} else {

						clusterIdFinance = itemParameters.getDictParameter().get(i).get(ID);
					}

					if ((itemParameters.getDictParameter().get(i).get(START_DATE)) != "") {

						clusterFin.setStartDate(new Timestamp(
								(formatter1.parse(itemParameters.getDictParameter().get(i).get(START_DATE)))
										.getTime()));

					}
					if ((itemParameters.getDictParameter().get(i).get(END_DATE)) != "") {

						clusterFin.setEndDate(new Timestamp(
								(formatter1.parse(itemParameters.getDictParameter().get(i).get(END_DATE))).getTime()));

					}

					clusterFin.setId(clusterIdFinance);
					clusterFin
							.setNumber2gSites(Float.parseFloat(itemParameters.getDictParameter().get(i).get(SITES2G)));
					clusterFin.setPl2g(Float.parseFloat(itemParameters.getDictParameter().get(i).get(PROFIT2G)));
					clusterFin.setNumber2g3gSites(
							Float.parseFloat(itemParameters.getDictParameter().get(i).get(SITES2G3G)));
					clusterFin.setPl2g3g(Float.parseFloat(itemParameters.getDictParameter().get(i).get(PROFIT2G3G)));
					clusterFin.setNumber2g3g4gSites(
							Float.parseFloat(itemParameters.getDictParameter().get(i).get(SITES2G3G4G)));
					clusterFin
							.setPl2g3g4g(Float.parseFloat(itemParameters.getDictParameter().get(i).get(PROFIT2G3G4G)));
					clusterFin
							.setTotalSites(Float.parseFloat(itemParameters.getDictParameter().get(i).get(TOTALSITES)));
					clusterFin.setSumProfitLoss(
							(Float.parseFloat(itemParameters.getDictParameter().get(i).get(TOTALSUM))));
					clusterFin.setClusterID(clusterID);
					clusterFin.setClusterName(clusterName);
					clusterFin.setCreationDate(CreationDate);
					clusterFin.setLastModifyDate(lastModifiedDate);

					session.saveOrUpdate(clusterFin);
					tx.commit();
				}
				rtn.put("clusterID", clusterID);
			} catch (Exception e) {
				tx.rollback();
				System.out.println("catch messsage is " + e.getMessage());

			} finally {

				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/GetAllClusters", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllClusters(Locale locale, Model model, HttpServletRequest request) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String cluster = "%" + request.getParameter("clusterID") + "%";

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			try {
				query = session.createQuery(
						"select t.id, t.name from Cluster t where t.id like :param1 or t.name like :param1 ORDER BY lastModifieddate DESC");
				query.setParameter("param1", cluster);
				rtn.put("listCluster", query.list());

			} catch (Exception e) {

				System.out.println("catch messsage is " + e.getMessage());

			} finally {

				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		return rtn;
	}

}
