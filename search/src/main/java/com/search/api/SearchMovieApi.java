package com.search.api;

import com.search.models.MovieList;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/search")
public interface SearchMovieApi {

    @GetMapping(path="/keyword")
    public MovieList searchByKeyword(@RequestParam(name="keyword") String key);

    @GetMapping(path="/toprated")
    public MovieList searchTopRated();
}
