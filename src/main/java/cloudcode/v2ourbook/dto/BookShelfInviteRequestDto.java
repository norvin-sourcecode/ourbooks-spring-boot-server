package cloudcode.v2ourbook.dto;

import cloudcode.v2ourbook.models.BookShelfInviteRequest;

public class BookShelfInviteRequestDto {

    Long id;

    String message;

    Long receiver;

    Long sender;

    Long bookClub;

    public BookShelfInviteRequestDto(BookShelfInviteRequest request) {
        this.id = request.getId();
        this.receiver = request.getReceiver().getId();
        this.sender = request.getSender().getId();
        this.message = request.getMessage();
        this.bookClub = request.getBookClub().getId();
    }

    public Long getBookClub() {
        return bookClub;
    }

    public void setBookClub(Long bookClub) {
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

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }
}
