package com.moviehub.MovieHub.Servies.Implementation;

import com.moviehub.MovieHub.Models.Movie;
import com.moviehub.MovieHub.Models.User;
import com.moviehub.MovieHub.Repository.MovieRepository;
import com.moviehub.MovieHub.Repository.RatingRepository;
import com.moviehub.MovieHub.Repository.UserRepository;
import com.moviehub.MovieHub.Servies.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.moviehub.MovieHub.Models.Rating;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Movie> getAllmovie() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> getmovieById(Long movieId) {
        return movieRepository.findById(movieId);
    }

    @Override
    public Movie savemovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deletemovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }

    @Override
    public Double getAverageRatingBymovieId(Long movieId) {
        OptionalDouble average = ratingRepository.findBymovieId(movieId)
                .stream()
                .mapToDouble(Rating::getRating)
                .average();

        return average.orElse(0.0); // Default value if no ratings are found
    }

    @Override
    public void increaseOpenedByOne(Long movieId) {
        Optional<Movie> optionalmovie = movieRepository.findById(movieId);

        if (optionalmovie.isPresent()) {
            Movie movie = optionalmovie.get();
            movie.setOpened(movie.getOpened() + 1);
            movieRepository.save(movie);
        } else {
            // Handle the case where the movie with the given ID is not found
            // You might throw an exception, log a message, or handle it in another way
            // based on your requirements.
        }
    }

    @Override
    public void addToFavorites(Long movieId, Long userId) {
        Optional<Movie> optionalmovie = movieRepository.findById(movieId);
        Optional<User> optionalUser = userRepository.findById(userId);

        optionalmovie.ifPresent(movie -> optionalUser.ifPresent(user -> {
            movie.addFavoritedBy(user);
            movieRepository.save(movie);
        }));
    }

    @Override
    public void removeFromFavorites(Long movieId, Long userId) {
        Optional<Movie> optionalmovie = movieRepository.findById(movieId);
        Optional<User> optionalUser = userRepository.findById(userId);

        optionalmovie.ifPresent(movie -> optionalUser.ifPresent(user -> {
            movie.removeFavoritedBy(user);
            movieRepository.save(movie);
        }));
    }

    @Override
    public Set<String> getAllGenres() {
        List<Movie> allmovie = movieRepository.findAll();
        Set<String> allGenres = new HashSet<>();

        for (Movie movie : allmovie) {
            allGenres.addAll(movie.getGenders());
        }

        return allGenres;
    }

    @Override
    public List<Movie> getmovieByGenre(String genders) {
        return movieRepository.findByGendersContainingIgnoreCase(genders);
    }

    @Override
    public List<Object[]> countmovieByGender() {
        return movieRepository.countmovieByGender();
    }

    @Override
    public List<Object[]> countmovieByGenders() {
        return movieRepository.countmovieByGender();
    }

    public List<Object[]> getDataForChart() {
        // Example: Get the count of movies for each gender
        return movieRepository.countmovieByGender();
    }

    @Override
    public Movie updatemovieById(Long movieId, Movie updatedmovie) {
        Optional<Movie> optionalmovie = movieRepository.findById(movieId);

        if (optionalmovie.isPresent()) {
            Movie existingmovie = optionalmovie.get();

            // Update the fields with the new values
            existingmovie.setDescription(updatedmovie.getDescription());
            existingmovie.setName(updatedmovie.getName());
            existingmovie.setOpened(updatedmovie.getOpened());
            existingmovie.setImage(updatedmovie.getImage());
            existingmovie.setGenders(updatedmovie.getGenders());

            // Save the updated movie
            return movieRepository.save(existingmovie);
        } else {
            return null; // You can return null or throw an exception
        }
    }

}
