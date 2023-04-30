import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CalendarGUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel monthLabel;
	private JPanel panel, calendarPanel, buttonPanel, buttonPanel2;
	private JButton previousButton, nextButton, changeUserButton, printTaskButton, lookupButton, dayButton,
			addTaskButton, removeTaskButton;
	private DateFormat monthFormat, dateFormat;
	private Calendar currentMonth;
	private JTextArea taskField;
	private JTextField taskNameField, taskDescField, taskPriorityField;
	private JScrollPane taskScroll;
	private JFrame frame;

	public CalendarGUI() {
		// Set frame properties
		setResizable(false);
		this.setTitle("Calendar");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(844, 434));
		this.setLocationRelativeTo(null);

		// Create the month label and buttons
		monthLabel = new JLabel("", SwingConstants.CENTER);
		monthLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		previousButton = new JButton("<<");
		previousButton.addActionListener(this);
		nextButton = new JButton(">>");
		nextButton.addActionListener(this);

		// Create the calendar panel
		calendarPanel = new JPanel();
		calendarPanel.setLayout(new GridLayout(7, 7));
		calendarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		monthFormat = new SimpleDateFormat("MMM yyyy");
		dateFormat = new SimpleDateFormat("MM/yyyy");
		currentMonth = Calendar.getInstance();
		updateCalendar();

		// Create a button panel
		buttonPanel2 = new JPanel();
		buttonPanel2.setLayout(new GridLayout(3, 3));
		buttonPanel2.setBorder(BorderFactory.createTitledBorder("Other Functions"));
		getContentPane().add(buttonPanel2);

		// Create a button to lookup users
		lookupButton = new JButton("User Lookup");
		lookupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Code to create new GUI window for user lookup
				@SuppressWarnings("unused")
				UserLookupGUI userLookup = new UserLookupGUI();
			}
		});

		// Create a button to change users
		changeUserButton = new JButton("Change User");
		changeUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				@SuppressWarnings("unused")
				LoginGUI newLogin = new LoginGUI();
			}
		});

		// Create a button to print out all tasks
		printTaskButton = new JButton("Print Tasks");
		printTaskButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				taskField.setText("");
				ArrayList<Task> tasks = readTasks();
				for (Task t : tasks) {
					taskField.append("Title: " + t.getTitle() + "\n");
					taskField.append("Description: " + t.getDescription() + "\n");
					taskField.append("Due Date: " + t.getDueDate() + "\n");
					taskField.append("Priority: " + t.getPriority() + "\n\n");
				}
			}
		});

		// Create a text area to be populated with tasks
		taskField = new JTextArea();
		taskField.setBackground(Color.WHITE);
		taskField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		taskField.setColumns(25);
		taskField.setEditable(false);
		taskScroll = new JScrollPane(taskField);

		// Add buttons to panel
		buttonPanel2.add(lookupButton);
		buttonPanel2.add(changeUserButton);
		buttonPanel2.add(printTaskButton);

		// Button to remove task from file
		removeTaskButton = new JButton("Remove Task");
		buttonPanel2.add(removeTaskButton);

		// Functionality for remove task button
		removeTaskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				TaskRemovalGUI taskRemover = new TaskRemovalGUI();
			}
		});

		updateCalendar();

		// Add the components to the container
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		container.add(monthLabel, BorderLayout.NORTH);
		container.add(calendarPanel, BorderLayout.WEST);
		container.add(taskScroll, BorderLayout.EAST);
		buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(previousButton);
		buttonPanel.add(nextButton);
		container.add(buttonPanel, BorderLayout.SOUTH);
		container.add(buttonPanel2, BorderLayout.CENTER);
	}

	// This actionperformed method is used for the arrow buttons to increment
	// or decrement the month that is selected
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == previousButton) {
			currentMonth.add(Calendar.MONTH, -1);
		} else if (e.getSource() == nextButton) {
			currentMonth.add(Calendar.MONTH, 1);
		}
		updateCalendar();
	}

	// Updates the month that is being shown when it is called
	private void updateCalendar() {
		// Sets the month label according the format MMM yyyy
		monthLabel.setText(monthFormat.format(currentMonth.getTime()));
		
		calendarPanel.removeAll();

		// Uses this array to show the days of the week within the calendar panel
		String[] daysOfWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		for (String day : daysOfWeek) {
			calendarPanel.add(new JLabel(day, SwingConstants.CENTER));
		}

		// The number of days in the month according to actual months
		int daysInMonth = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		// The first day of the month in each calendar month
		int firstDayOfMonth = currentMonth.get(Calendar.DAY_OF_WEEK) - 1;
		for (int i = 0; i < firstDayOfMonth; i++) {
			calendarPanel.add(new JLabel(""));
		}

		// Creates a button for each of the days in the month
		for (int day = 1; day <= daysInMonth; day++) {
			final int finalDay = day;
			// Button with the day of the month on it
			dayButton = new JButton(Integer.toString(day));
			String date = Integer.toString(finalDay) + "/";
			// Button to add task to file
			addTaskButton = new JButton("Add Task");
			// Functionality for add task button
			addTaskButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Task newTask = new Task(taskNameField.getText(), taskDescField.getText(),
							date + dateFormat.format(currentMonth.getTime()), taskPriorityField.getText());
					writeToFile(newTask);
				}
			});

			// Function for the day button
			dayButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Set frame properties
					frame = new JFrame("List for " + finalDay + " " + monthFormat.format(currentMonth.getTime()));

					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setSize(new Dimension(500, 200));
					frame.setLocationRelativeTo(null);

					// Set panel properties
					panel = new JPanel(new GridLayout(0, 1));
					panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

					// Add items to panel
					panel.add(taskNameField = new JTextField("Enter task name here..."));
					panel.add(taskDescField = new JTextField("Enter description here..."));
					panel.add(taskPriorityField = new JTextField("Enter priority (1-10) here..."));
					panel.add(addTaskButton);

					frame.getContentPane().add(panel);
					frame.setVisible(true);
				}
			});

			calendarPanel.add(dayButton);
		}
		revalidate();
		repaint();
	}

	// Reads the tasks from tasks.txt and returns an arraylist
	public ArrayList<Task> readTasks() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		try {
			File file = new File("tasks.txt");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] fields = line.split(",");
				String title = fields[0];
				String desc = fields[1];
				String duedate = fields[2];
				String priority = fields[3];
				Task task = new Task(title, desc, duedate, priority);
				tasks.add(task);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	// Method that writes new tasks to tasks.txt
	public static void writeToFile(Task newTask) {
		try {
			FileWriter writer = new FileWriter("tasks.txt", true);
			writer.write(newTask.getTitle() + "," + newTask.getDescription() + "," + newTask.getDueDate() + ","
					+ newTask.getPriority() + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		CalendarGUI calendarGUI = new CalendarGUI();
		calendarGUI.setVisible(true);
	}
}
