package exceptions;

/**
 * Custom exception class to handle driver creation errors in Selenium.
 */
public class DriverCreationException extends Exception {

    // Default constructor
    public DriverCreationException() {
        super("Driver creation failed");
    }

    // Constructor that accepts a custom message
    public DriverCreationException(String message) {
        super(message);
    }

    // Constructor that accepts a custom message and a throwable cause
    public DriverCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a throwable cause
    public DriverCreationException(Throwable cause) {
        super(cause);
    }
}
