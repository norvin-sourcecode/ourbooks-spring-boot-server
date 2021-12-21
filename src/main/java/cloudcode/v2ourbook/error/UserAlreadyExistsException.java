package cloudcode.v2ourbook.error;

/**
 *
 * @author norvin_klinkmann
 */

public class UserAlreadyExistsException extends Exception {
    private long userId;
    public UserAlreadyExistsException(long userId) {
        super(String.format("Bib is not found with id : '%d'", userId));
    }


    public UserAlreadyExistsException(String message) {
        super(message);
    }
}