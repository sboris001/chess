package exceptions;

/**
 * Indicates there was an error connecting to the database
 */
public class Unauthorized extends Exception{
    public Unauthorized(String message) {
        super(message);
    }
}
