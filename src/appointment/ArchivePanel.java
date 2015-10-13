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

	private static final int PESEL_LENGTH = 11;
	private static final int DOCTOR_NAME_LENGTH = 50;
	private static final int DAY_MONTH_LENGTH = 2;
	private static final int YEAR_LENGTH = 4;

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
		year = new JTextField(YEAR_LENGTH);
		year.setDocument(new LengthFilter(YEAR_LENGTH, true));
		year.setMaximumSize(year.getPreferredSize());
		month = new JTextField(DAY_MONTH_LENGTH);
		month.setDocument(new LengthFilter(DAY_MONTH_LENGTH, true));
		month.setMaximumSize(month.getPreferredSize());
		day = new JTextField(DAY_MONTH_LENGTH);
		day.setDocument(new LengthFilter(DAY_MONTH_LENGTH, true));
		day.setMaximumSize(day.getPreferredSize());
		patientPESEL = new JTextField(PESEL_LENGTH);
		patientPESEL.setDocument(new LengthFilter(PESEL_LENGTH, true));
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

		System.out.println();
		int y = -1, m = -1, d = -1;
		// long p = -1;
		try { // niepotrzebne, bo tylko cyfry
			if (!year.getText().isEmpty())// equals(""))
				y = Integer.parseInt(year.getText());
			if (!month.getText().isEmpty())
				m = Integer.parseInt(month.getText());
			if (!day.getText().isEmpty())
				d = Integer.parseInt(day.getText());
			String pesel = patientPESEL.getText();
			if (!pesel.isEmpty()) {
				if (pesel.length() < PESEL_LENGTH)
					return false;
				long p = Long.parseLong(pesel);
			}
		} catch (NumberFormatException e) {
			System.out.println("NUM");
			return false;

		}

		// System.out.println("AFT 1");
		if ((m != -1 && m < 1) || m > 12 || (d != -1 && d < 1) || d > 31)
			return false;

		System.out.println("AFT  <>");
		if (m == 2 && d > 29)
			return false;

		System.out.println("AFT 20 FEB");
		if ((m == 4 || m == 6 || m == 9 || m == 11) && d == 31)
			return false;

		System.out.println("AFT 31");
		Calendar cal = Calendar.getInstance();
		int currYear = cal.get(Calendar.YEAR);
		if (y > currYear || (y != -1 && y < MIN_YEAR))
			return false;

		System.out.println("YEARS");
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
		else {
			helper.setSurname(docSurname);
			System.out.println("Doc " + docSurname);
		}
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

	public boolean emptySearchParameters() {
		return doctorSurname.getSelectedIndex() == 0 && day.getText().isEmpty()
				&& month.getText().isEmpty() && year.getText().isEmpty()
				&& patientPESEL.getText().isEmpty();// &&
	}

	public void reset() {
		doctorSurname.setSelectedIndex(0);
		day.setText("");
		month.setText("");
		year.setText("");
		patientPESEL.setText("");
	}
}
