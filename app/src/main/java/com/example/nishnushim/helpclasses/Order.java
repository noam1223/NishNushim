package com.example.nishnushim.helpclasses;

import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.DishChanges;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    int orderNumber;
    Classification order;
    String wayOfDelivery;
    String noteForDelivery;
    int sumOfOrder = 0;
    int numOfCulture = 1;
    List<DishChanges> sauceChanges = new ArrayList<>();
    MyAddress addressToDeliver;
    String date;
    String time;
    String costumerName;
    String costumerPhone;
    List<Boolean> cashOrCredit = new ArrayList<>(); // FALSE - CASH , TRUE - CREDIT



    public Order() {
    }

    public Classification getOrder() {
        return order;
    }

    public void setOrder(Classification order) {
        this.order = order;
    }

    public String getWayOfDelivery() {
        return wayOfDelivery;
    }

    public void setWayOfDelivery(String wayOfDelivery) {
        this.wayOfDelivery = wayOfDelivery;
    }

    public int getSumOfOrder() {

        if (sumOfOrder == 0){
            calculateOrderPrice();
        }

        return sumOfOrder;
    }

    public void setSumOfOrder(int sumOfOrder) {
        this.sumOfOrder = sumOfOrder;
    }

    public int getNumOfCulture() {
        return numOfCulture;
    }

    public void setNumOfCulture(int numOfCulture) {
        this.numOfCulture = numOfCulture;
    }

    public List<DishChanges> getSauceChanges() {
        return sauceChanges;
    }

    public void setSauceChanges(List<DishChanges> sauceChanges) {
        this.sauceChanges = sauceChanges;
    }

    public void calculateOrderPrice(){

        sumOfOrder = 0;

        for (int i = 0; i < order.getDishList().size(); i++) {
            sumOfOrder += order.getDishList().get(i).getPrice();
        }

        for (int i = 0; i < sauceChanges.size(); i++) {
            sumOfOrder += sauceChanges.get(i).getPrice();
        }

    }

    public MyAddress getAddressToDeliver() {
        return addressToDeliver;
    }

    public void setAddressToDeliver(MyAddress addressToDeliver) {
        this.addressToDeliver = addressToDeliver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public String getCostumerPhone() {
        return costumerPhone;
    }

    public void setCostumerPhone(String costumerPhone) {
        this.costumerPhone = costumerPhone;
    }


    public String getNoteForDelivery() {
        return noteForDelivery;
    }

    public void setNoteForDelivery(String noteForDelivery) {
        this.noteForDelivery = noteForDelivery;
    }

    public List<Boolean> getCashOrCredit() {
        return cashOrCredit;
    }

    public void setCashOrCredit(List<Boolean> cashOrCredit) {
        this.cashOrCredit = cashOrCredit;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", order=" + order +
                ", wayOfDelivery='" + wayOfDelivery + '\'' +
                ", noteForDelivery='" + noteForDelivery + '\'' +
                ", sumOfOrder=" + sumOfOrder +
                ", numOfCulture=" + numOfCulture +
                ", sauceChanges=" + sauceChanges +
                ", addressToDeliver=" + addressToDeliver +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", costumerName='" + costumerName + '\'' +
                ", costumerPhone='" + costumerPhone + '\'' +
                ", cashOrCredit=" + cashOrCredit +
                '}';
    }
}
