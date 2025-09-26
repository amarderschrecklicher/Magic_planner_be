package ba.unsa.etf.cehajic.hcehajic2.appback.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @JoinColumn(name = "taskId")
    private Task task;

}
