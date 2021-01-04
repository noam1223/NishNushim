package com.example.nishnushim.helpclasses;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {

    String name;
    MyAddress myAddress;
    List<AreasForDelivery> areasForDeliveries;
    String phoneNumber;
    List<String> openHour, closeHour;
    String dateOfAdd;

    //TODO: DELETE DELIVERY TIME BECAUSE I NEED TO TAKE FROM LIST OF AREA FOR DELIVERIES
    String deliveryTime;

    Uri logoUri;
    Uri profileImageUri;
    List<CreditsRestaurant> creditsRestaurants = new ArrayList<>();
    boolean kosher, discount;
    List<Integer> classificationList = new ArrayList<>();
    Menu menu;

    public Restaurant() {

    }


    public Restaurant(String name, MyAddress myAddress, List<AreasForDelivery> areasForDeliveries, String phoneNumber, List<String> openHour, List<String> closeHour, String dateOfAdd, String deliveryTime, Uri logoUri, Uri profileImageUri, boolean kosher, boolean discount, List<Integer> classificationList, Menu menu) {
        this.name = name;
        this.myAddress = myAddress;
        this.areasForDeliveries = areasForDeliveries;
        this.phoneNumber = phoneNumber;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.dateOfAdd = dateOfAdd;
        this.deliveryTime = deliveryTime;
        this.logoUri = logoUri;
        this.profileImageUri = profileImageUri;
        this.kosher = kosher;
        this.discount = discount;
        this.classificationList = classificationList;
        this.menu = menu;
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

    public List<AreasForDelivery> getAreasForDeliveries() {
        return areasForDeliveries;
    }

    public void setAreasForDeliveries(List<AreasForDelivery> areasForDeliveries) {
        this.areasForDeliveries = areasForDeliveries;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getOpenHour() {
        return openHour;
    }

    public void setOpenHour(List<String> openHour) {
        this.openHour = openHour;
    }

    public List<String> getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(List<String> closeHour) {
        this.closeHour = closeHour;
    }

    public String getDateOfAdd() {
        return dateOfAdd;
    }

    public void setDateOfAdd(String dateOfAdd) {
        this.dateOfAdd = dateOfAdd;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Uri getLogoUri() {
        return logoUri;
    }

    public void setLogoUri(Uri logoUri) {
        this.logoUri = logoUri;
    }

    public Uri getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(Uri profileImageUri) {
        this.profileImageUri = profileImageUri;
    }

    public List<CreditsRestaurant> getCreditsRestaurants() {
        return creditsRestaurants;
    }

    public void setCreditsRestaurants(List<CreditsRestaurant> creditsRestaurants) {
        this.creditsRestaurants = creditsRestaurants;
    }

    public boolean isKosher() {
        return kosher;
    }

    public void setKosher(boolean kosher) {
        this.kosher = kosher;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public List<Integer> getClassificationList() {
        return classificationList;
    }

    public void setClassificationList(List<Integer> classificationList) {
        this.classificationList = classificationList;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
