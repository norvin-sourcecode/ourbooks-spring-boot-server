package cloudcode.v2ourbook.dto;

import cloudcode.v2ourbook.models.Book;
import cloudcode.v2ourbook.models.BookClub;
import cloudcode.v2ourbook.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookShelfBookClubDto {

    Long id;

    String name;

    Long bookclubOwner;

    List<Long> bookclubMembers;

    List<BookDto> booksList;

    public BookShelfBookClubDto(BookClub bookClub, List<Book> books) {
        this.booksList = books.stream().map(BookDto::new).collect(Collectors.toList());
        this.id = bookClub.getId();
        this.name = bookClub.getName();
        this.bookclubOwner = bookClub.getBookclubOwner().getId();
        this.bookclubMembers = new ArrayList<>();
        setBookclubMembers(bookClub.getBookclubMembers().stream().map(User::getId).collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBookclubOwner() {
        return bookclubOwner;
    }

    public void setBookclubOwner(Long bookclubOwner) {
        this.bookclubOwner = bookclubOwner;
    }

    public List<Long> getBookclubMembers() {
        return bookclubMembers;
    }

    public void setBookclubMembers(List<Long> bookclubMembers) {
        this.bookclubMembers = bookclubMembers;
    }

    public List<BookDto> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<BookDto> booksList) {
        this.booksList = booksList;
    }

    @Override
    public String toString() {
        return "RestBookShelfBookClub{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bookclubOwner=" + bookclubOwner +
                ", bookclubMembers=" + bookclubMembers +
                ", booksList=" + booksList +
                '}';
    }
}
