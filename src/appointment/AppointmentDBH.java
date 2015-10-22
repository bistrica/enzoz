package appointment;

import items.Appointment;

import java.sql.SQLException;
import java.util.ArrayList;

import people.Doctor;
import GUI_items.SearchHelper;
import daos.AppointmentDAO;
import daos.DoctorDAO;
import database.DBHandler;
import exceptions.ArchiveException;
import exceptions.LoadDataException;
import exceptions.PreviewCannotBeCreatedException;
import exceptions.TodayException;

public class AppointmentDBH {

	private AppointmentDAO appDAO;
	private ArrayList<Appointment> tempApps;

	private DoctorDAO doctorDAO;
	private SearchHelper previousHelper;
	private String doctorString = "dane lekarzy";

	public AppointmentDBH() {
		appDAO = new AppointmentDAO();
		doctorDAO = new DoctorDAO();
	}

	public ArrayList<Appointment> getTodayAppointments(Doctor doctor)
			throws TodayException {

		ArrayList<Appointment> apps = new ArrayList<Appointment>();

		try {
			apps = appDAO.getTodayAppointments(doctor);
		} catch (SQLException e) {
			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new TodayException();
			} else {

				DBHandler.incrementTrialsNo();
				apps = getTodayAppointments(doctor);
			}
		}
		DBHandler.resetTrialsNo();

		return apps;
	}

	public ArrayList<Appointment> getArchiveAppointments(boolean recentSearch)
			throws ArchiveException {

		ArrayList<Appointment> apps = new ArrayList<Appointment>();
		SearchHelper searchData = recentSearch ? previousHelper : null;
		try {
			apps = appDAO.getArchiveAppointments(searchData);// getArchiveAppointments();
		} catch (SQLException e) {
			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new ArchiveException();
			} else {

				DBHandler.incrementTrialsNo();
				apps = getArchiveAppointments(recentSearch);
			}

		}
		DBHandler.resetTrialsNo();
		tempApps = apps;
		return apps;
	}

	public void openPreview(Appointment app)
			throws PreviewCannotBeCreatedException {
		try {
			appDAO.updateData(app);
		} catch (SQLException e) {

			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new PreviewCannotBeCreatedException();
			} else {

				DBHandler.incrementTrialsNo();
				openPreview(app);
			}

		}
		DBHandler.resetTrialsNo();

	}

	public ArrayList<Appointment> searchData(SearchHelper searchData)
			throws ArchiveException {

		ArrayList<Appointment> apps = new ArrayList<Appointment>();
		try {
			previousHelper = searchData;
			apps = appDAO.getArchiveAppointments(searchData);
		} catch (SQLException e) {
			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new ArchiveException();
			} else {
				System.out.println("ONCE MORE");
				DBHandler.incrementTrialsNo();
				apps = searchData(searchData);
			}
		}
		DBHandler.resetTrialsNo();
		tempApps = apps;
		return apps;
	}

	public ArrayList<Doctor> getDoctors() throws LoadDataException {
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();

		try {
			doctors = doctorDAO.getDoctors();
		} catch (SQLException e) {
			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new LoadDataException(doctorString);
			} else {
				DBHandler.incrementTrialsNo();
				doctors = getDoctors();
			}
		}
		DBHandler.resetTrialsNo();

		return doctors;
	}

}
