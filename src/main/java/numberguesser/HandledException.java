package numberguesser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * The `HandledException` class represents a custom exception with an associated error code.
 *
 * <p>This class extends the standard `Exception` class and adds an additional attribute for an error code,
 * providing more context about the error. It includes constructors to create an exception with a message,
 * an error code, and optionally a cause. Accessor methods are available to retrieve and modify the error code.</p>
 *
 * <p>Additionally, this class provides a utility method to redirect the standard error stream (`System.err`)
 * to a file, allowing for error logging to a specified file path.</p>
 */
public class HandledException extends Exception {
    private String code;

    /**
     * Constructs a new `HandledException` with the specified error code and message.
     *
     * @param code    the error code associated with the exception
     * @param message the detail message explaining the exception
     */
    public HandledException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    /**
     * Constructs a new `HandledException` with the specified error code, message, and cause.
     *
     * @param code    the error code associated with the exception
     * @param message the detail message explaining the exception
     * @param cause   the cause of the exception
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

    /**
     * Redirects the standard error stream (`System.err`) to a file named "error.txt".
     *
     * <p>If the file does not exist, it will be created. This method appends error messages
     * to the file, allowing for persistent error logging.</p>
     */
    public static void redirectErrorStream() {
        Path errorFilePath = Path.of("error.txt");
        try {
            // Create the file if it doesn't exist
            if (Files.notExists(errorFilePath)) {
                Files.createFile(errorFilePath);
            }

            // Redirect System.err to the file
            FileOutputStream fos = new FileOutputStream(errorFilePath.toFile(), true); // Append mode
            PrintStream ps = new PrintStream(fos);
            System.setErr(ps);

        } catch (IOException e) {
            // Handle any IOExceptions that occur during file operations
            System.err.println("Failed to redirect error stream: " + e.getMessage());
        }
    }
}
