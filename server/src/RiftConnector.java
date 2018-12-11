

import java.net.*;
import java.io.*;
import java.util.Random;


public class RiftConnector {
    public RiftConnector(String s, int i){
        serverName = s;
        port = i;
    }

    Socket client;
    DataOutputStream out;
    DataInputStream in;
    String serverName = "";
    int port;




    public void establishConnection() throws IOException {
            client = new Socket(serverName, port);
            client.setSoTimeout(4000);
            InputStream inFromServer = client.getInputStream();
            in = new DataInputStream(inFromServer);
            OutputStream outToServer = client.getOutputStream();
            out = new DataOutputStream(outToServer);
    }
    public void endConnection() throws IOException {
            client.close();

        client = null;
        in = null;
        out = null;
    }
    public String sendToRift(String s) throws IOException {
        establishConnection();

            out.writeUTF(s);

        return receiveFromRift();
    }
    public String receiveFromRift() throws IOException {

            return in.readUTF();
    }
}
