package daos;

import items.Konsultacja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBHandler;

public class KonsultacjaDAO {

	Connection conn;
	
	public KonsultacjaDAO(){
		conn=DBHandler.getDatabaseConnection();
	}
	
	public Konsultacja getInterview(int appId) {
		
		PreparedStatement st;
		String queryString="SELECT treœæ FROM konsultacje WHERE idWizyty = ? ORDER BY data DESC LIMIT 1";
		String interview="";
		try {
			
			st = conn.prepareStatement(queryString);
			st.setInt(appId, 1);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				interview=rs.getString("treœæ");
				break;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return new Konsultacja(interview);
	}
	
}
