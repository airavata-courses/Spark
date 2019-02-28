package com.search.service;

import com.search.mapper.MovieListMapper;
import com.search.models.MovieList;
import com.search.models.Movies;
import com.search.models.SearchMovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchMovieService {

    @Autowired
    private MovieListMapper movieListMapper;

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ZooKeeperServices zooKeeperServices;

    private InetAddress ip;

    @PostConstruct
    public void registerService(){
        try {
            ip = InetAddress.getLocalHost();
            System.out.println("IP address is: " + ip.getHostAddress());
            zooKeeperServices.registerService("http://" + ip.getHostAddress() + ":8080/");
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
        String s = zooKeeperServices.discoverServiceURI("search");
        System.out.println("data :: " + s);
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
