package cloudcode.v2ourbook.error;

/**
 *
 * @author norvin_klinkmann
 */

public class BibNotFoundException extends Exception {
    private long book_id;
    public BibNotFoundException(long book_id) {
        super(String.format("Bib is not found with id : '%s'", book_id));
    }
}
