package com.MovieRatingService.entity;

import java.io.Serializable;
import java.util.UUID;

public class UserMovieRatingId implements Serializable {
    String userId;
    long movieId;

    public UserMovieRatingId(String userId, long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public UserMovieRatingId() {
    }
}
