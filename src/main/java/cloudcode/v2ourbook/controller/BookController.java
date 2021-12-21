package cloudcode.v2ourbook.controller;

import cloudcode.v2ourbook.dto.BookDto;
import cloudcode.v2ourbook.dto.FeedPostDto;
import cloudcode.v2ourbook.enums.BookStatus;
import cloudcode.v2ourbook.enums.PostType;
import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.models.*;
import cloudcode.v2ourbook.repositories.*;
import cloudcode.v2ourbook.services.BookService;
import cloudcode.v2ourbook.services.NotificationService;
import cloudcode.v2ourbook.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author norvin_klinkmann
 */

@RestController
public class BookController {


    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserController userController;

    @Autowired
    BookClubRepository bookclubRepository;

    @Autowired
    BookService bookService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

     // Get All Notes By CurrentUser
    @GetMapping("/books")
    public List<BookDto> getAllNotes() {
        List<Book> books = userController.getCurrentUser().getBooks();
        return books.stream().map(BookDto::new).collect(Collectors.toList());
    }

    @PostMapping("/newNotification")
    public void newNotification(@RequestBody List<String> pushTokens){
        List<String> messageList = List.of("hey 1", "hi 2");
        notificationService.sendNotification(messageList, pushTokens);
    }

    @GetMapping("/books/forUser/{id}")
    public List<BookDto> getAllNotes(@PathVariable(value = "id") Long id) throws ExceptionBlueprint {
        User user = userRepository.findById(id).
            orElseThrow(() -> new ExceptionBlueprint("book not found","no",1));
        return bookService.visibilityFilter(user.getBooks()).stream().
                map(BookDto::new).collect(Collectors.toList());
    }

    @GetMapping("/books/filtered/{term}")
    public List<BookDto> booksFiltered(@PathVariable(name = "term") String term) {
        List<BookDto> list = new java.util.ArrayList<>();
        for(Book e : bookRepository.findAll()){
            if (e.getTitel().toLowerCase().contains(term.toLowerCase())){
                list.add(new BookDto(e));
            }
        }
        return list;
    }

    // Create a new Note
    //@Transactional
    @PostMapping("/books/createBook")
    public ResponseEntity<Long> createBook(@Valid @RequestBody BookDto bookDto) throws FirebaseMessagingException {
        User currentUser = userController.getCurrentUser();
        Book book = new Book();
        book.setTitel(bookDto.getTitel());
        book.setIsbn(bookDto.getIsbn());
        book.setAuflage(bookDto.getAuflage());
        book.setAuthorName(bookDto.getAuthorName());
        book.setErscheinungsDatum(bookDto.getErscheinungsDatum());
        book.setSprache(bookDto.getSprache());
        book.setStatus(BookStatus.AVAILABLE);
        book.setUser(currentUser);
        book.setUserLocation(currentUser);
        book.setPictureUrl(bookDto.getPictureUrl());
        book.setDescription(bookDto.getDescription());
        currentUser.addBook(book);
        bookRepository.save(book);
        List<String> registrationTokens = currentUser.getFriends().stream().map(User::getToken).collect(Collectors.toList()); // TODO change token
        // See documentation on defining a message payload.
        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(currentUser.getUsername() + " hat ein neues Buch hochgeladen")
                        .setBody("Jetzt verfügbar: " + book.getTitel())
                        .build())
                .addAllTokens(registrationTokens)
                .build();
        BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
        System.out.println(response.getSuccessCount() + " messages were sent successfully");
        return new ResponseEntity<>(book.getId(), HttpStatus.OK);
    }

    @GetMapping("/books/newlyAdded/{page}") // TODO ask ilker for hibernate keyword
    public List<BookDto> newlyAdded(@PathVariable(value = "page")int page){
        Set<User> usersRadius = userService.userRadius();
        List<Book> books = new ArrayList<>();
        usersRadius.forEach(u -> books.addAll(u.getBooks()));
        return bookService.visibilityFilter(books).stream().sorted(Comparator.comparing(Book::getTimeCreated, Comparator.nullsLast(Comparator.reverseOrder()))).
                limit(10).map(BookDto::new).collect(Collectors.toList()); // im so sorry
    }

    @GetMapping("/books/newlyAdded") // TODO ask ilker for hibernate keyword
    public List<FeedPostDto> newlyAdded(){
        Set<User> usersRadius = userService.userRadius();
        List<Book> books = new ArrayList<>();
        usersRadius.forEach(u -> books.addAll(u.getBooks()));
        return bookService.visibilityFilter(books).stream().sorted(Comparator.comparing(Book::getTimeCreated, Comparator.nullsLast(Comparator.reverseOrder()))).
                limit(10).map(b -> new FeedPostDto(b, PostType.NEWBOOK)).collect(Collectors.toList()); // im so sorry
    }

    // gets a list of the newl added books that are also on the reading List
    @GetMapping("/books/wishedFor") // TODO ask ilker for hibernate keyword
    public List<BookDto> wishedFor(){
        User currentUser = userService.getCurrentUser();
        Set<User> usersRadius = userService.userRadius();
        List<Book> books = new ArrayList<>();
        usersRadius.forEach(u -> books.addAll(u.getBooks()));
        return bookService.visibilityFilter(books).stream().filter(b -> currentUser.getReadingList().contains(b)).
                sorted(Comparator.comparing(Book::getTimeCreated, Comparator.nullsLast(Comparator.reverseOrder()))).
                limit(10).map(BookDto::new).collect(Collectors.toList()); // im so sorry
    }

    // Method compiles a list of known users that are either friends or
    // in a bib that the user is part of (order is: friends first, bib second)
    @GetMapping("/books/available/{isbn}")
    public List<Available> checkIfAvailable(@PathVariable(value = "isbn") String isbn) throws ExceptionBlueprint{
        Set<User> userRadius = userService.userRadius();
        // TODO ask ilker for more elegant aproach
        Set<Available> availableAtUsers = new HashSet<>();
        // each users books are searched through and the ones with the same isbn collected
        for (User u: userRadius) {
            List<Book> books = bookRepository.findAllByUserLocationAndIsbn(u, isbn);
            List<Book> userBooksTemp = books.stream()
                    .filter(b -> b.getStatus().equals(BookStatus.AVAILABLE) && bookService.visible(b)).collect(Collectors.toList());
            if (!userBooksTemp.isEmpty()){
                userBooksTemp.forEach(b -> availableAtUsers.add(
                        new Available(b.getUserLocation().getUsername(), b.getId())));
            }
        }
        return new ArrayList<>(availableAtUsers);


        // loops through all the bookclubs of the user and for each bookclub adds the users to the list
//        for (BookClub club:
//                userController.getCurrentUser().getBookclubMemberships()) {
//            for (User member :
//                    club.getBookclubMembers()){
//                if (!usersRadius.contains(member) && !member.equals(userController.getCurrentUser())){
//                    usersRadius.add(member);
//                }
//            }
//        }
        // Then looks for books of the given ISBN and checks for each user if the there is a book with a matching userid.
        // take all the User ids and check for every entry of the books with the same isbn if the book is from a known user;
//        List<Book> books = bookRepository.findAllByIsbn(isbn);
//        List<RestUser> usersWithBook = new java.util.ArrayList<>();
//        for (User user:
//             usersRadius) {
//            for (Book book :
//                    books) {
//                if (user.equals(book.getUser())){
//                    usersWithBook.add(new RestUser(user));
//                }
//            }
//        }
//        // It returns the list of userIds that have a the searched for book in the order friends first, bib second.
//        if (usersWithBook.isEmpty()){
//            throw new ExceptionBlueprint("not available","no",1);
//        }
//        return usersWithBook;
    }
