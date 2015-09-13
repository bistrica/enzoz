package appointment;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AppointmentView extends JFrame {

	JScrollPane appTable;
	// JTable apps;
	private String todayAppString = "Dzisiejsze wizyty";
	private String openString = "Rozpocznij wizytê";
	JPanel appPanel;
	// private String[] columnNames;
	// private Object[][] appointments;
	private JButton openVisitButton;
	private JTable tableToday, tableArchive;
	private ArchivePanel archivePanel;
	private String archiveString = "Archiwum";

	public AppointmentView() {

		JTabbedPane tabbedPanel = new JTabbedPane();
		appPanel = new JPanel();
		openVisitButton = new JButton(openString);
		appPanel.add(openVisitButton);
		appTable = new JScrollPane();
		appPanel.add(appTable);

		archivePanel = new ArchivePanel(this);
		tabbedPanel.add(todayAppString, appPanel);
		tabbedPanel.add(archiveString, archivePanel);
		getContentPane().add(tabbedPanel);
		setSize(700, 600);
		// pack();
	}

	public void setOpenButtonListener(ActionListener al) {
		openVisitButton.addActionListener(al);
	}

	/*
	 * public void setArchiveData(ArrayList<Wizyta> apps){
	 * archivePanel.setData(apps); }
	 */

	/*
	 * public void dodajPrzycisk() { JButton jb=new JButton("Dzia³a");
	 * appPanel.add(jb); }
	 */

	public void setAppointments(String[] columnsNames,
			Object[][] convertAppointments) {
		// System.out.println("ap "+convertAppointments);
		tableToday = new JTable(new DefaultTableModel(convertAppointments,
				columnsNames));
		appPanel.remove(appTable);
		appTable = new JScrollPane(tableToday);
		appPanel.add(appTable);

	}

	public int getSelectedAppIndex() {
		return tableToday.getSelectedRow();
	}

	// TODO: refresh (added appointments for today!)
	public void refreshTodayApps() {

	}

	public void refreshArchiveApps() {

	}

	public void setArchiveListener(ActionListener al) {
		archivePanel.setPreviewButtonListener(al);
	}

	/*
	 * public void tryToMakePreview(Wizyta wizyta) { ia }
	 */

	public int getSelectedArchiveAppIndex() {
		return archivePanel.getArchiveAppIndex();
	}

	public void setArchiveAppointments(String[] columnNames,
			Object[][] convertArchiveAppointments) {

		archivePanel.setData(convertArchiveAppointments, columnNames);
		revalidate(); // to check
	}

	public void displayInfo(String message, String titleBar) {
		JOptionPane.showMessageDialog(null, message, titleBar,
				JOptionPane.INFORMATION_MESSAGE);

	}

}
