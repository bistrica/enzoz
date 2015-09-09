package GUI_items;

import items.Poradnia;
import items.PozycjaNaRecepcie;
import items.Skierowanie;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ExaminationListPanel extends JPanel {

	Poradnia[] clinics;
	
	public ExaminationListPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	}
	
	public void setAllClinics(ArrayList<Poradnia> clinics){
		
		Poradnia[] items=new Poradnia[clinics.size()];
		items=clinics.toArray(items);
		this.clinics=items;
	}
	
	public void add(Skierowanie pos) {
		for (Component panel: getComponents()) {
			if (((ExamPanel)panel).getPosition().equals(pos))
				return;
		}
		add(new ExamPanel(pos,clinics));
		revalidate();
		
	}
}
