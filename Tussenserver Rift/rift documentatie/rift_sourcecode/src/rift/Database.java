package rift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Database {
	 Connection myConn = null;
	    Statement myStmt = null;
	    ResultSet rs = null;
	    public Database(String a, String b, String c){
	        try {
	            myConn = DriverManager.getConnection(a, b, c);
	            myStmt = myConn.createStatement();
	            System.out.println("succesfully connected to database");
	        } catch(Exception e){
	        	e.printStackTrace();
	            System.out.println("database probleem");}

	    }
		public String getKey(String ipadress) throws Exception {
			String r = null;
			try{
				
		        //System.out.println("select aeskey from banks where ipadress="+"\""+ipadress+"\""+";");
		        rs = myStmt.executeQuery("select aeskey from banks where ipadress="+"\""+ipadress+"\""+";");
		        while(rs.next()) {
		            r = rs.getString("aeskey");
		        }
		         } catch(Exception e) {
		            System.out.println("    +can't get data from database");
		            e.printStackTrace();
		            r = null;
		            throw new Exception();
		        }
			return r;
		}
		public String getKeyFromBanknumber(String bankNumber) throws Exception {
			String r = null;
			try{
				
		        //System.out.println("select aeskey from banks where ipadress="+"\""+ipadress+"\""+";");
		        rs = myStmt.executeQuery("select aeskey from banks where banknumber="+"\""+bankNumber+"\""+";");
		        while(rs.next()) {
		            r = rs.getString("aeskey");
		        }
		         } catch(Exception e) {
		            System.out.println("    +can't get data from database");
		            e.printStackTrace();
		            r = null;
		            throw new Exception();
		        }
			return r;
		}
		public int archive(String pasId, String bankFrom, String destinationbank ,String money,String atmnr) throws Exception {
	        String date  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	        int id = 0;
	        try {
	   
	        	
	            //System.out.println("insert into transactions(date,pasID,bankFrom,bankTo,amount,atmNr,status) values ('" + date +  "\'" +"," +  "\"" +pasId + "\"" +"," + "\""  + bankFrom + "\",\""+ destinationbank +"\",\"" + money +"\",\""  + atmnr +"\",\"" + "send to bank" +"\");");
	            myStmt.executeUpdate("insert into transactions(date,pasID,bankFrom,bankTo,amount,atmNr,status) values ('" + date +  "\'" +"," +  "\"" +pasId + "\"" +"," + "\""  + bankFrom + "\",\""+ destinationbank +"\",\"" + money +"\",\""  + atmnr +"\",\"" + "send to bank" +"\");");
	            rs = myStmt.executeQuery("select max(ID) from transactions;");
		        while(rs.next()) {
		            id = rs.getInt("max(id)");
		        }
	        } catch (Exception e) {
	            System.out.println("    +can't get data from database");
	            e.printStackTrace();
	            throw new Exception();
	        }
			return id;
	    }
		public String getIp(String bankTo) throws Exception {
			String r = null;
			try{
				
		        System.out.println("select ipadress from banks where bankNumber="+"\""+bankTo+"\""+";");
		        rs = myStmt.executeQuery("select ipadress from banks where bankNumber="+"\""+bankTo+"\""+";");
		        while(rs.next()) {
		            r = rs.getString("ipadress");
		        }
		         } catch(Exception e) {
		            System.out.println("    +can't get data from database");
		            e.printStackTrace();
		            r = null;
		            throw new Exception();
		        }
			return r;
		}
		public String getPort(String bankTo) throws Exception {
			String r = null;
			try{
				
		        //System.out.println("select serverport from banks where bankNumber="+"\""+bankTo+"\""+";");
		        rs = myStmt.executeQuery("select serverport from banks where bankNumber="+"\""+bankTo+"\""+";");
		        while(rs.next()) {
		            r = rs.getString("serverport");
		        }
		         } catch(Exception e) {
		            System.out.println("    +can't get data from database");
		            e.printStackTrace();
		            r = null;
		            throw new Exception();
		        }
			return r;
		}
		public void setStatus(int databaseID, String status) {
			try {
				myStmt.executeUpdate("update transactions set status=\""+status+"\"where ID="+databaseID+";");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
