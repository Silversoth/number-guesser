package numberguesser;

/**
 * Custom exception class that includes an error code along with the message.
 *
 * <p>This class extends the standard Exception class and adds a code attribute
 * to provide additional context about the exception. It provides constructors
 * to initialize the exception with a message, a code, and optionally a cause.</p>
 *
 * <p>Methods are provided to get and set the error code.</p>
 */
public class HandledException extends Exception {
    private String code;

    /**
     * Represents a custom exception with an associated error code.
     *
     * <p>This exception class extends {@code Exception} and includes an additional
     * attribute for an error code, providing more context about the error. It offers
     * constructors to create an exception with a message, an error code, and optionally
     * a cause. Accessor methods are available to retrieve and modify the error code.</p>
     * Constructs a new HandledException with the specified error code and message.
     *
     * @param code    the error code associated with the exception
     * @param message the detail message explaining the exception
     */
    public HandledException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    /**
     * Constructs a new HandledException with the specified error code and message.
     *
     * @param code    the error code associated with the exception
     * @param message the detail message explaining the exception
     */
    public HandledException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    /**
     * Retrieves the error code associated with this exception.
     *
     * @return the error code as a String
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the error code for this exception.
     *
     * @param code the error code to be associated with this exception
     */
    public void setCode(String code) {
        this.code = code;
    }
}
