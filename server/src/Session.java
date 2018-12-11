import io.netty.handler.codec.sctp.SctpOutboundByteStreamHandler;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Rene Schouten on 6/6/2017.
 */
public class Session extends Thread {
    private DataInputStream dIn;
    private DataOutputStream dOut;
    private static String databaseUrl = "jdbc:mysql://localhost:3306/bank?verifyServerCertificate=false&useSSL=true&requireSSL=false";
    private static String username = "root";
    private static String password = "4cP47d";
    public Socket server;
    private Database database;
    private Logger logger;
    Encryption aes = new AES();
    private boolean isATM=false;
    public Session(Socket _server, Logger logger, server serverClass) {
        this.server = _server;
        this.logger=logger;
        database = new Database(databaseUrl, username , password);
        try {
            dIn = new DataInputStream(this.server.getInputStream());
            dOut = new DataOutputStream(this.server.getOutputStream());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        if(server.getPort()!=8501)
        {
            System.out.println("main ATM connected");
            isATM=true;
            serverClass.ATM_session=this;
            //aes=new AESIV();
        }

    }


    public void run() {
        while (true) {
            try {
                XML xml = new XML();
                boolean done = false;

                passwordAES passwordAes = new passwordAES();
                String result = dIn.readUTF();
                String decrypted = aes.decrypt(result);
                logger.info("Message : " + decrypted);
                Document doc = xml.convertStringToDocument(decrypted);
                if(doc.getElementsByTagName("actionType").item(0).getTextContent().equals("print"))//internal status message
                {
                    System.out.println(doc.getElementsByTagName("message").item(0).getTextContent());
                    logger.info(doc.getElementsByTagName("message").item(0).getTextContent());
                }else {
                    String passNumber = "";
                    String bankNumber = "";
                    String pincode = "";
                    String money = "";
                    passNumber = doc.getElementsByTagName("pasnumber").item(0).getTextContent();
                    bankNumber = doc.getElementsByTagName("banknumber").item(0).getTextContent();
                    pincode = passwordAes.encrypt(doc.getElementsByTagName("pincode").item(0).getTextContent() + "+" + passNumber.toLowerCase());
                    if (bankNumber.equals("070002")) {
                        if (database.checkPass(passNumber, pincode) == true) {
                            if (doc.getElementsByTagName("actionType").item(0).getTextContent().equals("withdraw")) {
                                //System.out.println("    - type withdraw");

                                money = doc.getElementsByTagName("amount").item(0).getTextContent();
                                if (database.withdraw(passNumber, Integer.parseInt(money)) == true) {
                                    dOut.writeUTF(aes.encrypt(XML.writeXML("withdrawConfirm", true, passNumber, bankNumber, "0", database.message)));
                                    logger.info("reply:" + XML.writeXML("withdrawConfirm", true, passNumber, bankNumber, "0", database.message));
                                    database.archive(true, passNumber, bankNumber, money, 1);
                                } else {
                                    dOut.writeUTF(aes.encrypt(XML.writeXML("withdrawConfirm", false, passNumber, bankNumber, "0", database.message)));
                                    logger.info("reply:" + XML.writeXML("withdrawConfirm", false, passNumber, bankNumber, "0", database.message));
                                }

                            } else if (doc.getElementsByTagName("actionType").item(0).getTextContent().equals("getSaldo")) {
                                //System.out.println("    - type getSaldo");

                                dOut.writeUTF(aes.encrypt(XML.writeXML("saldoReply", true, passNumber, bankNumber, database.getSaldo(passNumber), "")));
                                logger.info("reply:" + XML.writeXML("saldoReply", true, passNumber, bankNumber, database.getSaldo(passNumber), ""));
                            } else if (doc.getElementsByTagName("actionType").item(0).getTextContent().equals("login")) {
                                //System.out.println("    -login request");

                                dOut.writeUTF(aes.encrypt(XML.writeXML("loginReply", true, passNumber, bankNumber, "0", database.message)));
                                logger.info("reply:" + XML.writeXML("loginReply", true, passNumber, bankNumber, "0", database.message));
                            } else {
                                dOut.writeUTF(aes.encrypt(XML.writeXML("unknown", false, passNumber, bankNumber, "0", "invalid type")));
                                logger.info("reply:" + XML.writeXML("loginReply", true, passNumber, bankNumber, "0", database.message));
                            }
                        } else {
                            if (doc.getElementsByTagName("actionType").item(0).getTextContent().equals("withdraw")) {
                                dOut.writeUTF(aes.encrypt(XML.writeXML("withdrawConfirm", false, passNumber, bankNumber, "0", database.message)));
                                logger.info("reply:" + XML.writeXML("withdrawConfirm", false, passNumber, bankNumber, "0", database.message));
                            } else if (doc.getElementsByTagName("actionType").item(0).getTextContent().equals("getSaldo")) {
                                dOut.writeUTF(aes.encrypt(XML.writeXML("saldoReply", false, passNumber, bankNumber, "0", database.message)));
                                logger.info("reply:" + XML.writeXML("saldoReply", false, passNumber, bankNumber, "0", database.message));
                            } else if (doc.getElementsByTagName("actionType").item(0).getTextContent().equals("login")) {
                                dOut.writeUTF(aes.encrypt(XML.writeXML("loginReply", false, passNumber, bankNumber, "0", database.message)));
                                logger.info("reply:" + XML.writeXML("loginReply", false, passNumber, bankNumber, "0", database.message));
                            }
                        }
                    } else {
                        AES riftAes=new AES();
                        logger.info(">forwarded to other bank " + bankNumber);
                        String reply = "";
                        try {
                            RiftConnector c = new RiftConnector("145.24.222.69", 8501);
                            String message = xml.convertDocumentToString(doc);
                            String encryptedmessage =  riftAes.encrypt(message);
                            reply =  riftAes.decrypt(c.sendToRift(encryptedmessage));
                            logger.info("reply:" + reply);
                            dOut.writeUTF(aes.encrypt(reply));
                            xml.convertStringToDocument(reply);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (doc.getElementsByTagName("actionType").item(0).getTextContent().equals("withdraw")) {
                                dOut.writeUTF(aes.encrypt(XML.writeXML("withdrawConfirm", false, passNumber, bankNumber, "0", "RIFT_ERROR")));
                                logger.info("error:" + XML.writeXML("withdrawConfirm", false, passNumber, bankNumber, "0", "RIFT_ERROR"));
                            } else if (doc.getElementsByTagName("actionType").item(0).getTextContent().equals("getSaldo")) {
                                dOut.writeUTF(aes.encrypt(XML.writeXML("saldoReply", false, passNumber, bankNumber, "0", "RIFT_ERROR")));
                                logger.info("error:" + XML.writeXML("saldoReply", false, passNumber, bankNumber, "0", "RIFT_ERROR"));
                            } else if (doc.getElementsByTagName("actionType").item(0).getTextContent().equals("login")) {
                                dOut.writeUTF(aes.encrypt(XML.writeXML("loginReply", false, passNumber, bankNumber, "0", "RIFT_ERROR")));
                                logger.info("error:" + XML.writeXML("loginReply", false, passNumber, bankNumber, "0", "RIFT_ERROR"));
                            }
                        }

                    }
                }
                    dOut.flush(); // send data back
                    xml = null;
                } catch (Exception e) {
                logger.info(server.getRemoteSocketAddress() + " disconnected");
                System.out.println(server.getRemoteSocketAddress() + " disconnected");
                break;
            }
        }
    }
    public void send(String message)
    {
        try {
            dOut.writeUTF(aes.encrypt(message));
            dOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("message:"+message);
    }
}
