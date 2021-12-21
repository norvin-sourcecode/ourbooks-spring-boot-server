package cloudcode.v2ourbook.repositories;

import cloudcode.v2ourbook.models.GetBookProcess;
import cloudcode.v2ourbook.enums.GetBookStatus;
import cloudcode.v2ourbook.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

//@RepositoryRestResource(path="processes")
@Repository
public interface GetBookProcessRepository extends JpaRepository<GetBookProcess,Long> {

    List<GetBookProcess> findAllByBookGiver(User bookGiver);

    List<GetBookProcess> findAllByBookReceiver(User bookReceiver);

    List<GetBookProcess> findAllByBookGiverAndStatus(User bookGiver, GetBookStatus status);

    List<GetBookProcess> findAllByBookReceiverAndStatus(User bookReceiver, GetBookStatus status);

    Integer countAllByBookGiver(User bookGiver);

    Integer countAllByBookReceiver(User bookGiver);

//    GetBookProcess findBy
}
