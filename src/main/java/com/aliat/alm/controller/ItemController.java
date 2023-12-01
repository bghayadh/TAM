package com.aliat.alm.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.models.Item;
import com.aliat.alm.models.ItemBarcode;
import com.aliat.alm.models.ItemPartNumber;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ItemController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static Session session = null;
	private static Transaction tx = null;
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	private static ObjectMapper mapper = new ObjectMapper();
	// private String ID;
	private String itemCode;
	
	
	
	@Autowired
	Notify notifications;
	
	@Autowired
	Form form;

	@RequestMapping(value = "/ItemListView", method = RequestMethod.GET)
	public String ItemListView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			try {
				query = session.createNativeQuery(
						"select itemCode, item_code, item_name, item_model, item_part_number, LAST_MODIFIED_DATE from (SELECT t.item_code AS itemCode, t.item_code, COALESCE( t.item_name, ' ') as item_name, COALESCE(s.item_model,' ') as item_model, COALESCE(s.item_part_number,' ') as item_part_number,"
								+ "TO_CHAR(t.LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as LAST_MODIFIED_DATE , ROW_NUMBER() over(PARTITION by t.item_code order by s.item_model ) as rownumber "
								+ "FROM item t left join item_model_partnumber s on t.item_code = s.item_code where s.PRIMARY='1')order by LAST_MODIFIED_DATE DESC");
				model.addAttribute("ListGridTable", mapper.writeValueAsString(query.list()));
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

		return "ItemListView";
	}

	@RequestMapping(value = "/FilteredItemListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> FilteredItemListView(Locale locale, Model model, HttpServletRequest request,
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

				String startdate, enddate, itemcode, itemname, itemmodel;
				startdate = request.getParameter("startDate");
				enddate = request.getParameter("endDate");
				itemcode = request.getParameter("itemcode");
				itemname = request.getParameter("itemname");
				itemmodel = request.getParameter("itemmodel");


				String str = "select 1 as chkBox , ITEM_CODE as itemCode , ITEM_NAME as itemName , ITEM_MODEL as itemModel , "
						+ " ITEM_PART_NUMBER as itemPartNumber ,TO_CHAR(LAST_MODIFIED_DATE,'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate"
						+ " from ITEM ";

				if (startdate != null && enddate != null) {
					str = str + " where CREATED_DATE between TO_DATE('" + startdate + "','YYYY-MM-DD') and TO_DATE('"
							+ enddate + "','YYYY-MM-DD')";
				}
				if (itemcode != null && !itemcode.equalsIgnoreCase("")) {

					str = str + " and (upper(ITEM_CODE) LIKE upper('%" + itemcode + "%'))";
				}

				if (itemname != null && !itemname.equalsIgnoreCase("")) {

					str = str + " and (upper(ITEM_NAME) LIKE upper('%" + itemname + "%') )";
				}
				if (itemmodel != null && !itemmodel.equalsIgnoreCase("")) {

					str = str + " and (upper(ITEM_MODEL) LIKE upper('%" + itemmodel + "%'))";
				}
				str = str + " ORDER BY LAST_MODIFIED_DATE DESC ";

			
				rtn.put("listItem", session.createNativeQuery(str).list());

			} catch (Exception e) {
				logger.info("Error in showing the filtered Item list view with a message :" + e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;
	}

	@RequestMapping(value = "/ItemFormView", method = RequestMethod.GET)
	public String ItemFormView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String result[] = new String[4];
		String itemCategoryDetails = "";
		int SelectedIndex = 0;
		Item item;
		String ItemCode, navAction = "2";

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			notifications.headerNotifications(session, model);

			try {

				navAction = request.getParameter("NavAction");
				ItemCode = request.getParameter("itemcode");

				// to open item when click on ADD from item List
				if (ItemCode == null) {
					model.addAttribute("createdDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("lastModifiedDate",
							formatter.format(new Timestamp(System.currentTimeMillis())).toString());
					model.addAttribute("ListPRqItem", "addNew");
					model.addAttribute("ListPRqItemPNum", "addNew");
					model.addAttribute("SelectedIndex", "addNew");
					model.addAttribute("ItemsCount", "addNew");
					model.addAttribute("docStatus", "addNew");

					return "ItemFormView";
				}
				//String itemCode = request.getParameter("itemcode");

				
				

				result = form.NavigationNP(session, "Item", "ITEM_CODE", ItemCode, "LAST_MODIFIED_DATE",
						navAction);
				SelectedIndex = Integer.parseInt(result[1]);
				ItemCode = result[2];
				model.addAttribute("ItemsCount", Integer.parseInt(result[0]));
				model.addAttribute("SelectedIndex", SelectedIndex);

				item = (Item) session.get(Item.class, ItemCode);

				if (item != null) {
					model.addAttribute("itemCode", item.getItemCode());
					if (item.getCreatedDate() == null) {
						model.addAttribute("createdDate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());

					} else {
						model.addAttribute("createdDate", formatter.format(item.getCreatedDate()).toString());

					}

					if (item.getLastModifiedDate() == null) {
						model.addAttribute("lastModifiedDate",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());

					} else {
						model.addAttribute("lastModifiedDate", formatter.format(item.getLastModifiedDate()).toString());

					}

					itemCategoryDetails = item.getItemCategory() + ':' + item.getItemCatCode();

					model.addAttribute("itemName", item.getItemName());
					model.addAttribute("itemModel", item.getItemModel());
					model.addAttribute("itmCatCode", item.getItemCatCode());
					model.addAttribute("itemPartNo", item.getItemPartNumber());
					model.addAttribute("itemCategoryDetails", itemCategoryDetails);
					model.addAttribute("itemCatID", item.getItemCatID());
					model.addAttribute("itemRootCat", item.getItemRootCode());
					model.addAttribute("itemSkuNo", item.getSkuNumber());
					model.addAttribute("itemUUID", item.getUuid());
					model.addAttribute("itemManufact", item.getItemManufacturer());
					model.addAttribute("itemOS", item.getOs());
					model.addAttribute("itemPhysRAM", item.getPhysicalRam());
					model.addAttribute("itemProcType", item.getProcessorType());
					model.addAttribute("itemProcVendor", item.getProcessorVendor());
					model.addAttribute("itemDescription", item.getItemDescription());
					if (item.getDisabled() == '1') {
						model.addAttribute("disabled", "checked");
					} else {
						model.addAttribute("disabled", "");
					}

					model.addAttribute("itemImage", item.getItemImage());
					model.addAttribute("unit", item.getUnit());
					model.addAttribute("weight", item.getWeight());
					model.addAttribute("weightUOM", item.getWeightUOM());
					model.addAttribute("length", item.getLength());
					model.addAttribute("width", item.getWidth());
					model.addAttribute("height", item.getHeight());
					model.addAttribute("sizeUOM", item.getSizeUOM());

					model.addAttribute("itemKind", item.getItemKind());
					model.addAttribute("cableType", item.getCableType());
					model.addAttribute("itemType", item.getItemType());
					model.addAttribute("itemMode", item.getItemMode());
					model.addAttribute("domain", item.getDomain());
					if (item.getService() == '1') {
						model.addAttribute("service", "checked");
					} else {
						model.addAttribute("service", "");
					}
					if (item.getTech2G() == '1') {
						model.addAttribute("tech2G", "checked");
					} else {
						model.addAttribute("tech2G", "");
					}
					if (item.getTech3G() == '1') {
						model.addAttribute("tech3G", "checked");
					} else {
						model.addAttribute("tech3G", "");
					}
					if (item.getTech4G() == '1') {
						model.addAttribute("tech4G", "checked");
					} else {
						model.addAttribute("tech4G", "");
					}
					if (item.getTech5G() == '1') {
						model.addAttribute("tech5G", "checked");
					} else {
						model.addAttribute("tech5G", "");
					}

					if (item.getEndOfLife() == null) {
						model.addAttribute("endOfLife",
								formatter.format(new Timestamp(System.currentTimeMillis())).toString());

					} else {
						model.addAttribute("endOfLife", formatter.format(item.getEndOfLife()).toString());

					}
					model.addAttribute("warrantyPeriod", item.getWarrantyPeriod());
					model.addAttribute("useLifeMonths", item.getUsefull_LifeMonths());
					model.addAttribute("valuationRate", item.getValuationRate());
					model.addAttribute("deprec_Code", item.getDeprec_Code());
					model.addAttribute("accumDeprec_Code", item.getAccumDeprec_Code());
					
					// add data in table discoveryNewItem
					query = session.createQuery("from ItemBarcode where itemCode like :param1");
					query.setParameter("param1", ItemCode);
					model.addAttribute("ListPRqItem", mapper.writeValueAsString(query.list()));
					
					System.out.println("barcode array is : "+mapper.writeValueAsString(query.list()));
					query = session.createQuery("from ItemPartNumber where itemCode like :param1");
					query.setParameter("param1", ItemCode);
					model.addAttribute("ListPRqItemPNum", mapper.writeValueAsString(query.list()));
				}
			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getCause());
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}

		return "ItemFormView";

	}

	@RequestMapping(value = "/ItemFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ItemFormSave(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) throws Exception {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		System.out.println("ListPRqItem is " + request.getParameter("type"));

		Item item = new Item();
		DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String createdDate, endOfLife, resultot = "", activeB_code = "", itemPartNum = "", primary = "", itemModel = "",
				itmId = "";
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);

		float qtyPartNum = 0;
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

