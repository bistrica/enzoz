package individualApp;

import items.Choroba;
import items.Konsultacja;
import items.PozycjaNaRecepcie;
import items.Recepta;
import items.Skierowanie;
import items.Wizyta;

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

	Wizyta appointment;

	IndividualAppDBH iam;
	IndividualAppView iav;
	private String errorString = "Wyst�pi� b��d";
	AppointmentController parent;
	boolean userAllowedToEdit;

	private String dataInUseString = "Dane w trakcie edycji";

	private String dataEditedMessageString = "Nie mo�na dokona� edycji wizyty, gdy� dane pacjenta s� w trakcie modyfikacji.";

	private String anotherWindowOpenMessageString = "Obecnie jest ju� otwarta wizyta. Zamknij wizyt�, aby m�c rozpocz�� kolejn�.";

	private String anotherWindowOpenString = "Wizyta otwarta";

	public IndividualAppController(AppointmentController parent, Wizyta app,
			boolean previewMode) throws PatientAlreadyBlockedException {
		this.appointment = app;
		this.parent = parent;

		iam = new IndividualAppDBH();

		// boolean isDoctorAbleToBlockPatient = (DBHandler.getUser()
		// .equals(appointment.getLekarz()));
		// TODO: is blocked?
		if (!app.isArchiveAppointment()) { // zablokuj, je�li jest obecn� now�
											// wizyt�
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
							appointment.getLekarz())
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

				iav.setInfo(appointment.getPacjent().getMainInfo(), appointment
						.getPacjent().getAddressInfo());

				if (appointment.isArchiveAppointment()) {
					iav.setInterview(appointment.getKonsultacja().getTresc());
					iav.setTemporaryIllnesses(appointment
							.getRozpoznaneChoroby());
					/*
					 * iav.setConstantIllnesses(appointment.getPacjent()
					 * .getChorobyPrzewlek�e());
					 */
					iav.setPrescription(appointment.getRecepta().getPozycje());
					iav.setExaminations(appointment.getSkierowania());
				} else {
					iav.setConstantIllnesses(appointment.getPacjent()
							.getChorobyPrzewlek�e());
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
		Wizyta newOrEditedApp = new Wizyta(appointment.getId(),
				appointment.getData(), appointment.getPacjent(),
				appointment.getLekarz());

		newOrEditedApp.setArchive(appointment.isArchiveAppointment());

		Konsultacja interview = new Konsultacja(iav.getInterview());
		if (isEdited && appointment.getKonsultacja().equals(interview))
			newOrEditedApp.setKonsultacja(null);
		else
			newOrEditedApp.setKonsultacja(interview);

		ArrayList<Skierowanie> examinations = iav.getExaminations(isEdited);
		newOrEditedApp.setSkierowania(examinations);

		ArrayList<PozycjaNaRecepcie> prescriptionData = null;
		try {
			prescriptionData = iav.getPrescriptionData(isEdited);
		} catch (WrongInputException e) {
			iav.displayInfo(e.getMessage(), errorString);
			e.printStackTrace();
			return;
		}

		Recepta prescription = (prescriptionData != null) ? new Recepta(
				prescriptionData) : null;
		newOrEditedApp.setRecepta(prescription);

		ArrayList<Choroba> tempIllnesses = iav.getCurrentIllnesses(isEdited);
		newOrEditedApp.setRozpoznaneChoroby(tempIllnesses);

		if (!isEdited) {
			ArrayList<Choroba> constIllnesses = iav
					.getConstantIllnesses(isEdited);
			newOrEditedApp.getPacjent().setChorobyPrzewlek�e(constIllnesses);
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
