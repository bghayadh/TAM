package com.aliat.alm.common;

//import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
public class Form {

	//@SuppressWarnings("unchecked")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/NavigationNP", method = RequestMethod.GET)
	public <t> String[]  NavigationNP(Session session, String moduleName, String moduleIDName,String moduleIDValue,String moduleLMD,String navAction) throws ParseException {
	String result [] =new String[3];
	int SelectedIndex = 0,LastIndex=0;
	Query q = null; String query = "";
	JSONObject rowNav = new JSONObject();
	JSONParser jsonParser = new JSONParser();

				query =( "select json_object('CountAll' value (select Count (*) from "+moduleName+"),"
				+ " 'CurrentRowNum' value (select row_num from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where "+moduleIDName+" = :param1) )  from dual");
				q = session.createNativeQuery(query);
				q.setParameter("param1", moduleIDValue);

				rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
				result[0]=	rowNav.get("CountAll").toString() ;
				result[1]= 	rowNav.get("CurrentRowNum").toString() ;
				LastIndex= Integer.parseInt(result[0]);

				if(navAction.equals("1")) {

					query =( "select json_object('NextAreaId' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where row_num ="
							+ " (select row_num from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where "+moduleIDName+" = :param1) + 1) )  from dual");
							q = session.createNativeQuery(query);
							q.setParameter("param1", moduleIDValue);
							rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
							SelectedIndex= Integer.parseInt(result[1])+1;
							result[1]= 	SelectedIndex+"" ;
							result[2]= 	rowNav.get("NextAreaId").toString() ;

				}else if(navAction.equals("0")){

					query =( "select json_object('PrevAreaId' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where row_num = "
							+ "(select row_num from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where "+moduleIDName+" = :param1) - 1) )  from dual");
							q = session.createNativeQuery(query);
							q.setParameter("param1", moduleIDValue);

							rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
							SelectedIndex= Integer.parseInt(result[1])-1;
						    result[1]= 	SelectedIndex+"" ;
						    result[2]= 	rowNav.get("PrevAreaId").toString() ;

				}
	else if(navAction.equals("3")){
		query =( "select json_object('LasttID' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where row_num = "+1+" ) )  from dual");
		q = session.createNativeQuery(query);
		rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
	    result[1]= 	1+"" ;
	    result[2]= 	rowNav.get("LasttID").toString() ;

	}else if(navAction.equals("4")){
		query =( "select json_object('LasttID' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where row_num = "+LastIndex+" ) )  from dual");
		q = session.createNativeQuery(query);
		rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
	    result[1]= 	LastIndex+"" ;
	    result[2]= 	rowNav.get("LasttID").toString() ;

	}else {
					result[2]= moduleIDValue ;

				}
				
		
		

	return result;

	//return "header";

	}
	
