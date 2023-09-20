package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import ba.unsa.etf.cehajic.hcehajic2.appback.usersettings.UserSettings;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@CrossOrigin
class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.GetAllTasks();
    }

    @GetMapping(path = "/{id}")
    public List<Task> getTasksForAccount(@PathVariable("id") Long id) {
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
        Task newTask = taskService.AddNewTask(task);
        return ResponseEntity.ok().body(newTask);
    }

    @PutMapping(path = "/done/{id}")
    public void finishTask(@PathVariable Long id) {
        taskService.FinishTask(id);
    }


    @DeleteMapping(path = {"/{taskId}"})
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        System.out.println("Delete called!");
        taskService.deleteTask(taskId);
    }
}
