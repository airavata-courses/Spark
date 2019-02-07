package com.search.models;

public class Cast {
    private int cast_id;
    private String character;
    private int gender;
    private String name;
    private String profile_path;

    public int getCast_id() {
        return cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public int getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
