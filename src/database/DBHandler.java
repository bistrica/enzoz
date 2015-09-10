package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import daos.PracownikDAO;
import people.Lekarz;
import people.Pracownik;

public class DBHandler {

	private Pracownik currentUser;
	private Connection conn;
	private String host="192.168.56.1", login;
	private static DBHandler dbh=null;
	
	/*public DBHandler(Connection conn) {
		this.conn=conn;
	}*/

	public static Connection createAndGetDatabaseConnection(String login, String pass)  throws SQLException {
		if (dbh==null) {
			dbh=new DBHandler(login,pass);
		}
		return dbh.conn;	
	}
	
	public static Connection getDatabaseConnection() {
		return dbh.conn;
	}
	
	public static String getLogin() {
		return dbh.login;
	}
	
	private DBHandler(String login, String pass) throws SQLException {

		  this.conn=DriverManager.getConnection(
		            "jdbc:mysql://"+host+"/enzoz?useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true", login, pass);
		  this.login=login;
	}
	
	
	public static Pracownik getCurrentUser() throws SQLException {
		
		PracownikDAO employeeDAO=new PracownikDAO();
		if (dbh.currentUser==null)
			dbh.currentUser=employeeDAO.getEmployeeData(dbh.login);
		
		
		return dbh.currentUser;
	}

	
	

}
