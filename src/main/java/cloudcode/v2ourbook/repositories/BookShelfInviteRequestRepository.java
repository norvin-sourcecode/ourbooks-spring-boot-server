package cloudcode.v2ourbook.repositories;

import cloudcode.v2ourbook.enums.RequestResult;
import cloudcode.v2ourbook.models.BookClub;
import cloudcode.v2ourbook.models.BookShelfInviteRequest;
import cloudcode.v2ourbook.models.FriendRequest;
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
public interface BookShelfInviteRequestRepository extends JpaRepository<BookShelfInviteRequest, Long> {

    List<BookShelfInviteRequest> findAllByReceiverAndRequestResult(User receiver, RequestResult requestResult);

    Optional<BookShelfInviteRequest> findByReceiverAndBookClubAndRequestResult(User receiver, BookClub bookClub, RequestResult requestResult);
}
