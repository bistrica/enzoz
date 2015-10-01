package GUI_items;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BPanel extends JPanel {

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLUE);
	}

}
