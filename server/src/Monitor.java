import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Rene Schouten on 6/10/2017.
 */
public class Monitor extends Thread{
    server serverClass;
    String fileName;
    public Monitor(server _server, String _fileName)
    {
        serverClass=_server;
        fileName=_fileName;
    }
    public void run()
    {
        while(true)
        {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if(input.equals("help"))
            {
                System.out.println("-show atm");
                System.out.println("-atm status");
                System.out.println("-atm disable");
                System.out.println("-atm enable");
                System.out.println("-atm send message");
                System.out.println("-show log");

            }

            else if(input.equals("show atm"))
            {
                System.out.println("connections:");
                System.out.println(serverClass.ATM_session.server.getRemoteSocketAddress());
            }
            else if(input.equals("atm disable"))
            {
                System.out.println("disable");
                serverClass.ATM_session.send("<ATM_Request><actionType>print</actionType><message>disable</message></ATM_Request>");
            }
            else if(input.equals("atm enable"))
            {
                System.out.println("enable");
                serverClass.ATM_session.send("<ATM_Request><actionType>print</actionType><message>enable</message></ATM_Request>");
            }
            else if(input.equals("atm status"))
            {
                System.out.println("status");
                serverClass.ATM_session.send("<ATM_Request><actionType>print</actionType><message>status</message></ATM_Request>");
            }
            else if(input.length()>16) {
                if (input.substring(0, 16).equals("atm send message")) {
                    System.out.println("send message");
                    serverClass.ATM_session.send("<ATM_Request><actionType>print</actionType><message>message:" + input.substring(16, input.length())+"</message></ATM_Request>");
                }
            }
            else if(input.equals("show log"))
            {
                System.out.println("log:");
                String content = null;
                try {
                    content = new Scanner(new File("./logs/"+fileName)).useDelimiter("\\Z").next();
                    System.out.println(content);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                System.out.println("wrong input");
            }

        }
    }
}
