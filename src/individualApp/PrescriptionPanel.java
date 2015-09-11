package individualApp;

import items.Lek;
import items.Lek;
import items.PozycjaNaRecepcie;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import GUI_items.DrugListPanel;
import rendering.DrugCellRenderer;
import rendering.DrugTableModel;

public class PrescriptionPanel extends JPanel {

	private String dosesString="Iloœæ dawek/porcjê";
	private String discountString="Refundacja";
	private String packageString="Iloœæ opakowañ";
	private String ingestionString="Iloœæ za¿yæ/dzieñ";
	
	
	private HashSet<Lek> allSet;
	private JList<Lek> all;
	private DrugListPanel prescriptedMedicines;
	private JPanel medicines;
	private JScrollPane scrollAll;
	private JDialog medicinesDialog;
	private String searchString="Szukaj";
	private JTextField searchField;
	private String addString="Dodaj lek";
	private JButton addMedicine;
	private DrugTableModel medicinesModel;

	//private ArrayList<PozycjaNaRecepcie> positions;

	public PrescriptionPanel() {
	
		 /*PozycjaNaRecepcie[][] data = {
                 {new PozycjaNaRecepcie(new Lek(1,"x","y","z","p"), 1,1,1,1)},
                 {new PozycjaNaRecepcie(new Lek(1,"xx","yy","zz","pp"), 1,1,1,1)}
                };
		 Object[] columnNames = {"Name"};
		prescriptedMedicines=new JTable(data,columnNames);//JList<PozycjaNaRecepcie>();*/
		
		prescriptedMedicines = new DrugListPanel();//JTable((medicinesModel=new DrugTableModel()));
		/*DrugCellRenderer renderer=new DrugCellRenderer();
		prescriptedMedicines.setDefaultRenderer(Object.class, renderer);
		prescriptedMedicines.setDefaultEditor(Object.class, renderer);
		
		prescriptedMedicines.setRowHeight(300);
		medicinesModel.addRow(new Object[]{new PozycjaNaRecepcie(new Lek(1,"xxx","y","z","p"))});
		medicinesModel.addRow(new Object[]{new PozycjaNaRecepcie(new Lek(2,"2xxx","y","z","p"))});
		//medicinesModel.add(new PozycjaNaRecepcie(new Lek(3,"3xxx","y","z","p")));*/
		
		
		searchField=new JTextField(30);
		
		JButton addNewMedicine=new JButton(addString);
		addNewMedicine.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openMedicinesDialog();
			}
		});
		
		
		all=new JList<Lek>();
		//medicinesModel=new DefaultTableModel();//<PozycjaNaRecepcie>();
		//prescriptedMedicines.setDefaultRenderer(PozycjaNaRecepcie.class, new DrugCellRenderer());
		//prescriptedMedicines.setModel(medicinesModel);
		//medicinesModel.getCol
//		prescriptedMedicines.getColumnModel().getColumn(0).setCellRenderer(new DrugCellRenderer());
		
		JScrollPane scrollMedicines=new JScrollPane(prescriptedMedicines);
		//scrollMedicines.setPreferredSize(new Dimension(getWidth(), getHeight()));
		//setLayout(new BorderLayout());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addNewMedicine.setAlignmentX(CENTER_ALIGNMENT);
		add(addNewMedicine);//, BorderLayout.NORTH);
		add(scrollMedicines);//, BorderLayout.CENTER);
		
		
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
		String searchedPhrase=searchField.getText().trim();
		refresh(searchedPhrase);
	}


	public void setAll(ArrayList<Lek> all) {
		
		this.allSet=new HashSet<Lek>();
		for (Lek i: all)
			this.allSet.add(i);
		
		Lek[] ill=new Lek[all.size()];
		ill=all.toArray(ill);
		
		this.all = new JList<Lek>(ill);
		createMedicinesDialog();
	}
	
	private void refresh(String phrase) {
		
		List<Lek> col= phrase.isEmpty() ? new ArrayList<Lek>(allSet) : new ArrayList<Lek>();
		if (!phrase.isEmpty()){
			phrase=phrase.toLowerCase();
			for (Lek i: allSet)
				if (i.toString().toLowerCase().contains(phrase))
					col.add(i);
		}
		Collections.sort(col, new Comparator<Lek>() {
			@Override
			public int compare(Lek i1, Lek i2) {
				return i1.toString().compareTo(i2.toString());
			}
		});
		Lek[] all=new Lek[col.size()];
		all=col.toArray(all);
	
		this.medicines.remove(this.scrollAll);
		this.all = new JList<Lek>(all);
		this.scrollAll=new JScrollPane(this.all);
		this.medicines.add(this.scrollAll,BorderLayout.CENTER);
		
		medicinesDialog.revalidate();
		
	}
	
	
	private void createMedicinesDialog() {
		medicinesDialog=new JDialog();
		medicines=new JPanel();
		medicines.setLayout(new BorderLayout());
		scrollAll=new JScrollPane(all);
		medicines.add(scrollAll,BorderLayout.CENTER);
		
		//Szukanie po frazie
		JPanel searchPane=new JPanel();
		searchPane.add(new JLabel(searchString));
		searchPane.add(searchField);
		
		
		addMedicine=new JButton(addString);
		addMedicine.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("click");
				Lek newDrug=all.getSelectedValue();
				//medicinesModel.addElement(newDrug);
				//if (!prescriptedMedicines.contains(newDrug)){
					prescriptedMedicines.add(new PozycjaNaRecepcie(newDrug));
					repaint();
					//revalidate();
					//medicinesModel.addRow(new Object[]{new PozycjaNaRecepcie(newDrug)});
					//System.out.println("dodano");
					//revalidate();
				
					//prescriptedMedicines.
			}

			
		});
		medicines.add(searchPane,BorderLayout.NORTH);
		medicines.add(addMedicine,BorderLayout.SOUTH);
		
		medicinesDialog.add(medicines);
		medicinesDialog.pack();
	}
	
	
	
	private void openMedicinesDialog() {
		searchField.setText("");
		medicinesDialog.setVisible(true);
		
	}



	public void setPrescription(ArrayList<PozycjaNaRecepcie> positions) {
		for (PozycjaNaRecepcie pos: positions)
			prescriptedMedicines.add(pos);
		repaint();
		
		
	}

	/*public void setPrescriptionData(ArrayList<Lek> list) {
		// TODO Auto-generated method stub
		
	}*/
	
}
