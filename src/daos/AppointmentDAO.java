package daos;

import items.Appointment;
import items.Examination;
import items.Illness;
import items.Interview;
import items.Prescription;

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
import java.util.HashMap;

import people.Doctor;
import people.Patient;
import GUI_items.SearchHelper;
import database.DBHandler;

public class AppointmentDAO {

	private PatientDAO patientDAO;
	private PersonDAO personDAO;
	private InterviewDAO interviewDAO;
	private PrescriptionDAO prescriptionDAO;
	private ExaminationDAO examinationDAO;
	private IllnessDAO illnessDAO;
	private boolean blockingAutoCommit;

	private HashMap<Integer, Doctor> doctors;
	private HashMap<Integer, Patient> patients;

	public AppointmentDAO() {
		patientDAO = new PatientDAO();
		personDAO = new PersonDAO();
		interviewDAO = new InterviewDAO();
		prescriptionDAO = new PrescriptionDAO();
		examinationDAO = new ExaminationDAO();
		illnessDAO = new IllnessDAO();

		doctors = new HashMap<Integer, Doctor>();
		patients = new HashMap<Integer, Patient>();
	}

	public ArrayList<Appointment> getTodayAppointments(Doctor doctor)
			throws SQLException {
		System.out.println("GTA");

		ArrayList<Appointment> apps = new ArrayList<Appointment>();

		PreparedStatement st;

		String queryString = "SELECT w.IdWizyty, w.IdPacjenta, w.Data, w.status, "
				+ "o.Imie, o.Nazwisko, o.PESEL, o.Ulica, o.NrDomu, o.NrMieszkania, o.KodPocztowy, o.Miejscowosc, o.Telefon "
				+ "FROM wizytydzis AS w "
				+ "JOIN pacjenci AS p ON p.IdPacjenta=w.IdPacjenta "
				+ "JOIN osoby AS o ON o.IdOsoby=p.IdPacjenta "
				+ "WHERE w.IdLekarza=? " + "ORDER BY w.Data ASC";

		Connection conn = DBHandler.getDatabaseConnection();

		st = conn.prepareStatement(queryString);
		st.setInt(1, doctor.getId());
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			int appId = rs.getInt("w.IdWizyty");
			// Patient patient = patientDAO
			// .getPatientData(rs.getInt("IdPacjenta"));
			Patient patient = patients.get(rs.getShort("w.IdPacjenta"));
			if (patient == null) {
				patient = new Patient(rs.getInt("w.IdPacjenta"),

				rs.getString("o.Imie"), rs.getString("o.Nazwisko"),
						rs.getString("o.PESEL"), rs.getString("o.Ulica"),
						rs.getString("o.NrDomu"),
						rs.getString("o.NrMieszkania"),
						rs.getString("o.Miejscowosc"),
						rs.getString("o.KodPocztowy"),
						rs.getString("o.Telefon"));

				patients.put(patient.getId(), patient);
			}
			GregorianCalendar appDate = convertToDate(rs.getString("w.Data"));

			Appointment app = new Appointment(appId, appDate);
			app.setDoctor(doctor);
			app.setPatient(patient);
			app.setStatus(rs.getString("w.status"));
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
			e.printStackTrace();
		}

