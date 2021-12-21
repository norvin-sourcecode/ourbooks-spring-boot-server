package cloudcode.v2ourbook.repositories;


import cloudcode.v2ourbook.models.Book;
import cloudcode.v2ourbook.models.ConfirmationToken;
import cloudcode.v2ourbook.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);
}
