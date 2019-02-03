package com.search.service;

import com.search.mapper.MovieListMapper;
import com.search.models.MovieList;
import com.search.models.Movies;
import com.search.models.SearchMovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchMovieService {

    @Autowired
    private MovieListMapper movieListMapper;

    public MovieList searchByKeyword(@NonNull String key) {
        final String uri = String.format("https://api.themoviedb.org/3/search/movie?api_key=066f82f3715ba0beb02e8a92d3f1f31f&language=en-US&query=%s", key);

        return restApiCall(uri);
    }

    public MovieList searchTopRated() {
        final String uri = String.format("https://api.themoviedb.org/3/movie/top_rated?api_key=066f82f3715ba0beb02e8a92d3f1f31f&language=en-US");

        return restApiCall(uri);
    }

    private MovieList restApiCall(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            SearchMovieList result = restTemplate.getForObject(uri, SearchMovieList.class);

            List<Movies> movies = result.getResults().stream().map(movieListMapper::toMovie).collect(Collectors.toList());
            return new MovieList(movies);
        }  catch (Exception ex) {
            System.out.print("Request failed: " + ex.getMessage());
        }
        return null;
    }
}
