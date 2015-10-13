package appointment;

import items.Wizyta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import people.Lekarz;
import GUI_items.SearchHelper;
import daos.LekarzDAO;
import daos.WizytaDAO;
import database.DBHandler;
import exceptions.ArchiveException;
import exceptions.LoadDataException;
import exceptions.PreviewCannotBeCreatedException;
import exceptions.TodayException;

public class AppointmentDBH {

	Connection conn = null;
	WizytaDAO appDAO;
	ArrayList<Wizyta> tempApps;
	// OsobaDAO personDAO;
	LekarzDAO doctorDAO;
	private SearchHelper previousHelper;
	private String doctorString = "dane lekarzy";

	public AppointmentDBH() {
		conn = DBHandler.getDatabaseConnection();
		appDAO = new WizytaDAO();
		doctorDAO = new LekarzDAO();
	}

	public ArrayList<Wizyta> getTodayAppointments(Lekarz doctor)
			throws TodayException {

		ArrayList<Wizyta> apps = new ArrayList<Wizyta>();

		try {
			apps = appDAO.getTodayAppointments(doctor);
		} catch (SQLException e) {
			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new TodayException();
			} else {
				System.out.println("ONCE MORE");
				DBHandler.incrementTrialsNo();
				apps = getTodayAppointments(doctor);
			}
		}
		DBHandler.resetTrialsNo();

		return apps;
	}

	public ArrayList<Wizyta> getArchiveAppointments(boolean recentSearch)
			throws ArchiveException {

		ArrayList<Wizyta> apps = new ArrayList<Wizyta>();
		SearchHelper searchData = recentSearch ? previousHelper : null;
		try {
			apps = appDAO.getArchiveAppointments(searchData);// getArchiveAppointments();
		} catch (SQLException e) {
			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new ArchiveException();
			} else {
				System.out.println("ONCE MORE");
				DBHandler.incrementTrialsNo();
				apps = getArchiveAppointments(recentSearch);
			}
			// throw new ArchiveException();
		}
		DBHandler.resetTrialsNo();
		tempApps = apps;
		return apps;
	}

	public void openPreview(Wizyta app) throws PreviewCannotBeCreatedException {
		try {
			appDAO.updateData(app);
			// isPossible = appDAO.updateData(app);
		} catch (SQLException e) {

			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new PreviewCannotBeCreatedException();
			} else {
				System.out.println("ONCE MORE");
				DBHandler.incrementTrialsNo();
				openPreview(app);// IfPossible(app);
			}

		}
		DBHandler.resetTrialsNo();

	}

	public ArrayList<Wizyta> searchData(SearchHelper searchData)
			throws ArchiveException {

		ArrayList<Wizyta> apps = new ArrayList<Wizyta>();
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
				apps = searchData(searchData);// getArchiveAppointments();
			}
			// throw new ArchiveException();
		}
		DBHandler.resetTrialsNo();
		tempApps = apps;
		return apps;
	}

	public ArrayList<Lekarz> getDoctors() throws LoadDataException {
		ArrayList<Lekarz> doctors = new ArrayList<Lekarz>();

		try {
			doctors = doctorDAO.getDoctors();
		} catch (SQLException e) {
			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new LoadDataException(doctorString);
			} else {
				System.out.println("ONCE MORE");
				DBHandler.incrementTrialsNo();
				doctors = getDoctors();// getArchiveAppointments();
			}
		}
		DBHandler.resetTrialsNo();

		return doctors;
	}

	// TO REMOVE (?)
	/*
	 * private boolean openPreviewIfPossible(Wizyta app) throws
	 * PreviewCannotBeCreatedException { boolean isPossible = false; try {
	 * isPossible = appDAO.checkAndChangeStatus(app); // isPossible =
	 * appDAO.updateData(app); } catch (SQLException e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
	 * DBHandler.resetTrialsNo(); throw new PreviewCannotBeCreatedException(); }
	 * else { System.out.println("ONCE MORE"); DBHandler.incrementTrialsNo();
	 * isPossible = openPreviewIfPossible(app); }
	 * 
	 * } DBHandler.resetTrialsNo(); return isPossible; }/
	 * 
	 * /* public boolean statusAllowsEditing(Wizyta app) throws SQLException {
	 * return appDAO.checkAndChangeStatus(app); }
	 */

	/*
	 * public void updateAppData(Wizyta app) throws SQLException { // TODO
	 * Auto-generated method stub appDAO.updateData(app); }
	 */

}
