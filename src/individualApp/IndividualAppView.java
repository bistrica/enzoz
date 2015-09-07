package individualApp;

import java.util.ArrayList;

import items.Choroba;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class IndividualAppView extends JFrame {

	// tabbedPanel;
	//JPanel interviewPane, illnessesPane, prescriptionPane, examinationPane, archivesPane;
	InterviewPanel interviewPane;
	IllnessesPanel illnessesPane;
	PrescriptionPanel prescriptionPane;
	ExaminationPanel examinationPane;
	ArchivesPanel archivesPane;
	
	JLabel mainInfo, additionalInfo;
	String interviewPaneString="Wywiad", illnessesPaneString="Choroby", prescriptionPaneString="Recepty", examinationPaneString="Skierowania", archivesPaneString="Historia wizyt";
	
	
	public IndividualAppView() {
		
		mainInfo=new JLabel();
		additionalInfo=new JLabel();
		
		JTabbedPane tabbedPanel = new JTabbedPane();
		interviewPane=new InterviewPanel();
		illnessesPane=new IllnessesPanel();
		prescriptionPane=new PrescriptionPanel(); 
		examinationPane=new ExaminationPanel(); 
		archivesPane=new ArchivesPanel();
		
		
		tabbedPanel.add(interviewPaneString, interviewPane);
		tabbedPanel.add(illnessesPaneString, illnessesPane);
		tabbedPanel.add(prescriptionPaneString, prescriptionPane);
		tabbedPanel.add(examinationPaneString, examinationPane);
		tabbedPanel.add(archivesPaneString, archivesPane);
		
		
		getContentPane().add(tabbedPanel);
		
		pack();
		
		
	}
	
	public void setIllnessesList(ArrayList<Choroba> list){
		Choroba[] ill=new Choroba[list.size()];
		ill=list.toArray(ill);
		illnessesPane.setAll(ill);
	}
}
