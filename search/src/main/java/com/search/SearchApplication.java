package com.search;

import com.search.constants.UrlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SearchApplication {

	public static void main(String[] args) {
		//Jenkins test build
		SpringApplication.run(SearchApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public UrlProperties urlProperties() {
		return new UrlProperties();
	}
}

