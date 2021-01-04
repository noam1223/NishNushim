package com.example.nishnushim.helpclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable {

    List<Classification> classifications = new ArrayList<>();

    public Menu() {
    }

    public Menu(List<Classification> classifications) {
        this.classifications = classifications;
    }

    public List<Classification> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<Classification> classifications) {
        this.classifications = classifications;
    }

}
