package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBHandler;
import items.Choroba;
import items.Wizyta;

public class WizytaDAO {

	private static Connection conn;
	
	public static Wizyta getAppointmentData(int id){
		
		conn=DBHandler.getDatabaseConnection();

//		Wizyta app=new Wizyta();
		return null;
	}
	
	public static boolean writeAppointmentData(Wizyta app){
		return false;
	}

	public static int getId(Wizyta app) {
		int patientId=app.getPacjent().getId();
		int doctorId=app.getLekarz().getId();
		
		int appId=-1;
		PreparedStatement st;
		//SELECT idWizyty, data FROM wizytyDzis WHERE idPacjenta = 299 AND idLekarza = 303 ORDER BY data desc LIMIT 1 
		String queryString="SELECT idWizyty FROM wizytyDzis WHERE idPacjenta = ? AND idLekarza = ?";//"SELECT idTypu FROM pracownicy WHERE login = ?";
		
		try {
			st = conn.prepareStatement(queryString);
		
			st.setInt(1, patientId);
			st.setInt(2, doctorId);
			ResultSet rs = st.executeQuery();//Update();
			
			while (rs.next()){
				appId=rs.getInt("idWizyty");
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace(); //TODO: ?
		}
		return appId;
		
		
		
	}
	
}
