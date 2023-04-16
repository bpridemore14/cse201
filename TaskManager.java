import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getTasksByAssignee(String assignee) {
        List<Task> tasksByAssignee = new ArrayList<Task>();
        for (Task task : tasks) {
            if (task.getAssignee().equals(assignee)) {
                tasksByAssignee.add(task);
            }
        }
        return tasksByAssignee;
    }

    public void markTaskAsCompleted(Task task) {
        task.setCompleted(true);
    }
}