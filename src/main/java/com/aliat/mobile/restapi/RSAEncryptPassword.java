package com.aliat.mobile.restapi;


import java.io.File;
import java.io.FileInputStream;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;
import java.security.PublicKey;


public class RSAEncryptPassword{
    public static void main(String[] args) throws Exception{
    	String password = "1357";
        byte[] input = password.getBytes();
        FileInputStream inStream = new FileInputStream(new File("C:\\ALM32\\password_ecryption_files\\almAirtime.crt"));
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
        inStream.close();
        PublicKey publickey = (PublicKey)cert.getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publickey);
        byte[] encryptedBytes = cipher.doFinal(input);
        String encryptedString = new String(org.bouncycastle.util.encoders.Base64.encode(encryptedBytes)).trim();
        System.out.println("encrypted: " + encryptedString);

    	
    	//String command = "curl -k -d grant_type=password&username= katelecom&password= katelecoM20 -H Authorization: Basic MkZ3Uk9aQ2tTazZkNVFnY1R2ZEROZnhEWDRFYTpLeTZxTElUaFc5NTU2eEFRUG9LejFyQ3JCaGNh https://api.telkom.co.ke/token";
    	//Process process = Runtime.getRuntime().exec(command);
    	//System.out.println("process=: " + mapper.writeValueAsString(process.getOutputStream()));
    	
    	//String command = "curl -X POST https://your_url/path --data foo=bar"; 
    	//Process process = Runtime.getRuntime().exec(command);
    	//process.getInputStream();
    	//System.out.println("process=: " + process.getInputStream());
	
    }
}