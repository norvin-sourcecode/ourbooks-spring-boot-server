package cloudcode.v2ourbook.controller;

import cloudcode.v2ourbook.dto.FeedPostDto;
import cloudcode.v2ourbook.dto.FireUserDto;
import cloudcode.v2ourbook.models.User;
import cloudcode.v2ourbook.services.BookService;
import cloudcode.v2ourbook.services.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class FeedController {

    UserService userService;

    public FeedController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/feed")
    public List<FeedPostDto> userFeed() {
        //get userradius
        Set<User> userRadius = userService.userRadius();
        // get a list of all recent user activity
        return userService.recentActivity(userRadius);
    }
}
