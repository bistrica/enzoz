package individualApp;

import items.Choroba;
import items.Lek;
import items.Poradnia;
import items.Wizyta;

import java.sql.SQLException;
import java.util.ArrayList;

import daos.ChorobaDAO;
import daos.LekDAO;
import daos.PoradniaDAO;
import daos.WizytaDAO;
import database.DBHandler;
import exceptions.DataCannotBeEditedException;
import exceptions.LoadDataException;
import exceptions.SaveDataException;

public class IndividualAppDBH {

	WizytaDAO appDAO;
	ChorobaDAO illnessDAO;
	LekDAO drugDAO;
	PoradniaDAO clinicDAO;
	private String illnessesString = "dane chorób";
	private String medicinesString = "dane leków";
	private String clinicsString = "dane poradni";

	public IndividualAppDBH() {
		illnessDAO = new ChorobaDAO();
		drugDAO = new LekDAO();
		clinicDAO = new PoradniaDAO();
		appDAO = new WizytaDAO();
	}

	public ArrayList<Choroba> getAllIllnesses() throws LoadDataException {
		ArrayList<Choroba> ill = new ArrayList<Choroba>();
		try {
			ill = illnessDAO.getAllIllnesses();
		} catch (SQLException e) {

			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new LoadDataException(illnessesString);

			} else {
				System.out.println("ONCE MORE IND");
				DBHandler.incrementTrialsNo();
				ill = getAllIllnesses();
			}
		}

		DBHandler.resetTrialsNo();
		return ill;

	}

	public ArrayList<Lek> getAllMedicines() throws LoadDataException {
		ArrayList<Lek> med = new ArrayList<Lek>();
		try {
			med = drugDAO.getAllMedicines();
		} catch (SQLException e) {
			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new LoadDataException(medicinesString);

			} else {
				System.out.println("ONCE MORE IND");
				DBHandler.incrementTrialsNo();
				med = getAllMedicines();
			}
		}

		DBHandler.resetTrialsNo();
		return med;
	}

	public ArrayList<Poradnia> getAllClinics() throws LoadDataException {
		ArrayList<Poradnia> cli = new ArrayList<Poradnia>();
		try {
			cli = clinicDAO.getAllClinics();
		} catch (SQLException e) {
			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new LoadDataException(clinicsString);

			} else {
				System.out.println("ONCE MORE IND");
				DBHandler.incrementTrialsNo();
				cli = getAllClinics();
			}
		}

		DBHandler.resetTrialsNo();
		return cli;
	}

	public boolean isPossibleToEdit(Wizyta app)
			throws DataCannotBeEditedException {
		boolean isPossible = false;
		try {
			isPossible = appDAO.changeStatus(app);
			// isPossible = appDAO.updateData(app);
		} catch (SQLException e) {

			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new DataCannotBeEditedException();
			} else {
				System.out.println("ONCE MORE");
				DBHandler.incrementTrialsNo();
				isPossible = isPossibleToEdit(app);
			}

		}
		DBHandler.resetTrialsNo();
		return isPossible;
	}

	public void saveAppointment(Wizyta app) throws SaveDataException {

		try {
			appDAO.writeToDatabase(app);
		} catch (SQLException e) {

			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new SaveDataException();
			} else {
				System.out.println("ONCE MORE");
				DBHandler.incrementTrialsNo();
				saveAppointment(app);
			}

		}
		DBHandler.resetTrialsNo();

	}

	public boolean tryToBlockPatientForEdit(Wizyta appointment) {

		boolean isBlocked = false;
		try {
			isBlocked = appDAO.blockPatientData(appointment.getPacjent()
					.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isBlocked;
	}

	public void rewriteStatus(Wizyta appointment) throws SaveDataException {
		try {
			appDAO.writeBackOldStatus(appointment);
		} catch (SQLException e) {

			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new SaveDataException();
			} else {
				System.out.println("ONCE MORE");
				DBHandler.incrementTrialsNo();
				rewriteStatus(appointment);
			}

		}
		DBHandler.resetTrialsNo();
	}

}
