package razepl.dev.github.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import razepl.dev.github.api.interfaces.GithubExceptionHandlerInterface;
import razepl.dev.github.data.ExceptionResponse;
import razepl.dev.github.exceptions.UserDoesNotExistException;
import razepl.dev.github.exceptions.XmlHeaderException;

@Slf4j
@ControllerAdvice
public class GithubExceptionHandler implements GithubExceptionHandlerInterface {
    @Override
    @ExceptionHandler(UserDoesNotExistException.class)
    public final ResponseEntity<ExceptionResponse> handleUserDoesNotExistException(UserDoesNotExistException exception) {
        var exceptionResponse = ExceptionResponse
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .Message(exception.getMessage())
                .build();
        log.error("User does not exist: {}", exception.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    @ExceptionHandler(XmlHeaderException.class)
    public final ResponseEntity<ExceptionResponse> handleXmlHeaderException(XmlHeaderException exception) {
        var exceptionResponse = ExceptionResponse
                .builder()
                .status(HttpStatus.NOT_ACCEPTABLE.value())
                .Message(exception.getMessage())
                .build();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        log.error("Xml header exception: {}", exception.getMessage());

        return new ResponseEntity<>(exceptionResponse, headers, HttpStatus.NOT_ACCEPTABLE);
    }
}
