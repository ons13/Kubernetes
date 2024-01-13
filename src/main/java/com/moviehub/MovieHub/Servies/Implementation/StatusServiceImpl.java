package com.moviehub.MovieHub.Servies.Implementation;

import com.moviehub.MovieHub.Models.Rating;
import com.moviehub.MovieHub.Models.Request.StatusUpdateRequest;
import com.moviehub.MovieHub.Models.Status;
import com.moviehub.MovieHub.Repository.StatusRepository;
import com.moviehub.MovieHub.Servies.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<Status> getAllStatus() {
        return statusRepository.findAll();
    }

    @Override
    public Optional<Status> getStatusById(Long statusId) {
        return statusRepository.findById(statusId);
    }

    @Override
    public Status saveStatus(Status status) {
        return statusRepository.save(status);

    }

    @Override
    public void deleteStatus(Long statusId) {
        statusRepository.deleteById(statusId);
    }

    @Override
    public Optional<Status> getStatusByUserIdAndMovieId(Long userId, Long movieId) {
        return statusRepository.findByUserIdAndMovieId(userId, movieId);
    }

    public Optional<Status> getStatusBymovieIdAndUserId(Long movieId, Long userId) {
        return statusRepository.findBymovieIdAndUserId(movieId, userId);
    }

    @Override
    public boolean isModalOpened(Long movieId, Long userId) {
        return statusRepository.existsBymovieIdAndUserId(movieId, userId);
    }

    @Override
    public long countStatusRows(Long movieId) {
        return statusRepository.countBymovieIdAndStatusIsNotNull(movieId);
    }

    @Override
    public Double calculateAverageRating(Long movieId) {
        return statusRepository.calculateAverageRating(movieId);
    }

}