///////////////////////////////////////////////////////// SEND EMAIL BUTTON  //////////////////////////////////////////////////////////
				/*
				 * String email = request.getParameter("email"); String emailTo =
				 * request.getParameter("emailTo"); String password =
				 * request.getParameter("password"); String message =
				 * request.getParameter("message"); String subject =
				 * request.getParameter("subject"); String ccmail =
				 * request.getParameter("ccmail");
				 * 
				 * System.out.println("all email parameters  = " + email + " " + emailTo + " " +
				 * password + " " + message + " " + subject + " " + ccmail);
				 * 
				 * if (StringUtils.equalsIgnoreCase(request.getParameter("email"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("password"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("emailTo"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("message"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("subject"), "") &&
				 * StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), "")) {
				 * System.out.println("NO EMAIL TO SEND!");
				 * 
				 * } else if (StringUtils.equalsIgnoreCase(request.getParameter("ccmail"), ""))
				 * {
				 * 
				 * JavaMailUtil.SendEmails(email, password, emailTo, subject, message);
				 * 
				 * } else { JavaMailUtil.SendccEmails(email, password, emailTo, ccmail, subject,
				 * message); }
				 */

///////////////////////////////////////////// END OF SEND EMAIL BUTTON  //////////////////////////////////////////////////////////

