package cloudcode.v2ourbook.dto;

import cloudcode.v2ourbook.models.FriendRequest;

import javax.persistence.*;
import java.util.Date;

public class FriendRequestDto {

    Long id;

    String message;

    Long receiver;

    Long sender;

    public FriendRequestDto(FriendRequest request) {
        this.id = request.getId();
        this.receiver = request.getReceiver().getId();
        this.sender = request.getSender().getId();
        this.message = request.getSender().getUsername() + " send you a friend request";
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
