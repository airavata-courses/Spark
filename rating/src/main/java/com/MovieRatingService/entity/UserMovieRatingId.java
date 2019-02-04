package com.MovieRatingService.entity;

import java.io.Serializable;

public class UserMovieRatingId implements Serializable {
    long userId;
    long movieId;

    public UserMovieRatingId(long userId, long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
}
