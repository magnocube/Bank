import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
/**
 * Created by stefa on 3/20/2017.
 */
public class Database {
    Connection myConn = null;
    Statement myStmt = null;
    ResultSet rs = null;
    public Database(String a, String b, String c){
        try {
            myConn = DriverManager.getConnection(a, b, c);
            myStmt = myConn.createStatement();
            //System.out.println("succesfully connected to database");
        } catch(Exception e){
            //System.out.println("database probleem");
            message="UNEXPECTED_ERROR";
        }

    }
    public String message="";
public boolean checkPass(String passNumber, String pincode)
{
    message="";
    passNumber="\""+passNumber+"\"";
    boolean r=false;
    try{


        rs = myStmt.executeQuery("select isblocked from pasjes where id =" + passNumber + ";");
        String value="";
        while(rs.next()) {

            value = rs.getString("isblocked");
            //System.out.println("blocked="+value);
        }
        if(value.equals("1"))
        {
            //System.out.println("passBlocked");
            message="TOO_MANY_INCORRECT";
            return false;
        }
        else if(value.equals(""))
        {
            //System.out.println("unknown pas");
            message="UNKNOWN_CARD";
            return false;
        }

        //System.out.println("    +select pincode from pasjes where id =" + passNumber + ";");
        rs = myStmt.executeQuery("select pincode from pasjes where id =" + passNumber + ";");
        String databasePin="";
        while(rs.next()) {
            databasePin = rs.getString("pincode");
           // System.out.println(databasePin);
           // System.out.println(pincode);
        }
        if(databasePin.equalsIgnoreCase(pincode))
        {
            //System.out.println("    >pin oke");
            r=true;
            myStmt.executeUpdate("update pasjes set wrongCounter = 0 where id =" + passNumber + ";");
        }
        else
        {
            //System.out.println(pincode);
            message="BAD_PIN";
            //System.out.println("pincode fout");
            myStmt.executeUpdate("update pasjes set wrongCounter = wrongCounter +1 where id =" + passNumber + ";");
            rs = myStmt.executeQuery("select wrongCounter from pasjes where id =" + passNumber + ";");
            String wrongCounter="";
            int wrongcounter=0;
            while(rs.next()) {

                wrongCounter = rs.getString("wrongCounter");
                //System.out.println(wrongCounter);
            }
            wrongcounter= Integer.parseInt(wrongCounter);
            if(wrongcounter+1>3)
            {
                //System.out.println("this pass is blocked now!");
                myStmt.executeUpdate("update pasjes set isblocked = 1 where id =" + passNumber + ";");
            }
            //System.out.println(databasePin);
        }

    } catch(Exception e) {
        //System.out.println("    +can't get data from database");
        e.printStackTrace();
        r=false;
    }
    return r;
}

    public String getSaldo(String pasId){
        String r = null;
        try{
        //System.out.println("    +select saldo from rekeningen where rekeningid in (select rekeningid from pasjes where id =" + "\""+pasId+"\"" + ");");
        rs = myStmt.executeQuery("select saldo from rekeningen where rekeningid in (select rekeningid from pasjes where id ="+"\""+pasId+"\""+");");
        while(rs.next()) {
            r = rs.getString("saldo");
        }

         } catch(Exception e) {
            //System.out.println("    +can't get data from database");
            e.printStackTrace();
            r = null;
        }
        return r;
    }

    public boolean withdraw(String pasId,int money) {
        message="";
        double saldo = Double.parseDouble(getSaldo(pasId));
        try
        {

            rs = myStmt.executeQuery("select maxOpnameIntern from rekeningen where rekeningid in (select rekeningid from pasjes where id ="+"\""+pasId+"\""+");");
            while(rs.next()) {

                String value = rs.getString("maxOpnameIntern");
                int maxOpname = Integer.parseInt(value);
                //System.out.println("max opname intern="+value);
                if(maxOpname>money)
                {

                }
                else
                {
                    //System.out.println("te grote opname");
                    message="WITHDRAW_LIMIT";
                    return false;
                }

            }
        if(saldo>=money)
        {
            //System.out.println("update rekeningen set saldo = saldo - " +money+" where rekeningid = (select rekeningid from pasjes where id =" + "\""+pasId+"\"" + ");");
            myStmt.executeUpdate("update rekeningen set saldo = saldo - " +money+" where rekeningid = (select rekeningid from pasjes where id =" + "\""+pasId+"\"" + ");");
        }
        else
        {
            message="NOT_ENOUGH_BALANCE";
            return false;
        }

        } catch(Exception e) {
            //System.out.println("    +can't get data from database");
            e.printStackTrace();
            message="UNEXPECTED_ERROR";
            return false;
        }
        message="your transaction succesed";
        return true;

    }

    public void archive(boolean b, String pasId, String bankNumber, String money, int i) {
        String date  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String rekeningId = "";
        try {
            //System.out.println("select rekeningid from pasjes where id =" + "\"" + pasId + "\"" + ";");
            rs = myStmt.executeQuery("select rekeningid from pasjes where id =" + "\"" + pasId + "\"" + ";");
            while (rs.next()) {
                rekeningId = rs.getString("rekeningid");
            }
            System.out.println("insert into transacties(datum,afgeschreven,rekeningID,pasID,banknaam,automaatNR,status) values ('" + date + "'," + money + "," + rekeningId + "," + "\"" + pasId + "\"" + ","+bankNumber+",1,\"confirm sended\");");
            myStmt.executeUpdate("insert into transacties(datum,afgeschreven,rekeningID,pasID,banknaam,automaatNR,status) values ('" + date + "'," + money + "," + rekeningId + "," + "\"" + pasId + "\"" + ","+bankNumber+",1,\"confirm sended\");");
        } catch (Exception e) {
            //System.out.println("    +can't get data from database");
            e.printStackTrace();
            return;
        }
    }
}
