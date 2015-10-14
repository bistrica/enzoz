package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import people.Doctor;
import people.Employee;
import appointment.AppointmentController;
import exceptions.BadDataException;
import exceptions.ConnectionException;
import exceptions.LibraryException;

public class LoginController {

	private LoginView view;
	private LoginDBH model;
	// private DBHandler dbh;

	private String emptyFieldString = "Pola nie mog¹ byæ puste!",
			moduleNotImplementedString = "U¿ytkownik nie jest lekarzem. Modu³ nie zosta³ zaimplementowany.";

	public LoginController(LoginView lv, LoginDBH lm) {
		view = lv;
		model = lm;

		// TODO: skasowaæ liniê
		// loginTest();

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

				/*
				 * try { MessageDigest digest =
				 * MessageDigest.getInstance("SHA-512"); byte[] hash =
				 * digest.digest(password.getBytes("UTF-8"));
				 * 
				 * StringBuilder hexString = new StringBuilder(); for (int i :
				 * hash) { hexString.append(Integer.toHexString(0XFF & i)); }
				 * password = hexString.toString();
				 * System.out.println(hexString); } catch (Exception e1) {
				 * e1.printStackTrace(); // TODO: show dialog return; }
				 */

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

	}

	// TODO: skasowaæ

	public void loginTest() {

		Employee user;
		String login;
		String pass = login = "amis";
		try {
			user = model.tryToLog(login, pass);
		} catch (BadDataException | ConnectionException | LibraryException ex) {
			view.displayError(ex.getMessage());
			return;
		}
		view.setVisible(false);
		System.out.println("U: " + user);
		if (user instanceof Doctor)
			new AppointmentController((Doctor) user, this);
		else
			view.displayError(moduleNotImplementedString);

	}

	public void logOut() {
		view.clear();
		view.setVisible(true);
	}

}
