package com.example.application.data.entity;

public class QueryTwo {
    String genre;
    int year;
    float yearOverYearPercentageChange;

    public QueryTwo() {

    }

    public QueryTwo(String genre, int year, float yearOverYearPercentageChange) {
        this.genre = genre;
        this.year = year;
        this.yearOverYearPercentageChange = yearOverYearPercentageChange;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getYearOverYearPercentageChange() {
        return yearOverYearPercentageChange;
    }

    public void setYearOverYearPercentageChange(float yearOverYearPercentageChange) {
        this.yearOverYearPercentageChange = yearOverYearPercentageChange;
    }
}
