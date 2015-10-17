package GUI_items;

import items.Medicine;
import items.PrescriptedItem;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import exceptions.BadDataException;

public class DrugPanel extends JPanel {

	private String dosesString = "Iloœæ dawek/porcjê";
	private String discountString = "Refundacja";
	private String packageString = "Iloœæ opakowañ";
	private String ingestionString = "Iloœæ za¿yæ/dzieñ";
	private String removeString = "Usuñ pozycjê";

	JLabel typeLabel;
	JLabel nameDoseLabel;
	JTextField dosesCount;
	JTextField packageCount;
	JTextField discountVal;
	JTextField ingestionCount;
	private PrescriptedItem position;
	private DrugListPanel parent;
	JButton removeButton;

	public DrugPanel(PrescriptedItem pos, DrugListPanel parent) {

		this.parent = parent;
		position = pos;
		Medicine drug = pos.getMedicine();
		String labelString = drug.getName();

		if (!drug.getDose().isEmpty())
			labelString += ", " + drug.getDose();

		nameDoseLabel = new JLabel(labelString);
		typeLabel = new JLabel(drug.getFormulation() + ", "
				+ drug.getPackageDescription());
		nameDoseLabel.setFont(new Font("Arial", Font.BOLD, 20));
		typeLabel.setFont(new Font("Arial", Font.PLAIN, 15));

		setBorder(new EtchedBorder());

		JPanel labels = new JPanel();
		labels.setLayout(new BoxLayout(labels, BoxLayout.PAGE_AXIS));// //Y_AXIS));
		labels.setAlignmentX(CENTER_ALIGNMENT);
		labels.add(nameDoseLabel);
		labels.add(typeLabel);

		JPanel modifiedData = new JPanel();

		dosesCount = new JTextField(3);
		packageCount = new JTextField(3);
		discountVal = new JTextField(3);
		ingestionCount = new JTextField(3);

		dosesCount.setText("" + pos.getDosesCountPerIngestion());
		packageCount.setText("" + pos.getPackageCount());
		discountVal.setText((int) (pos.getDiscountPercent() * 100) + "%");
		ingestionCount.setText("" + pos.getIngestionCount());

		JLabel dosesCountLabel = new JLabel(dosesString);
		JLabel packageCountLabel = new JLabel(packageString);
		JLabel discountValLabel = new JLabel(discountString);
		JLabel ingestionCountLabel = new JLabel(ingestionString);

		modifiedData.add(ingestionCountLabel);
		modifiedData.add(ingestionCount);
		modifiedData.add(dosesCountLabel);
		modifiedData.add(dosesCount);
		modifiedData.add(packageCountLabel);
		modifiedData.add(packageCount);
		modifiedData.add(discountValLabel);
		modifiedData.add(discountVal);
		/*
		 * JPanel ingGrid=new JPanel(); JPanel doseGrid=new JPanel(); JPanel
		 * pckgGrid=new JPanel(); JPanel discGrid=new JPanel();
		 * 
		 * ingGrid.add(ingestionCountLabel); ingGrid.add(ingestionCount);
		 * doseGrid.add(dosesCountLabel); doseGrid.add(dosesCount);
		 * pckgGrid.add(packageCountLabel); pckgGrid.add(packageCount);
		 * discGrid.add(discountValLabel); discGrid.add(discountVal);
		 * 
		 * modifiedData.add(ingGrid); modifiedData.add(doseGrid);
		 * modifiedData.add(pckgGrid); modifiedData.add(discGrid);
		 */
		GroupLayout layout = new GroupLayout(modifiedData);
		modifiedData.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(ingestionCountLabel)
				.addComponent(dosesCountLabel));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(ingestionCount).addComponent(dosesCount));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(packageCountLabel).addComponent(discountValLabel));
		hGroup.addGroup(layout.createParallelGroup().addComponent(packageCount)
				.addComponent(discountVal));

		layout.setHorizontalGroup(hGroup);
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(ingestionCountLabel).addComponent(ingestionCount)
				.addComponent(packageCountLabel).addComponent(packageCount));

		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(dosesCountLabel).addComponent(dosesCount)
				.addComponent(discountValLabel).addComponent(discountVal));

		layout.setVerticalGroup(vGroup);

		removeButton = new JButton(removeString);
		JPanel thisPanel = this;
		removeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * Container cont = thisPanel.getParent();
				 * cont.remove(thisPanel); cont.revalidate();
				 */

				parent.remove(thisPanel);
				parent.updateCounter();
				parent.revalidate();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(removeButton);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //
		// GridBagLayout());

		// labels.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

		labels.setAlignmentX(CENTER_ALIGNMENT);// LEFT_ALIGNMENT);
		labels.setPreferredSize(new Dimension(320, 80));
		labels.setMinimumSize(new Dimension(320, 80));

		setBorder(new EmptyBorder(20, 30, 0, 30));
		// labels.setBackground(Color.RED);
		// modifiedData.setBackground(Color.BLUE);
		// buttonPanel.setBackground(Color.GREEN);

		// modifiedData.setBorder(BorderFactory.createEmptyBorder(50, 20, 50,
		// 20));
		// buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

		add(labels);// , BorderLayout.NORTH);
		add(modifiedData);// , BorderLayout.CENTER);
		add(buttonPanel);// , BorderLayout.SOUTH);

		setMinimumSize(getPreferredSize());
		// setMaximumSize(new Dimension(getPreferredSize().width, 200));

		// System.out.println("GG " + getPreferredSize().width);
		// setBorder(new EmptyBorder(0, 0, 5, 0));
	}

	public PrescriptedItem getPosition() {
		return position;
	}

	public void setPosition(PrescriptedItem position) {
		this.position = position;
	}

	@Override
	public void setEnabled(boolean state) {
		removeButton.setEnabled(state);
		dosesCount.setEditable(state);
		packageCount.setEditable(state);
		discountVal.setEditable(state);
		ingestionCount.setEditable(state);
	}

	public PrescriptedItem retrievePrescribedPosition() throws BadDataException {
		PrescriptedItem pos = null;

		try {
			double doses = Double.parseDouble(dosesCount.getText().replace(',',
					'.'));
			double discount = Double.parseDouble(discountVal.getText()
					.replace('%', ' ').trim()) * 0.01;

			int packages = Integer.parseInt(packageCount.getText());
			int ingestions = Integer.parseInt(ingestionCount.getText());

			if (doses <= 0 || discount < 0 || discount > 100 || packages <= 0
					|| ingestions <= 0)
				throw new BadDataException();

			System.out.println("d: " + doses + ", r: " + discount);
			pos = new PrescriptedItem(position.getMedicine(), packages, doses,
					ingestions, discount);

		} catch (NumberFormatException e) {
			throw new BadDataException();
		}
		/*
		 * } catch (NumberFormatException e) { throw new WrongInputException();
		 * }
		 */
		return pos;

	}

}
