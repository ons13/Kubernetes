package com.moviehub.MovieHub.Servies;

import com.moviehub.MovieHub.Models.Movie;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MovieService {

    List<Movie> getAllmovie();

    Optional<Movie> getmovieById(Long movieId);

    Movie savemovie(Movie movie);

    void deletemovie(Long movieId);

    Double getAverageRatingBymovieId(Long movieId);

    void increaseOpenedByOne(Long movieId);

    void addToFavorites(Long movieId, Long userId);

    void removeFromFavorites(Long movieId, Long userId);

    Set<String> getAllGenres();

    List<Movie> getmovieByGenre(String genders);

    Movie updatemovieById(Long movieId, Movie updatedmovie);

    @Query("SELECT a.genders, COUNT(a) FROM movie a GROUP BY a.genders")
    List<Object[]> countmovieByGender();

    List<Object[]> countmovieByGenders();

    List<Object[]> getDataForChart();
}
