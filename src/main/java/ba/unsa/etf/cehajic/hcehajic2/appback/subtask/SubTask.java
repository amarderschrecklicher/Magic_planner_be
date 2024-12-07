package ba.unsa.etf.cehajic.hcehajic2.appback.subtask;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ba.unsa.etf.cehajic.hcehajic2.appback.task.Task;

@Entity
@Table
public class SubTask {

    @Id
    @SequenceGenerator(
            name = "sub_task_sequence_new",
            sequenceName = "sub_task_sequence_new",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sub_task_sequence_new"
    )
    private Long id;
    private String description;
    @Column(nullable = true)
    private Boolean done = false;
    private Boolean needPhoto;

    @ManyToOne
    @JoinColumn(name = "taskId") // Specify the name of the foreign key column
    private Task task;

    public SubTask() {
        done = false;
    }

    public SubTask(String description, Task task) {
        this.description = description;
        this.task = task;
        this.done = false;
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


    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getNeedPhoto() {
        return needPhoto;
    }

    public void setPhotoNeed(Boolean needPhoto) {
        this.needPhoto = needPhoto;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", done=" + done +
                ", needPhoto=" + needPhoto +
                '}';
    }
}
