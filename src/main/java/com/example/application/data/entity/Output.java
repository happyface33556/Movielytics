package com.example.application.data.entity;

public class Output {
    private String title;
    private String name;
    private String budget;
    private String revenue;
    private String releaseDate;
    private Float vote_Average;

    public String getName() {
        return name;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Float getVote_Average() {
        return vote_Average;
    }

    public void setVote_Average(Float vote_Average) {
        this.vote_Average = vote_Average;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Output() {

    }

    public Output(String title, String actorName, String budget, String revenue, String releaseDate, Float vote_Average) {
        this.title = title;
        this.name = actorName;
        this.budget = budget;
        this.revenue = revenue;
        this.releaseDate = releaseDate;
        this.vote_Average = vote_Average;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
