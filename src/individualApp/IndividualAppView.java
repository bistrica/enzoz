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

	// tabbedPanel;
	// JPanel interviewPane, illnessesPane, prescriptionPane, examinationPane,
	// archivesPane;
	Object options[] = { "Tak", "Nie" };

	InterviewPanel interviewPane;
	IllnessesPanel illnessesPane;
	PrescriptionPanel prescriptionPane;
	ExaminationPanel examinationPane;
	ArchivesPanel archivesPane;

	JMenuItem editItem;

	JLabel mainInfo, additionalInfo;
	String interviewPaneString = "Wywiad", illnessesPaneString = "Choroby",
			prescriptionPaneString = "Recepty",
			examinationPaneString = "Skierowania",
			archivesPaneString = "Historia wizyt";

	boolean editMode;
	private String confirmExitString = "Czy na pewno chcesz opuœciæ to okno?";
	private String exitTitleBarString = "Wyjœcie";

	private String editMenuString = "Edycja";
	private String editString = "Edytuj wizytê";

	private JMenuItem saveItem;

	private String saveString = "Zapisz zmiany";

	private String appointmentString = "Dane wizyty";

	private String confirmSaveString = "Czy na pewno chcesz zatwierdziæ wizytê?";

	private String saveTitleBarString = "Zapisz i zakoñcz wizytê";

	// private JLabel appTime;

	private JLabel appData;

	public IndividualAppView(boolean isEnabled, boolean isEditingAllowed,
			boolean isArchive) {

		/*
		 * this.isEditable = isEditable; if (!isEditable) {
		 * 
		 * }
		 */
		editMode = isEnabled;

		JMenu menu = null;
		editItem = new JMenuItem(editString);
		saveItem = new JMenuItem(saveString);

		if (!isEnabled) { // wizyta jest archiwalna; archiwalna najpierw
							// dostaje
							// previewMode, czyli editMode==false
			menu = new JMenu(editMenuString);

			// System.out.println("IS ED ALL " + isEditingAllowed);
			editItem.setEnabled(isEditingAllowed);

			// menu.add(editItem);
			// editItem.setEnabled(false);
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
		// namePanel.setBorder(new EmptyBorder(50, 50, 50, 50));

		// appTime = new JLabel();
		// appTime.setFont(new Font("Arial", Font.BOLD, 20));
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
		// archivesPane = new ArchivesPanel();

		tabbedPanel.add(interviewPaneString, interviewPane);
		tabbedPanel.add(illnessesPaneString, illnessesPane);
		tabbedPanel.add(prescriptionPaneString, prescriptionPane);
		tabbedPanel.add(examinationPaneString, examinationPane);// new
																// JScrollPane(examinationPane));

		// if (isEnabled)
		// tabbedPanel.add(archivesPaneString, archivesPane);
		/*
		 * else disableComponents();
		 */

		setLayout(new BorderLayout());// new BoxLayout(getContentPane(),
										// BoxLayout.Y_AXIS));
		// namePanel.setBackground(Color.BLUE);
		getContentPane().add(info, BorderLayout.NORTH);
		getContentPane().add(tabbedPanel);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setPreferredSize(new Dimension(800, 600));
		setSize(getPreferredSize());// new Dimension(750, 500));

		setCentreLocation();

	}

	/*
	 * private void disableComponents() { changeComponentsState(false); }
	 * 
	 * private void enableComponents() { changeComponentsState(true); }
	 */

	public void setEditMode() {
		// System.out.println("SET EDIT MODE");
		this.editMode = true;
		setComponentsState();
	}

	/*
	 * public void setMode(boolean previewMode) { this.previewMode =
	 * previewMode; setComponentsState(); }
	 */

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

	// ³aduje wszystkie istniej¹ce z bazy
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

	/*
	 * public void setTempIllnesses(ArrayList<Choroba> ill) {
	 * illnessesPane.setTemporary(ill); illnessesPane.revalidate(); }
	 * 
	 * public void setConstIllnesses(ArrayList<Choroba> ill) {
	 * illnessesPane.setConstant(ill); illnessesPane.revalidate(); }
	 */

	public void setPrescription(ArrayList<PrescriptedItem> pos) {
		prescriptionPane.setPrescription(pos);
		prescriptionPane.revalidate();
	}

	public void setExaminations(ArrayList<Examination> exams) {
		examinationPane.setExaminations(exams);
		examinationPane.revalidate();
	}

	/*
	 * public String getInterview() { interviewPanel.getInterview().get(); }
	 */

	// ³aduje wszystkie leki z bazy
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
		setVisible(false); //
		dispose();
	}

	public String getInterview() {
		return interviewPane.getInterviewDescription().trim();
	}

	public ArrayList<Examination> getExaminations(boolean onlyEdited) {
		return examinationPane.getExaminations(onlyEdited);

	}

	public ArrayList<PrescriptedItem> getPrescriptionData(boolean onlyEdited)
			throws BadDataException {// WrongInputException {
		ArrayList<PrescriptedItem> positions = new ArrayList<PrescriptedItem>();
		// try {
		positions = prescriptionPane.getPrescriptedPositions(onlyEdited);
		/*
		 * } catch (BadDataException e) { throw new WrongInputException(); }
		 */
		return positions;
	}

	/*
	 * public ArrayList<Choroba> getTemporaryIllnesses() { return
	 * illnessesPane.getTemporaryIllnesses(); }
	 */

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

	/*
	 * public void setMedicinesList(ArrayList<Lek> list){
	 * prescriptionPane.setPrescriptionData(list); }
	 */

}
