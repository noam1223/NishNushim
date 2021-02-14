package com.example.nishnushim.helpUIClass;

public class ChangePizzaType {


    final int id;
    String name;
    int srcImg;


    public ChangePizzaType(int id, String name, int srcImg) {
        this.id = id;
        this.name = name;
        this.srcImg = srcImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSrcImg() {
        return srcImg;
    }

    public void setSrcImg(int srcImg) {
        this.srcImg = srcImg;
    }

    public int getId() {
        return id;
    }
}
