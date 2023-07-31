package com.aliat.alm.Parser;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


public class XMLReaderCLASSES {

	public static void main(String argv[]) {

	    try {

	    File fXmlFile = new File("C:\\Dumps\\CFGDATA.xml");
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	            
	    //optional, but recommended
	    //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	    doc.getDocumentElement().normalize();

	    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	            
	    NodeList nList = doc.getElementsByTagName("APPLICATION");
	            
	    System.out.println("----------------------------");

	   for (int temp = 0; temp < nList.getLength(); temp++) {
	        Node nNode = nList.item(temp);
	                
	        System.out.println("\nCurrent Element first :" + nNode.getNodeName());
	                
	        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	            Element eElement = (Element) nNode;
	            NodeList nList2 = eElement.getElementsByTagName("attributes");
	            for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {

	    	        Node nNode2 = nList2.item(temp2);
	    	        System.out.println("\nCurrent Element second :" + nNode2.getNodeName());
	    	        Element eElement2 = (Element) nNode2;
	    	        
	    	        NodeList nList3 = eElement.getElementsByTagName(eElement2.getElementsByTagName("AID").item(0).getNodeName());
	    	        for (int temp3 = 0; temp3 < nList3.getLength(); temp3++) {
	    	        	Node nNode3 = nList3.item(temp3);
	    	        	System.out.println("\nCurrent Element third :" + nNode3.getNodeName());
	    	        	Element eElement3 = (Element) nNode3;

	    	            System.out.println("AID : " +((NodeList) nNode3).item(0).getNodeValue());
	    	        }
	    	        
	    	        
	    	        NodeList nList4 = eElement.getElementsByTagName(eElement2.getElementsByTagName("AT").item(0).getNodeName());
	    	        for (int temp4 = 0; temp4 < nList4.getLength(); temp4++) {
	    	        	Node nNode4 = nList4.item(temp4);
	    	        	System.out.println("\nCurrent Element third :" + nNode4.getNodeName());
	    	        	Element eElement4 = (Element) nNode4;

	    	            System.out.println("AT : " +((NodeList) nNode4).item(0).getNodeValue());
	    	        }
	    	        
	    	        
	            }
	            
	            
	            

	          //  System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
	         //   System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
	          //  System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
	          //  System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

	        }
	        
	       // System.out.println("\nCurrent Element end by :" + nNode.getNodeName());
	    }
	    } catch (Exception e) {
	    e.printStackTrace();
	    }
	  }  
}

