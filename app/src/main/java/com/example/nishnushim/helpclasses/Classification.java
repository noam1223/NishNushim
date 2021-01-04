package com.example.nishnushim.helpclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Classification implements Serializable {

    String classificationName;
    List<Dish> dishList = new ArrayList<>();


    public Classification() {
    }


    public Classification(String classificationName, List<Dish> dishList) {
        this.classificationName = classificationName;
        this.dishList = dishList;
    }


    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }


}

