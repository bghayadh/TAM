package com.aliat.alm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.services.LoginServices;

@Controller
public class AssetFinanceDashboard {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static String dbDateFrom,dbDateTo;
	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@Autowired
	ALMSessions almsessions;

	@RequestMapping(value = "/AssetFinance", method = RequestMethod.GET)
	public String Dashboard(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		return "AssetFinanceDashboard";

	}

	/*@RequestMapping(value = "/testtable", method = RequestMethod.GET)
	public String testtable(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}
		ObjectMapper mapper = new ObjectMapper();
		// List<PurchaseRequestItem> listPurchaseRequestItem =
		// (List<PurchaseRequestItem>) appConfig.findAll(PurchaseRequestItem.class);

		String queryStmt = "select t.ItemCode ,t.Qty, t.Rate,t.DiscAmnt , t.NetRate, t.Total, t.TotalAt, t.prqiWarehouse,t.prqItemId  from PurchaseRequestItem t where t.PRQId like :param1";

		List<PurchaseRequestItem> listPurchaseRequestItem = (List<PurchaseRequestItem>) appConfig
				.findAllByQueryParam(PurchaseRequestItem.class, queryStmt, "param1", "r1");

		model.addAttribute("ListPRqItem", mapper.writeValueAsString(listPurchaseRequestItem));

		return "testtable";
	}*/

