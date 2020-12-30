package com.example.nishnushim.helpclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    String name;
    String phoneNumber;
    List<Address> addresses = new ArrayList<>();

    public User() {
    }

    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getChosenAddressString() {

        for (int i = 0; i < this.getAddresses().size(); i++) {

            if (this.getAddresses().get(i).isChosenAddress()) {
                return "" + getAddresses().get(i).getCityName() + ", " + getAddresses().get(i).getStreetName() +
                        " " + getAddresses().get(i).getHouseNumber();
            }
        }

        return "אנא הכנס כתובת למשלוח";
    }
}
