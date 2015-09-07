package individualApp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import items.Choroba;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class IllnessesPanel extends JPanel {

	//private HashSet<Choroba> tempSet;
	//private HashSet<Choroba> constSet;
	private HashSet<Choroba> allSet;
	JPanel illnesses;
	
	JList<Choroba> temporary, constant, all;
	//JList<Choroba> all;
	DefaultListModel<Choroba> temporaryModel, constantModel;
	
	JButton addTemporary, removeTemporary, addConstant, removeConstant, addIllness;
	String temporaryIllnessString="Obecne rozpoznania", constantIllnessString="Sta³e rozpoznania";
	String addString="Dodaj", addTemporaryString="Dodaj rozpoznanie", removeTemporaryString="Usuñ rozpoznanie", addConstantString="Dodaj sta³e rozpoznanie", removeConstantString="Usuñ sta³e rozpoznanie", searchString="Szukaj";
	JPanel temporaryPane, constantPane;
	JDialog illnessDialog;
	JTextField searchField;
	
	boolean temporaryWindow;
	private JScrollPane scrollAll;
	//private JScrollPane temporaryScroll;
	//private JScrollPane constantScroll;
	
	public IllnessesPanel() {
		//tempSet=new HashSet<Choroba>();
		//constSet=new HashSet<Choroba>();
		
		//JList<Choroba> 
		temporary=new JList<Choroba>();
		//JList<Choroba> 
		constant=new JList<Choroba>();
		
		temporaryModel=new DefaultListModel<Choroba>();
		constantModel=new DefaultListModel<Choroba>();
		temporary.setModel(temporaryModel);
		constant.setModel(constantModel);
		
		
		JScrollPane temporaryScroll=new JScrollPane(temporary);
		JScrollPane constantScroll=new JScrollPane(constant);
		
		temporaryScroll.setSize(getWidth()/2, 200);
		constantScroll.setSize(getWidth()/2, 200);
		
		all=new JList<Choroba>();
		
		addTemporary=new JButton(addTemporaryString);		
		removeTemporary=new JButton(removeTemporaryString);
		addConstant=new JButton(addConstantString);
		removeConstant=new JButton(removeConstantString);
		
		temporaryPane=new JPanel();
		constantPane=new JPanel();
		
		JLabel tempIll=new JLabel(temporaryIllnessString);
		JLabel constIll=new JLabel(constantIllnessString);
		JPanel tempButtons=new JPanel();
		tempButtons.add(addTemporary);
		tempButtons.add(removeTemporary);
		JPanel constButtons=new JPanel();
		constButtons.add(addConstant);
		constButtons.add(removeConstant);
		
		temporaryPane.setLayout(new BoxLayout(temporaryPane, BoxLayout.Y_AXIS));
		temporaryPane.add(tempIll);
		temporaryPane.add(temporaryScroll);
		temporaryPane.add(tempButtons);
		
		constantPane.setLayout(new BoxLayout(constantPane, BoxLayout.Y_AXIS));
		constantPane.add(constIll);
		constantPane.add(constantScroll);
		constantPane.add(constButtons);
		
		setLayout(new GridLayout(1,2));
		add(temporaryPane);
		add(constantPane);
		
		searchField=new JTextField(30);
		
		setListeners();
	}

	private void setListeners(){
		removeTemporary.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: potwierdzenie
				temporaryModel.remove(temporary.getSelectedIndex());
			}
		});
		
		removeConstant.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: potwierdzenie
				constantModel.remove(constant.getSelectedIndex());
			}
		});
		
		
		addTemporary.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: potwierdzenie
				temporaryWindow=true;
				openIllnessDialog(temporary);
			}

		});
		
		addConstant.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: potwierdzenie
				temporaryWindow=false;
				openIllnessDialog(constant);
			}
		});
		
		
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
		System.out.println("filter "+searchedPhrase);
		/*if (searchedPhrase.isEmpty())
			refreshAll();
		else
			refreshFiltered(searchedPhrase);	*/
		
