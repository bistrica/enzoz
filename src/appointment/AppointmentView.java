package appointment;

import items.Wizyta;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class AppointmentView extends JFrame {

	
	JTable wizyty;
	private String todayAppString="Dzisiejsze wizyty";
	JPanel appPanel;
	private String[] columnsNames;
	private Object[][] appointments;
	
	public AppointmentView() {
		
		
		JTabbedPane tabbedPanel = new JTabbedPane();
		appPanel=new JPanel();
		JButton jb=new JButton("Dzia³a1");
		appPanel.add(jb);
		tabbedPanel.add(todayAppString, appPanel);
		getContentPane().add(tabbedPanel);
		
		
		
		
		
		pack();
	}
	
	/*public void dodajPrzycisk() {
		JButton jb=new JButton("Dzia³a");
		appPanel.add(jb);
	}*/
	
	public void utworzTabele(Wizyta[] wizyty) {
		
		String[] kolumny = {"Imiê i nazwisko", "PESEL", "Godzina"};
		
		
		
	}

	public void setAppointments(String[] columnsNames, Object[][] convertAppointments) {
		this.columnsNames=columnsNames;
		this.appointments=convertAppointments;
	}
	
}
