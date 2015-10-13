package individualApp;

import items.Illness;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import GUI_items.BPanel;

public class IllnessesPanel extends BPanel {

	// private HashSet<Choroba> tempSet;
	// private HashSet<Choroba> constSet;
	private HashSet<Illness> allSet;
	JPanel illnesses;

	JList<Illness> temporary, constant, all;
	// JList<Choroba> all;
	DefaultListModel<Illness> temporaryModel, constantModel;

	JButton addTemporary, removeTemporary, addConstant, removeConstant,
			addIllness;
	String temporaryIllnessString = "Obecne rozpoznania",
			constantIllnessString = "Sta³e rozpoznania";
	String addString = "Dodaj", addTemporaryString = "Dodaj rozpoznanie",
			removeTemporaryString = "Usuñ rozpoznanie",
			addConstantString = "Dodaj sta³e rozpoznanie",
			removeConstantString = "Usuñ sta³e rozpoznanie",
			searchString = "Szukaj";
	JPanel temporaryPane, constantPane;
	JDialog illnessDialog;
	JTextField searchField;

	boolean temporaryWindow;
	private JScrollPane scrollAll;
	private ArrayList<Illness> constantIllnesses;
	private ArrayList<Illness> temporaryIllnesses;

	public IllnessesPanel(boolean archive) {

		temporary = new JList<Illness>();
		constant = new JList<Illness>();
		temporaryIllnesses = new ArrayList<Illness>();
		constantIllnesses = new ArrayList<Illness>();

		temporaryModel = new DefaultListModel<Illness>();
		constantModel = new DefaultListModel<Illness>();
		temporary.setModel(temporaryModel);
		constant.setModel(constantModel);

		JScrollPane temporaryScroll = new JScrollPane(temporary);
		JScrollPane constantScroll = new JScrollPane(constant);

		temporaryScroll.setSize(getWidth() / 2, 200);
		// temporaryScroll.setBackground(Color.GREEN);
		constantScroll.setSize(getWidth() / 2, 200);
		// constantScroll.setBackground(Color.BLUE);

		all = new JList<Illness>();

		addTemporary = new JButton(addTemporaryString);
		removeTemporary = new JButton(removeTemporaryString);
		addConstant = new JButton(addConstantString);
		removeConstant = new JButton(removeConstantString);

		temporaryPane = new JPanel();
		constantPane = new JPanel();

		JLabel tempIll = new JLabel(temporaryIllnessString);
		JLabel constIll = new JLabel(constantIllnessString);
		JPanel tempButtons = new JPanel();
		tempButtons.add(addTemporary);
		tempButtons.add(removeTemporary);
		JPanel constButtons = new JPanel();
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

		if (!archive) {
			setLayout(new GridLayout(1, 2));
			add(constantPane);
		}

		add(temporaryPane);
		searchField = new JTextField(30);

		setListeners();
	}

