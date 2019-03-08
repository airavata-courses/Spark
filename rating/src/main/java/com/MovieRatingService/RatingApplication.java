package com.MovieRatingService;

import com.MovieRatingService.config.IpProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RatingApplication {
	//Jenkins test build
	public static void main(String[] args) {
		SpringApplication.run(RatingApplication.class, args);
	}

	@Bean
	public IpProperties ipProperties() {
		return new IpProperties();
	}
}

