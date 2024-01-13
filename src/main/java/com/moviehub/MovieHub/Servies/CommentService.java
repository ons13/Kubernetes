package com.moviehub.MovieHub.Servies;

import com.moviehub.MovieHub.Models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> getAllComments();

    Optional<Comment> getCommentById(Long commentId);

    Comment saveComment(Comment comment);

    void deleteComment(Long commentId);
}
