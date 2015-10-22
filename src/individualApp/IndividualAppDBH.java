package individualApp;

import items.Appointment;
import items.Clinic;
import items.Illness;
import items.Medicine;

import java.sql.SQLException;
import java.util.ArrayList;

import people.Patient;
import daos.AppointmentDAO;
import daos.ClinicDAO;
import daos.IllnessDAO;
import daos.MedicineDAO;
import daos.PatientDAO;
import database.DBHandler;
import exceptions.DataCannotBeEditedException;
import exceptions.LoadDataException;
import exceptions.SaveDataException;

public class IndividualAppDBH {

	AppointmentDAO appDAO;
	IllnessDAO illnessDAO;
	MedicineDAO drugDAO;
	ClinicDAO clinicDAO;
	PatientDAO patientDAO;

	private String illnessesString = "dane chorób";
	private String medicinesString = "dane leków";
	private String clinicsString = "dane poradni";
	private ArrayList<Medicine> medicines;
	private ArrayList<Illness> illnesses;
	private String constantIllnessesString = "dane chorób przewlek³ych pacjenta";

	public IndividualAppDBH() {
		illnessDAO = new IllnessDAO();
		drugDAO = new MedicineDAO();
		clinicDAO = new ClinicDAO();
		appDAO = new AppointmentDAO();
		patientDAO = new PatientDAO();
	}

	public ArrayList<Illness> getAllIllnesses() throws LoadDataException {
		if (illnesses != null)
			return illnesses;

		illnesses = new ArrayList<Illness>();
		try {
			illnesses = illnessDAO.getAllIllnesses();
		} catch (SQLException e) {

			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new LoadDataException(illnessesString);

			} else {
				
				DBHandler.incrementTrialsNo();
				illnesses = getAllIllnesses();
			}
		}

		DBHandler.resetTrialsNo();
		return illnesses;

	}

	public ArrayList<Medicine> getAllMedicines() throws LoadDataException {
		if (medicines != null)
			return medicines;

		medicines = new ArrayList<Medicine>();
		try {
			medicines = drugDAO.getAllMedicines();
		} catch (SQLException e) {
			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new LoadDataException(medicinesString);

			} else {
				
				DBHandler.incrementTrialsNo();
				medicines = getAllMedicines();
			}
		}

		DBHandler.resetTrialsNo();
		return medicines;
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
		System.out.println("REWRITE " + appointment);
		try {
			appDAO.writeBackOldStatus(appointment);
		} catch (SQLException e) {

			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new SaveDataException();
			} else {

				DBHandler.incrementTrialsNo();
				rewriteStatus(appointment);
			}

		}
		DBHandler.resetTrialsNo();
	}

	public void setPatientConstantIllnesses(Patient patient)
			throws LoadDataException {
		ArrayList<Illness> ill = new ArrayList<Illness>();
		try {
			ill = patientDAO.getPatientConstantIllnesses(patient.getId());
		} catch (SQLException e) {
			e.printStackTrace();

			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()) {
				DBHandler.resetTrialsNo();
				throw new LoadDataException(constantIllnessesString);

			} else {
				
				DBHandler.incrementTrialsNo();
				setPatientConstantIllnesses(patient);
				return;
			}
		}

		DBHandler.resetTrialsNo();
		patient.setConstantIllnesses(ill);
	}

}
