package com.example.nishnushim.helpclasses.helpInterfaces;

import com.example.nishnushim.helpclasses.Dish;

public interface CartListener {
    void addDishToCart(Dish dish);
    void removeDishFromCart(Dish dish);
    void updateDishLongClicked(int ADAPTER_TAG, int dishPosition);
    void addChangesDishCart(Dish dish);
    void updateCartSum();
}
