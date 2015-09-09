package individualApp;

import items.Poradnia;
import items.Skierowanie;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import GUI_items.ExamPanel;

public class ExaminationPanel extends JPanel {

	Poradnia[] clinics;
	private String addString="Dodaj skierowanie";
	
	public ExaminationPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JButton addButton=new JButton(addString);
		addButton.setAlignmentX(CENTER_ALIGNMENT);
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				add(new ExamPanel(clinics));
				
				revalidate();

			}
		});
		
		add(addButton);
	}
	
	public void setAllClinics(ArrayList<Poradnia> clinics){
		
		Poradnia[] items=new Poradnia[clinics.size()];
		items=clinics.toArray(items);
		this.clinics=items;
	}
	
	/*public void add(Skierowanie pos) {
		
		
		
	}*/
	
	
}
