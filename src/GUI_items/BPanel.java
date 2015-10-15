package GUI_items;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class BPanel extends JPanel {

	private BufferedImage img;
	private BufferedImage scaled;

	public BPanel() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		String path1 = "/res/bg.png";

		try {

			URL resource = getClass().getResource(path1);
			scaled = img = ImageIO.read(resource);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return img == null ? super.getPreferredSize() : new Dimension(
				img.getWidth(), img.getHeight());
	}

	public void setBackground(BufferedImage value) {
		if (value != img) {
			this.img = value;
			repaint();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (scaled != null) {
			int x = (getWidth() - scaled.getWidth()) / 2;
			int y = (getHeight() - scaled.getHeight()) / 2;
			g.drawImage(scaled, x, y, this);
		}
	}
}
