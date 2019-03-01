package com.MovieRatingService.controller;

import java.net.InetAddress;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.MovieRatingService.api.UserMovieRatingApi;
import com.MovieRatingService.dao.UserMovieRatingRepository;
import com.MovieRatingService.entity.UserMovieRating;
import com.MovieRatingService.entity.UserMovieRatingId;
import com.MovieRatingService.mapper.UserRatingMapper;
import com.MovieRatingService.model.UserRating;

@CrossOrigin
@RestController
public class UserMovieRatingController implements UserMovieRatingApi {

    @Autowired
    UserMovieRatingRepository userMovieRatingRepository;
    
    @Autowired
    UserRatingMapper userRatingMapper;

    @Autowired
    Environment env;

    @PostConstruct
    public void registerService(){
        try {
        	InetAddress ip = InetAddress.getLocalHost();
            final String uri = "http://149.165.169.102:5000/services/register";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            params.add("name", "rating");
            params.add("uri", "http://" + env.getProperty("RATING_IP") +":8081");
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(uri, request, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public ResponseEntity<String> saveUserRating(@RequestBody UserRating userRating) {
        UserMovieRating userMovieRating = new UserMovieRating(userRating.getUserId().toString(), userRating.getMovieId(),
                userRating.getMovieName(), userRating.getRating());
        userMovieRatingRepository.save(userMovieRating);
//        System.out.println("uri for rating: " + zooKeeperServices.discoverServiceURI("rating"));
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
