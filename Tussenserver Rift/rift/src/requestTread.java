import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import org.w3c.dom.Document;



public class requestTread extends Thread{
	private static String databaseUrl = "jdbc:mysql://localhost:3306/mydb?verifyServerCertificate=false&useSSL=true&requireSSL=false";//jdbc:mysql://localhost:3306/bank?verifyServerCertificate=false&useSSL=true&requireSSL=false";
	private static String username = "root";
	private static String password = "4cP47d";
	private String encryptedMessage="";
	private Database database;
	private String remoteIp="";
	private Socket socket=null;
	DataInputStream dIn;
    DataOutputStream dOut;
	private Logger logger;
	public requestTread(Socket socketIn, Logger _logger)
	{
		logger=_logger;
		socket=socketIn;
		database = new Database(databaseUrl, username , password,_logger);
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
		logger.info("message from " +remoteIp);
		Document doc = null;
		XML xml=new XML();
		
		try{
			//first get the aes key from the database
			String keyFrom=database.getKey(remoteIp);
			logger.info(new AES().decrypt(encryptedMessage,keyFrom));
			doc = XML.convertStringToDocument(new AES().decrypt(encryptedMessage,keyFrom));
			String actionType=doc.getElementsByTagName("actionType").item(0).getTextContent();
			String bankFrom = doc.getElementsByTagName("afzender").item(0).getTextContent();
			String bankTo=doc.getElementsByTagName("banknumber").item(0).getTextContent();
			//check
			if(!remoteIp.equals(database.getIp(bankFrom)))
			{
				logger.info("the request come from a illigal ip");
				dOut.writeUTF("your ip is incorrect");
				dOut.flush();
				return;
			}
			int databaseID=0;
			if(actionType.equals("withdraw"))
			{
				logger.info("type = withdraw");
				String pasnumber = doc.getElementsByTagName("pasnumber").item(0).getTextContent();
				String amount = doc.getElementsByTagName("amount").item(0).getTextContent();

				logger.info("from bank: "+bankFrom);
				logger.info("to bank: "+bankTo);
				databaseID = database.archive(pasnumber,bankFrom,bankTo,amount,"1");
				logger.info("data saved in database");	
			}
			logger.info("encrypting data for other bank");
			String keyTo=database.getKeyFromBanknumber(bankTo);
			String encryptedMessage=new AES().encrypt(xml.xmlToString(doc),keyTo);
			
			String destinationIp=database.getIp(bankTo);
			String destinationPort=database.getPort(bankTo);
			logger.info("send to destination bank ip: "+destinationIp+" port: "+destinationPort);
			
			SocketConnector socketOut=new SocketConnector(destinationIp,Integer.parseInt(destinationPort));
			
			String replyMessage = new AES().decrypt(socketOut.send(encryptedMessage),keyTo);
			logger.info(replyMessage);
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
		}
		catch(Exception e)
		{
			logger.info("er is iets fout gegaan");
			logger.warning(e.getMessage());
			e.printStackTrace();
			try{
			dOut.writeUTF("an error occured "+e.toString()+", ask for logs");
			dOut.flush();
			}
			catch(Exception e2)
			{
				logger.info("fout met verzenden van foutmelding");
			}
			return;
		}
		
        
        
		
	}
}
