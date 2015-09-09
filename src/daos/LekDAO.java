package daos;

import items.Lek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class LekDAO {

	Connection conn;
	
	public ArrayList<Lek> getAllMedicines() throws SQLException {

		conn=DBHandler.getDatabaseConnection();
		
		PreparedStatement st;
		String queryString="SELECT idLeku, nazwa, postaæ, dawka, opakowanie FROM leki";
		ArrayList<Lek> medicines=new ArrayList<Lek>();	
		st = conn.prepareStatement(queryString);
		
		ResultSet rs = st.executeQuery();
		String type="", name="", dose="", pckg="";
		int id=-1;
		Lek drug=null;
		
		while (rs.next()){
			id=rs.getInt("idLeku");
			name=rs.getString("nazwa");
			type=rs.getString("postaæ");
			dose=rs.getString("dawka");
			pckg=rs.getString("opakowanie");
			
			drug=new Lek(id,name,type,dose,pckg);
			medicines.add(drug);
		}
		
		return medicines;
		
	}

}
