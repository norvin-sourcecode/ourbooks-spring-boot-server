package cloudcode.v2ourbook.repositories;

import cloudcode.v2ourbook.models.Book;
import cloudcode.v2ourbook.models.GetBookRequest;
import cloudcode.v2ourbook.enums.RequestResult;
import cloudcode.v2ourbook.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GetBookRequestRepository extends JpaRepository<GetBookRequest,Long> {

    List<GetBookRequest> findAllByGiverAndRequestResult(User giver, RequestResult getBookRequestResult);

    Optional<GetBookRequest> findGetBookRequestByBookAndReceiverAndRequestResult(Book book, User receiver, RequestResult getBookRequestResult);

    Integer countAllByGiverAndRequestResult(User giver, RequestResult requestResult);

    Optional<GetBookRequest> findByBookAndGiverAndReceiverAndRequestResult(Book book, User giver, User receiver, RequestResult requestResult);
}
