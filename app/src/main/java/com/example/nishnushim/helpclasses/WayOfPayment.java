package com.example.nishnushim.helpclasses;

import java.io.Serializable;

public class WayOfPayment implements Serializable {

    public enum  WayOfPaymentEnum{
        CASH,
        CREDIT
    }

    public WayOfPaymentEnum wayOfPaymentEnum;
    CreditCard creditCard;
    int amountToPay = 0;


    public WayOfPayment() {
    }

    public WayOfPayment(WayOfPaymentEnum wayOfPaymentEnum, CreditCard creditCard, int amountToPay) {
        this.wayOfPaymentEnum = wayOfPaymentEnum;
        this.creditCard = creditCard;
        this.amountToPay = amountToPay;
    }

    public WayOfPaymentEnum getWayOfPaymentEnum() {
        return wayOfPaymentEnum;
    }

    public void setWayOfPaymentEnum(WayOfPaymentEnum wayOfPaymentEnum) {
        this.wayOfPaymentEnum = wayOfPaymentEnum;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public int getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(int amountToPay) {
        this.amountToPay = amountToPay;
    }
}
