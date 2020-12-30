package com.example.nishnushim.helpclasses;

import java.io.Serializable;

public class Address implements Serializable {

    String cityName;
    String streetName;
    String houseNumber;
    boolean chosenAddress = true;

    //LON LAN - x, y by earth


    public Address() {
    }

    public Address(String cityName, String streetName, String houseNumber) {
        this.cityName = cityName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }


    public Address(String cityName, String streetName, String houseNumber, boolean chosenAddress) {
        this.cityName = cityName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.chosenAddress = chosenAddress;
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

    public boolean isChosenAddress() {
        return chosenAddress;
    }

    public void setChosenAddress(boolean chosenAddress) {
        this.chosenAddress = chosenAddress;
    }
}