//				if (StringUtils.equalsIgnoreCase(request.getParameter("type"), "addNew")) {

				if (StringUtils.equalsIgnoreCase(request.getParameter("itemCode"), "")) {
					synchronized (this) {
						// this.ID = request.getParameter("itemRootCat") + "_" + year + "_" +
						// appConfig.getSequenceNbr(78);
						this.itemCode = request.getParameter("itemRootCat") + "-" + year + "-" + Integer.parseInt(
								session.createNativeQuery("SELECT ITEM_CODE FROM SEQ_TABLE").uniqueResult().toString());						
						query = session.createNativeQuery("UPDATE SEQ_TABLE SET ITEM_CODE = ITEM_CODE + 1 ");
						query.executeUpdate();
						session.createNativeQuery("commit").executeUpdate();
					}
				} else {
					itemCode = request.getParameter("itemCode");
				}

				rtn.put("itemCode", this.itemCode);
				System.out.println("id is " + itemCode);
				createdDate = request.getParameter("createdDate");
				endOfLife = request.getParameter("endOfLife");
				// item.setItemCode(request.getParameter("itemCode"));
				item.setItemCode(itemCode);
				item.setCreatedDate(new Timestamp(formatter1.parse(createdDate).getTime()));
				item.setLastModifiedDate(new Timestamp((new Timestamp(System.currentTimeMillis())).getTime()));
				item.setItemName(request.getParameter("itemName"));
				item.setItemModel(request.getParameter(itemCode));
				item.setItemCategory(request.getParameter("itemCategory"));
				item.setItemCatCode(request.getParameter("itmCatCode"));
				item.setItemCatID(request.getParameter("itemCatID"));
				item.setItemRootCode(request.getParameter("itemRootCat"));
				item.setItemPartNumber(request.getParameter("itmPartNo"));
				item.setItemDescription(request.getParameter("itemDescription"));
				item.setItemImage(request.getParameter("itemImage"));
				item.setDisabled(request.getParameter("disabled").charAt(0));
				item.setDomain(request.getParameter("domain"));
				item.setSkuNumber(request.getParameter("itmSkuNo"));
				item.setUuid(request.getParameter("itmUUID"));
				item.setItemManufacturer(request.getParameter("itemManufact"));
				item.setOs(request.getParameter("itmOS"));
				item.setPhysicalRam(request.getParameter("itmPhysRAM"));
				item.setProcessorType(request.getParameter("itemProcType"));
				item.setProcessorVendor(request.getParameter("itmProcVendor"));
				item.setUnit(request.getParameter("unit"));
				item.setWeight(request.getParameter("weight"));
				item.setWeightUOM(request.getParameter("weightUOM"));
				item.setLength(request.getParameter("length"));
				item.setWidth(request.getParameter("width"));
				item.setHeight(request.getParameter("height"));
				item.setSizeUOM(request.getParameter("sizeUOM"));
				item.setItemKind(request.getParameter("itmKind"));
				item.setCableType(request.getParameter("cableType"));
				item.setItemType(request.getParameter("itemType"));
				item.setItemMode(request.getParameter("itmMode"));
				item.setService(request.getParameter("service").charAt(0));
				item.setTech2G(request.getParameter("tech2G").charAt(0));
				item.setTech3G(request.getParameter("tech3G").charAt(0));
				item.setTech4G(request.getParameter("tech4G").charAt(0));
				item.setTech5G(request.getParameter("tech5G").charAt(0));
				item.setWarrantyPeriod(request.getParameter("warrantyPeriod"));
				item.setUsefull_LifeMonths(request.getParameter("useLifeMonths"));
				item.setEndOfLife(new Timestamp((formatter1.parse(endOfLife)).getTime()));
				item.setValuationRate(Float.parseFloat(request.getParameter("valuationRate")));
				item.setDeprec_Code(request.getParameter("deprec_Code"));
				item.setAccumDeprec_Code(request.getParameter("accumDeprec_Code"));
				session.saveOrUpdate(item);

				// itemCode = request.getParameter("itemCode");
				// System.out.println("itemCode after save " + itemCode);
				query = session.createQuery("delete ItemBarcode  where itemCode = :param1");
				query.setParameter("param1", itemCode);
				query.executeUpdate();
				query = session.createQuery("delete ItemPartNumber  where itemCode = :param1");
				query.setParameter("param1", itemCode);
				query.executeUpdate();

				String barcodeNum = "";
				if (itemParameters.getDictParameterbarcode() != null) {
					for (int i = 0; i < itemParameters.getDictParameterbarcode().size(); i++) {
						barcodeNum = itemParameters.getDictParameterbarcode().get(i).get("barcodeno");
						query = session.createQuery("from ItemBarcode where barcodeNb like :param");
						query.setParameter("param", barcodeNum);
						Object result = query.uniqueResult();
						System.out.println("result  " + mapper.writeValueAsString(result));
						if (result == null) {
							ItemBarcode itemBarcde = new ItemBarcode();
							itemBarcde.setBarcodeNb(barcodeNum);
							activeB_code = itemParameters.getDictParameterbarcode().get(i).get("active");
							itemBarcde.setItemCode(itemCode);
							itemBarcde.setActivebcode(activeB_code);
							session.saveOrUpdate(itemBarcde);
							System.out.println("itemCode res null " + barcodeNum);
						}

						else {
							// resultot = resultot + " " + barcodeNum;
							// System.out.println("resultot " + resultot)
							resultot = barcodeNum;
							System.out.println("itemCode res not null " + barcodeNum);
						}

					}
					// rtn.put("exists_InOthers", resultot);
					rtn.put("existsbarcode", resultot);
				}

				if (itemParameters.getDictParameteritemPartnum() != null) {
					for (int j = 0; j < itemParameters.getDictParameteritemPartnum().size(); j++) {
						ItemPartNumber itemPartNumber = new ItemPartNumber();
						// itmId = "ITM" + calendar.get(Calendar.YEAR) + "_" +
						// appConfig.getSequenceNbr(24);
						synchronized (this) {
							// itmId = "ITM" + calendar.get(Calendar.YEAR) + "_" +
							// appConfig.getSequenceNbr(24);
							itmId = "ITM" + calendar.get(Calendar.YEAR) + "_" + Integer.parseInt(
									session.createNativeQuery("SELECT ITEM_ID FROM SEQ_TABLE").uniqueResult().toString());							
							query = session.createNativeQuery("UPDATE SEQ_TABLE SET ITEM_ID = ITEM_ID + 1 ");
							query.executeUpdate();
							session.createNativeQuery("commit").executeUpdate();
						}
						itemPartNum = itemParameters.getDictParameteritemPartnum().get(j).get("itemPartNum");
						primary = itemParameters.getDictParameteritemPartnum().get(j).get("active");
						itemModel = itemParameters.getDictParameteritemPartnum().get(j).get("itemModel");

						if (itemParameters.getDictParameteritemPartnum().get(j).get("Quantity") != "") {
							qtyPartNum = Float
									.parseFloat(itemParameters.getDictParameteritemPartnum().get(j).get("Quantity"));
							itemPartNumber.setQtyPartNum(qtyPartNum);
						}

						if (itemParameters.getDictParameteritemPartnum().get(j).get("itemModel") != ""
								|| itemParameters.getDictParameteritemPartnum().get(j).get("itemPartNum") != "") {
							itemPartNumber.setItmId(itmId);
							itemPartNumber.setItemCode(itemCode);
							itemPartNumber.setItemPartNum(itemPartNum);
							itemPartNumber.setPrimary(primary);
							itemPartNumber.setItemModel(itemModel);
							session.saveOrUpdate(itemPartNumber);
						}
					}
				}
				rtn.put("lastModifiedDate", formatter1.format(new Timestamp(System.currentTimeMillis())).toString());
			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	@RequestMapping(value = "/ItemDelete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ItemDelete(Locale locale, Model model, HttpServletRequest request,
			@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();		
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("result", "redirect:/");
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				String[] idList = request.getParameterValues("itemCode[]");
				if (idList != null) {
//					query = session.createQuery("delete ItemPartNumber where itemCode IN :param1");
					query = session.createNativeQuery("delete ITEM_MODEL_PARTNUMBER where ITEM_CODE IN :param1");
					query.setParameterList("param1", idList);
					query.executeUpdate();

//					query = session.createQuery("delete ItemBarcode where itemCode IN :param1");					
					query = session.createNativeQuery("delete ITEM_BARCODE where ITEM_CODE IN :param1");
					query.setParameterList("param1", idList);
					query.executeUpdate();

//					query = session.createQuery("delete Item t where t.itemCode IN :param1");
					query = session.createNativeQuery("delete ITEM where ITEM_CODE IN :param1");
					query.setParameterList("param1", idList);
					query.executeUpdate();
					rtn.put("result", "Success");
				}
			} catch (Exception e) {
				System.out.println("catch messsage is " + e.getCause());
				e.printStackTrace();
				rtn.put("result", "Failed");				
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;

	}

	@RequestMapping(value = "/getSequ", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSequ(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {

			// response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			rtn.put("Login", "redirect:/");
			return rtn;
		} else {
			String c1, c2, c3, c4, seq;
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				try {

					c1 = request.getParameter("catInput") == null ? "" : request.getParameter("catInput");
					c2 = request.getParameter("cat2Input") == null ? "" : request.getParameter("cat2Input");
					c3 = request.getParameter("cat3Input") == null ? "" : request.getParameter("cat3Input");
					c4 = request.getParameter("cat4Input") == null ? "" : request.getParameter("cat4Input");
					seq = "%" + request.getParameter("seqInput") + "%";

					if ((StringUtils.equalsIgnoreCase(c1, "") || c1 == null)
							&& (StringUtils.equalsIgnoreCase(c2, "") || c2 == null)
							&& (StringUtils.equalsIgnoreCase(c3, "") || c3 == null)
							&& (StringUtils.equalsIgnoreCase(c4, "") || c4 == null)) {

						query = session.createNativeQuery(
								"select t.item_code, t.item_name, t.item_category, nvl(a.item_model,'-'), nvl(a.item_part_number,'-')  from item t "
										+ "left join item_model_partnumber a on t.item_code = a.item_code "
										+ " where upper(t.item_code) like upper(:param5) ");
						query.setParameter("param5", seq);
					} else {
						query = session.createNativeQuery(
								"select t.item_code, t.item_name, t.item_category, nvl(a.item_model,'-'), nvl(a.item_part_number,'-')  from item t "
										+ "left join item_model_partnumber a on t.item_code = a.item_code "
										+ "where upper(t.item_code) like upper(:code) AND upper(t.item_code) like upper(:param5) ");

						String code = "";
						if (StringUtils.equalsIgnoreCase(c1, ""))
							code += "%-";
						else
							code += "%"+c1 + "-";

						if (StringUtils.equalsIgnoreCase(c2, ""))
							code += "%-";
						else
							code += c2 + "-";

						if (StringUtils.equalsIgnoreCase(c3, ""))
							code += "%-";
						else
							code += c3 + "-";

						if (StringUtils.equalsIgnoreCase(c4, ""))
							code += "%";
						else
							code += c4 +"%";

						query.setParameter("code", code);
						query.setParameter("param5", seq);
						/*
						 * if (!StringUtils.equalsIgnoreCase(c4, "") && c4 != null) {
						 * query.setString("code", "%"+c4+"%"); query.setString("param5", seq); } else
						 * if (!StringUtils.equalsIgnoreCase(c3, "") && c3 != null) {
						 * query.setString("code", "%"+c3+"%"); query.setString("param5", seq); } else
						 * if (!StringUtils.equalsIgnoreCase(c2, "") && c2 != null) {
						 * query.setString("code", "%"+c2+"%"); query.setString("param5", seq); } else
						 * if (!StringUtils.equalsIgnoreCase(c1, "") && c1 != null) {
						 * query.setString("code", "%"+c1+"%"); query.setString("param5", seq); }
						 */

					}

					query.setFirstResult(0);
					query.setMaxResults(40);

					rtn.put("listCat", query.list());
				} catch (Exception e) {
					logger.info("Error in geting data for main category. " + e);
					e.printStackTrace();
					rtn.put("ListCategory", "");
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}

			return rtn;
		}
	}

	@RequestMapping(value = "/getPartNo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPartNo(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		/*
		 * if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		 * rtn.put("Login", "redirect:/"); return rtn; }
		 */

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				String requestValue = request.getParameter("requestValue");
				String barcode = request.getParameter("barcode");

				String str = "select distinct t.item_part_number, a.item_code, a.item_name, nvl(t.item_model,'-'), nvl(b.barcode_number,'-') "

						+ "from item a left join item_model_partnumber t on a.item_code = t.item_code "
						+ "left join item_barcode b on a.item_code =  b.item_code "
						+ "where (LOWER(t.item_part_number) like LOWER(:param1))";

				if (!StringUtils.equalsIgnoreCase(barcode, "") && barcode != null) {
					str += " and b.barcode_number =:param2";
				}

				query = session.createNativeQuery(str);
				query.setParameter("param1", "%" + requestValue + "%");

				if (!StringUtils.equalsIgnoreCase(barcode, "") && barcode != null)
					query.setParameter("param2", barcode);

				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("ListPartNos", query.list());
			} catch (Exception e) {
				logger.info("Error while getting Part Nbs using autocomplete with error message: " + e);
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;
	}

	@RequestMapping(value = "/getModel", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getModel(HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		/*
		 * if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		 * rtn.put("Login", "redirect:/"); return rtn; }
		 */

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				String requestValue = request.getParameter("requestValue");
				String barcode = request.getParameter("barcode");

				String str = "select distinct t.item_model, a.item_code, a.item_name, nvl(t.item_part_number,'-'), nvl(b.barcode_number,'-') "
						+ "from item a left join item_model_partnumber t on a.item_code = t.item_code "
						+ "left join item_barcode b on a.item_code =  b.item_code "
						+ "where (LOWER(t.item_model) like LOWER(:param1))";

				if (!StringUtils.equalsIgnoreCase(barcode, "") && barcode != null) {
					str += " and b.barcode_number =:param2";
				}

				query = session.createNativeQuery(str);
				query.setParameter("param1", "%" + requestValue + "%");

				if (!StringUtils.equalsIgnoreCase(barcode, "") && barcode != null)
					query.setParameter("param2", barcode);

				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("ListModels", query.list());
			} catch (Exception e) {
				logger.info("Error while getting Models using autocomplete with error message: " + e);
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;
	}

	@RequestMapping(value = "/getBarcode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBarcode(HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {

				String requestValue = request.getParameter("requestValue");

				query = session.createNativeQuery(
						"select distinct b.barcode_number, a.item_code, a.item_name, nvl(t.item_model,'-'), nvl(t.item_part_number,'-'),  a.ITEM_CATCODE "

								+ "from item a left join item_model_partnumber t on a.item_code = t.item_code "
								+ "left join item_barcode b on a.item_code =  b.item_code "
								+ "where LOWER(b.barcode_number) like LOWER(:param1)");

				query.setParameter("param1", "%" + requestValue + "%");
				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("ListBarcode", query.list());
			} catch (Exception e) {
				logger.info("Error while getting Barcodes using autocomplete with error message: " + e);
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;
	}

	@RequestMapping(value = "/getItemCode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getItemCode(HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {

		Map<String, Object> rtn = new LinkedHashMap<>();
		/*
		 * if (LoginServices.checkSession(request, response).equals("redirect:/")) {
		 * rtn.put("Login", "redirect:/"); return rtn; }
		 */

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				String requestValue = request.getParameter("requestValue");
				String barcode = request.getParameter("barcode");

				String str = "select distinct a.item_code, a.item_name, nvl(t.item_model,'-'), nvl(t.item_part_number,'-'), nvl(b.barcode_number,'-'), a.item_category,"
						+ "a.ITEM_CATCODE, a.ITEM_CATID, a.ITEM_ROOT_CODE "

						+ "from item a left join item_model_partnumber t on a.item_code = t.item_code "
						+ "left join item_barcode b on a.item_code =  b.item_code "
						+ "where (LOWER(a.item_code) like LOWER(:param1) OR LOWER(a.item_name) like LOWER(:param1) "
						+ " OR LOWER(t.item_part_number) like LOWER(:param1) OR LOWER(t.item_model) like LOWER(:param1) OR LOWER(b.barcode_number) like LOWER(:param1))";

				if (!StringUtils.equalsIgnoreCase(barcode, "") && barcode != null) {
					str += " and b.barcode_number =:param2";
				}

				query = session.createNativeQuery(str);
				query.setParameter("param1", "%" + requestValue + "%");

				if (!StringUtils.equalsIgnoreCase(barcode, "") && barcode != null)
					query.setParameter("param2", barcode);

				query.setFirstResult(0);
				query.setMaxResults(40);

				rtn.put("ListItemDetails", query.list());
			} catch (Exception e) {
				logger.info("Error while getting itemCodes using autocomplete with error message: " + e);
				e.printStackTrace();

			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

		return rtn;
	}

	/*
	 * @RequestMapping(value = "/GetAllItemModelPopup", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> GetAllItemModelPopup(Locale locale,
	 * Model model, HttpServletRequest request, HttpServletResponse response) {
	 * Map<String, Object> rtn = new LinkedHashMap<>(); if
	 * (LoginServices.checkSession(request, response).equals("redirect:/")) {
	 * rtn.put("Login", "redirect:/"); return rtn; }
	 * 
	 * System.out.println("GetAllItemModelPopup Welcome"); String Item_Cat = "%" +
	 * request.getParameter("ItemCategory") + "%"; String requestName = "%" +
	 * request.getParameter("Item_Model") + "%"; String Item_code = "%" +
	 * request.getParameter("Item_code") + "%"; String Item_Name = "%" +
	 * request.getParameter("Item_Name") + "%"; String itemCategoryCode =
	 * request.getParameter("itemCategoryCode"); String Item_PartNum = "%" +
	 * request.getParameter("Item_PartNum") + "%"; String BarCode =
	 * request.getParameter("ItemBarCode");
	 * 
	 * System.out.println("itemCategoryCode is " + itemCategoryCode);
	 * 
	 * session = almsessions.getALMSession();
	 * 
	 * tx = session.beginTransaction();
	 * 
	 * if (BarCode == null) {
	 * 
	 * if (requestName.equalsIgnoreCase("%null%") == true) {
	 * System.out.println("test1"); System.out.println("Item_code " + Item_code);
	 * 
	 * if (Item_code.equalsIgnoreCase("%empty%") == true) {
	 * System.out.println("test1.1");
	 * 
	 * query = session.createQuery(
	 * "select itemName,itemCode,itemCategory,itemModel,itemPartNumber from Item where itemCode like :param1 or itemName like :param1 ORDER BY lastModifiedDate DESC"
	 * );
	 * 
	 * query.setString("param1", Item_Name); query.setFirstResult(0);
	 * query.setMaxResults(40);
	 * 
	 * rtn.put("ListItemName", query.list()); }
	 * 
	 * else { System.out.println("test1.2"); System.out.println("itemName is " +
	 * Item_Name); System.out.println("itemCode is " + Item_code);
	 * 
	 * query = session.createQuery(
	 * "select distinct itemName,' ' from Item where  itemName like :param1 and itemCode=:param2"
	 * ); query.setString("param1", Item_Name); query.setString("param2",
	 * Item_code); query.setFirstResult(0); query.setMaxResults(40);
	 * rtn.put("ListItemName", query.list());
	 * 
	 * } query = session.
	 * createQuery("SELECT count(*) FROM Item WHERE itemCatCode = :paramCode");
	 * query.setParameter("paramCode", itemCategoryCode);
	 * System.out.println("itemCategoryCode " + itemCategoryCode);
	 * 
	 * } else { System.out.println("test2");
	 * 
	 * if (Item_code.equalsIgnoreCase("%empty%") == true ||
	 * Item_code.equalsIgnoreCase("%%") == true ||
	 * requestName.equalsIgnoreCase("%%") == true) { System.out.println("test2.1");
	 * 
	 * // itemCode Autocomplete if (Item_PartNum.equalsIgnoreCase("%1%") == true) {
	 * System.out.println("test2.1.1");
	 * 
	 * query = session.createQuery("select distinct " +
	 * "(select nvl(b.itemModel,' ') from ItemPartNumber b where " +
	 * "b.itemCode=a.itemCode and b.primary=1) as itmModel ," +
	 * " (select nvl(a.itemCode,' ') from ItemPartNumber b where b.itemCode=a.itemCode and b.primary=1) as itmcode , nvl(a.itemName,' '),"
	 * +
	 * "(select nvl(b.itemPartNum,' ') from ItemPartNumber b where b.itemCode=a.itemCode and b.primary=1) as PartNumber"
	 * +
	 * "  from Item a where (a.itemCode like :param1 or a.itemName like :param1 ) and a.itemCode IN (select distinct NVL(b.itemCode,' ') "
	 * + "from ItemPartNumber b where b.primary=1)");
	 * 
	 * query.setString("param1", requestName); query.setFirstResult(0);
	 * query.setMaxResults(40); rtn.put("ListItemModelPopup", query.list());
	 * 
	 * } else { // itemPartNumber Autocomplete if
	 * (Item_PartNum.equalsIgnoreCase("%2%") == true) {
	 * System.out.println("test2.1.2.11111");
	 * 
	 * query = session.
	 * createQuery("select distinct nvl(t.itemModel,' '),nvl(t.itemCode,' ')," +
	 * "(select nvl(a.itemName,' ') from Item a where a.itemCode=t.itemCode)as itmname,"
	 * +
	 * "nvl(t.itemPartNum,' ') from ItemPartNumber t where t.itemPartNum IS NOT NULL and ( t.itemPartNum like :param1 or t.itemCode like :param1 or t.itemModel like :param1 )"
	 * );
	 * 
	 * query.setString("param1", requestName); query.setFirstResult(0);
	 * query.setMaxResults(40); rtn.put("ListItemModelPopup", query.list());
	 * 
	 * } // itemModel Autocomplete else {
	 * 
	 * System.out.println("test2.1.2.2222222");
	 * 
	 * query = session.
	 * createQuery("select distinct nvl(t.itemModel,' '),nvl(t.itemCode,' ')," +
	 * "(select nvl(a.itemName,' ') from Item a where a.itemCode=t.itemCode)as itmname,"
	 * +
	 * "nvl(t.itemPartNum,' ') from ItemPartNumber t where t.itemModel IS NOT NULL and ( t.itemPartNum like :param1 or t.itemCode like :param1 or t.itemModel like :param1 )"
	 * );
	 * 
	 * query.setString("param1", requestName); query.setFirstResult(0);
	 * query.setMaxResults(40); rtn.put("ListItemModelPopup", query.list()); } } }
	 * 
	 * } rtn.put("ListItemModelPopup", query.list());
	 * 
	 * } else { System.out.println("test3"); // Barcode Autocomplete if
	 * (Item_PartNum.equalsIgnoreCase("%0%") == true) {
	 * System.out.println("test3.1");
	 * 
	 * query = session.createSQLQuery(
	 * "SELECT b.ITEM_CODE , c.ITEM_NAME,b.ITEM_MODEL , b.ITEM_PART_NUMBER,c.ITEM_CAT_CODE   "
	 * + " FROM ITEM_MODEL_PARTNUMBER b  , ITEM c " +
	 * " inner join ITEM_BARCODE a  using (ITEM_CODE) " +
	 * " WHERE a.BARCODE_NUMBER like :param1 AND  b.ITEM_CODE IN (Select ITEM_CODE from ITEM_BARCODE WHERE BARCODE_NUMBER like :param1)"
	 * ); query.setString("param1", BarCode); query.setFirstResult(0);
	 * query.setMaxResults(40); rtn.put("ListInfo", query.list());
	 * 
	 * } // item autocomplete with barcode else if
	 * (Item_PartNum.equalsIgnoreCase("%1%") == true) {
	 * System.out.println("test3.2");
	 * 
	 * query = session.createSQLQuery(
	 * "SELECT a.ITEM_CODE , a.ITEM_NAME, b.ITEM_MODEL , b.ITEM_PART_NUMBER , c.BARCODE_NUMBER, a.ITEM_CAT_CODE "
	 * + "from ITEM a " +
	 * "inner join ITEM_MODEL_PARTNUMBER b ON b.item_code = a.item_code " +
	 * "inner join ITEM_BARCODE c ON c.item_code = a.item_code " +
	 * "where a.item_code like :param1"); query.setString("param1", Item_Cat);
	 * query.setFirstResult(0); query.setMaxResults(40); if (query.list().isEmpty())
	 * { rtn.put("ListInfo", ""); } else rtn.put("ListInfo", query.list());
	 * 
	 * } else { System.out.println("test3.3");
	 * 
	 * query = session.createSQLQuery(
	 * "SELECT BARCODE_NUMBER AS barcodeNb,ITEM_CODE AS itemCode,ACTIVE AS activeB_code FROM ITEM_BARCODE where BARCODE_NUMBER like COALESCE(:param1,'%%')"
	 * ); query.setString("param1", BarCode); query.setFirstResult(0);
	 * query.setMaxResults(40); if (query.list() != null &&
	 * !(query.list()).isEmpty()) rtn.put("listBarcode", query.list()); else
	 * rtn.put("listBarcode", ""); } }
	 * 
	 * return rtn; }
	 */
	@RequestMapping(value = "/GetItemsAutocomplete", method = RequestMethod.GET)
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
				query = session.createQuery("SELECT t1.itemName, t1.itemCode FROM Item t1"
						+ " WHERE LOWER(t1.itemName) LIKE LOWER(:paramTitle) OR t1.itemCode LIKE :paramTitle");

				query.setParameter("paramTitle", itemCategory);
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("ListItemCategory", query.list());

			} catch (Exception e) {
				System.out.println("erro");
				} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return rtn;
	}

	/*
	 * @RequestMapping(value = "/GetCountItemsAutocomplete", method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseBody public Map<String, Object> GetCountItemAutocomplete(Locale
	 * locale, Model model, HttpServletRequest request, HttpServletResponse
	 * response) { Map<String, Object> rtn = new LinkedHashMap<>(); if
	 * (LoginServices.checkSession(request, response).equals("redirect:/")) {
	 * rtn.put("Login", "redirect:/"); return rtn; }
	 * System.out.println("Welcome GetCountItemAutocomplete "); String
	 * itemCategoryCode = request.getParameter("itemCategoryCode");
	 * System.out.println("itemCategoryCode "+ itemCategoryCode);
	 * 
	 * session = almsessions.getALMSession(); tx = session.beginTransaction(); query =
	 * session.
	 * createQuery("SELECT count(*) FROM Item WHERE itemCatCode = :paramCode");
	 * query.setParameter("paramCode", itemCategoryCode); rtn.put("CountItems",
	 * query.list()); return rtn; }
	 */
	// Used in the Fixed Asset Register
	@RequestMapping(value = "/GetItemDetailsForm", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetItemDetailsForm(Locale locale, Model model, HttpServletRequest request) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		String itemdtl = "%" + request.getParameter("ItemCode") + "%";
		System.out.println("liliane");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);

			try {
				query = session.createQuery(
						"select itemName, unit, weight, weightUOM, length, width, height, sizeUOM, itemDescription, domain, cableType, itemType, usefull_LifeMonths, valuationRate, warrantyPeriod, endOfLife, itemModel, itemPartNumber, tech2G, tech3G, tech4G, tech5G, service, disabled  from Item where itemCode like :param1 or itemName like :param1 ");
				query.setParameter("param1", itemdtl);
				@SuppressWarnings("unused")
				Object Unit = null;

				for (Object obj : (query.list())) {

					Object[] fields = (Object[]) obj;
					Unit = fields[1];

				}
				rtn.put("ItemDetails", query.list());

				query = session.createQuery("from Item where itemCode like :param1 or itemName like :param1 ");
				query.setParameter("param1", itemdtl);
				Item item1 = (Item) query.uniqueResult();
				rtn.put("ItemRecord1", item1);

				query = session.createQuery(
						"select itemName as itemName, unit as unit, weight as weight, weightUOM as weightUOM from Item where itemCode like :param1 or itemName like :param1 ");

				query.setParameter("param1", itemdtl);
				Object item = query.uniqueResult();
				Object[] itemStringArray2 = (Object[]) query.list().get(0);
				rtn.put("ItemRecord", item);
				rtn.put("ItemRecord3", itemStringArray2);

			} catch (Exception e) {
				logger.info("Error in geting data for main category. " + e.getMessage());
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
