package individualApp;

import items.Clinic;
import items.Examination;
import items.Illness;
import items.Medicine;
import items.PrescriptedItem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import GUI_items.IconFrame;
import exceptions.BadDataException;

public class IndividualAppView extends IconFrame {

	private String options[] = { "Tak", "Nie" };

	private InterviewPanel interviewPane;
	private IllnessesPanel illnessesPane;
	private PrescriptionPanel prescriptionPane;
	private ExaminationPanel examinationPane;

	private JMenuItem editItem, saveItem;

	private JLabel mainInfo, additionalInfo;
	private String interviewPaneString = "Wywiad",
			illnessesPaneString = "Choroby",
			prescriptionPaneString = "Recepty",
			examinationPaneString = "Skierowania",
			confirmExitString = "Czy na pewno chcesz opu�ci� to okno?",
			exitTitleBarString = "Wyj�cie", editMenuString = "Edycja",
			editString = "Edytuj wizyt�", saveString = "Zapisz zmiany",
			appointmentString = "Dane wizyty",
			confirmSaveString = "Czy na pewno chcesz zatwierdzi� wizyt�?",
			saveTitleBarString = "Zapisz i zako�cz wizyt�";

	private boolean editMode;
	private JLabel appData;

	public IndividualAppView(boolean isEnabled, boolean isEditingAllowed,
			boolean isArchive) {

		editMode = isEnabled;

		JMenu menu = null;
		editItem = new JMenuItem(editString);
		saveItem = new JMenuItem(saveString);

		if (!isEnabled) { // wizyta jest archiwalna; archiwalna najpierw
							// dostaje
							// previewMode, czyli editMode==false
			menu = new JMenu(editMenuString);

			editItem.setEnabled(isEditingAllowed);

			saveItem.setEnabled(false);

			menu.add(editItem);
			menu.add(saveItem);

		} else { // wizyta dzisiejsza
			menu = new JMenu(appointmentString);
			saveItem.setEnabled(true);

			menu.add(saveItem);
		}

		System.out.println("EDIT ALL " + isEditingAllowed);

		if (isEnabled || isEditingAllowed) {
			JMenuBar bar = new JMenuBar();
			bar.add(menu);
			setJMenuBar(bar);
		}

		mainInfo = new JLabel();
		mainInfo.setFont(new Font("Arial", Font.BOLD, 20));
		additionalInfo = new JLabel();
		additionalInfo.setFont(new Font("Arial", Font.PLAIN, 15));
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
		namePanel.add(mainInfo);
		namePanel.add(additionalInfo);

		appData = new JLabel();
		appData.setFont(new Font("Arial", Font.BOLD, 20));

		JPanel appDataPanel = new JPanel();
		appDataPanel.setLayout(new BoxLayout(appDataPanel, BoxLayout.Y_AXIS));
		// appDataPanel.add(appTime);
		appDataPanel.add(appData);
		appData.setAlignmentX(RIGHT_ALIGNMENT);

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(1, 2));

		info.add(namePanel);
		info.add(appDataPanel);
		info.setBorder(new EmptyBorder(10, 10, 10, 10));

		JTabbedPane tabbedPanel = new JTabbedPane();
		interviewPane = new InterviewPanel();
		illnessesPane = new IllnessesPanel(isArchive, this);
		prescriptionPane = new PrescriptionPanel(this);
		examinationPane = new ExaminationPanel();
		examinationPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		tabbedPanel.add(interviewPaneString, interviewPane);
		tabbedPanel.add(illnessesPaneString, illnessesPane);
		tabbedPanel.add(prescriptionPaneString, prescriptionPane);
		tabbedPanel.add(examinationPaneString, examinationPane);

		setLayout(new BorderLayout());
		getContentPane().add(info, BorderLayout.NORTH);
		getContentPane().add(tabbedPanel);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setPreferredSize(new Dimension(800, 600));
		setSize(getPreferredSize());

		setCentreLocation();

	}

	public void setEditMode() {
		this.editMode = true;
		setComponentsState();
	}

	public void setComponentsState() {
		interviewPane.setEnabled(editMode);
		illnessesPane.setEnabled(editMode);
		prescriptionPane.setEnabled(editMode);
		examinationPane.setEnabled(editMode);
		saveItem.setEnabled(editMode);
		editItem.setEnabled(!editMode);
	}

	public void setClinicsList(ArrayList<Clinic> list) {
		examinationPane.setAllClinics(list);
	}

	// �aduje wszystkie istniej�ce z bazy
	public void setIllnessesList(ArrayList<Illness> list) {
		illnessesPane.setAll(list);
	}

	public void setTemporaryIllnesses(ArrayList<Illness> list) {
		illnessesPane.setTemporary(list);
		illnessesPane.revalidate();
	}

	public void setConstantIllnesses(ArrayList<Illness> list) {
		illnessesPane.setConstant(list);
		illnessesPane.revalidate();
	}

	public void setInfo(String name, String address, String date) {
		mainInfo.setText(name);
		additionalInfo.setText(address);
		appData.setText(date);
	}

	public void setInterview(String interview) {
		interviewPane.setInterview(interview);
		interviewPane.revalidate();
	}

	public void setPrescription(ArrayList<PrescriptedItem> pos) {
		prescriptionPane.setPrescription(pos);
		prescriptionPane.revalidate();
	}

	public void setExaminations(ArrayList<Examination> exams) {
		examinationPane.setExaminations(exams);
		examinationPane.revalidate();
	}

	// �aduje wszystkie leki z bazy
	public void setAllMedicinesList(ArrayList<Medicine> list) {
		prescriptionPane.setAll(list);
	}

	public void displayInfo(String message, String titleBar) {
		JOptionPane.showMessageDialog(null, message, titleBar,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void setWindowCloseListener(WindowAdapter windowAdapter) {
		addWindowListener(windowAdapter);
	}

	public void setEditItemListener(ActionListener al) {
		editItem.addActionListener(al);
	}

	public void setSaveItemListener(ActionListener al) {
		saveItem.addActionListener(al);
	}

	public boolean sureToCloseWindow() {
		return (JOptionPane.showOptionDialog(null, confirmExitString,
				exitTitleBarString, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]) == JOptionPane.YES_OPTION);
	}

	public void close() {
		setVisible(false);
		dispose();
	}

	public String getInterview() {
		return interviewPane.getInterviewDescription().trim();
	}

	public ArrayList<Examination> getExaminations(boolean onlyEdited) {
		return examinationPane.getExaminations(onlyEdited);

	}

	public ArrayList<PrescriptedItem> getPrescriptionData(boolean onlyEdited)
			throws BadDataException {
		ArrayList<PrescriptedItem> positions = new ArrayList<PrescriptedItem>();

		positions = prescriptionPane.getPrescriptedPositions(onlyEdited);

		return positions;
	}

	public ArrayList<Illness> getConstantIllnesses(boolean onlyIfEdited) {
		return illnessesPane.getConstantIllnesses(onlyIfEdited);
	}

	public ArrayList<Illness> getCurrentIllnesses(boolean onlyIfEdited) {
		return illnessesPane.getAllCurrentIllnesses(onlyIfEdited);
	}

	public boolean sureToSaveChanges() {
		return (JOptionPane.showOptionDialog(null, confirmSaveString,
				saveTitleBarString, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]) == JOptionPane.YES_OPTION);
	}

	public boolean getEditMode() {
		return editMode;
	}

}
