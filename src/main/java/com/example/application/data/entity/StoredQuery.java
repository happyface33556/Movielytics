package com.example.application.data.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class StoredQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    private String actors;
    private String genre;
    private LocalDate startDate;
    private LocalDate endDate;
    private String language;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoredQuery(String title, String actors, String genre, LocalDate startDate, LocalDate endDate, String language) {
        this.title = title;
        this.actors = actors;
        this.genre = genre;
        this.startDate = startDate;
        this.endDate = endDate;
        this.language = language;
    }

    public StoredQuery() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
