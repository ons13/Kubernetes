package com.moviehub.MovieHub.Servies;

import com.moviehub.MovieHub.Models.Movie;
import com.moviehub.MovieHub.Models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Long userId);

    User saveUser(User user);

    void deleteUser(Long userId);

    User authenticateUser(String email, String password);

    Set<Movie> getUserFavoritemovies(Long userId);

    void updateUser(Long userId, String newUsername, String newEmail, String newPassword);

}
