package appointment;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import GUI_items.BPanel;

public class ArchivePanel extends BPanel {

	JTable appointmentsTable;
	// String[] columnNames={"Data","Pacjent","Lekarz"};
	// ArrayList<Wizyta> appointments;
	private String previewString = "Podgl¹d";
	JButton preview;

	public ArchivePanel() {
		// appointments=new ArrayList<Wizyta>();
		appointmentsTable = new JTable();
		JScrollPane appScroll = new JScrollPane(appointmentsTable);
		preview = new JButton(previewString);

		add(appScroll);
		add(preview);

	}

	public void setData(Object[][] data, String[] columnNames) {// ArrayList<Wizyta>
																// apps) {

		DefaultTableModel model = new DefaultTableModel(data, columnNames);
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
