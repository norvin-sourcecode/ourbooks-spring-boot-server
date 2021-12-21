package cloudcode.v2ourbook.repositories;


import cloudcode.v2ourbook.models.ConfirmationToken;
import cloudcode.v2ourbook.models.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {

    Optional<ResetPasswordToken> findByResetPasswordToken(String token);
}
