package com.search.models;

public class Crew {
    private String credit_id;
    private String department;
    private String job;
    private String name;
    private String profile_path;

    public String getCredit_id() {
        return credit_id;
    }

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
