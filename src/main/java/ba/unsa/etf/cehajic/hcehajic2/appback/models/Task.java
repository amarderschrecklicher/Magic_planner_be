package ba.unsa.etf.cehajic.hcehajic2.appback.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"username"})
public class Task {

    @Id
    @SequenceGenerator(
            name = "task_sequence_new",
            sequenceName = "task_sequence_new",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence_new"
    )

    private Long id;
    private String taskName;
    private String description;

    private LocalDate dueDate;
    private String dueTime;

    @ManyToOne
    @JoinColumn(name = "childId")
    private Child child;

    private boolean priority;
    private boolean done;
    @Column(nullable = false)
    private boolean notiSent = false;
    // private String difficulty;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime taskSent;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime taskStart;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime taskEnd;

}
