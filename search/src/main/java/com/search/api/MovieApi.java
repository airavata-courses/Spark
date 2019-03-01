package com.search.api;

import com.search.models.MovieDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/movie")
public interface MovieApi {

    @GetMapping(path="/details")
    public MovieDetails getMovieDetails(@RequestParam(name="movieId") int movieId);
}
