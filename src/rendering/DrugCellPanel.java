package rendering;

import items.Lek;
import items.PozycjaNaRecepcie;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

public class DrugCellPanel extends JPanel {

	
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
	
	public DrugCellPanel() {
		
		//Lek drug=((PozycjaNaRecepcie)value).getLek();
		nameDoseLabel=new JLabel();//drug.getNazwa()+" "+drug.getDawka());
		typeLabel=new JLabel();//drug.getPostac()+", "+drug.getOpakowanie());
		//JLabel doseLabel=new JLabel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(LEFT_ALIGNMENT);
	
		add(nameDoseLabel);
		add(typeLabel);
		
		
		JPanel modifiedData=new JPanel();
		
		 dosesCount=new JTextField(3);
		 packageCount=new JTextField(3);
		 discountVal=new JTextField(3);
		 ingestionCount=new JTextField(3);
		
		dosesCount.setEnabled(true);
		
		JLabel dosesCountLabel=new JLabel(dosesString);
		JLabel packageCountLabel=new JLabel(packageString);
		JLabel discountValLabel=new JLabel(discountString);
		JLabel ingestionCountLabel=new JLabel(ingestionString);
		
		/*(modifiedData.add(ingestionCountLabel);
		modifiedData.add(ingestionCount);
		modifiedData.add(dosesCountLabel);
		modifiedData.add(dosesCount);
		modifiedData.add(packageCountLabel);
		modifiedData.add(packageCount);
		modifiedData.add(discountValLabel);
		modifiedData.add(discountVal);*/
		JPanel ingGrid=new JPanel();
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
		
		GroupLayout layout = new GroupLayout(modifiedData);
		modifiedData.setLayout(layout);


		   layout.setAutoCreateGaps(true);
		   layout.setAutoCreateContainerGaps(true);
		   GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(ingGrid).addComponent(doseGrid));
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(pckgGrid).addComponent(discGrid));//.addComponent(packageCount).addComponent(discountVal));
		   layout.setHorizontalGroup(hGroup);
		   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(ingGrid).addComponent(pckgGrid));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(doseGrid).addComponent(discGrid));
		   /*vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(packageCountLabel).addComponent(packageCount));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(discountValLabel).addComponent(discountVal));*/
		   layout.setVerticalGroup(vGroup);
		
		
		//JTextField 
		   add(modifiedData);
	}
	
	public void update(PozycjaNaRecepcie pos) {
		this.position=pos;
		System.out.println("pos "+pos);
		Lek drug=pos.getLek();
		nameDoseLabel.setText(drug.getNazwa()+" "+drug.getDawka());
		typeLabel.setText(drug.getPostac()+", "+drug.getOpakowanie());
	}

	public PozycjaNaRecepcie getValue() {
		return position;
		
	}
	
	
}
