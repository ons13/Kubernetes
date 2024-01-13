package com.moviehub.MovieHub.Servies;

import com.moviehub.MovieHub.Models.Rating;
import com.moviehub.MovieHub.Models.Request.RatingUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface RatingService {

    List<Rating> getAllRatings();

    Optional<Rating> getRatingById(Long ratingId);

    Rating saveRating(Rating rating);

    void deleteRating(Long ratingId);

    Rating updateRating(Long movieId, Long userId, RatingUpdateRequest ratingUpdateRequest);

}