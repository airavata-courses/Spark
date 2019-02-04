package com.MovieRatingService.model;

public class UserRating {
    private long userId;
    private long movieId;
    private String movieName;
    private float rating;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public UserRating(long userId, long movieId, String movieName, float rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.rating = rating;
    }

    public UserRating() {
    }
}
