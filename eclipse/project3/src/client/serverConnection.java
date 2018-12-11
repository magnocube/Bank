package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


/**
 * Created by stefa on 3/21/2017.
 */
public class serverConnection {

    static Socket client;
    static String serverName = null;
    static XML xml;
    static int port;
    private static AES aes = null;
    static DataOutputStream dOut;
    static DataInputStream dIn;

    public serverConnection(String Sname, int Spoort, AES a, XML x) {
        this.serverName = Sname;
        this.port = Spoort;
        this.aes = a;
        this.xml = x;
    }


    public String receive() {
        String received = "null";
        try {
            received = aes.decrypt(dIn.readUTF());
            System.out.println("Message Reply: " + received);
            return received;

        } catch (Exception e) {
            System.out.println("error,,, server did send nothing back");
        }

        

        return "null";
    }

    public boolean connect() {
        System.out.println("Connecting to " + serverName + " on port " + port);
        try {
            client = new Socket(serverName, port);  // make socket
            dOut = new DataOutputStream(client.getOutputStream()); // make data out
            dIn = new DataInputStream(client.getInputStream()); // make data in
            System.out.println("Just connected to " + client.getRemoteSocketAddress());  // if no errors in try block,,, connection established
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public void disConnect() {
        try {
            dOut.writeByte(-1); // to indicate server to stop
            dOut.close();// close own connection
            client  = null;
            dIn = null;
        } catch (Exception e) {
        }

    }

    public void send(String s) {
        try {

            dOut.writeByte(1); // message type
            dOut.writeUTF(s); // message
            dOut.flush(); // send data

        } catch (Exception e) {
            System.out.println("connection not established,, can't send data");
        }
    }
}
