package com.aliat.alm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.ItemCategoryTree;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ItemCategoryController {



	private static final Logger logger = Logger.getLogger(ItemCategoryController.class.getName());
	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static StringWriter sw;
	private static String exceptionAsString;

	
	
	
	@Autowired
	Notify notifications;

	
	@RequestMapping(value = "/ItemCategoryTreeView", method = RequestMethod.GET)
	public String ItemCategoryTreeView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		// logger.info("Welcome home! The client locale is {}.", locale);
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				try {
					query = session.createQuery("SELECT t.lft, t.rgt FROM ItemCategoryTree t WHERE t.parent = null");
					Object[] Result = (Object[]) query.uniqueResult();
					query = session.createQuery(
							"SELECT t.id, t.lft, t.rgt, t.title, t.code FROM ItemCategoryTree t WHERE t.lft between :param1 AND :param2 ORDER BY t.lft ASC");
					query.setParameter("param1", Result[0]);
					query.setParameter("param2", Result[1]);
					model.addAttribute("ListOfsubtree", mapper.writeValueAsString(query.list()));

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in ItemCategoryTreeView due to \n " + exceptionAsString);
					logger.info("Error in ItemCategoryTreeView due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
			return "ItemCategoryTreeView";
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ItemCategoryListView", method = RequestMethod.GET)
	public String ItemCategoryListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		// logger.info("Welcome home! The client locale is {}.", locale);
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		} else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				try {
					List<ItemCategoryTree> listItem = new ArrayList<ItemCategoryTree>();
					listItem = session.createQuery(
							"select id,title,code,parent,TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastmodified from ItemCategoryTree order by id")
							.list();
					model.addAttribute("ListGridTable", mapper.writeValueAsString(listItem));					
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error at the item catgeory list view due to \n " + exceptionAsString);
					logger.info("Error at the item catgeory list view due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
		}
		return "ItemCategoryListView";
	}

	@RequestMapping(value = "/FilteredItemCategoryListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredItemCategoryListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {
				String startdate, enddate, catname, rootcat, catcode;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				catname = request.getParameter("catname");
				rootcat = request.getParameter("rootcat");
				catcode = request.getParameter("catcode");
				System.out.println(startdate + " " + enddate + " " + catname + " " + rootcat + "" + catcode);
				String str = "select ID as id, TITLE as title , CODE as code , PARENT as parent , TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastmodified"
						+ "  from ITEM_CAT_TREE  ";

				if (startdate != null && enddate != null) {
					str = str + " where CREATION_DATE between TO_DATE('" + startdate + "','YYYY-MM-DD') and TO_DATE('"
							+ enddate + "','YYYY-MM-DD')";
				}
				if (catname != null && !catname.equalsIgnoreCase("")) {

					str = str + " and (upper(TITLE) LIKE upper('%" + catname + "%') )";
				}

				if (rootcat != null && !rootcat.equalsIgnoreCase("")) {
					str = str + " and (upper(PARENT) LIKE upper('%" + rootcat + "%') )";
				}
				if (catcode != null && !catcode.equalsIgnoreCase("")) {
					str = str + " and (upper(CODE) LIKE upper('%" + catcode + "%') )";
				}
				query = session.createSQLQuery(str);				
				rtn.put("listitemcat", query.list());
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in showing the filtered Item Category list view due to \n " + exceptionAsString);
				logger.info("Error in showing the filtered Item Category list view due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/ItemCategoryFormView", method = RequestMethod.GET)
	public String ItemCategoryFormView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonProcessingException {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}

		String itemsList = request.getParameter("itemcatList");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			model.addAttribute("DBConnStat", "successed");
			try {
				if (itemsList != null && itemsList.equals("itemcatList")) {
					query = session.createSQLQuery("SELECT ID FROM ITEM_CAT_TREE");
					if (query.list() != null) {
						model.addAttribute("listItemsNav", mapper.writeValueAsString(query.list()));
					}

				} else {
					model.addAttribute("listItemsNav", mapper.writeValueAsString("noList"));
				}

				Date date;
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

				String itemCatID = request.getParameter("itemCategoryID");
				System.out.println("ID2 is: " + itemCatID);
				if (itemCatID == null) {
					date = new Timestamp(System.currentTimeMillis());
					model.addAttribute("creationdate", formatter.format(date).toString());					
					model.addAttribute("lastmodified", formatter.format(date).toString());					
					model.addAttribute("docStatus", "addNew");
					return "ItemCategoryFormView";
				}
				ItemCategoryTree itemcat = (ItemCategoryTree) session.get(ItemCategoryTree.class, itemCatID);
				if (itemcat != null) {
					model.addAttribute("id", itemcat.getId());
					if (itemcat.getCreationDate() == null) {
						date = new Timestamp(System.currentTimeMillis());
					} else {
						date = itemcat.getCreationDate();
					}
					model.addAttribute("creationdate", formatter.format(date).toString());
					if (itemcat.getLastModified() == null) {
						date = new Timestamp(System.currentTimeMillis());

					} else {
						date = itemcat.getLastModified();
					}
					query = session.createSQLQuery("SELECT CODE FROM ITEM_CAT_TREE WHERE ID=:paramID");
					query.setParameter("paramID", itemcat.getParentId());
					String Catdetails = (String) query.uniqueResult();
					String ParentCat = itemcat.getParent() + ":" + Catdetails;
					model.addAttribute("lastmodified", formatter.format(date).toString());
					model.addAttribute("title", itemcat.getTitle());
					model.addAttribute("code", itemcat.getParentCode());
					model.addAttribute("parent", ParentCat);
					model.addAttribute("description", itemcat.getDescription());
					model.addAttribute("pid", itemcat.getParentId());
					model.addAttribute("itemcode", itemcat.getCode());
				}
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in opening the Item Category due to \n " + exceptionAsString);
				logger.info("Error in opening the Item Category due to \n " + exceptionAsString);
				model.addAttribute("lastmodified", null);
				model.addAttribute("title", null);
				model.addAttribute("code", null);
				model.addAttribute("parent", null);
				model.addAttribute("description", null);
				model.addAttribute("pid", null);
				model.addAttribute("itemcode", null);
				model.addAttribute("DBConnStat", "failed");
				return "redirect:/ItemCategoryListView";
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "ItemCategoryFormView";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ItemCategoryFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ItemCategoryFormSave(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		else {
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				try {
					ItemCategoryTree itemcat = new ItemCategoryTree();
					DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					Date date1 = null;
					Date date2 = null;
					String createdDate = request.getParameter("creationDate");
					String dateOfModification = request.getParameter("lastModifieddate");

					date1 = formatter1.parse(createdDate);
					Timestamp CreationDate = new Timestamp(date1.getTime());
					date2 = formatter1.parse(dateOfModification);
					date2 = new Timestamp(System.currentTimeMillis());
					Timestamp lastModifiedDate = new Timestamp(date2.getTime());

					Date date = new Date();
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(date);
					String ItemCatID;
					// System.out.println("ID: "+ItemCatID);
					synchronized (this) {
						// String ItemCatID = appConfig.getSequenceNbr(23);
						ItemCatID = "ITMCAT" + "-" + calendar.get(Calendar.YEAR) + "-" + Integer.parseInt(
								session.createSQLQuery("SELECT ITEM_CAT_ID FROM SEQ_TABLE").uniqueResult().toString());
						System.out.println("item cat id is " + ItemCatID);
						query = session.createSQLQuery("UPDATE SEQ_TABLE SET ITEM_CAT_ID = ITEM_CAT_ID + 1 ");
						query.executeUpdate();
						session.createSQLQuery("commit").executeUpdate();
					}

					String categoryId = request.getParameter("categoryId");
					System.out.println("categoryId is: " + categoryId);

					query = session
							.createQuery("SELECT t.title, t.lft, t.rgt FROM ItemCategoryTree t where t.id = :paramId ");
					query.setString("paramId", categoryId);

					Object[] Result = (Object[]) query.uniqueResult();

					int rgt = Integer.parseInt(Result[2].toString());
					int leftOfLastChild = rgt - 1;
					int lftNode = leftOfLastChild + 1; // lft of new cat
					int rgtNode = leftOfLastChild + 2;

					query = session
							.createQuery("UPDATE ItemCategoryTree t SET t.rgt = (t.rgt+2) WHERE t.rgt > :paramRgt");
					query.setParameter("paramRgt", leftOfLastChild);
					query.executeUpdate();

					query = session
							.createQuery("UPDATE ItemCategoryTree t SET t.lft = (t.lft+2) WHERE t.lft > :paramLft");
					query.setParameter("paramLft", leftOfLastChild);
					query.executeUpdate();
					List<String> codeList = new ArrayList<String>();
					String itemcatName = request.getParameter("itemcatName");

					String itemcatParent = request.getParameter("itemcatParent");

					String itemcatDescription = request.getParameter("itemcatDescription");
					String itemCode = request.getParameter("itemCode");

					query = session.createSQLQuery("SELECT CODE FROM ITEM_CAT_TREE START WITH TITLE IN "
							+ "(SELECT TITLE FROM ITEM_CAT_TREE WHERE TITLE = :paramTitle) "
							+ "CONNECT BY TITLE = PRIOR PARENT");
					query.setParameter("paramTitle", itemcatParent);
					codeList = query.list();
					Collections.reverse(codeList);
					String parentCode = "";
					String level2 = null, level3 = null, level4 = null;
					String level1 = null;
					for (int i = 1; i < codeList.size(); i++) {
						System.out.println("codeList is: " + codeList.get(i).toString());
						parentCode += codeList.get(i).toString();
						parentCode += "-";
					}
					parentCode += itemCode;
					// parentCode = parentCode.substring(0, parentCode.length() - 1);
					System.out.println("ParentCode in insert is: " + parentCode);
					if (parentCode.contains("-") == true) {
						String arr[] = parentCode.split("-");
						level1 = arr[0];
						level2 = arr[1];
						if (arr.length == 3 || arr.length == 4)
							level3 = arr[2];
						if (arr.length == 4)
							level4 = arr[3];
					} else
						level1 = parentCode;
					String categoryName = request.getParameter("itemcatName");

					query = session.createQuery(
							"SELECT count(*) FROM ItemCategoryTree t WHERE t.parentcode = :parentcode AND t.title =:param2");
					query.setString("parentcode", parentCode);
					query.setString("param2", categoryName);
					Object countList = query.uniqueResult();
					int count = 0;
					if (!(countList == null || Integer.parseInt(countList.toString()) == 0)) {
						count = Integer.parseInt(countList.toString());
						rtn.put("countList", count);
						count = 0;
						return rtn;
					} else {
						rtn.put("countList", count);
					}

					itemcat.setId(ItemCatID);
					itemcat.setCreationDate(CreationDate);
					itemcat.setLastModified(lastModifiedDate);
					itemcat.setTitle(itemcatName);
					itemcat.setCode(itemCode);
					itemcat.setParentCode(parentCode);
					itemcat.setParent(itemcatParent);
					itemcat.setDescription(itemcatDescription);
					itemcat.setLft(lftNode);
					itemcat.setRgt(rgtNode);
					itemcat.setCat1(level1);
					itemcat.setCat2(level2);
					itemcat.setCat3(level3);
					itemcat.setCat4(level4);
					itemcat.setParentId(categoryId);
					session.saveOrUpdate(itemcat);
					rtn.put("BassamTest", "SaveDone");
					rtn.put("itemcatID", ItemCatID);
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in item category form save due to \n " + exceptionAsString);
					logger.info("Error in item category form save to \n " + exceptionAsString);
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

	@RequestMapping(value = "/ItemCategoryListViewDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ItemCategoryListViewDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String[] categoryID = request.getParameterValues("categories[]");		
		String[] CatID = new String[categoryID.length];

		for (int i = 0; i < categoryID.length; i++) {
			CatID[i] = categoryID[i];
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				try {
					query = session.createQuery("SELECT t.lft, t.rgt FROM ItemCategoryTree t WHERE t.id IN :paramId");
					query.setParameter("paramId", CatID[i]);
					Object[] Result = (Object[]) query.uniqueResult();
					int lft = Integer.parseInt(Result[0].toString());
					int rgt = Integer.parseInt(Result[1].toString());

					if ((rgt - lft) != 1) {
						int nbToAdd = rgt - (lft + 1) + 2;

						query = session.createQuery(
								"DELETE FROM ItemCategoryTree t WHERE t.lft between :paramLft AND :paramRgt");
						query.setParameter("paramLft", lft);
						query.setParameter("paramRgt", rgt);
						query.executeUpdate();

						query = session.createQuery(
								"UPDATE ItemCategoryTree t SET t.rgt = (t.rgt-:paramNb) WHERE t.rgt > :paramRgt");
						query.setParameter("paramNb", nbToAdd);
						query.setParameter("paramRgt", rgt);
						query.executeUpdate();

						query = session.createQuery(
								"UPDATE ItemCategoryTree t SET t.lft = (t.lft-:paramNb) WHERE t.lft > :paramRgt");
						query.setParameter("paramNb", nbToAdd);
						query.setParameter("paramRgt", rgt);
						query.executeUpdate();
					} else {
						query = session.createQuery(
								"DELETE FROM ItemCategoryTree t WHERE t.lft = :paramLft AND t.rgt = :paramRgt");
						query.setParameter("paramLft", lft);
						query.setParameter("paramRgt", rgt);
						query.executeUpdate();
						query = session
								.createQuery("UPDATE ItemCategoryTree t SET t.rgt = (t.rgt-2) WHERE t.rgt > :paramRgt");
						query.setParameter("paramRgt", rgt);
						query.executeUpdate();
						query = session
								.createQuery("UPDATE ItemCategoryTree t SET t.lft = (t.lft-2) WHERE t.lft > :paramRgt");
						query.setParameter("paramRgt", rgt);
						query.executeUpdate();
					}
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in deleting from item cat list view due to \n " + exceptionAsString);
					logger.info("Error in deleting from item cat list view due to \n " + exceptionAsString);
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}
			}
		}
		rtn.put("Test", "Delete Done");
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/InsertCategory", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> InsertCategory(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		String categoryID = request.getParameter("categoryID");
		String categoryName = request.getParameter("categoryName");
		String categoryCode = request.getParameter("categoryCode");
		String categoryLevel;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {
				Calendar calendar = new GregorianCalendar();
				query = session.createQuery(
						"SELECT t.id, t.title, t.lft, t.rgt FROM ItemCategoryTree t WHERE t.id = :paramId");

				query.setString("paramId", categoryID);

				Object[] Result = (Object[]) query.uniqueResult();

				String parentID = Result[0].toString();
				String parentName = Result[1].toString();
				int rgt = Integer.parseInt(Result[3].toString());
				int leftOfLastChild = rgt - 1;

				int lftNode = leftOfLastChild + 1; // lft of new cat
				int rgtNode = leftOfLastChild + 2;

				List<String> codeList = new ArrayList<String>();

				query = session.createSQLQuery("SELECT CODE FROM ITEM_CAT_TREE START WITH ID = :paramID "
						+ "CONNECT BY TITLE = PRIOR PARENT AND ID = PRIOR PARENTID ");

				query.setParameter("paramID", parentID);
				codeList = query.list();
				Collections.reverse(codeList);
				int a = codeList.size();
				categoryLevel = Integer.toString(a);
				String parentCode = "";
				String level2 = null, level3 = null, level4 = null;
				String level1 = null;
				for (int i = 1; i < codeList.size(); i++) {
					parentCode += codeList.get(i).toString();
					parentCode += "-";
				}
				parentCode += categoryCode;
				if (parentCode.contains("-") == true) {
					String arr[] = parentCode.split("-");
					level1 = arr[0];
					level2 = arr[1];
					if (arr.length == 3 || arr.length == 4)
						level3 = arr[2];
					if (arr.length == 4)
						level4 = arr[3];
				} else
					level1 = parentCode;

				System.out.println(level1 + " , " + level2 + " , " + level3 + " , " + level4);
				query = session.createQuery("UPDATE ItemCategoryTree t SET t.rgt = (t.rgt+2) WHERE t.rgt > :paramRgt");
				query.setParameter("paramRgt", leftOfLastChild);
				query.executeUpdate();
				
				query = session.createQuery("UPDATE ItemCategoryTree t SET t.lft = (t.lft+2) WHERE t.lft > :paramLft");
				query.setParameter("paramLft", leftOfLastChild);
				query.executeUpdate();

				String ItemCatID;
				synchronized (this) {
					// String ItemCatID = appConfig.getSequenceNbr(23);
					ItemCatID = "ITMCAT" + "-" + calendar.get(Calendar.YEAR) + "-" + Integer.parseInt(
							session.createSQLQuery("SELECT ITEM_CAT_ID FROM SEQ_TABLE").uniqueResult().toString());
					System.out.println("item cat id is " + ItemCatID);
					query = session.createSQLQuery("UPDATE SEQ_TABLE SET ITEM_CAT_ID = ITEM_CAT_ID + 1 ");
					query.executeUpdate();
					session.createSQLQuery("commit").executeUpdate();
				}
				ItemCategoryTree CatTree = new ItemCategoryTree();
				CatTree.setId(ItemCatID);
				CatTree.setLft(lftNode);
				CatTree.setRgt(rgtNode);
				CatTree.setParent(parentName);
				CatTree.setTitle(categoryName);
				CatTree.setCode(categoryCode);
				CatTree.setLvl(Integer.parseInt(categoryLevel));
				CatTree.setParentCode(parentCode);
				CatTree.setParentId(categoryID);
				CatTree.setCat1(level1);
				CatTree.setCat2(level2);
				CatTree.setCat3(level3);
				CatTree.setCat4(level4);
				session.saveOrUpdate(CatTree);
				rtn.put("categoryID", categoryID);
				rtn.put("ItemCatID", ItemCatID);
				rtn.put("Test", "Add Done");
				return rtn;
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in inserting a new item cat due to \n " + exceptionAsString);
				logger.info("Error in inserting a new item cat due to \n " + exceptionAsString);
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
	@RequestMapping(value = "/EditCategory", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> EditCategory(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		String categoryID = request.getParameter("categoryID");
		String categoryName = request.getParameter("categoryName");
		String categoryCode = request.getParameter("categoryCode");

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {

				query = session.createQuery("SELECT t.title FROM ItemCategoryTree t WHERE t.id = :paramId");
				query.setParameter("paramId", categoryID);

				String parentName = (String) query.uniqueResult();

				/* Get All Parents For Added Category to Store it in Parent Code */
				List<String> codeList = new ArrayList<String>();

				query = session.createSQLQuery("SELECT CODE FROM ITEM_CAT_TREE START WITH TITLE IN "
						+ "(SELECT TITLE FROM ITEM_CAT_TREE WHERE TITLE = :paramTitle) "
						+ "CONNECT BY TITLE = PRIOR PARENT");
				query.setParameter("paramTitle", parentName);

				codeList = query.list();
				Collections.reverse(codeList);
				String parentCode = "";
				for (int i = 1; i < codeList.size() - 1; i++) {
					parentCode += codeList.get(i).toString();
					parentCode += "-";
				}
				parentCode += categoryCode;
				query = session.createQuery(
						"UPDATE ItemCategoryTree t SET t.title = :paramTitle, t.code = :paramCode WHERE t.id = :paramId");
				query.setParameter("paramTitle", categoryName);
				query.setParameter("paramCode", categoryCode);
				query.setParameter("paramId", categoryID);
				query.executeUpdate();

				/* Update Parent Category on Category Change */
				query = session.createQuery(
						"UPDATE ItemCategoryTree t SET t.parentcode = :paramParentCode WHERE t.id = :paramId");
				query.setParameter("paramParentCode", parentCode);
				query.setParameter("paramId", categoryID);
				query.executeUpdate();

				/* Update All Parents On Title Change */
				query = session.createQuery(
						"UPDATE ItemCategoryTree t SET t.parent = :paramCategory WHERE t.parent = :paramParent");
				query.setParameter("paramCategory", categoryName);
				query.setParameter("paramParent", parentName);
				query.executeUpdate();

				rtn.put("Test", "Edit Done");
				return rtn;
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in editing item cat due to \n " + exceptionAsString);
				logger.info("Error in editing item cat due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/DeleteCategory", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> DeleteCategory(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		String categoryID = request.getParameter("categoryID");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {
				query = session.createQuery("SELECT t.lft, t.rgt FROM ItemCategoryTree t WHERE t.id = :paramId");
				query.setParameter("paramId", categoryID);
				Object[] Result = (Object[]) query.uniqueResult();

				System.out.println("lft of is : " + Result[0]);
				System.out.println("rgt of is : " + Result[1]);

				// check if has subCategories

				int lft = Integer.parseInt(Result[0].toString());
				int rgt = Integer.parseInt(Result[1].toString());

				if ((rgt - lft) != 1) {
					int nbToAdd = rgt - (lft + 1) + 2;
					query = session
							.createQuery("DELETE FROM ItemCategoryTree t WHERE t.lft between :paramLft AND :paramRgt");
					query.setParameter("paramLft", lft);
					query.setParameter("paramRgt", rgt);
					query.executeUpdate();

					query = session.createQuery(
							"UPDATE ItemCategoryTree t SET t.rgt = (t.rgt-:paramNb) WHERE t.rgt > :paramRgt");
					query.setParameter("paramNb", nbToAdd);
					query.setParameter("paramRgt", rgt);
					query.executeUpdate();

					query = session.createQuery(
							"UPDATE ItemCategoryTree t SET t.lft = (t.lft-:paramNb) WHERE t.lft > :paramRgt");
					query.setParameter("paramNb", nbToAdd);
					query.setParameter("paramRgt", rgt);
					query.executeUpdate();
				} else {
					query = session
							.createQuery("delete from ItemCategoryTree t where t.lft =:paramLft and t.rgt =:paramRgt");
					query.setParameter("paramLft", lft);
					query.setParameter("paramRgt", rgt);
					query.executeUpdate();
					query = session
							.createQuery("update ItemCategoryTree t set t.rgt = (t.rgt-2) where t.rgt > :paramRgt");
					query.setParameter("paramRgt", rgt);
					query.executeUpdate();
					query = session
							.createQuery("update ItemCategoryTree t set t.lft = (t.lft-2) where t.lft > :paramRgt");
					query.setParameter("paramRgt", rgt);
					query.executeUpdate();
				}
				rtn.put("Test", "Delete Done");				
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in Delete Category due to \n " + exceptionAsString);
				logger.info("Error in Delete Category due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/ValidateCategory", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ValidateCategory(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String categoryID = request.getParameter("categoryID");
		String categoryName = request.getParameter("categoryName");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {

				query = session.createQuery("SELECT t.title FROM ItemCategoryTree t WHERE t.id = :paramId");
				query.setString("paramId", categoryID);

				String parentName = (String) query.uniqueResult();

				query = session.createQuery(
						"SELECT count(*) FROM ItemCategoryTree t WHERE t.parent = :paramparent AND t.title =:param2");
				query.setString("paramparent", parentName);
				query.setString("param2", categoryName);
				Object countList = query.uniqueResult();
				System.out.println("countList is" + countList);
				rtn.put("countList", countList);				
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in Validate Category due to \n " + exceptionAsString);
				logger.info("Error in Validate Category due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/GetAllCategory", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllCategory(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		/*
		 * if(LoginServices.checkSession(request, response).equals("redirect:/")) {
		 * rtn.put("Login", "redirect:/"); return rtn; }
		 */

		String itemCategory;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				itemCategory = "%" + request.getParameter("itemCategory") + "%";
				query = session.createQuery("SELECT t1.title, t1.code, t1.parentcode, t1.id FROM ItemCategoryTree t1"
						+ " WHERE LOWER(t1.title) LIKE LOWER(:paramTitle) OR t1.parentcode LIKE :paramTitle");

				query.setString("paramTitle", itemCategory);
				rtn.put("ListItemCategory", query.list());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in GetAllCategory due to \n " + exceptionAsString);
				logger.info("Error in GetAllCategory due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/getCatName", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCatName(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();

			try {
				String ItemCat1 = "%" + request.getParameter("ItemCat1") + "%";
				String ItemCat2 = "%" + request.getParameter("ItemCat2") + "%";
				String ItemCat3 = "%" + request.getParameter("ItemCat3") + "%";
				String ItemCat4 = "%" + request.getParameter("ItemCat4") + "%";

				query = session.createSQLQuery(" SELECT TITLE FROM Item_Cat_Tree where code like :param1 "
						+ "union SELECT TITLE FROM Item_Cat_Tree where code like :param2 "
						+ "union SELECT TITLE FROM Item_Cat_Tree where code like :param3 "
						+ "union SELECT TITLE FROM Item_Cat_Tree where code like :param4 ");

				query.setString("param1", ItemCat1);
				query.setString("param2", ItemCat2);
				query.setString("param3", ItemCat3);
				query.setString("param4", ItemCat4);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("ListInfos", query.list());

			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getCatName due to \n " + exceptionAsString);
				logger.info("Error in getCatName due to \n " + exceptionAsString);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/getParentCategory", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getParentCategory(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		String str, c1, c2, c3, c4;

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				c1 = request.getParameter("catInput");
				c2 = request.getParameter("cat2Input");
				c3 = request.getParameter("cat3Input");
				c4 = request.getParameter("cat4Input");

				str = "select a.title,a.code,a.PARENT,a.parentcode from item_cat_tree a where a.lvl ='1' and (UPPER(a.title) like UPPER(:param1) OR UPPER(a.code) like UPPER(:param1)) ";

				if (!StringUtils.equalsIgnoreCase(c2, "")) {
					str += " and a.ID in "
							+ "(select PARENTID from item_cat_tree where title like :param2 and lvl = '2' )";
				}

				if (!StringUtils.equalsIgnoreCase(c3, "")) {
					str += " and a.ID in " + "(select PARENTID from item_cat_tree where lvl = '2' and ID IN ( "
							+ "SELECT PARENTID from item_cat_tree where title like :param3 and lvl = '3'))";
				}

				if (!StringUtils.equalsIgnoreCase(c4, "")) {
					str += " and a.ID in " + "(select PARENTID from item_cat_tree where lvl = '2' and ID IN ( "
							+ "SELECT PARENTID from item_cat_tree where lvl = '3' and ID IN ( "
							+ "SELECT PARENTID from item_cat_tree where lvl = '4' and title like :param4) " + "))";
				}

				query = session.createSQLQuery(str);

				query.setString("param1", "%" + c1 + "%");

				if (!StringUtils.equalsIgnoreCase(c2, ""))
					query.setString("param2", "%" + c2 + "%");

				if (!StringUtils.equalsIgnoreCase(c3, ""))
					query.setString("param3", "%" + c3 + "%");

				if (!StringUtils.equalsIgnoreCase(c4, ""))
					query.setString("param4", "%" + c4 + "%");

				query.setFirstResult(0);
				query.setMaxResults(40);

				if (query.list() != null && !(query.list()).isEmpty())
					rtn.put("ListCategory", query.list());
				else
					rtn.put("ListCategory", "");
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getParentCategory due to \n " + exceptionAsString);
				logger.info("Error in getParentCategory due to \n " + exceptionAsString);
				rtn.put("ListCategory", "");
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;

	}

	// PR and Po cat2
	@RequestMapping(value = "/getCat2", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCat2(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {

			String str, c1, c2, c3, c4;

			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {
					c1 = request.getParameter("catInput");
					c2 = request.getParameter("cat2Input");
					c3 = request.getParameter("cat3Input");
					c4 = request.getParameter("cat4Input");

					str = "select a.title,a.code,a.PARENT,a.parentcode from item_cat_tree a where a.lvl ='2' and (UPPER(a.title) like UPPER(:param2) OR UPPER(a.code) like UPPER(:param2)) "
							+ "and a.PARENTID in "
							+ "(select b.ID from item_cat_tree b where b.title like :param1 and b.lvl = '1' ) ";

					if (!StringUtils.equalsIgnoreCase(c3, "")) {
						str += " and a.ID in "
								+ "(select c.PARENTID from item_cat_tree c where c.title like :param3 and c.lvl ='3')";
					}

					if (!StringUtils.equalsIgnoreCase(c4, "")) {
						str += " and a.ID in" + "(select PARENTID from item_cat_tree where lvl = '3' and ID IN ( "
								+ "select PARENTID from item_cat_tree  where title like :param4 and lvl = '4') " + ")";
					}

					query = session.createSQLQuery(str);
					query.setString("param1", "%" + c1 + "%");
					query.setString("param2", "%" + c2 + "%");

					if (!StringUtils.equalsIgnoreCase(c3, ""))
						query.setString("param3", "%" + c3 + "%");

					if (!StringUtils.equalsIgnoreCase(c4, ""))
						query.setString("param4", "%" + c4 + "%");

					query.setFirstResult(0);
					query.setMaxResults(40);

					if (query.list() != null && !(query.list()).isEmpty())
						rtn.put("listCat", query.list());
					else
						rtn.put("listCat", "");
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in getCat2 due to \n " + exceptionAsString);
					logger.info("Error in getCat2 due to \n " + exceptionAsString);
					rtn.put("ListCategory", "");
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

	// PR and Po cat3
	@RequestMapping(value = "/getCat3", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCat3(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String str, c1, c2, c3, c4;
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();

				try {

					c1 = request.getParameter("catInput");
					c2 = request.getParameter("cat2Input");
					c3 = request.getParameter("cat3Input");
					c4 = request.getParameter("cat4Input");

					str = "select a.title,a.code,a.PARENT,a.parentcode from item_cat_tree a where a.lvl ='3' and (UPPER(a.title) like UPPER(:param3) OR UPPER(a.code) like UPPER(:param3)) "
							+ " and a.PARENTID in "
							+ "(select a.ID as parent from item_cat_tree a where a.title like :param2 and a.lvl = '2' "
							+ ") and a.PARENTID in( "
							+ "select ID from item_cat_tree where lvl = '2' and PARENTID IN (select ID from item_cat_tree where lvl = '1' and title like :param1))";

					if (!StringUtils.equalsIgnoreCase(c4, "")) {
						str += "and a.ID in ( select b.PARENTID from item_cat_tree b where b.title like :param4 and b.lvl = '4')";
					}

					query = session.createSQLQuery(str);

					query.setString("param1", "%" + c1 + "%");
					query.setString("param2", "%" + c2 + "%");
					query.setString("param3", "%" + c3 + "%");
					if (!StringUtils.equalsIgnoreCase(c4, "")) {
						query.setString("param4", "%" + c4 + "%");
					}

					query.setFirstResult(0);
					query.setMaxResults(40);

					if (query.list() != null && !(query.list()).isEmpty())
						rtn.put("listCat", query.list());
					else
						rtn.put("listCat", "");

				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in getCat3 due to \n " + exceptionAsString);
					logger.info("Error in getCat3 due to \n " + exceptionAsString);
					rtn.put("ListCategory", "");
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

	@RequestMapping(value = "/getCat4", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCat4(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		} else {

			String c1, c2, c3, c4;
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				try {
					c1 = request.getParameter("catInput") == "" ? null : request.getParameter("catInput");
					c2 = request.getParameter("cat2Input") == "" ? null : request.getParameter("cat2Input");
					c3 = request.getParameter("cat3Input") == "" ? null : request.getParameter("cat3Input");
					c4 = request.getParameter("cat4Input") == "" ? null : "%" + request.getParameter("cat4Input") + "%";

					query = session.createSQLQuery(
							"select a.title,a.code,a.PARENT,a.parentcode from item_cat_tree a where a.lvl ='4' and (UPPER(a.title) like COALESCE(UPPER(:param4),'%%') OR UPPER(a.code) like COALESCE(UPPER(:param4),'%%')) "
									+ "and a.PARENTID in ( "
									+ "select a.ID as parent from item_cat_tree a where a.title like COALESCE(:param3,'%%') and a.lvl = '3'   "
									+ ") and a.PARENTID in (   "
									+ "select g.ID as parent from item_cat_tree g where g.lvl = '3' and g.PARENTID IN (select ID from item_cat_tree where lvl = '2' and title like COALESCE(:param2,'%%')) "
									+ ") and a.PARENTID in ( "
									+ "select ID from item_cat_tree where lvl = '3' and PARENTID IN (select ID from item_cat_tree where lvl = '2' and PARENTID IN (select ID from item_cat_tree where lvl = '1' and title like COALESCE(:param1,'%%'))))");
					query.setString("param1", c1);
					query.setString("param2", c2);
					query.setString("param3", c3);
					query.setString("param4", c4);
					query.setFirstResult(0);
					query.setMaxResults(40);

					if (query.list() != null && !(query.list().isEmpty()))
						rtn.put("listCat", query.list());
					else
						rtn.put("listCat", "");
				} catch (Exception e) {
					sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					exceptionAsString = sw.toString();
					logger.finest("Error in getCat4 due to \n " + exceptionAsString);
					logger.info("Error in getCat4 due to \n " + exceptionAsString);
					rtn.put("ListCategory", "");
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

	// Get all Categories in Tree
	@RequestMapping(value = "/getCategories", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCategories(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Passes here");
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}

		Object[] Result;
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {
				query = session.createQuery("SELECT t.lft, t.rgt FROM ItemCategoryTree t WHERE t.parent = null");
				Result = (Object[]) query.uniqueResult();
				query = session.createQuery(
						"SELECT t.id, t.lft, t.rgt, t.title, t.code FROM ItemCategoryTree t WHERE t.lft between :param1 AND :param2 ORDER BY t.lft ASC");
				query.setParameter("param1", Result[0]);
				query.setParameter("param2", Result[1]);
				rtn.put("ListOfsubtree", mapper.writeValueAsString(query.list()));
			} catch (Exception e) {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				exceptionAsString = sw.toString();
				logger.finest("Error in getCategories due to \n " + exceptionAsString);
				logger.info("Error in getCategories due to \n " + exceptionAsString);
				rtn.put("ListCategory", "");
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