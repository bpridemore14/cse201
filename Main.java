import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static List<UserAccount> userAccounts;
	private String userAccountsFile = "user_accounts.txt";
	private String delimiter = ",";

	public Main() {
		this.userAccounts = new ArrayList<>();
		loadUserAccountsFromFile();
	}

	public void createUserAccount(String username, String password, String email, boolean isAdmin) {
		UserAccount user = new UserAccount(username, password, email, isAdmin);
		this.userAccounts.add(user);
		saveUserAccountsToFile();
		System.out.println("User account created successfully.");
	}

	private void saveUserAccountsToFile() {
		try (PrintWriter writer = new PrintWriter(new FileWriter(userAccountsFile))) {
			for (UserAccount user : userAccounts) {
				writer.println(user.getUsername() + delimiter + user.getPassword() + delimiter + user.getEmail()
						+ delimiter + user.isAdmin());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadUserAccountsFromFile() {
		File file = new File(userAccountsFile);
		if (file.exists()) {
			try (Scanner scanner = new Scanner(file)) {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] fields = line.split(delimiter);
					String username = fields[0];
					String password = fields[1];
					String email = fields[2];
					boolean isAdmin = Boolean.parseBoolean(fields[3]);
					UserAccount user = new UserAccount(username, password, email, isAdmin);
					this.userAccounts.add(user);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		TaskManager taskManager = new TaskManager();
		Scanner scanner = new Scanner(System.in);

		// display menu and prompt user for input until they choose to exit
		while (true) {
			System.out.println("Task Management Tool");
			System.out.println("1. Create user account");
			System.out.println("2. Add Task");
			System.out.println("3. View Tasks by Assignee");
			System.out.println("4. Mark Task as Completed");
			System.out.println("5. Exit");
			System.out.print("Enter your choice (1-5): ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // consume the newline character

			switch (choice) {
			case 1:
				System.out.println("Enter username:");
				String username = scanner.nextLine();

				System.out.println("Enter password:");
				String password = scanner.nextLine();

				System.out.println("Enter email:");
				String email = scanner.nextLine();

				System.out.println("Is this an admin account? (true/false)");
				boolean isAdmin = scanner.nextBoolean();
				scanner.nextLine();

				UserAccount user = new UserAccount(username, password, email, isAdmin);
				userAccounts.add(user);
				System.out.println("User account created successfully.");
				break;
			case 2:
				System.out.print("Enter task title: ");
				String title = scanner.nextLine();
				System.out.print("Enter task description: ");
				String description = scanner.nextLine();
				System.out.print("Enter task assignee: ");
				String assignee = scanner.nextLine();
				Task task = new Task(title, description, assignee);
				taskManager.addTask(task);
				System.out.println("Task added successfully!");
				break;
			case 3:
				System.out.print("Enter assignee name: ");
				String name = scanner.nextLine();
				System.out.println("Tasks assigned to " + name + ":");
				for (Task t : taskManager.getTasksByAssignee(name)) {
					System.out.println(t.getTitle());
				}
				break;
			case 4:
				System.out.print("Enter task title: ");
				String titleToComplete = scanner.nextLine();
				boolean taskCompleted = false;
				for (Task t : taskManager.getTasks()) {
					if (t.getTitle().equals(titleToComplete)) {
						taskManager.markTaskAsCompleted(t);
						taskCompleted = true;
						break;
					}
				}
				if (taskCompleted) {
					System.out.println("Task marked as completed!");
				} else {
					System.out.println("Task not found!");
				}
				break;
			case 5:
				System.out.println("Exiting...");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}
}
