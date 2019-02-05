package com.search.models;

import java.util.UUID;

public class UserRating {
    private UUID userId;
    private long movieId;
    private String movieName;
    private float rating;

    public UUID getUserId() {
        return userId;
    }

    public long getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public float getRating() {
        return rating;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
