package razepl.dev.github.api.interfaces;

import org.springframework.http.ResponseEntity;
import razepl.dev.github.data.ExceptionResponse;
import razepl.dev.github.exceptions.UserDoesNotExistException;
import razepl.dev.github.exceptions.XmlHeaderException;

public interface GithubExceptionHandlerInterface {
    ResponseEntity<ExceptionResponse> handleUserDoesNotExistException(UserDoesNotExistException exception);

    ResponseEntity<ExceptionResponse> handleXmlHeaderException(XmlHeaderException exception);
}
