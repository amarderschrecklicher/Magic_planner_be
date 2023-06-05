package ba.unsa.etf.cehajic.hcehajic2.appback.subtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task/sub")
@CrossOrigin
class SubTaskController {

    private final SubTaskService subTaskService;

    @Autowired
    public SubTaskController(SubTaskService subTaskService) {
        this.subTaskService = subTaskService;
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
        SubTask newSubTask = subTaskService.AddNewSubTask(subTask);
        return ResponseEntity.ok().body(newSubTask);
    }

    @DeleteMapping(path={"/{subId}"})
    public void deleteTask(@PathVariable("subId") Long subId) {
        System.out.println("Delete called!");
        subTaskService.deleteTask(subId);
    }
}