package com.moviehub.MovieHub.Servies;

import com.moviehub.MovieHub.Models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> getAllReviews();

    Optional<Review> getReviewById(Long id);

    Review saveReview(Review review);

    void deleteReview(Long id);

    List<Review> getReviewsBymovieId(Long movieId);
}
