package com.search.mapper;

import com.search.models.MovieResults;
import com.search.models.Movies;
import org.springframework.stereotype.Component;


@Component
public class MovieListMapper {
    public Movies toMovie(MovieResults movieResults) {
        Movies movies = new Movies();
        movies.setMovie_id(movieResults.getId());
        movies.setTitle(movieResults.getTitle());
        movies.setPoster_path(movieResults.getPoster_path());
        movies.setOriginal_language(movieResults.getOriginal_language());
        movies.setVote_count(movieResults.getVote_count());
        return movies;
    }
}
