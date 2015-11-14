package individualApp;

import items.Appointment;
import items.Examination;
import items.Illness;
import items.Interview;
import items.PrescriptedItem;
import items.Prescription;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import appointment.AppointmentController;
import database.DBHandler;
import exceptions.BadDataException;
import exceptions.DataCannotBeEditedException;
import exceptions.LoadDataException;
import exceptions.PatientAlreadyBlockedException;
import exceptions.SaveDataException;

public class IndividualAppController {

	private Appointment appointment;

	private IndividualAppDBH iam;
	private IndividualAppView iav;
	private String errorString = "Wyst¹pi³ b³¹d",
			dataInUseString = "Dane w trakcie edycji",
			dataEditedMessageString = "Nie mo¿na dokonaæ edycji wizyty, gdy¿ dane pacjenta s¹ w trakcie modyfikacji.",
			anotherWindowOpenMessageString = "Obecnie jest ju¿ otwarta wizyta. Zamknij wizytê, aby móc rozpocz¹æ kolejn¹.",
			anotherWindowOpenString = "Wizyta otwarta",
			lackingInterviewString = "Treœæ konsultacji nie mo¿e byæ pusta.",
			notEnoughDataString = "Brak danych",
			lackingDescriptionString = "Treœæ skierowania nie mo¿e byæ pusta.",
			dataSavedString = "Zmiany zosta³y zapisane.",
			confirmString = "Potwierdzenie";

	private AppointmentController parent;
	private boolean userAllowedToEdit;

	public IndividualAppController(AppointmentController parent,
			Appointment app, boolean previewMode)
			throws PatientAlreadyBlockedException {
		this.appointment = app;
		this.parent = parent;

		iam = new IndividualAppDBH();

		if (!app.isArchiveAppointment()) { // zablokuj, jeœli jest obecn¹ now¹
											// wizyt¹
			try {

				if (!iam.isPossibleToEdit(appointment)) {
					throw new PatientAlreadyBlockedException();
				}
			} catch (DataCannotBeEditedException e) {
				parent.displayError(e.getMessage());
				// e.printStackTrace();
				return;
			}

		}

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				userAllowedToEdit = false;
				try {
					userAllowedToEdit = DBHandler.getCurrentUser().equals(
							appointment.getDoctor())
							&& !parent.isCurrentAppOpen();
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

					// e.printStackTrace();
					iav.displayInfo(e.getMessage(), errorString);
					System.exit(0);
					// return;
				}

				/*
				 * try { iam.setPatientAddress(appointment.getPatient()); }
				 * catch (LoadDataException e) { e.printStackTrace();
				 * iav.displayInfo(e.getMessage(), errorString); }
				 */

				iav.setInfo(appointment.getPatient().getMainInfo(), appointment
						.getPatient().getAddressInfo(), appointment
						.getDataToString());

				if (appointment.isArchiveAppointment()) {
					iav.setInterview(appointment.getInterview().getContent());
					iav.setTemporaryIllnesses(appointment
							.getRecognizedIllnesses());

					iav.setPrescription(appointment.getPrescription()
							.getPozycje());
					iav.setExaminations(appointment.getExaminations());
				} else {
					try {
						iam.setPatientConstantIllnesses(appointment
								.getPatient());
					} catch (LoadDataException e) {
						// e.printStackTrace();
						iav.displayInfo(e.getMessage(), errorString);
						System.exit(0);
						// return;
					}
					iav.setConstantIllnesses(appointment.getPatient()
							.getConstantIllnesses());
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
					if (iav.getEditMode()) {

						try {
							iam.rewriteStatus(appointment);

						} catch (SaveDataException e1) {
							// e1.printStackTrace();
							iav.displayInfo(e1.getMessage(), errorString);
							System.exit(0);
						}
					}

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
					if (saveChanges()) {
						parent.setCurrentAppOpen(false);
						parent.removeChildWindow(appointment);
						iav.displayInfo(dataSavedString, confirmString);
						iav.close();
					}
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

	protected boolean saveChanges() {
		boolean isEdited = appointment.isArchiveAppointment();

		Appointment newOrEditedApp = new Appointment(appointment.getId(),
				appointment.getDate(), appointment.getPatient(),
				appointment.getDoctor());

		newOrEditedApp.setArchive(appointment.isArchiveAppointment());

		String intvw = iav.getInterview();
		if (intvw.isEmpty()) {
			iav.displayInfo(lackingInterviewString, notEnoughDataString);
			return false;
		}
		Interview interview = new Interview(intvw);

		if (isEdited && appointment.getInterview().equals(interview))
			newOrEditedApp.setInterview(null);
		else
			newOrEditedApp.setInterview(interview);

		ArrayList<Examination> examinations = iav.getExaminations(isEdited);
		if (examinations != null)
			for (Examination exam : examinations)
				if (exam.getDescription().isEmpty()) {
					iav.displayInfo(lackingDescriptionString,
							notEnoughDataString);
					return false;
				}

		if ((appointment.getExaminations() == null || appointment
				.getExaminations().size() == 0) && examinations.isEmpty())
			examinations = null;

		newOrEditedApp.setExaminations(examinations);

		ArrayList<PrescriptedItem> prescriptionData = null;
		try {
			prescriptionData = iav.getPrescriptionData(isEdited);
		} catch (BadDataException e) {
			iav.displayInfo(e.getMessage(), errorString);
			// e.printStackTrace();
			return false;
		}

		Prescription prescription = (prescriptionData != null) ? new Prescription(
				prescriptionData) : null;
		newOrEditedApp.setPrescription(prescription);

		ArrayList<Illness> tempIllnesses = iav.getCurrentIllnesses(isEdited);
		newOrEditedApp.setRecognizedIllnesses(tempIllnesses);

		if (!isEdited) {
			ArrayList<Illness> constIllnesses = iav
					.getConstantIllnesses(isEdited);
			newOrEditedApp.getPatient().setConstantIllnesses(constIllnesses);
		}
		try {
			iam.saveAppointment(newOrEditedApp);
		} catch (SaveDataException e) {
			iav.displayInfo(e.getMessage(), errorString);
			System.exit(0);
			// e.printStackTrace();
			return false;
		}

		return true;
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
