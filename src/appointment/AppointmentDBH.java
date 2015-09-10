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
import exceptions.ArchiveException;
import exceptions.PreviewCannotBeCreatedException;
import exceptions.TodayException;

public class AppointmentDBH {

	Connection conn=null;
	WizytaDAO appDAO;
	
	
	
	public AppointmentDBH() {
		conn=DBHandler.getDatabaseConnection();
		appDAO=new WizytaDAO();
		
	}



	public ArrayList<Wizyta> getTodayAppointments(Lekarz doctor) throws TodayException {
		
		ArrayList<Wizyta> apps=new ArrayList<Wizyta>();
		
		try {
			apps=appDAO.getTodayAppointments(doctor);
		}
		catch (SQLException e) {
			e.printStackTrace();
			/*if (!DBHandler.reconnect())
				throw new TodayException(); 
			else{
				System.out.println("ONCE MORE");
				getTodayAppointments(doctor);
			}*/
		}
		
		return apps;
	}
	
	public ArrayList<Wizyta> getArchiveAppointments() throws ArchiveException {

		ArrayList<Wizyta> apps=new ArrayList<Wizyta>();
		try {
			apps=appDAO.getArchiveAppointments();
		}
		catch (SQLException e) {
			throw new ArchiveException(); 
		}
		
		return apps;
	}


	public boolean openPreviewIfPossible(Wizyta app) throws PreviewCannotBeCreatedException {
		boolean isPossible=false;
		try {
			isPossible=appDAO.checkAndChangeStatus(app);
		} catch (SQLException e) {
			/*if (!DBHandler.reconnect())
				throw new PreviewCannotBeCreatedException(); 
			else{
				System.out.println("ONCE MORE");
				openPreviewIfPossible(app);
			}*/
			
			e.printStackTrace();
			
		}
		return isPossible;
	}

	/*public boolean statusAllowsEditing(Wizyta app) throws SQLException {
		return appDAO.checkAndChangeStatus(app);
	}*/



	/*public void updateAppData(Wizyta app) throws SQLException {
		// TODO Auto-generated method stub
		appDAO.updateData(app);
	}*/
	
	
}
