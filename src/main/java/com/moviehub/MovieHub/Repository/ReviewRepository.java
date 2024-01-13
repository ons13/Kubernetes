package com.moviehub.MovieHub.Repository;

import com.moviehub.MovieHub.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.movieId = :movieId")
    List<Review> getReviewsBymovieId(@Param("movieId") Long movieId);

}
