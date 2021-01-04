package com.example.nishnushim.helpclasses;


import java.io.Serializable;

public class MyAddress implements Serializable {

    String cityName;
    String streetName;
    String houseNumber;

    double longitude;
    double latitude;

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

    public String fullMyAddress(){

        return cityName + ", " + streetName + " " + houseNumber;

    }
}
