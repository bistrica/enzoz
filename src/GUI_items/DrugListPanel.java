package GUI_items;

import items.PrescriptedItem;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DrugListPanel extends JPanel {

	private GridLayout GRID;
	private int counter, rows;

	public DrugListPanel() {
		GRID = new GridLayout(1, 2);
		setLayout(GRID);
		rows = 1;
		counter = 0;
		setBorder(new EmptyBorder(5, 5, 5, 5));
	}

	public void add(PrescriptedItem pos) {
		for (Component panel : getComponents()) {
			if (((DrugPanel) panel).getPosition().getMedicine()
					.equals(pos.getMedicine()))
				return;
		}
		add(new DrugPanel(pos, this));
		counter++;
		if (counter >= rows * 2) {
			rows++;
			GRID.setRows(rows);
		}
		revalidate();
	}

	public void updateCounter() {
		counter--;
		if (counter <= (rows - 1) * 2)
			if (rows != 1) {
				rows--;
				GRID.setRows(rows);
			}
	}

}
