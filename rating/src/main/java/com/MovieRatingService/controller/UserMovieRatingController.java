package com.MovieRatingService.controller;

import com.MovieRatingService.api.UserMovieRatingApi;
import com.MovieRatingService.dao.UserMovieRatingRepository;
import com.MovieRatingService.entity.UserMovieRating;
import com.MovieRatingService.entity.UserMovieRatingId;
import com.MovieRatingService.mapper.UserRatingMapper;
import com.MovieRatingService.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserMovieRatingController implements UserMovieRatingApi {

    @Autowired
    UserMovieRatingRepository userMovieRatingRepository;
    @Autowired
    UserRatingMapper userRatingMapper;

    @Override
    public ResponseEntity<String> saveUserRating(@RequestBody UserRating userRating) {
        UserMovieRating userMovieRating = new UserMovieRating(userRating.getUserId(), userRating.getMovieId(),
                userRating.getMovieName(), userRating.getRating());
        userMovieRatingRepository.save(userMovieRating);

        return new ResponseEntity<>("User rating successfully saved!!", HttpStatus.OK);
    }

    @Override
    public List<UserRating> getByUserId(@RequestParam(name = "user_id") long userId) {
        List<UserMovieRating> userMovieRatings = userMovieRatingRepository.findAllByUserId(userId);
        List<UserRating> userRatings = userMovieRatings.stream().map(userRatingMapper::toUserRating).collect(Collectors.toList());
        return userRatings;
    }

    @Override
    public UserRating getByUserIdMovieId(long userId, long movieId) {
        Optional<UserMovieRating> userMovieRating = userMovieRatingRepository.findById(new UserMovieRatingId(userId, movieId));
        return userMovieRating.map(obj -> {
            UserRating userRating = userRatingMapper.toUserRating(obj);
            return userRating;
        }).orElse(null);
    }
}
