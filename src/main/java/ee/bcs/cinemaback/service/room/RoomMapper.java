package ee.bcs.cinemaback.service.room;

import ee.bcs.cinemaback.persistence.room.Room;
import ee.bcs.cinemaback.service.room.dto.RoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {

    @Mapping(ignore = true, target = "id")
    Room toRoom(RoomDto roomDto);

    RoomDto toRoomDto(Room room);

    List<RoomDto> toRoomDtos(List<Room> rooms);

}
