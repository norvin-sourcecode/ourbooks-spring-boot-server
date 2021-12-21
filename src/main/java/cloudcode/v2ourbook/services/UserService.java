package cloudcode.v2ourbook.services;
import cloudcode.v2ourbook.dto.FeedPostDto;
import cloudcode.v2ourbook.enums.PostType;
import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.models.Book;
import cloudcode.v2ourbook.models.User;
import cloudcode.v2ourbook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookService bookService;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findUserByUsername((String) principal).get();
    }

    public User findById(Long id) throws ExceptionBlueprint {
        return userRepository.findById(id).
                orElseThrow(() -> new ExceptionBlueprint("user not found","no",1));
    }

    public User findByUsername(String name) throws ExceptionBlueprint {
        return userRepository.findUserByUsername(name).
                orElseThrow(() -> new ExceptionBlueprint("user not found","no",1));
    }

    public Set<User> userRadius(){
        User currentUser = getCurrentUser();
        Set<User> usersRadius = new HashSet<>(currentUser.getFriends());
        // for each bookclub get all the users and add them to usersRadius
        currentUser.getBookclubMemberships().
                forEach(b -> usersRadius.addAll(b.getBookclubMembers()));
        usersRadius.remove(currentUser);
        return usersRadius;
    }

    public List<FeedPostDto> recentActivity(Set<User> userRadius) {
        User currentUser = getCurrentUser();
        List<FeedPostDto> posts = new ArrayList<>();
        // render Post for all books, ... TODO add etc.
        for (User u :userRadius) {
            bookService.recentBooksByUser(u).forEach(b -> posts.add(new FeedPostDto(b, PostType.NEWBOOK)));
        }
        return posts;
    }


//    public void addFriend() {
//
//        Set<User> userUpdatedFriends = currentUser.getFriends();
//        userUpdatedFriends.add(friend);
//        currentUser.setFriends(userUpdatedFriends);
//        userRepository.save(currentUser);
//
//        Set<User> friendUpdatedFriends = friend.getFriends();
//        friendUpdatedFriends.add(currentUser);
//        friend.setFriends(friendUpdatedFriends);
//        userRepository.save(friend);
//    }
}
