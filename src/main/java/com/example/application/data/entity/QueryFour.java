package com.example.application.data.entity;

import java.time.LocalDate;

public class QueryFour {
    float spring;
    float summer;
    float autumn;
    float winter;
    int releaseYear;

    public QueryFour() {

    }

    public QueryFour(float spring, float summer, float autumn, float winter, int releaseYear) {
        this.spring = spring;
        this.summer = summer;
        this.autumn = autumn;
        this.winter = winter;
        this.releaseYear = releaseYear;
    }

    public float getSpring() {
        return spring;
    }

    public void setSpring(float spring) {
        this.spring = spring;
    }

    public float getSummer() {
        return summer;
    }

    public void setSummer(float summer) {
        this.summer = summer;
    }

    public float getAutumn() {
        return autumn;
    }

    public void setAutumn(float autumn) {
        this.autumn = autumn;
    }

    public float getWinter() {
        return winter;
    }

    public void setWinter(float winter) {
        this.winter = winter;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
