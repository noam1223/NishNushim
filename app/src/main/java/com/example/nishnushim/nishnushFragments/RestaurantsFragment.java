package com.example.nishnushim.nishnushFragments;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nishnushim.R;
import com.example.nishnushim.adapters.RestaurantDetailsAdapter;
import com.example.nishnushim.adapters.RestaurantTypeAdapter;
import com.example.nishnushim.helpclasses.MyAddress;
import com.example.nishnushim.helpclasses.Restaurant;

import java.util.ArrayList;
import java.util.List;


public class RestaurantsFragment extends Fragment {

    TextView numberOfOpenRestaurantsTextView, avgDeliveryTimeTextView;
    RecyclerView restaurantDetailRecyclerView;
    RecyclerView.Adapter restaurantDetailAdapter;
    List<Restaurant> restaurants = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);

        restaurantDetailRecyclerView = view.findViewById(R.id.restaurant_details_recycler_view_restaurants_fragment);

        MyAddress myAddress = new MyAddress("באר שבע", "וולפסון", "12");
        MyAddress myAddress1 = new MyAddress("תל אביב", "דיזינגוף", "7");
        MyAddress myAddress2 = new MyAddress("באר שבע", "וינגייט", "14");
        Restaurant restaurant = new Restaurant();
        restaurant.setMyAddress(myAddress);
        restaurant.setName("המבורגר");
        restaurant.setAmountOfMoney(50);
        restaurant.setDeliveryTime("40-50");



        Restaurant restaurant1 = new Restaurant();
        restaurant1.setMyAddress(myAddress1);
        restaurant1.setName("סיני");
        restaurant1.setAmountOfMoney(20);
        restaurant1.setDeliveryTime("20-30");



        Restaurant restaurant2 = new Restaurant();
        restaurant2.setMyAddress(myAddress2);
        restaurant2.setName("קמפאי");
        restaurant2.setAmountOfMoney(10);
        restaurant2.setDeliveryTime("10-20");

        restaurants.add(restaurant);
        restaurants.add(restaurant1);
        restaurants.add(restaurant2);

        initializeRestaurantsDetailsRecyclerView();

        return view;
    }



    private void initializeRestaurantsDetailsRecyclerView() {

        restaurantDetailRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, true);
        restaurantDetailRecyclerView.setLayoutManager(layoutManager);
        restaurantDetailAdapter = new RestaurantDetailsAdapter(getContext(), restaurants);
        restaurantDetailRecyclerView.setAdapter(restaurantDetailAdapter);

    }


}