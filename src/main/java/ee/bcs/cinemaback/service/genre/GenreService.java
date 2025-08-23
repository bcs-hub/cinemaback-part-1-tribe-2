package ee.bcs.cinemaback.service.genre;

import ee.bcs.cinemaback.infrastructure.exception.DatabaseConstraintException;
import ee.bcs.cinemaback.infrastructure.exception.DatabaseNameConflictException;
import ee.bcs.cinemaback.infrastructure.exception.ResourceNotFoundException;
import ee.bcs.cinemaback.persistence.genre.Genre;
import ee.bcs.cinemaback.persistence.genre.GenreRepository;
import ee.bcs.cinemaback.persistence.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static ee.bcs.cinemaback.infrastructure.Error.*;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final GenreMapper genreMapper;


    public List<GenreDto> getAllGenres() {
        return genreMapper.toDto(genreRepository.findAllAlphabetic());
    }

    public void addGenre(String genreName) {

        String name = validateGenre(genreName);
        Genre genre = new Genre();
        genre.setName(name);
        try {
            genreRepository.save(genre);
        } catch (DataIntegrityViolationException e) {

            throw new DatabaseNameConflictException("Genre already exists");
        }
    }


    public void deleteGenreBy(Integer id) {

        Genre genre = genreRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(RESOURCE_NOT_FOUND.getMessage()));

        if (movieRepository.existsByGenre(id)) {
            throw new DatabaseConstraintException(GENRE_HAS_MOVIES.getMessage());
        }

        genreRepository.delete(genre);
    }

    public void updateGenreName(Integer id, String newName) {
        Genre genre = genreRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Genre ID not found"));

        if (Objects.equals(genre.getName(), newName)) {
            return;
        }

        validateGenre(newName);
        genre.setName(newName);
        genreRepository.save(genre);
    }

    public String getGenreName(Integer id) {
        Genre genre = genreRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Genre ID not found"));
        return genre.getName();
    }

    String validateGenre(String genreName) {
        if (genreName == null) {
            throw new DatabaseConstraintException(DATABASE_NAME_MUST_NOT_BE_EMPTY.getMessage());
        }
        String name = genreName.strip();
        if (name.isEmpty()) {
            throw new DatabaseConstraintException(DATABASE_NAME_MUST_NOT_BE_EMPTY.getMessage());
        }
        if (genreRepository.existsByName(name)) {
            throw new DatabaseNameConflictException("Genre already exists");
        }
        return name;
    }
}
