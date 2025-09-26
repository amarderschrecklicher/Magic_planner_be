package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import ba.unsa.etf.cehajic.hcehajic2.appback.constants.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ba.unsa.etf.cehajic.hcehajic2.appback.child.Child;
import ba.unsa.etf.cehajic.hcehajic2.appback.child.ChildService;
import ba.unsa.etf.cehajic.hcehajic2.appback.token.Token;
import ba.unsa.etf.cehajic.hcehajic2.appback.token.TokenService;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/task")
@CrossOrigin
class TaskController {

    private final TaskService taskService;
    private final TokenService tokenService;
    private final ChildService childService;
    private final TaskNotificationService notificationService;
    private final TaskSchedulerService taskSchedulerService;

    @Autowired
    TaskController(TaskService taskService, TokenService tokenService, ChildService childService, TaskNotificationService notificationService, TaskSchedulerService taskSchedulerService) {
        this.taskService = taskService;
        this.tokenService = tokenService;
        this.childService = childService;
        this.notificationService = notificationService;
        this.taskSchedulerService = taskSchedulerService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.GetAllTasks();
    }

    @GetMapping(path = "/{id}")
    public List<TaskRequestDTO> getTasksForAccount(@PathVariable("id") Long id) {
        return taskService.GetTasksForAccount(id);
    }

    @GetMapping(path = "/undone/{id}")
    public List<Task> getUndoneTasksForAccount(@PathVariable("id") Long id) {
        return taskService.GetUndoneTasksForAccount(id);
    }

    @GetMapping(path = "/alldone/{id}")
    public List<Task> getDoneTasksForAccount(@PathVariable("id") Long id) {
        return taskService.GetDoneTasksForAccount(id);
    }

    @PostMapping
    public ResponseEntity<Task> addNewTask(@RequestBody Task task) {

        Child child = childService.GetChildById(task.getChild().getId());
        task.setChild(child);

        Task newTask = taskService.AddNewTask(task);

        Optional<List<Token>> pushTokens = tokenService.GetTokensForAccount(task.getChild().getId());

        System.out.println(pushTokens);

        // Send push notification to each token
        pushTokens.ifPresent(tokens -> notificationService.sendAllMobileNotifications(tokens, TaskMapper.toDTO(newTask), NotificationMessage.NEW_TASK));

        Instant when = taskSchedulerService.calculateNotificationTime(newTask);

        if (when != null) {
            taskSchedulerService.scheduleTaskNotification(
                    newTask.getId(),
                    when,
                    () -> taskSchedulerService.taskEndingSoon(newTask)
            );
        }

        return ResponseEntity.ok().body(newTask);
    }


    @PutMapping(path = "/start/{id}")
    public void startTask(@PathVariable Long id) {
        taskService.StartTask(id);
    }

    @PutMapping(path = "/done/{id}")
    public void finishTask(@PathVariable Long id) {
        taskService.FinishTask(id);
    }

    @DeleteMapping(path = {"/{taskId}"})
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        System.out.println("Delete called!");
        taskSchedulerService.cancelTaskNotifications(taskId);
        taskService.deleteTask(taskId);
    }

    @GetMapping(path = "/deadline-stats/{childId}")
    public ResponseEntity<List<Long>> getDeadlineStats(@PathVariable Long childId) {
        List<Long> stats = taskService.calculateTasksInAndOutOfDeadline(childId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping(path = "/tasks-over-time/{childId}")
    public ResponseEntity<Map<LocalDate, Long>> getTasksOverTime(@PathVariable Long childId) {
        Map<LocalDate, Long> stats = taskService.calculateCompletedTasksOverTime(childId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/task-summary/{childId}")
    public ResponseEntity<List<Map<String, Object>>> getTaskSummary(@PathVariable Long childId) {
        return ResponseEntity.ok(taskService.getTaskSummary(childId));
    }
}
