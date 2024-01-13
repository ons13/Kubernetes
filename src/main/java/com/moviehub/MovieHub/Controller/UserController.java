package com.moviehub.MovieHub.Controller;

import com.moviehub.MovieHub.Models.Movie;
import com.moviehub.MovieHub.Models.Request.AuthenticationRequest;
import com.moviehub.MovieHub.Models.User;
import com.moviehub.MovieHub.Servies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController

@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{userId}/username")
    public String getUsernameById(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);

        return user.map(User::getUsername).orElse(null);
    }

    @PostMapping("/create")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        User authenticatedUser = userService.authenticateUser(email, password);

        if (authenticatedUser != null) {
            // Authentication successful
            return ResponseEntity.ok(authenticatedUser);
        } else {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/{userId}/favorite-movies")
    public ResponseEntity<Set<Movie>> getUserFavoritemovies(@PathVariable Long userId) {
        Set<Movie> favoritemovies = userService.getUserFavoritemovies(userId);

        if (favoritemovies != null) {
            return new ResponseEntity<>(favoritemovies, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
