package cloudcode.v2ourbook.models;

import cloudcode.v2ourbook.enums.GetBookStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
//@Table(name = "GetBookProcess")
public class GetBookProcess {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(cascade=CascadeType.PERSIST)
    User bookReceiver;

    @ManyToOne(cascade=CascadeType.PERSIST)
    User bookGiver;

    @ManyToOne(cascade=CascadeType.PERSIST)
    Book book;

    Date giveDate;
    Date returnDate;
    Date bookReturnedDate;
    GetBookStatus status;
    boolean archived;

    @OneToOne(cascade=CascadeType.PERSIST)
    GetBookRequest getBookRequest;

    public GetBookProcess() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public GetBookRequest getGetBookRequest() {
        return getBookRequest;
    }

    public void setGetBookRequest(GetBookRequest getBookRequest) {
        this.getBookRequest = getBookRequest;
    }

    public Date getGiveDate() {
        return giveDate;
    }

    public void setGiveDate(Date giveDate) {
        this.giveDate = giveDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public GetBookStatus getStatus() {
        return status;
    }

    public void agreed(){
        this.setStatus(GetBookStatus.AGREED);
    }

    public void returned(){
        this.setStatus(GetBookStatus.RETURNED);
    }

    public void received(){
        this.setStatus(GetBookStatus.RECEIVED);
    }

    public void started(){
        this.setStatus(GetBookStatus.STARTED);
    }

    public void cancelled(){
        this.setStatus(GetBookStatus.CANCELLED);
    }

    public void due(){
        this.setStatus(GetBookStatus.DUE);
    }

    public void setStatus(GetBookStatus getBookStatus) {
        this.status = getBookStatus;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public GetBookRequest getBookGetRequest() {
        return getBookRequest;
    }

    public void setBookGetRequest(GetBookRequest getBookRequest) {
        this.getBookRequest = getBookRequest;
    }

    public User getBookReceiver() {
        return bookReceiver;
    }

    public void setBookReceiver(User bookReceiver) {
        this.bookReceiver = bookReceiver;
    }

    public User getBookGiver() {
        return bookGiver;
    }

    public void setBookGiver(User bookGiver) {
        this.bookGiver = bookGiver;
    }

    public Date getBookReturnedDate() {
        return bookReturnedDate;
    }

    public void setBookReturnedDate(Date bookReturnedDate) {
        this.bookReturnedDate = bookReturnedDate;
    }
}

