package com.aliat.alm.controller;

import java.io.IOException;

import java.util.ArrayList;
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
import com.aliat.alm.models.AssetRegistry;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.models.GoodsReceived;
import com.aliat.alm.models.PurchaseOrder;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static Session session = null;
	private static Transaction tx = null;
	@SuppressWarnings("rawtypes")
	private static Query query = null;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	@Autowired
	Notify notifications;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/home", method = RequestMethod.GET)

	public String home(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		} else {

			logger.info("Welcome home! The client locale is {}.", locale);

			String VType2 = request.getParameter("VoucherType");
			String Vnb2 = request.getParameter("VNCODE1");

			System.out.println(VType2);
			System.out.println(Vnb2);
			session = AlmDbSession.getInstance().getSession();
			System.out.println("HashCode Home: "+AlmDbSession.getInstance().hashCode());
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				try {
					if (VType2 != null && Vnb2 != null) {
						System.out.println("VType2 is : " + VType2);
						System.out.println("Vnb2 is : " + Vnb2);
						model.addAttribute("actionType", "SearchPage");
						model.addAttribute("VoucherType", VType2);
						model.addAttribute("Vnb2", Vnb2);

						//////////////////////////////////////////////////////////////// start////////////////////////////////////////////////////

						// PR
						if (StringUtils.equalsIgnoreCase(VType2, "pr") && !StringUtils.equalsIgnoreCase(Vnb2, "")) {
							System.out.println("pppppppppppppppppppppppppppppppppp");

							String getPRQ = " from PurchaseRequest where  ID like :param1";
							query = session.createQuery(getPRQ);
							query.setParameter("param1", Vnb2);
							List<PurchaseRequest> listPurchaseReq = query.list();

							float prNetTot = 0;
							float prTotQty = 0;
							String prStatus = "";
							String prId = "";

							for (PurchaseRequest purchaseReq : listPurchaseReq) {

								prNetTot = purchaseReq.getnTotal();
								prTotQty = purchaseReq.getPrtotalQty();
								prStatus = purchaseReq.getPrStatus();
								prId = purchaseReq.getPurchaseReqID();
							}
							model.addAttribute("prStatus", prStatus);
							model.addAttribute("prId", prId);
							model.addAttribute("totQtyPrCom", prTotQty);
							model.addAttribute("netPrCom", prNetTot);

							///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

							// Completed PO
							String queryStat = "from PurchaseOrder where ordPRQid like :param1";
							query = session.createQuery(queryStat);
							query.setParameter("param1", Vnb2);
							List<PurchaseOrder> listPurchaseOrder = query.list();
							System.out.println("afiiiiiiiiiiiiiiiiiiiiiiiif" + Vnb2);
							float ordNetTot = 0;
							float ordTotQty = 0;
							for (PurchaseOrder purchaseOrd : listPurchaseOrder) {

								ordNetTot += purchaseOrd.getOrdnettotal();
								ordTotQty += purchaseOrd.getOrdtotalQty();
							}
							System.out.println("" + ordNetTot);
							model.addAttribute("poCom", listPurchaseOrder.size());
							model.addAttribute("totQtyPOCom", ordTotQty);
							model.addAttribute("netPOCom", ordNetTot);

							// Not Completed PO
							String queryStatNotCom = "from PurchaseOrder t where t.ordPRQid like :param1 and t.ordStatus!='completed' and t.ordStatus!='closed'";
							query = session.createQuery(queryStatNotCom);
							query.setParameter("param1", Vnb2);
							List<PurchaseOrder> listPurchaseOrderT = query.list();

							float netPoNotCom = 0;
							float totQtyPoNotCom = 0;
							for (PurchaseOrder purchaseOrd : listPurchaseOrderT) {

								netPoNotCom += purchaseOrd.getOrdnettotal();
								totQtyPoNotCom += purchaseOrd.getOrdtotalQty();
							}
							model.addAttribute("poNotCom", listPurchaseOrderT.size());
							model.addAttribute("netPoNotCom", netPoNotCom);
							model.addAttribute("totQtyPoNotCom", totQtyPoNotCom);

							//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							// Completed GR
							String getGRCVD = "from GoodsReceived where grPRQid like :param1";
							query = session.createQuery(getGRCVD);
							query.setParameter("param1", Vnb2);
							List<GoodsReceived> listGoodsReceived = query.list();

							float grNetTot = 0;
							float grTotQty = 0;
							for (GoodsReceived goodsRCVD : listGoodsReceived) {

								grNetTot += goodsRCVD.getGrnettotal();
								grTotQty += goodsRCVD.getGrtotalQty();
							}
							model.addAttribute("grCom", listGoodsReceived.size());
							model.addAttribute("netGrCom", grNetTot);
							model.addAttribute("totQtyGrCom", grTotQty);

							// Not Completed GR
							String queryGRNotCom = "from GoodsReceived where grPRQid like :param1 and grStatus!='completed' and grStatus!='closed'";
							query = session.createQuery(queryGRNotCom);
							query.setParameter("param1", Vnb2);
							List<GoodsReceived> listGoodsReceivedNotComp = query.list();

							float netGRNotCom = 0;
							float totQtyGRNotCom = 0;
							for (GoodsReceived goodsRCVD : listGoodsReceivedNotComp) {

								netGRNotCom += goodsRCVD.getGrnettotal();
								totQtyGRNotCom += goodsRCVD.getGrtotalQty();
							}
							System.out.println("kjfwinvnwoinv" + listGoodsReceivedNotComp.size());

							model.addAttribute("grNotCom", listGoodsReceivedNotComp.size());
							model.addAttribute("netGRNotCom", netGRNotCom);
							model.addAttribute("totQtyGRNotCom", totQtyGRNotCom);

							/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

							String getDNew = "select a.dnTotalAmount,a.dnTotalQty from DiscoveryNew a,DiscoveryNewItem b,PurchaseOrder c where c.ordPRQid like :param1 and a.dnID=b.dniDNID and b.dniPOID=c.ID";
							query = session.createQuery(getDNew);
							query.setParameter("param1", Vnb2);
							List<Object[]> listDiscovNew = query.list();

							float dnNetTot = 0;
							float dnTotQty = 0;
							for (Object[] discovNew : listDiscovNew) {

								dnNetTot += (float) discovNew[0];
								dnTotQty += (float) discovNew[1];
							}

							model.addAttribute("dnComp", listDiscovNew.size());
							model.addAttribute("dnNetTotComp", dnNetTot);
							model.addAttribute("dnTotQtyComp", dnTotQty);

							String getDNewNotComp = "select a.dnTotalAmount,a.dnTotalQty from DiscoveryNew a,DiscoveryNewItem b,PurchaseOrder c where c.ordPRQid like :param1 and a.dnID=b.dniDNID and b.dniPOID=c.ID and a.dnStatus!='completed' and a.dnStatus!='closed'";
							query = session.createQuery(getDNewNotComp);
							query.setParameter("param1", Vnb2);
							List<Object[]> listDiscovNewNotComp = query.list();

							float dnNettTot = 0;
							float dnTottQty = 0;
							for (Object[] discovNew : listDiscovNewNotComp) {

								dnNettTot += (float) discovNew[0];
								dnTottQty += (float) discovNew[1];
							}
							System.out.println("yyyyyyyyyyyyyyyyyyyyyyy" + listGoodsReceivedNotComp.size());
							model.addAttribute("dnNotComp", listDiscovNewNotComp.size());
							model.addAttribute("dnNetTotNotComp", dnNettTot);
							model.addAttribute("dnTotQtyNotComp", dnTottQty);

							//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

							String getCapitalAll = "select b.cipvaluationRate,b.TOTALQTY from CapitalInProgress b,PurchaseOrder a,PurchaseRequest c where c.ID like:param1 and a.ordPRQid=c.ID and b.PoId=a.ID";
							query = session.createQuery(getCapitalAll);
							query.setParameter("param1", Vnb2);
							List<Object[]> listCapitalAll = query.list();

							float cipNetTot = 0;
							float cipTotQty = 0;
							for (Object[] capitalInProg : listCapitalAll) {

								cipNetTot += (float) capitalInProg[0];
								cipTotQty += (float) capitalInProg[1];
							}

							model.addAttribute("cipCountAll", listCapitalAll.size());
							model.addAttribute("cipNetTotAll", cipNetTot);
							model.addAttribute("cipTotQtyAll", cipTotQty);

							///////////////////////////////////////////////////////////////////////////////////////

							String getAssetRegAll = "select b.arvaluationRate,b.arID from AssetRegistry b,PurchaseOrder a,PurchaseRequest c where c.ID like:param1 and a.ordPRQid=c.ID and b.poID=a.ID";
							query = session.createQuery(getAssetRegAll);
							query.setParameter("param1", Vnb2);
							List<Object[]> listAssetRegAll = query.list();
							float arValRate = 0;

							for (Object[] assetReg : listAssetRegAll) {

								arValRate = (float) assetReg[0];

							}

							model.addAttribute("arCountAll", listAssetRegAll.size());
							model.addAttribute("arValRateAll", arValRate);

							////////////////////////////////////////////////////////////////////////////////////////////

							String getFixedAssetRegAll = "select a.iniCost,a.netCost from FixedAssetRegistry a,PurchaseOrder b,PurchaseRequest c where c.ID like :param1 and a.PoId=b.ID and b.ordPRQid=c.ID";
							query = session.createQuery(getFixedAssetRegAll);
							query.setParameter("param1", Vnb2);
							List<Object[]> listFixedAssetRegAll = query.list();

							float farValRate = 0;

							for (Object[] fixedAssetReg : listFixedAssetRegAll) {

								farValRate += (float) fixedAssetReg[0];

							}

							model.addAttribute("farCountAll", listFixedAssetRegAll.size());
							model.addAttribute("farValRateAll", farValRate);

							//////////////////////////////////////////////////////////////////////////////////

						}

						// PO

						else if (StringUtils.equalsIgnoreCase(VType2, "po")
								&& !StringUtils.equalsIgnoreCase(Vnb2, "")) {

							String getPO = " from PurchaseOrder where ID like :param1";
							query = session.createQuery(getPO);
							query.setParameter("param1", Vnb2);
							List<PurchaseOrder> listPurchaseOrd = query.list();

							float poNetTot = 0;
							float poTotQty = 0;
							String poStatus = "";
							String poId = "";

							for (PurchaseOrder purchaseOrd : listPurchaseOrd) {

								poNetTot = purchaseOrd.getOrdnettotal();
								poTotQty = purchaseOrd.getOrdtotalQty();
								poStatus = purchaseOrd.getOrdStatus();
								poId = purchaseOrd.getPurchaseOrdID();
							}
							model.addAttribute("poStatus", poStatus);
							model.addAttribute("poId", poId);
							model.addAttribute("poTotQty", poTotQty);
							model.addAttribute("poNetTot", poNetTot);

							/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

							String getGReceivedComp = "from GoodsReceived where grPOid like :param1";
							query = session.createQuery(getGReceivedComp);
							query.setParameter("param1", Vnb2);
							List<GoodsReceived> listGoodsRcved = query.list();

							float goodsrNetTot = 0;
							float goodsrTotQty = 0;
							for (GoodsReceived goodsRCVD : listGoodsRcved) {

								goodsrNetTot += goodsRCVD.getGrnettotal();
								goodsrTotQty += goodsRCVD.getGrtotalQty();
							}

							model.addAttribute("grCom", listGoodsRcved.size());
							model.addAttribute("netGrCom", goodsrNetTot);
							model.addAttribute("totQtyGrCom", goodsrTotQty);

							String getGReceivedNotComp = "from GoodsReceived where grPOid like :param1 and grStatus!='completed' and grStatus!='closed'";
							query = session.createQuery(getGReceivedNotComp);
							query.setParameter("param1", Vnb2);
							List<GoodsReceived> listGoodsRcvedNotComp = query.list();

							float goodsrNetToT = 0;
							float goodsrTotQtY = 0;
							for (GoodsReceived goodsRCVD : listGoodsRcvedNotComp) {

								goodsrNetToT += goodsRCVD.getGrnettotal();
								goodsrTotQtY += goodsRCVD.getGrtotalQty();
							}

							model.addAttribute("grNotCom", listGoodsRcved.size());
							model.addAttribute("netGRNotCom", goodsrNetToT);
							model.addAttribute("totQtyGRNotCom", goodsrTotQtY);

							/////////////////////////////////////////////////////////////////////////////////////////////////

							String getPReq = "select b.netTotal,b.TotalQty,b.ID,b.prStatus from PurchaseOrder t,PurchaseRequest b where t.ordPRQid=b.ID and t.ID like :param1";
							query = session.createQuery(getPReq);
							query.setParameter("param1", Vnb2);
							List<Object[]> listPurchReq = query.list();

							String PurchReqId = "";
							String prStatus = "";
							float prNetTot = 0;
							float prTotQty = 0;
							for (Object[] purchaseReq : listPurchReq) {

								prNetTot += (float) purchaseReq[0];
								prTotQty += (float) purchaseReq[1];
								PurchReqId = "" + purchaseReq[2];
								prStatus = "" + purchaseReq[3];
							}
							model.addAttribute("prId", PurchReqId);
							model.addAttribute("prStatus", prStatus);
							model.addAttribute("prNetTot", prNetTot);
							model.addAttribute("prTotQty", prTotQty);
							/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

							String getDNew = "select a.dnTotalAmount,a.dnTotalQty from DiscoveryNew a,DiscoveryNewItem b where b.dniPOID like :param1 and a.dnID=b.dniDNID";
							query = session.createQuery(getDNew);
							query.setParameter("param1", Vnb2);
							List<Object[]> listDiscovNewAll = query.list();

							float dnNettTot = 0;
							float dnTottQty = 0;
							for (Object[] discovNew : listDiscovNewAll) {

								dnNettTot += (float) discovNew[0];
								dnTottQty += (float) discovNew[1];
							}

							model.addAttribute("dnComp", listDiscovNewAll.size());
							model.addAttribute("dnNetTotComp", dnNettTot);
							model.addAttribute("dnTotQtyComp", dnTottQty);

							String getDNewNotComp = "select a.dnTotalAmount,a.dnTotalQty from DiscoveryNew a,DiscoveryNewItem b where b.dniPOID like :param1 and a.dnID=b.dniDNID and a.dnStatus!='completed' and a.dnStatus!='closed'";
							query = session.createQuery(getDNewNotComp);
							query.setParameter("param1", Vnb2);
							List<Object[]> listDiscovNewNotComp = query.list();
							// List<Object[]> listDiscovNewNotComp = (List<Object[]>)
							// appConfig.findAllByMultiClzQryPrms(getDNewNotComp, "param1", Vnb2);

							float dnNetTot = 0;
							float dnTotQty = 0;
							for (Object[] discovNew : listDiscovNewNotComp) {

								dnNetTot += (float) discovNew[0];
								dnTotQty += (float) discovNew[1];
							}

							model.addAttribute("dnNotComp", listDiscovNewNotComp.size());
							model.addAttribute("dnNetTotNotComp", dnNetTot);
							model.addAttribute("dnTotQtyNotComp", dnTotQty);

							/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

							String getCapitalInProgr = "select a.cipvaluationRate,a.TOTALQTY from CapitalInProgress a,PurchaseOrder b where b.ID like:param1 and a.PoId=b.ID";
							query = session.createQuery(getCapitalInProgr);
							query.setParameter("param1", Vnb2);
							List<Object[]> listCapitalAll = query.list();
							// List<Object[]> listCapitalAll = (List<Object[]>)
							// appConfig.findAllByMultiClzQryPrms(getCapitalInProgr, "param1", Vnb2);

							float cipNetTot = 0;
							float cipTotQty = 0;
							for (Object[] capitalInProg : listCapitalAll) {

								cipNetTot += (float) capitalInProg[0];
								cipTotQty += (float) capitalInProg[1];
							}

							model.addAttribute("cipCountAll", listCapitalAll.size());
							model.addAttribute("cipNetTotAll", cipNetTot);
							model.addAttribute("cipTotQtyAll", cipTotQty);
							///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							String getAssetReg = "select b.arvaluationRate,b.arID from AssetRegistry b,PurchaseOrder a where a.ID like:param1 and  b.poID=a.ID";
							query = session.createQuery(getAssetReg);
							query.setParameter("param1", Vnb2);
							List<Object[]> listAssetRegAll = query.list();
							// List<Object[]> listAssetRegAll = (List<Object[]>)
							// appConfig.findAllByMultiClzQryPrms(getAssetReg, "param1", Vnb2);

							float arValRate = 0;

							for (Object[] assetReg : listAssetRegAll) {

								arValRate = (float) assetReg[0];

							}

							model.addAttribute("arCountAll", listAssetRegAll.size());
							model.addAttribute("arValRateAll", arValRate);
							//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							String getFixedAssetReg = "select a.iniCost,a.netCost from FixedAssetRegistry a,PurchaseOrder b where b.ID like :param1 and a.PoId=b.ID";
							query = session.createQuery(getFixedAssetReg);
							query.setParameter("param1", Vnb2);
							List<Object[]> listFixedAssetRegAll = query.list();

							// List<Object[]> listFixedAssetRegAll = (List<Object[]>)
							// appConfig.findAllByMultiClzQryPrms(getFixedAssetReg, "param1", Vnb2);

							float farValRate = 0;

							for (Object[] fixedAssetReg : listFixedAssetRegAll) {

								farValRate += (float) fixedAssetReg[0];

							}

							model.addAttribute("farCountAll", listFixedAssetRegAll.size());
							model.addAttribute("farValRateAll", farValRate);

						}
						////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

						// GR

						else if (StringUtils.equalsIgnoreCase(VType2, "gr")
								&& !StringUtils.equalsIgnoreCase(Vnb2, "")) {

							String getPO = " from GoodsReceived where ID like :param1";
							query = session.createQuery(getPO);
							query.setParameter("param1", Vnb2);
							List<GoodsReceived> listGoodsReceived = query.list();

							float grNetTot = 0;
							float grTotQty = 0;
							String grStatus = "";
							String grId = "";

							for (GoodsReceived goodsRec : listGoodsReceived) {

								grNetTot = goodsRec.getGrnettotal();
								grTotQty = goodsRec.getGrtotalQty();
								grStatus = goodsRec.getGrStatus();
								grId = goodsRec.getGoodsReceiveID();
							}
							model.addAttribute("grStatus", grStatus);
							model.addAttribute("grId", grId);
							model.addAttribute("grTotQty", grTotQty);
							model.addAttribute("grNetTot", grNetTot);

							/////////////////////////////////////////////////////////////////

							String getPurchREq = "select b.ID,b.prStatus,b.netTotal,b.TotalQty from GoodsReceived a,PurchaseRequest b where a.ID like :param1 and a.grPRQid=b.ID";
							query = session.createQuery(getPurchREq);
							query.setParameter("param1", Vnb2);
							List<Object[]> listPurchReq = query.list();
							// List<Object[]> listPurchReq = (List<Object[]>)
							// appConfig.findAllByMultiClzQryPrms(getPurchREq, "param1", Vnb2);

							String PurchReqId = "";
							String prStatus = "";
							float prNetTot = 0;
							float prTotQty = 0;
							for (Object[] purchaseReq : listPurchReq) {

								prNetTot += (float) purchaseReq[2];
								prTotQty += (float) purchaseReq[3];
								PurchReqId = "" + purchaseReq[0];
								prStatus = "" + purchaseReq[1];
							}
							model.addAttribute("grPurchReqId", PurchReqId);
							model.addAttribute("grPrStatus", prStatus);
							model.addAttribute("grPrNetTot", prNetTot);
							model.addAttribute("grPrTotQty", prTotQty);
							/////////////////////////////////////////////////////
							String getPurchOrd = "select b.ID,b.ordStatus,b.netTotal,b.TotalQty from GoodsReceived a,PurchaseOrder b where a.ID like :param1 and a.grPOid=b.ID";
							query = session.createQuery(getPurchOrd);
							query.setParameter("param1", Vnb2);
							List<Object[]> listPurchOrd = query.list();
							// List<Object[]> listPurchOrd = (List<Object[]>)
							// appConfig.findAllByMultiClzQryPrms(getPurchOrd, "param1", Vnb2);

							float grOrdnetTot = 0;
							float grOrdtotQty = 0;
							String grPurchOrdId = "";
							String grPoStatus = "";
							for (Object[] purchaseOrd : listPurchOrd) {

								grOrdnetTot += (float) purchaseOrd[2];
								grOrdtotQty += (float) purchaseOrd[3];
								grPurchOrdId = "" + purchaseOrd[0];
								grPoStatus = "" + purchaseOrd[1];
							}
							model.addAttribute("grPurchOrdId", grPurchOrdId);
							model.addAttribute("grPoStatus", grPoStatus);
							model.addAttribute("grOrdtotQty", grOrdtotQty);
							model.addAttribute("grOrdnetTot", grOrdnetTot);
							/////////////////////////////////////////////////////////
							String getDiscovNew = "select a.dnID,a.dnStatus,a.dnTotalAmount,a.dnTotalQty from DiscoveryNew a,DiscoveryNewItem b,GoodsReceived d where d.ID like :param1 and a.dnID=b.dniDNID and b.dniPOID=d.grPOid";
							query = session.createQuery(getDiscovNew);
							query.setParameter("param1", Vnb2);
							List<Object[]> listDiscovNew = query.list();
							// List<Object[]> listDiscovNew = (List<Object[]>)
							// appConfig.findAllByMultiClzQryPrms(getDiscovNew, "param1", Vnb2);

							float grDN_NetTot = 0;
							float grDN_TotQty = 0;
							String grDN_Id = "";
							String grDN_Status = "";
							for (Object[] discovvNew : listDiscovNew) {

								grDN_NetTot += (float) discovvNew[2];
								grDN_TotQty += (float) discovvNew[3];
								grDN_Id = "" + discovvNew[0];
								grDN_Status = "" + discovvNew[1];
							}
							model.addAttribute("grDN_Id", grDN_Id);
							model.addAttribute("grDN_Status", grDN_Status);
							model.addAttribute("grDN_TotQty", grDN_TotQty);
							model.addAttribute("grDN_NetTot", grDN_NetTot);
							//////////////////////////////////////////////////////////////////////////////////////

							String getCapInProg = "select a.cipvaluationRate,a.TOTALQTY from CapitalInProgress a,GoodsReceived b where b.ID like:param1 and a.PoId=b.grPOid";
							query = session.createQuery(getCapInProg);
							query.setParameter("param1", Vnb2);
							List<Object[]> listCapitalAll = query.list();
							// List<Object[]> listCapitalAll = (List<Object[]>)
							// appConfig.findAllByMultiClzQryPrms(getCapInProg, "param1", Vnb2);

							float grCipNetTot = 0;
							float grCipTotQty = 0;
							for (Object[] capitalInProg : listCapitalAll) {

								grCipNetTot += (float) capitalInProg[0];
								grCipTotQty += (float) capitalInProg[1];
							}

							model.addAttribute("cipCountAll", listCapitalAll.size());
							model.addAttribute("cipNetTotAll", grCipNetTot);
							model.addAttribute("cipTotQtyAll", grCipTotQty);
							///////////////////////////////////////////////////////////////////////////////////////

							String getAssetRegAll = "select b.arvaluationRate,b.arID from AssetRegistry b,GoodsReceived a where a.ID like:param1 and  b.poID=a.grPOid";
							query = session.createQuery(getAssetRegAll);
							query.setParameter("param1", Vnb2);
							List<Object[]> listAssetRegAll = query.list();
							// List<Object[]> listAssetRegAll = (List<Object[]>)
							// appConfig.findAllByMultiClzQryPrms(getAssetRegAll, "param1", Vnb2);
							float arValRate = 0;

							for (Object[] assetReg : listAssetRegAll) {

								arValRate = (float) assetReg[0];

							}

							model.addAttribute("arCountAll", listAssetRegAll.size());
							model.addAttribute("arValRateAll", arValRate);

							////////////////////////////////////////////////////////////////////////////////////////////

							String getFixedAssetRegAll = "select a.iniCost,a.netCost from FixedAssetRegistry a,GoodsReceived b where b.ID like :param1 and a.PoId=b.grPOid";
							query = session.createQuery(getFixedAssetRegAll);
							query.setParameter("param1", Vnb2);
							List<Object[]> listFixedAssetRegAll = query.list();
							// List<Object[]> listFixedAssetRegAll = (List<Object[]>)
							// appConfig.findAllByMultiClzQryPrms(getFixedAssetRegAll, "param1", Vnb2);

							float farValRate = 0;

							for (Object[] fixedAssetReg : listFixedAssetRegAll) {

								farValRate += (float) fixedAssetReg[0];

							}
							model.addAttribute("farCountAll", listFixedAssetRegAll.size());
							model.addAttribute("farValRateAll", farValRate);

							///////////////////////////////////////////////////////////// end////////////////////////////////////////////////////

						}

						else {
							model.addAttribute("actionType", "defaultPage");
						}

					}
				} catch (Exception e) {
					logger.info("Error on home with a message : " + e);
					e.printStackTrace();

				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
					}
				}

			}

			return "home";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetallVtypehome", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetallVtypehome(Locale locale, Model model, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		// logger.info("Welcome home! The client locale is {}.", locale);
		// System.out.println("Welcome to GetAllSupplier");

		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		String requestName = null;
		// List<PurchaseOrder> listrequest = new ArrayList<PurchaseOrder>();
		String VType = request.getParameter("voucherType");
		System.out.println("the Vtype is " + VType);

		Session session = AlmDbSession.getInstance().getSession();
		Transaction tx = session.beginTransaction();
		notifications.headerNotifications(session, model);
		if (StringUtils.equalsIgnoreCase(VType, "pr")) {
			List<PurchaseRequest> listrequest = new ArrayList<PurchaseRequest>();
			requestName = "%" + request.getParameter("VoucherNb") + "%";

			query = session.createQuery("select ID ,' ' from PurchaseRequest where ID like :param1");
			query.setParameter("param1", requestName);
			query.setFirstResult(0);
			query.setMaxResults(40);
			listrequest = query.list();
			tx.commit();
			session.close();
			
			System.out.println("the list is " + mapper.writeValueAsString(listrequest));
			model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
			rtn.put("Listreq", listrequest);

		}
		if (StringUtils.equalsIgnoreCase(VType, "po")) {
			List<PurchaseOrder> listrequest = new ArrayList<PurchaseOrder>();
			requestName = "%" + request.getParameter("VoucherNb") + "%";

			query = session.createQuery("select ID ,' ' from PurchaseOrder where ID like :param1");
			query.setParameter("param1", requestName);
			query.setFirstResult(0);
			query.setMaxResults(40);
			listrequest = query.list();
			tx.commit();
			session.close();
			
			System.out.println("the list is " + mapper.writeValueAsString(listrequest));
			model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
			rtn.put("Listreq", listrequest);
		}
		if (StringUtils.equalsIgnoreCase(VType, "gr")) {
			List<GoodsReceived> listrequest = new ArrayList<GoodsReceived>();
			requestName = "%" + request.getParameter("VoucherNb") + "%";

			query = session.createQuery("select ID ,' '  from GoodsReceived where ID like :param1");
			query.setParameter("param1", requestName);
			query.setFirstResult(0);
			query.setMaxResults(40);
			listrequest = query.list();
			tx.commit();
			session.close();
			
			System.out.println("the list is " + mapper.writeValueAsString(listrequest));
			model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
			rtn.put("Listreq", listrequest);
		}
		if (StringUtils.equalsIgnoreCase(VType, "ar")) {
			List<AssetRegistry> listrequest = new ArrayList<AssetRegistry>();
			requestName = "%" + request.getParameter("VoucherNb") + "%";

			query = session.createQuery("select arID ,' '  from AssetRegistry where arID like :param1");
			query.setParameter("param1", requestName);
			query.setFirstResult(0);
			query.setMaxResults(40);
			listrequest = query.list();
			tx.commit();
			session.close();
			
			System.out.println("the list is " + mapper.writeValueAsString(listrequest));
			model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
			rtn.put("Listreq", listrequest);
		}
		if (StringUtils.equalsIgnoreCase(VType, "far")) {
			List<FixedAssetRegistry> listrequest = new ArrayList<FixedAssetRegistry>();
			requestName = "%" + request.getParameter("VoucherNb") + "%";

			query = session.createQuery("select farID ,' '  from FixedAssetRegistry where farID like :param1");
			query.setParameter("param1", requestName);
			query.setFirstResult(0);
			query.setMaxResults(40);
			listrequest = query.list();
			tx.commit();
			session.close();
			
			System.out.println("the list is " + mapper.writeValueAsString(listrequest));
			model.addAttribute("Listreq", mapper.writeValueAsString(listrequest));
			rtn.put("Listreq", listrequest);
		}
		return rtn;
	}
}
