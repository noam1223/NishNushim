package com.example.nishnushim.helpUIClass;

import android.content.Context;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.RestaurantTypeClass;
import com.example.nishnushim.helpclasses.enums.TypeOfRestaurantEnum;

import java.util.ArrayList;
import java.util.List;

public class RestaurantTypeHelper {

    List<RestaurantTypeClass> restaurantTypeClassList;

    public RestaurantTypeHelper(Context context) {

        restaurantTypeClassList = new ArrayList<>();

        RestaurantTypeClass chickenRestaurant = new RestaurantTypeClass(context.getString(R.string.NISHNUSHIM) ,TypeOfRestaurantEnum.CHICKEN, R.drawable.ic_chicken_type, R.drawable.ic_chicken_type);
        RestaurantTypeClass burgerRestaurant = new RestaurantTypeClass("המבורגרים" ,TypeOfRestaurantEnum.BURGER, R.drawable.ic_burger_not_clicked, R.drawable.ic_burger_not_clicked);
        RestaurantTypeClass sandwichRestaurant = new RestaurantTypeClass("סנדוויצ׳ים" ,TypeOfRestaurantEnum.SANDWICH, R.drawable.ic_sandwich, R.drawable.ic_sandwich);

        restaurantTypeClassList.add(chickenRestaurant);
        restaurantTypeClassList.add(burgerRestaurant);
        restaurantTypeClassList.add(sandwichRestaurant);



    }

    public List<RestaurantTypeClass> getRestaurantTypeClassList() {
        return restaurantTypeClassList;
    }

}
