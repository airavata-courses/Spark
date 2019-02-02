package com.search.models;

public class Movies {
    private int movie_id;
    private String poster_path;
    private String title;
    private int vote_count;
    private String original_language;
    private String release_date;
    private float vote_average;

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getTitle() {
        return title;
    }

    public int getVote_count() {
        return vote_count;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getRelease_date() {
        return release_date;
    }

    public float getVote_average() {
        return vote_average;
    }
}
