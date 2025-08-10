package ee.bcs.cinemaback.service.movie;

import ee.bcs.cinemaback.persistence.movie.Movie;
import ee.bcs.cinemaback.service.movie.dto.MovieAdminSummary;
import ee.bcs.cinemaback.service.movie.dto.MovieDto;
import ee.bcs.cinemaback.service.movie.dto.MovieListDto;
import ee.bcs.cinemaback.util.ImageUtil;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovieMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "genreId", target = "genre.id") //added
    @Mapping(source = "runtime", target = "runtime")
    @Mapping(source = "director", target = "director")
    @Mapping(source = "youtubeLink", target = "youtubeLink")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "posterImage", target = "posterImage", qualifiedByName = "imageStringToByteArray")
    Movie toMovie(MovieDto movieDto);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "genre.name", target = "genreName")
    @Mapping(source = "genre.id", target = "genreId") // add this to provide the genre id number, otherwise it will show null
    @Mapping(source = "youtubeLink", target = "youtubeLink")
    @Mapping(source = "runtime", target = "runtime")
    @Mapping(source = "director", target = "director")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "posterImage", target = "posterImage", qualifiedByName = "byteArrayToImageString")
    MovieDto toMovieDto(Movie movie);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    MovieListDto toMovieListDto(Movie movie);

    @Named("imageStringToByteArray")
    static byte[] imageStringToByteArray(String imageString) {
        return imageString == null ? null : ImageUtil.base64ImageDataToByteArray(imageString);
    }

    @Named("byteArrayToImageString")
    static String byteArrayToImageString(byte[] imageByteArray) {
        return imageByteArray == null ? null : ImageUtil.byteArrayToBase64ImageData(imageByteArray);
    }

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "genre.name", target = "genreName")
    MovieAdminSummary toAdminSummary(Movie movie);

    @Mapping(source = "posterImage", target = "posterImage", qualifiedByName = "imageStringToByteArray")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "runtime", target = "runtime")
    @Mapping(source = "director", target = "director")
    @Mapping(source = "youtubeLink", target = "youtubeLink")
    @Mapping(ignore = true, target = "genre.id")
    @Mapping(source = "description", target = "description")
    void updateMovieFromDto(MovieDto movieDto, @MappingTarget Movie movie);

    List<MovieDto> toMovieDtoList(List<Movie> movies);
}
