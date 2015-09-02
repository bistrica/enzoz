package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBHandler;
import people.Lekarz;
import people.Pracownik;
import exceptions.BadDataException;
import exceptions.ConnectionException;

public class LoginDBH {

	Connection conn = null;
	//String login;
	//DBHandler dbh;
	
	public Pracownik tryToLog(String login, String pass) throws ConnectionException, BadDataException {
		
		//this.login=login;
		Pracownik user=null;
		
		try {
	      
	        //dbh=new DBHandler(login, pass);
	        conn=DBHandler.createAndGetDatabaseConnection(login, pass);
			user=DBHandler.getCurrentUser();
	        
	        
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			if (ex.getErrorCode()==1045)
				throw new BadDataException();
			else
				throw new ConnectionException();
			
		}
		
		return user;	
	}

	
	

}