	@RequestMapping(value = "/GetChartsDetailsAssetFinance", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetChartsDetailsAssetFinance(HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("GetChartsDetails");
		Map<String, Object> map = new HashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
		}
		// ******************* date *******************
	
	    Session session =null;
	    Transaction tx=null ;
		session = almsessions.getSession();
			try {
				
				 tx = session.beginTransaction();
			
		
				 
	
						
						
				 Query q = session.createSQLQuery(
							"SELECT SUM(INITIALCOST),SUM(ACCUMULDEPRECAMNT),SUM(NETCOST),SUPPLIER_ID FROM FIXED_ASSET_REGISTRY GROUP BY SUPPLIER_ID ORDER BY SUM(INITIALCOST) DESC FETCH NEXT 10 ROWS ONLY");
					Query q2 = session.createQuery(" select supplierName from Supplier where supplierID =:param");

					List<Object[]> topSupplier = (List<Object[]>) q.list();
					final List<Object[]> topTenSupplier = new ArrayList<Object[]>();
					if (topSupplier != null) {
						String name;
						for (Object[] o : topSupplier) {

							if (o[0] == null) {
								o[0] = 0;
							}
							if (o[1] == null) {
								o[1] = 0;
							}
							if (o[2] == null) {
								o[2] = 0;
							}
							if (o[3] != null) {
								if (q2.setParameter("param", o[3]).uniqueResult() != null) {
									name = q2.setParameter("param", o[3]).uniqueResult().toString();
								} else {
									name = "";
								}
								o[3] = o[3] + " \n\r" + name;
							} else {
								o[3] = ".";
							}
							topTenSupplier.add(o);
						}
					}



					// ******************** line chart last 12 month *************************************
					List<String> monthName = new ArrayList<String>();
					List<Float> initCost = new ArrayList<Float>();
					List<Float> Acc = new ArrayList<Float>();
					List<Float> netCost = new ArrayList<Float>();
					Query last12MonthLineChart = session.createSQLQuery(
							"SELECT INITIALCOST,ACCUMULDEPRECAMNT,NETCOST,CREATED_DATE FROM ASSET_DEPRECIATION_12MONTH");
					List<Object[]> lastmonthsList = (List<Object[]>) last12MonthLineChart.list();
					for (Object[] obj : lastmonthsList) {
						if (obj != null) {
							if (obj[0] != null) {
								initCost.add(Float.parseFloat(obj[0].toString()));
							} else {
								initCost.add(0f);
							}
							if (obj[1] != null) {
								Acc.add(Float.parseFloat(obj[1].toString()));
							} else {
								Acc.add(0f);
							}
							if (obj[2] != null) {
								netCost.add(Float.parseFloat(obj[2].toString()));
							} else {
								netCost.add(0f);
							}
							if (obj[3] != null) {
								monthName.add(obj[3].toString());
							} else {
								monthName.add("");
							}
						}
					}

					// ************************************************************************************

					// ************************************************************************************
					// **************************************** bar chart compare top 10 item
					// ********************************************

					List<String[]> totalNameList = new ArrayList<String[]>();
					List<Float> itemInitCost = new ArrayList<Float>();
					List<Float> itemAccCost = new ArrayList<Float>();
					List<Float> itemNetCost = new ArrayList<Float>();

					Query qCompare = session.createSQLQuery(
							"SELECT SUM(INITIALCOST),SUM(ACCUMULDEPRECAMNT),SUM(NETCOST),ITEM_CODE,ITEM_NAME,ITEM_MODEL FROM FIXED_ASSET_REGISTRY GROUP BY ITEM_CODE,ITEM_NAME,ITEM_MODEL ORDER BY SUM(INITIALCOST) DESC FETCH NEXT 10 ROWS ONLY");
					List<Object[]> topItems = (List<Object[]>) qCompare.list();
//					Query QitemName = session
//							.createQuery("select faritemName from FixedAssetRegistry where faritemCode =:param");

//					if (topItems != null) {
//						Object[] itmName;
//						for (int i = 0; i < topItems.size(); i++) {
//							itmName = topItems.get(i);
//							if (itmName[3] != null) {
//								QitemName.setParameter("param", itmName[3].toString());
//								System.out.println("i name is " + QitemName.list().get(0).toString());
//								if (QitemName.list().get(0) != null) {
//									nameLable[0] = itmName[3].toString();
//									nameLable[1] = QitemName.list().get(0).toString();
//									totalNameList.add(nameLable);
//								} else {
//									nameLable[0] = itmName[3].toString();
//									nameLable[1] = "";
//									totalNameList.add(nameLable);
//								}
//							}
//						}
//					}
					String[] nameLable = new String[3];
					if (topItems != null) {
						for (Object[] obj : topItems) {
							if (obj[0] != null) {
								itemInitCost.add(Float.parseFloat(obj[0].toString()));
							} else {
								itemInitCost.add(Float.parseFloat("0"));
							}
							if (obj[1] != null) {
								itemAccCost.add(Float.parseFloat(obj[1].toString()));
							} else {
								itemAccCost.add(Float.parseFloat("0"));
							}
							if (obj[2] != null) {
								itemNetCost.add(Float.parseFloat(obj[2].toString()));
							} else {
								itemNetCost.add(Float.parseFloat("0"));
							}
							if (obj[3] != null) {
								nameLable[0] = obj[3].toString();
							} else {
								nameLable[0] = "";
							}
							if (obj[4] != null) {
								nameLable[1] = obj[4].toString();
							} else {
								nameLable[1] = "";
							}
							if (obj[5] != null) {
								nameLable[2] = obj[5].toString();
							} else {
								nameLable[2] = "";
							}
							totalNameList.add(nameLable);
						}

					}
					
					

					// ************************************************************************************

					// ***************************** doughnut2d pie chart items domain
					// *****************************
					
					
					
					
				/*	List<Integer> domainCount = new ArrayList<Integer>();
					Query QMAD = session.createSQLQuery(
							"select count(*) from WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_4G = '0' and TECH_3G = '0'");
					if (QMAD.uniqueResult() != null) {
						System.out.println("count test");
						domainCount.add(Integer.parseInt(QMAD.uniqueResult().toString()));
					} else {
						domainCount.add(0);
					}

					Query QTD = session.createSQLQuery(
							"select count(*) from WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_3G = '1' and TECH_2G = '1'and TECH_4G = '0'");
					if (QTD.uniqueResult() != null) {
						domainCount.add(Integer.parseInt(QTD.uniqueResult().toString()));
					} else {
						domainCount.add(0);
					}

					Query QCD = session.createSQLQuery(
							"select count(*) from WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_4G = '1' and TECH_3G = '1' and TECH_2G = '1'");
					if (QCD.uniqueResult() != null) {
						domainCount.add(Integer.parseInt(QCD.uniqueResult().toString()));
					} else {
						domainCount.add(0);
					}

					Query QPD = session.createSQLQuery(
							"select count(*) from WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_4G = '0' and TECH_3G = '0' and TECH_2G = '0'");
					if (QPD.uniqueResult() != null) {
						domainCount.add(Integer.parseInt(QPD.uniqueResult().toString()));
					} else {
						domainCount.add(0);
					}*/
					// ************************************************************************************
					// get sites gain
					float g2g, g23g, g234g;
					Query gain2g = session.createSQLQuery(
							"SELECT SUM( PROFIT_AND_LOSS ) FROM WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='0' and TECH_4G = '0' and PROFIT_AND_LOSS > 0");
					if (gain2g.uniqueResult() != null) {
						System.out.println("enter if");
						g2g = Float.parseFloat(gain2g.uniqueResult().toString());
					} else {
						System.out.println("out if");
						g2g = 0;
					}

					Query gain23g = session.createSQLQuery(
							"SELECT SUM( PROFIT_AND_LOSS ) FROM WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '0' and PROFIT_AND_LOSS > 0");
					if (gain23g.uniqueResult() != null) {
						g23g = Float.parseFloat(gain23g.uniqueResult().toString());

					} else {
						g23g = 0;
					}

					Query gain234g = session.createSQLQuery(
							"SELECT SUM( PROFIT_AND_LOSS ) FROM WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '1' and PROFIT_AND_LOSS > 0");
					if (gain234g.uniqueResult() != null) {
						g234g = Float.parseFloat(gain234g.uniqueResult().toString());
					} else {
						g234g = 0;
					}

					// for site loss

					float l2g, l23g, l234g;
					Query loss2g = session.createSQLQuery(
							"SELECT SUM( PROFIT_AND_LOSS ) FROM WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='0' and TECH_4G = '0' and PROFIT_AND_LOSS < 0");
					if (loss2g.uniqueResult() != null) {
						System.out.println("loss" + loss2g.uniqueResult().toString());
						l2g = Float.parseFloat(loss2g.uniqueResult().toString());
					} else {
						l2g = 0;
					}

					Query loss23g = session.createSQLQuery(
							"SELECT SUM( PROFIT_AND_LOSS ) FROM WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '0' and PROFIT_AND_LOSS < 0");
					if (loss23g.uniqueResult() != null) {
						l23g = Float.parseFloat(loss23g.uniqueResult().toString());
					} else {
						l23g = 0;
					}

					Query loss234g = session.createSQLQuery(
							"SELECT SUM( PROFIT_AND_LOSS ) FROM WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '1' and PROFIT_AND_LOSS < 0");
					if (loss234g.uniqueResult() != null) {
						l234g = Float.parseFloat(loss234g.uniqueResult().toString());
					} else {
						l234g = 0;
					}
					// ************************************************************************************
					// line chart for top 10 site owner loss revenue
					List<String> ownerName = new ArrayList<String>();
					List<Float> ownerLoss = new ArrayList<Float>();
					Query allLossOwner = session.createSQLQuery(
							"SELECT SUM(PROFIT_AND_LOSS),SITE_OWNER FROM WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') GROUP BY SITE_OWNER ORDER BY SUM(PROFIT_AND_LOSS) ASC FETCH NEXT 10 ROWS ONLY");
					if (allLossOwner.list() != null) {
						List<Object[]> listLoss = allLossOwner.list();
						for (Object[] loss : listLoss) {
							if (loss != null) {
								if (loss[0] != null) {
									ownerLoss.add(Float.parseFloat(loss[0].toString()));
								} else {
									ownerLoss.add(0f);
								}
								if (loss[1] != null) {
									ownerName.add(loss[1].toString());
								} else {
									ownerName.add("NO SITE OWNER FOR THIS LOSS");
								}
							}
						}
					} else {
						ownerLoss.add(0f);
						ownerName.add("NO SITE OWNER AND LOSS");
					}
					// ************************************************************************************
					// nbr site of specific tech

					float fiberCount = Float.parseFloat(session
							.createSQLQuery(
									"select count(TRANSMISSION) from WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TRANSMISSION = 'Fiber'")
							.uniqueResult().toString());
					float mwCount = Float.parseFloat(session
							.createSQLQuery("select count(TRANSMISSION) from WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TRANSMISSION = 'MW'")
							.uniqueResult().toString());

					// ************************************************************************************
					// get top 10 site get revenue
					List<Float> top10Profit = new ArrayList<Float>();
					List<String[]> top10ProfitName = new ArrayList<String[]>();
					Query topSiteRevenue = session.createSQLQuery(
							"SELECT SUM(PROFIT_AND_LOSS),SITE_ID,AREA_NAME,WARE_ID,WARE_NAME,TECH_2G,TECH_3G,TECH_4G,TRANSMISSION,SITE_OWNER FROM WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and PROFIT_AND_LOSS > 0 GROUP  BY SITE_ID,AREA_NAME,WARE_ID,WARE_NAME,TECH_2G,TECH_3G,TECH_4G,TRANSMISSION,SITE_OWNER ORDER BY SUM(PROFIT_AND_LOSS) ASC FETCH NEXT 10 ROWS ONLY");
					List<Object[]> siteList = topSiteRevenue.list();

					if (siteList != null) {
						String[] comName;
						int n;
						for (Object[] obj : siteList) {
							n = 0;
							comName = new String[4];
							if (obj != null) {
								if (obj[0] != null) {
									top10Profit.add(Float.parseFloat(obj[0].toString()));
								} else {
									top10Profit.add(0f);
								}

								if (obj[1] != null || obj[4] != null) {
									String s = "";
									if (obj[1] != null) {
									s = obj[1].toString();
									}
									if (obj[4] != null) {
										s += ", " + obj[4].toString() + ", ";
									}
									comName[n] = s;
									n++;
								}

								if (obj[3] != null|| obj[5] != null || obj[6] != null || obj[7] != null) {
									String s = "";
									if (obj[3] != null) {
										s += obj[3].toString();
									}
									if (obj[5] != null && obj[5].toString().equals("1")) {
										s += "2G, ";
									}
									if (obj[6] != null && obj[6].toString().equals("1")) {
										s += "3G, ";
									}
									if (obj[7] != null && obj[7].toString().equals("1")) {
										s += "4G, ";
									}
									comName[n] = s;
									n++;
								}
								if (obj[8] != null || obj[9] != null || obj[2] != null) {
									String s = "";
									if (obj[8] != null) {
										s += obj[8].toString();
									}
									if (obj[9] != null) {
										s += ", " + obj[9].toString() + ", ";
									}
									if (obj[2] != null) {
										s += ", " + obj[2].toString();
									}
									comName[n] = s;
								}
								if (comName[3] == null) {
									comName[3] = "";
								}
								top10ProfitName.add(comName);

							}
						}
					} else {
						String[] comName = { "NO SITES" };
						top10Profit.add(0f);
						top10ProfitName.add(comName);
					}

					// get top 10 site loss revenue
					List<Float> top10Profitloss = new ArrayList<Float>();
					List<String[]> top10ProfitlossName = new ArrayList<String[]>();
					Query topSiteRevenueloss = session.createSQLQuery(
							"SELECT SUM(PROFIT_AND_LOSS),SITE_ID,AREA_NAME,WARE_ID,WARE_NAME,TECH_2G,TECH_3G,TECH_4G,TRANSMISSION,SITE_OWNER FROM WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and PROFIT_AND_LOSS < 0 GROUP  BY SITE_ID,AREA_NAME,WARE_ID,WARE_NAME,TECH_2G,TECH_3G,TECH_4G,TRANSMISSION,SITE_OWNER ORDER BY SUM(PROFIT_AND_LOSS) ASC FETCH NEXT 10 ROWS ONLY");
					List<Object[]> siteListloss = topSiteRevenueloss.list();

					if (siteListloss != null) {
						String[] comName;
						int n;
						for (Object[] obj : siteListloss) {
							n = 0;
							comName = new String[4];
							if (obj != null) {
								if (obj[0] != null) {
									top10Profitloss.add(Float.parseFloat(obj[0].toString()));
								} else {
									top10Profit.add(0f);
								}

								if (obj[1] != null || obj[4] != null) {
									String s = "";
									if (obj[1] != null) {
									s = obj[1].toString();
									}
									if (obj[4] != null) {
										s += ", " + obj[4].toString() + ", ";
									}
									comName[n] = s;
									n++;
								}

								if (obj[3] != null|| obj[5] != null || obj[6] != null || obj[7] != null) {
									String s = "";
									if (obj[3] != null) {
										s += obj[3].toString();
									}
									if (obj[5] != null && obj[5].toString().equals("1")) {
										s += "2G, ";
									}
									if (obj[6] != null && obj[6].toString().equals("1")) {
										s += "3G, ";
									}
									if (obj[7] != null && obj[7].toString().equals("1")) {
										s += "4G, ";
									}
									comName[n] = s;
									n++;
								}
								if (obj[8] != null || obj[9] != null || obj[2] != null) {
									String s = "";
									if (obj[8] != null) {
										s += obj[8].toString();
									}
									if (obj[9] != null) {
										s += ", " + obj[9].toString() + ", ";
									}
									if (obj[2] != null) {
										s += ", " + obj[2].toString();
									}
									comName[n] = s;							
								}
								if (comName[3] == null) {
									comName[3] = "";
								}
								top10ProfitlossName.add(comName);

							}
						}
					} else {
						String[] comName = { "NO SITES" };
						top10Profit.add(0f);
						top10ProfitName.add(comName);
					}
					
					map.put("top10Profit", top10Profit);
					map.put("top10ProfitName", top10ProfitName);
					map.put("top10Profitloss", top10Profitloss);
					map.put("top10ProfitlossName", top10ProfitlossName);
					// line chart for all revenue
					map.put("fiberCount", fiberCount);
					map.put("mwCount", mwCount);
					map.put("totalRevenue", getAllRevenue());
					map.put("siteOwnerLossName", ownerName);
					map.put("siteOwnerLossValue", ownerLoss);
					
					
					map.put("totalRevenueTransmission", getAllRevenueWithTransmission());
					map.put("expenses", getTechAndTransmissionExpenses());
					map.put("getTransmissionExpenses", getTransmissionExpenses());
					map.put("getTechExpenses", getTechExpenses());
					
					
					
					map.put("getTop10AreaProfite", getTop10AreaProfite());
					map.put("getTop10AreaLoss", getTop10AreaLoss());
					// ************************************************************************************
					map.put("gain2g", g2g);
					map.put("gain23g", g23g);
					map.put("gain234g", g234g);

					map.put("loss2g", l2g);
					map.put("loss23g", l23g);
					map.put("loss234g", l234g);

					

					map.put("totalNameList", totalNameList);
					map.put("itemInitCost", itemInitCost);
					map.put("itemAccCost", itemAccCost);
					map.put("itemNetCost", itemNetCost);

//					map.put("count2G", count2G);
//					map.put("count3G", count3G);
//					map.put("count4G", count4G);
//					map.put("count5G", count5G);

					map.put("lineChartMonth", monthName);
					map.put("lineChartInitCost", initCost);
					map.put("lineChartAcc", Acc);
					map.put("lineChartNetCost", netCost);

					map.put("topTenSupplierList", topTenSupplier);
					map.put("fiberMwTrProfitAndLoss", siteTrProfitAndLoss());
					map.put("siteProfitAndLossTechAndTx", siteProfitAndLossTechAndTx());
					map.put("areaProfitAndLoss", areaProfitAndLoss());
					map.put("sitetechProfitAndLoss", sitetechProfitAndLoss());
					map.put("fiberVsMwMoreProfit", fiberVsMwMoreProfit());
					map.put("techMoreProfit", techMoreProfit());
					
							
		}catch (Exception e) {
			logger.info("Error in creating session with Database", e);
		} 
			//**************************** end easy pie chart ****************************
	

	finally {
		if (session != null && session.isOpen()) {
			tx.commit();
			session.close();
		}
	}	// ************************************************************************************
			
			return map;

	}
	
	
	

