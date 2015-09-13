package individualApp;

import items.Wizyta;
import exceptions.LoadDataException;

public class IndividualAppController {

	Wizyta appointment;

	IndividualAppDBH iam;
	IndividualAppView iav;
	private String errorString = "Wyst¹pi³ b³¹d";

	public IndividualAppController(Wizyta app, boolean isEditable) {
		this.appointment = app;

		iam = new IndividualAppDBH();
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				iav = new IndividualAppView(isEditable);

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
					iav.setConstantIllnesses(appointment.getPacjent()
							.getChorobyPrzewlek³e());
					iav.setPrescription(appointment.getRecepta().getPozycje());
					iav.setExaminations(appointment.getSkierowania());
				}

				iav.setComponentsState();
				iav.setVisible(true);

			}
		});

	}

}
