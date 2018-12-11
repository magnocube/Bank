package rift;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.w3c.dom.Document;

import client.MainWindow;

public class requestTread extends Thread{
	private static String databaseUrl = "jdbc:mysql://localhost:3306/bank";//jdbc:mysql://localhost:3306/bank?verifyServerCertificate=false&useSSL=true&requireSSL=false";
	private static String username = "root";
	private static String password = "";
	private String encryptedMessage="";
	private Database database = new Database(databaseUrl, username , password);
	private String remoteIp="";
	private Socket socket=null;
	DataInputStream dIn;
    DataOutputStream dOut;
	public requestTread(Socket socketIn)
	{
		socket=socketIn;
	}
	public void run()
	{
		 try {
			dIn = new DataInputStream(socket.getInputStream());
			dOut = new DataOutputStream(socket.getOutputStream());
			encryptedMessage = dIn.readUTF();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		 
		remoteIp=socket.getRemoteSocketAddress().toString().split(":")[0];
		remoteIp = remoteIp.substring(1,remoteIp.length());	
		System.out.println("message from " +remoteIp);
		Document doc = null;
		XML xml=new XML();
		
		try{
			//first get the aes key from the database
			String keyFrom=database.getKey(remoteIp);
			doc = XML.convertStringToDocument(new AES().decrypt(encryptedMessage,keyFrom));
			String actionType=doc.getElementsByTagName("actionType").item(0).getTextContent();
			String bankFrom = doc.getElementsByTagName("afzender").item(0).getTextContent();
			String bankTo=doc.getElementsByTagName("banknumber").item(0).getTextContent();
			//check
			if(!remoteIp.equals(database.getIp(bankFrom)))
			{
				System.out.println("the request come from a illigal ip");
				throw new Exception();
			}
			int databaseID=0;
			if(actionType.equals("withdraw"))
			{
				System.out.println("type = withdraw");
				String pasnumber = doc.getElementsByTagName("pasnumber").item(0).getTextContent();
				String amount = doc.getElementsByTagName("amount").item(0).getTextContent();
				String automaatnr = doc.getElementsByTagName("automaatNr").item(0).getTextContent();
				
				System.out.println("from bank: "+bankFrom);
				System.out.println("to bank: "+bankTo);
				databaseID = database.archive(pasnumber,bankFrom,bankTo,amount,automaatnr);
				System.out.println("data saved in database");	
			}
			System.out.println("encrypting data for other bank");
			String keyTo=database.getKeyFromBanknumber(bankTo);
			String encryptedMessage=new AES().encrypt(xml.xmlToString(doc),keyTo);
			
			String destinationIp=database.getIp(bankTo);
			String destinationPort=database.getPort(bankTo);
			System.out.println("send to destination bank ip: "+destinationIp+" port: "+destinationPort);
			
			SocketConnector socketOut=new SocketConnector(destinationIp,Integer.parseInt(destinationPort));
			
			String replyMessage = new AES().decrypt(socketOut.send(encryptedMessage),keyTo);
			System.out.println(replyMessage);
			if(actionType.equals("withdraw"))//this is the actiontype of the first message
			{
				doc = XML.convertStringToDocument(replyMessage);
				actionType=doc.getElementsByTagName("actionType").item(0).getTextContent();
				String succes = doc.getElementsByTagName("succes").item(0).getTextContent();
				if(actionType.equals("withdrawconfirm"))//this is the actiontype of the reply message
				{
					if(succes.equals("true"))
					{
						database.setStatus(databaseID,"succesfull reply");
					}
					else
					{
						database.setStatus(databaseID,"unsuccesfull reply");
					}
				}
				else
				{
					database.setStatus(databaseID,"no reply");
				}
			}
			dOut.writeUTF(new AES().encrypt(replyMessage,keyFrom));
			dOut.flush();
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("er is iets fout gegaan");
			return;
		}
		
        
        
		
	}
}
