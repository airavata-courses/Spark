package com.MovieRatingService.controller;

import com.MovieRatingService.dao.UserMovieRatingRepository;
import com.MovieRatingService.entity.UserMovieRating;
import com.MovieRatingService.model.UserRating;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserMovieRatingTestController {


    private MockMvc mockMvc;

    @Mock
    private UserMovieRatingRepository userMovieRatingRepository;

    @InjectMocks
    private ObjectMapper mapper;

    @InjectMocks
    private UserMovieRatingController userMovieRatingController;

    private UserRating userRating;
    private UUID userid;
    private UserMovieRating userMovieRating1;
    private UserMovieRating userMovieRating2;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userMovieRatingController).build();
        userid = UUID.randomUUID();
        userRating = new UserRating(userid, 999999, "Test Movie", 8);
        userMovieRating1 = new UserMovieRating(userid.toString(), 999999, "Test Movie", 8);
        userMovieRating2 = new UserMovieRating(userid.toString(), 999998, "Test Movie 2", 2);
    }

    @Test
    public void saveUserRating() {
        when(userMovieRatingRepository.save(any())).thenReturn(userMovieRating1);

        try{
            mockMvc.perform(
                    post("/usermovierating/save")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(userRating)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println("exception" + e.getMessage());
        }
    }

    @Test
    public void findRatingByUseridAndMovieid() {
        when(userMovieRatingRepository.findById(any())).thenReturn(Optional.of(userMovieRating1));

        try{
            mockMvc.perform(
                    get("/usermovierating/getbyuseridmovieid")
                            .param("user_id", userid.toString())
                            .param("movie_id", "999999")
            )
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.movieName",  is("Test Movie")))
                    .andExpect(jsonPath("$.rating",  is(8)));
        } catch (Exception e) {
            System.out.println("exception" + e.getMessage());
        }
    }

    @Test
    public void findRatingByUserid() {
        when(userMovieRatingRepository.findAllByUserId(userid.toString()))
                .thenReturn(Arrays.asList(userMovieRating1, userMovieRating2));

        try {
            mockMvc.perform(
                    get("/usermovierating/getbyuserid")
                            .param("user_id", userid.toString())
            )
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].movieName", is("Test Movie")))
                    .andExpect(jsonPath("$[0].rating",  is(8)))
                    .andExpect(jsonPath("$[0].movieName", is("Test Movie 2")))
                    .andExpect(jsonPath("$[0].rating",  is(2)));
        } catch (Exception e) {

        }
    }
}
