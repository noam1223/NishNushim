package com.example.nishnushim.helpclasses;

import java.io.Serializable;

public class RecommendationRestaurant implements Serializable {

    //CHANGE FROM NAME TO USER
    private User user;
    String date;
    String creditLetter;
    int creditStar;


    public RecommendationRestaurant() {
    }


    public RecommendationRestaurant(User user, String date, String creditLetter, int creditStar) {
        this.user = user;
        this.date = date;
        this.creditLetter = creditLetter;
        this.creditStar = creditStar;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreditLetter() {
        return creditLetter;
    }

    public void setCreditLetter(String creditLetter) {
        this.creditLetter = creditLetter;
    }

    public int getCreditStar() {
        return creditStar;
    }

    public void setCreditStar(int creditStar) {
        this.creditStar = creditStar;
    }
}
