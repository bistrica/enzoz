package appointment;

import individualApp.IndividualAppController;
import items.Wizyta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import people.Lekarz;
import people.Pacjent;
import exceptions.ArchiveException;
import exceptions.PreviewCannotBeCreatedException;
import exceptions.TodayException;

public class AppointmentController {

	AppointmentDBH am;
	AppointmentView av;
	Lekarz doctor;

	String[] colNames = { "Pacjent", "PESEL", "Godzina" };
	ArrayList<Wizyta> appointmentsToday;
	ArrayList<Wizyta> appointmentsArchive;

	String[] columnNames = { "Data", "Pacjent", "Lekarz" };
	private String notEditableString = "Nie mo¿na w tej chwili zobaczyæ wizyty. Wizyta jest edytowana przez innego u¿ytkownika.";
	private String titleBarString = "Wizyta w trakcie edycji";
	private String errorString = "Wyst¹pi³ b³¹d";

	/*
	 * public AppointmentController(String login) { // TODO Auto-generated
	 * constructor stub }
	 */

	public AppointmentController(Lekarz user) {
		doctor = user;
		am = new AppointmentDBH();
		av = new AppointmentView();

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				boolean running = true;
				while (running) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println("stop refreshing");
						running = false;
						e.printStackTrace();
					}
					refreshData();

					System.out.println("refreshed");
				}
			}
		});

		refreshData();
		t.start();

		av.setOpenButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = av.getSelectedAppIndex();
				if (index != -1) {
					boolean editable = true;
					createAppointment(appointmentsToday.get(index), editable);
				}
			}

		});
		av.setArchiveListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = av.getSelectedArchiveAppIndex();
				if (index != -1) {
					tryToCreateArchiveAppointmentPreview(appointmentsArchive
							.get(index));
				}

			}

		});

		// TODO: odkomentowaæ
		av.setVisible(true);

		// TODO:usun¹æ
		// createAppointment(appointments.get(0));
	}

	private void refreshData() {
		getAppointments();
		getArchiveAppointments();
		av.setAppointments(colNames, convertAppointments());
		av.setArchiveAppointments(columnNames, convertArchiveAppointments());
	}

	private void getArchiveAppointments() {
		ArrayList<Wizyta> tempAppArchive = new ArrayList<Wizyta>();
		// appointmentsArchive = new ArrayList<Wizyta>();
		try {
			tempAppArchive = am.getArchiveAppointments();
			// appointmentsArchive = am.getArchiveAppointments();
		} catch (ArchiveException e) {
			av.displayInfo(e.getMessage(), errorString);
		}
		appointmentsArchive = tempAppArchive;
	}

	private void createAppointment(Wizyta app, boolean editable) {
		System.out.println("Nowa: " + app.toString());

		new IndividualAppController(app, editable);
	}

	private void tryToCreateArchiveAppointmentPreview(Wizyta app) {
		/*
		 * boolean allowed=false; try { allowed = am.statusAllowsEditing(app); }
		 * catch (SQLException e) { throw new PreviewCannotBeCreatedException();
		 * }
		 * 
		 * if (allowed){ try { am.updateAppData(app); boolean editable=false;
		 * createAppointment(app, editable); } catch(SQLException e) {
		 * 
		 * } } else av.displayInfo(notEditableString, titleBarString);
		 */

		try {

			// if (am.statusAllowsEditing(app)) {
			// am.updateAppData(app);
			if (am.openPreviewIfPossible(app)) {
				boolean editable = false;
				createAppointment(app, editable);
			} else
				av.displayInfo(notEditableString, titleBarString);
		} catch (PreviewCannotBeCreatedException e) {
			av.displayInfo(e.getMessage(), errorString);
		}

	}

	/*
	 * private String[] getColumnsNames() { return colNames;
	 * 
	 * }
	 */

	private Object[][] convertAppointments() {
		Object[][] converted = new Object[appointmentsToday.size()][colNames.length];
		int i = 0;
		for (Wizyta app : appointmentsToday) {
			Pacjent patient = app.getPacjent();
			converted[i][0] = patient.nameToString();
			converted[i][1] = patient.getPESEL();
			converted[i][2] = app.getHourToString(); //
			i++;
		}
		return converted;
	}

	private Object[][] convertArchiveAppointments() {
		Object[][] data = new Object[appointmentsArchive.size()][3];
		int i = 0;
		for (Wizyta app : appointmentsArchive) {
			data[i][0] = app.getDataToString();
			data[i][1] = app.getPacjent().getMainInfo();
			data[i++][2] = app.getLekarz().getName();
		}
		return data;
	}

	private void getAppointments() {
		ArrayList<Wizyta> tempAppToday = new ArrayList<Wizyta>();
		try {
			tempAppToday = am.getTodayAppointments(doctor);
		} catch (TodayException e) {
			av.displayInfo(e.getMessage(), errorString);
		}
		appointmentsToday = tempAppToday;
	}

}
