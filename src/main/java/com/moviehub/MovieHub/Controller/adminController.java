package com.moviehub.MovieHub.Controller;

import com.moviehub.MovieHub.Models.Movie;
import com.moviehub.MovieHub.Models.Comment;
import com.moviehub.MovieHub.Models.User;
import com.moviehub.MovieHub.Servies.MovieService;
import com.moviehub.MovieHub.Servies.CommentService;
import com.moviehub.MovieHub.Servies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class adminController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        // List<Object[]> chartData = movieService.getDataForChart();

        List<Movie> movieList = movieService.getAllmovie();

        List<User> userlist = userService.getAllUsers();

        List<Comment> commentlist = commentService.getAllComments();

        // model.addAttribute("chartData", chartData);
        model.addAttribute("movieList", movieList);
        model.addAttribute("users", userlist);
        model.addAttribute("Genres", movieService.getAllGenres());
        model.addAttribute("comment", commentlist);

        return "pages/index";
    }

    @GetMapping("/admin/dashboard/users")
    public String users(Model model) {

        List<User> userlist = userService.getAllUsers();
        model.addAttribute("users", userlist);

        return "pages/users";
    }

    @GetMapping("/admin/dashboard/categories")
    public String categories(Model model) {

        Set<String> genr = movieService.getAllGenres();
        model.addAttribute("cat", genr);

        return "pages/genres";
    }

    @GetMapping("/admin/dashboard/movies")
    public String movies(Model model) {

        List<Movie> movies = movieService.getAllmovie();
        model.addAttribute("movies", movies);

        return "pages/movies";
    }

    @PostMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/dashboard/users";
    }

    @GetMapping("/edit/{userId}")
    public String editUser(@PathVariable Long userId, Model model) {
        Optional<User> userOptional = userService.getUserById(userId);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "pages/Actions/EditUser";
    }

    @PostMapping("/edit/{userId}")
    public String saveEditedUser(@PathVariable Long userId,
            @RequestParam String newUsername,
            @RequestParam String newEmail,
            @RequestParam String newPassword) {
        userService.updateUser(userId, newUsername, newEmail, newPassword);
        return "redirect:/admin/dashboard/users";
    }

    @PostMapping("/movies/delete/{movieId}")
    public String deletemovie(@PathVariable Long movieId) {
        movieService.deletemovie(movieId);
        return "redirect:/admin/dashboard/movies";
    }

    @GetMapping("/movies/edit/{movieId}")
    public String editmovie(@PathVariable Long movieId, Model model) {
        Optional<Movie> movieOptional = movieService.getmovieById(movieId);
        Movie movie = movieOptional.orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("movie", movie);
        return "pages/Actions/Editmovie";
    }

    @PostMapping("/movies/edit/{movieId}")
    public String saveEditedmovie(@PathVariable Long movieId,
            @RequestParam String newDescription,
            @RequestParam String newName,
            @RequestParam Integer newOpened,
            @RequestParam String newImage,
            @RequestParam Set<String> newGenders) {
        // Retrieve the existing movie by its ID
        Optional<Movie> optionalmovie = movieService.getmovieById(movieId);

        if (optionalmovie.isPresent()) {
            Movie movie = optionalmovie.get();

            // Update the fields with the new values
            movie.setDescription(newDescription);
            movie.setName(newName);
            movie.setOpened(newOpened);
            movie.setImage(newImage);
            movie.setGenders(newGenders);

            // Save the updated movie
            movieService.updatemovieById(movieId, movie);

            // Redirect to the movie list page or any other appropriate page
            return "redirect:/admin/dashboard/movies";
        } else {
            // Handle the case where the movie with the given ID is not found
            // You might want to show an error message or redirect to an error page
            return "redirect:/error";
        }
    }

    @PostMapping("/admin/dashboard/addmovie")
    public String addmovie(@ModelAttribute Movie movie) {
        // Save the new movie to the database
        movieService.savemovie(movie);

        // Redirect to the movie list page or any other appropriate page
        return "redirect:/admin/dashboard/movies";
    }

    @GetMapping("/admin/dashboard/addmovie")
    public String showAddmovieForm(Model model) {
        // Create a new movie object to bind the form data
        Movie movie = new Movie();
        model.addAttribute("movie", movie);

        return "pages/Actions/addmovie";
    }

}