//		repaintRefreshed();
		refresh(searchedPhrase);
	}
	
	private void createIllnessDialog() {
		illnessDialog=new JDialog();
		illnesses=new JPanel();
		illnesses.setLayout(new BorderLayout());
		//all.setSize(300,200);
		scrollAll=new JScrollPane(all);
		//scrollPane.setSize(300, 200);
		illnesses.add(scrollAll,BorderLayout.CENTER);
		
		//Szukanie po frazie
		JPanel searchPane=new JPanel();
		searchPane.add(new JLabel(searchString));
		searchPane.add(searchField);
		
		
		addIllness=new JButton(addString);
		addIllness.addActionListener(new ActionListener() {
			
			

			@Override
			public void actionPerformed(ActionEvent e) {
				//DefaultListModel<Choroba> model=new DefaultListModel<Choroba>();
				//ListModel oldModel=chosen.getModel();
				//for (Choroba ill: chosen.getModel().)
				//chosen.getModel().addElement(all.getSelectedValue());
				Choroba newIll=all.getSelectedValue();
				if (temporaryWindow) {
					
					if (constantModel.contains(newIll) || temporaryModel.contains(newIll)) {
						//TODO: check or override equals; show info  
						return;
					}
					
					temporaryModel.addElement(newIll);
					//DefaultListModetemporary.getModel().;
					/*tempSet.add(newIll);
					
					Choroba[] ill=new Choroba[tempSet.size()];
					ill=tempSet.toArray(ill);
					setTemporary((Choroba[])(ill));*/
					
				}
				else {
					if (temporaryModel.contains(newIll) || constantModel.contains(newIll)) {
						//TODO: check or override equals; show info  
						return;
					}
					
					constantModel.addElement(newIll);
					/*constSet.add(newIll);
					
					Choroba[] ill=new Choroba[constSet.size()];
					ill=constSet.toArray(ill);
					setConstant(ill);*/
				}
				
				
			}

			
		});
		illnesses.add(searchPane,BorderLayout.NORTH);
		illnesses.add(addIllness,BorderLayout.SOUTH);
		
		illnessDialog.add(illnesses);
		
		//illnessDialog.setSize(300,200);
		illnessDialog.pack(); //check?
	}
	
	
	
	private void openIllnessDialog(JList<Choroba> temporary) {
		searchField.setText("");
		illnessDialog.setVisible(true);
		
	}
	
	
	public JList<Choroba> getTemporary() {
		return temporary;
	}

	/*public void setTemporary(Choroba[] temporary) {
		this.temporary = new JList<Choroba>(temporary);
	}*/

	public JList<Choroba> getConstant() {
		return constant;
	}

	/*public void setConstant(Choroba[] constant) {
		this.constant = new JList<Choroba>(constant);
	}*/
	
	public void setAll(Choroba[] all) {
		
		this.allSet=new HashSet<Choroba>();
		for (Choroba i: all)
			this.allSet.add(i);
		this.all = new JList<Choroba>(all);
		createIllnessDialog();
	}
	
	
	/*private void refreshAll() {
		Choroba[] all=new Choroba[allSet.size()];
		
		all=allSet.toArray(all);
		//repaintRefreshed(all);
		//createIllnessDialog();
	}
	
	private void refreshFiltered(String phrase) {
		ArrayList<Choroba> ill=new ArrayList<Choroba>();
		for (Choroba i: allSet)
			if (i.toString().contains(phrase))
				ill.add(i);

		Choroba[] all=new Choroba[ill.size()];
		all=ill.toArray(all);
		//repaintRefreshed(all);
	}
	
	private void repaintRefreshed(Choroba[] all) {
		this.all = new JList<Choroba>(all);
		illnessDialog.repaint();
	}*/
	
	private void refresh(String phrase) {
		
		List<Choroba> col= phrase.isEmpty() ? new ArrayList<Choroba>(allSet) : new ArrayList<Choroba>();
		if (!phrase.isEmpty()){
			phrase=phrase.toLowerCase();
			for (Choroba i: allSet)
				if (i.toString().toLowerCase().contains(phrase))
					col.add(i);
		}
		Collections.sort(col, new Comparator<Choroba>() {
			@Override
			public int compare(Choroba i1, Choroba i2) {
				return i1.toString().compareTo(i2.toString());
			}
		});
		Choroba[] all=new Choroba[col.size()];
		all=col.toArray(all);
	
		this.illnesses.remove(this.scrollAll);
		this.all = new JList<Choroba>(all);
		this.scrollAll=new JScrollPane(this.all);
		this.illnesses.add(this.scrollAll,BorderLayout.CENTER);
		
		//this.illnesses.repaint();
		illnessDialog.revalidate();
		
	}
	
}

