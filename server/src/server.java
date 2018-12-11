import java.net.*;
import java.io.*;
import java.sql.*;
import org.w3c.dom.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class server extends Thread {
    private static String databaseUrl = "jdbc:mysql://localhost:3306/bank?verifyServerCertificate=false&useSSL=true&requireSSL=false";
    private static String username = "root";
    private static String password = "4cP47d";
    private static int port = 8500;
    private ServerSocket serverSocket;
    private Socket server;
    static DataOutputStream dOut;
    static DataInputStream dIn;
    public Session ATM_session;
    public server(int port) {
        try {
            String fileName = createLogger();
            Monitor monitor = new Monitor(this,fileName);
            monitor.start();

            serverSocket = new ServerSocket(port);
            try{
                while(true) {
                    makeSession();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void makeSession() throws Exception {

        //System.out.println("Waiting for new client on port " + serverSocket.getLocalPort() + "...");
        server = serverSocket.accept();
        new Session(server,logger,this).start();
        System.out.println("Just connected to " + server.getRemoteSocketAddress());
        logger.info("connected to " + server.getRemoteSocketAddress());

    }
    public static Logger logger = Logger.getLogger("MyLog");
    public static FileHandler fh;

    public static void main(String[] args) {

        try {
                new server(port);
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

    private static String createLogger() {
        String fileName="";
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime now = LocalDateTime.now();
            fileName=dtf.format(now)+".log";
            System.out.println(fileName);
            File file = new File("./logs/"+fileName);
            file.createNewFile();
            // This block configure the logger with handler and formatter
            fh = new FileHandler("./logs/"+fileName);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();

            fh.setFormatter(formatter);
            logger.setUseParentHandlers(false);
            // the following statement is used to log any messages


        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("server started");
        return fileName;
    }
}
