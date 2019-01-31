package com.search.models;

import java.util.List;

public class MovieList {
    private List<Movies> movies;

    public MovieList(List<Movies> movies) {
        this.movies = movies;
    }

    public List<Movies> getMovies() {
        return movies;
    }

    public void setMovies(List<Movies> movies) {
        this.movies = movies;
    }
}
