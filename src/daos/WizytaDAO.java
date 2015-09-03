package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import people.Lekarz;
import people.Pacjent;
import database.DBHandler;
import items.Choroba;
import items.Wizyta;

public class WizytaDAO {

	private static Connection conn;
	private PacjentDAO patientDAO;
	
	public WizytaDAO() {
		patientDAO=new PacjentDAO();
		conn=DBHandler.getDatabaseConnection();
	}
	
	
	//private ArrayList<Wizyta> getAppointments(Lekarz doct)
	
	public ArrayList<Wizyta> getTodayAppointments(Lekarz doctor) {
		ArrayList<Wizyta> apps=new ArrayList<Wizyta>();
		PreparedStatement st;
		String queryString="SELECT idWizyty, idPacjenta, data FROM wizytyDzis WHERE idLekarza=?";	
		
		try {
			System.out.println("c "+conn);
			st = conn.prepareStatement(queryString);
			st.setInt(1, doctor.getId());
			ResultSet rs = st.executeQuery();//Update();
			while (rs.next()){
				int appId=rs.getInt("idWizyty");
				Pacjent patient=patientDAO.getPatientData(rs.getInt(2));
				GregorianCalendar appDate=convertToDate(rs.getString(3));
				Wizyta app=new Wizyta(appId, appDate);
				app.setLekarz(doctor);
				app.setPacjent(patient);
				apps.add(app);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return apps;
	}
	
	
	private GregorianCalendar convertToDate(String dateString) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date=null;
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GregorianCalendar appDate = new GregorianCalendar();
		appDate.setTime(date);
		return appDate;
	}


	/*private Wizyta getAppointmentData(int id){
		
		conn=DBHandler.getDatabaseConnection();
		
		Wizyta app=new Wizyta(id,appDate, );
		return null;
	}*/
	
	private boolean writeAppointmentData(Wizyta app){
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
