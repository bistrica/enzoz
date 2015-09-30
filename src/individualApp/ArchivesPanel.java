package individualApp;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class ArchivesPanel extends JPanel {

	JTable appointmentsTable;
	private JTextArea previewWindow;
	// String[] columnNames={"Data","Pacjent","Lekarz"};
	// ArrayList<Wizyta> appointments;
	private String previewString = "Podgl¹d";
	JButton preview;

	public ArchivesPanel() {// IndividualAppView parent) {
		// appointments=new ArrayList<Wizyta>();
		appointmentsTable = new JTable();
		JScrollPane appScroll = new JScrollPane(appointmentsTable);
		preview = new JButton(previewString);
		previewWindow = new JTextArea();
		JScrollPane prevScroll = new JScrollPane(previewWindow);

		JPanel leftPane = new JPanel();
		leftPane.add(appScroll);
		leftPane.add(preview);

		setLayout(new GridLayout(1, 2));
		add(leftPane);
		add(prevScroll);

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
