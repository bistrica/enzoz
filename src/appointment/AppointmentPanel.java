package appointment;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import GUI_items.NonEditableDefaultTableModel;

public class AppointmentPanel extends JPanel {

	JTable appointmentsTable;
	// String[] columnNames={"Data","Pacjent","Lekarz"};
	// ArrayList<Wizyta> appointments;
	// private String previewString = "Podgl¹d";
	JButton button; // preview;

	public AppointmentPanel() {
		// appointments=new ArrayList<Wizyta>();
		appointmentsTable = new JTable();
		appointmentsTable.setFont(new Font("Arial", Font.PLAIN, 18));
		appointmentsTable.setRowHeight(appointmentsTable.getRowHeight() * 2);

		JScrollPane appScroll = new JScrollPane(appointmentsTable);
		appScroll.setBorder(new EmptyBorder(0, 0, 0, 0));
		button = new JButton();// previewString);
		// setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(new BorderLayout());
		add(appScroll, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(button);// preview);
		add(buttonPanel, BorderLayout.SOUTH);

	}

	public void setData(Object[][] data, String[] columnNames) {// ArrayList<Wizyta>
																// apps) {

		DefaultTableModel model = new NonEditableDefaultTableModel(data,
				columnNames);
		appointmentsTable.setModel(model);
		revalidate();
	}

	public void setButtonListenerAndLabel(String label, ActionListener al) {
		button.setText(label);
		button.addActionListener(al);
	}

	public int getAppIndex() {
		return appointmentsTable.getSelectedRow();
	}
}
