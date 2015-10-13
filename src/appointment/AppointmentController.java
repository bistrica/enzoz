package appointment;

import individualApp.IndividualAppController;
import items.Wizyta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import people.Lekarz;
import people.Pacjent;
import database.DBHandler;
import exceptions.ArchiveException;
import exceptions.LoadDataException;
import exceptions.PatientAlreadyBlockedException;
import exceptions.PreviewCannotBeCreatedException;
import exceptions.TodayException;

public class AppointmentController {

	boolean includeSearch;
	AppointmentDBH am;
	AppointmentView av;
	Lekarz doctor;
	ArrayList<Wizyta> appsInChildWindows;

	String[] colNames = { "Pacjent", "PESEL", "Godzina" };
	ArrayList<Wizyta> appointmentsToday;
	ArrayList<Wizyta> appointmentsArchive;

	String[] columnNames = { "Data", "Pacjent", "Lekarz" };
	// private String notEditableString =
	// "Nie mo¿na w tej chwili zobaczyæ wizyty. Wizyta jest edytowana przez innego u¿ytkownika.";
	// private String titleBarString = "Wizyta w trakcie edycji";
	private String errorString = "Wyst¹pi³ b³¹d";
	private String wrongDataTitleString = "B³êdne dane";
	private String wrongDataString = "WprowadŸ poprawne dane.";

	protected long SLEEP_DURATION = 300000;// 6000;

	boolean currentAppOpen;
	protected String currentAppOpenString = "Wizyta otwarta";
	protected String currentAppOpenMessageString = "Obecnie jest ju¿ otwarta wizyta. Zamknij wizytê, aby mój rozpocz¹æ kolejn¹.";

	ArrayList<Lekarz> doctors;
	protected String searchErrorString = "Wyst¹pi³ b³¹d przy wyszukiwaniu danych. Spróbuj póŸniej.";

	/*
	 * public AppointmentController(String login) { // TODO Auto-generated
	 * constructor stub }
	 */

	public AppointmentController(Lekarz user) {
		currentAppOpen = false;
		doctors = new ArrayList<Lekarz>();
		doctor = user;
		am = new AppointmentDBH();

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				boolean error = false;
				String errorMessage = "";

				try {
					doctors = am.getDoctors();
				} catch (LoadDataException e) {
					e.printStackTrace();
					error = true;
					errorMessage = e.getMessage();
				}

				av = new AppointmentView(getDoctorSurnames());

				av.setVisible(true);

				if (error)
					av.displayInfo(errorMessage, errorString);

				refreshData();

				setListeners();

				// TODO: odkomentowaæ

				// TODO:usun¹æ
				// createAppointment(appointments.get(0));
			}
		});

		appsInChildWindows = new ArrayList<Wizyta>();

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				boolean running = true;
				while (running) {

					if (DBHandler.isClosed())
						break;

					try {
						Thread.sleep(SLEEP_DURATION); // co 5 min
					} catch (InterruptedException e) {
						System.out.println("stop refreshing");
						running = false;
						e.printStackTrace();
					}

					// if (iav.)
					refreshData();

					System.out.println("refreshed");
				}
			}
		});

		t.start();

	}

	private Lekarz[] getDoctorSurnames() {
		Lekarz[] surnames = new Lekarz[doctors.size() + 1];
		surnames[0] = new Lekarz(0, "", "", "");
		int i = 1;
		for (Lekarz doctor : doctors)
			surnames[i++] = doctor;// doctor.getImie() + " " +
									// doctor.getNazwisko();
		return surnames;
		// av.setDoctorsSurnames(surnames);
	}

	private void setListeners() {

		av.setSearchListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!av.validateSearchParameters()) {
					av.displayInfo(wrongDataString, wrongDataTitleString);
					return;
				}

				includeSearch = !av.emptySearchParameters();

				try {
					appointmentsArchive = includeSearch ? am.searchData(av
							.getSearchData()) : am
							.getArchiveAppointments(includeSearch);
					av.setArchiveAppointments(columnNames,
							convertArchiveAppointments());
				} catch (ArchiveException e1) {
					av.displayInfo(searchErrorString, errorString);
					e1.printStackTrace();
				}

			}
		});

		av.setTodayListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = av.getSelectedAppIndex();
				if (index != -1) {
					if (currentAppOpen) {
						av.displayInfo(currentAppOpenMessageString,
								currentAppOpenString);
						return;
					}

					// currentAppOpen = true;
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

		av.setRefreshListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				includeSearch = false;
				av.resetSearchResults();
				refreshData();
			}
		});
	}

	private void refreshData() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				getAppointments();
				getArchiveAppointments();
				av.setTodayAppointments(colNames, convertAppointments());
				av.setArchiveAppointments(columnNames,
						convertArchiveAppointments());
			}
		});
	}

	private void getArchiveAppointments() {
		ArrayList<Wizyta> tempAppArchive = new ArrayList<Wizyta>();
		// appointmentsArchive = new ArrayList<Wizyta>();
		try {
			tempAppArchive = am.getArchiveAppointments(includeSearch);
			// appointmentsArchive = am.getArchiveAppointments();
		} catch (ArchiveException e) {
			av.displayInfo(e.getMessage(), errorString);
		}
		appointmentsArchive = tempAppArchive;
	}

	private void createAppointment(Wizyta app, boolean editable) {
		// System.out.println("Nowa: " + app.toString());
		// System.out.println("CONTAINS " + appsInChildWindows.contains(app));
		System.out.println("CURR " + currentAppOpen);
		if (!appsInChildWindows.contains(app)) {
			appsInChildWindows.add(app);
			if (!app.isArchiveAppointment())
				currentAppOpen = true;
			try {
				new IndividualAppController(this, app, editable);
			} catch (PatientAlreadyBlockedException e) {
				// e.printStackTrace();
				appsInChildWindows.remove(app);
				if (!app.isArchiveAppointment())
					currentAppOpen = false;
				av.displayInfo(e.getMessage(), errorString);
			}
		}
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

			// if (am.openPreviewIfPossible(app)) {
			am.openPreview(app);
			boolean editable = false;
			createAppointment(app, editable);
			// } else
			// av.displayInfo(notEditableString, titleBarString);
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

	public void removeChildWindow(Wizyta childWindowApp) {
		// if (!childWindowApp.isArchiveAppointment())
		// currentAppOpen = false;
		refreshData();
		// System.out.println("SIZE PRE " + appsInChildWindows.size());
		appsInChildWindows.remove(childWindowApp);
		// System.out.println("SIZE AFTER " + appsInChildWindows.size());
	}

	public boolean isCurrentAppOpen() {
		return currentAppOpen;
	}

	public void setCurrentAppOpen(boolean curr) {
		currentAppOpen = curr;
	}

	public void displayError(String message) {
		av.displayInfo(message, errorString);
	}

	/*
	 * public void setArchiveAppOpen(boolean b) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 */

}
