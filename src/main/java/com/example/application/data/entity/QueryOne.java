package com.example.application.data.entity;

public class QueryOne {
    String year;
    Float average_revenue;
    String genre;

    QueryOne() {

    }

    public QueryOne(String year, Float average_revenue, String genre) {
        this.year = year;
        this.average_revenue = average_revenue;
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Float getAverage_revenue() {
        return average_revenue;
    }

    public void setAverage_revenue(Float average_revenue) {
        this.average_revenue = average_revenue;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
