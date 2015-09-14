package individualApp;

import items.Poradnia;
import items.Skierowanie;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import GUI_items.ExamPanel;

public class ExaminationPanel extends JPanel {

	Poradnia[] clinics;
	private String addString = "Dodaj skierowanie";
	JButton addButton;

	public ExaminationPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		addButton = new JButton(addString);
		addButton.setAlignmentX(CENTER_ALIGNMENT);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				add(new ExamPanel(clinics));

				revalidate();

			}
		});

		add(addButton);
	}

	public void setAllClinics(ArrayList<Poradnia> clinics) {

		Poradnia[] items = new Poradnia[clinics.size()];
		items = clinics.toArray(items);
		this.clinics = items;
	}

	public void setExaminations(ArrayList<Skierowanie> exams) {
		System.out.println("SET");

		for (Skierowanie exam : exams) {
			System.out.println("::" + exam.getOpisBadan());
			ExamPanel examPane = new ExamPanel(clinics);
			examPane.setPosition(exam);
			examPane.setClinic(exam.getPoradnia());
			examPane.setDescription(exam.getOpisBadan());
			examPane.revalidate();
			add(examPane);

		}
		revalidate();

	}

	@Override
	public void setEnabled(boolean state) {
		addButton.setEnabled(state); // zbêdne
		for (Component panel : getComponents()) {
			panel.setEnabled(state);
			// System.out.println("P:" + panel);
			// if (panel instanceof ExamPanel)
			// ((ExamPanel) panel).setEnabled(state); // get ExamPanels,
			// overriden
			// setEnabled
			// disables button in exampanel
		}
	}

	/*
	 * public void add(Skierowanie pos) {
	 * 
	 * 
	 * 
	 * }
	 */

	public ArrayList<Skierowanie> getExaminations(boolean onlyIfChanged) {

		ArrayList<Skierowanie> exams = new ArrayList<Skierowanie>();
		boolean allTheSame = true;

		for (Component panel : getComponents()) {
			if (panel instanceof ExamPanel) {
				ExamPanel examPanel = (ExamPanel) panel;
				Skierowanie exam = examPanel.retrieveExam();
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
