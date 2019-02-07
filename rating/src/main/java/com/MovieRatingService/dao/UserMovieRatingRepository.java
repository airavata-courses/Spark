package com.MovieRatingService.dao;

import com.MovieRatingService.entity.UserMovieRating;
import com.MovieRatingService.entity.UserMovieRatingId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserMovieRatingRepository extends CrudRepository<UserMovieRating, UserMovieRatingId>{
    List<UserMovieRating> findAllByUserId(UUID userId);
}
