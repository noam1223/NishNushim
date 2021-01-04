package com.example.nishnushim.helpclasses;

import java.io.Serializable;

public class DishChanges implements Serializable {


    String change;
    int price;


    public DishChanges() {
    }

    public DishChanges(String change, int price) {
        this.change = change;
        this.price = price;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

