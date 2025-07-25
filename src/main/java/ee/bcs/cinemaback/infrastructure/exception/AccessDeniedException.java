package ee.bcs.cinemaback.infrastructure.exception;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException(String errorMessage) {
        super(errorMessage);
    }
}
