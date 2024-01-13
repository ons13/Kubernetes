package com.moviehub.MovieHub.Controller;

import com.moviehub.MovieHub.Models.Movie;
import com.moviehub.MovieHub.Models.Comment;
import com.moviehub.MovieHub.Models.User;
import com.moviehub.MovieHub.Servies.MovieService;
import com.moviehub.MovieHub.Servies.CommentService;
import com.moviehub.MovieHub.Servies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        return comment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment) {
        Comment savedComment = commentService.saveComment(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @PostMapping("/add-comment/{movieId}/{userId}")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long movieId,
            @PathVariable Long userId,
            @RequestBody String commentContent) {
        Optional<Movie> optionalmovie = movieService.getmovieById(movieId);
        Optional<User> optionalUser = userService.getUserById(userId);

        if (optionalmovie.isPresent() && optionalUser.isPresent()) {
            Movie movie = optionalmovie.get();
            User user = optionalUser.get();

            Comment comment = new Comment();
            comment.setMovie(movie);
            comment.setUser(user);
            comment.setComment(commentContent);

            Comment savedComment = commentService.saveComment(comment);

            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
