package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import java.util.List;

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

    public void checkTasksEndingSoon() {

        // Call the repository method with the formatted time strings
        List<Task> tasksEndingSoon = taskRepository.findTasksWithHalfTimeLeft();
        System.out.println(tasksEndingSoon);
    
        // Iterate over the tasks and send notifications
        for (Task task : tasksEndingSoon) {
            List<String> pushTokens = notificationService.getTokens(task.getChild().getEmail());
/* 
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime sentTime = task.getSent();
            LocalDateTime dueTime = LocalDateTime.of(sentTime.toLocalDate(), LocalDateTime.parse(task.getDueTime(), timeFormatter).toLocalTime());
            Duration duration = Duration.between(sentTime, dueTime);
            long timeLeft = duration.getSeconds() / 2;
            String message = "Ostalo je još ";

            if (dueTime.isAfter(sentTime)) {
                long totalMinutes = duration.toMinutes();
                long hours = totalMinutes / 60;
                long minutes = totalMinutes % 60;
                
                if (hours > 0) {
                    message += (hours + "H i ");
                } 
                message += (minutes+"min");
            }
*/            
            System.out.println(pushTokens);


            // Send push notification to each token
            for (String pushToken : pushTokens) {
                //notificationService.sendMobileNotification(pushToken, task, message);
                notificationService.sendMobileNotification(pushToken, task, "Uskoro ističe vrijeme!");
            }
            
            taskService.NotificationSent(task.getId());
            
        }
    }
}

