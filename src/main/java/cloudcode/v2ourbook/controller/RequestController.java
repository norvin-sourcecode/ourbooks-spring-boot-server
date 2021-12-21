package cloudcode.v2ourbook.controller;

import cloudcode.v2ourbook.dto.BookShelfInviteRequestDto;
import cloudcode.v2ourbook.dto.FriendRequestDto;
import cloudcode.v2ourbook.dto.GetBookProcessDto;
import cloudcode.v2ourbook.dto.GetBookRequestDto;
import cloudcode.v2ourbook.enums.BookStatus;
import cloudcode.v2ourbook.enums.RequestResult;
import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.models.*;
import cloudcode.v2ourbook.repositories.*;
import cloudcode.v2ourbook.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static cloudcode.v2ourbook.enums.GetBookStatus.AGREED;


@RestController
public class RequestController {

    UserController userController;

    UserRepository userRepository;

    BookRepository bookRepository;

    GetBookRequestRepository getBookRequestRepository;

    FriendRequestRepository friendRequestRepository;

    GetBookProcessService getBookProcessService;

    BookShelfInviteRequestRepository bookShelfInviteRequestRepository;

    BookClubRepository bookClubRepository;

    GetBookProcessRepository getBookProcessRepository;

    RequestService requestService;

    BookClubService bookClubService;

    MessageService messageService;

    BookService bookService;

    public RequestController(GetBookProcessService getBookProcessService, UserController userController, UserRepository userRepository, BookRepository bookRepository, GetBookRequestRepository getBookRequestRepository, GetBookProcessRepository getBookProcessRepository, FriendRequestRepository friendRequestRepository, BookShelfInviteRequestRepository bookShelfInviteRequestRepository, BookClubRepository bookClubRepository, RequestService requestService, BookClubService bookClubService, MessageService messageService, BookService bookService) {
        this.bookShelfInviteRequestRepository = bookShelfInviteRequestRepository;
        this.messageService = messageService;
        this.userController = userController;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.getBookRequestRepository = getBookRequestRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.getBookProcessService = getBookProcessService;
        this.bookClubRepository = bookClubRepository;
        this.getBookProcessRepository = getBookProcessRepository;
        this.requestService = requestService;
        this.bookClubService = bookClubService;
        this.bookService = bookService;
    }

