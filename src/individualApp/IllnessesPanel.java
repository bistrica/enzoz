package individualApp;

import items.Illness;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class IllnessesPanel extends JPanel {

	private HashSet<Illness> allSet;
	private JPanel illnesses;

	private JList<Illness> temporary, constant, all;
	private DefaultListModel<Illness> temporaryModel, constantModel;

	private JButton addTemporary, removeTemporary, addConstant, removeConstant,
			addIllness;
	private String temporaryIllnessString = "Obecne rozpoznania",
			constantIllnessString = "Sta�e rozpoznania", addString = "Dodaj",
			addTemporaryString = "Dodaj rozpoznanie",
			removeTemporaryString = "Usu� rozpoznanie",
			addConstantString = "Dodaj sta�e rozpoznanie",
			removeConstantString = "Usu� sta�e rozpoznanie",
			searchString = "Szukaj";
	private JPanel temporaryPane, constantPane;
	private JDialog illnessDialog;
	private JTextField searchField;

	boolean temporaryWindow;
	private JScrollPane scrollAll;
	private ArrayList<Illness> constantIllnesses;
	private ArrayList<Illness> temporaryIllnesses;
	private IndividualAppView parentView;

	public IllnessesPanel(boolean archive, IndividualAppView parent) {
		this.parentView = parent;
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

		all = new JList<Illness>();

		addTemporary = new JButton(addTemporaryString);
		removeTemporary = new JButton(removeTemporaryString);
		addConstant = new JButton(addConstantString);
		removeConstant = new JButton(removeConstantString);

		temporaryPane = new JPanel();
		constantPane = new JPanel();

		JLabel tempIllLabel = new JLabel(temporaryIllnessString);
		JLabel constIllLabel = new JLabel(constantIllnessString);
		JPanel tempButtons = new JPanel();
		tempButtons.add(addTemporary);
		tempButtons.add(removeTemporary);
		JPanel constButtons = new JPanel();
		constButtons.add(addConstant);
		constButtons.add(removeConstant);

		temporaryPane.setLayout(new BoxLayout(temporaryPane, BoxLayout.Y_AXIS));
		temporaryPane.add(temporaryScroll);
		temporaryPane.add(tempButtons);
		tempIllLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		temporaryPane.setPreferredSize(new Dimension(getWidth() / 2,
				(int) getPreferredSize().getHeight()));
		if (!archive) {
			constantPane
					.setLayout(new BoxLayout(constantPane, BoxLayout.Y_AXIS));
			constantPane.add(constantScroll);
			constantPane.add(constButtons);
			constantScroll.setBorder(new CompoundBorder(new EmptyBorder(20, 50,
					20, 50), new TitledBorder(constantIllnessString)));

			constIllLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

			constantPane.setPreferredSize(new Dimension(getWidth() / 2,
					(int) getPreferredSize().getHeight()));

			setLayout(new GridLayout(2, 1));
			add(constantPane);
		} else {

			setLayout(new BorderLayout());

		}

		temporaryScroll.setBorder(new CompoundBorder(new EmptyBorder(20, 50,
				20, 50), new TitledBorder(temporaryIllnessString)));
		add(temporaryPane);
		searchField = new JTextField(30);

		setListeners();
	}

	private void setListeners() {
		removeTemporary.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (temporary.getSelectedIndex() == -1)
					return;
				temporaryModel.remove(temporary.getSelectedIndex());
			}
		});

		removeConstant.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (constant.getSelectedIndex() == -1)
					return;
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
		illnessDialog = new JDialog(parentView, "",
				Dialog.ModalityType.DOCUMENT_MODAL);
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

				Illness newIll = all.getSelectedValue();
				if (temporaryWindow) {

					if (constantModel.contains(newIll)
							|| temporaryModel.contains(newIll)) {
						return;
					}

					temporaryModel.addElement(newIll);

				} else {
					if (temporaryModel.contains(newIll)
							|| constantModel.contains(newIll)) {
						return;
					}

					constantModel.addElement(newIll);

				}

			}

		});
		illnesses.add(searchPane, BorderLayout.NORTH);
		illnesses.add(addIllness, BorderLayout.SOUTH);

		illnessDialog.add(illnesses);

		int size = 450;
		illnessDialog.setSize(size, size);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		illnessDialog.setLocation((dim.width - size) / 2,
				(dim.height - size) / 2);
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
		temporaryIllnesses = list;

		for (Illness ill : list)
			temporaryModel.addElement(ill);

		repaint();
	}

	public void setConstant(ArrayList<Illness> list) {

		constantIllnesses = list;

		for (Illness ill : list)
			constantModel.addElement(ill);

	}

	public ArrayList<Illness> getConstantIllnesses(boolean onlyIfEdited) {

		boolean changes = false;
		int size = constantModel.getSize();
		if (size != constantIllnesses.size())
			changes = true;

		ArrayList<Illness> cons = new ArrayList<Illness>();
		for (int i = 0; i < size; i++) {
			Illness illness = constantModel.getElementAt(i);
			if (!changes && !constantIllnesses.contains(illness))
				changes = true;
			cons.add(illness);
		}
		if ((onlyIfEdited && !changes) || (!onlyIfEdited && cons.isEmpty()))
			// nie by�o zmian w edycji || nowa wizyta bez rozpozna� sta�ych
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

		// if (illnesses.isEmpty() ||
		if ((onlyIfEdited && !changes))
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
