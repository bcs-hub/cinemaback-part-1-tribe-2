package ee.bcs.cinemaback.infrastructure.exception;

public class DatabaseConstraintException extends RuntimeException {
    public DatabaseConstraintException(String errorMessage) {
        super(errorMessage);
    }
}

