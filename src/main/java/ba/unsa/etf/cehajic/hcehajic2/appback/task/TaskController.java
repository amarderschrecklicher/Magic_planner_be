package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import ba.unsa.etf.cehajic.hcehajic2.appback.child.Child;
import ba.unsa.etf.cehajic.hcehajic2.appback.child.ChildService;
import ba.unsa.etf.cehajic.hcehajic2.appback.token.Token;
import ba.unsa.etf.cehajic.hcehajic2.appback.token.TokenService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@CrossOrigin
class TaskController {

    private final TaskService taskService;
    private final TokenService tokenService;
    private final ChildService childService;

    @Autowired
    public TaskController(TaskService taskService, TokenService tokenService,ChildService childService) {
        this.taskService = taskService;
        this.tokenService = tokenService;
        this.childService = childService;
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

        Child child = childService.GetChildById(task.getChild().getId());
        task.setChild(child);

        Task newTask = taskService.AddNewTask(task);

        List<Token> pushTokens = tokenService.GetTokensForAccount(task.getChild().getId());

        // Send push notification to each token
        for (Token pushToken : pushTokens) {
            sendPushNotification(pushToken,newTask);
        }

        return ResponseEntity.ok().body(newTask);
    }

    private void sendPushNotification(Token pushToken,Task task) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                
                String body = "{\"to\": \"" + pushToken.getToken() + "\", \"title\": \"" + 
                "Imaš novi task!" + 
                "\", \"body\": \"" 
                + task.getTaskName() + "\", \"sound\": \"default\", \"data\": {\"taskId\": " + task.getId() + ", \"dueDate\": \"" + task.getDueDate() + "\"}}";
                HttpEntity<String> entity = new HttpEntity<>(body, headers);

                String apiUrl = "https://exp.host/--/api/v2/push/send";
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    System.out.println("Push notification sent successfully to token: " + pushToken);
                } else {
                    System.out.println("Failed to send push notification to token: " + pushToken + ". Response code: " + response.getStatusCode());
                }
            } 
            
            catch ( Exception e) {
                System.out.println(e);
            }
        
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
