package cloudcode.v2ourbook.services;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.net.http.HttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class NotificationService {

    UserService userService;

    public NotificationService(UserService userService) {
        this.userService = userService;
    }

    public void sendNotification(List<String> messageList, List<String> pushTokens) throws IOException, InterruptedException {
        String postEndpoint = "https://us-central1-totemic-beaker-332216.cloudfunctions.net/newNotification";

//        String inputJson = "{ \"pushTokens\":\" "+ new JSONArray(pushTokens) +" \", \"messageList\":\" " + new JSONArray(pushTokens) + " \"}";

        String inputJson = "{\"pushTokens\":"+ helper(pushTokens) +", \"messageList\":"+ helper(messageList) +"}";


        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();

        var client = HttpClient.newHttpClient();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    String helper(List<String> tmpArr) {
        String result = "";
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (int i = 0; i < tmpArr.size(); i++) {
            str.append("\"").append(tmpArr.get(i)).append("\"");
            if (i != tmpArr.size()-1) {
                str.append(",");
            }
        }
        str.append("]");
        result = str.toString();
        return result;
    }

}
