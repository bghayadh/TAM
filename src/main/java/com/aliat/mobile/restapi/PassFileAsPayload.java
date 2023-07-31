package com.aliat.mobile.restapi;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonObject;
 
public class PassFileAsPayload {
	public static void main(String[] args) throws Exception{
		 
	    BufferedReader reader = null;
	    HttpsURLConnection connection = null;
	    int flag = 0;
	    String api_response_code = null,response_message = null,registration_status,data;
	    System.out.println("Start filling parameter to Airtime" );

	    URL url = new URL("https://api.telkom.co.ke/ejaze/V3/ejazeAtp");
	    URLConnection con = url.openConnection();
	    HttpsURLConnection http = (HttpsURLConnection)con;
	    http.setRequestMethod("POST"); // PUT is another valid option
	    http.setDoOutput(true);
			Boolean connectionFailed = false;
		
                String pinencrypt="yrIjJpzC/C5n9xrPP8/ErHIYxunRZ/hHxLjWb2s6dpYrE18pvpVw3ENvi22pg9QycQM8H+pRkBsSjTCB/EmW64OVxzz8z2vCLnjpmomRSsleX+lyQ1+BekHQWoowtlB3FPgWLM2C7xyxdCB1gjed/XiU+/F5ytVs3mw9IT+tjS6QajN1mtgx0+9SajUFyq5xmcMyLudR8XdGTm6lRXK1epkgcoNgSN8ficJWvDiXFfHS+n/jIFjZ1tG6Sccn1kwPxGYIJl5ChzECWhyfKDGKy0CMxsruPTjaAB8ZLGkr9Oh9525XRluB+B11nS1D86+NpHvqxC7/JskDJVbI/zXZsYh8dYGvhQCedNw+0G/hndSoZmv0iRh7JIacr1Ay4qvAwrgAHicOeRo71qZLCS9DeeTVOFR8J83OeIVn2Ud19CZHghYNk0v6QCahvQfDaEufY2NcQKqCEnTnC1hUxbjbyT3Dz/OTwWKSWbclAzFwhQi9qpL5NQagnr5m3P9sxBmIvIsNRHxKFOsk5+c72y755Zw07jLQyBzFJ53yNDpMqkC50tfE8UjFFkz6Xg7k5TMZixHvUPutxQnbYyyALzJdE6PWwL35lgeEdpxeyEz47L/7LVK1N2fG/0/SWuxZqHp7stCZ/rf/4aI9HVzPirbNEdBOewEz3L3ZsLV9iCK1AUI=";
				System.out.println("Reading Airtime paraemters");
				//{ \"airtimeRequest\": { \"loginId\":\"Katelecomtest\", \"password\":\"katecoM2022%\", \"pin\": \"1357\", \"code\": \"8084\", \"sourcemsisdn\": \"254774898334\", \"destMsisdn\": \"254774258153\",\"amount\": \"10\", \"extrefnum\": \"1359726\"}}
				byte[] out = "{ \"airtimeRequest\": { \"loginId\":\"Katelecomtest\", \"password\":\"katecoM2022%\", \"pin\":  \"1357\" , \"code\": \"8084\", \"sourcemsisdn\": \"254774898334\", \"destMsisdn\": \"254774258153\",\"amount\": \"10\", \"extrefnum\": \"1359726\"}}" .getBytes(StandardCharsets.UTF_8);
				//int length = out.length;
				//System.out.println("out.length ="+length);
			//	String s = new String(out);
			//	System.out.println("out is:  "+s);
				
				JsonObject postData = new JsonObject();
				postData.addProperty("loginId", "Katelecomtest");
				postData.addProperty("password", "katecoM2022%");
				postData.addProperty("pin", pinencrypt);
				postData.addProperty("code", "8084");
				postData.addProperty("sourceMsisdn", "254774898334");
				postData.addProperty("destMsisdn", "254774258153");
				postData.addProperty("amount", "1");
				postData.addProperty("extrefnum", "1359726");
				JsonObject postData2 = new JsonObject();
				postData2.add("airtimeRequest", postData);
				System.out.println("postData2 is " +postData2.toString());				
				http.setFixedLengthStreamingMode(postData2.toString().getBytes("utf-8").length);
				http.setRequestProperty("Content-Type", "application/json");
				http.setRequestProperty("accept", "application/json");
				http.setRequestProperty("Authorization", "Bearer 3b87b6b6-24ae-32d1-a0eb-30fa71bb3ba3");
				System.out.println("Airtime before connect");
				http.connect();
				System.out.println("Airtime after connect");
				try {
					System.out.println("Airtime inside try");
					OutputStream os = http.getOutputStream();
					System.out.println("Airtime inside try after stream");
				    os.write(postData2.toString().getBytes());
				    System.out.println("Airtime inside try after write");
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				int code = http.getResponseCode();
				System.out.println("response code ="+code);
				/*
				JsonObject postData = new JsonObject();
				postData.addProperty("loginId", "Katelecomtest");
				postData.addProperty("password", "katecoM2022%");
				postData.addProperty("pin", "1357");
				postData.addProperty("code", "8084");
				postData.addProperty("sourceMsisdn", "254774898334");
				postData.addProperty("destMsisdn", "254774258153");
				postData.addProperty("amount", "1");
				postData.addProperty("extrefnum", "1359726"); 
				postData.add("airtimeRequest", postData);*/
				

				
				

		}
  
}
