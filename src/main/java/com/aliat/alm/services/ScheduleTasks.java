package com.aliat.alm.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.models.AssetDepreciation12Month;

@Controller
public class ScheduleTasks {
//	public static void main(String[] args) {
//		LineChart12Month();
//	}
	@RequestMapping(value = "/AssetDepreciation12Month", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> AssetDepreciation12Month() {
		List<String> monthName = new ArrayList<String>();
		List<Float> initCost = new ArrayList<Float>();
		List<Float> Acc = new ArrayList<Float>();
		List<Float> netCost = new ArrayList<Float>();
		long startTime = System.currentTimeMillis();
		ALMSessions almsessions = new ALMSessions();
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();

		session.createSQLQuery("TRUNCATE TABLE ASSET_DEPRECIATION_12MONTH").executeUpdate();
		
		System.out.println("clear the table first");
		// ********************* Line Chart (12 month) *********************
					Query month12Query = session.createSQLQuery(
							"select  (SUM (INITIALCOST) OVER ()),(SUM (ACCUMULDEPRECAMNT) OVER ()),(SUM (NETCOST) OVER ()),to_char(CREATED_DATE, 'month-yyyy') from FIXED_ASSET_REGISTRY where CREATED_DATE <= add_months(trunc(sysdate, 'month'), -10) ORDER BY CREATED_DATE DESC FETCH NEXT 1 ROWS ONLY");
					Object[] month12 = (Object[]) month12Query.uniqueResult();
					if (month12 != null) {

						if (month12[3] != null) {
							monthName.add(month12[3].toString().replaceAll("\\s+", ""));
						} else {
							month12[3] = ".";
						}
						if (month12[0] != null) {
							initCost.add(Float.parseFloat(month12[0].toString()));
						} else {
							month12[0] = 0;
						}
						if (month12[1] != null) {
							Acc.add(Float.parseFloat(month12[1].toString()));
						} else {
							month12[1] = 0;
						}
						if (month12[1] != null) {
							netCost.add(Float.parseFloat(month12[2].toString()));
						} else {
							month12[2] = 0;
						}
					}
					// ************************************************************************************
					Query month11Query = session.createSQLQuery(
							"select  (SUM (INITIALCOST) OVER ()),(SUM (ACCUMULDEPRECAMNT) OVER ()),(SUM (NETCOST) OVER ()),to_char(CREATED_DATE, 'month-yyyy') from FIXED_ASSET_REGISTRY where CREATED_DATE <= add_months(trunc(sysdate, 'month'), -9) ORDER BY CREATED_DATE DESC FETCH NEXT 1 ROWS ONLY");
					Object[] month11 = (Object[]) month11Query.uniqueResult();
					if (month11 != null) {

						if (month11[3] != null) {
							monthName.add(month11[3].toString().replaceAll("\\s+", ""));
						} else {
							month11[3] = ".";
						}
						if (month11[0] != null) {
							initCost.add(Float.parseFloat(month11[0].toString()));
						} else {
							month11[0] = 0;
						}
						if (month11[1] != null) {
							Acc.add(Float.parseFloat(month11[1].toString()));
						} else {
							month11[1] = 0;
						}
						if (month11[2] != null) {
							netCost.add(Float.parseFloat(month11[2].toString()));
						} else {
							month11[2] = 0;
						}
					}
					// ************************************************************************************
					Query month10Query = session.createSQLQuery(
							"select  (SUM (INITIALCOST) OVER ()),(SUM (ACCUMULDEPRECAMNT) OVER ()),(SUM (NETCOST) OVER ()),to_char(CREATED_DATE, 'month-yyyy') from FIXED_ASSET_REGISTRY where CREATED_DATE <= add_months(trunc(sysdate, 'month'), -8) ORDER BY CREATED_DATE DESC FETCH NEXT 1 ROWS ONLY");
					Object[] month10 = (Object[]) month10Query.uniqueResult();
					if (month10 != null) {
						if (month10[3] != null) {
							monthName.add(month10[3].toString().replaceAll("\\s+", ""));
						} else {
							month10[3] = ".";
						}
						if (month10[0] != null) {
							initCost.add(Float.parseFloat(month10[0].toString()));
						} else {
							month10[3] = 0;
						}
						if (month10[1] != null) {
							Acc.add(Float.parseFloat(month10[1].toString()));
						} else {
							month10[1] = 0;
						}
						if (month10[2] != null) {
							netCost.add(Float.parseFloat(month10[2].toString()));
						} else {
							month10[2] = 0;
						}
					}
					// ************************************************************************************
					Query month9Query = session.createSQLQuery(
							"select  (SUM (INITIALCOST) OVER ()),(SUM (ACCUMULDEPRECAMNT) OVER ()),(SUM (NETCOST) OVER ()),to_char(CREATED_DATE, 'month-yyyy') from FIXED_ASSET_REGISTRY where CREATED_DATE <= add_months(trunc(sysdate, 'month'), -7) ORDER BY CREATED_DATE DESC FETCH NEXT 1 ROWS ONLY");
					Object[] month9 = (Object[]) month9Query.uniqueResult();
					if (month9 != null) {
						if (month9[3] != null) {
							monthName.add(month9[3].toString().replaceAll("\\s+", ""));
						} else {
							month9[3] = ".";
						}
						if (month9[0] != null) {
							initCost.add(Float.parseFloat(month9[0].toString()));
						} else {
							month9[0] = 0;
						}
						if (month9[1] != null) {
							Acc.add(Float.parseFloat(month9[1].toString()));
						} else {
							month9[1] = 0;
						}
						if (month9[2] != null) {
							netCost.add(Float.parseFloat(month9[2].toString()));
						} else {
							month9[2] = 0;
						}
					}
					// ************************************************************************************
					Query month8Query = session.createSQLQuery(
							"select  (SUM (INITIALCOST) OVER ()),(SUM (ACCUMULDEPRECAMNT) OVER ()),(SUM (NETCOST) OVER ()),to_char(CREATED_DATE, 'month-yyyy') from FIXED_ASSET_REGISTRY where CREATED_DATE <= add_months(trunc(sysdate, 'month'), -6) ORDER BY CREATED_DATE DESC FETCH NEXT 1 ROWS ONLY");
					Object[] month8 = (Object[]) month8Query.uniqueResult();
					if (month8 != null) {
						if (month8[3] != null) {
							monthName.add(month8[3].toString().replaceAll("\\s+", ""));
						} else {
							month8[3] = ".";
						}
						if (month8[0] != null) {
							initCost.add(Float.parseFloat(month8[0].toString()));
						} else {
							month8[0] = 0;
						}
						if (month8[1] != null) {
							Acc.add(Float.parseFloat(month8[1].toString()));
						} else {
							month8[1] = 0;
						}
						if (month8[2] != null) {
							netCost.add(Float.parseFloat(month8[2].toString()));
						} else {
							month8[2] = 0;
						}
					}
					// ************************************************************************************
					Query month7Query = session.createSQLQuery(
							"select  (SUM (INITIALCOST) OVER ()),(SUM (ACCUMULDEPRECAMNT) OVER ()),(SUM (NETCOST) OVER ()),to_char(CREATED_DATE, 'month-yyyy') from FIXED_ASSET_REGISTRY where CREATED_DATE <= add_months(trunc(sysdate, 'month'), -5) ORDER BY CREATED_DATE DESC FETCH NEXT 1 ROWS ONLY");
					Object[] month7 = (Object[]) month7Query.uniqueResult();
					if (month7 != null) {
						if (month7[3] != null) {
							monthName.add(month7[3].toString().replaceAll("\\s+", ""));
						} else {
							month7[3] = ".";
						}
						if (month7[0] != null) {
							initCost.add(Float.parseFloat(month7[0].toString()));
						} else {
							month7[0] = 0;
						}
						if (month7[1] != null) {
							Acc.add(Float.parseFloat(month7[1].toString()));
						} else {
							month7[1] = 0;
						}
						if (month7[2] != null) {
							netCost.add(Float.parseFloat(month7[2].toString()));
						} else {
							month7[2] = 0;
						}
					}
					// ************************************************************************************
					Query month6Query = session.createSQLQuery(
							"select  (SUM (INITIALCOST) OVER ()),(SUM (ACCUMULDEPRECAMNT) OVER ()),(SUM (NETCOST) OVER ()),to_char(CREATED_DATE, 'month-yyyy') from FIXED_ASSET_REGISTRY where CREATED_DATE <= add_months(trunc(sysdate, 'month'), -4) ORDER BY CREATED_DATE DESC FETCH NEXT 1 ROWS ONLY");
					Object[] month6 = (Object[]) month6Query.uniqueResult();
					if (month6 != null) {
						if (month6[3] != null) {
							monthName.add(month6[3].toString().replaceAll("\\s+", ""));
						} else {
							month6[3] = ".";
						}
						if (month6[0] != null) {
							initCost.add(Float.parseFloat(month6[0].toString()));
						} else {
							month6[0] = 0;
						}
						if (month6[1] != null) {
							Acc.add(Float.parseFloat(month6[1].toString()));
						} else {
							month6[1] = 0;
						}
						if (month6[2] != null) {
							netCost.add(Float.parseFloat(month6[2].toString()));
						} else {
							month6[2] = 0;
						}
					}
					// ************************************************************************************
					Query month5Query = session.createSQLQuery(
							"select  (SUM (INITIALCOST) OVER ()),(SUM (ACCUMULDEPRECAMNT) OVER ()),(SUM (NETCOST) OVER ()),to_char(CREATED_DATE, 'month-yyyy') from FIXED_ASSET_REGISTRY where CREATED_DATE <= add_months(trunc(sysdate, 'month'), -3) ORDER BY CREATED_DATE DESC FETCH NEXT 1 ROWS ONLY");
					Object[] month5 = (Object[]) month5Query.uniqueResult();
					if (month5 != null) {
						if (month5[3] != null) {
							monthName.add(month5[3].toString().replaceAll("\\s+", ""));
						} else {
							month5[3] = ".";
						}
						if (month5[0] != null) {
							initCost.add(Float.parseFloat(month5[0].toString()));
						} else {
							month5[0] = 0;
						}
						if (month5[1] != null) {
							Acc.add(Float.parseFloat(month5[1].toString()));
						} else {
							month5[1] = 0;
						}
						if (month5[2] != null) {
							netCost.add(Float.parseFloat(month5[2].toString()));
						} else {
							month5[2] = 0;
						}
					}
					// ************************************************************************************
					Query month4Query = session.createSQLQuery(
							"select  (SUM (INITIALCOST) OVER ()),(SUM (ACCUMULDEPRECAMNT) OVER ()),(SUM (NETCOST) OVER ()),to_char(CREATED_DATE, 'month-yyyy') from FIXED_ASSET_REGISTRY where CREATED_DATE <= add_months(trunc(sysdate, 'month'), -2) ORDER BY CREATED_DATE DESC FETCH NEXT 1 ROWS ONLY");
					Object[] month4 = (Object[]) month4Query.uniqueResult();
					if (month4 != null) {
						if (month4[3] != null) {
							monthName.add(month4[3].toString().replaceAll("\\s+", ""));
						} else {
							month4[3] = ".";
						}
						if (month4[0] != null) {
							initCost.add(Float.parseFloat(month4[0].toString()));
						} else {
							month4[0] = 0;
						}
						if (month4[1] != null) {
							Acc.add(Float.parseFloat(month4[1].toString()));
						} else {
							month4[1] = 0;
						}
						if (month4[2] != null) {
							netCost.add(Float.parseFloat(month4[2].toString()));
						} else {
							month4[2] = 0;
						}
					}
					// ************************************************************************************
					Query month3Query = session.createSQLQuery(
							"select  (SUM (INITIALCOST) OVER ()),(SUM (ACCUMULDEPRECAMNT) OVER ()),(SUM (NETCOST) OVER ()),to_char(CREATED_DATE, 'month-yyyy') from FIXED_ASSET_REGISTRY where CREATED_DATE <= add_months(trunc(sysdate, 'month'), -1) ORDER BY CREATED_DATE DESC FETCH NEXT 1 ROWS ONLY");
					Object[] month3 = (Object[]) month3Query.uniqueResult();
					if (month3 != null) {
						if (month3[3] != null) {
							monthName.add(month3[3].toString().replaceAll("\\s+", ""));
						} else {
							month3[3] = ".";
						}
						if (month3[0] != null) {
							initCost.add(Float.parseFloat(month3[0].toString()));
						} else {
							month3[0] = 0;
						}
						if (month3[1] != null) {
							Acc.add(Float.parseFloat(month3[1].toString()));
						} else {
							month3[1] = 0;
						}
						if (month3[2] != null) {
							netCost.add(Float.parseFloat(month3[2].toString()));
						} else {
							month3[2] = 0;
						}
					}
					// ************************************************************************************
					Query month2Query = session.createSQLQuery(
							"select (SUM (INITIALCOST) OVER ()),(SUM (ACCUMULDEPRECAMNT) OVER ()),(SUM (NETCOST) OVER ()),to_char(CREATED_DATE, 'month-yyyy') from FIXED_ASSET_REGISTRY where CREATED_DATE <= trunc(sysdate, 'month')  ORDER BY CREATED_DATE DESC FETCH NEXT 1 ROWS ONLY");
					Object[] month2 = (Object[]) month2Query.uniqueResult();
					if (month2 != null) {
						if (month2[3] != null) {
							monthName.add(month2[3].toString().replaceAll("\\s+", ""));
						} else {
							month2[3] = ".";
						}
						if (month2[0] != null) {
							initCost.add(Float.parseFloat(month2[0].toString()));
						} else {
							month2[0] = 0;
						}
						if (month2[1] != null) {
							Acc.add(Float.parseFloat(month2[1].toString()));
						} else {
							month2[1] = 0;
						}
						if (month2[2] != null) {
							netCost.add(Float.parseFloat(month2[2].toString()));
						} else {
							month2[2] = 0;
						}
					}
					// ************************************************************************************
					Query month1Query = session.createSQLQuery(
							"select (SUM (INITIALCOST) OVER ()),(SUM (ACCUMULDEPRECAMNT) OVER ()),(SUM (NETCOST) OVER ()),to_char(CREATED_DATE, 'month-yyyy') from FIXED_ASSET_REGISTRY  ORDER BY CREATED_DATE DESC FETCH NEXT 1 ROWS ONLY");
					Object[] month1 = (Object[]) month1Query.uniqueResult();
					if (month1 != null) {
						try {
							if(month2 !=null) {
							if (month2[3] != null || !month2[3].equals(null) || !month2[3].toString().equals("")) {
								System.out.println("enter if ");
								if (month1[3].equals(month2[3]) || month1[3] == month2[3]) {
									// we don't have values in the current month
									Query currentMonth = session
											.createSQLQuery("SELECT TO_CHAR (SYSDATE, 'month-yyyy') \"NOW\" FROM DUAL");
									String month = (String) currentMonth.uniqueResult().toString().replaceAll("\\s+", "");
									monthName.add(month);
								} else {
									System.out.println("else");
									if (month1[3] != null) {
										monthName.add(month1[3].toString().replaceAll("\\s+", ""));
									} else {
										month1[3] = ".";
									}
								}
							}} else {
								if (month1[3] != null) {
									monthName.add(month1[3].toString().replaceAll("\\s+", ""));
								} else {
									month1[3] = ".";
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e.getMessage());
						} finally {
							System.out.println("finich try*************");
						}

						if (month1[0] != null) {
							initCost.add(Float.parseFloat(month1[0].toString()));
						} else {
							month1[0] = 0;
						}
						if (month1[1] != null) {
							Acc.add(Float.parseFloat(month1[1].toString()));
						} else {
							month1[1] = 0;
						}
						if (month1[2] != null) {
							netCost.add(Float.parseFloat(month1[2].toString()));
						} else {
							month1[2] = 0;
						}
					}
					if (Acc.isEmpty()) {
						Acc.add(Float.parseFloat("0"));
					}
					if (netCost.isEmpty()) {
						netCost.add(Float.parseFloat("0"));
					}
					if (initCost.isEmpty()) {
						initCost.add(Float.parseFloat("0"));
					}
					if (monthName.isEmpty()) {
						monthName.add(".");
					}
					
					
					if(monthName.size() == initCost.size() && Acc.size() == netCost.size() && monthName.size() == netCost.size()) {
						for(int i=0;i<monthName.size();i++) {
						session.save(new AssetDepreciation12Month(initCost.get(i),Acc.get(i),netCost.get(i),monthName.get(i)));
						if(i % 30 ==0) {
							session.flush();
							session.clear();
						}
						}
					}
					
					tx.commit();
					session.close();
					long finishTime = System.currentTimeMillis();
					System.out.println("take time "+ (finishTime-finishTime));
					Map<String,String> map = new HashMap<String, String>();
					map.put("res", "success");
					return map;
	}
	
}
