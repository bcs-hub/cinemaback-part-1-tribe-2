package ee.bcs.cinemaback.service.ticket.type;

import ee.bcs.cinemaback.persistence.ticket.type.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketTypeMapper {
    TicketType toEntity(TicketTypeDto ticketTypeDto);

    TicketTypeDto toDto(TicketType ticketType);

    List<TicketTypeDto> toDto(List<TicketType> ticketTypes);

}
