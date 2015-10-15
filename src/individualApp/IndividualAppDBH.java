package individualApp;

import items.Appointment;
import items.Clinic;
import items.Illness;
import items.Medicine;

import java.sql.SQLException;
import java.util.ArrayList;

import daos.AppointmentDAO;
import daos.ClinicDAO;
import daos.IllnessDAO;
import daos.MedicineDAO;
import database.DBHandler;
import exceptions.DataCannotBeEditedException;
import exceptions.LoadDataException;
import exceptions.SaveDataException;

public class IndividualAppDBH {

	AppointmentDAO appDAO;
	IllnessDAO illnessDAO;
	MedicineDAO drugDAO;
	ClinicDAO clinicDAO;
	private String illnessesString = "dane chorób";
	private String medicinesString = "dane leków";
	private String clinicsString = "dane poradni";

	public IndividualAppDBH() {
		illnessDAO = new IllnessDAO();
		drugDAO = new MedicineDAO();
		clinicDAO = new ClinicDAO();
		appDAO = new AppointmentDAO();
	}

	public ArrayList<Illness> getAllIllnesses() throws LoadDataException {
		ArrayList<Illness> ill = new ArrayList<Illness>();
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

	public ArrayList<Medicine> getAllMedicines() throws LoadDataException {
		ArrayList<Medicine> med = new ArrayList<Medicine>();
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

	public ArrayList<Clinic> getAllClinics() throws LoadDataException {
		ArrayList<Clinic> cli = new ArrayList<Clinic>();
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

	public boolean isPossibleToEdit(Appointment app)
			throws DataCannotBeEditedException {

		boolean isPossible = false;
		try {
			isPossible = appDAO.changeStatus(app);

			// to check
			if (isPossible)
				tryToBlockPatientForEdit(app);
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

	public void saveAppointment(Appointment app) throws SaveDataException {

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

	public boolean tryToBlockPatientForEdit(Appointment appointment) {

		System.out.println("TRY TO BLOCK");
		boolean isBlocked = false;
		try {
			isBlocked = appDAO.blockPatientData(appointment.getPatient()
					.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isBlocked;
	}

	public void rewriteStatus(Appointment appointment) throws SaveDataException {
		System.out.println("REWRITE");
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
