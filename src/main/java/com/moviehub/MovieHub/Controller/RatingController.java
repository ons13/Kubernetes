package com.moviehub.MovieHub.Controller;

import com.moviehub.MovieHub.Models.Rating;
import com.moviehub.MovieHub.Models.Request.RatingRequest;
import com.moviehub.MovieHub.Models.Request.RatingUpdateRequest;
import com.moviehub.MovieHub.Repository.MovieRepository;
import com.moviehub.MovieHub.Repository.UserRepository;
import com.moviehub.MovieHub.Servies.MovieService;
import com.moviehub.MovieHub.Servies.RatingService;
import com.moviehub.MovieHub.Servies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/all")
    public List<Rating> getAllRatings() {
        return ratingService.getAllRatings();
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<Rating> getRatingById(@PathVariable Long ratingId) {
        Optional<Rating> rating = ratingService.getRatingById(ratingId);
        return rating.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Rating> saveRating(@RequestBody Rating rating) {
        Rating savedRating = ratingService.saveRating(rating);
        return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/createWithRating")
    public ResponseEntity<Rating> saveRating(@RequestBody RatingRequest ratingRequest) {
        // Assuming RatingRequest is a DTO class that includes movieId, userId, and
        // status
        Rating rating = new Rating();
        movieService.getmovieById(ratingRequest.getMovieId()).ifPresent(rating::setMovie);
        userService.getUserById(ratingRequest.getUserId()).ifPresent(rating::setUser);

        rating.setStatus(ratingRequest.getStatus());

        Rating savedRating = ratingService.saveRating(rating);
        return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
    }

    @PutMapping("/update/{movieId}/{userId}")
    public ResponseEntity<Rating> updateRating(
            @PathVariable Long movieId,
            @PathVariable Long userId,
            @RequestBody RatingUpdateRequest updateRequest) {

        Rating updatedRating = ratingService.updateRating(movieId, userId, updateRequest);
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

}