	//method for the second chart
/*	private List<List<Float>> getRevenue() {
		float mfloat = 0f;
		Configuration cfg ;
		 Session session =null;
		 Transaction tx=null ;
		 SessionFactory sf ;
		 StandardServiceRegistryBuilder builder ;
		 List<List<Float>> totalRevenue = new ArrayList<List<Float>>();
		try {
		 cfg = new Configuration().configure("/almrpt.cfg.xml");
		 builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
	     sf = cfg.buildSessionFactory(builder.build());
          session = sf.openSession();
         tx = session.beginTransaction();
		List<Float> allVoice = new ArrayList<Float>();
		List<Float> allSms = new ArrayList<Float>();
		List<Float> allData = new ArrayList<Float>();
		List<Float> allVoiceIc = new ArrayList<Float>();
		
		Query sumAll2g = session.createSQLQuery(
				"select SUM( b.VOICE_REVENUE ),SUM( b.SMS_REVENUE ),SUM( b.DATA_REVENUE ),SUM( b.VAS_REVENUE ) FROM  alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE a.combination_technology='2G' and trunc(b.REVENUE_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(b.REVENUE_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY')") ;
		if (sumAll2g.uniqueResult() != null) {
			
			Object[] obj = (Object[]) sumAll2g.uniqueResult();
			
			if (obj[0] != null) {
				allVoice.add(Float.parseFloat(obj[0].toString()));
			} else {
				allVoice.add(mfloat);
			}

			if (obj[1] != null) {
				allSms.add(Float.parseFloat(obj[1].toString()));
			} else {
				allSms.add(mfloat);
			}

			if (obj[2] != null) {
				allData.add(Float.parseFloat(obj[2].toString()));
			} else {
				allData.add(mfloat);
			}

			if (obj[3] != null) {
				allVoiceIc.add(Float.parseFloat(obj[3].toString()));
			} else {
				allVoiceIc.add(mfloat);
			}

		} else {
			allVoice.add(mfloat);
			allSms.add(mfloat);
			allData.add(mfloat);
			allVoiceIc.add(mfloat);
			
		}

		Query sumVoice23g = session.createSQLQuery(
				"Select SUM( b.VOICE_REVENUE ),SUM( b.SMS_REVENUE ),SUM( b.DATA_REVENUE ),SUM( b.VAS_REVENUE ) FROM  alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE a.combination_technology='2G_3G' and trunc(b.REVENUE_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(b.REVENUE_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY')");
		if (sumVoice23g.uniqueResult() != null) {
			Object[] obj = (Object[]) sumVoice23g.uniqueResult();
			
			if (obj[0] != null) {
				allVoice.add(Float.parseFloat(obj[0].toString()));
			} else {
				allVoice.add(mfloat);
			}

			if (obj[1] != null) {
				allSms.add(Float.parseFloat(obj[1].toString()));
			} else {
				allSms.add(mfloat);
			}

			if (obj[2] != null) {
				allData.add(Float.parseFloat(obj[2].toString()));
			} else {
				allData.add(mfloat);
			}

			if (obj[3] != null) {
				allVoiceIc.add(Float.parseFloat(obj[3].toString()));
			} else {
				allVoiceIc.add(mfloat);
			}

			

		} else {
			allVoice.add(mfloat);
			allSms.add(mfloat);
			allData.add(mfloat);
			allVoiceIc.add(mfloat);
			
		}

		Query sumVoice234g = session.createSQLQuery(
				"Select SUM( b.VOICE_REVENUE ),SUM( b.SMS_REVENUE ),SUM( b.DATA_REVENUE ),SUM( b.VAS_REVENUE ) FROM  alm_warehouse a INNER JOIN PREPAID_PAYG_REVENUE b ON a.SITE_ID = b.SITE_ID WHERE a.combination_technology='2G_3G_4G' and trunc(b.REVENUE_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(b.REVENUE_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') ");
		if (sumVoice234g.uniqueResult() != null) {
			Object[] obj = (Object[]) sumVoice234g.uniqueResult();
			if (obj[0] != null) {
				allVoice.add(Float.parseFloat(obj[0].toString()));
			} else {
				allVoice.add(mfloat);
			}

			if (obj[1] != null) {
				allSms.add(Float.parseFloat(obj[1].toString()));
			} else {
				allSms.add(mfloat);
			}

			if (obj[2] != null) {
				allData.add(Float.parseFloat(obj[2].toString()));
			} else {
				allData.add(mfloat);
			}

			if (obj[3] != null) {
				allVoiceIc.add(Float.parseFloat(obj[3].toString()));
			} else {
				allVoiceIc.add(mfloat);
			}

			
		} else {
			allVoice.add(mfloat);
			allSms.add(mfloat);
			allData.add(mfloat);
			allVoiceIc.add(mfloat);
			
		}
		

	    totalRevenue = new ArrayList<List<Float>>();
		totalRevenue.add(allVoice);
		totalRevenue.add(allSms);
		totalRevenue.add(allData);
		totalRevenue.add(allVoiceIc);
		
		}
		
		catch (Exception e) {
			 
			logger.info("Error in retreiving Services from database");
				
			}
		
		finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.close();
			    
			}
		
		

	}
		return totalRevenue;	
	}
*/
	private List<List<Float>> getAllRevenue() {
		float mfloat = 0f;
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		List<Float> allVoice = new ArrayList<Float>();
		List<Float> allSms = new ArrayList<Float>();
		List<Float> allData = new ArrayList<Float>();
		List<Float> allVoiceIc = new ArrayList<Float>();
		List<Float> allSmsIc = new ArrayList<Float>();
		Query sumAll2g = session.createSQLQuery(
				"SELECT SUM( VOICE_REVENU ),SUM( SMS_REVENU ),SUM( DATA ),SUM( TOTAL_VOICE_TRAFFIC_IC ),SUM( TOTAL_SMS_TRAFFIC_IC ) FROM WAREHOUSE_PROFIT_LOSS where  trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='0' and TECH_4G = '0'");
		if (sumAll2g.uniqueResult() != null) {

			Object[] obj = (Object[]) sumAll2g.uniqueResult();
			if (obj[0] != null) {
				allVoice.add(Float.parseFloat(obj[0].toString()));
			} else {
				allVoice.add(mfloat);
			}

			if (obj[1] != null) {
				allSms.add(Float.parseFloat(obj[1].toString()));
			} else {
				allSms.add(mfloat);
			}

			if (obj[2] != null) {
				allData.add(Float.parseFloat(obj[2].toString()));
			} else {
				allData.add(mfloat);
			}

			if (obj[3] != null) {
				allVoiceIc.add(Float.parseFloat(obj[3].toString()));
			} else {
				allVoiceIc.add(mfloat);
			}

			if (obj[4] != null) {
				allSmsIc.add(Float.parseFloat(obj[4].toString()));
			} else {
				allSmsIc.add(mfloat);
			}

		} else {
			allVoice.add(mfloat);
			allSms.add(mfloat);
			allData.add(mfloat);
			allVoiceIc.add(mfloat);
			allSmsIc.add(mfloat);
		}

		Query sumVoice23g = session.createSQLQuery(
				"SELECT SUM( VOICE_REVENU ),SUM( SMS_REVENU ),SUM( DATA ),SUM( TOTAL_VOICE_TRAFFIC_IC ),SUM( TOTAL_SMS_TRAFFIC_IC ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '0'");
		if (sumVoice23g.uniqueResult() != null) {
			Object[] obj = (Object[]) sumVoice23g.uniqueResult();
			if (obj[0] != null) {
				allVoice.add(Float.parseFloat(obj[0].toString()));
			} else {
				allVoice.add(mfloat);
			}

			if (obj[1] != null) {
				allSms.add(Float.parseFloat(obj[1].toString()));
			} else {
				allSms.add(mfloat);
			}

			if (obj[2] != null) {
				allData.add(Float.parseFloat(obj[2].toString()));
			} else {
				allData.add(mfloat);
			}

			if (obj[3] != null) {
				allVoiceIc.add(Float.parseFloat(obj[3].toString()));
			} else {
				allVoiceIc.add(mfloat);
			}

			if (obj[4] != null) {
				allSmsIc.add(Float.parseFloat(obj[4].toString()));
			} else {
				allSmsIc.add(mfloat);
			}

		} else {
			allVoice.add(mfloat);
			allSms.add(mfloat);
			allData.add(mfloat);
			allVoiceIc.add(mfloat);
			allSmsIc.add(mfloat);
		}

		Query sumVoice234g = session.createSQLQuery(
				"SELECT SUM( VOICE_REVENU ),SUM( SMS_REVENU ),SUM( DATA ),SUM( TOTAL_VOICE_TRAFFIC_IC ),SUM( TOTAL_SMS_TRAFFIC_IC ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '1'");
		if (sumVoice234g.uniqueResult() != null) {
			Object[] obj = (Object[]) sumVoice234g.uniqueResult();
			if (obj[0] != null) {
				allVoice.add(Float.parseFloat(obj[0].toString()));
			} else {
				allVoice.add(mfloat);
			}

			if (obj[1] != null) {
				allSms.add(Float.parseFloat(obj[1].toString()));
			} else {
				allSms.add(mfloat);
			}

			if (obj[2] != null) {
				allData.add(Float.parseFloat(obj[2].toString()));
			} else {
				allData.add(mfloat);
			}

			if (obj[3] != null) {
				allVoiceIc.add(Float.parseFloat(obj[3].toString()));
			} else {
				allVoiceIc.add(mfloat);
			}

			if (obj[4] != null) {
				allSmsIc.add(Float.parseFloat(obj[4].toString()));
			} else {
				allSmsIc.add(mfloat);
			}

		} else {
			allVoice.add(mfloat);
			allSms.add(mfloat);
			allData.add(mfloat);
			allVoiceIc.add(mfloat);
			allSmsIc.add(mfloat);
		}
		tx.commit();
		session.close();
		List<List<Float>> totalRevenue = new ArrayList<List<Float>>();
		totalRevenue.add(allVoice);
		totalRevenue.add(allSms);
		totalRevenue.add(allData);
		totalRevenue.add(allVoiceIc);
		totalRevenue.add(allSmsIc);

		return totalRevenue;

	}

