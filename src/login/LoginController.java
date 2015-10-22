package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import people.Doctor;
import people.Employee;
import appointment.AppointmentController;
import database.DBHandler;
import exceptions.BadDataException;
import exceptions.ConnectionException;
import exceptions.LibraryException;

public class LoginController {

	private LoginView view;
	private LoginDBH model;

	private String emptyFieldString = "Pola nie mog¹ byæ puste!",
			moduleNotImplementedString = "U¿ytkownik nie jest lekarzem. Modu³ nie zosta³ zaimplementowany.";

	public LoginController(LoginView lv, LoginDBH lm) {
		view = lv;
		model = lm;

		LoginController caller = this;

		view.setLoginListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				char[] pass = view.getPassword();
				String password = new String(pass);
				String login = view.getLogin();
				password = password.trim();
				login = login.trim();
				if (password.isEmpty() || login.isEmpty()) {
					lv.displayError(emptyFieldString);
					return;
				}

				Employee user = null;

				try {
					user = model.tryToLog(login, password);
				} catch (BadDataException | ConnectionException
						| LibraryException ex) {
					view.displayError(ex.getMessage());
					return;
				}
				view.setVisible(false);
				System.out.println("U: " + user);
				if (user instanceof Doctor)
					new AppointmentController((Doctor) user, caller);
				else
					view.displayError(moduleNotImplementedString);

			}
		});

		view.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				if (!DBHandler.isClosed()) {
					System.out.println("CLOSE");
					DBHandler.close();
				}
			}

		});

	}

	/*
	 * public void loginTest() {
	 * 
	 * Employee user; String login; String pass = login = "adolega"; try { user
	 * = model.tryToLog(login, pass); } catch (BadDataException |
	 * ConnectionException | LibraryException ex) {
	 * view.displayError(ex.getMessage()); return; } view.setVisible(false);
	 * System.out.println("U: " + user); if (user instanceof Doctor) new
	 * AppointmentController((Doctor) user, this); else
	 * view.displayError(moduleNotImplementedString);
	 * 
	 * }
	 */

	public void logOut() {
		view.clear();
		view.setVisible(true);
	}

}
