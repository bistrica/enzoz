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

	private String illnessesString = "dane chor�b";
	private String medicinesString = "dane lek�w";
	private String clinicsString = "dane poradni";
	private static ArrayList<Medicine> medicines;
	private static ArrayList<Illness> illnesses;
	private static ArrayList<Clinic> clinics;
	private String constantIllnessesString = "dane chor�b przewlek�ych pacjenta";

	public IndividualAppDBH() {
		illnessDAO = new IllnessDAO();
		drugDAO = new MedicineDAO();
		clinicDAO = new ClinicDAO();
		appDAO = new AppointmentDAO();
		patientDAO = new PatientDAO();
	}

	public ArrayList<Illness> getAllIllnesses() throws LoadDataException {
		if (illnesses != null) {
			System.out.println("static");
			return illnesses;
		}

		ArrayList<Illness> ill = new ArrayList<Illness>();
		try {
			ill = illnessDAO.getAllIllnesses();
		} catch (SQLException e) {

			// e.printStackTrace();

			if (!DBHandler.reconnect()) {// ) {
				//
				throw new LoadDataException(illnessesString);

			} else {
				//
				ill = getAllIllnesses();
			}
		}

		//
		illnesses = ill;
		return illnesses;

	}

	public ArrayList<Medicine> getAllMedicines() throws LoadDataException {
		if (medicines != null)
			return medicines;

		ArrayList<Medicine> med = new ArrayList<Medicine>();
		try {
			med = drugDAO.getAllMedicines();
		} catch (SQLException e) {
			// e.printStackTrace();

			if (!DBHandler.reconnect()) {// ) {
				//
				throw new LoadDataException(medicinesString);

			} else {

				//
				med = getAllMedicines();
			}
		}

		medicines = med;
		return medicines;
	}

	public ArrayList<Clinic> getAllClinics() throws LoadDataException {
		if (clinics != null)
			return clinics;

		ArrayList<Clinic> cli = new ArrayList<Clinic>();
		try {
			cli = clinicDAO.getAllClinics();
		} catch (SQLException e) {
			e.printStackTrace();

			if (!DBHandler.reconnect()) {

				throw new LoadDataException(clinicsString);

			} else {

				cli = getAllClinics();
			}
		}

		clinics = cli;
		return clinics;
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

			// e.printStackTrace();

			if (!DBHandler.reconnect()) {

				throw new DataCannotBeEditedException();
			} else {
				System.out.println("ONCE MORE");

				isPossible = isPossibleToEdit(app);
			}

		}

		return isPossible;
	}

	public void saveAppointment(Appointment app) throws SaveDataException {

		try {
			appDAO.writeToDatabase(app);
		} catch (SQLException e) {// SQLException e) {

			// e.printStackTrace();
			System.out.println("save " + e.getMessage());
			e.printStackTrace();

			if (!DBHandler.reconnect()) {
				System.out.println("XXX");

				throw new SaveDataException();

			} else {
				System.out.println("ONCE MORE ");

				saveAppointment(app);
				return;
			}

		}
		// sysout

	}

	public boolean tryToBlockPatientForEdit(Appointment appointment) {

		System.out.println("TRY TO BLOCK");
		boolean isBlocked = false;
		try {
			isBlocked = appDAO.blockPatientData(appointment.getPatient()
					.getId());
		} catch (SQLException e) {
			// e.printStackTrace();
		}
		return isBlocked;
	}

	public void rewriteStatus(Appointment appointment) throws SaveDataException {
		System.out.println("REWRITE " + appointment);
		try {
			appDAO.writeBackOldStatus(appointment);
		} catch (SQLException e) {

			// e.printStackTrace();

			if (!DBHandler.reconnect()) {

				throw new SaveDataException();
			} else {

				rewriteStatus(appointment);
			}

		}

	}

	public void setPatientConstantIllnesses(Patient patient)
			throws LoadDataException {
		ArrayList<Illness> ill = new ArrayList<Illness>();
		try {
			ill = patientDAO.getPatientConstantIllnesses(patient.getId());
		} catch (SQLException e) {
			// e.printStackTrace();

			if (!DBHandler.reconnect()) {

				throw new LoadDataException(constantIllnessesString);

			} else {

				setPatientConstantIllnesses(patient);
				return;
			}
		}

		patient.setConstantIllnesses(ill);
	}

	// public void setPatientAddress(Patient patient) throws LoadDataException {
	// ArrayList<Illness> ill = new ArrayList<Illness>();
	// try {
	// ill = patientDAO.getPatientConstantIllnesses(patient.getId());
	// } catch (SQLException e) {
	// e.printStackTrace();
	//
	// if (!DBHandler.reconnect() ) {
	//
	// throw new LoadDataException(constantIllnessesString);
	//
	// } else {
	//
	//
	// setPatientConstantIllnesses(patient);
	// return;
	// }
	// }
	//
	//
	// patient.setConstantIllnesses(ill);
	//
	// }

}
