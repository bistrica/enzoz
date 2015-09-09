package daos;

import java.sql.Connection;

import database.DBHandler;

public class SkierowanieDAO {

	Connection conn;
	
	public SkierowanieDAO(){
		conn=DBHandler.getDatabaseConnection();
	}
	
	
}
