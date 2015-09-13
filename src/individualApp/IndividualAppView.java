package individualApp;

import items.Choroba;
import items.Lek;
import items.Poradnia;
import items.PozycjaNaRecepcie;
import items.Skierowanie;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class IndividualAppView extends JFrame {

	// tabbedPanel;
	// JPanel interviewPane, illnessesPane, prescriptionPane, examinationPane,
	// archivesPane;
	InterviewPanel interviewPane;
	IllnessesPanel illnessesPane;
	PrescriptionPanel prescriptionPane;
	ExaminationPanel examinationPane;
	ArchivesPanel archivesPane;

	JLabel mainInfo, additionalInfo;
	String interviewPaneString = "Wywiad", illnessesPaneString = "Choroby",
			prescriptionPaneString = "Recepty",
			examinationPaneString = "Skierowania",
			archivesPaneString = "Historia wizyt";

	boolean isEditable;

	public IndividualAppView(boolean isEditable) {

		/*
		 * this.isEditable = isEditable; if (!isEditable) {
		 * 
		 * }
		 */

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

		if (isEditable)
			tabbedPanel.add(archivesPaneString, archivesPane);
		/*
		 * else disableComponents();
		 */

		setLayout(new BorderLayout());// new BoxLayout(getContentPane(),
										// BoxLayout.Y_AXIS));
		// namePanel.setBackground(Color.BLUE);
		getContentPane().add(namePanel, BorderLayout.NORTH);
		getContentPane().add(tabbedPanel);

		pack();

	}

	/*
	 * private void disableComponents() { changeComponentsState(false); }
	 * 
	 * private void enableComponents() { changeComponentsState(true); }
	 */

	public void setComponentsState() {
		interviewPane.setEnabled(isEditable);
		illnessesPane.setEnabled(isEditable);
		prescriptionPane.setEnabled(isEditable);
		examinationPane.setEnabled(isEditable);
	}

	public void setClinicsList(ArrayList<Poradnia> list) {
		examinationPane.setAllClinics(list);
	}

	// ³aduje wszystkie istniej¹ce z bazy
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

	// ³aduje wszystkie leki z bazy
	public void setAllMedicinesList(ArrayList<Lek> list) {
		prescriptionPane.setAll(list);
	}

	public void displayInfo(String message, String titleBar) {
		JOptionPane.showMessageDialog(null, message, titleBar,
				JOptionPane.INFORMATION_MESSAGE);
	}

	/*
	 * public void setMedicinesList(ArrayList<Lek> list){
	 * prescriptionPane.setPrescriptionData(list); }
	 */

}
