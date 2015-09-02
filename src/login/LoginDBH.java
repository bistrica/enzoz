package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import database.DBHandler;
import people.Pracownik;
import exceptions.BadDataException;
import exceptions.ConnectionException;

public class LoginDBH {

	//Connection conn = null;
	DBHandler dbh;
	
	public Pracownik tryToLog(String login, String pass) throws ConnectionException, BadDataException {
		
		Pracownik user=null;
		
		try {
	      
	        //dbh=new DBHandler(login, pass);
	        dbh=DBHandler.createAndGetDatabaseHandler(login, pass);
			user=dbh.getCurrentUser();
	        
	        
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
