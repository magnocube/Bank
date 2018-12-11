package client;

import java.net.*;

import org.w3c.dom.Document;

import java.io.*;

import static java.lang.Thread.sleep;

public class ServerAction {
	 private static Socket client;
	 private static int port = 8500;
	 private static String serverName = "145.24.222.69";
	 private static DataOutputStream dOut;
	 private static DataInputStream dIn;
	 private static AES aes = new AES();
     private static XML xml = new XML();
     serverConnection server = new serverConnection(serverName,port,aes, xml);
     String errorMessage="";
     private String money="";
    public ServerAction()
    {
    	int pogingen=0;
    	while(server.connect()==false)
    	{
    		pogingen+=1;
    		System.out.println("error connecting try "+pogingen);
    		
    		if(pogingen>5)
    		{
    			System.out.println("error connection, program will stop");
    			System.exit(1);
    		}
    		sleep(500);
    				
    	}
       
	}
    public boolean withdraw(String pasnumber,String bankNumber,String pinCode,int amount)
    {
    	errorMessage="";
    	money="";
		String xmlSegment =(xml.writeXML("withdraw",pasnumber,bankNumber,pinCode+"",amount+"","leave this area for error messages or notes"));
        System.out.println(xml.getXMLType(xmlSegment));
       
		server.send(aes.encrypt(xmlSegment));

		String incoming="";
		int pogingen=0;
        incoming = server.receive();
        if(incoming==null)
        {
        	return false;
        }
        Document doc = xml.convertStringToDocument(incoming);
        if(doc.getElementsByTagName("actionType").item(0).getTextContent().equals("withdrawconfirm"))
        {
        	if(doc.getElementsByTagName("banknumber").item(0).getTextContent().equals(bankNumber))
        	{
        		if( doc.getElementsByTagName("pasnumber").item(0).getTextContent().equals(pasnumber))
        		{
        			if( doc.getElementsByTagName("succes").item(0).getTextContent().equals("true"))
        			{
        				return true;
        			}
        			else
        			{
        				errorMessage=doc.getElementsByTagName("errorNote").item(0).getTextContent();
            			return false;
        			}
        		}
        	}
        }
        errorMessage=doc.getElementsByTagName("errorNote").item(0).getTextContent();
		return false;
    }
    public String getError()
    {
    	return errorMessage;
    }
    public String getMoney()
    {
    	return money;
    }
    private void sleep(int time)
    {
    	try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	public boolean getSaldo(String pasnumber, String bankNumber, String pinCode) {
		money="";
		errorMessage="";
		String xmlSegment =(xml.writeXML("getSaldo",pasnumber,bankNumber,pinCode+"","","leave this area for error messages or notes"));
        System.out.println(xml.getXMLType(xmlSegment));
       
		server.send(aes.encrypt(xmlSegment));

		String incoming="";
		int pogingen=0;
        incoming = server.receive();
        if(incoming==null)
        {
        	return false;
        }
        Document doc = xml.convertStringToDocument(incoming);
        if(doc.getElementsByTagName("actionType").item(0).getTextContent().equals("saldoReply"))
        {
        	if(doc.getElementsByTagName("banknumber").item(0).getTextContent().equals(bankNumber))
        	{
        		if( doc.getElementsByTagName("pasnumber").item(0).getTextContent().equals(pasnumber))
        		{
        			if( doc.getElementsByTagName("succes").item(0).getTextContent().equals("true"))
        			{
        			money = doc.getElementsByTagName("amount").item(0).getTextContent();
        			return true;
        			}
        			else
        			{
        				errorMessage=doc.getElementsByTagName("errorNote").item(0).getTextContent();
            			return false;
        			}
        		}
        	}
        }
        errorMessage=doc.getElementsByTagName("errorNote").item(0).getTextContent();
		return false;
	}
	public boolean login(String pasnumber, String bankNumber, String pinCode) {
		errorMessage="";
		String xmlSegment =(xml.writeXML("login",pasnumber,bankNumber,pinCode+"","","leave this area for error messages or notes"));
        System.out.println(xml.getXMLType(xmlSegment));
       
		server.send(aes.encrypt(xmlSegment));

		String incoming="";
		int pogingen=0;
        incoming = server.receive();
        if(incoming==null)
        {
        	return false;
        }
        Document doc = xml.convertStringToDocument(incoming);
        if(doc.getElementsByTagName("actionType").item(0).getTextContent().equals("loginReply"))
        {
        	if(doc.getElementsByTagName("banknumber").item(0).getTextContent().equals(bankNumber))
        	{
        		if( doc.getElementsByTagName("pasnumber").item(0).getTextContent().equals(pasnumber))
        		{
        			if( doc.getElementsByTagName("succes").item(0).getTextContent().equals("true"))
        			{
        				errorMessage=doc.getElementsByTagName("errorNote").item(0).getTextContent();
        				return true;
        			}
        			else
        			{
        				errorMessage=doc.getElementsByTagName("errorNote").item(0).getTextContent();
            			return false;
        			}
        		}
        	}
        }
        errorMessage=doc.getElementsByTagName("errorNote").item(0).getTextContent();
		return false;
	}



}