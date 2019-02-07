package com.search.models;

import java.util.List;

public class MovieDetails {
    private boolean adult;
    private List<Genre> genres;
    private int id;
    private String original_language;
    private String original_title;
    private String overview;
    private String poster_path;
    private String release_date;
    private int runtime;
    private String status;
    private String tagline;
    private String title;
    private float vote_average;
    private int vote_count;
    private List<Cast> cast;
    private List<Crew> crew;
    private boolean isSeen;
    private float selfRating;

    public boolean isAdult() {
        return adult;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public float getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
