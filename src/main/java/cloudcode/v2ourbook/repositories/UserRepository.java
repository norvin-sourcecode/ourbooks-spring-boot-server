package cloudcode.v2ourbook.repositories;

import cloudcode.v2ourbook.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author norvin_klinkmann
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    Optional<User> findById(Long id);

    boolean existsUserByUsername(@Param("username") String username);

    Optional<User> findUserByUsername(@Param("username") String username);

    List<User> findAllByUsernameContains(String username);

    Optional<User> findUserByPasswordResetToken(@Param("passwordResetToken") String passwordResetToken);

    Optional<User> findUserByUsernameOrEmail(String username, String email);

    Optional<User> findUserByEmail(String email);
}