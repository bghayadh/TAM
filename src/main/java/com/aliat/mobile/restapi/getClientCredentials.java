package com.aliat.mobile.restapi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.ContentType;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.fasterxml.jackson.databind.ObjectMapper;




public class getClientCredentials {
	private static final Pattern pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*");
	private static final String consumerKey = "2FwROZCkSk6d5QgcTvdDNfxDX4Ea";//clientId //2FwROZCkSk6d5QgcTvdDNfxDX4Ea    _SGJcPQXiAdyGJiqRR9Yv2JPOnsa
	private static final String consumerSecret = "Ky6qLIThW9556xAQPoKz1rCrBhca";//client secret //Ky6qLIThW9556xAQPoKz1rCrBhca    qQAY4rz6Cn_ydRf4Os50WrcPMVUa
	private static final String tokenUrl = "https://api.telkom.co.ke/token"; //"https://api.telkom.co.ke/ejaze/V3/ejazeAtp"
	private static final String auth = consumerKey + ":" + consumerSecret;
	private static final String authentication = Base64.getEncoder().encodeToString(auth.getBytes());
	private static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws Exception{ 
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
		 System.out.println("before context" );
		 context.init(null, trustManager, new SecureRandom());
		 System.out.println("after context");
		 HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		 System.out.println("after httpsurl");
		
		 String content = "grant_type=password&username=katelecomapp&password=katelecoM20";
		    BufferedReader reader = null;
		    //HttpsURLConnection connection = null;
		    String returnValue = "";
		    System.out.println("Start Connect to get token id" );
		    System.out.println("authentication1: "+authentication );
		    
		    
		 //   String encryptedString = new String(org.bouncycastle.util.encoders.Base64.encode(auth.getBytes())).trim();
	   //     System.out.println("authentication2: " + encryptedString);
		    
		    //String command ="curl -k -d \"grant_type=password&username=katelecom22&password=katelecoM20\" -H \"Authorization: Basic X1NHSmNQUVhpQWR5R0ppcVJSOVl2MkpQT25zYTpxUUFZNHJ6NkNuX3lkUmY0T3M1MFdyY1BNVlVh\"  https://api.telkom.co.ke/token";

		
		    
		    HttpsURLConnection httpsurlconnection = null;
		    
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
		        System.out.println("before connect to getoutputstream" );
		        PrintStream os = new PrintStream(httpsurlconnection.getOutputStream());
		        System.out.println("After connect to getoutputstream" );
		        os.print(content);
		        os.close();
		        reader = new BufferedReader(new InputStreamReader(httpsurlconnection.getInputStream()));
		        String line = null;
		        StringWriter out = new StringWriter(httpsurlconnection.getContentLength() > 0 ? httpsurlconnection.getContentLength() : 2048);
		        System.out.println("before while loop");
		        while ((line = reader.readLine()) != null) {
		            out.append(line);
		        }
		        String response = out.toString();
		        Matcher matcher = pat.matcher(response);
		        
		        System.out.println("before if matcher");
		        System.out.println("matcher="+matcher.toString());
		        if (matcher.matches() && matcher.groupCount() > 0) {
		            returnValue = matcher.group(1);
		        }
		    } catch (Exception e) {		    	
		        System.out.println("Error : " + e.toString());		        
		        e.printStackTrace();
		    } finally {
		    	System.out.println("Finally");
		        if (reader != null) {
		            try {
		                reader.close();
		            } catch (IOException e) {
		            }
		        }
		        httpsurlconnection.disconnect();
		    }
		    System.out.println("TokenValue ="+returnValue);  
		 
		 
		   
         
		 /////////////////////////////////////////////////////////////////////
		 
		/* System.out.println(invokeCurlGet("\"grant_type=password&username=katelecom22&password=katelecoM20\" -H \"Authorization: Basic X1NHSmNQUVhpQWR5R0ppcVJSOVl2MkpQT25zYTpxUUFZNHJ6NkNuX3lkUmY0T3M1MFdyY1BNVlVh\" https://api.telkom.co.ke/token"));	  

	 }
	 
	 
	 public static String invokeCurlGet(String _host) throws IOException
	    {
	        byte[] res = execute("curl " + " -k -d " + _host, 0);

	        return new String(res);
	    }

	    public static byte[] execute(String _cmd, int _maxResLength) throws IOException
	    {
	    	System.out.println("command is:  "+_cmd);
	        Process process = Runtime.getRuntime().exec(_cmd);

	        try
	        {
	            int result = process.waitFor();
	            System.out.println("result is:  "+result);
	            if(result != 0)
	            {
	                throw new IOException("Fail to execute cammand. Exit Value[" + result + "], cmd => " + _cmd);
	            }
	        }
	        catch(InterruptedException e)
	        {
	            process.destroyForcibly();

	            throw new IOException(e);
	        }

	        BufferedInputStream in = null;

	        try
	        {
	            ByteArrayOutputStream out = new ByteArrayOutputStream();
	            in = new BufferedInputStream(process.getInputStream());
	            byte[] buf = new byte[1024];
	            int read = 0;

	            while((read = in.read(buf)) != -1)
	            {
	                out.write(buf, 0, read);
	                out.flush();

	               // if(_maxResLength > 0 && out.size() > _maxResLength)
	              //  {
	              //      throw new IOException("Response length exceeded.");
	              //  }
	            }
	            System.out.println("http.getResponseCode() " +mapper.writeValueAsString(out.toByteArray()));
	            return out.toByteArray();
	        }
	        finally
	        {
	            if(in != null)
	            {
	                in.close();
	            }
	        }*/
		 
		 //////////////////////////////////////////
		 

		

	
		 
		 
		 
	    } 
	 
	 
	 
}
