package cloudcode.v2ourbook.models;

import cloudcode.v2ourbook.enums.RequestResult;

import javax.persistence.*;
import java.util.Date;

@Entity
public class GetBookRequest {
    @Id
    @GeneratedValue
    Long id;
    String message;
    @ManyToOne
    User receiver;
    @ManyToOne
    User giver;
    @OneToOne
    Book book;
    @Basic
    @Temporal(TemporalType.DATE)
    Date timeCreated;
    RequestResult requestResult;


    public GetBookRequest() {
        super();
        timeCreated = new Date();
        requestResult = RequestResult.PENDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getGiver() {
        return giver;
    }

    public void setGiver(User giver) {
        this.giver = giver;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public RequestResult getGetBookRequestResult() {
        return requestResult;
    }

    public void setGetBookRequestResult(RequestResult requestResult) {
        this.requestResult = requestResult;
    }
}
