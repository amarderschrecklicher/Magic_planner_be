package ba.unsa.etf.cehajic.hcehajic2.appback.services;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;

import ba.unsa.etf.cehajic.hcehajic2.appback.constants.NotificationMessage;
import ba.unsa.etf.cehajic.hcehajic2.appback.mappers.TaskMapper;
import ba.unsa.etf.cehajic.hcehajic2.appback.models.Task;
import ba.unsa.etf.cehajic.hcehajic2.appback.models.Token;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;


@Service
public class TaskSchedulerService {

    private final TaskNotificationService notificationService;
    private final TaskScheduler taskScheduler;
    private final TaskService taskService;
    private final TokenService tokenService;

    private final Map<Long, List<ScheduledFuture<?>>> registry = new ConcurrentHashMap<>();

    public TaskSchedulerService(TaskScheduler taskScheduler,
                                TaskNotificationService notificationService,
                                TaskService taskService,
                                TokenService tokenService) {
        this.taskScheduler = taskScheduler;
        this.notificationService = notificationService;
        this.taskService = taskService;
        this.tokenService = tokenService;
    }

    public void scheduleTaskNotification(Long taskId,Instant when, Runnable job) {

        Runnable wrapped = () -> {
            try{
                job.run();
            } finally {
                List<ScheduledFuture<?>> list = registry.get(taskId);
                if (list != null) {
                    list.removeIf(f -> f.isDone() || f.isCancelled());
                }
            }
        };
       ScheduledFuture<?> future = taskScheduler.schedule(wrapped, when);
       registry.computeIfAbsent(taskId, k -> new CopyOnWriteArrayList<>()).add(future);
    }



    public void taskEndingSoon(Task taskEndingSoon) {

        System.out.println(taskEndingSoon);

        Optional<List<Token>> pushTokens = tokenService.GetTokensForAccount(taskEndingSoon.getChild().getId());

        System.out.println(pushTokens);

        pushTokens.ifPresent(tokens -> notificationService.sendAllMobileNotifications(tokens, TaskMapper.toDTO(taskEndingSoon), NotificationMessage.ENDING_TASK));

        taskService.NotificationSent(taskEndingSoon.getId());

    }



    public void cancelTaskNotifications(Long taskId) {
        List<ScheduledFuture<?>> futures = registry.remove(taskId);
        if (futures == null) return;
        for (ScheduledFuture<?> f : futures) {
            f.cancel(false); // don't interrupt if already running
        }
    }


    public Instant calculateNotificationTime(Task taskEndingSoon) {
        // Pretpostavljam da je dueDate = "2025-09-22", dueTime = "14:30"
        LocalDate dueDate = taskEndingSoon.getDueDate();
        LocalTime dueTime = LocalTime.parse(taskEndingSoon.getDueTime());
        LocalDateTime dueDateTime = LocalDateTime.of(dueDate, dueTime);

        LocalDateTime now = LocalDateTime.now();

        // Ako je dueTime barem 2h ispred trenutnog vremena
        //if (Duration.between(now, dueDateTime).toHours() >= 2) {
            return dueDateTime.minusMinutes(30).atZone(ZoneId.of("Europe/Sarajevo")).toInstant();

        //} else {
        //    return null;
        //}

    }
}

