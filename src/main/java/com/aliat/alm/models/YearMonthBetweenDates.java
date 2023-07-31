package com.aliat.alm.models;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

public class YearMonthBetweenDates {
	
	public YearMonthBetweenDates() {}
	  

	   public List<Timestamp> datesBetween(Timestamp StartDate, Timestamp EndDate) throws ParseException {
		   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
         //  Date d1=sdf.parse(StartDate);
        //   Date d2=sdf.parse(EndDate);
		   System.out.println("d1111 " +StartDate);
		   System.out.println("d222 " +EndDate);
           Calendar cal=Calendar.getInstance();
		   System.out.println("d3333 ");

           cal.setTime(StartDate); 
		   System.out.println("d44444" );

		    List<Timestamp> ret = new ArrayList<Timestamp>();
			   System.out.println("d55555" );

		    while (cal.getTimeInMillis()<EndDate.getTime()) {
				   System.out.println("d66666" );
				   
				ret.add(new Timestamp(cal.getTime().getTime()));

		        cal.add(Calendar.MONTH, 1);
				   System.out.println("d77777" );
/*
               Date date= cal.getTime();
               Timestamp date1 = new Timestamp(date.getTime());
     		   System.out.println("d888888" );

                SimpleDateFormat format1=new SimpleDateFormat("MM-yyyy");
               // Timestamp date1=format1.format(date);
				   System.out.println("DATE11111 " +date);

             
		        ret.add(date1);
				   System.out.println("CALL " +cal);
		   		   System.out.println("RETTTT " +ret);
		   		   
*/		   		   

		    }
		    return ret;

		}
}