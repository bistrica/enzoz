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
	
	public Konsultacja getInterview(int appId) throws SQLException {
		
		PreparedStatement st;
		String queryString="SELECT treœæ FROM konsultacje WHERE idWizyty = ? ORDER BY data DESC LIMIT 1";
		String interview="";
		//try {
			
			st = conn.prepareStatement(queryString);
			st.setInt(1,appId);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				interview=rs.getString("treœæ");
				break;
			}
		/*}
		catch (SQLException e) {
			e.printStackTrace();
		}*/
		/*System.out.println("COunter:  "+DBHandler.counter);	
		if (DBHandler.counter++%4!=0) throw new SQLException();
		System.out.println("Classic return");*/
		return new Konsultacja(interview);
	}
	
}