	private List<List<Float>> getAllRevenueWithTransmission() {
		float mfloat = 0f;
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		List<Float> allVoice = new ArrayList<Float>();
		List<Float> allSms = new ArrayList<Float>();
		List<Float> allData = new ArrayList<Float>();
		List<Float> allVoiceIc = new ArrayList<Float>();
		List<Float> allSmsIc = new ArrayList<Float>();

		Query sumAll2gfiber = session.createSQLQuery(
				"SELECT SUM( VOICE_REVENU ),SUM( SMS_REVENU ),SUM( DATA ),SUM( TOTAL_VOICE_TRAFFIC_IC ),SUM( TOTAL_SMS_TRAFFIC_IC ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='0' and TECH_4G = '0' and TRANSMISSION = 'Fiber'");
		if (sumAll2gfiber.uniqueResult() != null) {

			Object[] obj = (Object[]) sumAll2gfiber.uniqueResult();
			if (obj[0] != null) {
				allVoice.add(Float.parseFloat(obj[0].toString()));
			} else {
				allVoice.add(mfloat);
			}

			if (obj[1] != null) {
				allSms.add(Float.parseFloat(obj[1].toString()));
			} else {
				allSms.add(mfloat);
			}

			if (obj[2] != null) {
				allData.add(Float.parseFloat(obj[2].toString()));
			} else {
				allData.add(mfloat);
			}

			if (obj[3] != null) {
				allVoiceIc.add(Float.parseFloat(obj[3].toString()));
			} else {
				allVoiceIc.add(mfloat);
			}

			if (obj[4] != null) {
				allSmsIc.add(Float.parseFloat(obj[4].toString()));
			} else {
				allSmsIc.add(mfloat);
			}

		} else {
			allVoice.add(mfloat);
			allSms.add(mfloat);
			allData.add(mfloat);
			allVoiceIc.add(mfloat);
			allSmsIc.add(mfloat);
		}

		Query sumAll2gmw = session.createSQLQuery(
				"SELECT SUM( VOICE_REVENU ),SUM( SMS_REVENU ),SUM( DATA ),SUM( TOTAL_VOICE_TRAFFIC_IC ),SUM( TOTAL_SMS_TRAFFIC_IC ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='0' and TECH_4G = '0' and TRANSMISSION = 'MW'");
		if (sumAll2gmw.uniqueResult() != null) {

			Object[] obj = (Object[]) sumAll2gmw.uniqueResult();
			if (obj[0] != null) {
				allVoice.add(Float.parseFloat(obj[0].toString()));
			} else {
				allVoice.add(mfloat);
			}

			if (obj[1] != null) {
				allSms.add(Float.parseFloat(obj[1].toString()));
			} else {
				allSms.add(mfloat);
			}

			if (obj[2] != null) {
				allData.add(Float.parseFloat(obj[2].toString()));
			} else {
				allData.add(mfloat);
			}

			if (obj[3] != null) {
				allVoiceIc.add(Float.parseFloat(obj[3].toString()));
			} else {
				allVoiceIc.add(mfloat);
			}

			if (obj[4] != null) {
				allSmsIc.add(Float.parseFloat(obj[4].toString()));
			} else {
				allSmsIc.add(mfloat);
			}

		} else {
			allVoice.add(mfloat);
			allSms.add(mfloat);
			allData.add(mfloat);
			allVoiceIc.add(mfloat);
			allSmsIc.add(mfloat);
		}

		Query sumVoice23g = session.createSQLQuery(
				"SELECT SUM( VOICE_REVENU ),SUM( SMS_REVENU ),SUM( DATA ),SUM( TOTAL_VOICE_TRAFFIC_IC ),SUM( TOTAL_SMS_TRAFFIC_IC ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '0'  and TRANSMISSION = 'Fiber'");
		if (sumVoice23g.uniqueResult() != null) {
			Object[] obj = (Object[]) sumVoice23g.uniqueResult();
			if (obj[0] != null) {
				allVoice.add(Float.parseFloat(obj[0].toString()));
			} else {
				allVoice.add(mfloat);
			}

			if (obj[1] != null) {
				allSms.add(Float.parseFloat(obj[1].toString()));
			} else {
				allSms.add(mfloat);
			}

			if (obj[2] != null) {
				allData.add(Float.parseFloat(obj[2].toString()));
			} else {
				allData.add(mfloat);
			}

			if (obj[3] != null) {
				allVoiceIc.add(Float.parseFloat(obj[3].toString()));
			} else {
				allVoiceIc.add(mfloat);
			}

			if (obj[4] != null) {
				allSmsIc.add(Float.parseFloat(obj[4].toString()));
			} else {
				allSmsIc.add(mfloat);
			}

		} else {
			allVoice.add(mfloat);
			allSms.add(mfloat);
			allData.add(mfloat);
			allVoiceIc.add(mfloat);
			allSmsIc.add(mfloat);
		}

		Query sumVoice23gmw = session.createSQLQuery(
				"SELECT SUM( VOICE_REVENU ),SUM( SMS_REVENU ),SUM( DATA ),SUM( TOTAL_VOICE_TRAFFIC_IC ),SUM( TOTAL_SMS_TRAFFIC_IC ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '0'  and TRANSMISSION = 'MW'");
		if (sumVoice23gmw.uniqueResult() != null) {
			Object[] obj = (Object[]) sumVoice23gmw.uniqueResult();
			if (obj[0] != null) {
				allVoice.add(Float.parseFloat(obj[0].toString()));
			} else {
				allVoice.add(mfloat);
			}

			if (obj[1] != null) {
				allSms.add(Float.parseFloat(obj[1].toString()));
			} else {
				allSms.add(mfloat);
			}

			if (obj[2] != null) {
				allData.add(Float.parseFloat(obj[2].toString()));
			} else {
				allData.add(mfloat);
			}

			if (obj[3] != null) {
				allVoiceIc.add(Float.parseFloat(obj[3].toString()));
			} else {
				allVoiceIc.add(mfloat);
			}

			if (obj[4] != null) {
				allSmsIc.add(Float.parseFloat(obj[4].toString()));
			} else {
				allSmsIc.add(mfloat);
			}

		} else {
			allVoice.add(mfloat);
			allSms.add(mfloat);
			allData.add(mfloat);
			allVoiceIc.add(mfloat);
			allSmsIc.add(mfloat);
		}

		Query sumVoice234g = session.createSQLQuery(
				"SELECT SUM( VOICE_REVENU ),SUM( SMS_REVENU ),SUM( DATA ),SUM( TOTAL_VOICE_TRAFFIC_IC ),SUM( TOTAL_SMS_TRAFFIC_IC ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '1' and TRANSMISSION = 'Fiber'");
		if (sumVoice234g.uniqueResult() != null) {
			Object[] obj = (Object[]) sumVoice234g.uniqueResult();
			if (obj[0] != null) {
				allVoice.add(Float.parseFloat(obj[0].toString()));
			} else {
				allVoice.add(mfloat);
			}

			if (obj[1] != null) {
				allSms.add(Float.parseFloat(obj[1].toString()));
			} else {
				allSms.add(mfloat);
			}

			if (obj[2] != null) {
				allData.add(Float.parseFloat(obj[2].toString()));
			} else {
				allData.add(mfloat);
			}

			if (obj[3] != null) {
				allVoiceIc.add(Float.parseFloat(obj[3].toString()));
			} else {
				allVoiceIc.add(mfloat);
			}

			if (obj[4] != null) {
				allSmsIc.add(Float.parseFloat(obj[4].toString()));
			} else {
				allSmsIc.add(mfloat);
			}

		} else {
			allVoice.add(mfloat);
			allSms.add(mfloat);
			allData.add(mfloat);
			allVoiceIc.add(mfloat);
			allSmsIc.add(mfloat);
		}

		Query sumVoice234gmw = session.createSQLQuery(
				"SELECT SUM( VOICE_REVENU ),SUM( SMS_REVENU ),SUM( DATA ),SUM( TOTAL_VOICE_TRAFFIC_IC ),SUM( TOTAL_SMS_TRAFFIC_IC ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '1' and TRANSMISSION = 'MW'");
		if (sumVoice234gmw.uniqueResult() != null) {
			Object[] obj = (Object[]) sumVoice234gmw.uniqueResult();
			if (obj[0] != null) {
				allVoice.add(Float.parseFloat(obj[0].toString()));
			} else {
				allVoice.add(mfloat);
			}

			if (obj[1] != null) {
				allSms.add(Float.parseFloat(obj[1].toString()));
			} else {
				allSms.add(mfloat);
			}

			if (obj[2] != null) {
				allData.add(Float.parseFloat(obj[2].toString()));
			} else {
				allData.add(mfloat);
			}

			if (obj[3] != null) {
				allVoiceIc.add(Float.parseFloat(obj[3].toString()));
			} else {
				allVoiceIc.add(mfloat);
			}

			if (obj[4] != null) {
				allSmsIc.add(Float.parseFloat(obj[4].toString()));
			} else {
				allSmsIc.add(mfloat);
			}

		} else {
			allVoice.add(mfloat);
			allSms.add(mfloat);
			allData.add(mfloat);
			allVoiceIc.add(mfloat);
			allSmsIc.add(mfloat);
		}

		tx.commit();
		session.close();
		List<List<Float>> totalRevenue = new ArrayList<List<Float>>();
		totalRevenue.add(allVoice);
		totalRevenue.add(allSms);
		totalRevenue.add(allData);
		totalRevenue.add(allVoiceIc);
		totalRevenue.add(allSmsIc);
		System.out.println();
		return totalRevenue;

	}

