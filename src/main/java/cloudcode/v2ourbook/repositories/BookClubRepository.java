package cloudcode.v2ourbook.repositories;
import cloudcode.v2ourbook.models.BookClub;
import cloudcode.v2ourbook.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@RepositoryRestResource(path="authorities")
@Repository
public interface BookClubRepository extends JpaRepository<BookClub,Long> {
    List<BookClub> findAllByBookclubOwner(User bookclubOwner);

    BookClub findBookClubById(Long id);

    Optional<BookClub> findByName(String name);
}
