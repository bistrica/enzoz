package daos;

import items.Lek;
import items.Poradnia;
import items.PozycjaNaRecepcie;
import items.Skierowanie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class SkierowanieDAO {

	Connection conn;
	PoradniaDAO clinicsDAO;
	
	public SkierowanieDAO(){
		conn=DBHandler.getDatabaseConnection();
		clinicsDAO=new PoradniaDAO();
	}

	public ArrayList<Skierowanie> getExaminations(int appId) throws SQLException {
		ArrayList<Skierowanie> examinations=new ArrayList<Skierowanie>();
		
		//if (true) throw new SQLException();
		
		
		PreparedStatement st;
		String queryString="SELECT idSkierowania FROM skierowania WHERE idWizyty = ? ORDER BY data DESC LIMIT 1";
		int idExam=-1;
		
		//try {
			st = conn.prepareStatement(queryString);
			st.setInt(1, appId);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()){
				idExam=rs.getInt("idSkierowania");
				break;
			}
			
			queryString="SELECT idPoradni, komentarz FROM pozycjeNaSkierowaniach WHERE idSkierowania = ?";
			
			st = conn.prepareStatement(queryString);
			st.setInt(1, idExam);
			
			rs = st.executeQuery();
			
			while (rs.next()){
				Poradnia clinic=clinicsDAO.getClinic(rs.getInt("idPoradni"));
				Skierowanie examination=new Skierowanie(clinic, rs.getString("komentarz"));
				System.out.println("::>"+examination.getOpisBadan());
				examinations.add(examination);
			}
			
		/*}
		catch (SQLException e) {
			e.printStackTrace();
		}*/
		
		
		return examinations;
	}
	
	
}
