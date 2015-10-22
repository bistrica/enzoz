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

import people.Doctor;
import GUI_items.LengthFilter;
import GUI_items.SearchHelper;

public class ArchivePanel extends AppointmentPanel {

	private static final int PESEL_LENGTH = 11, DAY_MONTH_LENGTH = 2,
			YEAR_LENGTH = 4;

	private int MIN_YEAR = 2000;
	private JTextField year, month, day, patientPESEL;
	private JComboBox<Doctor> doctorSurname;
	private String yearString = "Rok", monthString = "Miesi¹c",
			dayString = "Dzieñ", PESELString = "PESEL pacjenta",
			surnameString = "Nazwisko lekarza", searchString = "Szukaj";
	private JButton searchButton;

	public ArchivePanel(Doctor[] doctors) {
		super();
		JPanel searchPane = new JPanel();

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
		doctorSurname = new JComboBox<Doctor>(doctors);
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

		searchPane.add(datePane);
		searchPane.add(patPane);
		searchPane.add(docPane);
		searchPane.add(searchButton);
		searchPane.setMinimumSize(searchPane.getPreferredSize());

		add(searchPane, BorderLayout.NORTH);

	}

	public void setDoctorsSurnames(Doctor[] sur) {
		doctorSurname = new JComboBox<Doctor>(sur);
		repaint();
	}

	public void setSearchButtonListener(ActionListener al) {
		searchButton.addActionListener(al);
	}

	public boolean validateFields() {

		System.out.println();
		int y = -1, m = -1, d = -1;

		try { // niepotrzebne, bo tylko cyfry
			if (!year.getText().isEmpty())
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

		if ((m != -1 && m < 1) || m > 12 || (d != -1 && d < 1) || d > 31)
			return false;

		if (m == 2 && d > 29)
			return false;

		if ((m == 4 || m == 6 || m == 9 || m == 11) && d == 31)
			return false;

		Calendar cal = Calendar.getInstance();
		int currYear = cal.get(Calendar.YEAR);
		if (y > currYear || (y != -1 && y < MIN_YEAR))
			return false;

		if (y != -1) {
			cal.set(Calendar.YEAR, y);
			boolean leapYear = cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
			if (m == 2 && d == 29 && !leapYear)
				return false;
		}

		return true;
	}

	public SearchHelper getSearchData() {
		SearchHelper helper = new SearchHelper();
		Doctor docSurname = (Doctor) doctorSurname.getSelectedItem();
		if (docSurname.toString().trim().equals(""))
			helper.setSurname(null);
		else {
			helper.setSurname(docSurname);
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
