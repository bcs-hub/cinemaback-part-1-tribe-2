package ee.bcs.cinemaback.infrastructure.exception;

public class DatabaseNameConflictException extends RuntimeException {
    public DatabaseNameConflictException(String errorMessage) {
        super(errorMessage);
    }
}
