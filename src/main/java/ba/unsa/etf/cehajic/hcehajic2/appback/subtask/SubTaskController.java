package ba.unsa.etf.cehajic.hcehajic2.appback.subtask;

import java.util.List;

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

import ba.unsa.etf.cehajic.hcehajic2.appback.task.Task;
import ba.unsa.etf.cehajic.hcehajic2.appback.task.TaskService;

@RestController
@RequestMapping("/api/v1/task/sub")
@CrossOrigin
class SubTaskController {

    private final SubTaskService subTaskService;
    private final TaskService taskService;

    @Autowired
    public SubTaskController(SubTaskService subTaskService, TaskService taskService) {
        this.subTaskService = subTaskService;
        this.taskService = taskService;
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
    }

    @DeleteMapping(path={"/{subId}"})
    public void deleteTask(@PathVariable("subId") Long subId) {
        System.out.println("Delete called!");
        subTaskService.deleteTask(subId);
    }
}