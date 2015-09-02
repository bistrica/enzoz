package appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;

import items.Wizyta;
import people.Lekarz;
import daos.WizytaDAO;
import database.DBHandler;

public class AppointmentDBH {

	Connection conn=null;
	
	public AppointmentDBH() {
		conn=DBHandler.getDatabaseConnection();
	}



	public ArrayList<Wizyta> getTodayAppointments(Lekarz doctor) {
		
		ArrayList<Wizyta> apps=new ArrayList<Wizyta>();
		PreparedStatement st;
		String queryString="SELECT idWizyty FROM wizytyDzis WHERE Lekarz=?";	
		
		try {
			st = conn.prepareStatement(queryString);
			st.setInt(1, doctor.getId());
			ResultSet rs = st.executeQuery();//Update();
			while (rs.next()){
				int appId=rs.getInt("idWizyty");
				//int patientId=rs.getInt("idPacjenta");
				Wizyta app=WizytaDAO.getAppointmentData(appId);
				apps.add(app);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
	
	
}
