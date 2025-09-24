package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import java.time.*;
import java.util.List;
import java.util.Optional;

import ba.unsa.etf.cehajic.hcehajic2.appback.token.Token;
import ba.unsa.etf.cehajic.hcehajic2.appback.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskSchedulerService {

    @Autowired
    private TaskNotificationService notificationService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private TokenService tokenService;

    public void taskEndingSoon(Task taskEndingSoon) {

        System.out.println(taskEndingSoon);

        Optional<List<Token>> pushTokens = tokenService.GetTokensForAccount(taskEndingSoon.getChild().getId());

        System.out.println(pushTokens);

        pushTokens.ifPresent(tokens -> notificationService.sendAllMobileNotifications(tokens, taskEndingSoon, "Uskoro ističe vrijeme!"));
            
        taskService.NotificationSent(taskEndingSoon.getId());
            
    }


    public Instant calculateNotificationTime(Task taskEndingSoon) {
        // Pretpostavljam da je dueDate = "2025-09-22", dueTime = "14:30"
        LocalDate dueDate = taskEndingSoon.getDueDate();
        LocalTime dueTime = LocalTime.parse(taskEndingSoon.getDueTime());
        LocalDateTime dueDateTime = LocalDateTime.of(dueDate, dueTime);

        LocalDateTime now = LocalDateTime.now();

        // Ako je dueTime barem 2h ispred trenutnog vremena
        if (Duration.between(now, dueDateTime).toHours() >= 2) {
            return dueDateTime.minusMinutes(30).atZone(ZoneId.systemDefault()).toInstant();

        } else {
            return null;
        }

    }
}