	private List<Float> getTechAndTransmissionExpenses() {
		float mfloat = 0f;
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		List<Float> expenses = new ArrayList<Float>();

		Query ex2gf = session.createSQLQuery(
				"SELECT SUM( TOTAL_OPEX_MONTHLY ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='0' and TECH_4G = '0' and TRANSMISSION = 'Fiber'");
		if (ex2gf.uniqueResult() != null) {

			expenses.add(Float.parseFloat(ex2gf.uniqueResult().toString()));

		} else {
			expenses.add(mfloat);
		}

		Query ex2gm = session.createSQLQuery(
				"SELECT SUM( TOTAL_OPEX_MONTHLY ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='0' and TECH_4G = '0' and TRANSMISSION = 'MW'");
		if (ex2gm.uniqueResult() != null) {

			expenses.add(Float.parseFloat(ex2gm.uniqueResult().toString()));

		} else {
			expenses.add(mfloat);
		}

		Query sumVoice23g = session.createSQLQuery(
				"SELECT SUM( TOTAL_OPEX_MONTHLY ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '0'  and TRANSMISSION = 'Fiber'");
		if (sumVoice23g.uniqueResult() != null) {

			expenses.add(Float.parseFloat(sumVoice23g.uniqueResult().toString()));

		} else {
			expenses.add(mfloat);
		}

		Query sumVoice23gmw = session.createSQLQuery(
				"SELECT SUM( TOTAL_OPEX_MONTHLY ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '0'  and TRANSMISSION = 'MW'");
		if (sumVoice23gmw.uniqueResult() != null) {

			expenses.add(Float.parseFloat(sumVoice23gmw.uniqueResult().toString()));

		} else {
			expenses.add(mfloat);
		}

		Query sumVoice234g = session.createSQLQuery(
				"SELECT SUM( TOTAL_OPEX_MONTHLY ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '1' and TRANSMISSION = 'Fiber'");
		if (sumVoice234g.uniqueResult() != null) {

			expenses.add(Float.parseFloat(sumVoice234g.uniqueResult().toString()));

		} else {
			expenses.add(mfloat);
		}

		Query sumVoice234gmw = session.createSQLQuery(
				"SELECT SUM( TOTAL_OPEX_MONTHLY ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '1' and TRANSMISSION = 'MW'");
		if (sumVoice234gmw.uniqueResult() != null) {

			expenses.add(Float.parseFloat(sumVoice234gmw.uniqueResult().toString()));

		} else {
			expenses.add(mfloat);
		}

		tx.commit();
		session.close();

		return expenses;

	}

