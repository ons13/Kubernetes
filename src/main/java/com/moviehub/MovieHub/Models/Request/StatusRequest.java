package com.moviehub.MovieHub.Models.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatusRequest {

    private Long movieId;
    private Long userId;
    private String status;
}
