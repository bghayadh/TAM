package com.aliat.mobile.restapi;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import com.google.gson.JsonObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AirtimeRequest {
       private static final Pattern pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*");
       private static final String consumerKey = "2FwROZCkSk6d5QgcTvdDNfxDX4Ea";//clientId //2FwROZCkSk6d5QgcTvdDNfxDX4Ea    _SGJcPQXiAdyGJiqRR9Yv2JPOnsa
       private static final String consumerSecret = "Ky6qLIThW9556xAQPoKz1rCrBhca";//client secret //Ky6qLIThW9556xAQPoKz1rCrBhca    qQAY4rz6Cn_ydRf4Os50WrcPMVUa
       private static final String tokenUrl = "https://api.telkom.co.ke/token"; //"https://api.telkom.co.ke/ejaze/V3/ejazeAtp"
       private static final String auth = consumerKey + ":" + consumerSecret;
       private static final String authentication = Base64.getEncoder().encodeToString(auth.getBytes());
       public static void main(String[] args) throws Exception{
    	   
    	   System.out.println("step1");
    	   
              SSLContext context = SSLContext.getInstance("TLSv1.2");
              TrustManager[] trustManager = new TrustManager[] {
                   new X509TrustManager() {
                      public X509Certificate[] getAcceptedIssuers() {
                          return new X509Certificate[0];
                      }
                      public void checkClientTrusted(X509Certificate[] certificate, String str) {}
                      public void checkServerTrusted(X509Certificate[] certificate, String str) {}
                   }
              };
              System.out.println("step2");
              context.init(null, trustManager, new SecureRandom());
              HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
              
              String content = "grant_type=password&username=katelecomapp&password=katelecoM20";
                  BufferedReader readertoken = null;
                  String returnValue = "";

                  HttpsURLConnection httpsurlconnection = null;
                  System.out.println("step3");
                  try {
                      URL url = new URL(tokenUrl);
                      httpsurlconnection = (HttpsURLConnection) url.openConnection();
                      httpsurlconnection.setRequestMethod("POST");
                      httpsurlconnection.setDoOutput(true);
                      httpsurlconnection.setRequestProperty("username", "katelecomapp");//katelecomapp  katelecom22
                      httpsurlconnection.setRequestProperty("password", "katelecoM20" );//katelecoM20   katelecoM20
                      httpsurlconnection.setRequestProperty("Authorization", "Basic " + authentication);
                      httpsurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                      httpsurlconnection.setRequestProperty("Accept", "application/json");
                      PrintStream os = new PrintStream(httpsurlconnection.getOutputStream());
                      System.out.println("After connect to getoutputstream" );
                      os.print(content);
                      os.close();
                      readertoken = new BufferedReader(new InputStreamReader(httpsurlconnection.getInputStream()));
                      String line = null;
                      StringWriter out = new StringWriter(httpsurlconnection.getContentLength() > 0 ? httpsurlconnection.getContentLength() : 2048);
                      while ((line = readertoken.readLine()) != null) {
                          out.append(line);
                      }
                      String response = out.toString();
                      Matcher matcher = pat.matcher(response);
                     System.out.println("matcher="+matcher.toString());
                      if (matcher.matches() && matcher.groupCount() > 0) {
                          returnValue = matcher.group(1);
                      }
                  } catch (Exception e) {              
                      System.out.println("Error : " + e.toString());                
                      e.printStackTrace();
                  } finally {
                     System.out.println("Finally");
                      if (readertoken != null) {
                          try {
                            readertoken.close();
                          } catch (IOException e) {
                          }
                      }
                      httpsurlconnection.disconnect();
                  }
                  System.out.println("TokenValue ="+returnValue);  
              
              /////////////////////////////////////////////////////////////////////////////////
           System.out.println("Start filling parameter to Airtime" );

               HttpURLConnection urlConnection = null;
                     try {
                           //read variable daa amount, source msisdn and destination nmsisdn
                           String varamount="1";
                           String sourcenumber="254774898334";
                           String destinationnumber="254774258153"; //254774258153
                           
                           String data = "{\"airtimeRequest\": {\"loginId\": \"KatelecomTest\",\"password\": \"katecoM2022%\",\"pin\": \"1357\",\"code\": \"8084\",\"sourceMsisdn\": \""+ sourcenumber + "\",\"destMsisdn\": \"" + destinationnumber + "\",\"amount\": \"" + varamount+ "\",\"extrefnum\": \"1374925\"}}";
                           System.out.println("data1 is " +data);


                           System.out.println("Start connection");
                           URL url = new URL("https://api.telkom.co.ke/ejaze/v3/ejazeAtp");
                           System.out.println("Authorization = "+"Bearer "+returnValue);
                     
                           urlConnection = (HttpURLConnection) url.openConnection();
                           urlConnection.setRequestProperty("Content-Type", "application/json");
                           urlConnection.setRequestProperty("Authorization", "Bearer "+returnValue);//returnValue  "Bearer 46bff224-da9d-3d98-ad38-b4155d63f834"
                           urlConnection.setRequestProperty("Cookie", "route=845cfb0baf56b152b52a2267bb5b403abfc44ee5");
                           urlConnection.setRequestProperty("Accept", "application/json");
                           urlConnection.setRequestMethod("POST");
                           urlConnection.setDoOutput(true);
                           urlConnection.setDoInput(true);
                           urlConnection.setChunkedStreamingMode(0);
                           System.out.println("Start connection in process");
                           /// validate if we have access to URL
                           HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                           System.out.println("After Open Connection");
                           httpConnection.setConnectTimeout(5000);
                                                             urlConnection.connect();
                                  OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                                  BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                                  writer.write(data);
                                  writer.flush();
                                  System.out.println("Start connection success");

                                  int code = urlConnection.getResponseCode();
                                  System.out.println("code = " + code);
                                  BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                                  String line = null;
                                  while ((line = rd.readLine()) != null) {
                                         if (line.contains("airtimeResponse")) {
                                                System.out.println("our main responsemesage = " + line);
                       
                                         }
                                         
                                         if (line.contains("message")) {

                                                int n = 0,m=0;
                                                n = line.indexOf("message");
                                                m = line.indexOf("transactionId");
                                                String message = line.substring(n + 10, m-3);
                                                System.out.println("response_message = " + message);
                                         }
                                  }
                                  
                           
                                  

                           /////////////////////////////////////////////

                     } catch (Exception e) {
                           e.printStackTrace();
                     } finally {
                           if (urlConnection != null) {
                                  urlConnection.disconnect();

                           } 
                     }

              
                       

              }
  
}




 

