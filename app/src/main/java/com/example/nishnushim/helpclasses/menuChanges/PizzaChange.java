package com.example.nishnushim.helpclasses.menuChanges;

import java.io.Serializable;


public class PizzaChange implements Serializable {

    int id;
    String name;
    int cost = -1;


    boolean leftSide = false;
    boolean rightSide = false;
    boolean bothSides = false;


    public PizzaChange() {
    }


    public PizzaChange(int id) {
        this.id = id;
    }

    public PizzaChange(int id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


    public boolean isLeftSide() {
        return leftSide;
    }

    public void setLeftSide(boolean leftSide) {
        this.leftSide = leftSide;
    }

    public boolean isRightSide() {
        return rightSide;
    }

    public void setRightSide(boolean rightSide) {
        this.rightSide = rightSide;
    }

    public boolean isBothSides() {
        return bothSides;
    }

    public void setBothSides(boolean bothSides) {
        this.bothSides = bothSides;
    }
}
