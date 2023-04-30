import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class CreateGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JLabel usernameLabel, passwordLabel, phoneLabel, emailLabel;
	private JTextField usernameField, emailField, phoneField;
	private JPasswordField passwordField;
	private JCheckBox adminCheckBox;
	private JButton createAccountButton;

	public CreateGUI() {
		// Create frame
		frame = new JFrame("Create Account");
		frame.setSize(300, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new GridLayout(6, 2));

		// Create UI components
		usernameLabel = new JLabel("Username:");
		usernameField = new JTextField();
		passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField();
		emailLabel = new JLabel("Email:");
		emailField = new JTextField();
		phoneLabel = new JLabel("Phone:");
		phoneField = new JTextField();
		adminCheckBox = new JCheckBox("Admin User?");

		// Create the 'create account' button along with its functionality
		createAccountButton = new JButton("Create Account");
		createAccountButton.addActionListener(new CreateAccountButtonListener());

		// Add UI components to frame
		frame.add(usernameLabel);
		frame.add(usernameField);
		frame.add(passwordLabel);
		frame.add(passwordField);
		frame.add(emailLabel);
		frame.add(emailField);
		frame.add(phoneLabel);
		frame.add(phoneField);
		frame.add(adminCheckBox);
		frame.add(createAccountButton);

		// Set frame properties
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public void main() {
		// Create createAccount gui
		@SuppressWarnings("unused")
		CreateGUI gui = new CreateGUI();
		System.out.println("test");
	}

	private class CreateAccountButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String username = usernameField.getText();
			String password = new String(passwordField.getPassword());
			String email = emailField.getText();
			String phone = phoneField.getText();
			boolean isAdmin = adminCheckBox.isSelected();

			try {
				FileWriter writer = new FileWriter("accounts.txt", true);
				writer.write(username + "," + password + "," + email + "," + phone + "," + isAdmin + "\n");
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(frame, "Account created successfully!");
			frame.dispose();
		}
	}
}
