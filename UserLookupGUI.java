import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserLookupGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel panel;
	private JLabel titleLabel, usernameLabel, emailLabel, phoneLabel;
	private JTextField usernameField;
	private JButton lookupButton;

	public UserLookupGUI() {

		// Initialize the frame and panel
		frame = new JFrame("User Lookup");
		panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1));

		// Initialize the components
		titleLabel = new JLabel("User Lookup");
		titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
		usernameLabel = new JLabel("Enter username:");
		usernameField = new JTextField(20);
		lookupButton = new JButton("Lookup");
		emailLabel = new JLabel("");
		phoneLabel = new JLabel("");
		emailLabel.setSize(500, 50);
		phoneLabel.setSize(100, 50);
		
		// Add the components to the panel
		panel.add(titleLabel);
		panel.add(usernameLabel);
		panel.add(usernameField);
		panel.add(lookupButton);
		panel.add(emailLabel);
		panel.add(phoneLabel);

		// Add an ActionListener to the lookupButton
		lookupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get the username entered by the user
				String email = "";
				String phoneNum = "";
				String username = usernameField.getText();
				ArrayList<UserAccount> users = readAccounts();
				UserAccount user = getUserByUsername(users, username);

				// Perform the lookup and display the result
				if (isValidUser(username)) {
					email = user.getEmail();
					phoneNum = user.getPhoneNum();
					emailLabel.setText(email.toString());
					phoneLabel.setText(phoneNum.toString());
					email = "";
					phoneNum = "";
				} else {
					emailLabel.setText("Lookup failed");
					phoneLabel.setText("User not found");
				}
			}

			// Helper method to get the user that is equal to the username entered
			private UserAccount getUserByUsername(ArrayList<UserAccount> users, String username) {
				for (UserAccount user : users) {
					if (user.getUsername().equals(username)) {
						return user;
					}
				}
				return null;
			}
		});

		// Set the frame properties
		frame.setSize(400, 200);
		frame.setResizable(false);
		frame.setContentPane(panel);
		frame.setVisible(true);

		frame.setLocationRelativeTo(null);
	}

	// Method that checks to see if the username and password entered match any of
	// the ones
	// within the arraylist which is read from the file accounts.txt
	boolean isValidUser(String username) {
		ArrayList<UserAccount> users = readAccounts();
		for (UserAccount user : users) {
			if (user.getUsername().equals(username)) {
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
			System.out.println("File not found: " + e.getMessage());
		}
		return users;
	}
}
