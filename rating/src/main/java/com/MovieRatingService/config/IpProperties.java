package com.MovieRatingService.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "url")
public class IpProperties {
    private String RATING_IP;

    public String getRATING_IP() {
        return RATING_IP;
    }

    public void setRATING_IP(String RATING_IP) {
        this.RATING_IP = RATING_IP;
    }
}