// TODO correct after decision

//    @GetMapping("/books/forBookClub/{id}")
//    public List<RestBook> getBooksForBookClub(@PathVariable(value = "id") Long bookClubId) throws ExceptionBlueprint{
//        List<Book> booksByBookClub = bookclubRepository.findById(bookClubId).get().get;
//        List<RestBook> list = new java.util.ArrayList<>();
//        Optional<Book> book;
//        System.out.println(list.toString());
//        for (BibBook b :
//                bibBooksByBibId) {
//             book = bookRepository.findBookById(b.getBookId());
//            if (book.isPresent()){
//                list.add(new RestBook(book.get()));
//            }
//        }
//        return list;
//        //return new RestBook("lorem Ipsum"+isbn,isbn.toString());
//    }
//
//    @GetMapping("/books/forBibFiltered/{bibId}")
//    public List<RestBook> getBooksForBibFiltered(@PathVariable(value = "bibId") Long bibId, String term) throws ExceptionBlueprint{
//        List<BibBook> bibBooksByBibId = bibBookRepository.findBibBooksByBibId(bibId);
//        List<RestBook> list = new java.util.ArrayList<>();
//        Optional<Book> book;
//        for (BibBook b :
//                bibBooksByBibId) {
//            book = bookRepository.findBookById(b.getBookId());
//            if (book.isPresent() && book.get().getTitel().toLowerCase().contains(term.toLowerCase())){
//                list.add(new RestBook(book.get()));
//            }
//        }
//        return list;
//        //return new RestBook("lorem Ipsum"+isbn,isbn.toString());
//    }

    // Get a Single Note
    @GetMapping("/books/{id}")
    public BookDto getBooks(@PathVariable(value = "id") Long bookId) throws ExceptionBlueprint {
        return new BookDto(bookRepository.findById(bookId)
                .orElseThrow(() -> new ExceptionBlueprint("book not found","no",1)));
    }

