package exceptions;

/**
 * Indicates there was an error connecting to the database
 */
public class AlreadyTaken extends Exception{
    public AlreadyTaken(String message) {
        super(message);
    }
}
