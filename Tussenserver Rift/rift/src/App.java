import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


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
                new requestTread(server,logger).start();


            } catch (Exception e) {e.printStackTrace();}
        }
    }
    public static void main(String[] args) {
        createLogger();
        try {
            Thread t = new App(port);
            t.start();
        } catch (IOException e) { }

        logger.info("Rift has successfully started");
    }

    public static Logger logger = Logger.getLogger("MyLog");
    public static FileHandler fh;
    private static void createLogger() {
        String fileName="";
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime now = LocalDateTime.now();
            fileName=dtf.format(now)+".log";
            System.out.println(fileName);
            File file = new File("./riftlogs/"+fileName);
            file.createNewFile();
            // This block configure the logger with handler and formatter
            fh = new FileHandler("./riftlogs/"+fileName);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();

            fh.setFormatter(formatter);



        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
