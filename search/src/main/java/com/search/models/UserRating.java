package com.search.models;

public class UserRating {
    private long userId;
    private long movieId;
    private String movieName;
    private float rating;

    public long getUserId() {
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

    public void setUserId(long userId) {
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
