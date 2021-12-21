package cloudcode.v2ourbook.dto;

import cloudcode.v2ourbook.models.BookClub;
import cloudcode.v2ourbook.models.User;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookClubDto {

    Long id;

    String name;

    Long bookclubOwner;

    List<Long> bookclubMembers;

    public BookClubDto(BookClub bookClub) {
        this.id = bookClub.getId();
        this.name = bookClub.getName();
        this.bookclubOwner = bookClub.getBookclubOwner().getId();
        this.bookclubMembers = new ArrayList<>();
        setBookclubMembers(bookClub.getBookclubMembers().stream().map(User::getId).collect(Collectors.toList()));
    }

    public BookClubDto() {
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
}
