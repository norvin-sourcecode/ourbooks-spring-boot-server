package cloudcode.v2ourbook.repositories;

import cloudcode.v2ourbook.models.Authority;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(path="authorities")
@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long> {

}
