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
import items.Konsultacja;
import items.Recepta;
import items.Skierowanie;
import items.Wizyta;

public class WizytaDAO {

	private static Connection conn;
	private PacjentDAO patientDAO;	
	private OsobaDAO personDAO;
	private KonsultacjaDAO interviewDAO;
	private ReceptaDAO prescriptionDAO;
	private SkierowanieDAO examinationDAO;
	private ChorobaDAO illnessDAO;
	
	
	public WizytaDAO() {
		patientDAO=new PacjentDAO();
		personDAO=new OsobaDAO();
		interviewDAO=new KonsultacjaDAO();
		prescriptionDAO=new ReceptaDAO();
		examinationDAO=new SkierowanieDAO();
		illnessDAO=new ChorobaDAO();
		conn=DBHandler.getDatabaseConnection();
	}
	
	
	//private ArrayList<Wizyta> getAppointments(Lekarz doct)
	
	public ArrayList<Wizyta> getTodayAppointments(Lekarz doctor)  throws SQLException {
		ArrayList<Wizyta> apps=new ArrayList<Wizyta>();
		PreparedStatement st;
		String queryString="SELECT idWizyty, idPacjenta, data FROM wizytyDzis WHERE idLekarza=?";	
		
		//try {
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
			
		/*} catch (SQLException e) {
			e.printStackTrace();
		}*/
	
		return apps;
	}
	
	
	private GregorianCalendar convertToDate(String dateString) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=null;
		GregorianCalendar appDate = new GregorianCalendar();
		try {
			date = format.parse(dateString);
			appDate.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
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


	public boolean checkAndChangeStatus(Wizyta app)  throws SQLException {

		//conn=DBHandler.getDatabaseConnection();
	//	conn.setAutoCommit(false);
		
		int appId=app.getId();
		PreparedStatement st;
		//SELECT idWizyty, data FROM wizytyDzis WHERE idPacjenta = 299 AND idLekarza = 303 ORDER BY data desc LIMIT 1 
		String status="realizowana";
		//System.out.println(">"+appId);
		String queryString="UPDATE wizyty SET status = ? WHERE idWizyty = ? AND status != ? ";//"SELECT idWizyty FROM wizytyDzis WHERE idPacjenta = ? AND idLekarza = ?";//"SELECT idTypu FROM pracownicy WHERE login = ?";
		int updatedRecords=-1;
		//try {
			st = conn.prepareStatement(queryString);
			//st.setInt(1, 1);
			st.setString(1, status);
			st.setInt(2, appId);
			st.setString(3, status);
			updatedRecords = st.executeUpdate();
			System.out.println(updatedRecords);
		/*} catch (SQLException e) {
			e.printStackTrace(); //TODO: ?
			
			System.out.println(">>>>"+DBHandler.getDatabaseConnection());
		}*/
		
			if (updatedRecords > 0) {
				/*try {
					updateData(app);
		//			conn.commit();
				}
				catch(SQLException e){
					System.out.println("rollback");
					//e.printStackTrace();
		//			conn.rollback();
		//			conn.setAutoCommit(true);
					throw e;
				}*/
				
			}
		//conn.setAutoCommit(true);
		return updatedRecords>0;
	}


	public void updateData(Wizyta app) throws SQLException {
		int appId=app.getId();
		
		Pacjent patient=patientDAO.getPatientData(app.getPacjent().getId());
		app.setPacjent(patient);
		
		Konsultacja interview=interviewDAO.getInterview(appId);
		app.setKonsultacja(interview);
		
		Recepta prescription=prescriptionDAO.getPrescription(appId);
		app.setRecepta(prescription);
		
		ArrayList<Skierowanie> examinations=examinationDAO.getExaminations(appId);
		app.setSkierowania(examinations);
		
		ArrayList<Choroba> tempIllnesses=illnessDAO.getTemporaryIllnesses(appId, app.getPacjent().getChorobyPrzewlek³e());
		app.setRozpoznaneChoroby(tempIllnesses);

		
	}


	public ArrayList<Wizyta> getArchiveAppointments() throws SQLException {

		ArrayList<Wizyta> apps=new ArrayList<Wizyta>();
		PreparedStatement st;
		String queryString="SELECT idWizyty, idPacjenta, idLekarza, data FROM wizytyArchiwum ORDER BY data DESC";	
		
		//try {
			
			st = conn.prepareStatement(queryString);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				int appId=rs.getInt("idWizyty");
				Pacjent patient=new Pacjent(personDAO.getShortPersonData(rs.getInt("idPacjenta")));//patientDAO.getShortPatientData(rs.getInt("idPacjenta"));
				Lekarz doctor=new Lekarz(personDAO.getShortPersonData(rs.getInt("idLekarza"))); //TODO: check, czy wystarczy osobaDAO || make doctorDAO
				GregorianCalendar appDate=convertToDate(rs.getString("data"));
				Wizyta app=new Wizyta(appId, appDate);			
				app.setLekarz(doctor);
				app.setPacjent(patient);
				apps.add(app);
			}
			
		/*} catch (SQLException e) {
			e.printStackTrace();
		}*/
	
		return apps;
		
	}
	
}
