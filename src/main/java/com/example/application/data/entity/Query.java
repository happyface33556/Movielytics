package com.example.application.data.entity;

import oracle.sql.DATE;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Query {
    private String title;
    private String actors;
    private String genre;
    private LocalDate startDate;
    private LocalDate endDate;
    private String language;
    private Set<String> output;

    public Query() {

    }

    public Set<String> getOutput() {
        return output;
    }

    public void setOutput(Set<String> output) {
        this.output = output;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Query(String title, String actors, String genre, LocalDate startDate, LocalDate endDate, String language, Set<String> output) {
        this.title = title;
        this.actors = actors;
        this.genre = genre;
        this.startDate = startDate;
        this.endDate = endDate;
        this.language = language;
        this.output = output;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
