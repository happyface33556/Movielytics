package com.example.application.data.entity;

public class QueryFive {
    String releaseYear;
    String genre;
    int annualBudget;

    public QueryFive() {

    }

    public QueryFive(String year, String genre, int annualBudget) {
        this.releaseYear = year;
        this.genre = genre;
        this.annualBudget = annualBudget;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAnnualBudget() {
        return annualBudget;
    }

    public void setAnnualBudget(int annualBudget) {
        this.annualBudget = annualBudget;
    }
}
