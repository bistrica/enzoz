package individualApp;

import items.Choroba;

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

public class IllnessesPanel extends JPanel {

	// private HashSet<Choroba> tempSet;
	// private HashSet<Choroba> constSet;
	private HashSet<Choroba> allSet;
	JPanel illnesses;

	JList<Choroba> temporary, constant, all;
	// JList<Choroba> all;
	DefaultListModel<Choroba> temporaryModel, constantModel;

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

	public IllnessesPanel() {

		temporary = new JList<Choroba>();
		constant = new JList<Choroba>();

		temporaryModel = new DefaultListModel<Choroba>();
		constantModel = new DefaultListModel<Choroba>();
		temporary.setModel(temporaryModel);
		constant.setModel(constantModel);

		JScrollPane temporaryScroll = new JScrollPane(temporary);
		JScrollPane constantScroll = new JScrollPane(constant);

		temporaryScroll.setSize(getWidth() / 2, 200);
		constantScroll.setSize(getWidth() / 2, 200);

		all = new JList<Choroba>();

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

		setLayout(new GridLayout(1, 2));
		add(temporaryPane);
		add(constantPane);

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
				Choroba newIll = all.getSelectedValue();
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

	public JList<Choroba> getTemporary() {
		return temporary;
	}

	public JList<Choroba> getConstant() {
		return constant;
	}

	public void setAll(ArrayList<Choroba> all) {

		this.allSet = new HashSet<Choroba>();
		for (Choroba i : all)
			this.allSet.add(i);

		Choroba[] ill = new Choroba[all.size()];
		ill = all.toArray(ill);

		this.all = new JList<Choroba>(ill);
		createIllnessDialog();
	}

	private void refresh(String phrase) {

		List<Choroba> col = phrase.isEmpty() ? new ArrayList<Choroba>(allSet)
				: new ArrayList<Choroba>();
		if (!phrase.isEmpty()) {
			phrase = phrase.toLowerCase();
			for (Choroba i : allSet)
				if (i.toString().toLowerCase().contains(phrase))
					col.add(i);
		}
		Collections.sort(col, new Comparator<Choroba>() {
			@Override
			public int compare(Choroba i1, Choroba i2) {
				return i1.toString().compareTo(i2.toString());
			}
		});
		Choroba[] all = new Choroba[col.size()];
		all = col.toArray(all);

		this.illnesses.remove(this.scrollAll);
		this.all = new JList<Choroba>(all);
		this.scrollAll = new JScrollPane(this.all);
		this.illnesses.add(this.scrollAll, BorderLayout.CENTER);

		illnessDialog.revalidate();

	}

	public void setTemporary(ArrayList<Choroba> list) {
		// temporaryModel=new DefaultListModel<Choroba>(); //zbêdne raczej

		for (Choroba ill : list)
			temporaryModel.addElement(ill);

		repaint();
	}

	public void setConstant(ArrayList<Choroba> list) {
		// constantModel=new DefaultListModel<Choroba>(); //zbêdne raczej

		for (Choroba ill : list)
			constantModel.addElement(ill);

	}

	@Override
	public void setEnabled(boolean state) {
		addTemporary.setEnabled(state);
		removeTemporary.setEnabled(state);
		addConstant.setEnabled(state);
		removeConstant.setEnabled(state);
	}

}
