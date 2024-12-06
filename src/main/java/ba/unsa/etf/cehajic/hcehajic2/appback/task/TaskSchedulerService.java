package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.unsa.etf.cehajic.hcehajic2.appback.token.TokenService;


@Service
public class TaskSchedulerService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskNotificationService notificationService;

    @Autowired
    private TokenService tokenService;

    public void checkTasksEndingSoon() {

        // Call the repository method with the formatted time strings
        /*List<Task> tasksEndingSoon = taskRepository.findTasksWithHalfTimeLeft();
        System.out.println(tasksEndingSoon);
    
        // Iterate over the tasks and send notifications
        for (Task task : tasksEndingSoon) {
            List<String> pushTokens = notificationService.getTokens(task.getChild().getEmail());
            System.out.println(pushTokens);
            // Send push notification to each token
            for (String pushToken : pushTokens) {
                notificationService.sendMobileNotification(pushToken, task, "Uskoro završava!");
            }
        }*/
    }
}

