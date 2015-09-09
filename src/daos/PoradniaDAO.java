package daos;

import items.Lek;
import items.Poradnia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class PoradniaDAO {

	Connection conn;
	
	public ArrayList<Poradnia> getAllClinics() throws SQLException {
		
		conn=DBHandler.getDatabaseConnection();
		
		PreparedStatement st;
		String queryString="SELECT idPoradni, nazwa FROM poradnie";
		ArrayList<Poradnia> clinics=new ArrayList<Poradnia>();	
		st = conn.prepareStatement(queryString);
		
		ResultSet rs = st.executeQuery();
		String name="";
		int id=-1;
		Poradnia clinic=null;
		
		while (rs.next()){
			id=rs.getInt("idPoradni");
			name=rs.getString("nazwa");
			
			
			clinic=new Poradnia(id,name);
			clinics.add(clinic);
		}
		
		return clinics;
		
	}

}
