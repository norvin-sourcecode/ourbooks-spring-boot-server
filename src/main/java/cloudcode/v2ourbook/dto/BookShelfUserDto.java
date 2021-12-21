package cloudcode.v2ourbook.dto;

import cloudcode.v2ourbook.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class BookShelfUserDto {

    Long id;

    String username;
    String firstname;
    String lastname;
    String email;
    boolean active;
    List<BookDto> booksList;

    public BookShelfUserDto(User user) {
        this.booksList = user.getBooks().stream().map(BookDto::new).collect(Collectors.toList());
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.active = user.isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user_username) {
        this.username = user_username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String user_vorname) {
        this.firstname = user_vorname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String user_nachname) {
        this.lastname = user_nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String user_email) {
        this.email = user_email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<BookDto> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<BookDto> booksList) {
        this.booksList = booksList;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, username='%s', password='%s', vorname='%s', nachname='%s', email='%s']",
                id, username, firstname, lastname, email);
    }

}
