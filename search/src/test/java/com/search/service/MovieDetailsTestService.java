package com.search.service;

import com.search.models.Cast;
import com.search.models.CastAndCrew;
import com.search.models.Crew;
import com.search.models.MovieDetails;
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
public class MovieDetailsTestService {

    @InjectMocks
    private MovieDetailsService movieDetailsService;

    @Mock
    private Environment env;

    @Mock
    private RestTemplate restTemplate;

    private MovieDetails movieDetails;
    private Cast cast;
    private Crew crew;
    private CastAndCrew castAndCrew;
    private int castAndCrewId;
    private int movieID;

    @Before
    public void setup() {
        castAndCrewId = 123;
        movieID = 123;

        cast = new Cast();
        cast.setCast_id(1);
        cast.setName("Test Cast");

        crew = new Crew();
        crew.setCredit_id("1");
        crew.setName("Test Crew");

        castAndCrew = new CastAndCrew();
        castAndCrew.setId(1);
        castAndCrew.setCast(Arrays.asList(cast));
        castAndCrew.setCrew(Arrays.asList(crew));

        movieDetails = new MovieDetails();
        movieDetails.setId(movieID);
        movieDetails.setTitle("Test Movie");

    }

    @Test
    public void getMovieDetails() {
        when(env.getProperty("url.url_getById")).thenReturn("test/uri/");
        when(env.getProperty("url.applicationKey")).thenReturn("123");
        when(restTemplate.getForObject("test/uri/123?api_key=123&language=en-US", MovieDetails.class)).thenReturn(movieDetails);
        when(restTemplate.getForObject("https://api.themoviedb.org/3/movie/123/credits?api_key=123", CastAndCrew.class)).thenReturn(castAndCrew);

        MovieDetails movieDetails1 = movieDetailsService.getMovieDetailsById(movieID);

        assertThat(movieDetails1.getId(), is(123));
        assertThat(movieDetails1.getCast(), is(notNullValue()));
        assertThat(movieDetails1.getCrew(), is(notNullValue()));
        assertThat(movieDetails1.getCast().size(), is(1));
        assertThat(movieDetails1.getCrew().size(), is(1));

    }

}
