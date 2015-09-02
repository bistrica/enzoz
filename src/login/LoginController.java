package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import appointment.AppointmentController;
import exceptions.BadDataException;
import exceptions.ConnectionException;
import people.*;

public class LoginController {

	
	private LoginView view;
	private LoginDBH model;
	
	private String emptyFieldString="Pola nie mog� by� puste!", moduleNotImplementedString="U�ytkownik nie jest lekarzem. Modu� nie zosta� zaimplementowany.";

	public LoginController(LoginView lv, LoginDBH lm) {
		view=lv;
		model=lm;
		view.setLoginListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String pass=view.getPassword();
				String login=view.getLogin();
				pass=pass.trim();
				login=login.trim();
				if (pass.isEmpty() || login.isEmpty()){
					lv.displayError(emptyFieldString);
					return;
				}
				
				Pracownik user=null;
				
				try {
					user=model.tryToLog(login,pass);
				}
				catch (BadDataException | ConnectionException ex) {
					view.displayError(ex.getMessage());
					return;
				}
				view.setVisible(false);
				System.out.println("U: "+user);
				if (user instanceof Lekarz)
					new AppointmentController(user);
				else
					view.displayError(moduleNotImplementedString);
				
				
			}
		}); 
		
	}
	
	
	
}