		return appDate;
	}

	public static int getId(Appointment app) {
		Connection conn = DBHandler.getDatabaseConnection();

		int patientId = app.getPatient().getId();
		int doctorId = app.getDoctor().getId();

		int appId = -1;
		PreparedStatement st;
		String queryString = "SELECT IdWizyty FROM wizytydzis WHERE IdPacjenta = ? AND IdLekarza = ?";

		try {
			st = conn.prepareStatement(queryString);

			st.setInt(1, patientId);
			st.setInt(2, doctorId);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				appId = rs.getInt("IdWizyty");
				break;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return appId;

	}

	public boolean changeStatus(Appointment app) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		int appId = app.getId();
		PreparedStatement st;
		String status = "realizowana";
		String queryString = "UPDATE wizyty SET status = ? WHERE IdWizyty = ? AND status != ? ";// "SELECT IdWizyty FROM wizytydzis WHERE IdPacjenta = ? AND IdLekarza = ?";//"SELECT idTypu FROM pracownicy WHERE login = ?";
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

	public void updateData(Appointment app) throws SQLException {
		int appId = app.getId();

		Patient patient = patientDAO.getPatientData(app.getPatient().getId());
		app.setPatient(patient);

		Interview interview = interviewDAO.getInterview(appId);
		app.setInterview(interview);

		Prescription prescription = prescriptionDAO.getPrescription(appId);
		app.setPrescription(prescription);

		ArrayList<Examination> examinations = examinationDAO
				.getExaminations(appId);
		app.setExaminations(examinations);

		ArrayList<Illness> tempIllnesses = illnessDAO.getTemporaryIllnesses(
				appId, app.getPatient().getConstantIllnesses());
		app.setRecognizedIllnesses(tempIllnesses);

		app.setArchive(true);
	}

	private ArrayList<Appointment> getArchiveAppointments() throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		ArrayList<Appointment> apps = new ArrayList<Appointment>();

		PreparedStatement st;

		int year = Calendar.getInstance().get(Calendar.YEAR) - 2;

		String queryString = "SELECT w.IdWizyty, w.IdPacjenta, w.Data, w.status, w.IdLekarza, "
				+ "o.Imie, o.Nazwisko, o.PESEL, l.Imie, l.Nazwisko, l.PESEL "
				+ "FROM wizytyarchiwum w "
				+ "JOIN pacjenci p ON p.IdPacjenta=w.IdPacjenta "
				+ "JOIN osoby o ON o.IdOsoby=p.IdPacjenta "
				+ "JOIN lekarze le ON le.idOsoby=w.IdLekarza "
				+ "JOIN osoby l ON l.IdOsoby=le.idOsoby "
				+ "WHERE YEAR(w.Data)> " + year + " ORDER BY w.Data DESC";

		st = conn.prepareStatement(queryString);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			int appId = rs.getInt("w.IdWizyty");
			Patient patient = patients.get(rs.getInt("w.IdPacjenta"));
			if (patient == null) {
				patient = new Patient(rs.getInt("w.IdPacjenta"),
						rs.getString("o.Imie"), rs.getString("o.Nazwisko"),
						rs.getString("o.PESEL"));
				patients.put(patient.getId(), patient);
			}
			// personDAO.getShortPersonData(rs.getInt("IdPacjenta")));//
			// patientDAO.getShortPatientData(rs.getInt("IdPacjenta"));
			Doctor doctor = doctors.get(rs.getInt("w.IdLekarza"));
			if (doctor == null) {
				doctor = new Doctor(rs.getInt("w.IdLekarza"),
						rs.getString("l.Imie"), rs.getString("l.Nazwisko"),
						rs.getString("l.PESEL"));
				doctors.put(doctor.getId(), doctor);
			}
			// new Doctor(personDAO.getShortPersonData(rs.getInt("IdLekarza")));
			GregorianCalendar appDate = convertToDate(rs.getString("w.Data"));
			Appointment app = new Appointment(appId, appDate);
			app.setDoctor(doctor);
			app.setPatient(patient);
			app.setStatus(rs.getString("w.status"));
			apps.add(app);
		}

		rs.close();
		st.close();

		return apps;

	}

	public ArrayList<Appointment> getArchiveAppointments(SearchHelper sh)
			throws SQLException {

		if (sh == null)
			return getArchiveAppointments();

		Connection conn = DBHandler.getDatabaseConnection();

		ArrayList<Appointment> apps = new ArrayList<Appointment>();
		PreparedStatement st;

		String queryString = "SELECT w.IdWizyty, w.IdPacjenta, w.Data, w.status, w.IdLekarza, "
				+ "o.Imie, o.Nazwisko, o.PESEL, l.Imie, l.Nazwisko, l.PESEL "
				+ "FROM wizytyarchiwum w "
				+ "JOIN pacjenci p ON p.IdPacjenta=w.IdPacjenta "
				+ "JOIN osoby o ON o.IdOsoby=p.IdPacjenta "
				+ "JOIN lekarze le ON le.idOsoby=w.IdLekarza "
				+ "JOIN osoby l ON l.IdOsoby=le.idOsoby " + "WHERE ";

		StringBuilder sb = new StringBuilder(queryString);
		int year = sh.getYear(), month = sh.getMonth(), day = sh.getDay();
		long PESEL = sh.getPESEL();
		Doctor doc = sh.getSurname();

		ArrayList<Integer> parameters = new ArrayList<Integer>();
		if (year != -1) {
			sb.append("YEAR(w.Data)=? AND ");
			parameters.add(year);
		}
		if (month != -1) {
			sb.append("MONTH(w.Data)=? AND ");
			parameters.add(month);
		}
		if (day != -1) {
			sb.append("DAY(w.Data)=? AND ");
			parameters.add(day);
		}
		if (PESEL != -1) {
			int patientId = personDAO.getPersonId(PESEL);
			sb.append("w.IdPacjenta=? AND ");
			parameters.add(patientId);
		}
		if (doc != null) {
			sb.append("w.IdLekarza=? AND ");
			parameters.add(doc.getId());
		}

		sb.append(" 1 ORDER BY w.Data DESC");
		queryString = sb.toString();

		st = conn.prepareStatement(queryString);
		int i = 1;
		for (int param : parameters)
			st.setInt(i++, param);

		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			int appId = rs.getInt("w.IdWizyty");

			Patient patient = patients.get(rs.getInt("w.IdPacjenta"));
			if (patient == null) {
				patient = new Patient(rs.getInt("w.IdPacjenta"),
						rs.getString("o.Imie"), rs.getString("o.Nazwisko"),
						rs.getString("o.PESEL"));
				patients.put(patient.getId(), patient);
			}

			Doctor doctor = doctors.get(rs.getInt("w.IdLekarza"));
			if (doctor == null) {
				doctor = new Doctor(rs.getInt("w.IdLekarza"),
						rs.getString("l.Imie"), rs.getString("l.Nazwisko"),
						rs.getString("l.PESEL"));
				doctors.put(doctor.getId(), doctor);
			}
			// Patient patient =
			// new Patient(personDAO.getShortPersonData(rs
			// .getInt("IdPacjenta")));//
			// patientDAO.getShortPatientData(rs.getInt("IdPacjenta"));
			// Doctor doctor =
			// new Doctor(personDAO.getShortPersonData(rs
			// .getInt("IdLekarza"))); // TODO: check, czy wystarczy
			// osobaDAO || make doctorDAO
			GregorianCalendar appDate = convertToDate(rs.getString("w.Data"));
			Appointment app = new Appointment(appId, appDate);
			app.setDoctor(doctor);
			app.setPatient(patient);
			app.setStatus(rs.getString("w.status"));
			apps.add(app);
		}

		rs.close();
		st.close();

		return apps;

	}

	// private ArrayList<Appointment> getArchiveAppointments() throws
	// SQLException {
	//
	// Connection conn = DBHandler.getDatabaseConnection();
	//
	// ArrayList<Appointment> apps = new ArrayList<Appointment>();
	// PreparedStatement st;
	//
	// int year = Calendar.getInstance().get(Calendar.YEAR) - 2;
	//
	// String queryString =
	// "SELECT IdWizyty, IdPacjenta, IdLekarza, Data, status FROM wizytyarchiwum WHERE YEAR(Data)>"
	// + year + " ORDER BY Data DESC";
	//
	// // try {
	//
	// st = conn.prepareStatement(queryString);
	// ResultSet rs = st.executeQuery();
	// while (rs.next()) {
	// int appId = rs.getInt("IdWizyty");
	//
	// Patient patient = patients.get(rs.getInt("IdPacjenta"));
	// if (patient == null) {
	//
	// patient = new Patient(personDAO.getShortPersonData(rs
	// .getInt("IdPacjenta")));
	// patients.put(patient.getId(), patient);
	// System.out.println("POBRANO P " + patient);
	// }
	// // patientDAO.getShortPatientData(rs.getInt("idPacjenta"));
	// Doctor doctor = doctors.get(rs.getInt("IdLekarza"));
	// if (doctor == null) {
	// doctor = new Doctor(personDAO.getShortPersonData(rs
	// .getInt("IdLekarza")));
	// doctors.put(doctor.getId(), doctor);
	// System.out.println("POBRANO D " + doctor);
	// }
	//
	// // TODO: check, czy wystarczy
	// // osobaDAO || make doctorDAO
	// GregorianCalendar appDate = convertToDate(rs.getString("Data"));
	// Appointment app = new Appointment(appId, appDate);
	// app.setDoctor(doctor);
	// app.setPatient(patient);
	// app.setStatus(rs.getString("status"));
	// apps.add(app);
	// }
	//
	// rs.close();
	// st.close();
	//
	// return apps;
	//
	// }
	//
	// public ArrayList<Appointment> getArchiveAppointments(SearchHelper sh)
	// throws SQLException {
	//
	// if (sh == null)
	// return getArchiveAppointments();
	//
	// Connection conn = DBHandler.getDatabaseConnection();
	//
	// ArrayList<Appointment> apps = new ArrayList<Appointment>();
	// PreparedStatement st;
	//
	// // int year = Calendar.getInstance().get(Calendar.YEAR) - 2;
	//
	// String queryString =
	// "SELECT IdWizyty, IdPacjenta, IdLekarza, Data, status FROM wizytyarchiwum WHERE ";
	// StringBuilder sb = new StringBuilder(queryString);
	// int year = sh.getYear(), month = sh.getMonth(), day = sh.getDay();
	// long PESEL = sh.getPESEL();
	// Doctor doc = sh.getSurname();
	//
	// ArrayList<Integer> parameters = new ArrayList<Integer>();
	// if (year != -1) {
	// sb.append("YEAR(Data)=? AND ");
	// parameters.add(year);
	// }
	// if (month != -1) {
	// sb.append("MONTH(Data)=? AND ");
	// parameters.add(month);
	// }
	// if (day != -1) {
	// sb.append("DAY(Data)=? AND ");
	// parameters.add(day);
	// }
	// if (PESEL != -1) {
	// int patientId = personDAO.getPersonId(PESEL);
	// sb.append("IdPacjenta=? AND ");
	// parameters.add(patientId);
	// }
	// if (doc != null) {
	// sb.append("IdLekarza=? AND ");
	// parameters.add(doc.getId());
	// }
	//
	// sb.append(" 1 ORDER BY Data DESC");
	// queryString = sb.toString();
	//
	// // try {
	//
	// st = conn.prepareStatement(queryString);
	// int i = 1;
	// for (int param : parameters)
	// st.setInt(i++, param);
	//
	// ResultSet rs = st.executeQuery();
	// while (rs.next()) {
	// int appId = rs.getInt("IdWizyty");
	//
	// Patient patient = patients.get(rs.getInt("IdPacjenta"));
	// if (patient == null) {
	//
	// patient = new Patient(personDAO.getShortPersonData(rs
	// .getInt("IdPacjenta")));
	// patients.put(patient.getId(), patient);
	// System.out.println("POBRANO P1 " + patient);
	// }
	// // patientDAO.getShortPatientData(rs.getInt("idPacjenta"));
	// Doctor doctor = doctors.get(rs.getInt("IdLekarza"));
	// if (doctor == null) {
	// doctor = new Doctor(personDAO.getShortPersonData(rs
	// .getInt("IdLekarza")));
	// doctors.put(doctor.getId(), doctor);
	// System.out.println("POBRANO D1 " + doctor);
	// }
	//
	// // Patient patient = new Patient(personDAO.getShortPersonData(rs
	// // .getInt("IdPacjenta")));//
	// // patientDAO.getShortPatientData(rs.getInt("idPacjenta"));
	// // Doctor doctor = new Doctor(personDAO.getShortPersonData(rs
	// // .getInt("IdLekarza"))); // TODO: check, czy wystarczy
	// // // osobaDAO || make doctorDAO
	// GregorianCalendar appDate = convertToDate(rs.getString("Data"));
	// Appointment app = new Appointment(appId, appDate);
	// app.setDoctor(doctor);
	// app.setPatient(patient);
	// app.setStatus(rs.getString("status"));
	// apps.add(app);
	// }
	//
	// rs.close();
	// st.close();
	//
	// return apps;
	//
	// }

	public void writeToDatabase(Appointment app) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		if (conn.getAutoCommit())
			conn.setAutoCommit(false);

		Savepoint svp = conn.setSavepoint();

		int appId = app.getId();

		try {
			Interview interview = app.getInterview();
			if (interview != null)
				interviewDAO.writeToDatabase(appId, interview);

			Prescription prescription = app.getPrescription();
			if (prescription != null)
				prescriptionDAO.writeToDatabase(appId, prescription);

			ArrayList<Examination> exams = app.getExaminations();
			if (exams != null)
				examinationDAO.writeToDatabase(appId, exams);

			ArrayList<Illness> tempIllnesses = app.getRecognizedIllnesses();
			if (tempIllnesses != null)
				illnessDAO.writeCurrentIllnesses(appId, tempIllnesses);

			if (!app.isArchiveAppointment())
				patientDAO.writePatientConstantIllnesses(app.getPatient());

			closeAppointment(app);

		} catch (SQLException e) {
			conn.rollback(svp);
			throw e;
		}
	}

	private void closeAppointment(Appointment app) throws SQLException {
		System.out.println("closeapp");
		Connection conn = DBHandler.getDatabaseConnection();

		int appId = app.getId();
		PreparedStatement st;
		String status = "zrealizowana";
		String queryString = "UPDATE wizyty SET status = ? WHERE IdWizyty = ? ";// AND
																				// status
																				// !=
																				// ?
																				// ";// "SELECT
																				// IdWizyty
																				// FROM
																				// wizytyDzis
																				// WHERE
																				// IdPacjenta
																				// =
																				// ?
																				// AND
																				// IdLekarza
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
		st.executeUpdate();

		st.close();

		unblockPatientData();

	}

	public boolean blockPatientData(int patientId) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		blockingAutoCommit = conn.getAutoCommit();

		conn.setAutoCommit(false);
		PreparedStatement st;
		String queryString = "SELECT * FROM wizyty WHERE IdPacjenta = ? LOCK IN SHARE MODE";// "SELECT IdWizyty FROM wizytydzis WHERE IdPacjenta = ? AND IdLekarza = ?";//"SELECT idTypu FROM pracownicy WHERE login = ?";
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

	// jeden pacjent jednoczeœnie przyjmowany

	public void unblockPatientData() throws SQLException {
		System.out.println("unblock");
		Connection conn = DBHandler.getDatabaseConnection();
		conn.commit(); // odblokowanie SELECT LOCK IN SHARE MODE (?)
		conn.setAutoCommit(blockingAutoCommit);
	}

	public void writeBackOldStatus(Appointment app) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		if (conn.getAutoCommit())
			conn.setAutoCommit(false);

		int appId = app.getId();
		PreparedStatement st;
		String status = app.getStatus();
		String queryString = "UPDATE wizyty SET status = ? WHERE IdWizyty = ?";
		st = conn.prepareStatement(queryString);
		st.setString(1, status);
		st.setInt(2, appId);
		st.executeUpdate();
		st.close();

		unblockPatientData();
	}

}
