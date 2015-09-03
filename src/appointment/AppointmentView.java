package appointment;

import java.awt.event.ActionListener;

import items.Wizyta;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AppointmentView extends JFrame {

	JScrollPane appTable;
	//JTable apps;
	private String todayAppString="Dzisiejsze wizyty";
	private String openString="Rozpocznij wizytê";
	JPanel appPanel;
	private String[] columnNames;
	private Object[][] appointments;
	private JButton openVisitButton;
	private JTable table;
	
	
	public AppointmentView() {
		
		
		JTabbedPane tabbedPanel = new JTabbedPane();
		appPanel=new JPanel();
		openVisitButton=new JButton(openString);
		appPanel.add(openVisitButton);
		appTable=new JScrollPane();
		appPanel.add(appTable);
		tabbedPanel.add(todayAppString, appPanel);
		getContentPane().add(tabbedPanel);
		
		pack();
	}
	
	public void setOpenButtonListener(ActionListener al) {
		openVisitButton.addActionListener(al);
	}
	
	/*public void dodajPrzycisk() {
		JButton jb=new JButton("Dzia³a");
		appPanel.add(jb);
	}*/
	
	public void utworzTabele() {
		
			
		
	}

	public void setAppointments(String[] columnsNames, Object[][] convertAppointments) {
		this.columnNames=columnsNames;
		this.appointments=convertAppointments;
		System.out.println("ap "+convertAppointments);
		table=new JTable(new DefaultTableModel(this.appointments, this.columnNames));
		appPanel.remove(appTable);
		appTable=new JScrollPane(table);
		appPanel.add(appTable);
		
	}

	public int getSelectedAppIndex() {
		return table.getSelectedRow();
	}
	
}
