package login;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Painter;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

import GUI_items.BPanel;
import GUI_items.IconFrame;

public class LoginView extends IconFrame {

	private JTextField login;
	private JPasswordField password;
	private JButton loginButton;

	private String loginString = "Login:", passwordString = "Has³o:",
			loginButtonString = "Zaloguj", titleBar = "Wyst¹pi³ b³¹d";

	public LoginView() {
		BPanel bgPanel = new BPanel();
		JPanel panel = new JPanel();
		JPanel buttonPanel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		panel.setBorder(new EmptyBorder(0, 20, 0, 20));
		buttonPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

		JLabel loginLabel = new JLabel(loginString);
		JLabel passwordLabel = new JLabel(passwordString);
		loginLabel.setForeground(Color.WHITE);
		passwordLabel.setForeground(Color.WHITE);

		login = new JTextField(30);
		password = new JPasswordField(30);
		loginButton = new JButton(loginButtonString);

		buttonPanel.add(loginButton);

		panel.add(loginLabel);
		panel.add(login);
		panel.add(passwordLabel);
		panel.add(password);

		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		hGroup.addGroup(layout.createParallelGroup().addComponent(loginLabel)
				.addComponent(passwordLabel));
		hGroup.addGroup(layout.createParallelGroup().addComponent(login)
				.addComponent(password));
		layout.setHorizontalGroup(hGroup);
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(loginLabel).addComponent(login));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(passwordLabel).addComponent(password));
		layout.setVerticalGroup(vGroup);

		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		panel.setOpaque(false);
		buttonPanel.setOpaque(false);

		JPanel emptyPanel = new JPanel();
		emptyPanel.setOpaque(false);
		emptyPanel.setPreferredSize(new Dimension(600, 300));

		bgPanel.add(emptyPanel);
		bgPanel.add(panel);
		bgPanel.add(buttonPanel);

		getContentPane().add(bgPanel);
		pack();
		setPreferredSize(new Dimension(600, 400));
		setResizable(false);

		setCentreLocation();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public String getLogin() {
		return login.getText();
	}

	public char[] getPassword() {
		return password.getPassword();
	}

	public void setLoginListener(ActionListener al) {
		loginButton.addActionListener(al);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				setLookAndFeel();

				LoginView view = new LoginView();
				LoginDBH model = new LoginDBH();
				LoginController controller = new LoginController(view, model);
				view.setVisible(true);

			}
		});

	}

	public static void setLookAndFeel() {

		UIManager.put("nimbusBase", new Color(0, 68, 102));
		UIManager.put("nimbusBlueGrey", new Color(0, 68, 102));
		UIManager.put("control", new Color(10, 68, 102));
		UIManager.put("nimbusSelectionBackground", new Color(135, 1, 45));
		UIManager.put("text", Color.WHITE);
		UIManager.put("TextField.font", new Font("Font", Font.PLAIN, 12));
		UIManager.put("TextField.textForeground", new Color(0, 0, 0));
		UIManager.put("PasswordField.foreground", new Color(0, 0, 0));
		UIManager.put("TextArea.font", new Font("Arial", Font.BOLD, 15));
		UIManager.put("TextArea.background", new Color(57, 105, 138));
		UIManager.put("FormattedTextField.foreground", new Color(0, 0, 0));
		UIManager.put("List.background", new Color(57, 105, 138));
		UIManager.put("List.font", new Font("Arial", Font.BOLD, 15));

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Err");
		}

		UIManager.getLookAndFeelDefaults().put(
				"Table:\"Table.cellRenderer\".background",
				new ColorUIResource(new Color(74, 144, 178)));

		UIManager.getLookAndFeelDefaults().put("Table.background",
				new ColorUIResource(new Color(74, 144, 178)));
		UIManager.getLookAndFeelDefaults().put("Table.alternateRowColor",
				new Color(0, 68, 102));
		Border border = new EmptyBorder(2, 5, 2, 5);
		UIManager.getLookAndFeelDefaults().put(
				"Table.focusCellHighlightBorder", border);

		Painter<Component> p = new Painter<Component>() {
			@Override
			public void paint(Graphics2D g, Component c, int width, int height) {
				g.setColor(new Color(57, 105, 138));
				g.fillRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
			}
		};
		UIManager.getLookAndFeelDefaults().put(
				"MenuBar[Enabled].backgroundPainter", p);
		UIManager.getLookAndFeelDefaults().put(
				"MenuBar:Menu[Enabled].textForeground", Color.WHITE);

	}

	public void displayError(String message) {
		JOptionPane.showMessageDialog(null, message, titleBar,
				JOptionPane.INFORMATION_MESSAGE);

	}

	public void clear() {
		login.setText("");
		password.setText("");
	}

	/*
	 * public static void setLookAndFeel2() {
	 * 
	 * UIManager.put("nimbusBase", new Color(0, 68, 102));
	 * UIManager.put("nimbusBlueGrey", new Color(60, 145, 144));
	 * UIManager.put("control", new Color(43, 82, 102)); UIManager.put("text",
	 * new Color(255, 255, 255)); UIManager.put("Table.alternateRowColor", new
	 * Color(0, 68, 102)); UIManager.put("TextField.font", new Font("Font",
	 * Font.BOLD, 12)); UIManager.put("TextField.textForeground", new Color(0,
	 * 0, 0)); UIManager.put("PasswordField.foreground", new Color(0, 0, 0));
	 * UIManager.put("TextArea.foreground", new Color(0, 0, 0));
	 * UIManager.put("FormattedTextField.foreground", new Color(0, 0, 0));
	 * UIManager.put("ComboBox:\"ComboBox.listRenderer\".background", new
	 * Color(0, 68, 102)); UIManager.put(
	 * "ComboBox:\"ComboBox.listRenderer\"[Selected].background", new Color(0,
	 * 0, 0));
	 * 
	 * try { for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	 * if ("Nimbus".equals(info.getName())) {
	 * UIManager.setLookAndFeel(info.getClassName()); break; } } } catch
	 * (Exception e) { }
	 * 
	 * UIManager.getLookAndFeelDefaults().put(
	 * "Table:\"Table.cellRenderer\".background", new ColorUIResource(new
	 * Color(74, 144, 178)));
	 * UIManager.getLookAndFeelDefaults().put("Table.background", new
	 * ColorUIResource(new Color(74, 144, 178)));
	 * 
	 * }
	 */
}
