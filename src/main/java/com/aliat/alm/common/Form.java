package com.aliat.alm.common;

import org.hibernate.Query;
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
	@RequestMapping(value = "/NavigationNP", method = RequestMethod.GET)
	public <t> String[]  NavigationNP(Session session, String moduleName, String moduleIDName,String moduleIDValue,String moduleLMD,String navAction) throws ParseException {
	String result [] =new String[3];
	int SelectedIndex = 0,LastIndex=0;
	Query q = null; String query = "";
	JSONObject rowNav = new JSONObject();
	JSONParser jsonParser = new JSONParser();

				query =( "select json_object('CountAll' value (select Count (*) from "+moduleName+"),"
				+ " 'CurrentRowNum' value (select row_num from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where "+moduleIDName+" = :param1) )  from dual");
				q = session.createSQLQuery(query);
				q.setString("param1", moduleIDValue);

				rowNav =  (JSONObject) jsonParser.parse(q.uniqueResult().toString());
				result[0]=	rowNav.get("CountAll").toString() ;
				result[1]= 	rowNav.get("CurrentRowNum").toString() ;
				LastIndex= Integer.parseInt(result[0]);

				if(navAction.equals("1")) {

					query =( "select json_object('NextAreaId' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where row_num ="
							+ " (select row_num from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where "+moduleIDName+" = :param1) + 1) )  from dual");
							q = session.createSQLQuery(query);
							q.setString("param1", moduleIDValue);
							rowNav =  (JSONObject) jsonParser.parse(q.uniqueResult().toString());
							SelectedIndex= Integer.parseInt(result[1])+1;
							result[1]= 	SelectedIndex+"" ;
							result[2]= 	rowNav.get("NextAreaId").toString() ;

				}else if(navAction.equals("0")){

					query =( "select json_object('PrevAreaId' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where row_num = "
							+ "(select row_num from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where "+moduleIDName+" = :param1) - 1) )  from dual");
							q = session.createSQLQuery(query);
							q.setString("param1", moduleIDValue);

							rowNav =  (JSONObject) jsonParser.parse(q.uniqueResult().toString());
							SelectedIndex= Integer.parseInt(result[1])-1;
						    result[1]= 	SelectedIndex+"" ;
						    result[2]= 	rowNav.get("PrevAreaId").toString() ;

				}
	else if(navAction.equals("3")){
		query =( "select json_object('LasttID' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where row_num = "+1+" ) )  from dual");
		q = session.createSQLQuery(query);
		rowNav =  (JSONObject) jsonParser.parse(q.uniqueResult().toString());
	    result[1]= 	1+"" ;
	    result[2]= 	rowNav.get("LasttID").toString() ;

	}else if(navAction.equals("4")){
		query =( "select json_object('LasttID' value (select "+moduleIDName+" from (select "+moduleIDName+", ROW_NUMBER() OVER( ORDER BY "+moduleLMD+" DESC ) AS row_num from "+moduleName+") where row_num = "+LastIndex+" ) )  from dual");
		q = session.createSQLQuery(query);
		rowNav =  (JSONObject) jsonParser.parse(q.uniqueResult().toString());
	    result[1]= 	LastIndex+"" ;
	    result[2]= 	rowNav.get("LasttID").toString() ;

	}else {
					result[2]= moduleIDValue ;

				}
				
		
		

	return result;

	//return "header";

	}
}
