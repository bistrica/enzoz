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
	private int trialsNo=0;
	private int criticalNo=5;
	
	
	
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
			
			if (!DBHandler.reconnect() || trialsNo==criticalNo){
				trialsNo=0;
				throw new TodayException(); 
			}
			else{
				System.out.println("ONCE MORE");
				trialsNo++;
				apps=getTodayAppointments(doctor);
			}
		}
		trialsNo=0;
		return apps;
	}
	
	public ArrayList<Wizyta> getArchiveAppointments() throws ArchiveException {

		ArrayList<Wizyta> apps=new ArrayList<Wizyta>();
		try {
			apps=appDAO.getArchiveAppointments();
		}
		catch (SQLException e) {
			e.printStackTrace();
			
			if (!DBHandler.reconnect() || trialsNo==criticalNo){
				trialsNo=0;
				throw new ArchiveException(); 
			}
			else{
				System.out.println("ONCE MORE");
				trialsNo++;
				apps=getArchiveAppointments();
			}
			//throw new ArchiveException(); 
		}
		trialsNo=0;
		return apps;
	}


	public boolean openPreviewIfPossible(Wizyta app) throws PreviewCannotBeCreatedException {
		boolean isPossible=false;
		try {
			isPossible=appDAO.checkAndChangeStatus(app);
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			if (!DBHandler.reconnect() || trialsNo==criticalNo){
				trialsNo=0;
				throw new PreviewCannotBeCreatedException(); 
			}
			else{
				System.out.println("ONCE MORE");
				trialsNo++;
				isPossible=openPreviewIfPossible(app);
			}
			
			
			
		}
		trialsNo=0;
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
