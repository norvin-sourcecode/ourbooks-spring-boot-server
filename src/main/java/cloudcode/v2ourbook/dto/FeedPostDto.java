package cloudcode.v2ourbook.dto;

import cloudcode.v2ourbook.enums.PostType;
import cloudcode.v2ourbook.models.Book;
import cloudcode.v2ourbook.services.FeedService;

public class FeedPostDto {

    BookDto book;
    UserDto user;
    String message;
    Long timeCreated;
    PostType postType;


    public FeedPostDto(Book book, PostType postType) {
        FeedService feedService = new FeedService();
        this.setTimeCreated(book.getTimeCreated().getTime());
        this.setUser(new UserDto(book.getUser()));
        this.setBook(new BookDto(book));
        this.setPostType(postType);
        this.setMessage(feedService.generateMessage(book, postType));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }
}
