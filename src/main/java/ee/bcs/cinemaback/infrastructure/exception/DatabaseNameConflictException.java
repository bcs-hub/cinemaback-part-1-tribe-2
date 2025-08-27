package ee.bcs.cinemaback.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DatabaseNameConflictException extends RuntimeException {

    public DatabaseNameConflictException(String errorMessage) {
        super(errorMessage);
    }

}
