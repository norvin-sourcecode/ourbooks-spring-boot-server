package cloudcode.v2ourbook.repositories;


import cloudcode.v2ourbook.models.Book;

import cloudcode.v2ourbook.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByTitel(@Param("titel") String titel);

    Optional<Book> findByUser(@Param("user") User user);

    Optional<Book> findBookById(Long id);

    List<Book> findAllByIsbn(String isbn);

    List<Book> findAllByAuthorNameContains(String authorName);

    List<Book> findAllByOrderByTimeCreatedDesc();

    List<Book> findAllByUser(User user);

    List<Book> findAllByTitelContains(String titel);

    //@RestResource(path = "/searchByTitel")
    Page<Book> findByTitelContains(@Param("query") String titel, Pageable pageable);

    Optional<Book> findBookByIsbn(@Param("isbn") String isbn);

    Integer countBooksByUser(User user);

    List<Book> findAllByUserLocationAndIsbn(User userLocation, String isbn);

}
