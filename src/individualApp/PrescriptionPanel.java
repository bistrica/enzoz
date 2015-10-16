package individualApp;

import items.Medicine;
import items.PrescriptedItem;

import java.awt.BorderLayout;
import java.awt.Component;
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

import rendering.DrugTableModel;
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
	private DrugTableModel medicinesModel;

	JButton addNewMedicine;
	private int itemsInitialNo;

	// private ArrayList<PozycjaNaRecepcie> positions;

	public PrescriptionPanel() {

		/*
		 * PozycjaNaRecepcie[][] data = { {new PozycjaNaRecepcie(new
		 * Lek(1,"x","y","z","p"), 1,1,1,1)}, {new PozycjaNaRecepcie(new
		 * Lek(1,"xx","yy","zz","pp"), 1,1,1,1)} }; Object[] columnNames =
		 * {"Name"}; prescriptedMedicines=new
		 * JTable(data,columnNames);//JList<PozycjaNaRecepcie>();
		 */

		prescriptedMedicines = new DrugListPanel();
		// prescriptedMedicines.setInsets=new Insets(0,0,0,0);//setBorder(new
		// EmptyBorder(0, 0, 0, 0));
		// JTable((medicinesModel=new
		// DrugTableModel()));
		/*
		 * DrugCellRenderer renderer=new DrugCellRenderer();
		 * prescriptedMedicines.setDefaultRenderer(Object.class, renderer);
		 * prescriptedMedicines.setDefaultEditor(Object.class, renderer);
		 * 
		 * prescriptedMedicines.setRowHeight(300); medicinesModel.addRow(new
		 * Object[]{new PozycjaNaRecepcie(new Lek(1,"xxx","y","z","p"))});
		 * medicinesModel.addRow(new Object[]{new PozycjaNaRecepcie(new
		 * Lek(2,"2xxx","y","z","p"))}); //medicinesModel.add(new
		 * PozycjaNaRecepcie(new Lek(3,"3xxx","y","z","p")));
		 */

		searchField = new JTextField(30);

		addNewMedicine = new JButton(addString);
		addNewMedicine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openMedicinesDialog();
			}
		});

		all = new JList<Medicine>();
		// medicinesModel=new DefaultTableModel();//<PozycjaNaRecepcie>();
		// prescriptedMedicines.setDefaultRenderer(PozycjaNaRecepcie.class, new
		// DrugCellRenderer());
		// prescriptedMedicines.setModel(medicinesModel);
		// medicinesModel.getCol
		// prescriptedMedicines.getColumnModel().getColumn(0).setCellRenderer(new
		// DrugCellRenderer());

		JScrollPane scrollMedicines = new JScrollPane(prescriptedMedicines);
		scrollMedicines.setBorder(new EmptyBorder(0, 0, 0, 0));
		// scrollMedicines.setPreferredSize(new Dimension(getWidth(),
		// getHeight()));
		// setLayout(new BorderLayout());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addNewMedicine.setAlignmentX(CENTER_ALIGNMENT);
		add(addNewMedicine);// , BorderLayout.NORTH);
		add(scrollMedicines);// , BorderLayout.CENTER);

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
		medicinesDialog = new JDialog();
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
				// medicinesModel.addElement(newDrug);
				// if (!prescriptedMedicines.contains(newDrug)){
				prescriptedMedicines.add(new PrescriptedItem(newDrug));
				repaint();
				// revalidate();
				// medicinesModel.addRow(new Object[]{new
				// PozycjaNaRecepcie(newDrug)});
				// System.out.println("dodano");
				// revalidate();

				// prescriptedMedicines.
			}

		});
		medicines.add(searchPane, BorderLayout.NORTH);
		medicines.add(addMedicine, BorderLayout.SOUTH);

		medicinesDialog.add(medicines);
		medicinesDialog.pack();
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

	/*
	 * public void setPrescriptionData(ArrayList<Lek> list) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

}
