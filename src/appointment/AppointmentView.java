package appointment;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import people.Doctor;
import GUI_items.IconFrame;
import GUI_items.SearchHelper;

public class AppointmentView extends IconFrame {

	private JScrollPane appTable;

	private String options[] = { "Tak", "Nie" };

	private AppointmentPanel todayPanel;
	private ArchivePanel archivePanel;
	private String archiveString = "Archiwum";
	private JMenuItem refreshItem, logoutItem, exitItem;
	private String viewMenuString = "Widok", refreshString = "Odœwie¿",
			noArchiveAppsString = "Nie znaleziono archiwalnych wizyt.",
			noAppsString = "Brak wizyt",
			noTodayAppsString = "Brak wizyt na dzisiaj.",
			exitString = "Wyjœcie", logoutString = "Wyloguj: ",
			optionMenuString = "Opcje programu",
			exitTitleBarString = "Wyjœcie",
			confirmExitString = "Czy na pewno chcesz opuœciæ to okno?",
			todayAppString = "Dzisiejsze wizyty",
			openString = "Rozpocznij wizytê", previewString = "Podgl¹d";
	private boolean firstTimeOpenArchive, firstTimeOpenToday;

	public AppointmentView(Doctor[] doctors, String userName) {

		firstTimeOpenArchive = true;
		firstTimeOpenToday = true;

		JMenu menuView = new JMenu(viewMenuString);
		refreshItem = new JMenuItem(refreshString);
		menuView.add(refreshItem);
		JMenu menuOptions = new JMenu(optionMenuString);
		logoutItem = new JMenuItem(logoutString + " " + userName);
		menuOptions.add(logoutItem);
		exitItem = new JMenuItem(exitString);
		menuOptions.add(exitItem);
		JMenuBar bar = new JMenuBar();
		bar.add(menuOptions);
		bar.add(menuView);
		setJMenuBar(bar);

		JTabbedPane tabbedPanel = new JTabbedPane();

		todayPanel = new AppointmentPanel();
		archivePanel = new ArchivePanel(doctors);
		todayPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
		archivePanel.setBorder(new EmptyBorder(0, 10, 0, 10));
		JScrollPane scrollToday = new JScrollPane(todayPanel);
		JScrollPane scrollArchive = new JScrollPane(archivePanel);

		scrollToday.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollArchive.setBorder(new EmptyBorder(0, 0, 0, 0));

		tabbedPanel.add(todayAppString, scrollToday);
		tabbedPanel.add(archiveString, scrollArchive);
		getContentPane().add(tabbedPanel);

		pack();

		Dimension dim = new Dimension(getPreferredSize().width + 100,
				getPreferredSize().height + 100);
		setSize(dim);
		setPreferredSize(dim);
		setCentreLocation();

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	public void setRefreshListener(ActionListener al) {
		refreshItem.addActionListener(al);
	}

	public int getSelectedAppIndex() {
		return todayPanel.getAppIndex();
	}

	public void setTodayListener(ActionListener al) {
		todayPanel.setButtonListenerAndLabel(openString, al);
	}

	public void setArchiveListener(ActionListener al) {
		archivePanel.setButtonListenerAndLabel(previewString, al);
	}

	public void setSearchListener(ActionListener al) {
		archivePanel.setSearchButtonListener(al);
	}

	public boolean validateSearchParameters() {
		return archivePanel.validateFields();
	}

	public void setDoctorsSurnames(Doctor[] sur) {
		archivePanel.setDoctorsSurnames(sur);
	}

	public int getSelectedArchiveAppIndex() {
		return archivePanel.getAppIndex();
	}

	public void setArchiveAppointments(String[] columnNames,
			Object[][] convertedArchiveAppointments, boolean showMessageIfNoApp) {

		archivePanel.setData(convertedArchiveAppointments, columnNames);
		revalidate();

		if (convertedArchiveAppointments.length == 0
				&& (showMessageIfNoApp || firstTimeOpenArchive))
			displayInfo(noArchiveAppsString, noAppsString);

		if (firstTimeOpenArchive)
			firstTimeOpenArchive = false;
	}

	public void setTodayAppointments(String[] columnNames,
			Object[][] convertedAppointments) {

		todayPanel.setData(convertedAppointments, columnNames);
		revalidate();

		if (convertedAppointments.length == 0 && firstTimeOpenToday)
			displayInfo(noTodayAppsString, noAppsString);

		if (firstTimeOpenToday)
			firstTimeOpenToday = false;
	}

	public void displayInfo(String message, String titleBar) {
		JOptionPane.showMessageDialog(null, message, titleBar,
				JOptionPane.INFORMATION_MESSAGE);

	}

	public SearchHelper getSearchData() {
		return archivePanel.getSearchData();
	}

	public boolean emptySearchParameters() {

		return archivePanel.emptySearchParameters();
	}

	public void resetSearchResults() {
		archivePanel.reset();
	}

	public void setLogOutListener(ActionListener al) {
		logoutItem.addActionListener(al);
	}

	public void setExitListener(ActionListener al) {
		exitItem.addActionListener(al);
	}

	public boolean sureToCloseWindow() {

		return (JOptionPane.showOptionDialog(null, confirmExitString,
				exitTitleBarString, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]) == JOptionPane.YES_OPTION);
	}

	public void setWindowCloseListener(WindowAdapter windowAdapter) {
		addWindowListener(windowAdapter);
	}
}
