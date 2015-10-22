package appointment;

import individualApp.IndividualAppController;
import items.Appointment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import login.LoginController;
import people.Doctor;
import people.Patient;
import database.DBHandler;
import exceptions.ArchiveException;
import exceptions.LoadDataException;
import exceptions.PatientAlreadyBlockedException;
import exceptions.PreviewCannotBeCreatedException;
import exceptions.TodayException;

public class AppointmentController {

	private boolean includeSearch, currentAppOpen;
	private AppointmentDBH am;
	private AppointmentView av;
	private Doctor doctor;
	private ArrayList<Appointment> appsInChildWindows, appointmentsToday,
			appointmentsArchive;

	private String[] todayColumnNames = { "Pacjent", "PESEL", "Godzina" };
	private String[] archiveColumnNames = { "Data", "Nazwisko pacjenta",
			"PESEL pacjenta", "Lekarz" };

	private long SLEEP_DURATION = 300000;
	private String currentAppOpenString = "Wizyta otwarta",
			currentAppOpenMessageString = "Obecnie jest ju¿ otwarta wizyta. Zamknij wizytê, aby mój rozpocz¹æ kolejn¹.",
			searchErrorString = "Wyst¹pi³ b³¹d przy wyszukiwaniu danych. Spróbuj póŸniej.",
			openAppsString = "Otwarte wizyty",
			cannotCloseString = "Nie mo¿na opuœciæ okna, poniewa¿ jest otwarte okno ze szczegó³ami wizyty. Zamknij okno wizyty i spróbuj ponownie.",
			errorString = "Wyst¹pi³ b³¹d",
			wrongDataTitleString = "B³êdne dane",
			wrongDataString = "WprowadŸ poprawne dane.";

	ArrayList<Doctor> doctors;

	private LoginController loginController;
	boolean refreshRunning;

	public AppointmentController(Doctor user, LoginController caller) {
		currentAppOpen = false;
		doctors = new ArrayList<Doctor>();
		doctor = user;
		loginController = caller;
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

				av = new AppointmentView(getDoctorSurnames(), doctor.getName());

				av.setVisible(true);

				if (error)
					av.displayInfo(errorMessage, errorString);

				setListeners();

			}
		});

		appsInChildWindows = new ArrayList<Appointment>();

		refreshRunning = true;
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				refreshDataInvoke();

				while (refreshRunning) {

					try {
						Thread.sleep(SLEEP_DURATION); // co 5 min
					} catch (InterruptedException e) {
						System.out.println("stop refreshing");
						refreshRunning = false;
						e.printStackTrace();
					}

					if (DBHandler.isClosed())
						break;
					refreshDataInvoke();

				}
			}
		});

		t.start();

	}

	private Doctor[] getDoctorSurnames() {
		Doctor[] surnames = new Doctor[doctors.size() + 1];
		surnames[0] = new Doctor(0, "", "", "");
		int i = 1;
		for (Doctor doctor : doctors)
			surnames[i++] = doctor;
		return surnames;

	}

	private void setListeners() {

		av.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				logout();
			}

		});

		av.setLogOutListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});

		av.setExitListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (av.sureToCloseWindow()) {
					if (appsInChildWindows.isEmpty())
						System.exit(0);
					else
						av.displayInfo(cannotCloseString, openAppsString);
				}
			}
		});

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
					av.setArchiveAppointments(archiveColumnNames,
							convertArchiveAppointments(), true);
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

	protected void logout() {
		if (av.sureToCloseWindow())
			if (appsInChildWindows.isEmpty()) {
				refreshRunning = false;
				av.dispose();
				loginController.logOut();

			} else
				av.displayInfo(cannotCloseString, openAppsString);
	}

	private void refreshDataInvoke() {

		getAppointments(true);
		getArchiveAppointments(true);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				av.setTodayAppointments(todayColumnNames, convertAppointments());
				av.setArchiveAppointments(archiveColumnNames,
						convertArchiveAppointments(), false);
			}
		});
	}

	private void refreshData() {

		getAppointments(false);
		getArchiveAppointments(false);
		av.setTodayAppointments(todayColumnNames, convertAppointments());
		av.setArchiveAppointments(archiveColumnNames,
				convertArchiveAppointments(), false);

	}

	private void getArchiveAppointments(boolean invoked) {
		ArrayList<Appointment> tempAppArchive = new ArrayList<Appointment>();
		try {
			tempAppArchive = am.getArchiveAppointments(includeSearch);
		} catch (ArchiveException e) {
			if (invoked) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						av.displayInfo(e.getMessage(), errorString);
					}
				});
			} else
				av.displayInfo(e.getMessage(), errorString);
		}
		appointmentsArchive = tempAppArchive;
	}

	private void createAppointment(Appointment app, boolean editable) {
		System.out.println("CURR " + currentAppOpen);
		if (!appsInChildWindows.contains(app)) {
			appsInChildWindows.add(app);
			if (!app.isArchiveAppointment())
				currentAppOpen = true;
			try {
				new IndividualAppController(this, app, editable);
			} catch (PatientAlreadyBlockedException e) {
				appsInChildWindows.remove(app);
				if (!app.isArchiveAppointment())
					currentAppOpen = false;
				av.displayInfo(e.getMessage(), errorString);
			}
		}
	}

	private void tryToCreateArchiveAppointmentPreview(Appointment app) {

		try {

			am.openPreview(app);
			boolean editable = false;
			createAppointment(app, editable);
		} catch (PreviewCannotBeCreatedException e) {
			av.displayInfo(e.getMessage(), errorString);
		}

	}

	private Object[][] convertAppointments() {
		Object[][] converted = new Object[appointmentsToday.size()][todayColumnNames.length];
		int i = 0;
		for (Appointment app : appointmentsToday) {
			Patient patient = app.getPatient();
			converted[i][0] = patient.nameToString();
			converted[i][1] = patient.getPESEL();
			converted[i][2] = app.getHourToString(); //
			i++;
		}
		return converted;
	}

	private Object[][] convertArchiveAppointments() {
		Object[][] data = new Object[appointmentsArchive.size()][4];
		int i = 0;
		for (Appointment app : appointmentsArchive) {
			data[i][0] = app.getDataToString();
			data[i][1] = app.getPatient().getName();
			data[i][2] = app.getPatient().getPESEL();
			data[i++][3] = app.getDoctor().getName();
		}
		return data;
	}

	private void getAppointments(boolean invoked) {
		ArrayList<Appointment> tempAppToday = new ArrayList<Appointment>();
		try {
			tempAppToday = am.getTodayAppointments(doctor);
		} catch (TodayException e) {
			if (invoked) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						av.displayInfo(e.getMessage(), errorString);
					}
				});
			} else
				av.displayInfo(e.getMessage(), errorString);
		}
		appointmentsToday = tempAppToday;
	}

	public void removeChildWindow(Appointment childWindowApp) {
		refreshData();
		appsInChildWindows.remove(childWindowApp);

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

}
