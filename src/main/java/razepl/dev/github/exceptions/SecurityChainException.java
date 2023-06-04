package razepl.dev.github.exceptions;

/**
 * Exception featuring issues with Security Filter Chain errors.
 */
public class SecurityChainException extends SecurityException {
    public SecurityChainException(String message) {
        super(message);
    }
}
