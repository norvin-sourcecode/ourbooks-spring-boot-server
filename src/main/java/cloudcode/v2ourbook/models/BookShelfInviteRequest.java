package cloudcode.v2ourbook.models;

import cloudcode.v2ourbook.enums.RequestResult;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BookShelfInviteRequest {

    @Id
    @GeneratedValue
    Long id;

    String message;
    @ManyToOne
    User receiver;
    @ManyToOne
    User sender;

    @Basic
    @Temporal(TemporalType.DATE)
    Date timeCreated;
    RequestResult requestResult;

    @OneToOne
    BookClub bookClub;

    public BookShelfInviteRequest() {
        super();
        setTimeCreated(new Date());
    }

    public BookClub getBookClub() {
        return bookClub;
    }

    public void setBookClub(BookClub bookClub) {
        this.bookClub = bookClub;
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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public RequestResult getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(RequestResult requestResult) {
        this.requestResult = requestResult;
    }
}
