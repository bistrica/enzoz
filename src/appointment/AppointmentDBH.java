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
	
	public ArrayList<Wizyta> getArchiveAppointments() {
		
		ArrayList<Wizyta> apps=appDAO.getArchiveAppointments();

		return apps;
	}



	public boolean statusAllowsEditing(Wizyta app) {
		return appDAO.checkAndChangeStatus(app);
	}



	public void updateAppData(Wizyta app) {
		// TODO Auto-generated method stub
		appDAO.updateData(app);
	}
	
	
}
