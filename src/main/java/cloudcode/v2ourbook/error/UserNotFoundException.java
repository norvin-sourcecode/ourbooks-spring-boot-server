package cloudcode.v2ourbook.error;

/**
 *
 * @author norvin_klinkmann
 */

public class UserNotFoundException extends Exception {
    private long book_id;
    public UserNotFoundException(long book_id) {
        super(String.format("User is not found with id : '%s'", book_id));
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
