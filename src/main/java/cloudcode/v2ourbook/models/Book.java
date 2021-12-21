package cloudcode.v2ourbook.models;

import cloudcode.v2ourbook.enums.BookStatus;
import cloudcode.v2ourbook.enums.VisibilityType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author norvin_klinkmann
 */

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue
    Long id;

    String titel;
    String isbn;
    BookStatus status;
    String authorName;
    String erscheinungsDatum;
    String sprache;
    String auflage;
    String pictureUrl;
    Double ratio;

    @Lob
    @Column
    String description;

    VisibilityType visibilityType;

    @OneToOne(cascade=CascadeType.ALL)
    Licence licence;

    @OneToOne(cascade=CascadeType.PERSIST)
    User userLocation;

    @Basic
    @Temporal(TemporalType.DATE)
    Date timeCreated;

    @OneToMany(cascade=CascadeType.PERSIST)
    List<BookClub> bookClubCircle;

    @OneToOne(cascade=CascadeType.PERSIST)
    User user;

    public Book(){
        super();
        this.timeCreated = new Date();
        this.setVisibilityType(VisibilityType.USER);
        this.setLicence(new Licence());
        this.bookClubCircle = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String book_titel) {
        this.titel = book_titel;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String book_isbn) {
        this.isbn = book_isbn;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String book_author_name) {
        this.authorName = book_author_name;
    }

    public String getErscheinungsDatum() {
        return erscheinungsDatum;
    }

    public void setErscheinungsDatum(String book_erscheinungsdatum) {
        this.erscheinungsDatum = book_erscheinungsdatum;
    }

    public String getSprache() {
        return sprache;
    }

    public void setSprache(String book_sprache) {
        this.sprache = book_sprache;
    }

    public String getAuflage() {
        return auflage;
    }

    public void setAuflage(String book_auflage) {
        this.auflage = book_auflage;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public User getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(User userLocation) {
        this.userLocation = userLocation;
    }

    public List<BookClub> getBookClubCircle() {
        return bookClubCircle;
    }

    public void setBookClubCircle(List<BookClub> bookClubCircle) {
        this.bookClubCircle = bookClubCircle;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public VisibilityType getVisibilityType() {
        return visibilityType;
    }

    public void setVisibilityType(VisibilityType visibilityType) {
        this.visibilityType = visibilityType;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    @Override
    public String toString() {
        return String.format(
                "Book[id=%d, book_titel='%s', book_isbn='%s', book_status='%d', book_author_name='%s', book_erscheinungsdatum='%s', book_sprache='%s', book_auflage='%s']",
                id, titel, isbn, status, authorName, erscheinungsDatum, sprache, auflage);
    }
}
