package com.moviehub.MovieHub.Repository;

import com.moviehub.MovieHub.Models.Status;
import org.springframework.data.domain.Pageable;

import com.moviehub.MovieHub.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT a FROM Movie a ORDER BY a.opened DESC LIMIT 5")
    List<Movie> findTop5ByOpened();

    List<Movie> findByGendersContainingIgnoreCase(String genders);

    @Query(value = "SELECT gender, COUNT(*) as count FROM movie_genders GROUP BY gender", nativeQuery = true)
    List<Object[]> countmovieByGender();



}
