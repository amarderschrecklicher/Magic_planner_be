package ba.unsa.etf.cehajic.hcehajic2.appback.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task/sub")
@CrossOrigin
class MaterialController {

    private final MaterialService subTaskService;

    @Autowired
    public MaterialController(MaterialService subTaskService) {
        this.subTaskService = subTaskService;
    }

    @GetMapping
    public List<Material> getAllSubTasks() {
        return subTaskService.GetAllSubTasks();
    }

    @GetMapping(path="/{id}")
    public List<Material> getSubsForTask(@PathVariable("id") Long id) {
        return subTaskService.GetSubsForTask(id);
    }

    @PostMapping
    public ResponseEntity<Material> addNewSubTask(@RequestBody Material subTask) {
        Material newSubTask = subTaskService.AddNewSubTask(subTask);
        return ResponseEntity.ok().body(newSubTask);
    }

    @PutMapping(path = "/done/{id}")
    public void finishSubTask(@PathVariable Long id) {
        subTaskService.FinishSubTask(id);
    }

    @DeleteMapping(path={"/{subId}"})
    public void deleteTask(@PathVariable("subId") Long subId) {
        System.out.println("Delete called!");
        subTaskService.deleteTask(subId);
    }
}