package com.moviehub.MovieHub.Controller;

import com.moviehub.MovieHub.Models.Rating;
import com.moviehub.MovieHub.Models.Request.RatingRequest;
import com.moviehub.MovieHub.Models.Request.RatingUpdateRequest;
import com.moviehub.MovieHub.Models.Request.StatusRequest;
import com.moviehub.MovieHub.Models.Request.StatusUpdateRequest;
import com.moviehub.MovieHub.Models.Status;
import com.moviehub.MovieHub.Servies.MovieService;
import com.moviehub.MovieHub.Servies.RatingService;
import com.moviehub.MovieHub.Servies.StatusService;
import com.moviehub.MovieHub.Servies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/Status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/all")
    public List<Status> getAllRatings() {
        return statusService.getAllStatus();
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<Status> getRatingById(@PathVariable Long ratingId) {
        Optional<Status> status = statusService.getStatusById(ratingId);
        return status.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Status> saveRating(@RequestBody Status status) {
        Status savedStatus = statusService.saveStatus(status);
        return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long statusId) {
        statusService.deleteStatus(statusId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // @PostMapping("/add")
    // public ResponseEntity<Status> addStatus(@RequestBody StatusRequest
    // statusRequest) {
    // // Convert StatusRequest to Status entity
    // Status newStatus = new Status();
    // newStatus.setmovieId(statusRequest.getmovieId());
    // newStatus.setUserId(statusRequest.getUserId());
    // newStatus.setStatus(statusRequest.getStatus());
    //
    // // Save the new status
    // Status savedStatus = statusService.saveStatus(newStatus);
    //
    // return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
    // }

    // In your controller
    @GetMapping("/movie/{movieId}/modal-status/{userId}")
    public ResponseEntity<Boolean> checkModalStatus(@PathVariable Long movieId, @PathVariable Long userId) {
        boolean isModalOpened = statusService.isModalOpened(movieId, userId);
        return ResponseEntity.ok(isModalOpened);
    }

    @PostMapping("/add")
    public ResponseEntity<Status> addStatus(@RequestBody StatusRequest statusRequest) {
        // Check if a status with the same movieId and userId already exists
        Optional<Status> existingStatus = statusService.getStatusBymovieIdAndUserId(
                statusRequest.getMovieId(), statusRequest.getUserId());

        if (existingStatus.isPresent()) {
            // If status already exists, return conflict
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Convert StatusRequest to Status entity
        Status newStatus = new Status();
        newStatus.setMovieId(statusRequest.getMovieId());
        newStatus.setUserId(statusRequest.getUserId());
        newStatus.setStatus(statusRequest.getStatus());

        // Save the new status
        Status savedStatus = statusService.saveStatus(newStatus);

        return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
    }

    @PutMapping("/update/{statusId}")
    public ResponseEntity<Status> updateStatus(@PathVariable Long statusId,
            @RequestBody StatusUpdateRequest updateRequest) {
        // Get the existing status
        Status existingStatus = statusService.getStatusById(statusId)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        // Update the status based on the request
        if (updateRequest.getNewStatus() != null) {
            existingStatus.setStatus(updateRequest.getNewStatus());
        }
        if (updateRequest.getNewRating() != null) {
            existingStatus.setRating(updateRequest.getNewRating());
        }

        // Save the updated status
        Status updatedStatus = statusService.saveStatus(existingStatus);

        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
    }

    @PutMapping("/update/user")
    public ResponseEntity<Status> updateStatusByUser(@RequestParam Long userId, @RequestParam Long movieId,
            @RequestBody StatusUpdateRequest updateRequest) {

        // Get the existing status by userId and movieId
        Status existingStatus = statusService.getStatusByUserIdAndMovieId(userId, movieId)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        // Update the status based on the request
        if (updateRequest.getNewStatus() != null) {
            existingStatus.setStatus(updateRequest.getNewStatus());
        }
        if (updateRequest.getNewRating() != null) {
            existingStatus.setRating(updateRequest.getNewRating());
        }

        // Save the updated status
        Status updatedStatus = statusService.saveStatus(existingStatus);

        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
    }

    @GetMapping("/get/{movieId}/{userId}")
    public ResponseEntity<Status> getStatusBymovieIdAndUserId(
            @PathVariable Long movieId,
            @PathVariable Long userId) {
        Optional<Status> status = statusService.getStatusBymovieIdAndUserId(movieId, userId);

        return status.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/count/{movieId}")
    public ResponseEntity<Long> countStatusRows(@PathVariable Long movieId) {
        long count = statusService.countStatusRows(movieId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/average-rating/{movieId}")
    public ResponseEntity<Double> calculateAverageRating(@PathVariable Long movieId) {
        Double averageRating = statusService.calculateAverageRating(movieId);
        return ResponseEntity.ok(averageRating);
    }

}
