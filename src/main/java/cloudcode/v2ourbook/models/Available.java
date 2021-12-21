package cloudcode.v2ourbook.models;

public class Available {

    String username;

    Long bookId;

    public Available() {
    }

    public Available(String username, Long bookId) {
        this.username = username;
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