	/*
	 * ************************* transmission **************************
	 */
	private List<Float> getTransmissionExpenses() {
		float mfloat = 0f;
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		List<Float> expenses = new ArrayList<Float>();

		Query ex2gf = session.createSQLQuery(
				"SELECT SUM( TOTAL_OPEX_MONTHLY ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TRANSMISSION = 'Fiber'");
		if (ex2gf.uniqueResult() != null) {

			expenses.add(Float.parseFloat(ex2gf.uniqueResult().toString()));

		} else {
			expenses.add(mfloat);
		}

		Query sumVoice23g = session.createSQLQuery(
				"SELECT SUM( TOTAL_OPEX_MONTHLY ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TRANSMISSION = 'MW'");
		if (sumVoice23g.uniqueResult() != null) {

			expenses.add(Float.parseFloat(sumVoice23g.uniqueResult().toString()));

		} else {
			expenses.add(mfloat);
		}

		tx.commit();
		session.close();

		return expenses;

	}

	private List<Float> getTechExpenses() {
		float mfloat = 0f;
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		List<Float> expenses = new ArrayList<Float>();

		Query ex2gf = session.createSQLQuery(
				"SELECT SUM( TOTAL_OPEX_MONTHLY ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='0' and TECH_4G = '0'");
		if (ex2gf.uniqueResult() != null) {

			expenses.add(Float.parseFloat(ex2gf.uniqueResult().toString()));

		} else {
			expenses.add(mfloat);
		}

		Query sumVoice23g = session.createSQLQuery(
				"SELECT SUM( TOTAL_OPEX_MONTHLY ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '0'");
		if (sumVoice23g.uniqueResult() != null) {

			expenses.add(Float.parseFloat(sumVoice23g.uniqueResult().toString()));

		} else {
			expenses.add(mfloat);
		}

		Query sumVoice234g = session.createSQLQuery(
				"SELECT SUM( TOTAL_OPEX_MONTHLY ) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '1'");
		if (sumVoice234g.uniqueResult() != null) {

			expenses.add(Float.parseFloat(sumVoice234g.uniqueResult().toString()));

		} else {
			expenses.add(mfloat);
		}

		tx.commit();
		session.close();

		return expenses;

	}

