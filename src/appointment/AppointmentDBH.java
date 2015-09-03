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
	WizytaDAO appDAO;
	
	
	
	public AppointmentDBH() {
		conn=DBHandler.getDatabaseConnection();
		appDAO=new WizytaDAO();
		
	}



	public ArrayList<Wizyta> getTodayAppointments(Lekarz doctor) {
		
		ArrayList<Wizyta> apps=appDAO.getTodayAppointments(doctor);

		return apps;
	}
	
	
}
