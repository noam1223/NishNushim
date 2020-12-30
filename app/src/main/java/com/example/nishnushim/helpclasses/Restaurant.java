package com.example.nishnushim.helpclasses;

import android.net.Uri;

import java.io.Serializable;

public class Restaurant implements Serializable {

    String name;
    MyAddress myAddress;
    String phoneNumber;
    String openHour, closeHour;
    String deliveryTime;
    Uri photoProfile;
    int amountOfMoney;


    public Restaurant() {
    }


    public Restaurant(String name, MyAddress myAddress, String phoneNumber, String openHour, String closeHour, String deliveryTime, int amountOfMoney) {
        this.name = name;
        this.myAddress = myAddress;
        this.phoneNumber = phoneNumber;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.amountOfMoney = amountOfMoney;
        this.deliveryTime = deliveryTime;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyAddress getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(MyAddress myAddress) {
        this.myAddress = myAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOpenHour() {
        return openHour;
    }

    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    public String getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(String closeHour) {
        this.closeHour = closeHour;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public Uri getPhotoProfile() {
        return photoProfile;
    }

    public void setPhotoProfile(Uri photoProfile) {
        this.photoProfile = photoProfile;
    }
}
