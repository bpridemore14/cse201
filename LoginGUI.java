import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton, createAccountButton;
	private JPanel panel;
	@SuppressWarnings("unused")
	private CreateGUI create;
	
	public LoginGUI() {
		super("Login");
		setResizable(false);
		setLocationRelativeTo(null);
		// Create UI components
		usernameField = new JTextField(20);
		passwordField = new JPasswordField(20);
		loginButton = new JButton("Login");
		
		// Create the a create account button along with the functionality
		createAccountButton = new JButton("Create Account");
		createAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == loginButton) {
					char[] charArray = passwordField.getPassword();
					String pass = new String(charArray);
					isValidUser(usernameField.getText(), pass);
				} else if (e.getSource() == createAccountButton) {
					create = new CreateGUI();
				}
			}
		});
		
		// Create the login button along with the functionality
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				// Check if the username and password are valid
				if (isValidUser(username, password)) {
					// Create a new instance of the main GUI window
					CalendarGUI calendar = new CalendarGUI();
					calendar.setVisible(true);
					// Close the login window
					dispose();
				} else {
					JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password", "Login Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		

		// Create panel for UI components
		panel = new JPanel(new GridLayout(3, 2));
		panel.add(new JLabel("Username: "));
		panel.add(usernameField);
		panel.add(new JLabel("Password: "));
		panel.add(passwordField);
		panel.add(loginButton);
		panel.add(createAccountButton);

		// Add panel to frame
		this.add(panel);

		// Set frame properties
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	// Handle button clicks
	
	
	// Method that checks to see if the username and password entered match any of the ones
	// within the arraylist which is read from the file accounts.txt
	boolean isValidUser(String username, String password) {
		ArrayList<UserAccount> users = readAccounts();
		for (UserAccount user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	// Method that reads the users from accounts.txt into an arraylist of user objects
	public ArrayList<UserAccount> readAccounts() {
		ArrayList<UserAccount> users = new ArrayList<UserAccount>();
		try {
			File file = new File("accounts.txt");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] fields = line.split(",");
				String username = fields[0];
				String password = fields[1];
				String email = fields[2];
				String phone = fields[3];
				boolean isAdmin = Boolean.parseBoolean(fields[4]);
				UserAccount user = new UserAccount(username, password, email, phone, isAdmin);
				users.add(user);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return users;
	}

	public static void main(String[] args) {
		// Create login GUI
		@SuppressWarnings("unused")
		LoginGUI loginGUI = new LoginGUI();
	}
}
