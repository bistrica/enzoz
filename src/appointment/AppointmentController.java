package appointment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import individualApp.IndividualAppController;
import items.Wizyta;
import people.Lekarz;
import people.Pacjent;
import people.Pracownik;

public class AppointmentController {

	
	AppointmentDBH am;//WizytaDAO wystarczy?
	AppointmentView av;
	Lekarz doctor;
	
	String[] colNames={"Pacjent", "PESEL", "Godzina"};
	ArrayList<Wizyta> appointmentsToday;
	ArrayList<Wizyta> appointmentsArchive;
	
	String[] columnNames={"Data","Pacjent","Lekarz"};
	private String notEditableString="Nie mo¿na w tej chwili zobaczyæ wizyty. Wizyta jest edytowana przez innego u¿ytkownika.";
	private String titleBarString="Wizyta w trakcie edycji";
	
	/*public AppointmentController(String login) {
		// TODO Auto-generated constructor stub
	}*/

	public AppointmentController(Lekarz user) {
		doctor=user;
		am=new AppointmentDBH();
		av=new AppointmentView();
		getAppointments();
		getArchiveAppointments();
		av.setAppointments(colNames, convertAppointments());
		av.setArchiveAppointments(columnNames, convertArchiveAppointments());
		
		av.setOpenButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index=av.getSelectedAppIndex();
				if (index!=-1) {
					boolean editable=true;
					createAppointment(appointmentsToday.get(index),editable);
				}
			}

		});
		av.setArchiveListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index=av.getSelectedArchiveAppIndex();
				if (index!=-1) {
					tryToCreateArchiveAppointmentPreview(appointmentsArchive.get(index));
				}
 

			}

		});
		
		//TODO: odkomentowaæ
		av.setVisible(true);
		
		//TODO:usun¹æ
		//createAppointment(appointments.get(0));
	}
	
	private void getArchiveAppointments() {
		// TODO Auto-generated method stub
		
	}

	private void createAppointment(Wizyta app, boolean editable) {
		System.out.println("Nowa: "+app.toString());
		
		new IndividualAppController(app,editable);
	}

	private void tryToCreateArchiveAppointmentPreview(Wizyta app) {
		if (am.statusAllowsEditing(app)) {
			am.updateAppData(app);
			boolean editable=false;
			createAppointment(app, editable);
		}
		else
			av.displayInfo(notEditableString, titleBarString);
			
		
	}
	
	private String[] getColumnsNames() {
		return colNames;

	}

	private Object[][] convertAppointments() {
		Object[][] converted=new Object[appointmentsToday.size()][colNames.length];
		int i=0;
		for (Wizyta app: appointmentsToday){
			Pacjent patient=app.getPacjent();
			converted[i][0]=patient.nameToString();
			converted[i][1]=patient.getPESEL();
			converted[i][2]=app.getHourToString(); //
			i++;
		}
		return converted;
	}
	
	private Object[][] convertArchiveAppointments() {
		Object[][] data=new Object[appointmentsArchive.size()][3];
		int i=0;
		for (Wizyta app: appointmentsArchive) {
			data[i][0]=app.getDataToString();
			data[i][1]=app.getPacjent().getMainInfo();
			data[i++][2]=app.getLekarz().getName();
		}
		return data;
	}

	private void getAppointments() {
		appointmentsToday=am.getTodayAppointments(doctor);
		
	}

}
