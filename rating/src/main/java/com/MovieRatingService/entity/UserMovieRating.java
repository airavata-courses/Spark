package com.MovieRatingService.entity;

import javax.persistence.*;

@Entity @IdClass(UserMovieRatingId.class)
@Table(name="user_movie_rating")
public class UserMovieRating {

    @Id
    @Column(name="user_id")
    private long userId;

    @Id
    @Column(name="movie_id")
    private long movieId;

    @Column(name="movie_name")
    private String movieName;

    @Column(name="rating")
    private float rating;

    public UserMovieRating() {
        super();
    }

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

    public UserMovieRating(long userId, long movieId, String movieName, float rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.rating = rating;
    }
}
