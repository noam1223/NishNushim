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

        RestaurantTypeClass chickenRestaurant = new RestaurantTypeClass(context.getString(R.string.NISHNUSHIM), TypeOfRestaurantEnum.CHICKEN, R.drawable.ic_chicken_type, R.drawable.ic_chicken_type);
        RestaurantTypeClass pizzaRestaurant = new RestaurantTypeClass("פיצריות", TypeOfRestaurantEnum.PIZZA, R.drawable.ic_pizza_not_clicked, R.drawable.ic_pizza_clicked);
        RestaurantTypeClass burgerRestaurant = new RestaurantTypeClass("המבורגרים", TypeOfRestaurantEnum.BURGER, R.drawable.ic_burger_not_clicked, R.drawable.ic_burger_clicked);
        RestaurantTypeClass thaiRestaurant = new RestaurantTypeClass("תאילנדי", TypeOfRestaurantEnum.THAI, R.drawable.ic_thai_food_not_clicked, R.drawable.ic_thai_food_clicked);
        RestaurantTypeClass meatRestaurant = new RestaurantTypeClass("בשרים", TypeOfRestaurantEnum.MEAT, R.drawable.ic_meat_not_clicked, R.drawable.ic_meat_clicked);
        RestaurantTypeClass seaFoodRestaurant = new RestaurantTypeClass("דגים/פירות ים", TypeOfRestaurantEnum.SEA_FOOD, R.drawable.ic_fish_sea_food_not_clicked, R.drawable.ic_fish_sea_food_clicked);
        RestaurantTypeClass saladRestaurant = new RestaurantTypeClass("מרקים", TypeOfRestaurantEnum.SOUP, R.drawable.ic_soup_not_clicked, R.drawable.ic_soup_clicked);
        RestaurantTypeClass sandwichRestaurant = new RestaurantTypeClass("סנדוויצ׳ים", TypeOfRestaurantEnum.SANDWICH, R.drawable.ic_sandwich_not_clicked, R.drawable.ic_sandwich_clicked);
        RestaurantTypeClass dessertRestaurant = new RestaurantTypeClass("קינוחים", TypeOfRestaurantEnum.DESSERT, R.drawable.ic_dessert_not_clicked, R.drawable.ic_dessert_clicked);
        RestaurantTypeClass humusRestaurant = new RestaurantTypeClass("חומוס", TypeOfRestaurantEnum.HUMUS, R.drawable.ic_hummus_not_clicked, R.drawable.ic_hummus_clicked);
        RestaurantTypeClass kosherRestaurant = new RestaurantTypeClass("כשר", TypeOfRestaurantEnum.KOSHER, R.drawable.ic_kosher_not_clicked, R.drawable.ic_kosher_clicked);
        RestaurantTypeClass asianRestaurant = new RestaurantTypeClass("אסייתי", TypeOfRestaurantEnum.ASIAN, R.drawable.ic_asian_food_not_clicked, R.drawable.ic_asian_food_clicked);
        RestaurantTypeClass italianRestaurant = new RestaurantTypeClass("איטלקי", TypeOfRestaurantEnum.ITALIAN, R.drawable.ic_italian_not_clicked, R.drawable.ic_italian_clicked);
        RestaurantTypeClass mexicanRestaurant = new RestaurantTypeClass("מקסיקני", TypeOfRestaurantEnum.MEXICAN, R.drawable.ic_mexican_not_clicked, R.drawable.ic_mexican_clicked);
        RestaurantTypeClass coffeeShopRestaurant = new RestaurantTypeClass("בתי קפה", TypeOfRestaurantEnum.COFFEE_SHOP, R.drawable.ic_coffee_shop_not_clicked, R.drawable.ic_coffee_cup_clicked);
        RestaurantTypeClass veganRestaurant = new RestaurantTypeClass("טבעוני", TypeOfRestaurantEnum.VEGAN, R.drawable.ic_vegan_not_clicked, R.drawable.ic_vegan_clicked);
        RestaurantTypeClass vegetarianRestaurant = new RestaurantTypeClass("טבעוני", TypeOfRestaurantEnum.VEGETARIAN, R.drawable.ic_vegeterian_not_clicked, R.drawable.ic_vegeterian_clicked);
        RestaurantTypeClass burekasRestaurant = new RestaurantTypeClass("מאפים", TypeOfRestaurantEnum.BUREKAS, R.drawable.ic_bakery_not_clicked, R.drawable.ic_bakery_clicked);
        RestaurantTypeClass iceCreamRestaurant = new RestaurantTypeClass("גלידריות", TypeOfRestaurantEnum.SANDWICH, R.drawable.ic_ice_cream_not_clicked, R.drawable.ic_ice_cream_clicked);


        restaurantTypeClassList.add(chickenRestaurant);
        restaurantTypeClassList.add(pizzaRestaurant);
        restaurantTypeClassList.add(burgerRestaurant);
        restaurantTypeClassList.add(thaiRestaurant);
        restaurantTypeClassList.add(meatRestaurant);
        restaurantTypeClassList.add(seaFoodRestaurant);
        restaurantTypeClassList.add(saladRestaurant);
        restaurantTypeClassList.add(sandwichRestaurant);
        restaurantTypeClassList.add(dessertRestaurant);
        restaurantTypeClassList.add(humusRestaurant);
        restaurantTypeClassList.add(kosherRestaurant);
        restaurantTypeClassList.add(asianRestaurant);
        restaurantTypeClassList.add(italianRestaurant);
        restaurantTypeClassList.add(mexicanRestaurant);
        restaurantTypeClassList.add(coffeeShopRestaurant);
        restaurantTypeClassList.add(veganRestaurant);
        restaurantTypeClassList.add(vegetarianRestaurant);
        restaurantTypeClassList.add(burekasRestaurant);
        restaurantTypeClassList.add(iceCreamRestaurant);


    }


    public List<RestaurantTypeClass> getRestaurantTypeClassList() {
        return restaurantTypeClassList;
    }

}
