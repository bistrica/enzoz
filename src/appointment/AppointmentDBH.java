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
			// e.printStackTrace();

			if (!DBHandler.reconnect()) {

				throw new TodayException();
			} else {

				apps = getTodayAppointments(doctor);
			}
		}

		return apps;
	}

	public ArrayList<Appointment> getArchiveAppointments(boolean recentSearch)
			throws ArchiveException {

		ArrayList<Appointment> apps = new ArrayList<Appointment>();
		SearchHelper searchData = recentSearch ? previousHelper : null;
		try {
			apps = appDAO.getArchiveAppointments(searchData);// getArchiveAppointments();
		} catch (SQLException e) {
			// e.printStackTrace();

			if (!DBHandler.reconnect()) {

				throw new ArchiveException();
			} else {

				apps = getArchiveAppointments(recentSearch);
			}

		}

		tempApps = apps;
		return apps;
	}

	public void openPreview(Appointment app)
			throws PreviewCannotBeCreatedException {
		try {
			if (true)
				throw new PreviewCannotBeCreatedException();
			appDAO.updateData(app);
		} catch (SQLException e) {

			// e.printStackTrace();

			if (!DBHandler.reconnect()) {

				throw new PreviewCannotBeCreatedException();
			} else {

				openPreview(app);
			}

		}

	}

	public ArrayList<Appointment> searchData(SearchHelper searchData)
			throws ArchiveException {

		ArrayList<Appointment> apps = new ArrayList<Appointment>();
		try {
			previousHelper = searchData;
			apps = appDAO.getArchiveAppointments(searchData);

			System.out.println("a" + apps.size());
		} catch (SQLException e) {
			// e.printStackTrace();

			if (!DBHandler.reconnect()) {

				throw new ArchiveException();
			} else {
				System.out.println("ONCE MORE");

				apps = searchData(searchData);
			}
		}

		tempApps = apps;
		return apps;
	}

	public ArrayList<Doctor> getDoctors() throws LoadDataException {
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();

		try {
			doctors = doctorDAO.getDoctors();
		} catch (SQLException e) {
			// e.printStackTrace();

			if (!DBHandler.reconnect()) {

				throw new LoadDataException(doctorString);
			} else {

				doctors = getDoctors();
			}
		}

		return doctors;
	}

}
