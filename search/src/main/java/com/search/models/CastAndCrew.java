package com.search.models;

import java.util.List;

public class CastAndCrew {
    private int id;
    private List<Cast> cast;
    private List<Crew> crew;

    public int getId() {
        return id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
