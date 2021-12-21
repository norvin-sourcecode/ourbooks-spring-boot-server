package cloudcode.v2ourbook.models;

import javassist.bytecode.ByteArray;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author norvin_klinkmann
 */

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    Long id;

    @ManyToMany(cascade=CascadeType.PERSIST)
    Set<User> friends = new HashSet<>();

    @OneToMany(cascade=CascadeType.ALL)
    Set<GetBookProcess> receiveProcesses = new HashSet<>();;

    @OneToMany(cascade=CascadeType.ALL)
    Set<GetBookProcess> giveProcesses = new HashSet<>();;

    //(mappedBy = "bookclubMembers")
    @ManyToMany(cascade=CascadeType.PERSIST)
    Set<BookClub> bookclubMemberships = new HashSet<>();

    @ManyToMany(cascade=CascadeType.PERSIST)
    Set<Book> readingList = new HashSet<>();

    String username;
    String password;
    String firstname = "";
    String lastname = "";
    @Email
    String email;
    boolean active;
    String passwordResetToken;
    java.util.Date passwordResetTokenDate;

    @OneToMany(cascade=CascadeType.ALL)
    List<Book> books;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Authority> authorities = new HashSet<>();;

    @Lob
    @Column
    String token;

    public User(){
        super();
    }

    public User(Long id, String username, String password, String firstname, String lastname, String email) {
        super();
        this.books = new ArrayList<>();
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Set<BookClub> getBookclubMemberships() {
        return bookclubMemberships;
    }

    public void setBookclubMemberships(Set<BookClub> bookclubMemberships) {
        this.bookclubMemberships = bookclubMemberships;
    }

    public void addBookclub(BookClub club) {
        this.bookclubMemberships.add(club);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String user_password) {
        this.password = user_password;
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

    public Set<GetBookProcess> getReceiveProcesses() {
        return receiveProcesses;
    }

    public void setReceiveProcesses(Set<GetBookProcess> receiveProcesses) {
        this.receiveProcesses = receiveProcesses;
    }

    public Set<GetBookProcess> getGiveProcesses() {
        return giveProcesses;
    }

    public void setGiveProcesses(Set<GetBookProcess> giveProcesses) {
        this.giveProcesses = giveProcesses;
    }

    public Set<Book> getReadingList() {
        return readingList;
    }

    public void setReadingList(Set<Book> readingList) {
        this.readingList = readingList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public Date getPasswordResetTokenDate() {
        return passwordResetTokenDate;
    }

    public void setPasswordResetTokenDate(Date passwordResetTokenDate) {
        this.passwordResetTokenDate = passwordResetTokenDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book){
        this.books.add(book);
        setBooks(this.books);
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", friends=" + friends +
                ", receiveProcesses=" + receiveProcesses +
                ", giveProcesses=" + giveProcesses +
                ", bookclubMemberships=" + bookclubMemberships +
                ", readingList=" + readingList +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", passwordResetToken='" + passwordResetToken + '\'' +
                ", passwordResetTokenDate=" + passwordResetTokenDate +
                ", authorities=" + authorities +
                ", token='" + token + '\'' +
                '}';
    }

    public boolean isValidUsername(String name)
    {

        // Regex to check valid username.
        String regex = "^(?=[a-zA-Z0-9._]{4,12}$)(?!.*[_.]{2})[^_.].*[^_.]$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the username is empty
        // return false
        if (name == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given username
        // and regular expression.
        Matcher m = p.matcher(name);

        // Return if the username
        // matched the ReGex
        //
        return m.matches();
//        return true;
    }

    public boolean isValidPassword(String password)
    {

        // Regex to check valid password.
        String regex = "^[0-9A-Za-z]\\w{8,29}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given username
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the username
        // matched the ReGex
//        return m.matches();
        return true;
    }
}

