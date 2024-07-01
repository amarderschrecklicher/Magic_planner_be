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
    // Get the current time in Oregon (US West) and convert to ZonedDateTime
    LocalDateTime now = LocalDateTime.now();
    ZonedDateTime oregonNow = now.atZone(ZoneId.of("America/Los_Angeles"));

    // Convert Oregon time to Sarajevo time
    ZonedDateTime sarajevoA = oregonNow.plusMinutes(29).plusSeconds(30).withZoneSameInstant(ZoneId.of("Europe/Sarajevo"));
    ZonedDateTime sarajevoB = oregonNow.plusMinutes(30).plusSeconds(30).withZoneSameInstant(ZoneId.of("Europe/Sarajevo"));

    // Format ZonedDateTime to ISO string
    String A = sarajevoA.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    System.out.println(A);
    String B = sarajevoB.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
 
    
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

