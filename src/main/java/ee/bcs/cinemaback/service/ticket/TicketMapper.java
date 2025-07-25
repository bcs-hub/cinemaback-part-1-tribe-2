package ee.bcs.cinemaback.service.ticket;

import ee.bcs.cinemaback.persistence.ticket.Ticket;
import org.mapstruct.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketMapper {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    ZoneId TIME_ZONE = ZoneId.of("Europe/Tallinn");

    @Mapping(source = "ticketType.name", target = "ticketTypeName")
    @Mapping(source = "seance.startTime", target = "seanceStartTime", qualifiedByName = "instantToDateTime")
    @Mapping(source = "seance.movie.title", target = "seanceMovieTitle")
    @Mapping(source = "seance.room.name", target = "seanceRoomName")
    @Mapping(source = "seat.row", target = "seatRow")
    @Mapping(source = "seat.col", target = "seatCol")
    TicketDto toTicketDto(Ticket ticket);

    @Named("instantToDateTime")
    static String instantToDateTime(Instant instant) {
        return instant == null ? null : instant.atZone(TIME_ZONE).format(DATE_TIME_FORMATTER);
    }

    List<TicketDto> toTicketDtoList(List<Ticket> tickets);
}
