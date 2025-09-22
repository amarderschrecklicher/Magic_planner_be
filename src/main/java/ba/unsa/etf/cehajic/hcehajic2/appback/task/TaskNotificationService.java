package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import java.util.*;

import ba.unsa.etf.cehajic.hcehajic2.appback.token.Token;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class TaskNotificationService {
 
    public void sendAllMobileNotifications(List<Token> pushTokens,Task task,String title) {
        try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                for (Token pushToken : pushTokens) {

                    Map<String,Object> body = new HashMap<>();
                    body.put("to",pushToken.getToken());
                    body.put("title",title);
                    body.put("body",task.getTaskName());
                    body.put("sound","default");

                    Map<String,Object> data = new HashMap<>();
                    data.put("taskId",task.getId());
                    data.put("dueDate",task.getDueDate());
                    body.put("data",data);

                    HttpEntity<Map<String,Object>> entity = new HttpEntity<>(body, headers);

                    String apiUrl = "https://exp.host/--/api/v2/push/send";
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

                    if (response.getStatusCode() == HttpStatus.OK) {
                        System.out.println("Push notification sent successfully to token: " + pushToken.getToken());
                    } else {
                        System.out.println("Failed to send push notification to token: " + pushToken.getToken() + ". Response code: " + response.getStatusCode());
                    }
                }
            } 
            
            catch ( Exception e) {
                System.out.println(e);
            }
        
    }

    
}
