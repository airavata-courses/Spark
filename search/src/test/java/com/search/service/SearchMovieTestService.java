package com.search.service;

import com.search.mapper.MovieListMapper;
import com.search.models.MovieList;
import com.search.models.MovieResults;
import com.search.models.Movies;
import com.search.models.SearchMovieList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.notNullValue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchMovieTestService {

    @Mock
    private MovieListMapper movieListMapper;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Environment env;

    @InjectMocks
    private SearchMovieService searchMovieService;

    private SearchMovieList searchMovieList;
    private Movies movies;
    private MovieResults movieResults;
    private MovieList movieList;
    private int movieId;

    @Before
    public void setup() {
        movieId = 123;

        movieResults = new MovieResults();
        movieResults.setId(movieId);
        movieResults.setTitle("Test Movie Name");
        movieResults.setPoster_path("Test Poster Path");

        movies = new Movies();
        movies.setMovie_id(movieId);
        movies.setTitle("Test Movie Name");
        movies.setPoster_path("Test Poster Path");

        searchMovieList = new SearchMovieList();
        searchMovieList.setResults(Arrays.asList(movieResults));

        movieList = new MovieList(Arrays.asList(movies));
    }

    @Test
    public void searchTopRatedMovie() {
        when(env.getProperty("url.urlTopRated")).thenReturn("Test URL");
        when(restTemplate.getForObject("Test URL", SearchMovieList.class)).thenReturn(searchMovieList);
        when(movieListMapper.toMovie(movieResults)).thenReturn(movies);

        MovieList movieList = searchMovieService.searchTopRated();

        assertThat(movieList.getMovies().size(), is(1));
        assertThat(movieList.getMovies(), is(notNullValue()));
        assertThat(movieList.getMovies().get(0).getMovie_id(), is(movieId));
        assertThat(movieList.getMovies().get(0).getTitle(), is("Test Movie Name"));
        assertThat(movieList.getMovies().get(0).getPoster_path(), is("Test Poster Path"));
    }

    @Test
    public void searchMovieByKey() {
        when(env.getProperty("url.url_searchBykeyword")).thenReturn("test/uri?");
        when(restTemplate.getForObject("test/uri?query=mom", SearchMovieList.class)).thenReturn(searchMovieList);
        when(movieListMapper.toMovie(movieResults)).thenReturn(movies);

        MovieList movieList = searchMovieService.searchByKeyword("mom");

        assertThat(movieList.getMovies().size(), is(1));
        assertThat(movieList.getMovies(), is(notNullValue()));
        assertThat(movieList.getMovies().get(0).getMovie_id(), is(movieId));
        assertThat(movieList.getMovies().get(0).getTitle(), is("Test Movie Name"));
        assertThat(movieList.getMovies().get(0).getPoster_path(), is("Test Poster Path"));
    }
}
