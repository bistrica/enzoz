package individualApp;

import items.Medicine;
import items.PrescriptedItem;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import GUI_items.DrugListPanel;
import GUI_items.DrugPanel;
import exceptions.BadDataException;

public class PrescriptionPanel extends JPanel {

	/*
	 * private String dosesString="Iloœæ dawek/porcjê"; private String
	 * discountString="Refundacja"; private String
	 * packageString="Iloœæ opakowañ"; private String
	 * ingestionString="Iloœæ za¿yæ/dzieñ";
	 */

	private HashSet<Medicine> allSet;
	private JList<Medicine> all;
	private DrugListPanel prescriptedMedicines;
	private JPanel medicines;
	private JScrollPane scrollAll;
	private JDialog medicinesDialog;
	private String searchString = "Szukaj";
	private JTextField searchField;
	private String addString = "Dodaj lek";
	private JButton addMedicine;

	JButton addNewMedicine;
	private int itemsInitialNo;
	private IndividualAppView parentView;

	public PrescriptionPanel(IndividualAppView parent) {

		this.parentView = parent;
		prescriptedMedicines = new DrugListPanel();

		searchField = new JTextField(30);

		addNewMedicine = new JButton(addString);
		addNewMedicine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openMedicinesDialog();
			}
		});

		all = new JList<Medicine>();

		JScrollPane scrollMedicines = new JScrollPane(prescriptedMedicines);
		scrollMedicines.setBorder(new EmptyBorder(0, 0, 0, 0));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addNewMedicine.setAlignmentX(CENTER_ALIGNMENT);
		add(addNewMedicine);
		add(scrollMedicines);

		setListeners();
	}

	public void setListeners() {

		searchField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				filter();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				filter();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filter();
			}
		});

	}

	private void filter() {
		String searchedPhrase = searchField.getText().trim();
		refresh(searchedPhrase);
	}

	public void setAll(ArrayList<Medicine> all) {

		this.allSet = new HashSet<Medicine>();
		for (Medicine i : all)
			this.allSet.add(i);

		Medicine[] ill = new Medicine[all.size()];
		ill = all.toArray(ill);

		this.all = new JList<Medicine>(ill);
		createMedicinesDialog();
	}

	private void refresh(String phrase) {

		List<Medicine> col = phrase.isEmpty() ? new ArrayList<Medicine>(allSet)
				: new ArrayList<Medicine>();
		if (!phrase.isEmpty()) {
			phrase = phrase.toLowerCase();
			for (Medicine i : allSet)
				if (i.toString().toLowerCase().contains(phrase))
					col.add(i);
		}
		Collections.sort(col, new Comparator<Medicine>() {
			@Override
			public int compare(Medicine i1, Medicine i2) {
				return i1.toString().compareTo(i2.toString());
			}
		});
		Medicine[] all = new Medicine[col.size()];
		all = col.toArray(all);

		this.medicines.remove(this.scrollAll);
		this.all = new JList<Medicine>(all);
		this.scrollAll = new JScrollPane(this.all);
		this.medicines.add(this.scrollAll, BorderLayout.CENTER);

		medicinesDialog.revalidate();

	}

	private void createMedicinesDialog() {
		medicinesDialog = new JDialog(parentView, "",
				Dialog.ModalityType.DOCUMENT_MODAL);
		medicines = new JPanel();
		medicines.setLayout(new BorderLayout());
		scrollAll = new JScrollPane(all);
		medicines.add(scrollAll, BorderLayout.CENTER);

		// Szukanie po frazie
		JPanel searchPane = new JPanel();
		searchPane.add(new JLabel(searchString));
		searchPane.add(searchField);

		addMedicine = new JButton(addString);
		addMedicine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// System.out.println("click");
				Medicine newDrug = all.getSelectedValue();

				prescriptedMedicines.add(new PrescriptedItem(newDrug));
				repaint();

			}

		});
		medicines.add(searchPane, BorderLayout.NORTH);
		medicines.add(addMedicine, BorderLayout.SOUTH);

		medicinesDialog.add(medicines);
		// medicinesDialog.pack();

		int size = 450;
		medicinesDialog.setSize(size, size);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		medicinesDialog.setLocation((dim.width - size) / 2,
				(dim.height - size) / 2);

		// medicinesDialog.setModalityType(ModalityType.DOCUMENT_MODAL);//
		// APPLICATION_MODAL);

		// medicinesDialog.addWindowListener(new WindowAdapter() {

		// });
	}

	private void openMedicinesDialog() {
		searchField.setText("");
		medicinesDialog.setVisible(true);

	}

	public void setPrescription(ArrayList<PrescriptedItem> positions) {
		itemsInitialNo = positions.size();
		for (PrescriptedItem pos : positions)
			prescriptedMedicines.add(pos);
		repaint();

	}

	@Override
	public void setEnabled(boolean state) {
		addNewMedicine.setEnabled(state);
		for (Component panel : prescriptedMedicines.getComponents()) {
			panel.setEnabled(state); // get DrugPanels, overriden setEnabled
										// disables button in drugpanel
		}
	}

	public ArrayList<PrescriptedItem> getPrescriptedPositions(
			boolean onlyIfChanged) throws BadDataException {

		ArrayList<PrescriptedItem> positions = new ArrayList<PrescriptedItem>();

		boolean allTheSame = true;
		Component[] items = prescriptedMedicines.getComponents();
		if (items.length != itemsInitialNo)
			allTheSame = false;

		for (Component panel : items) {
			// System.out.println("III");
			if (panel instanceof DrugPanel) {
				DrugPanel drugPanel = (DrugPanel) panel;
				PrescriptedItem pos = drugPanel.retrievePrescribedPosition();
				if (!pos.equals(drugPanel.getPosition()))
					allTheSame = false;
				positions.add(pos);
			}
		}

		System.out.println(allTheSame + " POS: " + positions + " : "
				+ positions.size());

		if (positions.isEmpty())
			positions = null;

		return (onlyIfChanged && allTheSame) ? null : positions;

	}

}
