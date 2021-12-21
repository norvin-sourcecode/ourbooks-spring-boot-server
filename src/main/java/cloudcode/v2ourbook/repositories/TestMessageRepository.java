package cloudcode.v2ourbook.repositories;

import cloudcode.v2ourbook.models.TestMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestMessageRepository extends CrudRepository<TestMessage, Integer> {

}
