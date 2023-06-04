package razepl.dev.github.exceptions;

public class UserDoesNotExistException extends IllegalArgumentException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
