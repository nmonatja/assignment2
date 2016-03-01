import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//import com.mysql.jdbc.Driver;


public class DbItem {
	
	/* this provides the db methods */
	
	public Connection openDbConnection(String dbname) {
		
		String SQLServerIP = "localhost";
		
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
			String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/" + dbname;
			Connection DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
		
			return DBConn;
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		return null;
	}
	
	public void closeDbConnection(Connection DBConn) {
		try {
			DBConn.close();
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
	}
	
	public int run(Connection DBConn, String SQLstatement) {
		try {
			Statement s = DBConn.createStatement();
			int executeUpdateVal = s.executeUpdate(SQLstatement);
			return executeUpdateVal;
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		return 0;
	}
	
	public ResultSet runResult(Connection DBConn, String SQLstatement) {
		try {
			Statement s = DBConn.createStatement();
			ResultSet res = s.executeQuery(SQLstatement);
			return res;

		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		return null;
	}
}