package GUI_items;

import items.Clinic;
import items.Examination;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class ExamPanel extends JPanel {

	private Examination position;

	private JTextArea description;
	private JComboBox<Clinic> clinics;
	private String removeString = "Usuñ skierowanie";
	JButton removeButton;

	private String descLabelString;

	private String clinicLabelString;

	public ExamPanel(Clinic[] clinics) {
		// this.position=pos;

		this.clinics = new JComboBox<Clinic>(clinics);

		removeButton = new JButton(removeString);
		JPanel thisPanel = this;
		removeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Container cont = thisPanel.getParent(); // ew. tu change
				cont.remove(thisPanel);
				cont.revalidate();// repaint();
				// System.out.println(cont + "*"
				// + Thread.currentThread().getName());

			}
		});

		description = new JTextArea(5, 50);
		description.setLineWrap(true);
		JScrollPane examination = new JScrollPane(description);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(removeButton);

		JLabel descLabel = new JLabel(descLabelString);
		JLabel clinicLabel = new JLabel(clinicLabelString);

		JPanel examPane = new JPanel();
		examPane.add(descLabel);
		examPane.add(examination);

		JPanel clinicPane = new JPanel();
		clinicPane.add(clinicLabel);
		clinicPane.add(this.clinics);

		setBorder(new EtchedBorder());
		setLayout(new BorderLayout());
		add(clinicPane, BorderLayout.NORTH);
		add(examPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setMinimumSize(getPreferredSize());
		setMaximumSize(new Dimension(getPreferredSize().width, 200));

		setBorder(new EmptyBorder(5, 0, 0, 0));
	}

	public Examination getPosition() {
		return position;
	}

	public void setPosition(Examination position) {
		this.position = position;
	}

	public void setClinic(Clinic clinic) {
		this.clinics.setSelectedItem(clinic);
	}

	public void setDescription(String desc) {
		this.description.setText(desc);
	}

	@Override
	public void setEnabled(boolean state) {
		removeButton.setEnabled(state);
		description.setEditable(state);
		clinics.setEnabled(state);
	}

	public Examination retrieveExam() {
		return new Examination((Clinic) (clinics.getSelectedItem()),
				description.getText().trim());
	}

}
