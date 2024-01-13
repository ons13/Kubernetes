package com.moviehub.MovieHub.Repository;

import com.moviehub.MovieHub.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
