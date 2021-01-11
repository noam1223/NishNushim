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
        RestaurantTypeClass burgerRestaurant = new RestaurantTypeClass("המבורגרים" ,TypeOfRestaurantEnum.BURGER, R.drawable.ic_burger_not_clicked, R.drawable.ic_burger_clicked);
        RestaurantTypeClass sandwichRestaurant = new RestaurantTypeClass("סנדוויצ׳ים" ,TypeOfRestaurantEnum.SANDWICH, R.drawable.ic_sandwich_not_clicked, R.drawable.ic_sandwich_clicked);
        RestaurantTypeClass sushiRestaurant = new RestaurantTypeClass("סושי" ,TypeOfRestaurantEnum.SUSHI, R.drawable.ic_sushi_not_clicked, R.drawable.ic_sushi_clicked);
        RestaurantTypeClass meatRestaurant = new RestaurantTypeClass("בשרים" ,TypeOfRestaurantEnum.MEAT, R.drawable.ic_meat_not_clicked, R.drawable.ic_meat_clicked);
        RestaurantTypeClass burekasRestaurant = new RestaurantTypeClass("בורקס" ,TypeOfRestaurantEnum.BUREKAS, R.drawable.ic_bakery_not_clicked, R.drawable.ic_bakery_clicked);

//        RestaurantTypeClass tortiaRestaurant = new RestaurantTypeClass("טורטייה" ,TypeOfRestaurantEnum.SANDWICH, R.drawable.ic_sandwich, R.drawable.ic_sandwich);

//        RestaurantTypeClass humusRestaurant = new RestaurantTypeClass("חומוס" ,TypeOfRestaurantEnum.SANDWICH, R.drawable.ic_sandwich, R.drawable.ic_sandwich);


//        RestaurantTypeClass jahnunRestaurant = new RestaurantTypeClass("ג׳חנון" ,TypeOfRestaurantEnum.SANDWICH, R.drawable.ic_sandwich, R.drawable.ic_sandwich);


        RestaurantTypeClass soupRestaurant = new RestaurantTypeClass("מרקים" ,TypeOfRestaurantEnum.SOUP, R.drawable.ic_suop_not_clicked, R.drawable.ic_soup_clicked);
        RestaurantTypeClass dessertRestaurant = new RestaurantTypeClass("קינוחים" ,TypeOfRestaurantEnum.SANDWICH, R.drawable.ic_dessert_not_clicked, R.drawable.ic_dessert_clicked);
        RestaurantTypeClass iceCreamRestaurant = new RestaurantTypeClass("גלידריות" ,TypeOfRestaurantEnum.SANDWICH, R.drawable.ic_ice_cream_not_clicked, R.drawable.ic_ice_cream_clicked);
        RestaurantTypeClass fishRestaurant = new RestaurantTypeClass("דגים" ,TypeOfRestaurantEnum.FISH, R.drawable.ic_fish_not_clicked, R.drawable.ic_fish_clicked);
//        RestaurantTypeClass seaFruitRestaurant = new RestaurantTypeClass("פירות ים" ,TypeOfRestaurantEnum.SANDWICH, R.drawable.ic_sandwich, R.drawable.ic_sandwich);

        restaurantTypeClassList.add(chickenRestaurant);
        restaurantTypeClassList.add(burgerRestaurant);
        restaurantTypeClassList.add(sandwichRestaurant);
        restaurantTypeClassList.add(sushiRestaurant);
        restaurantTypeClassList.add(meatRestaurant);
        restaurantTypeClassList.add(burekasRestaurant);
        restaurantTypeClassList.add(soupRestaurant);
        restaurantTypeClassList.add(dessertRestaurant);
        restaurantTypeClassList.add(iceCreamRestaurant);
        restaurantTypeClassList.add(fishRestaurant);


    }



    public List<RestaurantTypeClass> getRestaurantTypeClassList() {
        return restaurantTypeClassList;
    }

}
