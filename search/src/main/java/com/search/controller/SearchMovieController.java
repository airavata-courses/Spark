package com.search.controller;


import com.search.api.SearchMovieApi;
import com.search.models.MovieList;
import com.search.service.SearchMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/search")
public class SearchMovieController implements SearchMovieApi {

    @Autowired
    private SearchMovieService searchMovieService;


    @Override
    public MovieList searchByKeyword(@RequestParam(name="keyword") String key) {
        return searchMovieService.searchByKeyword(key);
    }

    @Override
    public MovieList searchTopRated() {
        return searchMovieService.searchTopRated();
    }
}
