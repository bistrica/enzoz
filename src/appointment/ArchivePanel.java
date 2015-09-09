package appointment;

import items.Wizyta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ArchivePanel extends JPanel {

	JTable appointmentsTable;
	//String[] columnNames={"Data","Pacjent","Lekarz"};
	//ArrayList<Wizyta> appointments;
	private String previewString="Podgl�d";
	JButton preview;
	
	public ArchivePanel(AppointmentView parent) {
		//appointments=new ArrayList<Wizyta>();
		appointmentsTable=new JTable();
		JScrollPane appScroll=new JScrollPane(appointmentsTable);
		preview=new JButton(previewString);
		
		
		add(appScroll);
		add(preview);
		
		
		
	}

	public void setData(Object[][] data, String[] columnNames){//ArrayList<Wizyta> apps) {
		
		DefaultTableModel model=new DefaultTableModel(data, columnNames);
		appointmentsTable.setModel(model);
		revalidate();
	}

	public void setPreviewButtonListener(ActionListener al) {
		preview.addActionListener(al);
	}

	public int getArchiveAppIndex() {
		return appointmentsTable.getSelectedRow();
	}
	
}
