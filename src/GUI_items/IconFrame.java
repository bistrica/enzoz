package GUI_items;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class IconFrame extends JFrame {

	private static Image icon;
	private static final String title = "ENZOZ";

	public IconFrame() {
		String path1 = "/res/logo_icon.png";

		if (icon == null) {

			URL resource = getClass().getResource(path1);
			try {
				icon = ImageIO.read(resource);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (icon != null)
			setIconImage(icon);

		setTitle(title);

	}

	public void setCentreLocation() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		setLocation((dim.width - getSize().width) / 2,
				(dim.height - getSize().height) / 2);
	}
}
