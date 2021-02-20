package ee.backoffice.assignment.exception;

/**
 * @author Sander Kadajane
 * @since 01.02.2019
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
