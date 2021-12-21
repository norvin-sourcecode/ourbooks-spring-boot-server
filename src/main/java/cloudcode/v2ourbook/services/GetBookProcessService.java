package cloudcode.v2ourbook.services;

import cloudcode.v2ourbook.controller.UserController;
import cloudcode.v2ourbook.enums.BookStatus;
import cloudcode.v2ourbook.enums.GetBookStatus;
import cloudcode.v2ourbook.enums.RequestResult;
import cloudcode.v2ourbook.models.GetBookProcess;
import cloudcode.v2ourbook.models.GetBookRequest;
import cloudcode.v2ourbook.models.User;
import cloudcode.v2ourbook.repositories.GetBookProcessRepository;
import cloudcode.v2ourbook.repositories.UserRepository;
import com.google.api.services.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GetBookProcessService {

    UserService userService;

    UserRepository userRepository;

    GetBookProcessRepository getBookProcessRepository;

    public GetBookProcessService(UserService userService, UserRepository userRepository, GetBookProcessRepository getBookProcessRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.getBookProcessRepository = getBookProcessRepository;
    }

    public GetBookProcess create(GetBookRequest getBookRequest){
        GetBookProcess getBookProcess = new GetBookProcess();
        getBookProcess.setBookGiver(getBookRequest.getGiver());
        getBookProcess.setBookReceiver(getBookRequest.getReceiver());
        getBookProcess.setBook(getBookRequest.getBook());
        getBookProcess.setBookGetRequest(getBookRequest);

        getBookRequest.setGetBookRequestResult(RequestResult.APPROVED);
        getBookProcess.setStatus(GetBookStatus.STARTED);
        getBookProcess.getBook().setStatus(BookStatus.UNAVAILABLE);
        return getBookProcess;
    }

    public void store(GetBookProcess getBookProcess){
        User bookReceiver = getBookProcess.getBookReceiver();
        User bookGiver = userService.getCurrentUser();
        Set<GetBookProcess> giveProcesses = bookGiver.getGiveProcesses();
        giveProcesses.add(getBookProcess);
        bookGiver.setGiveProcesses(giveProcesses);
        Set<GetBookProcess> receiveProcesses = bookReceiver.getReceiveProcesses();
        receiveProcesses.add(getBookProcess);
        bookReceiver.setReceiveProcesses(receiveProcesses);
        getBookProcessRepository.save(getBookProcess);
//        userRepository.save(bookGiver);
//        userRepository.save(bookReceiver);
    }
}