    @GetMapping("/requests/getBookRequests")
    public ResponseEntity<List<GetBookRequestDto>> getBookRequests() {
        List<GetBookRequest> getBookRequests = getBookRequestRepository.findAllByGiverAndRequestResult(userController.getCurrentUser(), RequestResult.PENDING).
                stream().sorted(Comparator.comparing(GetBookRequest::getTimeCreated, Comparator.nullsLast(Comparator.reverseOrder()))).collect(Collectors.toList());
        List<GetBookRequestDto> getBookRequestDtos = getBookRequests.stream().map(GetBookRequestDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(getBookRequestDtos, HttpStatus.OK);
    }

    @GetMapping("/requests/friendRequests")
    public ResponseEntity<List<FriendRequestDto>> friendRequests() {
        List<FriendRequestDto> friendRequests = new ArrayList<>();
        friendRequestRepository.findAllByReceiverAndRequestResult(userController.getCurrentUser(), RequestResult.PENDING).forEach(request -> friendRequests.add(new FriendRequestDto(request)));
        return new ResponseEntity<>(friendRequests, HttpStatus.OK);
    }

    @GetMapping("/requests/BookShelfInviteRequests")
    public List<BookShelfInviteRequestDto> bookShelfInviteRequests() {
        return bookShelfInviteRequestRepository.findAllByReceiverAndRequestResult(userController.getCurrentUser(), RequestResult.PENDING).
                stream().map(BookShelfInviteRequestDto::new).collect(Collectors.toList());
    }

    @GetMapping("/requests/dashStat")
    public Integer RequestsDashStat() throws Exception {
        return getBookRequestRepository.countAllByGiverAndRequestResult(userController.getCurrentUser(), RequestResult.PENDING);
    }

    @GetMapping("/requests/alreadySend/{id}")
    public Boolean alreadySend(@PathVariable(value = "id") Long bookId) throws Exception {
        Book book = bookService.findById(bookId);
        Optional<GetBookRequest> pending = getBookRequestRepository.findGetBookRequestByBookAndReceiverAndRequestResult(book, userController.getCurrentUser(), RequestResult.PENDING);
        if (pending.isPresent()){
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/request/newGetBookRequest") // TODO message should be integrated and response possible
    public ResponseEntity<?> newGetBookRequest(@Valid @RequestBody GetBookRequestDto body) throws Exception {
        User currentUser = userController.getCurrentUser();
        Book book = bookRepository.findBookById(body.getBookId()).
                orElseThrow(() -> new ExceptionBlueprint("book not found", "no", 1));
        // check if there is already a pending request
        if (getBookRequestRepository.findByBookAndGiverAndReceiverAndRequestResult(book, book.getUserLocation(), currentUser, RequestResult.PENDING).isPresent()){
            throw new ExceptionBlueprint("request already present", "no", 1);
        }
        GetBookRequest getBookRequest = new GetBookRequest();
        getBookRequest.setMessage(currentUser.getUsername() + " will das Buch " + book.getTitel() + " ausleihen!");
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new Exception();
        }
        getBookRequest.setBook(book);
        getBookRequest.setGiver(book.getUserLocation());
        getBookRequest.setReceiver(userController.getCurrentUser());
        getBookRequestRepository.save(getBookRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/request/newFriendRequest/{username}")
    public ResponseEntity<?> newFriendRequestByUsername(@PathVariable(value = "username") String username) throws Exception {
        User currentUser = userController.getCurrentUser();
        User receiver = userRepository.findUserByUsername(username).
                orElseThrow(() -> new ExceptionBlueprint("user does not exist", "no", 1));
        // check if there is already a pending request
        if (friendRequestRepository.findByReceiverAndSenderAndRequestResult(receiver, currentUser, RequestResult.PENDING).isPresent()){
            throw new ExceptionBlueprint("request already present", "no", 1);
        }
        FriendRequest request = new FriendRequest();
        request.setSender(currentUser);
        request.setReceiver(receiver);
        request.setRequestResult(RequestResult.PENDING);
        friendRequestRepository.save(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request/newBookShelfInviteRequest")
    public ResponseEntity<?> newBookShelfInviteRequest(Long bookclubId, String newMemberName) throws ExceptionBlueprint { // TODO needs to be Authorized
        BookClub bookClub = bookClubService.findById(bookclubId);
        if (bookClub.getAdmins().contains(userController.getCurrentUser())){

        }
        requestService.createBookShelfInviteRequest(bookclubId, newMemberName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request/respondGetBookRequest/{id}")
    public GetBookProcessDto respondGetBookRequest(@RequestBody @PathVariable(value = "id") Long requestId, boolean accept) throws ExceptionBlueprint, ExecutionException, InterruptedException {
        GetBookRequest getBookRequest = getBookRequestRepository.findById(requestId).
                orElseThrow(() -> new ExceptionBlueprint("BookRequest not found","no",1));
        if (getBookRequest.getBook().getStatus() != BookStatus.AVAILABLE) {
            throw new ExceptionBlueprint("book not available","no",1); // TODO write new Exceptions for book not available
        }
        if (accept) {
            GetBookProcess process = getBookProcessService.create(getBookRequest);
            if (process.getBook().getLicence().isKeepLicence()){
                process.setStatus(AGREED);
            }
            getBookProcessService.store(process);
            messageService.createSystemMessage("Bitte wähle Bedingungen für den Verleih des Buchs aus!", process.getId());
            return new GetBookProcessDto(process);
        } else {
            getBookRequest.setGetBookRequestResult(RequestResult.DENIED);
            getBookRequestRepository.save(getBookRequest);
            return null;
        }
    }

    @PostMapping("/request/respondBookShelfInviteRequest/{id}") // TODO Post?
    public ResponseEntity<?> respondBookShelfInviteRequest(@RequestBody @PathVariable(value = "id") Long requestId, boolean accept) throws Exception {
        BookShelfInviteRequest request = bookShelfInviteRequestRepository.findById(requestId).
                orElseThrow(() -> new ExceptionBlueprint("Request not found", "no", 1));
        User currentUser = userController.getCurrentUser();
        BookClub bookClub = request.getBookClub();
        if (currentUser.getBookclubMemberships().contains(bookClub)){
            throw new ExceptionBlueprint("you are already part of this bookshelf","no",1);
        }
        if (accept){
            bookClub.getBookclubMembers().add(currentUser);
            bookClub.setBookclubMembers(bookClub.getBookclubMembers());
            currentUser.getBookclubMemberships().add(bookClub);
            currentUser.setBookclubMemberships(currentUser.getBookclubMemberships());
            request.setRequestResult(RequestResult.APPROVED);
            userRepository.save(currentUser);
            bookClubRepository.save(bookClub);
        } else {
            request.setRequestResult(RequestResult.DENIED);
        }
        bookShelfInviteRequestRepository.save(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request/respondFriendRequest/{id}") // TODO Post?
    public ResponseEntity<?> respondFriendRequest(@RequestBody @PathVariable(value = "id") Long requestId, boolean accept) throws Exception {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId).
                orElseThrow(() -> new ExceptionBlueprint("friendrequest does not exist","no",1));
        User currentUser = userController.getCurrentUser();
        User friend = friendRequest.getSender();
        if (currentUser.getFriends().contains(friend)){
            throw new ExceptionBlueprint("already a friend of yours","no",1);
        }
        System.out.println("current user: " + currentUser.getUsername());
        System.out.println("friend user: " + friend.getUsername());
        if (accept) {
            Set<User> userUpdatedFriends = currentUser.getFriends();
            userUpdatedFriends.add(friend);
            currentUser.setFriends(userUpdatedFriends);
            userRepository.save(currentUser);

            Set<User> friendUpdatedFriends = friend.getFriends();
            friendUpdatedFriends.add(currentUser);
            friend.setFriends(friendUpdatedFriends);
            userRepository.save(friend);

            friendRequest.setRequestResult(RequestResult.APPROVED);
        } else {
            friendRequest.setRequestResult(RequestResult.DENIED);
        }
        friendRequestRepository.save(friendRequest);
        return ResponseEntity.ok().build();
    }

}