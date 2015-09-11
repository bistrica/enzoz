package GUI_items;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import items.Lek;
import items.PozycjaNaRecepcie;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

public class DrugPanel extends JPanel {

	private String dosesString="Iloœæ dawek/porcjê";
	private String discountString="Refundacja";
	private String packageString="Iloœæ opakowañ";
	private String ingestionString="Iloœæ za¿yæ/dzieñ";
		
	JLabel typeLabel;
	JLabel nameDoseLabel;
	JTextField dosesCount;
	JTextField packageCount;
	JTextField discountVal;
	JTextField ingestionCount;
	private PozycjaNaRecepcie position;
	private String removeString="Usuñ pozycjê";
	
	public DrugPanel(PozycjaNaRecepcie pos) {
		
		position=pos;
		Lek drug=pos.getLek();
		nameDoseLabel=new JLabel(drug.getNazwa()+", "+drug.getDawka());
		typeLabel=new JLabel(drug.getPostac()+", "+drug.getOpakowanie());
		nameDoseLabel.setFont(new Font("Arial", Font.BOLD, 20));
		typeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		
		setBorder(new EtchedBorder());
		
		JPanel labels=new JPanel();
		labels.setLayout(new BoxLayout(labels, BoxLayout.PAGE_AXIS));
		labels.add(nameDoseLabel);
		labels.add(typeLabel);
		
		JPanel modifiedData=new JPanel();
		
		 dosesCount=new JTextField(3);
		 packageCount=new JTextField(3);
		 discountVal=new JTextField(3);
		 ingestionCount=new JTextField(3);
		
		 dosesCount.setText(""+pos.getIloscDawekNaPrzyjecie());
		 packageCount.setText(""+pos.getIloscOpakowan());
		 discountVal.setText((int)(pos.getProcentRefundacji()*100)+"%");
		 ingestionCount.setText(""+pos.getIloscPrzyjec());
		
		JLabel dosesCountLabel=new JLabel(dosesString);
		JLabel packageCountLabel=new JLabel(packageString);
		JLabel discountValLabel=new JLabel(discountString);
		JLabel ingestionCountLabel=new JLabel(ingestionString);
		
		modifiedData.add(ingestionCountLabel);
		modifiedData.add(ingestionCount);
		modifiedData.add(dosesCountLabel);
		modifiedData.add(dosesCount);
		modifiedData.add(packageCountLabel);
		modifiedData.add(packageCount);
		modifiedData.add(discountValLabel);
		modifiedData.add(discountVal);
		/*JPanel ingGrid=new JPanel();
		JPanel doseGrid=new JPanel();
		JPanel pckgGrid=new JPanel();
		JPanel discGrid=new JPanel();
		
		ingGrid.add(ingestionCountLabel); ingGrid.add(ingestionCount);
		doseGrid.add(dosesCountLabel); doseGrid.add(dosesCount);
		pckgGrid.add(packageCountLabel); pckgGrid.add(packageCount);
		discGrid.add(discountValLabel); discGrid.add(discountVal);
		
		modifiedData.add(ingGrid);
		modifiedData.add(doseGrid);
		modifiedData.add(pckgGrid);
		modifiedData.add(discGrid);
		*/
		GroupLayout layout = new GroupLayout(modifiedData);
		modifiedData.setLayout(layout);


		   layout.setAutoCreateGaps(true);
		   layout.setAutoCreateContainerGaps(true);
		   GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(ingestionCountLabel).addComponent(dosesCountLabel));
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(ingestionCount).addComponent(dosesCount));
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(packageCountLabel).addComponent(discountValLabel));
		   hGroup.addGroup(layout.createParallelGroup().
				   addComponent(packageCount).addComponent(discountVal));
		  
		   
		   layout.setHorizontalGroup(hGroup);
		   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(ingestionCountLabel).addComponent(ingestionCount).
		            addComponent(packageCountLabel).addComponent(packageCount));
		           
		  
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
				   	addComponent(dosesCountLabel).addComponent(dosesCount).
		            addComponent(discountValLabel).addComponent(discountVal));
		  
		   layout.setVerticalGroup(vGroup);
		
		   JButton removeButton=new JButton(removeString);
		   JPanel thisPanel=this;
		   removeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Container cont=thisPanel.getParent();
				cont.remove(thisPanel);
				cont.revalidate();
			}
		});
		   
		   JPanel buttonPanel=new JPanel();
		   buttonPanel.add(removeButton);
		   
		   setLayout(new GridBagLayout());
		   
		   labels.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 0));
		  
		   labels.setAlignmentX(LEFT_ALIGNMENT);
		   labels.setPreferredSize(new Dimension(350, 100));
		   labels.setMinimumSize(new Dimension(350, 100));
		  
		   modifiedData.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		   buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
		   
		   
		   add(labels);
		   add(modifiedData);
		   add(buttonPanel);
		   
		   setMinimumSize(getPreferredSize());
		   setMaximumSize(new Dimension(getPreferredSize().width, 200));
		   
	}

	public PozycjaNaRecepcie getPosition() {
		return position;
	}

	public void setPosition(PozycjaNaRecepcie position) {
		this.position = position;
	}
	
	
	
}
