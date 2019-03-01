package com.search.service;

import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.search.mapper.MovieListMapper;
import com.search.models.MovieList;
import com.search.models.Movies;
import com.search.models.SearchMovieList;

@Service
public class SearchMovieService {

    @Autowired
    private MovieListMapper movieListMapper;

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void registerService(){
        try {
            System.out.println("Hi in  registry");
        	InetAddress ip = InetAddress.getLocalHost();
            final String uri = "http://149.165.169.102:5000/services/register";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            params.add("name", "search");
            params.add("uri", "http://" + System.getProperty("SEARCH_IP") +":8080");
            System.out.println("system property:" + System.getProperty("SEARCH_IP"));
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(uri, request, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MovieList searchByKeyword(@NonNull String key) {
        final String uri = String.format(env.getProperty("url.url_searchBykeyword")+"query=%s", key);

        return restApiCall(uri);
    }

    public MovieList searchTopRated() {
        final String uri = env.getProperty("url.urlTopRated");
        return restApiCall(uri);
    }

    public MovieList restApiCall(String uri) {
//        RestTemplate restTemplate = new RestTemplate();
        try {
            SearchMovieList result = restTemplate.getForObject(uri, SearchMovieList.class);

            List<Movies> movies = result.getResults().stream().map(movieListMapper::toMovie).collect(Collectors.toList());
            return new MovieList(movies);
        }  catch (Exception ex) {
            System.out.print("Request failed: " + ex.getMessage());
        }
        return null;
    }
}