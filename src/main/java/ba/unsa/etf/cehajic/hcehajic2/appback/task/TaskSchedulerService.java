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
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime oregonNow = now.atZone(ZoneId.of("America/Los_Angeles"));
        
        // Convert Oregon time to Sarajevo time
        ZonedDateTime sarajevoA = oregonNow.withZoneSameInstant(ZoneId.of("Europe/Sarajevo")).plusMinutes(29).plusSeconds(30);
        ZonedDateTime sarajevoB = oregonNow.withZoneSameInstant(ZoneId.of("Europe/Sarajevo")).plusMinutes(30).plusSeconds(30);
        
        // Format ZonedDateTime to ISO string for verification
        String A = sarajevoA.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String B = sarajevoB.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        // Print the converted times for verification
        System.out.println("Time A in Sarajevo: " + A); 
    
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

