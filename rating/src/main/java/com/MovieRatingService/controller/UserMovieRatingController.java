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
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class UserMovieRatingController implements UserMovieRatingApi {

    @Autowired
    UserMovieRatingRepository userMovieRatingRepository;
    @Autowired
    UserRatingMapper userRatingMapper;

    @Override
    public ResponseEntity<String> saveUserRating(@RequestBody UserRating userRating) {
        System.out.print("Userid : "+ userRating.getUserId());
        UserMovieRating userMovieRating = new UserMovieRating(userRating.getUserId().toString(), userRating.getMovieId(),
                userRating.getMovieName(), userRating.getRating());
        System.out.print("Userid : "+ userMovieRating.getUserId());
        userMovieRatingRepository.save(userMovieRating);

        return new ResponseEntity<>("User rating successfully saved!!", HttpStatus.OK);
    }

    @Override
    public List<UserRating> getByUserId(@RequestParam(name = "user_id") UUID userId) {
        List<UserMovieRating> userMovieRatings = userMovieRatingRepository.findAllByUserId(userId.toString());
        List<UserRating> userRatings = userMovieRatings.stream().map(userRatingMapper::toUserRating).collect(Collectors.toList());
        return userRatings;
    }

    @Override
    public UserRating getByUserIdMovieId(UUID userId, long movieId) {
        System.out.print("Userid : "+ userId);
        Optional<UserMovieRating> userMovieRating = userMovieRatingRepository.findById(new UserMovieRatingId(userId.toString(), movieId));
        System.out.print(userMovieRating.get().getUserId());
        return userMovieRating.map(obj -> {
            UserRating userRating = userRatingMapper.toUserRating(obj);
            return userRating;
        }).orElse(null);
    }
}
