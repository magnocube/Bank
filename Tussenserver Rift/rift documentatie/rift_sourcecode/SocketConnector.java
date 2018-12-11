package rift;

import java.net.*;
import java.io.*;
import java.util.Random;


public class SocketConnector {
    public SocketConnector(String s, int i){
        serverName = s;
        port = i;
    }

    Socket client;
   DataOutputStream out;
   DataInputStream in;
    String serverName = "";
    int port;




    public void establishConnection(){
        try {
            client = new Socket(serverName, port);
            InputStream inFromServer = client.getInputStream();
            in = new DataInputStream(inFromServer);
            OutputStream outToServer = client.getOutputStream();
            out = new DataOutputStream(outToServer);

        }catch(IOException e) {

            System.out.println("no connection established");
        }
    }
    public void endConnection(){
        try {
            client.close();
        } catch(Exception e) {
            System.out.println("failed closing the connection");
        }
        client = null;
        in = null;
        out = null;
    }
    public String send(String s){
        establishConnection();
        try {
            out.writeUTF(s);
        } catch (IOException e)
        {
            System.out.println("something went wrong while sending to the server");
        }
        return receive();
    }
    public String receive(){
        try {

            return in.readUTF();

        } catch (IOException e)
        {
            System.out.println("something went wrong while receiving from the server");
        }
        endConnection();
        return null;
    }
}
