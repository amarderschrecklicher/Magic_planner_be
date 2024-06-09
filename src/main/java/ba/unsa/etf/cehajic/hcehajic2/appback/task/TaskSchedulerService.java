package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.unsa.etf.cehajic.hcehajic2.appback.token.Token;
import ba.unsa.etf.cehajic.hcehajic2.appback.token.TokenService;

import java.time.LocalDateTime;
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
        LocalDateTime start = LocalDateTime.now().plusMinutes(29).plusSeconds(30);
        LocalDateTime end = LocalDateTime.now().plusMinutes(30).plusSeconds(30);;

        List<Task> tasks = taskRepository.findTasksEndingIn30Minutes(start,end);

        for (Task task : tasks) {
            List<Token> pushTokens = tokenService.GetTokensForAccount(task.getChild().getId());

            // Send push notification to each token
            for (Token pushToken : pushTokens) {
                notificationService.sendMobileNotification(pushToken, task, "Još 30min");
            }
            notificationService.sendWebNotification(task);
        }
    }
}

