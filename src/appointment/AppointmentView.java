package appointment;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import database.DBHandler;

public class AppointmentView extends JFrame {

	JScrollPane appTable;
	// JTable apps;
	private String todayAppString = "Dzisiejsze wizyty";
	private String openString = "Rozpocznij wizytê", previewString = "Podgl¹d";

	private AppointmentPanel archivePanel, todayPanel;
	private String archiveString = "Archiwum";
	private JMenuItem refreshItem;
	private String viewMenuString = "Widok";
	private String refreshString = "Odœwie¿";

	public AppointmentView() {

		JMenu menu = new JMenu(viewMenuString);
		refreshItem = new JMenuItem(refreshString);
		menu.add(refreshItem);
		JMenuBar bar = new JMenuBar();
		bar.add(menu);
		setJMenuBar(bar);

		JTabbedPane tabbedPanel = new JTabbedPane();

		todayPanel = new AppointmentPanel();
		archivePanel = new AppointmentPanel();// this);
		tabbedPanel.add(todayAppString, todayPanel);
		tabbedPanel.add(archiveString, archivePanel);
		getContentPane().add(tabbedPanel);
		setSize(700, 600);

		// TODO: daæ to jako w kontrolerze
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DBHandler.close();
				System.out.println("closed");
				System.exit(0);

			}
		});
		// pack();
	}

	public void setRefreshListener(ActionListener al) {
		refreshItem.addActionListener(al);
	}

	/*
	 * public void setArchiveData(ArrayList<Wizyta> apps){
	 * archivePanel.setData(apps); }
	 */

	/*
	 * public void dodajPrzycisk() { JButton jb=new JButton("Dzia³a");
	 * appPanel.add(jb); }
	 */

	/*
	 * public void setAppointments(String[] columnsNames, Object[][]
	 * convertAppointments) { // System.out.println("ap "+convertAppointments);
	 * tableToday = new JTable(new NonEditableDefaultTableModel(
	 * convertAppointments, columnsNames)); appPanel.remove(appTable); appTable
	 * = new JScrollPane(tableToday); appPanel.add(appTable);
	 * 
	 * }
	 */

	public int getSelectedAppIndex() {
		return todayPanel.getAppIndex();// getSelectedRow();
	}

	public void setTodayListener(ActionListener al) {
		todayPanel.setButtonListenerAndLabel(openString, al);
	}

	// TODO: refresh (added appointments for today!)
	/*
	 * public void refreshTodayApps() {
	 * 
	 * }
	 * 
	 * public void refreshArchiveApps() {
	 * 
	 * }
	 */

	public void setArchiveListener(ActionListener al) {
		archivePanel.setButtonListenerAndLabel(previewString, al);
	}

	/*
	 * public void tryToMakePreview(Wizyta wizyta) { ia }
	 */

	public int getSelectedArchiveAppIndex() {
		return archivePanel.getAppIndex();
	}

	public void setArchiveAppointments(String[] columnNames,
			Object[][] convertArchiveAppointments) {

		archivePanel.setData(convertArchiveAppointments, columnNames);
		revalidate(); // to check
	}

	public void setTodayAppointments(String[] columnNames,
			Object[][] convertArchiveAppointments) {

		todayPanel.setData(convertArchiveAppointments, columnNames);
		revalidate(); // to check
	}

	public void displayInfo(String message, String titleBar) {
		JOptionPane.showMessageDialog(null, message, titleBar,
				JOptionPane.INFORMATION_MESSAGE);

	}

}
