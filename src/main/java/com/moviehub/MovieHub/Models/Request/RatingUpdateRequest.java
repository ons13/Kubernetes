package com.moviehub.MovieHub.Models.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RatingUpdateRequest {

    private Integer newRating;
    private String newStatus;
}
