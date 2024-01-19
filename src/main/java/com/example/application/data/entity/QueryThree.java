package com.example.application.data.entity;

public class QueryThree {
    int year;
    float languageOne;
    float languageTwo;
    float languageThree;
    float languageFour;
    float languageFive;

    public QueryThree() {

    }

    public QueryThree(int year, float languageOne, float languageTwo, float languageThree, float languageFour, float languageFive) {
        this.year = year;
        this.languageOne = languageOne;
        this.languageTwo = languageTwo;
        this.languageThree = languageThree;
        this.languageFour = languageFour;
        this.languageFive = languageFive;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getLanguageOne() {
        return languageOne;
    }

    public void setLanguageOne(float languageOne) {
        this.languageOne = languageOne;
    }

    public float getLanguageTwo() {
        return languageTwo;
    }

    public void setLanguageTwo(float languageTwo) {
        this.languageTwo = languageTwo;
    }

    public float getLanguageThree() {
        return languageThree;
    }

    public void setLanguageThree(float languageThree) {
        this.languageThree = languageThree;
    }

    public float getLanguageFour() {
        return languageFour;
    }

    public void setLanguageFour(float languageFour) {
        this.languageFour = languageFour;
    }

    public float getLanguageFive() {
        return languageFive;
    }

    public void setLanguageFive(float languageFive) {
        this.languageFive = languageFive;
    }
}
