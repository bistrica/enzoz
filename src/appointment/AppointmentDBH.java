package appointment;

import items.Wizyta;
import people.Lekarz;
import database.DBHandler;

public class AppointmentDBH {

	DBHandler dbh;
	
	public AppointmentDBH() {
		dbh=DBHandler.getDatabaseHandler();
	}

	

	public Wizyta[] getTodayAppointments(Lekarz doctor) {

		Wizyta[] appointments=dbh.getAppointments(doctor.getId());
		return null;
	}
}
