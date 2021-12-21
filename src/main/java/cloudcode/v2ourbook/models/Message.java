package cloudcode.v2ourbook.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Message {
    String message_id;
    String text;
    Date createdAt;
    FireUser user;

    public Message() {
        this.createdAt = new Date();
        this.user = new FireUser();
        this.message_id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Message{" +
                ", message_id='" + message_id + '\'' +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }
}
