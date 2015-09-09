package GUI_items;

import items.Item;
import items.PozycjaNaRecepcie;

import javax.swing.JPanel;

public class Panel extends JPanel {

	Item position;
	
	public Panel(Item i){
		position=i;
	}
	
	public Item getPosition() {
		return position;
	}

	public void setPosition(Item position) {
		this.position = position;
	}
}
