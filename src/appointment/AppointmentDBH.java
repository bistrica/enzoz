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
			
			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()){
				DBHandler.resetTrialsNo();
				throw new TodayException(); 
			}
			else{
				System.out.println("ONCE MORE");
				DBHandler.incrementTrialsNo();
				apps=getTodayAppointments(doctor);
			}
		}
		DBHandler.resetTrialsNo();
		return apps;
	}
	
	public ArrayList<Wizyta> getArchiveAppointments() throws ArchiveException {

		ArrayList<Wizyta> apps=new ArrayList<Wizyta>();
		try {
			apps=appDAO.getArchiveAppointments();
		}
		catch (SQLException e) {
			e.printStackTrace();
			
			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()){
				DBHandler.resetTrialsNo();
				throw new ArchiveException(); 
			}
			else{
				System.out.println("ONCE MORE");
				DBHandler.incrementTrialsNo();
				apps=getArchiveAppointments();
			}
			//throw new ArchiveException(); 
		}
		DBHandler.resetTrialsNo();
		return apps;
	}


	public boolean openPreviewIfPossible(Wizyta app) throws PreviewCannotBeCreatedException {
		boolean isPossible=false;
		try {
			isPossible=appDAO.checkAndChangeStatus(app);
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()){
				DBHandler.resetTrialsNo();
				throw new PreviewCannotBeCreatedException(); 
			}
			else{
				System.out.println("ONCE MORE");
				DBHandler.incrementTrialsNo();
				isPossible=openPreviewIfPossible(app);
			}
			
			
			
		}
		DBHandler.resetTrialsNo();
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