	///// DN pending nav //////////////
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/dnNavigation", method = RequestMethod.GET)
	public String[] dnNavigation(Session session, String queryBase, String moduleIDName, String moduleIDValue, String moduleLMD, String navAction) throws ParseException {
	    String[] result = new String[3];
	    int currentIndex;
	    int lastIndex;
	    Query q;
	    JSONObject rowNav;
	    JSONParser jsonParser = new JSONParser();

	    // Step 1: Get total count and current row number
	    String countAndRowQuery =
	            "SELECT json_object(" +
	            "'CountAll' VALUE (SELECT COUNT(*) FROM (" + queryBase + ")), " +
	            "'CurrentRowNum' VALUE (SELECT row_num FROM (SELECT " + moduleIDName +
	            ", ROW_NUMBER() OVER (ORDER BY " + moduleLMD + " DESC, " + moduleIDName + " DESC) AS row_num FROM (" + queryBase + ")) " +
	            "WHERE " + moduleIDName + " = :param1)) FROM dual";

	    q = session.createNativeQuery(countAndRowQuery);
	    q.setParameter("param1", moduleIDValue);
	    rowNav = (JSONObject) jsonParser.parse(q.getSingleResult().toString());

	    result[0] = rowNav.get("CountAll").toString();
	    result[1] = rowNav.get("CurrentRowNum").toString();

	    lastIndex = Integer.parseInt(result[0]);
	    currentIndex = Integer.parseInt(result[1]);

	    switch (navAction) {
	        case "1": // Next
	            if (currentIndex < lastIndex) {
	                int nextIndex = currentIndex + 1;
	                String nextId = getNavigationId(session, queryBase, moduleIDName, moduleLMD, nextIndex, jsonParser, "NextAreaId");
	                if (!nextId.equals(moduleIDValue)) {
	                    result[1] = String.valueOf(nextIndex);
	                    result[2] = nextId;
	                } else {
	                    result[2] = moduleIDValue;
	                }
	            } else {
	                result[2] = moduleIDValue;
	            }
	            break;

	        case "0": // Previous
	            if (currentIndex > 1) {
	                int prevIndex = currentIndex - 1;
	                String prevId = getNavigationId(session, queryBase, moduleIDName, moduleLMD, prevIndex, jsonParser, "PrevAreaId");
	                if (!prevId.equals(moduleIDValue)) {
	                    result[1] = String.valueOf(prevIndex);
	                    result[2] = prevId;
	                } else {
	                    result[2] = moduleIDValue;
	                }
	            } else {
	                result[2] = moduleIDValue;
	            }
	            break;

	        case "3": // First
	            result[1] = "1";
	            result[2] = getNavigationId(session, queryBase, moduleIDName, moduleLMD, 1, jsonParser, "FirstID");
	            break;

	        case "4": // Last
	            result[1] = String.valueOf(lastIndex);
	            result[2] = getNavigationId(session, queryBase, moduleIDName, moduleLMD, lastIndex, jsonParser, "LastID");
	            break;

	        default: // Same/Current
	            result[2] = moduleIDValue;
	            break;
	    }

	    return result;
	}

