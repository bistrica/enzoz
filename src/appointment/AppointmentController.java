package appointment;

import items.Wizyta;
import people.Lekarz;
import people.Pacjent;
import people.Pracownik;

public class AppointmentController {

	
	AppointmentDBH am;
	AppointmentView av;
	Lekarz doctor;
	
	String[] colNames={"Pacjent", "PESEL", "Godzina"};
	Wizyta[] appointments;
	/*public AppointmentController(String login) {
		// TODO Auto-generated constructor stub
	}*/

	public AppointmentController(Pracownik user) {
		doctor=(Lekarz) user;
		am=new AppointmentDBH();
		av=new AppointmentView();
		getAppointments();
		av.setAppointments(getColumnsNames(), convertAppointments());//dodajPrzycisk();
		av.setVisible(true);
	}

	private String[] getColumnsNames() {
		return colNames;

	}

	private Object[][] convertAppointments() {
		Object[][] converted=new Object[appointments.length][colNames.length];
		for (int i=0;i<appointments.length;i++){
			Wizyta app=appointments[i];
			Pacjent patient=app.getPacjent();
			converted[i][0]=patient.nameToString();
			converted[i][1]=patient.getPESEL();
			converted[i][2]=app.getHourToString(); //
		}
		return converted;
	}

	private void getAppointments() {
		appointments=am.getTodayAppointments(doctor);
		
	}

}
