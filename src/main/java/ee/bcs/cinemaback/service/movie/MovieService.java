package ee.bcs.cinemaback.service.movie;

import ee.bcs.cinemaback.infrastructure.exception.DatabaseConstraintException;
import ee.bcs.cinemaback.infrastructure.exception.DatabaseNameConflictException;
import ee.bcs.cinemaback.infrastructure.exception.ResourceNotFoundException;
import ee.bcs.cinemaback.persistence.genre.Genre;
import ee.bcs.cinemaback.persistence.genre.GenreRepository;
import ee.bcs.cinemaback.persistence.movie.Movie;
import ee.bcs.cinemaback.persistence.movie.MovieRepository;
import ee.bcs.cinemaback.persistence.seance.SeanceRepository;
import ee.bcs.cinemaback.service.movie.dto.MovieAdminSummary;
import ee.bcs.cinemaback.service.movie.dto.MovieDto;
import ee.bcs.cinemaback.service.movie.dto.MovieListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static ee.bcs.cinemaback.infrastructure.Error.*;
import static ee.bcs.cinemaback.infrastructure.Status.ACTIVE;
import static ee.bcs.cinemaback.infrastructure.Status.DELETED;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final SeanceRepository seanceRepository;
    private final MovieMapper movieMapper;

    public MovieDto getMovie(Integer movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResourceNotFoundException(MOVIE_NOT_FOUND.getMessage()));
        return movieMapper.toMovieDto(movie);
    }

    public void addNewMovie(MovieDto movieDto) {
        Genre selectedGenre = genreRepository.getReferenceById(movieDto.getGenreId());

        Movie movie = getAndValidateMovie(movieDto);
        if (movie == null) return;

        movie.setStatus(ACTIVE.getLetter());
        movie.setGenre(selectedGenre);
        movieRepository.save(movie);
    }

    public List<MovieAdminSummary> getMovieAdminSummary() {
        List<Movie> movies = movieRepository.findAllMoviesBy(ACTIVE.getLetter());
        List<MovieAdminSummary> movieSummaries = new ArrayList<>();

        for (Movie movie : movies) {
            MovieAdminSummary summary = movieMapper.toAdminSummary(movie);
            summary.setNumberOfSeances(seanceRepository.countByMovie(movie.getId()));
            movieSummaries.add(summary);
        }

        return movieSummaries;
    }

    public List<MovieListDto> getMovieList() {
        List<Movie> movies = movieRepository.findAllMoviesBy(ACTIVE.getLetter());
        List<MovieListDto> movieListDtos = new ArrayList<>();

        for (Movie movie : movies) {
            MovieListDto movieListDto = movieMapper.toMovieListDto(movie);
            movieListDtos.add(movieListDto);
        }

        return movieListDtos;
    }

    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAllActiveMovies();

        return movieMapper.toMovieDtoList(movies);
    }

    public void updateMovie(Integer id, MovieDto movieDto) {
        Movie movie = movieRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(MOVIE_NOT_FOUND.getMessage()));

        if (movieRepository.existsBy(movieDto.getTitle()) && !movie.getTitle().equals(movieDto.getTitle())) {
            throw new DatabaseNameConflictException(MOVIE_EXISTS.getMessage());
        }

        movieMapper.updateMovieFromDto(movieDto, movie);

        movieRepository.save(movie);
    }

    public void deleteMovie(Integer id) {
        Movie movie = movieRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(MOVIE_NOT_FOUND.getMessage()));

        if (seanceRepository.countByMovieAndStatus(id, ACTIVE.getLetter())) {
            throw new DatabaseConstraintException(MOVIE_HAS_SEANCES.getMessage());
        }

        movie.setStatus(DELETED.getLetter());
        movieRepository.save(movie);

    }

    private Movie getAndValidateMovie(MovieDto movieDto) {
        if (movieRepository.deletedByTitle(movieDto.getTitle())) {
            reactivateMovie(movieDto);
            return null;
        }

        if (movieRepository.existsBy(movieDto.getTitle())) {
            throw new DatabaseNameConflictException(MOVIE_EXISTS.getMessage());
        }

        return movieMapper.toMovie(movieDto);
    }

    private void reactivateMovie(MovieDto movieDto) {
        Integer movieId = movieRepository.getIdByTitle(movieDto.getTitle());
        Movie movie = movieMapper.toMovie(movieDto);
        movie.setStatus(ACTIVE.getLetter());
        movie.setId(movieId);
        movieRepository.save(movie);
    }
}
