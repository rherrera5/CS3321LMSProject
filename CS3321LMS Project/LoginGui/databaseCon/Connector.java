package databaseCon;

import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;



public class Connector {
	
	
    private static String url = "jdbc:sqlserver://localhost:1433;" + 
		"databaseName = LMS;integratedSecurity=true;";    
    private static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";   
    private static Connection con;
    Statement stmt = null;
    ResultSet rs = null;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url);
            } catch (SQLException ex) {
                // log an exception
                System.out.println("Failed to create the database connection."); 
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found."); 
        }
        return con;
    }
    
    public boolean verifyCreds(String user, String pass) {
    	try {
    		String SQLPass = "SELECT pass FROM LMS.dbo.People WHERE username = " + "'" + user + "'";
    		stmt = con.createStatement();
    		rs = stmt.executeQuery(SQLPass);
    		while(rs.next()) {
    			//if passed password equals db password for passed username
    			if(rs.getString(1).equals(pass)) {
    				return true;
    				/*String SQL = "SELECT * FROM LMS.dbo.People WHERE username = " + "'" + user + "'";
    				stmt = con.createStatement();
    				rs = stmt.executeQuery(SQL);
    				while (rs.next()) {
    					System.out.println(rs.getString("username") + ", " + rs.getString("name") + ", " + rs.getString("job"));
    				}*/	
    			}
    			else {
    				System.out.println("Password is incorrect.");
    				return false;
    			}
    		}
    		return false;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    public String[] userInfo(String user){
    	String[] result = new String[13];
    	try {
	    	String SQLPass = "SELECT username, name, job, courseID, exam1, exam2, exam3, "
	    			+ "exam4, exam5, exam6 FROM LMS.dbo.People WHERE username = " + "'" + user + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQLPass);
			while(rs.next()) {
				result[0] = rs.getString(1);
				result[1] = rs.getString(2);
				result[2] = rs.getString(3);
				result[3] = rs.getString(4);
				result[4] = rs.getString(5);
				result[5] = rs.getString(6);
				result[6] = rs.getString(7);
				result[7] = rs.getString(8);
				result[8] = rs.getString(9);
				result[9] = rs.getString(10);
			}
			return result;
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    		return result;
    	}

    }
    
    public String getJob(String user) {
    	try {
	    	String SQLPass = "SELECT job FROM LMS.dbo.People WHERE username = " + "'" + user + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQLPass);
			while(rs.next()) {
				//job = rs.getString(1);
				return rs.getString(1);
			}
			return "";
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    		return "";
    	}  	
    }
    public void insertStudent(String username, String name, String password, String course) {
    	try {
	    	String SQL = "INSERT INTO LMS.dbo.People (username, name, pass, job, courseID)"
	    			+ "VALUES ('" + username + "', '" + name + "', '" + password + "', 'student', '" + course + "');";
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}  	
    
    }
/*  //Sandbox for testing
    public static void main (String[] args) {
	
    	Connector c = new Connector();
    	c.getConnection();
    	if(c.verifyCreds("wilcoxj", "pass123")) {
    		String[] result = c.userInfo("wilcoxj");
    		System.out.println(Arrays.toString(result));
    		System.out.println(c.getJob("wilcoxj"));
    	}
    	c.insertStudent("johnsond", "Don Johnson", "pass789", "001");
    }*/
}
