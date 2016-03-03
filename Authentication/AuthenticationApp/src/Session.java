
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * This class performs authentication and event logging
 */
public class Session {
        //define session variables. 
	private Boolean loggedOn; //whether a session has successfully logged on or not
        private String usrname;
        private Connection DBConn;       // MySQL connection handle
	
	
	//default constructor
	public Session ()
	{
            loggedOn = false; //whether a session has successfully logged on or not
            usrname = null;
            DBConn = null;
	}
        
        //check for valid username and password and logon user if
        //authentication is successful
        public boolean Logon (String username, String password)
        {
        
        String msgString = null;        // String for displaying non-error messages
        ResultSet res = null;           // SQL query result set pointer
        String SQLServerIP;
        java.sql.Statement s = null;    // SQL statement pointer
       
        
        if (loggedOn)//session is already logged on
        {
            return true;
        }
        
        // perform simple logon verification check
        if ( username == null || password == null)
        {
            return false;
            
        } else {
        //Now, we try to connect to the security database.
            try
            {
                //load JDBC driver class for MySQL
                Class.forName( "com.mysql.jdbc.Driver" );
                //hardcoded localhost for now
                SQLServerIP = "localhost";
                String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/security";
                System.out.println ("Attemting to log on... username: " + username 
              + " password: " + password);
                //create a connection to the db
                DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
                   
            } catch (Exception e) {
                //Problem connecting to database
                return false;

            } // end try-catch

            try
            {
                s = DBConn.createStatement();
                //check if the user and password are correct
                //users and passwords are stored in the 'users' table
                String SQLStatement = "Select username from users where username = \"" + username 
                        + "\" and password = \"" + password + "\";";
                res = s.executeQuery(SQLStatement);
                
                while (res.next())
                {
                    msgString = res.getString(1);
                    System.err.println (msgString);
                } // while
                //user found, logon the session and log the event
                if (msgString.equals(username))
                {
                    loggedOn = true;
                    usrname = username; //maintain username associated with the
                                        //logged on session
                    logEvent("logon");
                }
                
            } catch (Exception e) {

                return false;

            } // end try-catch
        }
            return loggedOn;
        }
        
        public boolean isLoggedOn()
        {
            return loggedOn;
        }
        
        //return true is logoff operation successful, false is logoff failed
        //log the event
        public boolean Logoff ()
        {
            if (loggedOn)
            {
                loggedOn = false;
                logEvent("logoff");
                return true;
            }
            else//already logged off
            {
                return false;
            }
        }
        
        //performs event logging. The log is stored in 'LogonOff' table
        private void logEvent (String event)//event can be either logoff or logon
        {
            String msgString = null;        // String for displaying non-error messages
            ResultSet res = null;           // SQL query result set pointer
            java.sql.Statement s = null;    // SQL statement pointer
            
            try
            {
                s = DBConn.createStatement();
                
                String SQLStatement = "insert into LogonOff (username,logtimestamp,event) "
                        + "values (\"" + usrname + "\",current_timestamp,\"" + event + "\");";
               // s.executeQuery(SQLStatement); 
                s.executeUpdate(SQLStatement);
                
            } catch (Exception e) {
                String errString =  "\nProblem with query:: " + e;
                System.out.println (errString);

            } // end try-catch
        }
}
