package daos;

import items.Choroba;
import items.Konsultacja;
import items.Recepta;
import items.Skierowanie;
import items.Wizyta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import people.Lekarz;
import people.Pacjent;
import GUI_items.SearchHelper;
import database.DBHandler;

public class WizytaDAO {

	// private static Connection conn;
	private PacjentDAO patientDAO;
	private OsobaDAO personDAO;
	private KonsultacjaDAO interviewDAO;
	private ReceptaDAO prescriptionDAO;
	private SkierowanieDAO examinationDAO;
	private ChorobaDAO illnessDAO;
	private boolean blockingAutoCommit;

	public WizytaDAO() {
		patientDAO = new PacjentDAO();
		personDAO = new OsobaDAO();
		interviewDAO = new KonsultacjaDAO();
		prescriptionDAO = new ReceptaDAO();
		examinationDAO = new SkierowanieDAO();
		illnessDAO = new ChorobaDAO();
		// conn = DBHandler.getDatabaseConnection();
	}

	// private ArrayList<Wizyta> getAppointments(Lekarz doct)

	public ArrayList<Wizyta> getTodayAppointments(Lekarz doctor)
			throws SQLException {
		System.out.println("GTA");
		// conn = DBHandler.getDatabaseConnection(); // refreshed

		ArrayList<Wizyta> apps = new ArrayList<Wizyta>();
		PreparedStatement st;
		String queryString = "SELECT idWizyty, idPacjenta, data, status FROM wizytyDzis WHERE idLekarza=? ORDER BY data ASC";

		Connection conn = DBHandler.getDatabaseConnection();
		// try {
		// System.out.println("c " + conn);
		st = conn.prepareStatement(queryString);
		st.setInt(1, doctor.getId());
		ResultSet rs = st.executeQuery();// Update();
		while (rs.next()) {
			int appId = rs.getInt("idWizyty");
			Pacjent patient = patientDAO
					.getPatientData(rs.getInt("idPacjenta"));
			System.out.println("P: " + patient);
			GregorianCalendar appDate = convertToDate(rs.getString("data"));

			Wizyta app = new Wizyta(appId, appDate);
			app.setLekarz(doctor);
			app.setPacjent(patient);
			app.setStatus(rs.getString("status"));
			apps.add(app);
		}
		rs.close();
		st.close();

		return apps;
	}

	private GregorianCalendar convertToDate(String dateString) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
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

	/*
	 * private Wizyta getAppointmentData(int id){
	 * 
	 * conn=DBHandler.getDatabaseConnection();
	 * 
	 * Wizyta app=new Wizyta(id,appDate, ); return null; }
	 */

	private boolean writeAppointmentData(Wizyta app) {
		return false;
	}

