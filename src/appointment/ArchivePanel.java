package appointment;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import people.Lekarz;
import GUI_items.LengthFilter;
import GUI_items.SearchHelper;

public class ArchivePanel extends AppointmentPanel {

	int MIN_YEAR = 2000;
	JTextField year, month, day, patientPESEL;
	JComboBox<Lekarz> doctorSurname;
	String yearString = "Rok", monthString = "Miesi¹c", dayString = "Dzieñ",
			PESELString = "PESEL pacjenta", surnameString = "Nazwisko lekarza";
	private JButton searchButton;
	private String searchString = "Szukaj";

	// private ArrayList<String> surnames;

	public ArchivePanel(Lekarz[] doctors) {
		super();
		JPanel searchPane = new JPanel();
		// searchPane.setLayout(new BoxLayout(searchPane, BoxLayout.Y_AXIS));

		JLabel yearLab = new JLabel(yearString), monthLab = new JLabel(
				monthString), dayLab = new JLabel(dayString), patientLab = new JLabel(
				PESELString), doctorNameLab = new JLabel(surnameString);
		year = new JTextField(4);
		year.setDocument(new LengthFilter(4, true));
		year.setMaximumSize(year.getPreferredSize());
		month = new JTextField(2);
		month.setDocument(new LengthFilter(2, true));
		month.setMaximumSize(month.getPreferredSize());
		day = new JTextField(2);
		day.setDocument(new LengthFilter(2, true));
		day.setMaximumSize(day.getPreferredSize());
		patientPESEL = new JTextField(11);
		patientPESEL.setDocument(new LengthFilter(11, true));
		patientPESEL.setMaximumSize(patientPESEL.getPreferredSize());
		doctorSurname = new JComboBox<Lekarz>(doctors);// JTextField(20);
		// doctorSurname.setDocument(new LengthFilter(50, false));
		doctorSurname.setMaximumSize(doctorSurname.getPreferredSize());

		JPanel datePane = new JPanel();
		datePane.setLayout(new BoxLayout(datePane, BoxLayout.X_AXIS));
		datePane.add(yearLab);
		datePane.add(year);
		datePane.add(monthLab);
		datePane.add(month);
		datePane.add(dayLab);
		datePane.add(day);

		JPanel docPane = new JPanel();
		docPane.add(doctorNameLab);
		docPane.add(doctorSurname);

		JPanel patPane = new JPanel();
		patPane.add(patientLab);
		patPane.add(patientPESEL);

		searchButton = new JButton(searchString);

		/*
		 * datePane.setAlignmentX(Component.RIGHT_ALIGNMENT);// 0.0
		 * docPane.setAlignmentX(Component.RIGHT_ALIGNMENT);// 0.0
		 * patPane.setAlignmentX(Component.RIGHT_ALIGNMENT);// 0.0
		 */

		searchPane.add(datePane);
		searchPane.add(patPane);
		searchPane.add(docPane);
		searchPane.add(searchButton);
		searchPane.setMinimumSize(searchPane.getPreferredSize());
		// searchPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		// patPane.setBackground(Color.RED);
		// docPane.setBackground(Color.GREEN);
		// datePane.setBackground(Color.BLACK);
		// searchPane.setAutoscrolls(true);
		add(searchPane, BorderLayout.NORTH);

	}

	public void setDoctorsSurnames(Lekarz[] sur) {
		doctorSurname = new JComboBox<Lekarz>(sur);
		repaint();
	}

	public void setSearchButtonListener(ActionListener al) {
		searchButton.addActionListener(al);
	}

	public boolean validateFields() {

		/*
		 * String surname = doctorSurname.getText(); if
		 * (!surname.trim().equals("")) { if (!surnames.contains(surname)) //
		 * ;// { return false; // } }
		 */

		int y = -1, m = -1, d = -1;// , p = -1;
		try {
			if (!year.getText().trim().equals(""))
				y = Integer.parseInt(year.getText());
			if (!month.getText().trim().equals(""))
				m = Integer.parseInt(month.getText());
			if (!day.getText().trim().equals(""))
				d = Integer.parseInt(day.getText());
			// if (!patientPESEL.getText().trim().equals(""))
			// p = Integer.parseInt(patientPESEL.getText());
		} catch (NumberFormatException e) {
			return false;
		}

		if (m < 1 || m > 12 || d < 1 || d > 31)
			return false;

		if (m == 2 && d > 29)
			return false;

		if ((m == 4 || m == 6 || m == 9 || m == 11) && d == 31)
			return false;

		Calendar cal = Calendar.getInstance();
		int currYear = cal.get(Calendar.YEAR);
		if (y > currYear || y < MIN_YEAR)
			return false;

		if (y != -1) {
			cal.set(Calendar.YEAR, y);
			boolean leapYear = cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
			if (m == 2 && d == 29 && !leapYear)
				return false;
		}

		return true;
	}

	/*
	 * JTable appointmentsTable; // String[]
	 * columnNames={"Data","Pacjent","Lekarz"}; // ArrayList<Wizyta>
	 * appointments; private String previewString = "Podgl¹d"; JButton preview;
	 * 
	 * public ArchivePanel() { // appointments=new ArrayList<Wizyta>();
	 * appointmentsTable = new JTable(); JScrollPane appScroll = new
	 * JScrollPane(appointmentsTable); preview = new JButton(previewString);
	 * 
	 * add(appScroll); add(preview);
	 * 
	 * }
	 * 
	 * public void setData(Object[][] data, String[] columnNames) {//
	 * ArrayList<Wizyta> // apps) {
	 * 
	 * DefaultTableModel model = new NonEditableDefaultTableModel(data,
	 * columnNames); appointmentsTable.setModel(model); revalidate(); }
	 * 
	 * public void setPreviewButtonListener(ActionListener al) {
	 * preview.addActionListener(al); }
	 * 
	 * public int getArchiveAppIndex() { return
	 * appointmentsTable.getSelectedRow(); }
	 */

	public SearchHelper getSearchData() {
		SearchHelper helper = new SearchHelper();
		Lekarz docSurname = (Lekarz) doctorSurname.getSelectedItem();
		if (docSurname.toString().trim().equals(""))
			helper.setSurname(null);
		else
			helper.setSurname(docSurname);
		String dayStr = day.getText().trim(), monthStr = month.getText().trim(), yearStr = year
				.getText().trim(), PESELStr = patientPESEL.getText().trim();
		if (dayStr.isEmpty())
			helper.setDay(-1);
		else
			helper.setDay(Integer.parseInt(dayStr));
		if (monthStr.isEmpty())
			helper.setMonth(-1);
		else
			helper.setMonth(Integer.parseInt(monthStr));
		if (yearStr.isEmpty())
			helper.setYear(-1);
		else
			helper.setYear(Integer.parseInt(yearStr));
		if (PESELStr.isEmpty())
			helper.setPESEL(-1);
		else
			helper.setPESEL(Long.parseLong(PESELStr));
		return helper;
	}
}