	private Object[] areaProfitAndLoss() {
		float mfloat = 0f;
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		Object[] obj;
		Query ex2gf = session.createSQLQuery(
				"SELECT  SUM (PROFIT_AND_LOSS_2G),SUM (PROFIT_AND_LOSS_2G_3G),SUM (PROFIT_AND_LOSS_2G_3G_4G) FROM AREA_FINANCE");
		if (ex2gf.uniqueResult() != null) {
			obj = (Object[]) ex2gf.uniqueResult();
			if (obj[0] == null) {
				obj[0] = 0;
			}
			if (obj[1] == null) {
				obj[1] = 0;
			}
			if (obj[2] == null) {
				obj[2] = 0;
			}
		} else {
			obj = new Object[1];
			obj[0] = 0;
		}

		tx.commit();
		session.close();

		return obj;

	}

	private List<Float> siteProfitAndLossTechAndTx() {
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		List<Float> list = new ArrayList<Float>();

		Query ex2gf = session.createSQLQuery(
				"SELECT  SUM (PROFIT_AND_LOSS) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='0' and TECH_4G = '0' and TRANSMISSION = 'Fiber' ");
		if (ex2gf.uniqueResult() != null) {
			list.add(Float.parseFloat(ex2gf.uniqueResult().toString()));

		} else {
			list.add(0f);
		}

		Query ex2gm = session.createSQLQuery(
				"SELECT  SUM (PROFIT_AND_LOSS) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='0' and TECH_4G = '0' and TRANSMISSION = 'MW' ");
		if (ex2gm.uniqueResult() != null) {
			list.add(Float.parseFloat(ex2gm.uniqueResult().toString()));
		} else {
			list.add(0f);
		}
		// ******************************************************************
		Query ex23gf = session.createSQLQuery(
				"SELECT  SUM (PROFIT_AND_LOSS) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '0' and TRANSMISSION = 'Fiber' ");
		if (ex23gf.uniqueResult() != null) {
			list.add(Float.parseFloat(ex23gf.uniqueResult().toString()));

		} else {
			list.add(0f);
		}

		Query ex23gm = session.createSQLQuery(
				"SELECT  SUM (PROFIT_AND_LOSS) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '0' and TRANSMISSION = 'MW' ");
		if (ex23gm.uniqueResult() != null) {
			list.add(Float.parseFloat(ex23gm.uniqueResult().toString()));

		} else {
			list.add(0f);
		}
		// ******************************************************************
		Query ex234gf = session.createSQLQuery(
				"SELECT  SUM (PROFIT_AND_LOSS) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '1' and TRANSMISSION = 'Fiber' ");
		if (ex234gf.uniqueResult() != null) {
			list.add(Float.parseFloat(ex234gf.uniqueResult().toString()));

		} else {
			list.add(0f);
		}

		Query ex234gm = session.createSQLQuery(
				"SELECT  SUM (PROFIT_AND_LOSS) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '1' and TRANSMISSION = 'MW' ");
		if (ex234gm.uniqueResult() != null) {
			list.add(Float.parseFloat(ex234gm.uniqueResult().toString()));

		} else {
			list.add(0f);
		}

		tx.commit();
		session.close();

		return list;

	}

	private List<Float> sitetechProfitAndLoss() {
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		List<Float> list = new ArrayList<Float>();

		Query ex2gf = session.createSQLQuery(
				"SELECT  SUM (PROFIT_AND_LOSS) FROM WAREHOUSE_PROFIT_LOSS  where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='0' and TECH_4G = '0'");
		if (ex2gf.uniqueResult() != null) {
			list.add(Float.parseFloat(ex2gf.uniqueResult().toString()));

		} else {
			list.add(0f);
		}

		// ******************************************************************
		Query ex23gf = session.createSQLQuery(
				"SELECT  SUM (PROFIT_AND_LOSS) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '0'");
		if (ex23gf.uniqueResult() != null) {
			list.add(Float.parseFloat(ex23gf.uniqueResult().toString()));

		} else {
			list.add(0f);
		}

		// ******************************************************************
		Query ex234gf = session.createSQLQuery(
				"SELECT  SUM (PROFIT_AND_LOSS) FROM WAREHOUSE_PROFIT_LOSS where trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '1'");
		if (ex234gf.uniqueResult() != null) {
			list.add(Float.parseFloat(ex234gf.uniqueResult().toString()));

		} else {
			list.add(0f);
		}
		tx.commit();
		session.close();

		return list;

	}

