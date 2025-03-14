package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.UserDAO;

public class LoginFrame extends JFrame {
	private JLabel lblForgotPass;
	private JTextField usernameField;
	private JPasswordField passwordField;

	public LoginFrame() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 682);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new GridLayout(1, 2));

		var Panel = new JPanel();
		Panel.setForeground(new Color(255, 255, 255));
		Panel.setBackground(new Color(255, 255, 255));
		Panel.setLayout(null);

		getContentPane().add(Panel);

		var lblLeft = new JLabel();
		var img = new ImageIcon(this.getClass().getResource("/image.png")).getImage();
		lblLeft.setIcon(new ImageIcon(img));
		lblLeft.setBounds(40, 96, 309, 459);

		lblLeft.setHorizontalAlignment(SwingConstants.CENTER);
		Panel.add(lblLeft);

		var loginTitle = new JLabel("LOGIN", SwingConstants.CENTER);
		loginTitle.setForeground(new Color(0, 128, 255));
		loginTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		loginTitle.setBounds(487, 96, 174, 28);
		Panel.add(loginTitle);

		var usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		usernameLabel.setBounds(411, 154, 86, 22);
		Panel.add(usernameLabel);

		usernameField = new JTextField(15);
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		usernameField.setBounds(411, 201, 325, 28);
		Panel.add(usernameField);

		var passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passwordLabel.setBounds(411, 255, 81, 22);
		Panel.add(passwordLabel);

		passwordField = new JPasswordField(15);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passwordField.setBounds(411, 300, 325, 28);
		Panel.add(passwordField);

		var loginButton = new JButton("LOGIN");
		loginButton.setForeground(Color.WHITE);
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		loginButton.setFocusPainted(false);
		loginButton.setBackground(new Color(0, 128, 255));
		loginButton.setBounds(487, 430, 193, 29);
		loginButton.addActionListener(this::handleLogin);
		Panel.add(loginButton);

		var chckbxSavePass = new JCheckBox("Remember Password");
		chckbxSavePass.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxSavePass.setBackground(new Color(255, 255, 255));
		chckbxSavePass.setBounds(411, 347, 150, 23);
		Panel.add(chckbxSavePass);

		var forgotPasswordLabel = new JLabel("<html><u>Forgot password?</u></html>");
		forgotPasswordLabel.setForeground(Color.BLUE);
		forgotPasswordLabel.setFont(new Font("Tahoma", Font.ITALIC, 14));
		forgotPasswordLabel.setBounds(412, 374, 150, 23);
		forgotPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				handleForgotPassword();
			}
		});
		Panel.add(forgotPasswordLabel);
		Panel.setVisible(true);

		setVisible(true);
	}

	private void handleLogin(ActionEvent e) {
		var dao = new UserDAO();
		var username = usernameField.getText().trim();
		var password = new String(passwordField.getPassword()).trim();

		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter both username and password!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		var isValidUser = dao.validateUser(username, password);
		if (isValidUser) {
			var role = dao.getUserRole(username, password);
			var IDStu = dao.getStudentId(username, password);
			if ("Admin".equals(role)) {
				JOptionPane.showMessageDialog(this, "Login successful as Admin!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				new MenuFrame().setVisible(true);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Login successful as User!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				new InfoStu(IDStu).setVisible(true);
				this.dispose();
			}
		} else {
			JOptionPane.showMessageDialog(this, "Incorrect username or password!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void handleForgotPassword() {
		JOptionPane.showMessageDialog(this, "Password has been sent to the admin.", "Notification",
				JOptionPane.INFORMATION_MESSAGE);
	}

//	public static void main(String[] args) {
//		new LoginFrame();
//	}

}