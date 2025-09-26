package ba.unsa.etf.cehajic.hcehajic2.appback.controllers;

import java.util.List;
import java.util.Optional;

import ba.unsa.etf.cehajic.hcehajic2.appback.constants.NotificationMessage;
import ba.unsa.etf.cehajic.hcehajic2.appback.dtos.TaskRequestDTO;
import ba.unsa.etf.cehajic.hcehajic2.appback.mappers.TaskMapper;
import ba.unsa.etf.cehajic.hcehajic2.appback.models.SubTask;
import ba.unsa.etf.cehajic.hcehajic2.appback.models.Task;
import ba.unsa.etf.cehajic.hcehajic2.appback.services.SubTaskService;
import ba.unsa.etf.cehajic.hcehajic2.appback.services.TaskNotificationService;
import ba.unsa.etf.cehajic.hcehajic2.appback.services.TaskService;
import ba.unsa.etf.cehajic.hcehajic2.appback.models.Token;
import ba.unsa.etf.cehajic.hcehajic2.appback.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task/sub")
@CrossOrigin
class SubTaskController {

    private final SubTaskService subTaskService;
    private final TaskService taskService;
    private final TaskNotificationService taskNotificationService;
    private final TokenService tokenService;

    @Autowired
    SubTaskController(SubTaskService subTaskService, TaskService taskService, TaskNotificationService taskNotificationService, TokenService tokenService) {
        this.subTaskService = subTaskService;
        this.taskService = taskService;
        this.taskNotificationService = taskNotificationService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public List<SubTask> getAllSubTasks() {
        return subTaskService.GetAllSubTasks();
    }

    @GetMapping(path="/{id}")
    public List<SubTask> getSubsForTask(@PathVariable("id") Long id) {
        return subTaskService.GetSubsForTask(id);
    }

    @PostMapping
    public ResponseEntity<SubTask> addNewSubTask(@RequestBody SubTask subTask) {
        Task task = taskService.getTaskById(subTask.getTask().getId());
        if (task == null) {
            return ResponseEntity.badRequest().build();
        }
        subTask.setTask(task);
        SubTask newSubTask = subTaskService.AddNewSubTask(subTask);
        return ResponseEntity.ok().body(newSubTask);
    }


    @PutMapping(path = "/done/{id}")
    public void finishSubTask(@PathVariable Long id,@RequestBody SubTask subTask) {   

        subTaskService.FinishSubTask(id, subTask.getDone());
        Task task = taskService.getTaskById(subTask.getTask().getId());

        if (task != null) {

        String message;
        if (Boolean.TRUE.equals(subTask.getDone())) {
            message = NotificationMessage.SUBTASK_DONE;
        } else {
            message = NotificationMessage.SUBTASK_NOT_DONE;
        }

        TaskRequestDTO subtask = TaskMapper.toDTO(task);
        subtask.setTaskName(subTask.getDescription());

        Optional<List<Token>> pushTokens = tokenService.GetTokensForAccount(task.getChild().getId());
        pushTokens.ifPresent(tokens ->
                taskNotificationService.sendAllMobileNotifications(tokens, subtask, message)
        );
        }
    }

    @DeleteMapping(path={"/{subId}"})
    public void deleteTask(@PathVariable("subId") Long subId) {
        System.out.println("Delete called!");
        subTaskService.deleteTask(subId);
    }
}