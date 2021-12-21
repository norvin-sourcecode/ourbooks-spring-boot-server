package cloudcode.v2ourbook.controller;

import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.dto.BookShelfUserDto;
import cloudcode.v2ourbook.dto.UserDto;
import cloudcode.v2ourbook.models.User;
import cloudcode.v2ourbook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author norvin_klinkmann
 */

@RestController
public class FriendController {

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;

    // gets all friends of the users
    @GetMapping("/friend")
    public List<UserDto> getAllMyFriends() {
        return userController.getCurrentUser().getFriends().
                stream().map(UserDto::new).collect(Collectors.toList());
    }

    @GetMapping("/friend/bookShelves")
    public ResponseEntity<List<BookShelfUserDto>> getAllMyFriendsForBookShelf() {
        return new ResponseEntity<>(userController.getCurrentUser().getFriends().
                stream().map(BookShelfUserDto::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    // Create a new Note
//    @PostMapping("/friend")
//    public ResponseEntity<?> newFriend(@Valid @RequestBody RestFriend bodyFriend) {
//        Friend friend = new Friend();
//        friend.setMe(userController.getCurrentUser());
//        friend.setFriend(userRepository.findById(bodyFriend.getId()).get());
//        friendRepository.save(friend);
//        friendRepository.save(friend.reverse(friend));
//        return ResponseEntity.ok().build();
//    }

    // Get a Single Note
//    @GetMapping("/friend/{id}")
//    public Friend getFriendById(@PathVariable(value = "id") Long contactId) throws ExceptionBlueprint {
//        return friendRepository.findById(contactId)
//                .orElseThrow(() -> new ExceptionBlueprint("contactnotfound", "no", 1));
//    }

//    // Update a Note
//    @PutMapping("/contact/{id}")
//    public ResponseEntity<?> updateNote(@PathVariable(value = "id") Long contactId,
//                           @Valid @RequestBody Contact contactDetails) throws ExceptionBlueprint {
//
//        Contact contact = contactRepository.findById(contactId)
//                .orElseThrow(() -> new ExceptionBlueprint("contactnotfound","no",1));
//
//        contact.setMe(userController.getCurrentUser());
//        contact.setFriend(contactDetails.getFriend());
//
//        Contact updatedContact = contactRepository.save(contact);
//
//        return ResponseEntity.ok().build();
//    }

    // Delete a Note
    @DeleteMapping("/friend/{id}")
    public ResponseEntity<?> deleteFriend(@PathVariable(value = "id") Long userId) throws ExceptionBlueprint {
        User user = userController.getCurrentUser();
        User friend = userRepository.findById(userId).
                orElseThrow(() -> new ExceptionBlueprint("user not found", "no", 1));
        if (user.getFriends().contains(friend)) {
            user.getFriends().remove(friend);
            user.setFriends(user.getFriends());
            friend.getFriends().remove(user);
            friend.setFriends(friend.getFriends());
            userRepository.save(user);
            userRepository.save(friend);
            return ResponseEntity.ok().build();
        } else {
            throw new ExceptionBlueprint("friend not found", "no", 1);
        }
    }
}
