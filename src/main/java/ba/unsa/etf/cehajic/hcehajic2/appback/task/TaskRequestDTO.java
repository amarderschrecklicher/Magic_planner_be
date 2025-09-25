package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskRequestDTO {

    private Long id;
    private String taskName;
    private String description;
    private LocalDate dueDate;
    private String dueTime;
    private Long childId;
    private boolean priority;
    private boolean done;
    private boolean notiSent;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime taskSent;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime taskStart;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime taskEnd;
}
