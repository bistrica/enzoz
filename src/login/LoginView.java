package login;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class LoginView extends JFrame {

	JTextField login;
	JPasswordField password;
	JButton loginButton;

	String loginString = "Login:", passwordString = "Has³o:",
			loginButtonString = "Zaloguj", titleBar = "Wyst¹pi³ b³¹d";

	public LoginView() {

		JPanel panel = new JPanel(), buttonPanel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));

		JLabel loginLabel = new JLabel(loginString);
		JLabel passwordLabel = new JLabel(passwordString);

		login = new JTextField(30);
		password = new JPasswordField(30);
		loginButton = new JButton(loginButtonString);
		buttonPanel.add(loginButton);

		panel.add(loginLabel);
		panel.add(login);
		panel.add(passwordLabel);
		panel.add(password);
		// panel.setAlignmentX(RIGHT_ALIGNMENT);
		// panel.setAlignmentY(CENTER_ALIGNMENT);

		// panel.add(loguj);
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

		getContentPane().add(panel);
		getContentPane().add(buttonPanel);// loguj);
		pack(); // todo: maybe to change?

	}

	public String getLogin() {
		return login.getText();
	}

	public String getPassword() {
		return password.getText();
	}

	public void setLoginListener(ActionListener al) {
		loginButton.addActionListener(al);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				LoginView view = new LoginView();
				LoginDBH model = new LoginDBH();
				LoginController controller = new LoginController(view, model);
				view.setVisible(true);

				try {

					/*
					 * InfoNodeLookAndFeelTheme theme = new
					 * InfoNodeLookAndFeelTheme( "My Theme", new Color(110, 120,
					 * 150), new Color(0, 170, 0), new Color(80, 80, 80),
					 * Color.WHITE, new Color(0, 170, 0), Color.WHITE, 0.8);
					 */
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					for (LookAndFeelInfo info : UIManager
							.getInstalledLookAndFeels()) {
						// System.out.println("*" + info.getName());
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
					// theme));//
					// OyoahaLookAndFeel laf = new OyoahaLookAndFeel();

					// LiquidLookAndFeel laf = new LiquidLookAndFeel();
					// UIManager.setLookAndFeel(laf);
					// UIManager
					// .setLookAndFeel("com.oyoaha.swing.plaf.oyoaha.OyoahaLookAndFeel");
					// // info.getClassName());

					// break;
					// }

					// }

				} catch (Exception e) {
					System.out.println("Err");
				}

			}
		});

		// view.setVisible(true);

	}

	public void displayError(String message) {
		JOptionPane.showMessageDialog(null, message, titleBar,
				JOptionPane.INFORMATION_MESSAGE);

	}

}
