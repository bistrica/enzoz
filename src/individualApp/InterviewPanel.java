package individualApp;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import GUI_items.LengthFilter;

public class InterviewPanel extends JPanel {

	private int MAX_CHARS = 1500;
	private JTextArea interview;
	private String interviewString = "Treœæ konsultacji (" + MAX_CHARS
			+ " znaków)";

	public InterviewPanel() {

		setLayout(new BorderLayout());
		setBorder(new CompoundBorder(new EmptyBorder(20, 50, 20, 50),
				new TitledBorder(interviewString)));
		interview = new JTextArea();
		interview.setDocument(new LengthFilter(MAX_CHARS, false));
		interview.setLineWrap(true);
		JScrollPane interviewScroll = new JScrollPane(interview);
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
