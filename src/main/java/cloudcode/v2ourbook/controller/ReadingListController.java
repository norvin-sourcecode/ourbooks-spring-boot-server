package cloudcode.v2ourbook.controller;
import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.models.Book;
import cloudcode.v2ourbook.dto.BookDto;
import cloudcode.v2ourbook.models.User;
import cloudcode.v2ourbook.repositories.BookRepository;

import cloudcode.v2ourbook.repositories.UserRepository;
import cloudcode.v2ourbook.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@RestController
public class ReadingListController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookService bookService;

    @GetMapping("/readingList/dashStat")
    public Integer dashStat() {
        return userController.getCurrentUser().getReadingList().size();
    }

    @PostMapping("/readingList/addBook/{id}")
    public ResponseEntity<?> addBookById(@PathVariable(value = "id") Long id) throws ExceptionBlueprint {
        Book book = bookRepository.findBookById(id).
                orElseThrow(() -> new ExceptionBlueprint("Book not found","no",1));
        User currentUser = userController.getCurrentUser();
        Set<Book> readingList = currentUser.getReadingList();
        if (!readingList.contains(book)){
            readingList.add(book);
            userRepository.save(currentUser);
            return ResponseEntity.ok().build();
        } else {
            throw new ExceptionBlueprint("Book is already in the list","no",1);
        }
    }

    @PostMapping("/readingList/addBookByIsbn/{isbn}") // TODO bookmark
    public ResponseEntity<Long> addBookByISBN(@PathVariable(value = "isbn") String isbn) throws ExceptionBlueprint, IOException {
        ResponseEntity<String> response = bookService.lookUpByIsbn(isbn);
        Book book = bookService.extractBookFromResponse(response, userController.getCurrentUser(), 0);
        bookRepository.save(book);
        System.out.println(new BookDto(book));
        User currentUser = userController.getCurrentUser();
        Set<Book> readingList = currentUser.getReadingList();
        if (!readingList.contains(book)){
            readingList.add(book);
            userRepository.save(currentUser);
            return new ResponseEntity<>(book.getId(), HttpStatus.OK);
        } else {
            throw new ExceptionBlueprint("Book is already in the list","no",1);
        }
    }

    @PostMapping("/readingList/inReadingList/{id}")
    public ResponseEntity<Boolean> inReadingList(@PathVariable(value = "id") Long id) throws ExceptionBlueprint {
        Book book = bookRepository.findBookById(id).
                orElseThrow(() -> new ExceptionBlueprint("Book not found","no",1));
        Set<Book> readingList = userController.getCurrentUser().getReadingList();
        return new ResponseEntity<>(readingList.contains(book), HttpStatus.OK);
    }

    @DeleteMapping("/readingList/removeBook/{id}")
    public ResponseEntity<?> removeBookById(@PathVariable(value = "id") Long id) throws ExceptionBlueprint {
        System.out.println("id: " + id);
        User currentUser = userController.getCurrentUser();
        Set<Book> readingList = currentUser.getReadingList();
        Book book = bookRepository.findBookById(id).
                orElseThrow(() -> new ExceptionBlueprint("Book not found","no",1));
        if (readingList.contains(book)){
            readingList.remove(book);
            userRepository.save(currentUser);
            return ResponseEntity.ok().build();
        } else {
            throw new ExceptionBlueprint("Book is not in the list","no",1);
        }
    }

}
