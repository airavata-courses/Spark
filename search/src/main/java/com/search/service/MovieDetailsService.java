package com.search.service;

import com.search.models.CastAndCrew;
import com.search.models.MovieDetails;
import com.search.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
public class MovieDetailsService {

    @Autowired
    private Environment env;

    public MovieDetails getMovieDetailsById(@NonNull UUID userId, @NonNull int movieId) {
        final String uri = String.format(env.getProperty("url.url_getById") + movieId +"?api_key=" + env.getProperty("url.applicationKey") + "&language=en-US");
        RestTemplate restTemplate = new RestTemplate();

        try {
            MovieDetails movieDetails = restTemplate.getForObject(uri, MovieDetails.class);

            if (movieDetails != null) {
                CastAndCrew castAndCrew = getCastAndCrewByMovieId(movieId);
                if (castAndCrew != null) {
                    movieDetails.setCast(castAndCrew.getCast());
                    movieDetails.setCrew(castAndCrew.getCrew());
                }
                if (checkSeenStatus(userId, movieDetails.getId())) {
                    movieDetails.setSeen(true);
                } else {
                    movieDetails.setSeen(false);
                }
            }

            return movieDetails;
        }  catch (Exception ex) {
            System.out.print("Request failed: " + ex.getMessage());
        }
        return null;
    }

    public CastAndCrew getCastAndCrewByMovieId(@NonNull int id) {
        final String uri = String.format("https://api.themoviedb.org/3/movie/" + id +"/credits?api_key=" + env.getProperty("url.applicationKey"));
        RestTemplate restTemplate = new RestTemplate();

        try {
            CastAndCrew castAndCrew = restTemplate.getForObject(uri, CastAndCrew.class);
            return castAndCrew;
        }  catch (Exception ex) {
            System.out.print("Request failed: " + ex.getMessage());
        }

        return null;
    }

    public boolean checkSeenStatus(@NonNull UUID userid, @NonNull int movieid) {
        final String uri = String.format("http://localhost:8080/usermovierating/getbyuseridmovieid?user_id=%s&movie_id=%s", userid, movieid);
        RestTemplate restTemplate = new RestTemplate();
        try {
            UserRating userRating = restTemplate.getForObject(uri, UserRating.class);
            return Optional.ofNullable(userRating)
                    .map(s -> {return true;})
                    .orElse(false);
        }  catch (Exception ex) {
            System.out.print("Request failed: " + ex.getMessage());
        }
        return false;
    }
}
