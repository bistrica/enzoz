package daos;

import items.PozycjaNaRecepcie;
import items.Recepta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class ReceptaDAO {

	Connection conn;
	private PozycjaNaRecepcieDAO prescriptionPositionDAO;
	
	
	public ReceptaDAO(){
		conn=DBHandler.getDatabaseConnection();
		prescriptionPositionDAO=new PozycjaNaRecepcieDAO();
	}
	
	public Recepta getPrescription (int appId) {
		
		PreparedStatement st;
		String queryString="SELECT idRecepty FROM recepty WHERE idWizyty = ? ORDER BY data DESC LIMIT 1";
		int idPresc=-1;
		ArrayList<PozycjaNaRecepcie> positions=new ArrayList<PozycjaNaRecepcie>();
		
		try {
			
			st = conn.prepareStatement(queryString);
			st.setInt(appId, 1);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				idPresc=rs.getInt(1);
				break;
			}
			
			positions=prescriptionPositionDAO.getPrescriptedPositions(idPresc);
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
				
				
		return new Recepta(positions);
	}
	
}