	private String getNavigationId(Session session, String queryBase, String moduleIDName, String moduleLMD, int rowNum, JSONParser jsonParser, String label) throws ParseException {
	    String navQuery =
	            "SELECT json_object('" + label + "' VALUE (SELECT " + moduleIDName +
	            " FROM (SELECT " + moduleIDName + ", ROW_NUMBER() OVER (ORDER BY " + moduleLMD + " DESC, " + moduleIDName + " DESC) AS row_num FROM (" + queryBase + ")) " +
	            "WHERE row_num = " + rowNum + ")) FROM dual";

	    Query q = session.createNativeQuery(navQuery);
	    JSONObject rowNav = (JSONObject) jsonParser.parse(q.getSingleResult().toString());
	    return rowNav.get(label).toString();
	}


	
	
	
	
	
	///////////////////////////////////////node nav///////////////////////////////////////
	//@SuppressWarnings("unchecked")
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/NodeNavigationNP", method = RequestMethod.GET)
		public <t> String[]  NodeNavigationNP(Session session, String moduleName, String moduleIDName,String moduleIDValue,String moduleLMD,String navAction) throws ParseException {
		String result [] =new String[3];
		int SelectedIndex = 0,LastIndex=0;
		Query q = null; String query = "";
		JSONObject rowNav = new JSONObject();
		JSONParser jsonParser = new JSONParser();

					query =( "select json_object('CountAll' value (select Count (*) from "+moduleName+" where ACTIVE_RECORD =1),"
					+ " 'CurrentRowNum' value (select row_num from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+" where ACTIVE_RECORD = 1) where "+moduleIDName+" = :param1) )  from dual");
					q = session.createNativeQuery(query);
					q.setParameter("param1", moduleIDValue);

					rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
					result[0]=	rowNav.get("CountAll").toString() ;
					result[1]= 	rowNav.get("CurrentRowNum").toString() ;
					LastIndex= Integer.parseInt(result[0]);

					if(navAction.equals("1")) {

						query =( "select json_object('NextAreaId' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+" where ACTIVE_RECORD = 1) where row_num ="
								+ " (select row_num from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+" where ACTIVE_RECORD = 1) where "+moduleIDName+" = :param1) + 1) )  from dual");
								q = session.createNativeQuery(query);
								q.setParameter("param1", moduleIDValue);
								rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
								SelectedIndex= Integer.parseInt(result[1])+1;
								result[1]= 	SelectedIndex+"" ;
								result[2]= 	rowNav.get("NextAreaId").toString() ;

					}else if(navAction.equals("0")){

						query =( "select json_object('PrevAreaId' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+" where ACTIVE_RECORD = 1) where row_num = "
								+ "(select row_num from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+" where ACTIVE_RECORD = 1) where "+moduleIDName+" = :param1) - 1) )  from dual");
								q = session.createNativeQuery(query);
								q.setParameter("param1", moduleIDValue);

								rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
								SelectedIndex= Integer.parseInt(result[1])-1;
							    result[1]= 	SelectedIndex+"" ;
							    result[2]= 	rowNav.get("PrevAreaId").toString() ;

					}
		else if(navAction.equals("3")){
			query =( "select json_object('LasttID' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+" where ACTIVE_RECORD = 1) where row_num = "+1+" ) )  from dual");
			q = session.createNativeQuery(query);
			rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
		    result[1]= 	1+"" ;
		    result[2]= 	rowNav.get("LasttID").toString() ;

		}else if(navAction.equals("4")){
			query =( "select json_object('LasttID' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+" where ACTIVE_RECORD = 1) where row_num = "+LastIndex+" ) )  from dual");
			q = session.createNativeQuery(query);
			rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
		    result[1]= 	LastIndex+"" ;
		    result[2]= 	rowNav.get("LasttID").toString() ;

		}else {
						result[2]= moduleIDValue ;

					}
					
			
			

		return result;

		//return "header";

		}
	//////////////////////////////////////////////////////////////////////////////////////
	
	//@SuppressWarnings("unchecked")
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/NavNextPrev", method = RequestMethod.GET)
		public <t> String[]  NavNextPrev(EntityManager entityManager, String moduleName, String moduleIDName,String moduleIDValue,String moduleLMD,String navAction) throws ParseException {
		String result [] =new String[3];
		int SelectedIndex = 0,LastIndex=0;
		Query q = null; String query = "";
		JSONObject rowNav = new JSONObject();
		JSONParser jsonParser = new JSONParser();

					query =( "select json_object('CountAll' value (select Count (*) from "+moduleName+"),"
					+ " 'CurrentRowNum' value (select row_num from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where "+moduleIDName+" = :param1) )  from dual");
					q = entityManager.createNativeQuery(query);
					q.setParameter("param1", moduleIDValue);

					rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
					result[0]=	rowNav.get("CountAll").toString() ;
					result[1]= 	rowNav.get("CurrentRowNum").toString() ;
					LastIndex= Integer.parseInt(result[0]);

					if(navAction.equals("1")) {

						query =( "select json_object('NextAreaId' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where row_num ="
								+ " (select row_num from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where "+moduleIDName+" = :param1) + 1) )  from dual");
								q = entityManager.createNativeQuery(query);
								q.setParameter("param1", moduleIDValue);
								rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
								SelectedIndex= Integer.parseInt(result[1])+1;
								result[1]= 	SelectedIndex+"" ;
								result[2]= 	rowNav.get("NextAreaId").toString() ;

					}else if(navAction.equals("0")){

						query =( "select json_object('PrevAreaId' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where row_num = "
								+ "(select row_num from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where "+moduleIDName+" = :param1) - 1) )  from dual");
								q = entityManager.createNativeQuery(query);
								q.setParameter("param1", moduleIDValue);

								rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
								SelectedIndex= Integer.parseInt(result[1])-1;
							    result[1]= 	SelectedIndex+"" ;
							    result[2]= 	rowNav.get("PrevAreaId").toString() ;

					}
		else if(navAction.equals("3")){
			query =( "select json_object('LasttID' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where row_num = "+1+" ) )  from dual");
			q = entityManager.createNativeQuery(query);
			rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
		    result[1]= 	1+"" ;
		    result[2]= 	rowNav.get("LasttID").toString() ;

		}else if(navAction.equals("4")){
			query =( "select json_object('LasttID' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where row_num = "+LastIndex+" ) )  from dual");
			q = entityManager.createNativeQuery(query);
			rowNav =  (JSONObject) jsonParser.parse(q.getSingleResult().toString());
		    result[1]= 	LastIndex+"" ;
		    result[2]= 	rowNav.get("LasttID").toString() ;

		}else {
						result[2]= moduleIDValue ;

					}
					
			
			

		return result;

		//return "header";

		}
}
