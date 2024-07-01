package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.unsa.etf.cehajic.hcehajic2.appback.token.Token;
import ba.unsa.etf.cehajic.hcehajic2.appback.token.TokenService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class TaskSchedulerService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskNotificationService notificationService;

    @Autowired
    private TokenService tokenService;

    public void checkTasksEndingSoon() {
     // Get the current local time and convert to ZonedDateTime in the system's default time zone
     LocalDateTime now = LocalDateTime.now();
     ZonedDateTime localNow = now.atZone(ZoneId.systemDefault());
 
     // Convert local time to UTC
     ZonedDateTime utcA = localNow.plusMinutes(29).plusSeconds(30).withZoneSameInstant(ZoneId.of("UTC"));
     ZonedDateTime utcB = localNow.plusMinutes(30).plusSeconds(30).withZoneSameInstant(ZoneId.of("UTC"));
 
     // Format ZonedDateTime to ISO string
     String A = utcA.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);System.out.println(A);
     String B = utcB.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
 
    
        // Call the repository method with the formatted time strings
        List<Task> tasksEndingSoon = taskRepository.findTasksEndingInNext30Minutes(A, B);
        System.out.println(tasksEndingSoon);
    
        // Iterate over the tasks and send notifications
        for (Task task : tasksEndingSoon) {
            List<Token> pushTokens = tokenService.GetTokensForAccount(task.getChild().getId());
            System.out.println(pushTokens);
            // Send push notification to each token
            for (Token pushToken : pushTokens) {
                notificationService.sendMobileNotification(pushToken, task, "Još 30min");
            }
        }
    }
}

