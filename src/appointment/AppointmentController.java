package appointment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import items.Wizyta;
import people.Lekarz;
import people.Pacjent;
import people.Pracownik;

public class AppointmentController {

	
	AppointmentDBH am;//WizytaDAO wystarczy?
	AppointmentView av;
	Lekarz doctor;
	
	String[] colNames={"Pacjent", "PESEL", "Godzina"};
	ArrayList<Wizyta> appointments;
	
	/*public AppointmentController(String login) {
		// TODO Auto-generated constructor stub
	}*/

	public AppointmentController(Lekarz user) {
		doctor=user;
		am=new AppointmentDBH();
		av=new AppointmentView();
		getAppointments();
		av.setAppointments(getColumnsNames(), convertAppointments());//dodajPrzycisk();
		av.setOpenButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index=av.getSelectedAppIndex();
				if (index!=-1) {
					createAppointment(appointments.get(index));
				}
			}

		});
		
		av.setVisible(true);
	}
	
	private void createAppointment(Wizyta wizyta) {
		System.out.println("Nowa: "+wizyta.toString());
	}

	private String[] getColumnsNames() {
		return colNames;

	}

	private Object[][] convertAppointments() {
		Object[][] converted=new Object[appointments.size()][colNames.length];
		int i=0;
		for (Wizyta app: appointments){
			Pacjent patient=app.getPacjent();
			converted[i][0]=patient.nameToString();
			converted[i][1]=patient.getPESEL();
			converted[i][2]=app.getHourToString(); //
			i++;
		}
		return converted;
	}

	private void getAppointments() {
		appointments=am.getTodayAppointments(doctor);
		
	}

}
