package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import java.util.List;
import java.util.Optional;

import ba.unsa.etf.cehajic.hcehajic2.appback.token.Token;
import ba.unsa.etf.cehajic.hcehajic2.appback.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskSchedulerService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskNotificationService notificationService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private TokenService tokenService;

    public void checkTasksEndingSoon() {

        // Call the repository method with the formatted time strings
        List<Task> tasksEndingSoon = taskRepository.findTasksWithHalfTimeLeft();
        System.out.println(tasksEndingSoon);
    
        // Iterate over the tasks and send notifications
        for (Task task : tasksEndingSoon) {
            Optional<List<Token>> pushTokens = tokenService.GetTokensForAccount(task.getChild().getId());

            System.out.println(pushTokens);

            pushTokens.ifPresent(tokens -> notificationService.sendAllMobileNotifications(tokens, task, "Uskoro ističe vrijeme!"));

            
            taskService.NotificationSent(task.getId());
            
        }
    }
}

