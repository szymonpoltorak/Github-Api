package razepl.dev.github.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import razepl.dev.github.data.ExceptionResponse;
import razepl.dev.github.exceptions.UserDoesNotExistException;

@Slf4j
@ControllerAdvice
public class GithubExceptionHandler {
    @ExceptionHandler(UserDoesNotExistException.class)
    public final ResponseEntity<ExceptionResponse> handleUserDoesNotExistException(UserDoesNotExistException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());

        log.error("User does not exist: {}", ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
