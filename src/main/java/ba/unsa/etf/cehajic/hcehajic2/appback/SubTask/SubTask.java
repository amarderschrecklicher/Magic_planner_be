package ba.unsa.etf.cehajic.hcehajic2.appback.SubTask;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class SubTask {

    @Id
    @SequenceGenerator(
            name = "sub_task_sequence",
            sequenceName = "sub_task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sub_task_sequence"
    )
    private Long id;
    private String description;
    private Long accountId;
    private Long taskId;

    public SubTask() {}

    public SubTask(String description, Long accountId, Long taskId) {
        this.description = description;
        this.accountId = accountId;
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", userId=" + accountId +
                ", taskId=" + taskId +
                '}';
    }
}
