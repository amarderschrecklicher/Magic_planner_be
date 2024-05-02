package ba.unsa.etf.cehajic.hcehajic2.appback.material;

import javax.persistence.*;

import ba.unsa.etf.cehajic.hcehajic2.appback.task.Task;

@Entity
@Table
public class Material {

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
    private Boolean done;
    @ManyToOne
    @JoinColumn(name = "taskId") // Specify the name of the foreign key column
    private Task task;

    public Material() {
        done = false;
    }

    public Material(String description,Long taskId) {
        this.description = description;
        this.task = new Task();
        this.task.setId(taskId);
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

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", done=" + done +
                '}';
    }
}