package com.moviehub.MovieHub.Servies.Implementation;

import com.moviehub.MovieHub.Models.Movie;
import com.moviehub.MovieHub.Models.User;
import com.moviehub.MovieHub.Repository.MovieRepository;
import com.moviehub.MovieHub.Repository.UserRepository;
import com.moviehub.MovieHub.Servies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User saveUser(User user) {

        // Hash the password before saving
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User authenticateUser(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Use the password encoder to check if the entered password matches the stored
            // hashed password
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user; // Authentication successful
            }
        }

        return null; // Authentication failed
    }

    public Set<Movie> getUserFavoritemovies(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getFavoritemovies();
        } else {
            // Handle the case where the user with the given ID is not found
            return null;
        }
    }

    @Override
    public void updateUser(Long userId, String newUsername, String newEmail, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // Logic to update user details
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setUsername(newUsername);
            user.setEmail(newEmail);
            user.setPassword(passwordEncoder.encode(newPassword)); // Ensure to encode the password
            userRepository.save(user);
        }
    }

}