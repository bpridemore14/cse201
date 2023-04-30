import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TaskRemovalGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField taskNameField;
	private JTextArea outputArea;
	private JPanel inputPanel, buttonPanel;
	private JLabel taskNameLabel;
	private JButton confirmButton, clearButton;
	private JScrollPane scrollPane;

	public TaskRemovalGUI() {
		super("Task Removal GUI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		// Create JPanel and add labels
		inputPanel = new JPanel();
		taskNameLabel = new JLabel("Enter task name to remove: ");
		taskNameField = new JTextField(20);
		inputPanel.add(taskNameLabel);
		inputPanel.add(taskNameField);

		// Create button with functionality
		confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String taskName = taskNameField.getText();
				removeTask(taskName);
				outputArea.setText("Task removed: " + taskName);
			}
		});

		// Create button with functionality
		clearButton = new JButton("Clear all tasks");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Task> tasks = new ArrayList<Task>();
				writeTasksToFile(tasks);
				outputArea.setText("Removed all tasks.");
			}
		});
		
		// Create a jpanel for button and add buttons
		buttonPanel = new JPanel();
		buttonPanel.add(confirmButton);
		buttonPanel.add(clearButton);
		
		// Create text area and scrollpane
		outputArea = new JTextArea(10, 30);
		outputArea.setEditable(false);
		scrollPane = new JScrollPane(outputArea);

		// Add them to GUI
		add(inputPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.CENTER);
		add(scrollPane, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	// Method removes the task object from the arraylist that has the matching
	// taskname and
	// calls writetaskstofile
	private void removeTask(String taskName) {
		ArrayList<Task> tasks = readTasksFromFile();
		for (Task t : tasks) {
			if (t.getTitle().equals(taskName)) {
				tasks.remove(t);
				writeTasksToFile(tasks);
				return;
			}
		}
	}

	// Method that overwrites the file tasks.txt with the new arraylist of task
	// objects passed in
	// from the removetask method
	private void writeTasksToFile(ArrayList<Task> tasks) {
		try {
			FileWriter writer = new FileWriter("tasks.txt", false);
			for (Task t : tasks) {
				writer.write(
						t.getTitle() + "," + t.getDescription() + "," + t.getDueDate() + "," + t.getPriority() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Method to read the task objects from tasks.txt
	// Stores the objects in arraylist and returns an arraylist
	private ArrayList<Task> readTasksFromFile() {
		ArrayList<Task> tasks = new ArrayList<Task>();

		try {
			Scanner scanner = new Scanner(new File("tasks.txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(",");
				String title = parts[0];
				String description = parts[1];
				String dueDate = parts[2];
				String priority = parts[3];
				Task task = new Task(title, description, dueDate, priority);
				tasks.add(task);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return tasks;
	}

	public static void main(String[] args) {
		new TaskRemovalGUI();
	}

}