//    @GetMapping("/books/byAuthor/{name}")
//    public List<RestBook> getBooksByAuthor(@PathVariable(value = "name") String name) {
//        List<RestBook> books = new java.util.ArrayList<>();
//        for (Book b:
//                bookRepository.findAllByAuthorNameContains(name)) {
//            books.add(new RestBook(b));
//        }
//        return books;
//    }


    @GetMapping("/books/forReadingList")
    //TODO inkonistent falls Buch gelöscht wird (wie werden Bücher gefunden??)
    public List<BookDto> getBooksForList() {
        return userController.getCurrentUser().getReadingList().
                stream().map(BookDto::new).collect(Collectors.toList());
    }

    @GetMapping("/books/forBookClub/{id}")
    public List<BookDto> getBooksForBookClub(@PathVariable(value = "id") Long id) {
        List<Book> books = new java.util.ArrayList<>();
        bookclubRepository.findBookClubById(id).getBookclubMembers().
                forEach(m -> books.addAll(m.getBooks()));
        return bookService.visibilityFilter(books).stream().map(BookDto::new).collect(Collectors.toList());
    }

    @GetMapping("/books/forBookClub/filtered/{id}") // TODO also for Author
    public List<BookDto> getBooksForBookClubFiltered(@PathVariable(value = "id") Long id, String term) {
        List<Book> books = new java.util.ArrayList<>();
        bookclubRepository.findBookClubById(id).getBookclubMembers().
                forEach(m -> m.getBooks().stream().filter(b -> b.getTitel().toLowerCase().contains(term.toLowerCase())).
                        forEach(books::add));
        return bookService.visibilityFilter(books).stream().map(BookDto::new).collect(Collectors.toList());
    }

    @GetMapping("/books/forReadingListFiltered/{term}")
    //TODO inkonistent falls Buch gelöscht wird (wie werden Bücher gefunden??)
    public List<BookDto> getBooksForListFiltered(@PathVariable(value = "term") String term) {
        List<Book> books = userController.getCurrentUser().getReadingList().
                stream().filter(b -> b.getTitel().toLowerCase().contains(term.toLowerCase())).
                collect(Collectors.toList());
        return bookService.visibilityFilter(books).stream().map(BookDto::new).collect(Collectors.toList());
    }

    // Update a Note
    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable(value = "id") Long bookId,
                           @Valid @RequestBody BookDto bookDetails) throws ExceptionBlueprint {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ExceptionBlueprint("book not found","no",1));

        book.setTitel(bookDetails.getTitel());
        book.setIsbn(bookDetails.getIsbn());
//        book.setStatus(bookDetails.getStatus().ordinal());
        book.setAuthorName(bookDetails.getAuthorName());
        book.setErscheinungsDatum(bookDetails.getErscheinungsDatum());
        book.setSprache(bookDetails.getSprache());
        book.setAuflage(bookDetails.getAuflage());

        bookRepository.save(book);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/books/dashStat")
    //TODO inkonistent falls Buch gelöscht wird (wie werden Bücher gefunden??)
    public Integer booksDashStat() {
        return bookRepository.countBooksByUser(userController.getCurrentUser());
    }

//    @GetMapping("/books/searchInApp/{term}")
//    public List<RestBook> searchInApp(@PathVariable(name = "term") String term) {
//        List<RestBook> result = new ArrayList<>();
//        bookRepository.findAllByTitelContains(term).forEach(r -> result.add(new RestBook(r)));
//        return result;
//    }

    @GetMapping("/books/searchOnlineISBN/{isbn}")
    public BookDto searchOnlineByISBN(@PathVariable(name = "isbn") String isbn) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl + isbn, String.class);
        Book book = bookService.extractBookFromResponse(response, userController.getCurrentUser(), isbn);
        return new BookDto(book);
    }

    @GetMapping("/books/searchOnlineTitle/{term}")
    public BookDto searchOnlineByTitle(@PathVariable(name = "term") String term) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "https://www.googleapis.com/books/v1/volumes?q=intitle:";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl + term, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        String title = root.at("/items/0/volumeInfo/title").asText();
        String isbn = root.at("/items/0/volumeInfo/industryIdentifiers/0/identifier").asText();
        String author = root.at("/items/0/volumeInfo/authors/0").asText();
        String erscheinungsDatum = root.at("/items/0/volumeInfo/publishedDate").asText();
        String sprache = root.at("/items/0/volumeInfo/language").asText();
        String pictureUrl = root.at("/items/0/volumeInfo/imageLinks/thumbnail").asText();
        String description = root.at("/items/0/volumeInfo/description").asText();
//        String auflage = root.at("/items/0/volumeInfo/title").asText();
        BookDto bookDto = new BookDto();
        bookDto.setIsbn(isbn);
        bookDto.setPictureUrl(pictureUrl);
        bookDto.setTitel(title);
        bookDto.setAuthorName(author);
        bookDto.setErscheinungsDatum(erscheinungsDatum);
        bookDto.setSprache(sprache);
        System.out.println(bookDto);
        return bookDto;
    }

    // Delete a Note
    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long bookId) throws ExceptionBlueprint {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ExceptionBlueprint("book not found","no",1));
        User currentUser = userController.getCurrentUser();
        // nur löschen falls das Buch in der Userbib ist
        if (currentUser.getBooks().contains(book)){
            List<Book> updatedBooks = currentUser.getBooks();
            updatedBooks.remove(book);
            currentUser.setBooks(updatedBooks);
            userRepository.save(currentUser);
            return ResponseEntity.ok().build();
        } else {
            throw new ExceptionBlueprint("book not in userbib","no",1);
        }
    }


}
