package com.MovieRatingService.api;

import com.MovieRatingService.model.UserRating;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usermovierating")
public interface UserMovieRatingApi {
    @PostMapping("/save")
    ResponseEntity<?> saveUserRating(@RequestBody UserRating userRating);

    @GetMapping("/getbyuserid")
    List<UserRating> getByUserId(@RequestParam(name = "user_id") long userId);

    @GetMapping("/getbyuseridmovieid")
    UserRating getByUserIdMovieId(@RequestParam(name = "user_id") long userId,
                                        @RequestParam(name = "movie_id") long movieId);
}
