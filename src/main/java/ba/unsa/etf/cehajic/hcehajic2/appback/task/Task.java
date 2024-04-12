package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import javax.persistence.*;

import ba.unsa.etf.cehajic.hcehajic2.appback.child.Child;

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
    @ManyToOne
    @JoinColumn(name = "childId") // Specify the name of the foreign key column
    private Child child;
    private boolean priority;
    private boolean done;
    private String difficulty;
    public Task() {  priority = false; done = false;  }

    public Task(String taskName, String description, LocalDate dateOfCreation, String dueTime, Long accountId,String difficulty) {
        this(taskName, description, dateOfCreation, dueTime, accountId, false,difficulty);
    }

    public Task(String taskName, String description, LocalDate dateOfCreation, String dueTime, Long accountId, boolean priority, String difficulty) {
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dateOfCreation;
        this.dueTime = dueTime;
        this.child = new Child();
        this.child.setId(accountId);
        this.priority = priority;
        this.done = false;
        this.difficulty = difficulty;
    }

    public Task(String taskName, String description, LocalDate dateOfCreation, String dueTime, Long accountId, boolean priority, boolean done,String difficulty) {
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dateOfCreation;
        this.dueTime = dueTime;
        this.child = new Child();
        this.child.setId(accountId);
        this.priority = priority;
        this.done = done;
        this.difficulty = difficulty;
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

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", dueTime=" + dueTime +
                ", priority=" + priority +
                ", done=" + done +
                '}';
    }
}
