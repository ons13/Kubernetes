package com.moviehub.MovieHub.Servies.Implementation;

import com.moviehub.MovieHub.Models.Review;
import com.moviehub.MovieHub.Repository.ReviewRepository;
import com.moviehub.MovieHub.Servies.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<Review> getReviewsBymovieId(Long movieId) {
        return reviewRepository.getReviewsBymovieId(movieId);
    }

}
