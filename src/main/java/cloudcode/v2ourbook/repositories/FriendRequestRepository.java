package cloudcode.v2ourbook.repositories;

import cloudcode.v2ourbook.models.FriendRequest;
import cloudcode.v2ourbook.enums.RequestResult;
import cloudcode.v2ourbook.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author norvin_klinkmann
 */

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findAllByReceiverAndRequestResult(User receiver, RequestResult requestResult);

    Optional<FriendRequest> findByReceiverAndSenderAndRequestResult(User receiver, User sender, RequestResult requestResult);
}
