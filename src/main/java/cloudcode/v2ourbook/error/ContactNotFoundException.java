package cloudcode.v2ourbook.error;

/**
 *
 * @author norvin_klinkmann
 */

public class ContactNotFoundException extends Exception {
    private long book_id;
    public ContactNotFoundException(long book_id) {
        super(String.format("Book is not found with id : '%s'", book_id));
    }
}

