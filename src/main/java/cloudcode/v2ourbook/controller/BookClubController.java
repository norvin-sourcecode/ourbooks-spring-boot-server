package cloudcode.v2ourbook.controller;

import cloudcode.v2ourbook.dto.BookClubDto;
import cloudcode.v2ourbook.dto.BookShelfBookClubDto;
import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.models.*;
import cloudcode.v2ourbook.services.BookClubService;
import cloudcode.v2ourbook.services.BookService;
import cloudcode.v2ourbook.services.RequestService;
import cloudcode.v2ourbook.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class BookClubController {

    UserService userService;

    BookService bookService;

    BookClubService bookClubService;

    RequestService requestService;

    public BookClubController(UserService userService, BookService bookService, BookClubService bookClubService, RequestService requestService) {
        this.userService = userService;
        this.bookService = bookService;
        this.bookClubService = bookClubService;
        this.requestService = requestService;
    }

    // returns a list of all BookClubs the user is part of
    @GetMapping("/bookclub")
    public ResponseEntity<List<BookClubDto>> bookclubs() {
        return new ResponseEntity<>(userService.getCurrentUser().getBookclubMemberships().stream().
                map(BookClubDto::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/bookclub/bookShelves")
    public ResponseEntity<List<BookShelfBookClubDto>> restBookShelves() {
        List<BookShelfBookClubDto> result = userService.getCurrentUser().getBookclubMemberships().stream().
                map(bc -> new BookShelfBookClubDto(bc, bookService.getBooksForBookClub(bc.getId()))).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/bookclub/filtered/{term}")
    public List<BookClubDto> bookclubsFiltered(@PathVariable(value = "term") String term) {
        List<BookClubDto> clubs = new ArrayList<>();
        for (BookClub c : bookClubService.findAllByBookclubOwner(userService.getCurrentUser())) {
            if (c.getName().toLowerCase().contains(term.toLowerCase())){
                clubs.add(new BookClubDto(c));
            }
        }
        return clubs;
    }

    // create a new BookClub and aString timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());dd members to it
    @PostMapping("/bookclub/createNew")
    public ResponseEntity<?> newBookclub(@RequestBody BookClubDto bookClubDto) throws ExceptionBlueprint {
//        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.ms").format(new Date());
        User currentUser = userService.getCurrentUser();
        BookClub newBookclub = new BookClub();
        if (bookClubService.nameTaken(bookClubDto.getName())){
            throw new ExceptionBlueprint("name already taken","no",1);
        }
        newBookclub.setName(bookClubDto.getName());
        newBookclub.setBookclubOwner(currentUser);
        newBookclub.addAdmin(currentUser);
        bookClubService.addMember(newBookclub, currentUser);
        // check that there are no duplicates and remove current user if present;
        bookClubDto.getBookclubMembers().remove(currentUser.getId());
        bookClubService.save(newBookclub);
        for (Long newMemberId : bookClubDto.getBookclubMembers().stream().distinct().collect(Collectors.toList())) {
            User newMember = userService.findById(newMemberId);
            // schicke eine anfrage an jeden User
            requestService.createBookShelfInviteRequest(newBookclub, newMember);
        }
//        bookClubRepository.save(newBookclub);
        bookClubService.save(newBookclub);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bookclub/{id}")
    public BookClubDto bookclubById(@PathVariable(value = "id") Long bookClubId) throws ExceptionBlueprint {
        return new BookClubDto(bookClubService.findById(bookClubId));
    }

    @GetMapping("/bookclub/addAdmin/{id}")
    public ResponseEntity<?> addAdmin(@PathVariable(value = "id") Long bookClubId, Long userId) throws ExceptionBlueprint {
        BookClub bookClub = bookClubService.findById(bookClubId);
        User user = userService.findById(userId);
        if (bookClub.getAdmins().contains(userService.getCurrentUser())){
            bookClub.addAdmin(user);
            bookClubService.save(bookClub);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bookclub/removeAdmin/{id}")
    public ResponseEntity<?> removeAdmin(@PathVariable(value = "id") Long bookClubId, Long userId) throws ExceptionBlueprint {
        BookClub bookClub = bookClubService.findById(bookClubId);
        User user = userService.findById(userId);
        if (bookClub.getAdmins().contains(userService.getCurrentUser())){
            bookClub.removeAdmin(user);
            bookClubService.save(bookClub);
        }
        return ResponseEntity.ok().build();
    }

    // Update a Note
    @PutMapping("/bookclub/changeName/{id}")
    public ResponseEntity<?> updateBookClub(@PathVariable(value = "id") Long bookclubId, String newName) throws ExceptionBlueprint {
        BookClub bookClub = bookClubService.findById(bookclubId);
        if(bookClub.getAdmins().contains(userService.getCurrentUser()) && !bookClubService.nameTaken(newName)){
            bookClub.setName(newName); // TODO needs to check if name already used
            bookClubService.save(bookClub);
        } else {
            throw new ExceptionBlueprint("Not Authorized","no",1);
        }
        return ResponseEntity.ok().build();
    }

    // Delete a Note
    @DeleteMapping("/bookclub/{id}")
    public ResponseEntity<?> deleteBookClub(@PathVariable(value = "id") Long bookclubId) throws ExceptionBlueprint {
        BookClub bookClub = bookClubService.findById(bookclubId);
        if(bookClub.getAdmins().contains(userService.getCurrentUser())){
            bookClubService.delete(bookClub);
        } else {
            throw new ExceptionBlueprint("Not Authorized","no",1);
        }
        return ResponseEntity.ok().build();
    }
}