	public static int getId(Wizyta app) {
		Connection conn = DBHandler.getDatabaseConnection();

		int patientId = app.getPacjent().getId();
		int doctorId = app.getLekarz().getId();

		int appId = -1;
		PreparedStatement st;
		// SELECT idWizyty, data FROM wizytyDzis WHERE idPacjenta = 299 AND
		// idLekarza = 303 ORDER BY data desc LIMIT 1
		String queryString = "SELECT idWizyty FROM wizytyDzis WHERE idPacjenta = ? AND idLekarza = ?";// "SELECT idTypu FROM pracownicy WHERE login = ?";

		try {
			st = conn.prepareStatement(queryString);

			st.setInt(1, patientId);
			st.setInt(2, doctorId);
			ResultSet rs = st.executeQuery();// Update();

			while (rs.next()) {
				appId = rs.getInt("idWizyty");
				break;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace(); // TODO: ?
		}
		return appId;

	}

	/*
	 * public boolean checkAndChangeStatus(Wizyta app) throws SQLException {
	 * 
	 * Connection conn = DBHandler.getDatabaseConnection();
	 * conn.setAutoCommit(false);
	 * 
	 * int appId = app.getId(); PreparedStatement st; // SELECT idWizyty, data
	 * FROM wizytyDzis WHERE idPacjenta = 299 AND // idLekarza = 303 ORDER BY
	 * data desc LIMIT 1 String status = "realizowana"; //
	 * System.out.println(">"+appId); String queryString =
	 * "UPDATE wizyty SET status = ? WHERE idWizyty = ? AND status != ? ";//
	 * "SELECT idWizyty FROM wizytyDzis WHERE idPacjenta = ? AND idLekarza = ?"
	 * ;//"SELECT idTypu FROM pracownicy WHERE login = ?"; int updatedRecords =
	 * -1; // try { st = conn.prepareStatement(queryString); // st.setInt(1, 1);
	 * st.setString(1, status); st.setInt(2, appId); st.setString(3, status);
	 * updatedRecords = st.executeUpdate(); System.out.println(updatedRecords);
	 * 
	 * 
	 * if (updatedRecords > 0) { try { updateData(app); conn.commit(); } catch
	 * (SQLException e) { System.out.println("rollback"); //
	 * e.printStackTrace(); conn.rollback(); conn.setAutoCommit(true); throw e;
	 * }
	 * 
	 * } conn.setAutoCommit(true); st.close(); return updatedRecords > 0; }
	 */

	public boolean changeStatus(Wizyta app) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();
		// conn.
		// boolean
		// conn.setAutoCommit(false);

		int appId = app.getId();
		PreparedStatement st;
		String status = "realizowana";
		String queryString = "UPDATE wizyty SET status = ? WHERE idWizyty = ? AND status != ? ";// "SELECT idWizyty FROM wizytyDzis WHERE idPacjenta = ? AND idLekarza = ?";//"SELECT idTypu FROM pracownicy WHERE login = ?";
		int updatedRecords = -1;
		st = conn.prepareStatement(queryString);
		st.setString(1, status);
		st.setInt(2, appId);
		st.setString(3, status);
		try {
			updatedRecords = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1205) // Lock wait timeout exceeded
				updatedRecords = -1;
			else
				throw e;
		}
		st.close();
		return updatedRecords > 0;
	}

	public void updateData(Wizyta app) throws SQLException {
		int appId = app.getId();

		Pacjent patient = patientDAO.getPatientData(app.getPacjent().getId());
		app.setPacjent(patient);

		Konsultacja interview = interviewDAO.getInterview(appId);
		app.setKonsultacja(interview);

		Recepta prescription = prescriptionDAO.getPrescription(appId);
		app.setRecepta(prescription);

		ArrayList<Skierowanie> examinations = examinationDAO
				.getExaminations(appId);
		app.setSkierowania(examinations);

		ArrayList<Choroba> tempIllnesses = illnessDAO.getTemporaryIllnesses(
				appId, app.getPacjent().getChorobyPrzewlek�e());
		app.setRozpoznaneChoroby(tempIllnesses);

		app.setArchive(true);
	}

	private ArrayList<Wizyta> getArchiveAppointments() throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		ArrayList<Wizyta> apps = new ArrayList<Wizyta>();
		PreparedStatement st;

		int year = Calendar.getInstance().get(Calendar.YEAR) - 2;

		String queryString = "SELECT idWizyty, idPacjenta, idLekarza, data, status FROM wizytyArchiwum WHERE YEAR(data)>"
				+ year + " ORDER BY data DESC";

		// try {

		st = conn.prepareStatement(queryString);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			int appId = rs.getInt("idWizyty");
			Pacjent patient = new Pacjent(personDAO.getShortPersonData(rs
					.getInt("idPacjenta")));// patientDAO.getShortPatientData(rs.getInt("idPacjenta"));
			Lekarz doctor = new Lekarz(personDAO.getShortPersonData(rs
					.getInt("idLekarza"))); // TODO: check, czy wystarczy
											// osobaDAO || make doctorDAO
			GregorianCalendar appDate = convertToDate(rs.getString("data"));
			Wizyta app = new Wizyta(appId, appDate);
			app.setLekarz(doctor);
			app.setPacjent(patient);
			app.setStatus(rs.getString("status"));
			apps.add(app);
		}

		rs.close();
		st.close();

		return apps;

	}

	public ArrayList<Wizyta> getArchiveAppointments(SearchHelper sh)
			throws SQLException {

		if (sh == null)
			return getArchiveAppointments();

		Connection conn = DBHandler.getDatabaseConnection();

		ArrayList<Wizyta> apps = new ArrayList<Wizyta>();
		PreparedStatement st;

		// int year = Calendar.getInstance().get(Calendar.YEAR) - 2;

		String queryString = "SELECT idWizyty, idPacjenta, idLekarza, data, status FROM wizytyArchiwum WHERE ";
		StringBuilder sb = new StringBuilder(queryString);
		int year = sh.getYear(), month = sh.getMonth(), day = sh.getDay();
		long PESEL = sh.getPESEL();
		Lekarz doc = sh.getSurname();

		ArrayList<Integer> parameters = new ArrayList<Integer>();
		if (year != -1) {
			sb.append("YEAR(data)=? AND ");
			parameters.add(year);
		}
		if (month != -1) {
			sb.append("MONTH(data)=? AND ");
			parameters.add(month);
		}
		if (day != -1) {
			sb.append("DAY(data)=? AND ");
			parameters.add(day);
		}
		if (PESEL != -1) {
			int patientId = personDAO.getPersonId(PESEL);
			sb.append("IdPacjenta=? AND ");
			parameters.add(patientId);
		}
		if (doc != null) {
			sb.append("IdLekarza=? AND ");
			parameters.add(doc.getId());
		}

		sb.append(" 1 ORDER BY data DESC");
		queryString = sb.toString();

		// try {

		st = conn.prepareStatement(queryString);
		int i = 1;
		for (int param : parameters)
			st.setInt(i++, param);

		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			int appId = rs.getInt("idWizyty");
			Pacjent patient = new Pacjent(personDAO.getShortPersonData(rs
					.getInt("idPacjenta")));// patientDAO.getShortPatientData(rs.getInt("idPacjenta"));
			Lekarz doctor = new Lekarz(personDAO.getShortPersonData(rs
					.getInt("idLekarza"))); // TODO: check, czy wystarczy
											// osobaDAO || make doctorDAO
			GregorianCalendar appDate = convertToDate(rs.getString("data"));
			Wizyta app = new Wizyta(appId, appDate);
			app.setLekarz(doctor);
			app.setPacjent(patient);
			app.setStatus(rs.getString("status"));
			apps.add(app);
		}

		rs.close();
		st.close();

		return apps;

	}

	/*
	 * public boolean checkStatus(Wizyta app) { // TODO Auto-generated method
	 * stub return false; }
	 */

	public void writeToDatabase(Wizyta app) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();
		Savepoint svp = conn.setSavepoint();
		// TODO: update status

		// boolean autoCommit = conn.getAutoCommit();
		// conn.setAutoCommit(false);
		int appId = app.getId();

		try {
			Konsultacja interview = app.getKonsultacja();
			if (interview != null)
				interviewDAO.writeToDatabase(appId, interview);

			Recepta prescription = app.getRecepta();
			if (prescription != null)
				prescriptionDAO.writeToDatabase(appId, prescription);

			ArrayList<Skierowanie> exams = app.getSkierowania();
			if (exams != null)
				examinationDAO.writeToDatabase(appId, exams);

			ArrayList<Choroba> tempIllnesses = app.getRozpoznaneChoroby();
			if (tempIllnesses != null)
				illnessDAO.writeCurrentIllnesses(appId, tempIllnesses);

			if (!app.isArchiveAppointment())
				patientDAO.writePatientConstantIllnesses(app.getPacjent());

			closeAppointment(app);

			// conn.commit();
		} catch (SQLException e) {
			conn.rollback(svp);
			throw e;
		} finally {
			// conn.setAutoCommit(autoCommit);
			// System.out.println("AUTO: " + autoCommit);
		}
	}

	private void closeAppointment(Wizyta app) throws SQLException {
		Connection conn = DBHandler.getDatabaseConnection();

		int appId = app.getId();
		PreparedStatement st;
		String status = "zrealizowana";
		String queryString = "UPDATE wizyty SET status = ? WHERE idWizyty = ? ";// AND
																				// status
																				// !=
																				// ?
																				// ";// "SELECT
																				// idWizyty
																				// FROM
																				// wizytyDzis
																				// WHERE
																				// idPacjenta
																				// =
																				// ?
																				// AND
																				// idLekarza
																				// =
																				// ?";//"SELECT
																				// idTypu
																				// FROM
																				// pracownicy
																				// WHERE
																				// login
																				// =
																				// ?";
		st = conn.prepareStatement(queryString);
		st.setString(1, status);
		st.setInt(2, appId);
		// st.setString(3, status);
		st.executeUpdate();

		st.close();

		unblockPatientData();

	}

	public boolean blockPatientData(int patientId) throws SQLException {

		System.out.println("BLOCK");
		Connection conn = DBHandler.getDatabaseConnection();

		blockingAutoCommit = conn.getAutoCommit();

		conn.setAutoCommit(false);
		PreparedStatement st;
		// String status = "zrealizowana";
		String queryString = "SELECT * FROM wizyty WHERE idPacjenta = ? LOCK IN SHARE MODE";// "SELECT idWizyty FROM wizytyDzis WHERE idPacjenta = ? AND idLekarza = ?";//"SELECT idTypu FROM pracownicy WHERE login = ?";
		try {
			st = conn.prepareStatement(queryString,
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

			st.setInt(1, patientId);

			st.executeQuery();

			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	// jeden pacjent jednocze�nie przyjmowany

	public void unblockPatientData() throws SQLException {
		System.out.println("UNBLOCK");
		Connection conn = DBHandler.getDatabaseConnection();
		conn.commit(); // odblokowanie SELECT FOR UPDATE (?)
		conn.setAutoCommit(blockingAutoCommit);
	}

	public void writeBackOldStatus(Wizyta app) throws SQLException {

		System.out.println("OLD");

		Connection conn = DBHandler.getDatabaseConnection();
		// conn.
		// boolean
		// conn.setAutoCommit(false);

		int appId = app.getId();
		PreparedStatement st;
		String status = app.getStatus();
		String queryString = "UPDATE wizyty SET status = ? WHERE idWizyty = ?";
		st = conn.prepareStatement(queryString);
		st.setString(1, status);
		st.setInt(2, appId);
		st.executeUpdate();
		st.close();

		unblockPatientData();
	}

}
