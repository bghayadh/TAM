package com.aliat.alm.controller;

import java.math.BigDecimal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.aliat.alm.models.AssetLifeCycle;
import com.aliat.alm.models.FixedAssetRegistry;
import com.aliat.alm.models.GoodsReceivedItem;
import com.aliat.alm.models.PurchaseOrderItem;
import com.aliat.alm.models.PurchaseRequestItem;
import com.aliat.alm.services.ItemParameters;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AssetLifeCycleThread extends Thread {
	private static final String PR_ITEMCODE = "prItemCode";
	private static final String PO_ITEMCODE = "itemCode";
	private static final String GR_ITEMCODE = "itemCode";
	private Session session = null;
	private Transaction tx = null;
	private String queryString = null;
	private String fullItmCode = null;
	private String itmCode = null;
	private Query query = null;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	String formView;
	String pRqID;
	String pOID;
	String grID;
	ItemParameters itemParameters;

	public AssetLifeCycleThread(String formView, String prID, String poID, String grID, ItemParameters itemParameters) {
		this.formView = formView;
		this.pRqID = prID;
		this.pOID = poID;
		this.grID = grID;
		this.itemParameters = itemParameters;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {

		Timestamp date;
		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		session = sf.openSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();

			try {
				if (StringUtils.equalsIgnoreCase(formView, "PR")) {
					ArrayList<PurchaseRequestItem> prItemId = new ArrayList<>();
					String[] prItemArray;
					// the following query is to make sure that what have inserted into the table of
					// purchase request item are only acceptable to be inserted into the asset life
					// cycle
					// if a problem happened there on the level of pRqitem table .
					queryString = " select prqItemId from PurchaseRequestItem where PRQId like :param_1 order by CreationDate asc";
					query = session.createQuery(queryString);
					query.setParameter("param_1", pRqID);
					prItemId = new ArrayList<PurchaseRequestItem>(query.list());
					prItemArray = new String[prItemId.size()];
					prItemId.toArray(prItemArray);
					date = new Timestamp(System.currentTimeMillis());
					for (int j = 0; j < itemParameters.getDictParameter().size() && j < prItemArray.length; j++) {

						fullItmCode = itemParameters.getDictParameter().get(j).get(PR_ITEMCODE);
						itmCode = fullItmCode.substring(0, fullItmCode.indexOf(":"));

						// insert the BOQ of the Purchase Request into the Asset Life Cycle.
						
					}
				} else if (StringUtils.equalsIgnoreCase(formView, "PO")) {
					List<PurchaseOrderItem> poItemiId;
					String[] poItemArray;

					queryString = " select pordItemId from PurchaseOrderItem where POId like :param_1 order by CreationDate asc";
					query = session.createQuery(queryString);
					query.setString("param_1", pOID);
					poItemiId = query.list();
					poItemArray = new String[poItemiId.size()];
					poItemiId.toArray(poItemArray);
					date = new Timestamp(System.currentTimeMillis());
					for (int j = 0; j < itemParameters.getDictParameter().size() && j < poItemArray.length; j++) {

						fullItmCode = itemParameters.getDictParameter().get(j).get(PO_ITEMCODE);
						itmCode = fullItmCode.substring(0, fullItmCode.indexOf(":"));
						}

					String pRQ = pRqID;
					if (!pRQ.isEmpty() && pRQ != null) {
						queryString = " select prStatus from PurchaseRequest where ID = :param_1";
						String prStat;
						query = session.createQuery(queryString);
						query.setString("param_1", pRQ);
						prStat = query.uniqueResult().toString();
						if (StringUtils.equalsIgnoreCase(prStat, "approved")
								|| StringUtils.equalsIgnoreCase(prStat, "closed")) {

							queryString = " select count(*) from PurchaseOrder where ordPRQid =:param_1 ";
							String allPurchOrds;
							String allPurchOrdsApproved;
							query = session.createQuery(queryString);
							query.setString("param_1", pRQ);
							allPurchOrds = query.uniqueResult().toString();
							queryString = " select count(*) from PurchaseOrder where ordPRQid = :param_1 and ordStatus = 'approved' ";
							query = session.createQuery(queryString);
							query.setString("param_1", pRQ);
							allPurchOrdsApproved = query.uniqueResult().toString();

							if (Double.parseDouble(allPurchOrds) == Double.parseDouble(allPurchOrdsApproved)) {
								List<Object[]> purchReqItem;
								queryString = "select distinct ItemCode,sum(Qty) from PurchaseRequestItem where PRQId like :param1 group by ItemCode";
								query = session.createQuery(queryString);
								query.setString("param1", pRQ);
								purchReqItem = query.list();
								String itemCode = null;
								double qty;
								double resQTYPO;
								String resStatus;
								for (Object[] item : purchReqItem) {

									itemCode = (String) item[0];
									qty = (double) item[1];
									queryString = "select sum(a.Qty) from PurchaseOrderItem a, PurchaseOrder b where a.ItemCode like :param_1 and b.ordPRQid like :param_2 and a.POId = b.ID";
									query = session.createQuery(queryString);
									query.setString("param_1", itemCode);
									query.setString("param_2", pRQ);
									if (query.uniqueResult() != null) {
										resQTYPO = (double) query.uniqueResult();
										if (resQTYPO >= qty) {
											resStatus = "1";
										
										queryString = "update PurchaseRequestItem set prqPoStatus = :param_1  where PRQId = :param_2 and ItemCode = :param_3";
										query = session.createQuery(queryString);
										query.setString("param_1", resStatus);
										query.setString("param_2", pRQ);
										query.setString("param_3", (String) itemCode);
										query.executeUpdate();
									}
								}
								}
								List<Object[]> purchReqItemList;
								queryString = " select distinct ItemCode from PurchaseRequestItem where PRQId like :param1 and prqPoStatus = '1'";
								query = session.createQuery(queryString);
								query.setString("param1", pRQ);
								purchReqItemList = query.list();
								if (purchReqItem.size() == purchReqItemList.size()) {
									queryString = "update PurchaseRequest set prStatus = :param_1  where ID = :param_2 ";
									query = session.createQuery(queryString);
									query.setParameter("param_1", "completed");
									query.setParameter("param_2", pRQ);
									query.executeUpdate();
								}

							}
						}
					}

				}

				else if (StringUtils.equalsIgnoreCase(formView, "GR")) {

					queryString = "select grItemId from GoodsReceivedItem where GRid like :param_1 order by CreationDate asc";
					query = session.createQuery(queryString);
					query.setString("param_1", grID);
					List<GoodsReceivedItem> GrItemiId = (List<GoodsReceivedItem>) query.list();

					ArrayList<GoodsReceivedItem> grItemId = new ArrayList<GoodsReceivedItem>(GrItemiId);

					String[] grItemArray = new String[grItemId.size()];
					grItemId.toArray(grItemArray);
					Timestamp dateNow = new Timestamp(System.currentTimeMillis());
					for (int i = 0; i < itemParameters.getDictParameter().size() && i < grItemArray.length; i++) {

						fullItmCode = itemParameters.getDictParameter().get(i).get(GR_ITEMCODE);
						itmCode = fullItmCode.substring(0, fullItmCode.indexOf(":"));

						InsertGrIntoALC(session, grID, grItemArray[i], itmCode, dateNow, dateNow);
					}

					String grItemStatus = "1";
					queryString = "update GoodsReceivedItem set grStatus = :param_1  where GRid = :param_2";
					query = session.createQuery(queryString);
					query.setString("param_1", grItemStatus);
					query.setString("param_2", grID);
					query.executeUpdate();

					///////////////////////////////////////////////////////////////////
					// get poID related to gr
					queryString = "select NVL(grPOid,'null') from GoodsReceived where ID = :param_1";
					query = session.createQuery(queryString);
					query.setString("param_1", grID);
					Object pOrdId = query.uniqueResult();
					queryString = "select NVL(grPRQid,'null') from GoodsReceived where ID = :param_1";
					query = session.createQuery(queryString);
					query.setString("param_1", grID);
					Object prdId = query.uniqueResult();
					if (!StringUtils.equalsIgnoreCase((CharSequence) pOrdId,"null")) {
						queryString = "select ordStatus from PurchaseOrder where ID = :param_1";
					query = session.createQuery(queryString);
					query.setString("param_1",(String) pOrdId);

					String poStat = query.uniqueResult().toString();

					if (StringUtils.equalsIgnoreCase(poStat, "approved")
							|| StringUtils.equalsIgnoreCase(poStat, "closed")) {
						queryString = " select count(*) from GoodsReceived where grPOid =:param_1 ";
						query = session.createQuery(queryString);
						query.setString("param_1", (String) pOrdId);
						String allGoodsRec = query.uniqueResult().toString();
						queryString = " select count(*) from GoodsReceived where grPOid = :param_1 and grStatus = 'completed' ";
						query = session.createQuery(queryString);
						query.setString("param_1", (String) pOrdId);
						String allGoodsRecApproved = query.uniqueResult().toString();
						if (Double.parseDouble(allGoodsRec) == Double.parseDouble(allGoodsRecApproved)) {
							queryString = "select distinct ItemCode from PurchaseOrderItem where POId like :param1";
							query = session.createQuery(queryString);
							query.setString("param1", (String) pOrdId);
							List<Object[]> purchOrdItem = (List<Object[]>) query.list();
							Object itemCode = null;
							for (Object PurchOrdItem : purchOrdItem) {
								itemCode = "" + PurchOrdItem;
								queryString = "select sum(Qty) from PurchaseOrderItem where ItemCode like :param_1 and POId like :param_2 ";
								query = session.createQuery(queryString);
								query.setString("param_1", (String) itemCode);
								query.setString("param_2", (String) pOrdId);
								double qty = (double) query.uniqueResult();
								queryString = "select sum(a.Qty) from GoodsReceivedItem a, GoodsReceived b where a.ItemCode like :param_1 and b.grPOid like :param_2 and a.GRid = b.ID";

								query = session.createQuery(queryString);
								query.setString("param_1", (String) itemCode);
								query.setString("param_2", (String) pOrdId);

								double resQTYGR = (double) query.uniqueResult();// appConfig.findAllByMultiClzMultiQryPrms(queryStatement,
																				// params);
								String resStatus = "0";
								if (resQTYGR >= qty) {

									resStatus = "1";
									queryString = "update PurchaseOrderItem set poItemStatus = :param_1  where POId = :param_2 and ItemCode = :param_3";
									query = session.createQuery(queryString);
									query.setString("param_1", resStatus);
									query.setString("param_2", (String) pOrdId);
									query.setString("param_3", (String) itemCode);
									query.executeUpdate();
								} else {
									queryString = "from FixedAssetRegistry where faritemCode like :param_1 and PoId like :param_2";
									query = session.createQuery(queryString);
									query.setString("param_1", (String) itemCode);
									query.setString("param_2", (String) pOrdId);
									List<FixedAssetRegistry> itemInFAR = (List<FixedAssetRegistry>) query
											.setResultTransformer(Transformers.aliasToBean(FixedAssetRegistry.class))
											.list();

									if ((itemInFAR.size() + resQTYGR) >= qty) {
										resStatus = "1";
										queryString = "update PurchaseOrderItem set poItemStatus = :param_1  where POId = :param_2 and ItemCode = :param_3";
										query = session.createQuery(queryString);
										query.setString("param_1", resStatus);
										query.setString("param_2", (String) pOrdId);
										query.setString("param_3", (String) itemCode);
										query.executeUpdate();
									} 

								}
							}

							queryString = " select distinct ItemCode from PurchaseOrderItem where POId like :param1 and poItemStatus = '1'";
							query = session.createQuery(queryString);
							query.setString("param1", (String) pOrdId);
							List<Object[]> purchOrdItemList = (List<Object[]>) query.list();
							if (purchOrdItem.size() == purchOrdItemList.size()) {
								queryString = "update PurchaseOrder set ordStatus = :param_1  where ID = :param_2 ";
								query = session.createQuery(queryString);
								query.setString("param_1", "completed");
								query.setString("param_2", (String) pOrdId);
								query.executeUpdate();

							}
						}
					}
				}
					
					
					
					
					
					
				
					if(!StringUtils.equalsIgnoreCase((CharSequence) prdId,"null")) {
					queryString = "select prStatus from PurchaseRequest where ID = :param_1";
					query = session.createQuery(queryString);
					query.setString("param_1",(String) prdId);

					String poStat = query.uniqueResult().toString();

					if (StringUtils.equalsIgnoreCase(poStat, "approved")
							|| StringUtils.equalsIgnoreCase(poStat, "closed")) {
						queryString = " select count(*) from GoodsReceived where grPRQid =:param_1 ";
						query = session.createQuery(queryString);
						query.setString("param_1", (String) prdId);
						String allGoodsRec = query.uniqueResult().toString();
						queryString = " select count(*) from GoodsReceived where grPRQid = :param_1 and grStatus = 'completed' ";
						query = session.createQuery(queryString);
						query.setString("param_1", (String) prdId);
						String allGoodsRecApproved = query.uniqueResult().toString();
						if (Double.parseDouble(allGoodsRec) == Double.parseDouble(allGoodsRecApproved)) {
							queryString = "select distinct ItemCode from PurchaseRequestItem where PRQId like :param1";
							query = session.createQuery(queryString);
							query.setString("param1", (String) prdId);
							List<Object[]> purchOrdItem = (List<Object[]>) query.list();
							Object itemCode = null;
							for (Object PurchOrdItem : purchOrdItem) {
								itemCode = "" + PurchOrdItem;
								queryString = "select sum(Qty) from PurchaseRequestItem where ItemCode like :param_1 and PRQId like :param_2 ";
								query = session.createQuery(queryString);
								query.setString("param_1", (String) itemCode);
								query.setString("param_2", (String) prdId);
								double qty = (double) query.uniqueResult();
								queryString = "select sum(a.Qty) from GoodsReceivedItem a, GoodsReceived b where a.ItemCode like :param_1 and b.grPRQid like :param_2 and a.GRid = b.ID";

								query = session.createQuery(queryString);
								query.setString("param_1", (String) itemCode);
								query.setString("param_2", (String) prdId);

								double resQTYGR = (double) query.uniqueResult();// appConfig.findAllByMultiClzMultiQryPrms(queryStatement,
																				// params);
								String resStatus = "0";
								if (resQTYGR >= qty) {

									resStatus = "1";
									queryString = "update PurchaseRequestItem set prqPoStatus = :param_1  where PRQId = :param_2 and ItemCode = :param_3";
									query = session.createQuery(queryString);
									query.setString("param_1", resStatus);
									query.setString("param_2", (String) prdId);
									query.setString("param_3", (String) itemCode);
									query.executeUpdate();
								} else {
									queryString = "from FixedAssetRegistry where faritemCode like :param_1 and PRQId like :param_2";
									query = session.createQuery(queryString);
									query.setString("param_1", (String) itemCode);
									query.setString("param_2", (String) prdId);
									List<FixedAssetRegistry> itemInFAR = (List<FixedAssetRegistry>) query
											.setResultTransformer(Transformers.aliasToBean(FixedAssetRegistry.class))
											.list();

									if ((itemInFAR.size() + resQTYGR) >= qty) {
										resStatus = "1";
										queryString = "update PurchaseRequestItem set prqPoStatus = :param_1  where PRQId = :param_2 and ItemCode = :param_3";
										query = session.createQuery(queryString);
										query.setString("param_1", resStatus);
										query.setString("param_2", (String) prdId);
										query.setString("param_3", (String) itemCode);
										query.executeUpdate();
									}

								}
							}

							queryString = " select distinct ItemCode from PurchaseRequestItem where PRQId like :param1 and prqPoStatus = '1'";
							query = session.createQuery(queryString);
							query.setString("param1", (String) prdId);
							List<Object[]> purchOrdItemList = (List<Object[]>) query.list();
							if (purchOrdItem.size() == purchOrdItemList.size()) {
								queryString = "update PurchaseRequest set prStatus = :param_1  where ID = :param_2 ";
								query = session.createQuery(queryString);
								query.setString("param_1", "completed");
								query.setString("param_2", (String) prdId);
								query.executeUpdate();

							}
						}
					}
				}
					
					
					
					
					
					

				}
			} catch (Exception e) {
				logger.info(
						"Error in the insertion of Asset Life Cycle on the level of "+formView+" in the following message :"
								+ e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void InsertGrIntoALC(Session session, String grID, String griID, String itmCode, Timestamp CreationDate,
			Timestamp lastModifiedDate) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);

		String alcID;
		String vchrtype = "GR";
		List<Object[]> QueryRows = new ArrayList<Object[]>();
		Query selection = null;
		alcID = "ALC_" + year + "_" +Integer.parseInt(session.createSQLQuery("SELECT Asset_Life_Cycle FROM SEQ_TABLE").uniqueResult().toString());
		query = session.createSQLQuery("UPDATE SEQ_TABLE SET Asset_Life_Cycle = Asset_Life_Cycle + 1 ");
		query.executeUpdate();
		session.createSQLQuery("commit").executeUpdate();
		
		System.out.println("preparing to insert into ALC table*********" + alcID);

		selection = session.createSQLQuery(
				"SELECT GR_ID,u.WAREHOUSE,ITEM_CODE,t.ITEM_NAME,s.QTY,t.SIZE_UOM,t.ITEM_MODEL,WARE_NAME,"// 7
						+ " t.VALUATION_RATE,s.NET_RATE,s.QTY*s.NET_RATE,s.NET_RATE/(t.USEFULL_LIFE_MONTHS*30)," // 11
						+ " ((s.NET_RATE/(t.USEFULL_LIFE_MONTHS*30))- (s.QTY*s.NET_RATE)),"// 12
						+ " (SELECT MAX(b.BALANCE_QTY) FROM ASSET_LIFE_CYCLE b WHERE b.VOUCHER_TYPE = 'GR' AND b.ITEM_CODE =:param2) + s.QTY,s.NET_RATE*s.QTY," // 14
						+ " ((s.QTY*s.NET_RATE)- ((s.NET_RATE/(t.USEFULL_LIFE_MONTHS*30))- (s.QTY*s.NET_RATE))),"// 15
						+ " COALESCE(s.QTY,s.QTY),u.SUPPLIER,u.SUPPLIER_NAME FROM  ITEM t" // 18
						+ " INNER JOIN GOODS_RECEIVED_ITEM s USING (ITEM_CODE)"
						+ " INNER JOIN GOODS_RECEIVED u USING (GR_ID)"
						+ " LEFT JOIN WAREHOUSE v ON v.WARE_ID=u.WAREHOUSE" + " WHERE GR_RCV_ITEM_ID =:param1");
		selection.setString("param1", griID);
		selection.setString("param2", itmCode);

		QueryRows = selection.list();
		AssetLifeCycle aclRow;
		for (Object[] rowObj : QueryRows) {
			final BigDecimal ValRate = (BigDecimal) (rowObj[8]);
			final BigDecimal NetPrice = (BigDecimal) (rowObj[9]);
			final BigDecimal InComRate = (BigDecimal) (rowObj[9]);
			final BigDecimal BalVal = (BigDecimal) (rowObj[10]);
			final BigDecimal NetBalVal = (BigDecimal) (rowObj[12]);
			final BigDecimal BalQty = (BigDecimal) (rowObj[13]);
			final BigDecimal InNetVal = (BigDecimal) (rowObj[14]);
			final BigDecimal InVal = (BigDecimal) (rowObj[14]);
			final BigDecimal TotAccumDep = (BigDecimal) (rowObj[15]);
			final BigDecimal ActQty = (BigDecimal) (rowObj[16]);

			final BigDecimal dailyDepre = (BigDecimal) (rowObj[11]);

			aclRow = new AssetLifeCycle();

			aclRow.setAlcID(alcID);
			aclRow.setCreationDate(CreationDate);
			aclRow.setLastModifiedDate(lastModifiedDate);
			aclRow.setVoucherType(vchrtype);
			aclRow.setVoucherNB(rowObj[0].toString());
			if (rowObj[1] == null)
				aclRow.setWarehouse("");
			else
				aclRow.setWarehouse(rowObj[1].toString());
			aclRow.setItemCode(rowObj[2].toString());
			aclRow.setItemName(rowObj[3].toString());
			aclRow.setUOM("NOS");
			aclRow.setDocstatus("Approved");

			if (rowObj[7] == null)
				aclRow.setWareName("");
			else
				aclRow.setWareName(rowObj[7].toString());
			aclRow.setValuationRate((ValRate));
			aclRow.setNetPrice(NetPrice);
			aclRow.setInComingRate(InComRate);
			aclRow.setBalanceValue(BalVal);
			aclRow.setDailyDepreciation(dailyDepre);
			aclRow.setNetBalanceValue(NetBalVal);
			if (BalQty == null)
				aclRow.setBalanceQty(ActQty);
			else
				aclRow.setBalanceQty(BalQty);
			aclRow.setInNetValue(InNetVal);
			aclRow.setInValue(InVal);
			aclRow.settAccumulatedDepreciation(TotAccumDep);
			aclRow.setActualQty(ActQty);
			if (rowObj[17] == null)
				aclRow.setSupplier("");
			else
				aclRow.setSupplier(rowObj[17].toString());
			if (rowObj[18] == null)
				aclRow.setSupplierName("");
			else
				aclRow.setSupplierName(rowObj[18].toString());

			session.saveOrUpdate(aclRow);

		}

	}

	}