package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import nl.martijndwars.webpush.Notification;

import ba.unsa.etf.cehajic.hcehajic2.appback.token.Token;
import nl.martijndwars.webpush.PushService;

@Service
public class TaskNotificationService {

    private final PushService pushService;

    public TaskNotificationService(PushService pushService) {
        this.pushService = pushService;
    }


    public void sendMobileNotification(Token pushToken,Task task,String title) {
        try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                
                String body = "{\"to\": \"" + pushToken.getToken() + "\", \"title\": \"" + 
                title + 
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
    
}
