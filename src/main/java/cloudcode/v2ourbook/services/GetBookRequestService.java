package cloudcode.v2ourbook.services;

import cloudcode.v2ourbook.enums.BookStatus;
import cloudcode.v2ourbook.enums.GetBookStatus;
import cloudcode.v2ourbook.enums.RequestResult;
import cloudcode.v2ourbook.models.GetBookProcess;
import cloudcode.v2ourbook.models.GetBookRequest;
import cloudcode.v2ourbook.models.User;
import cloudcode.v2ourbook.repositories.GetBookProcessRepository;
import cloudcode.v2ourbook.repositories.GetBookRequestRepository;
import cloudcode.v2ourbook.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GetBookRequestService {

    GetBookRequestRepository getBookRequestRepository;

    public GetBookRequestService(GetBookRequestRepository getBookRequestRepository) {
        this.getBookRequestRepository = getBookRequestRepository;
    }

    public List<GetBookRequest> cleanupInvalidRequests(List<GetBookRequest> getBookRequests){
        // check if the request points to a book, that is not available and remove it from the list
        getBookRequests.removeIf(request -> request.getBook().getStatus().equals(BookStatus.UNAVAILABLE));
        // check if the request points to a book that is not in the possession of the giver anymore and delete the request
        for (GetBookRequest request : getBookRequests) {
            if (!request.getBook().getUserLocation().equals(request.getGiver())){
                getBookRequests.remove(request);
                getBookRequestRepository.delete(request);
            }
        }
        return getBookRequests;
    }
}
