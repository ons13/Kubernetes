package com.moviehub.MovieHub.Controller;

import com.moviehub.MovieHub.Models.Movie;
import com.moviehub.MovieHub.Repository.MovieRepository;
import com.moviehub.MovieHub.Servies.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController

@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository repository;

    @GetMapping("/all")
    public List<Movie> getAllmovie() {
        return movieService.getAllmovie();
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getmovieById(@PathVariable Long movieId) {
        Optional<Movie> movie = movieService.getmovieById(movieId);
        return movie.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Movie> savemovie(@RequestBody Movie movie) {
        Movie savedmovie = movieService.savemovie(movie);
        return new ResponseEntity<>(savedmovie, HttpStatus.CREATED);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deletemovie(@PathVariable Long movieId) {
        movieService.deletemovie(movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/average-rating/{movieId}")
    public ResponseEntity<Double> getAverageRatingBymovieId(@PathVariable Long movieId) {
        Double averageRating = movieService.getAverageRatingBymovieId(movieId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }

    @PostMapping("/increaseOpened/{movieId}")
    public ResponseEntity<Void> increaseOpenedByOne(@PathVariable Long movieId) {
        Optional<Movie> optionalmovie = movieService.getmovieById(movieId);

        if (optionalmovie.isPresent()) {
            Movie movie = optionalmovie.get();
            movie.setOpened(movie.getOpened() + 1);
            movieService.savemovie(movie); // Save the updated movie with the increased opened count
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // Handle the case where the movie with the given ID is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/top5")
    public List<Movie> getTop5ByOpened() {
        return repository.findTop5ByOpened();
    }

    @PostMapping("/{movieId}/favorite/{userId}")
    public ResponseEntity<Void> addToFavorites(@PathVariable Long movieId, @PathVariable Long userId) {
        movieService.addToFavorites(movieId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{movieId}/favorite/{userId}")
    public ResponseEntity<Void> removeFromFavorites(@PathVariable Long movieId, @PathVariable Long userId) {
        movieService.removeFromFavorites(movieId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all-genres")
    public ResponseEntity<Set<String>> getAllGenres() {
        Set<String> allGenres = movieService.getAllGenres();
        return new ResponseEntity<>(allGenres, HttpStatus.OK);
    }

    @GetMapping("/by-genre/{genre}")
    public ResponseEntity<List<Movie>> getmovieByGenre(@PathVariable String genre) {
        List<Movie> movieByGenre = movieService.getmovieByGenre(genre);
        return new ResponseEntity<>(movieByGenre, HttpStatus.OK);
    }

    // Add movie
    @PostMapping("/add")
    public ResponseEntity<Movie> addmovie(@RequestBody Movie movie) {
        Movie addedmovie = movieService.savemovie(movie);
        return new ResponseEntity<>(addedmovie, HttpStatus.CREATED);
    }

    // Edit movie
    @PutMapping("/edit/{movieId}")
    public ResponseEntity<Movie> editmovie(@PathVariable Long movieId, @RequestBody Movie updatedmovie) {
        Optional<Movie> existingmovie = movieService.getmovieById(movieId);

        if (existingmovie.isPresent()) {
            Movie movieToUpdate = existingmovie.get();
            movieService.savemovie(movieToUpdate);
            return new ResponseEntity<>(movieToUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
