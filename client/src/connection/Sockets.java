package connection;
// client
import java.net.*;
import java.io.*;

public class Sockets {
	private static Socket client;
	private static int port = 8500;
	private static String serverName = "145.24.222.69";
	private static DataOutputStream dOut;
	private static DataInputStream dIn;
	private static AES aes = new AES();
	public static void main(String[] args) {
		connect();		
		for (int i = 0; i < 100; i++) {
			//sendSomething(aes.encrypt("DEADBEEF"));
			WriteXML xml = new WriteXML();
			sendSomething(aes.encrypt(xml.makeXml()));
			receiveSomething();			
		}		
		disConnect(); // close conversation(client)
		
		
		System.out.println("finished");
	}
	private static void receiveSomething(){
		try {
			String data= dIn.readUTF();
			System.out.println("Message Reply: " + aes.decrypt(data));
			
		} catch (Exception e) {
			System.out.println("error,,, server did send nothing back");
			e.printStackTrace();
		}
	}
	private static void sendSomething(String sendString) {
		try {
			dOut.writeByte(1); // message type
			dOut.writeUTF(sendString); // message
			dOut.flush(); // send data
		} catch (Exception e) {
			System.out.println("connection not established,, can't send data");
		}
	}
	
	private static void connect() {
		System.out.println("Connecting to " + serverName + " on port " + port);
		try {
			client = new Socket(serverName, port);  // make socket
			dOut = new DataOutputStream(client.getOutputStream()); // make data out
			dIn = new DataInputStream(client.getInputStream()); // make data in
			System.out.println("Just connected to " + client.getRemoteSocketAddress());  // if no errors in try block,,, connection established
		} catch (Exception e) {
			System.out.println("connection not established!");
			
		}

	}
	private static void disConnect() {
		try {
			dOut.writeByte(-1); // to indicate server to stop
			dOut.close();
		} catch (Exception e) {
		}

	}
	
}