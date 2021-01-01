package com.example.nishnushim.helpclasses;

import java.io.Serializable;
import java.util.List;

public class Dish implements Serializable {


    String name;
    String details;
    List<Changes> changes;
    int price;


    public Dish() {
    }


    public Dish(String name, String details, List<Changes> changes, int price) {
        this.name = name;
        this.details = details;
        this.changes = changes;
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

    public List<Changes> getChanges() {
        return changes;
    }

    public void setChanges(List<Changes> changes) {
        this.changes = changes;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }




    private class Changes implements Serializable{


        String change;
        int price;


        public Changes() {
        }

        public Changes(String change, int price) {
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

}
