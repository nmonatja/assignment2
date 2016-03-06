
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 *
 * This class performs authentication and event logging.
 * It sets parameters related to the Session: if session is logged on,
 * authorization level, username associated with the session, etc.
 */
public class Session {
        //define session variables. 
	private Boolean loggedOn; //whether a session has successfully logged on or not
        private String usrname;
        private Connection DBConn;       // MySQL connection handle
	private PasswordEncryptionService PES = new PasswordEncryptionService();
	private String authorization_level;
        
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
        String logEvent = null;
        java.sql.Statement s = null;    // SQL statement pointer
        String usr = null;              //stores username
        byte[] salt = null;             //password salt
        byte[] encrypted_pwd = null;    //encrypted password
        
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
                System.out.println ("Attemting to log on... username: " + username);
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
                //users and encrypted passwords are stored in the 'SecuredUsers' table
                String SQLStatement = "Select username,password,salt "
                        + "from SecuredUsers where username = \"" + username 
                        + "\";";
                res = s.executeQuery(SQLStatement);
                
                while (res.next())
                {
                    usr = res.getString(1);
                    encrypted_pwd = res.getBytes(2);
                    salt = res.getBytes(3);
                } // while
                //get the authorization level associated with the user
                SQLStatement = "Select authorization_level "
                        + "from UserAuthorization where username = \"" + username 
                        + "\";";
                res = s.executeQuery(SQLStatement);
                
                while (res.next())
                {
                    authorization_level = res.getString(1);
                } // while
                
                //user found, logon the session and log the event
                if (usr.equals(username))
                {
                    //check if password credentials are correct
                    if (PES.authenticate(password, encrypted_pwd, salt))
                    {
                        loggedOn = true;
                        logEvent = "logon";
                    }
                    else //authentication failed
                    {
                        
                        logEvent = "Failed Logon";
                    }
                         
                }
                usrname = username; //maintain username associated with the
                                            //logged on session
                logEvent(logEvent);//log the event
                
            } catch (Exception e) {

                return false;

            } // end try-catch
        }
            return loggedOn;
        }
        
        //returns whether a Session is logged on or not
        //this can be used for checking if user access has been obtained
        public boolean isLoggedOn()
        {
            return loggedOn;
        }
        
        //returns authorization level associated with a user
        //this can be used for checking if user has proper level of authorization
        public String getAuthorizationLevel()
        {
            return authorization_level;
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
        private void logEvent (String event)//event can be logoff, logon, logon failed, etc.
        {
            String msgString = null;        // String for displaying non-error messages
            ResultSet res = null;           // SQL query result set pointer
            java.sql.Statement s = null;    // SQL statement pointer
            
            try
            {
                s = DBConn.createStatement();
                //insert log information into a MySQL table
                String SQLStatement = "insert into LogonOff (username,logtimestamp,event) "
                        + "values (\"" + usrname + "\",current_timestamp,\"" + event + "\");";
                s.executeUpdate(SQLStatement);
                
            } catch (Exception e) {
                String errString =  "\nProblem with query:: " + e;
                System.out.println (errString);

            } // end try-catch
        }
}
