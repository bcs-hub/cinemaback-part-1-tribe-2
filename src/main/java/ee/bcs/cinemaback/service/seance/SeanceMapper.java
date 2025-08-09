package ee.bcs.cinemaback.service.seance;

import ee.bcs.cinemaback.persistence.seance.Seance;
import ee.bcs.cinemaback.service.seance.dto.SeanceAdminDto;
import ee.bcs.cinemaback.service.seance.dto.SeanceAdminSummary;
import ee.bcs.cinemaback.service.seance.dto.SeanceScheduleDto;
import ee.bcs.cinemaback.util.ImageUtil;
import org.mapstruct.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeanceMapper {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    ZoneId TIME_ZONE = ZoneId.of("Europe/Tallinn");

    @Mapping(source = "movieId", target = "movie.id")
    @Mapping(source = "roomId", target = "room.id")
    @Mapping(source = "dateTime", target = "startTime", qualifiedByName = "dateTimeToInstant")
    Seance toSeance(SeanceAdminDto seanceAdminDto);

    @Mapping(source = "startTime", target = "dateTime", qualifiedByName = "instantToDateTime")
    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "room.id", target = "roomId")
    SeanceAdminDto toAdminDto(Seance seance);

    @Mapping(source = "startTime", target = "dateTime", qualifiedByName = "instantToDateTime")
    @Mapping(source = "movie.posterImage", target = "moviePosterImage", qualifiedByName = "byteArrayToImageString")
    @Mapping(source = "movie.title", target = "movieTitle")
    @Mapping(source = "movie.runtime", target = "movieRuntime")
    @Mapping(source = "movie.genre.name", target = "movieGenreName")
    @Mapping(source = "room.name", target = "roomName")
    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "movie.youtubeLink", target = "movieYoutubeLink")
    @Mapping(source = "id", target = "seanceId")
    SeanceScheduleDto toScheduleDto(Seance seance);

    List<SeanceScheduleDto> toScheduleDtoList(List<Seance> seances);

    @Mapping(source = "startTime", target = "dateTime", qualifiedByName = "instantToDateTime")
    @Mapping(source = "movie.title", target = "movieTitle")
    @Mapping(source = "room.name", target = "roomName")
    @Mapping(source = "id", target = "id")
    SeanceAdminSummary toAdminSummary(Seance seance);

    @Mapping(ignore = true, target = "room.id")
    @Mapping(ignore = true, target = "movie.id")
    @Mapping(source = "language", target = "language")
    @Mapping(source = "subtitles", target = "subtitles")
    @Mapping(source = "dateTime", target = "startTime", qualifiedByName = "dateTimeToInstant")
    void updateSeanceFromDto(SeanceAdminDto seanceAdminDto, @MappingTarget Seance seance);

    @Named("dateTimeToInstant")
    static Instant dateTimeToInstant(String dateTime) {
        return dateTime == null ? null : LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER)
                .atZone(TIME_ZONE)
                .toInstant();
    }

    @Named("instantToDateTime")
    static String instantToDateTime(Instant instant) {
        return instant == null ? null : instant.atZone(TIME_ZONE).format(DATE_TIME_FORMATTER);
    }

    @Named("byteArrayToImageString")
    static String byteArrayToImageString(byte[] imageByteArray) {
        return imageByteArray == null ? null : ImageUtil.byteArrayToBase64ImageData(imageByteArray);
    }
}