	private Map<String, Object> getTop10AreaProfite() {
		Map<String, Object> map = new HashMap<String, Object>();
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		List<Float> top10Profit = new ArrayList<Float>();
		List<String> top10ProfitName = new ArrayList<String>();
		Query topSiteRevenue = session.createSQLQuery(
				"SELECT SUM(SUM_PROFIT_LOSS),AREA_ID,AREA_NAME FROM AREA_FINANCE where SUM_PROFIT_LOSS > 0 GROUP  BY AREA_ID,AREA_NAME ORDER BY SUM(SUM_PROFIT_LOSS) ASC FETCH NEXT 10 ROWS ONLY");
		List<Object[]> areaList = topSiteRevenue.list();

		if (areaList != null) {

			for (Object[] obj : areaList) {
				if (obj[0] != null) {
					top10Profit.add(Float.parseFloat(obj[0].toString()));

				} else {
					top10Profit.add(0f);
				}
				if (obj[1] != null || obj[2] != null) {
					String st = "";
					if (obj[1] != null) {
						st = obj[1].toString() + ", ";
					}
					if (obj[2] != null) {
						st += obj[2].toString();
					}
					top10ProfitName.add(st);
				} else {
					top10ProfitName.add("No Area Name And ID");
				}
			}

		} else {
			top10Profit.add(0f);
			top10ProfitName.add("No Area Name And ID");
		}
		map.put("areaProfit", top10Profit);
		map.put("areaName", top10ProfitName);
		tx.commit();
		session.close();
		return map;
	}

	private Map<String, Object> getTop10AreaLoss() {
		Map<String, Object> map = new HashMap<String, Object>();
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		List<Float> top10Profit = new ArrayList<Float>();
		List<String> top10ProfitName = new ArrayList<String>();
		Query topSiteRevenue = session.createSQLQuery(
				"SELECT SUM(SUM_PROFIT_LOSS),AREA_ID,AREA_NAME FROM AREA_FINANCE where SUM_PROFIT_LOSS < 0 GROUP  BY AREA_ID,AREA_NAME ORDER BY SUM(SUM_PROFIT_LOSS) ASC FETCH NEXT 10 ROWS ONLY");
		List<Object[]> areaList = topSiteRevenue.list();

		if (areaList != null) {

			for (Object[] obj : areaList) {
				if (obj[0] != null) {
					top10Profit.add(Float.parseFloat(obj[0].toString()));

				} else {
					top10Profit.add(0f);
				}
				if (obj[1] != null || obj[2] != null) {
					String st = "";
					if (obj[1] != null) {
						st = obj[1].toString() + ", ";
					}
					if (obj[2] != null) {
						st += obj[2].toString();
					}
					top10ProfitName.add(st);
				} else {
					top10ProfitName.add("No Area Name And ID");
				}
			}

		} else {
			top10Profit.add(0f);
			top10ProfitName.add("No Area Name And ID");
		}
		map.put("areaLoss", top10Profit);
		map.put("areaName", top10ProfitName);
		tx.commit();
		session.close();
		return map;
	}

	private List<Float> fiberVsMwMoreProfit(){
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		List<Float> total = new ArrayList<Float>();
		Query q = session.createSQLQuery(
				"SELECT SUM(PROFIT_AND_LOSS),count(TRANSMISSION) from WAREHOUSE_PROFIT_LOSS WHERE trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TRANSMISSION = 'Fiber'");
		Object[] obj = (Object[])q.uniqueResult();
		if(obj!=null) {
			if(obj[0]!=null && obj[1].toString()!="0") {
				float nbr = (Float.parseFloat(obj[0].toString())/Float.parseFloat(obj[1].toString()));
				total.add(nbr);
			}else {total.add(0f);}
		}
		
		Query q2 = session.createSQLQuery(
				"SELECT SUM(PROFIT_AND_LOSS),count(TRANSMISSION) from WAREHOUSE_PROFIT_LOSS WHERE trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TRANSMISSION = 'MW'");
		Object[] obj2 = (Object[])q2.uniqueResult();
		if(obj2!=null) {
			if(obj2[0]!=null && obj2[1].toString()!="0") {
				float nbr = (Float.parseFloat(obj2[0].toString())/Float.parseFloat(obj2[1].toString()));
				total.add(nbr);
			}else {total.add(0f);}
		}
		
		tx.commit();
		session.close();
		return total;
	}
	
	private List<Float> techMoreProfit(){
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		List<Float> total = new ArrayList<Float>();
		Query q = session.createSQLQuery(
				"SELECT SUM(PROFIT_AND_LOSS),count(TECH_2G) from WAREHOUSE_PROFIT_LOSS WHERE trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='0' and TECH_4G = '0'");
		Object[] obj = (Object[])q.uniqueResult();
		if(obj!=null) {
			if(obj[0]!=null && obj[1].toString()!="0") {
				float nbr = (Float.parseFloat(obj[0].toString())/Float.parseFloat(obj[1].toString()));
				total.add(nbr);
			}else {total.add(0f);}
		}
		
		Query q2 = session.createSQLQuery(
				"SELECT SUM(PROFIT_AND_LOSS),count(TECH_3G) from WAREHOUSE_PROFIT_LOSS WHERE trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '0'");
		Object[] obj2 = (Object[])q2.uniqueResult();
		if(obj2!=null) {
			if(obj2[0]!=null && obj2[1].toString()!="0") {
				float nbr = (Float.parseFloat(obj2[0].toString())/Float.parseFloat(obj2[1].toString()));
				total.add(nbr);
			}else {total.add(0f);}
		}
		
		Query q3 = session.createSQLQuery(
				"SELECT SUM(PROFIT_AND_LOSS),count(TECH_4G) from WAREHOUSE_PROFIT_LOSS WHERE trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TECH_2G = '1' and TECH_3G='1' and TECH_4G = '1'");
		Object[] obj3 = (Object[])q3.uniqueResult();
		if(obj3!=null) {
			if(obj3[0]!=null && obj3[1].toString()!="0") {
				float nbr = (Float.parseFloat(obj3[0].toString())/Float.parseFloat(obj3[1].toString()));
				total.add(nbr);
			}else {total.add(0f);}
		}
		
		tx.commit();
		session.close();
		return total;
	}
	private List<Float> siteTrProfitAndLoss(){
		// ************************************************************************************
		// get profit and loss respect to transmission type
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		
		List<Float> fiberMwTrList = new ArrayList<Float>();
		// List<Float> mwTrList = new ArrayList<Float>();
		Query fiberTr = session.createSQLQuery(
				"SELECT SUM(PROFIT_AND_LOSS) FROM WAREHOUSE_PROFIT_LOSS WHERE trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TRANSMISSION = 'Fiber'");
		if (fiberTr.uniqueResult() != null) {
			fiberMwTrList.add(Float.parseFloat(fiberTr.uniqueResult().toString()));
		} else {
			fiberMwTrList.add(0f);
		}

		Query mwTr = session
				.createSQLQuery("SELECT SUM(PROFIT_AND_LOSS) FROM WAREHOUSE_PROFIT_LOSS WHERE trunc(START_DATE) >= to_timestamp('"+dbDateFrom+"', 'MM-DD-YYYY') and trunc(END_DATE) <= to_timestamp('"+dbDateTo+"', 'MM-DD-YYYY') and TRANSMISSION = 'MW'");
		if (mwTr.uniqueResult() != null) {
			fiberMwTrList.add(Float.parseFloat(mwTr.uniqueResult().toString()));
		} else {
			fiberMwTrList.add(0f);
		}
		tx.commit();
		session.close();
		return fiberMwTrList;
	}
}
