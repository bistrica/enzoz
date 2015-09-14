package individualApp;

import items.Choroba;
import items.Lek;
import items.Poradnia;
import items.PozycjaNaRecepcie;
import items.Skierowanie;

import java.awt.BorderLayout;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import exceptions.WrongInputException;

public class IndividualAppView extends JFrame {

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
	private String confirmExitString = "Czy na pewno chcesz opu�ci� to okno?";
	private String exitTitleBarString = "Wyj�cie";

	private String editMenuString = "Edycja";
	private String editString = "Edytuj wizyt�";

	private JMenuItem saveItem;

	private String saveString = "Zapisz zmiany";

	private String appointmentString = "Dane wizyty";

	public IndividualAppView(boolean isEnabled, boolean isEditingAllowed) {

		/*
		 * this.isEditable = isEditable; if (!isEditable) {
		 * 
		 * }
		 */
		editMode = isEnabled;

		JMenu menu = null;
		editItem = new JMenuItem(editString);
		saveItem = new JMenuItem(saveString);

		if (!isEnabled) { // wizyta jest archiwalna; archiwalna najpierw dostaje
							// previewMode, czyli editMode==false
			menu = new JMenu(editMenuString);
			editItem.setEnabled(isEditingAllowed);

			menu.add(editItem);

			saveItem.setEnabled(false);

			menu.add(editItem);
			menu.add(saveItem);

		} else {
			menu = new JMenu(appointmentString);
			saveItem.setEnabled(true);

			menu.add(saveItem);
		}

		JMenuBar bar = new JMenuBar();
		bar.add(menu);
		setJMenuBar(bar);

		mainInfo = new JLabel();
		mainInfo.setFont(new Font("Arial", Font.BOLD, 20));
		additionalInfo = new JLabel();
		additionalInfo.setFont(new Font("Arial", Font.PLAIN, 15));
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
		namePanel.add(mainInfo);
		namePanel.add(additionalInfo);

		JTabbedPane tabbedPanel = new JTabbedPane();
		interviewPane = new InterviewPanel();
		illnessesPane = new IllnessesPanel();
		prescriptionPane = new PrescriptionPanel();
		examinationPane = new ExaminationPanel();
		archivesPane = new ArchivesPanel();

		tabbedPanel.add(interviewPaneString, interviewPane);
		tabbedPanel.add(illnessesPaneString, illnessesPane);
		tabbedPanel.add(prescriptionPaneString, prescriptionPane);
		tabbedPanel
				.add(examinationPaneString, new JScrollPane(examinationPane));

		if (isEnabled)
			tabbedPanel.add(archivesPaneString, archivesPane);
		/*
		 * else disableComponents();
		 */

		setLayout(new BorderLayout());// new BoxLayout(getContentPane(),
										// BoxLayout.Y_AXIS));
		// namePanel.setBackground(Color.BLUE);
		getContentPane().add(namePanel, BorderLayout.NORTH);
		getContentPane().add(tabbedPanel);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();

	}

	/*
	 * private void disableComponents() { changeComponentsState(false); }
	 * 
	 * private void enableComponents() { changeComponentsState(true); }
	 */

	public void setEditMode() {
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

	public void setClinicsList(ArrayList<Poradnia> list) {
		examinationPane.setAllClinics(list);
	}

	// �aduje wszystkie istniej�ce z bazy
	public void setIllnessesList(ArrayList<Choroba> list) {
		illnessesPane.setAll(list);
	}

	public void setTemporaryIllnesses(ArrayList<Choroba> list) {
		illnessesPane.setTemporary(list);
		illnessesPane.revalidate();
	}

	public void setConstantIllnesses(ArrayList<Choroba> list) {
		illnessesPane.setConstant(list);
		illnessesPane.revalidate();
	}

	public void setInfo(String name, String address) {
		mainInfo.setText(name);
		additionalInfo.setText(address);
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

	public void setPrescription(ArrayList<PozycjaNaRecepcie> pos) {
		prescriptionPane.setPrescription(pos);
		prescriptionPane.revalidate();
	}

	public void setExaminations(ArrayList<Skierowanie> exams) {
		examinationPane.setExaminations(exams);
		examinationPane.revalidate();
	}

	/*
	 * public String getInterview() { interviewPanel.getInterview().get(); }
	 */

	// �aduje wszystkie leki z bazy
	public void setAllMedicinesList(ArrayList<Lek> list) {
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
		return interviewPane.getInterviewDescription();
	}

	public ArrayList<Skierowanie> getExaminations(boolean onlyEdited) {
		return examinationPane.getExaminations(onlyEdited);

	}

	public ArrayList<PozycjaNaRecepcie> getPrescriptionData(boolean onlyEdited)
			throws WrongInputException {
		ArrayList<PozycjaNaRecepcie> positions = new ArrayList<PozycjaNaRecepcie>();
		try {
			positions = prescriptionPane.getPrescriptedPositions(onlyEdited);
		} catch (NumberFormatException e) {
			throw new WrongInputException();
		}
		return positions;
	}

	/*
	 * public ArrayList<Choroba> getTemporaryIllnesses() { return
	 * illnessesPane.getTemporaryIllnesses(); }
	 */

	public ArrayList<Choroba> getConstantIllnesses(boolean onlyIfEdited) {
		return illnessesPane.getConstantIllnesses(onlyIfEdited);
	}

	public ArrayList<Choroba> getCurrentIllnesses(boolean onlyIfEdited) {
		return illnessesPane.getAllCurrentIllnesses(onlyIfEdited);
	}

	/*
	 * public void setMedicinesList(ArrayList<Lek> list){
	 * prescriptionPane.setPrescriptionData(list); }
	 */

}
