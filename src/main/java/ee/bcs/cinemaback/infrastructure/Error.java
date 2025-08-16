package ee.bcs.cinemaback.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum Error {

    ACCESS_DENIED("Access denied"),
    DATABASE_CONSTRAINT("Database integrity violation"),
    DATABASE_NAME_CONFLICT("Name already exists"),
    DATABASE_NAME_MUST_NOT_BE_EMPTY("Name must not be empty"),

    GENRE_EXISTS("Genre already exists"),
    GENRE_HAS_MOVIES("Genre is associated with movies"),
    GENRE_NOT_FOUND("Genre not found"),

    INVALID_CREDENTIALS("Invalid username or password"),

    MOVIE_EXISTS("Movie already exists"),
    MOVIE_NOT_FOUND("Movie not found"),
    MOVIE_HAS_SEANCES("Movie is associated with seances"),

    RESOURCE_NOT_FOUND("Resource not found"),

    ROOM_CANNOT_BE_DELETED("Room cannot be deleted because it has seances"),
    ROOM_EXISTS("Room already exists"),
    ROOM_NOT_FOUND("Room not found"),
    ROOM_SEATS_CANNOT_BE_EDITED("Room seats cannot be edited because it has seances"),

    SEANCE_EXISTS("Seance already exists"),
    SEANCE_TIME_OVERLAP("Seance in the same room at this time already exists"),
    SEANCE_HAS_ACTIVE_TICKETS("Seance has active tickets"),
    SEANCE_NOT_FOUND("Seance not found"),
    SEAT_EXISTS("Seat already exists"),
    SEAT_NOT_FOUND("Seat not found"),

    TICKET_EXISTS("Ticket already exists"),
    TICKET_NOT_FOUND("Ticket not found"),
    TICKET_TYPE_EXISTS("Ticket type already exists"),
    TICKET_TYPE_NOT_FOUND("Ticket type not found"),
    TICKETS_EXIST_WITH_THIS_TICKET_TYPE("Tickets have already been sold with this ticket type"),

    USER_EXISTS("User already exists"),
    USER_EMAIL_EXISTS("Email is already in use"),
    USER_NOT_FOUND("User not found");

    private final String message;


}
