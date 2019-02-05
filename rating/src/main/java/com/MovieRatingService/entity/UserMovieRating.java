package com.MovieRatingService.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity @IdClass(UserMovieRatingId.class)
@Table(name="user_movie_rating")
public class UserMovieRating {

    @Id
    @Column(name="user_id", columnDefinition = "VARCHAR(100)")
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public UserMovieRating(String userId, long movieId, String movieName, float rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.rating = rating;
    }
}
