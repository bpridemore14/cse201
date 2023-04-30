
public class Task {
    private String title;
    private String description;
    private String dueDate;
    private String priority;

    public Task(String title, String description, String duedate, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = duedate;
        this.priority = priority;
    }

    public String getPriority() {
    	return priority;
    }
    
    public void setPriority(String priority) {
    	this.priority = priority;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
}
