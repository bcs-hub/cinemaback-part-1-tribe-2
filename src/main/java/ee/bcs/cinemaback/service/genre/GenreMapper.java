package ee.bcs.cinemaback.service.genre;

import ee.bcs.cinemaback.persistence.genre.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreMapper {


    GenreDto toDto(Genre genre);
    List<GenreDto> toDto(List<Genre> genre);

}
