package com.moviehub.MovieHub.Servies;

import com.moviehub.MovieHub.Models.Status;

import java.util.List;
import java.util.Optional;

public interface StatusService {

    List<Status> getAllStatus();

    Optional<Status> getStatusById(Long statusId);

    Status saveStatus(Status status);

    void deleteStatus(Long statusId);

    // Add the new method
    Optional<Status> getStatusByUserIdAndMovieId(Long userId, Long movieId);

    Optional<Status> getStatusBymovieIdAndUserId(Long movieId, Long userId);

    boolean isModalOpened(Long movieId, Long userId);

    // long countStatusRows();
    //
    // Double calculateAverageRating();

    long countStatusRows(Long movieId);

    Double calculateAverageRating(Long movieId);

}
