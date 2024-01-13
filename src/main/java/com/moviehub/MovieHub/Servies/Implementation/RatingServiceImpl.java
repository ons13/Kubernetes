package com.moviehub.MovieHub.Servies.Implementation;

import com.moviehub.MovieHub.Exceptions.InvalidStatusException;
import com.moviehub.MovieHub.Exceptions.RatingNotFoundException;
import com.moviehub.MovieHub.Models.Rating;
import com.moviehub.MovieHub.Models.Request.RatingUpdateRequest;
import com.moviehub.MovieHub.Repository.RatingRepository;
import com.moviehub.MovieHub.Servies.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Optional<Rating> getRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId);
    }

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    @Override
    public Rating updateRating(Long movieId, Long userId, RatingUpdateRequest ratingUpdateRequest) {
        Optional<Rating> optionalRating = ratingRepository.findBymovieIdAndUserId(movieId, userId);

        if (optionalRating.isPresent()) {
            Rating rating = optionalRating.get();

            if (ratingUpdateRequest.getNewRating() != null) {
                rating.setRating(ratingUpdateRequest.getNewRating());
            }

            if (ratingUpdateRequest.getNewStatus() != null) {
                try {
                    rating.setStatus(ratingUpdateRequest.getNewStatus());
                } catch (IllegalArgumentException e) {
                    // Handle the case where an invalid status is provided
                    throw new InvalidStatusException("Invalid status: " + ratingUpdateRequest.getNewStatus());
                }
            }

            return ratingRepository.save(rating);
        } else {
            // Handle the case where the rating does not exist
            throw new RatingNotFoundException("Rating not found for movieId: " + movieId + " and userId: " + userId);
        }
    }
}
