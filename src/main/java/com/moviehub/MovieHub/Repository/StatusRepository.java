package com.moviehub.MovieHub.Repository;

import com.moviehub.MovieHub.Models.Rating;
import com.moviehub.MovieHub.Models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {

    Optional<Status> findByUserIdAndMovieId(Long userId, Long movieId);


    Optional<Status> findBymovieIdAndUserId(Long movieId, Long userId);

    boolean existsBymovieIdAndUserId(Long movieId, Long userId);

    // long countByStatusIsNotNull();
    long countBymovieIdAndStatusIsNotNull(Long movieId);

    @Query("SELECT AVG(s.rating) FROM Status s WHERE s.movieId = :movieId AND s.rating IS NOT NULL")
    Double calculateAverageRating(@Param("movieId") Long movieId);

    // @Query("SELECT AVG(s.rating) FROM Status s WHERE s.rating IS NOT NULL")
    // Double calculateAverageRating();

}
