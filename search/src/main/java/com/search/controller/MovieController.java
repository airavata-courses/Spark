package com.search.controller;

import com.search.api.MovieApi;
import com.search.models.MovieDetails;
import com.search.service.MovieDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MovieController implements MovieApi {

    @Autowired
    private MovieDetailsService movieDetailsService;

    @Override
    public MovieDetails getMovieDetails(@RequestParam(name="movieId") int movieId) {
        return movieDetailsService.getMovieDetailsById(movieId);
    }
}
