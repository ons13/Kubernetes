package com.moviehub.MovieHub.Repository;

import com.moviehub.MovieHub.Models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findBymovieId(Long movieId);

    Optional<Rating> findBymovieIdAndUserId(Long movieId, Long userId);

}
