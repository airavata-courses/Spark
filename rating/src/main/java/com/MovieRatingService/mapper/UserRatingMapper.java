package com.MovieRatingService.mapper;

import com.MovieRatingService.entity.UserMovieRating;
import com.MovieRatingService.model.UserRating;
import org.springframework.stereotype.Component;

@Component
public class UserRatingMapper {

    public UserRating toUserRating(UserMovieRating userMovieRating) {
        return new UserRating(userMovieRating.getUserId(),
                userMovieRating.getMovieId(),
                userMovieRating.getMovieName(),
                userMovieRating.getRating());
    }
}
