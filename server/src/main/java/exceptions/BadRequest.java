package exceptions;

/**
 * Indicates there was an error connecting to the database
 */
public class BadRequest extends Exception{
    public BadRequest(String message) {
        super(message);
    }
}
