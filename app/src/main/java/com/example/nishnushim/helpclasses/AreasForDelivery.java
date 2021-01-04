package com.example.nishnushim.helpclasses;

import java.io.Serializable;

public class AreasForDelivery implements Serializable {

    String areaName;
    int deliveryCost;
    int minToDeliver;
    int timeOfDelivery;


    public AreasForDelivery() {
    }


    public AreasForDelivery(String areaName, int deliveryCost, int minToOrder, int timeOfDelivery) {
        this.areaName = areaName;
        this.deliveryCost = deliveryCost;
        this.minToDeliver = minToOrder;
        this.timeOfDelivery = timeOfDelivery;
    }


    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public int getMinToDeliver() {
        return minToDeliver;
    }

    public void setMinToDeliver(int minToDeliver) {
        this.minToDeliver = minToDeliver;
    }

    public int getTimeOfDelivery() {
        return timeOfDelivery;
    }

    public void setTimeOfDelivery(int timeOfDelivery) {
        this.timeOfDelivery = timeOfDelivery;
    }
}
