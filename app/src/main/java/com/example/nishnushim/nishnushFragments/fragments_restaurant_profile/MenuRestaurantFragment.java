package com.example.nishnushim.nishnushFragments.fragments_restaurant_profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.nishnushim.R;
import com.example.nishnushim.adapters.RestaurantMenuAdapter;
import com.example.nishnushim.adapters.SubTitleAdapter;
import com.example.nishnushim.helpclasses.Dish;
import com.example.nishnushim.helpclasses.helpInterfaces.CartListener;
import com.example.nishnushim.helpclasses.helpInterfaces.MenuItemListener;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Restaurant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MenuRestaurantFragment extends Fragment implements MenuItemListener, CartListener {


    ScrollView menuScrollView;
    RecyclerView subTitlesRecyclerView, menuRestaurantRecyclerView;
    RecyclerView.Adapter subTitleAdapter, menuRestaurantAdapter;


    Restaurant restaurant;
    String keyRestaurant;

    Classification cartClassification;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_restaurant, container, false);

        subTitlesRecyclerView = view.findViewById(R.id.sub_titles_recycler_view_menu_restaurant_fragment);
        menuRestaurantRecyclerView = view.findViewById(R.id.menu_recycler_view_restaurant_menu_fragment);
        menuScrollView = view.findViewById(R.id.scroll_view_menu_restaurant_fragment);
        menuScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {



            }
        });


        SharedPreferences sp = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String cartJsonString = sp.getString(getString(R.string.app_name), null);


        if (cartJsonString != null) {
            cartClassification = gson.fromJson(cartJsonString, Classification.class);
        } else {
            cartClassification = new Classification();
            cartClassification.setClassificationName("עגלת קניות");
        }


        if (getArguments() != null) {


            restaurant = (Restaurant) getArguments().getSerializable(getString(R.string.restaurant_detail));
            keyRestaurant = getArguments().getString("key");


            initializeMenuRestaurantRecyclerView();
            initializeSubTitlesRecyclerView();
        }


        return view;
    }


    private void initializeSubTitlesRecyclerView() {

        subTitlesRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        subTitlesRecyclerView.setLayoutManager(layoutManager);
        subTitleAdapter = new SubTitleAdapter(getContext(), restaurant.getMenu().getClassifications(), this);
        subTitlesRecyclerView.setAdapter(subTitleAdapter);

    }


    private void initializeMenuRestaurantRecyclerView() {

        menuRestaurantRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        menuRestaurantRecyclerView.setLayoutManager(layoutManager);
        menuRestaurantAdapter = new RestaurantMenuAdapter(getContext(), restaurant.getMenu(), cartClassification, this);
        menuRestaurantRecyclerView.setAdapter(menuRestaurantAdapter);

    }


    @Override
    public void changeMenuItemPosition(int position) {

        Toast.makeText(getContext(), "MENU ITEM POSITION CHANGE", Toast.LENGTH_SHORT).show();
        menuRestaurantRecyclerView.smoothScrollToPosition(position);
    }


    @Override
    public void addDishToCart(Dish dish) {

        cartClassification.getDishList().add(dish);

    }


    @Override
    public void removeDishFromCart(Dish dish) {

        for (int i = 0; i < cartClassification.getDishList().size(); i++) {

            if (cartClassification.getDishList().get(i).getName().equals(dish.getName())) {

                cartClassification.getDishList().remove(i);
                return;

            }

        }

    }


    @Override
    public void updateDishLongClicked(int ADAPTER_TAG, int dishPosition) {

        ((RestaurantMenuAdapter) menuRestaurantAdapter).updateMenuLongPressed(ADAPTER_TAG, dishPosition);

    }


    @Override
    public void updateCartSum() {

    }


    @Override
    public void onDestroy() {

        if (!cartClassification.getDishList().isEmpty()) {
            SharedPreferences sp = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            Gson gson = new Gson();
            String cartJson = gson.toJson(cartClassification);
            editor.putString("cart", cartJson);
            editor.apply();
        }

        super.onDestroy();
    }



    public void onScrollDownListener() {
        menuScrollView.scrollTo(menuScrollView.getScrollX(), menuScrollView.getScrollY() + 1);
    }



    public boolean onScrollUpListener() {

        if (menuScrollView.getScrollY() > menuScrollView.getChildAt(0).getTop()) {
            menuScrollView.scrollTo(menuScrollView.getScrollX(), menuScrollView.getScrollY() - 1);
            return true;
        }
        return false;
    }
}