	private void setListeners() {
		removeTemporary.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: potwierdzenie
				temporaryModel.remove(temporary.getSelectedIndex());
			}
		});

		removeConstant.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: potwierdzenie
				constantModel.remove(constant.getSelectedIndex());
			}
		});

		addTemporary.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: potwierdzenie
				temporaryWindow = true;
				openIllnessDialog();
			}

		});

		addConstant.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: potwierdzenie
				temporaryWindow = false;
				openIllnessDialog();
			}
		});

		searchField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				System.out.println("rem");
				filter();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				System.out.println("ins");
				filter();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("cha");
				filter();
			}
		});

	}

	private void filter() {
		String searchedPhrase = searchField.getText().trim();
		refresh(searchedPhrase);
	}

	private void createIllnessDialog() {
		illnessDialog = new JDialog();
		illnesses = new JPanel();
		illnesses.setLayout(new BorderLayout());
		scrollAll = new JScrollPane(all);
		illnesses.add(scrollAll, BorderLayout.CENTER);

		// Szukanie po frazie
		JPanel searchPane = new JPanel();
		searchPane.add(new JLabel(searchString));
		searchPane.add(searchField);

		addIllness = new JButton(addString);
		addIllness.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// DefaultListModel<Choroba> model=new
				// DefaultListModel<Choroba>();
				// ListModel oldModel=chosen.getModel();
				// for (Choroba ill: chosen.getModel().)
				// chosen.getModel().addElement(all.getSelectedValue());
				Illness newIll = all.getSelectedValue();
				if (temporaryWindow) {

					if (constantModel.contains(newIll)
							|| temporaryModel.contains(newIll)) {
						// TODO: check or override equals; show info
						return;
					}

					temporaryModel.addElement(newIll);

				} else {
					if (temporaryModel.contains(newIll)
							|| constantModel.contains(newIll)) {
						// TODO: check or override equals; show info
						return;
					}

					constantModel.addElement(newIll);

				}

			}

		});
		illnesses.add(searchPane, BorderLayout.NORTH);
		illnesses.add(addIllness, BorderLayout.SOUTH);

		illnessDialog.add(illnesses);
		illnessDialog.pack(); // check?
	}

	private void openIllnessDialog() {
		searchField.setText("");
		illnessDialog.setVisible(true);

	}

	public JList<Illness> getTemporary() {
		return temporary;
	}

	public JList<Illness> getConstant() {
		return constant;
	}

	public void setAll(ArrayList<Illness> all) {

		this.allSet = new HashSet<Illness>();
		for (Illness i : all)
			this.allSet.add(i);

		Illness[] ill = new Illness[all.size()];
		ill = all.toArray(ill);

		this.all = new JList<Illness>(ill);
		createIllnessDialog();
	}

	private void refresh(String phrase) {

		List<Illness> col = phrase.isEmpty() ? new ArrayList<Illness>(allSet)
				: new ArrayList<Illness>();
		if (!phrase.isEmpty()) {
			phrase = phrase.toLowerCase();
			for (Illness i : allSet)
				if (i.toString().toLowerCase().contains(phrase))
					col.add(i);
		}
		Collections.sort(col, new Comparator<Illness>() {
			@Override
			public int compare(Illness i1, Illness i2) {
				return i1.toString().compareTo(i2.toString());
			}
		});
		Illness[] all = new Illness[col.size()];
		all = col.toArray(all);

		this.illnesses.remove(this.scrollAll);
		this.all = new JList<Illness>(all);
		this.scrollAll = new JScrollPane(this.all);
		this.illnesses.add(this.scrollAll, BorderLayout.CENTER);

		illnessDialog.revalidate();

	}

	public void setTemporary(ArrayList<Illness> list) {
		// temporaryModel=new DefaultListModel<Choroba>(); //zbêdne raczej
		temporaryIllnesses = list;

		for (Illness ill : list)
			temporaryModel.addElement(ill);

		repaint();
	}

	public void setConstant(ArrayList<Illness> list) {
		// constantModel=new DefaultListModel<Choroba>(); //zbêdne raczej

		constantIllnesses = list;

		for (Illness ill : list)
			constantModel.addElement(ill);

	}

	/*
	 * public ArrayList<Choroba> getTemporaryIllnesses() {
	 * 
	 * ArrayList<Choroba> temp = new ArrayList<Choroba>();//
	 * Arrays.asList(temporaryModel.toArray(array)); int size =
	 * temporaryModel.getSize(); for (int i = 0; i < size; i++)
	 * temp.add(temporaryModel.getElementAt(i)); if (temp.isEmpty()) temp =
	 * null; return temp; }
	 */

	public ArrayList<Illness> getConstantIllnesses(boolean onlyIfEdited) {

		boolean changes = false;
		int size = constantModel.getSize();
		if (size != constantIllnesses.size())
			changes = true;

		ArrayList<Illness> cons = new ArrayList<Illness>();// Arrays.asList(temporaryModel.toArray(array));

		for (int i = 0; i < size; i++) {
			Illness illness = constantModel.getElementAt(i);
			if (!changes && !constantIllnesses.contains(illness))
				changes = true;
			cons.add(illness);
		}
		if ((onlyIfEdited && !changes) || (!onlyIfEdited && cons.isEmpty()))
			// nie by³o zmian w edycji || nowa wizyta bez rozpoznañ sta³ych
			cons = null;
		return cons;
	}

	public ArrayList<Illness> getAllCurrentIllnesses(boolean onlyIfEdited) {

		boolean changes = false;
		int size = temporaryModel.getSize();
		if (size != temporaryIllnesses.size())
			changes = true;

		ArrayList<Illness> illnesses = getConstantIllnesses(onlyIfEdited);
		if (illnesses == null)
			illnesses = new ArrayList<Illness>();
		else
			changes = true;

		for (int i = 0; i < size; i++) {
			Illness illness = temporaryModel.getElementAt(i);
			if (!changes && !temporaryIllnesses.contains(illness))
				changes = true;
			illnesses.add(illness);
		}

		if (illnesses.isEmpty() || (onlyIfEdited && !changes))
			illnesses = null;
		return illnesses;
	}

	@Override
	public void setEnabled(boolean state) {
		addTemporary.setEnabled(state);
		removeTemporary.setEnabled(state);
		addConstant.setEnabled(state);
		removeConstant.setEnabled(state);
	}

}
