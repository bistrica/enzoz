package individualApp;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class InterviewPanel extends JPanel {

	JTextArea interview;
	private String interviewString = "Treœæ konsultacji";

	public InterviewPanel() {

		setLayout(new BorderLayout());
		setBorder(new CompoundBorder(new EmptyBorder(20, 50, 20, 50),
				new TitledBorder(interviewString)));
		interview = new JTextArea();
		interview.setLineWrap(true);
		// interview.setSize(getWidth(), getHeight());
		JScrollPane interviewScroll = new JScrollPane(interview);
		// interviewScroll.setSize();////getParent().getWidth(),
		// getParent().getHeight());
		add(interviewScroll);

	}

	public void setInterview(String interview2) {
		interview.setText(interview2);

	}

	@Override
	public void setEnabled(boolean state) {
		interview.setEditable(state);
	}

	public String getInterviewDescription() {
		return interview.getText().trim();
	}

}
