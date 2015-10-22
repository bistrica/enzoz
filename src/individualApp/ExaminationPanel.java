package individualApp;

import items.Clinic;
import items.Examination;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import GUI_items.ExamPanel;

public class ExaminationPanel extends JPanel {

	private Clinic[] clinics;
	private String addString = "Dodaj skierowanie";
	private JButton addButton;
	private JPanel examsPane;

	public ExaminationPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		addButton = new JButton(addString);
		addButton.setAlignmentX(CENTER_ALIGNMENT);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				examsPane.add(new ExamPanel(clinics));

				revalidate();

			}
		});

		examsPane = new JPanel();

		examsPane.setLayout(new BoxLayout(examsPane, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(examsPane);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		examsPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight()
				- addButton.getHeight()));

		add(addButton);

		add(scrollPane);
	}

	public void setAllClinics(ArrayList<Clinic> clinics) {

		Clinic[] items = new Clinic[clinics.size()];
		items = clinics.toArray(items);
		this.clinics = items;
	}

	public void setExaminations(ArrayList<Examination> exams) {

		for (Examination exam : exams) {
			ExamPanel examPane = new ExamPanel(clinics);
			examPane.setPosition(exam);
			examPane.setClinic(exam.getClinic());
			examPane.setDescription(exam.getDescription());
			examPane.revalidate();
			examsPane.add(examPane);

		}
		revalidate();

	}

	@Override
	public void setEnabled(boolean state) {

		addButton.setEnabled(state); // zbêdne
		for (Component panel : examsPane.getComponents()) {
			panel.setEnabled(state);
		}
	}

	public ArrayList<Examination> getExaminations(boolean onlyIfChanged) {

		ArrayList<Examination> exams = new ArrayList<Examination>();
		boolean allTheSame = true;

		for (Component panel : examsPane.getComponents()) {
			if (panel instanceof ExamPanel) {
				ExamPanel examPanel = (ExamPanel) panel;
				Examination exam = examPanel.retrieveExam();
				if (!exam.equals(examPanel.getPosition()))
					allTheSame = false;
				exams.add(exam);
			}
		}

		if (exams.isEmpty())
			exams = null;
		return (onlyIfChanged && allTheSame) ? null : exams;
	}

}
