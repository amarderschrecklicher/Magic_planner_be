package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Task {

    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;
    private String taskName;
    private String description;
    private LocalDate dueDate;

    private String dueTime;
    private Long accountId;

    private boolean priority;
    public Task() {    }

    public Task(String taskName, String description, LocalDate dateOfCreation, String dueTime, Long accountId) {
        this(taskName, description, dateOfCreation, dueTime, accountId, false);
    }

    public Task(String taskName, String description, LocalDate dateOfCreation, String dueTime, Long accountId, boolean priority) {
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dateOfCreation;
        this.dueTime = dueTime;
        this.accountId = accountId;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", dueTime=" + dueTime +
                ", accountId=" + accountId +
                ", priority=" + priority +
                '}';
    }
}
