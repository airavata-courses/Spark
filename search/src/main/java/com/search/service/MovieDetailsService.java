package com.search.service;

import com.search.models.CastAndCrew;
import com.search.models.MovieDetails;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieDetailsService {

    public MovieDetails getMovieDetailsById(@NonNull int id) {
        final String uri = String.format("https://api.themoviedb.org/3/movie/" + id +"?api_key=066f82f3715ba0beb02e8a92d3f1f31f&language=en-US");
        RestTemplate restTemplate = new RestTemplate();
        try {
            MovieDetails movieDetails = restTemplate.getForObject(uri, MovieDetails.class);
            CastAndCrew castAndCrew = getCastAndCrewByMovieId(id);
            if (castAndCrew != null) {
                movieDetails.setCast(castAndCrew.getCast());
                movieDetails.setCrew(castAndCrew.getCrew());
            }
            return movieDetails;
        }  catch (Exception ex) {
            System.out.print("Request failed: " + ex.getMessage());
        }
        return null;
    }

    public CastAndCrew getCastAndCrewByMovieId(@NonNull int id) {
        final String uri = String.format("https://api.themoviedb.org/3/movie/" + id +"/credits?api_key=066f82f3715ba0beb02e8a92d3f1f31f");
        RestTemplate restTemplate = new RestTemplate();
        try {
            CastAndCrew castAndCrew = restTemplate.getForObject(uri, CastAndCrew.class);
            return castAndCrew;
        }  catch (Exception ex) {
            System.out.print("Request failed: " + ex.getMessage());
        }
        return null;
    }
}
