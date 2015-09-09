package GUI_items;

import items.PozycjaNaRecepcie;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class DrugListPanel extends JPanel {

	public DrugListPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	}
	
	public void add(PozycjaNaRecepcie pos) {
		for (Component panel: getComponents()) {
			if (((DrugPanel)panel).getPosition().equals(pos))
				return;
		}
		add(new DrugPanel(pos));
		revalidate();
		
	}
	
}