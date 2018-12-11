package rift;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class App extends Thread {
    private static int port = 8501;
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
        System.out.println("Rift has successfully started");
    }
}
