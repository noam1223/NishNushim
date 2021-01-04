package com.example.nishnushim.helpclasses;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dish implements Serializable {

    String name;
    String details;
    List<DishChanges> changes;
    int price;
    Uri image;


    public Dish() {
    }


    public Dish(String name, String details, List<DishChanges> changes, int price) {
        this.name = name;
        this.details = details;
        this.changes = changes = new ArrayList<>();
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<DishChanges> getChanges() {
        return changes;
    }

    public void setChanges(List<DishChanges> changes) {
        this.changes = changes;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}

