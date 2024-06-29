package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.unsa.etf.cehajic.hcehajic2.appback.token.Token;
import ba.unsa.etf.cehajic.hcehajic2.appback.token.TokenService;

import java.time.LocalDateTime;
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

        LocalDateTime a = LocalDateTime.now().plusMinutes(29).plusSeconds(30);
        LocalDateTime b = LocalDateTime.now().plusMinutes(30).plusSeconds(30);

        String A = a.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String B = b.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        List<Task> tasksEndingSoon = taskRepository.findTasksEndingInNext30Minutes(A, B);

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

