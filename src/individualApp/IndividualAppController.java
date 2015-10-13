package individualApp;

import items.Illness;
import items.Interview;
import items.PrescriptedItem;
import items.Prescription;
import items.Examination;
import items.Appointment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import appointment.AppointmentController;
import database.DBHandler;
import exceptions.DataCannotBeEditedException;
import exceptions.LoadDataException;
import exceptions.PatientAlreadyBlockedException;
import exceptions.SaveDataException;
import exceptions.WrongInputException;

public class IndividualAppController {

	Appointment appointment;

	IndividualAppDBH iam;
	IndividualAppView iav;
	private String errorString = "Wyst¹pi³ b³¹d";
	AppointmentController parent;
	boolean userAllowedToEdit;

	private String dataInUseString = "Dane w trakcie edycji";

	private String dataEditedMessageString = "Nie mo¿na dokonaæ edycji wizyty, gdy¿ dane pacjenta s¹ w trakcie modyfikacji.";

	private String anotherWindowOpenMessageString = "Obecnie jest ju¿ otwarta wizyta. Zamknij wizytê, aby móc rozpocz¹æ kolejn¹.";

	private String anotherWindowOpenString = "Wizyta otwarta";

	public IndividualAppController(AppointmentController parent, Appointment app,
			boolean previewMode) throws PatientAlreadyBlockedException {
		this.appointment = app;
		this.parent = parent;

		iam = new IndividualAppDBH();

		// boolean isDoctorAbleToBlockPatient = (DBHandler.getUser()
		// .equals(appointment.getLekarz()));
		// TODO: is blocked?
		if (!app.isArchiveAppointment()) { // zablokuj, jeœli jest obecn¹ now¹
											// wizyt¹
			try {
				if (!iam.isPossibleToEdit(appointment)) {// tryToBlockPatientForEdit(appointment))
															// {
					throw new PatientAlreadyBlockedException();
				}
			} catch (DataCannotBeEditedException e) {
				parent.displayError(e.getMessage());
				e.printStackTrace();
			}
			// return;
		}

		// java.awt.EventQueue.
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				//

				userAllowedToEdit = false;
				try {
					userAllowedToEdit = DBHandler.getCurrentUser().equals(
							appointment.getDoctor())
							&& !parent.isCurrentAppOpen();
					System.out.println("US " + userAllowedToEdit);
					// if (userAllowedToEdit && parent.isCurrentAppOpen())
					// userAllowedToEdit=false;
					// System.out.println("ALLOWED USER? " + userAllowedToEdit);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				iav = new IndividualAppView(previewMode, userAllowedToEdit,
						appointment.isArchiveAppointment());

				try {
					iav.setIllnessesList(iam.getAllIllnesses());
					iav.setAllMedicinesList(iam.getAllMedicines());
					iav.setClinicsList(iam.getAllClinics());
				} catch (LoadDataException e) {

					e.printStackTrace();
					iav.displayInfo(e.getMessage(), errorString);
					return;
				}

				iav.setInfo(appointment.getPatient().getMainInfo(), appointment
						.getPatient().getAddressInfo());

				if (appointment.isArchiveAppointment()) {
					iav.setInterview(appointment.getInterview().getContent());
					iav.setTemporaryIllnesses(appointment
							.getRecognizedIllnesses());
					/*
					 * iav.setConstantIllnesses(appointment.getPacjent()
					 * .getChorobyPrzewlek³e());
					 */
					iav.setPrescription(appointment.getPrescription().getPozycje());
					iav.setExaminations(appointment.getExaminations());
				} else {
					iav.setConstantIllnesses(appointment.getPatient()
							.getChorobyPrzewlek³e());
				}
				iav.setComponentsState();

				setListeners();
				iav.setVisible(true);

			}

		});

	}

	private void editMode() {

		if (parent.isCurrentAppOpen()) {
			iav.displayInfo(anotherWindowOpenMessageString,
					anotherWindowOpenString);
			return;
		}

		try {
			if (iam.isPossibleToEdit(appointment)) {
				iav.setEditMode();
				parent.setCurrentAppOpen(true);
			} else
				iav.displayInfo(dataEditedMessageString, dataInUseString);
		} catch (DataCannotBeEditedException e) {
			iav.displayInfo(e.getMessage(), errorString);
		}

	}

	private void setListeners() {

		iav.setWindowCloseListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				if (iav.sureToCloseWindow()) {
					System.out.println("REMM");
					if (iav.editMode) {

						try {
							iam.rewriteStatus(appointment);
						} catch (SaveDataException e1) {
							e1.printStackTrace();
							iav.displayInfo(e1.getMessage(), errorString);
						}
					}

					if (iav.editMode || !appointment.isArchiveAppointment())
						parent.setCurrentAppOpen(false);

					parent.removeChildWindow(appointment);
					iav.close();
				}
			}
		});

		iav.setSaveItemListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (iav.sureToSaveChanges()) {
					saveChanges();
					parent.setCurrentAppOpen(false);
					parent.removeChildWindow(appointment);
					iav.close();
				}
			}
		});

		if (userAllowedToEdit && appointment.isArchiveAppointment()) {

			iav.setEditItemListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					editMode();
				}

			});

		}

	}

	protected void saveChanges() {
		boolean isEdited = appointment.isArchiveAppointment();

		// TODO: potwierdzenie zapisu zmian
		Appointment newOrEditedApp = new Appointment(appointment.getId(),
				appointment.getDate(), appointment.getPatient(),
				appointment.getDoctor());

		newOrEditedApp.setArchive(appointment.isArchiveAppointment());

		Interview interview = new Interview(iav.getInterview());
		if (isEdited && appointment.getInterview().equals(interview))
			newOrEditedApp.setInterview(null);
		else
			newOrEditedApp.setInterview(interview);

		ArrayList<Examination> examinations = iav.getExaminations(isEdited);
		newOrEditedApp.setExaminations(examinations);

		ArrayList<PrescriptedItem> prescriptionData = null;
		try {
			prescriptionData = iav.getPrescriptionData(isEdited);
		} catch (WrongInputException e) {
			iav.displayInfo(e.getMessage(), errorString);
			e.printStackTrace();
			return;
		}

		Prescription prescription = (prescriptionData != null) ? new Prescription(
				prescriptionData) : null;
		newOrEditedApp.setPrescription(prescription);

		ArrayList<Illness> tempIllnesses = iav.getCurrentIllnesses(isEdited);
		newOrEditedApp.setRecognizedIllnesses(tempIllnesses);

		if (!isEdited) {
			ArrayList<Illness> constIllnesses = iav
					.getConstantIllnesses(isEdited);
			newOrEditedApp.getPatient().setChorobyPrzewlek³e(constIllnesses);
		}
		try {
			iam.saveAppointment(newOrEditedApp);
		} catch (SaveDataException e) {
			iav.displayInfo(e.getMessage(), errorString);
			e.printStackTrace();
			return;
		}

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndividualAppController other = (IndividualAppController) obj;
		if (appointment == null) {
			if (other.appointment != null)
				return false;
		} else if (!appointment.equals(other.appointment))
			return false;
		return true;
	}

}
