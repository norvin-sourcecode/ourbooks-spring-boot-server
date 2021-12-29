package cloudcode.v2ourbook.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class BookClub {

    @Id
    @GeneratedValue
    Long id;

    String name;

    @OneToOne(cascade=CascadeType.PERSIST)
    User bookclubOwner;

    @ManyToMany(cascade=CascadeType.PERSIST)

    Set<User> bookclubMembers = new HashSet<>();

    @OneToMany
    Set<User> admins = new HashSet<>();;

    public BookClub() {
        super();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getBookclubMembers() {
        return bookclubMembers;
    }

    public void setBookclubMembers(Set<User> bookclubMembers) {
        this.bookclubMembers = bookclubMembers;
    }

    public User getBookclubOwner() {
        return bookclubOwner;
    }

    public void setBookclubOwner(User bookclubOwner) {
        this.bookclubOwner = bookclubOwner;
    }

    public Set<User> getAdmins() {
        return admins;
    }

    public void addAdmin(User newAdmin) {
        this.admins.add(newAdmin);
    }

    public void removeAdmin(User admin) {
        this.admins.remove(admin);
    }

    @Override
    public String toString() {
        return "BookClub{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bookclubOwner=" + bookclubOwner +
                ", bookclubMembers=" + bookclubMembers +
                '}';
    }
}

