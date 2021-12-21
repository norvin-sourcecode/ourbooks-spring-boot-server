package cloudcode.v2ourbook.services;

import cloudcode.v2ourbook.enums.RequestResult;
import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.models.BookClub;
import cloudcode.v2ourbook.models.BookShelfInviteRequest;
import cloudcode.v2ourbook.models.User;
import cloudcode.v2ourbook.repositories.BookClubRepository;
import cloudcode.v2ourbook.repositories.BookShelfInviteRequestRepository;
import cloudcode.v2ourbook.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    BookClubRepository bookClubRepository;

    UserRepository userRepository;

    UserService userService;

    BookShelfInviteRequestRepository bookShelfInviteRequestRepository;

    public RequestService(BookClubRepository bookClubRepository, UserRepository userRepository, UserService userService, BookShelfInviteRequestRepository bookShelfInviteRequestRepository) {
        this.bookClubRepository = bookClubRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.bookShelfInviteRequestRepository = bookShelfInviteRequestRepository;
    }

    public void createBookShelfInviteRequest(Long bookclubId, String newMemberName) throws ExceptionBlueprint {
        BookClub bookclub = bookClubRepository.findById(bookclubId)
                .orElseThrow(() -> new ExceptionBlueprint("bookclub not found","no",1));
        User newMember = userRepository.findUserByUsername(newMemberName)
                .orElseThrow(() -> new ExceptionBlueprint("user not found","no",1));
        User currentUser = userService.getCurrentUser();
        // check if there is already a pending request
        if (bookShelfInviteRequestRepository.findByReceiverAndBookClubAndRequestResult(newMember, bookclub, RequestResult.PENDING).isPresent()){
            throw new ExceptionBlueprint("request already present", "no", 1);
        }
        BookShelfInviteRequest newRequest = new BookShelfInviteRequest();
        newRequest.setRequestResult(RequestResult.PENDING);
        newRequest.setSender(currentUser);
        newRequest.setReceiver(newMember);
        newRequest.setBookClub(bookclub);
        newRequest.setMessage(currentUser.getUsername() + " hat dich zum " + bookclub.getName() + " Bücherregal eingeladen");
        bookShelfInviteRequestRepository.save(newRequest);
    }

    public void createBookShelfInviteRequest(BookClub bookclub, User newMember) throws ExceptionBlueprint {
        User currentUser = userService.getCurrentUser();
        // check if there is already a pending request
        if (bookShelfInviteRequestRepository.findByReceiverAndBookClubAndRequestResult(newMember, bookclub, RequestResult.PENDING).isPresent()){
            throw new ExceptionBlueprint("request already present", "no", 1);
        }
        BookShelfInviteRequest newRequest = new BookShelfInviteRequest();
        newRequest.setRequestResult(RequestResult.PENDING);
        newRequest.setSender(currentUser);
        newRequest.setReceiver(newMember);
        newRequest.setBookClub(bookclub);
        newRequest.setMessage(currentUser.getUsername() + " hat dich zum " + bookclub.getName() + " Bücherregal eingeladen");
        bookShelfInviteRequestRepository.save(newRequest);
    }
}
