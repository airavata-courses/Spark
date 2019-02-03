package com.search.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "url")
public class UrlProperties {
    private String url_searchBykeyword;
    private String url_getById;
    private String applicationKey;
    private String urlTopRated;

    public String getUrl_searchBykeyword() {
        return url_searchBykeyword;
    }

    public String getUrl_getById() {
        return url_getById;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public String getUrlTopRated() {
        return urlTopRated;
    }

    public void setUrl_searchBykeyword(String url_searchBykeyword) {
        this.url_searchBykeyword = url_searchBykeyword;
    }

    public void setUrl_getById(String url_getById) {
        this.url_getById = url_getById;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }

    public void setUrlTopRated(String urlTopRated) {
        this.urlTopRated = urlTopRated;
    }
}
