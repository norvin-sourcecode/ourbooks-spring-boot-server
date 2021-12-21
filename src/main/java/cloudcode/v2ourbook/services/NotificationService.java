package cloudcode.v2ourbook.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    UserService userService;

    public NotificationService(UserService userService) {
        this.userService = userService;
    }

    public void sendNotification(List<String> messageList, List<String> pushTokens){
        String url = "https://us-central1-totemic-beaker-332216.cloudfunctions.net/newNotification";
        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        // request body parameters
        Map<String, List<String>> map = new HashMap<>();
        map.put("messageList", messageList);
        map.put("pushTokens", pushTokens);
        // send POST request
        ResponseEntity<Void> response = restTemplate.postForEntity(url, map, Void.class);
        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful");
        } else {
            System.out.println("Request Failed");
        }
    }

}
