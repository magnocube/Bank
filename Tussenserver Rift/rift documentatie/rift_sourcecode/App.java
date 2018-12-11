package rift;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import client.TimeOutThread;

public class App extends Thread {
    private static int port = 8666;
    private ServerSocket serverSocket;
    private Socket server;




    public App(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }



    public void run() {
        while (true) {
            try {


                server = serverSocket.accept();
                new requestTread(server).start();


            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public static void main(String[] args) {

        try {
            Thread t = new App(port);
            t.start();
        } catch (IOException e) { }
        System.out.println("Rift has successfully started!");
    }
}
/*public class App {
	 
	public static void main(String args[])
	{
		String message="<ATM_Request><actionType>withdraw</actionType>d<pasnumber>30192283</pasnumber><afzender>01</afzender><banknumber>01</banknumber><pincode>hrZAupOCJ597BYtPOxj03Q==</pincode><amount>100</amount><automaatNr>1</automaatNr><errorNote>leave this area for error messages or notes</errorNote></ATM_Request>";
		String encryptedmessage = new AES().encrypt(message,"darkbankbankdark");
		String ip="145.24.222.69";
		requestTread requestThread = new requestTread(encryptedmessage,ip);
		Thread thread = new Thread(requestThread);
		thread.start();
	}
}*/