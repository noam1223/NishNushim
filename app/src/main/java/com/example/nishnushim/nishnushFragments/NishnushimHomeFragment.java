package com.example.nishnushim.nishnushFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nishnushim.R;
import com.example.nishnushim.adapters.RestaurantDetailsAdapter;
import com.example.nishnushim.helpclasses.Restaurant;

import java.util.ArrayList;
import java.util.List;


public class NishnushimHomeFragment extends Fragment {

    RecyclerView restaurantDetailRecyclerView;
    RecyclerView.Adapter restaurantDetailAdapter;


    List<Restaurant> restaurants;
    List<String> keys;


    public NishnushimHomeFragment(List<Restaurant> restaurants, List<String> keys) {
        this.restaurants = restaurants;
        this.keys = keys;
    }

    public RecyclerView.Adapter getRestaurantDetailAdapter() {
        return restaurantDetailAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nishnushim_home, container, false);
        restaurantDetailRecyclerView = view.findViewById(R.id.restaurant_details_recycler_view_nishnushim_home_fragment);

        initializeRestaurantsDetailsRecyclerView();


        return view;
    }




    private void initializeRestaurantsDetailsRecyclerView() {

        restaurantDetailRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        restaurantDetailRecyclerView.setLayoutManager(layoutManager);
        restaurantDetailAdapter = new RestaurantDetailsAdapter(getContext(), restaurants, keys);
        restaurantDetailRecyclerView.setAdapter(restaurantDetailAdapter);

    }
}