package com.example.nishnushim.helpclasses;


import java.io.Serializable;

public class MyAddress implements Serializable {

    String cityName;
    String streetName;
    String houseNumber;

    //ADDED
    String floor;
    String entry;
    String classificationAddress;

    double longitude;
    double latitude;

    //ADDED
    boolean isChosen = false;

    public MyAddress() {
    }

    public MyAddress(String cityName, String streetName, String houseNumber) {
        this.cityName = cityName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;


    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }


    public String fullMyAddress(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cityName).append(", ");
        stringBuilder.append(streetName).append(" ");

        if (houseNumber != null){
            stringBuilder.append(houseNumber);
        }

        return stringBuilder.toString();

    }


    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getClassificationAddress() {
        return classificationAddress;
    }

    public void setClassificationAddress(String classificationAddress) {
        this.classificationAddress = classificationAddress;
    }
}
