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
import android.widget.Toast;

import com.example.nishnushim.R;
import com.example.nishnushim.adapters.RestaurantDetailsAdapter;
import com.example.nishnushim.adapters.RestaurantTypeAdapter;
import com.example.nishnushim.helpclasses.MyAddress;
import com.example.nishnushim.helpclasses.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class RestaurantsFragment extends Fragment {

    TextView numberOfOpenRestaurantsTextView, avgDeliveryTimeTextView;
    RecyclerView restaurantDetailRecyclerView;
    RecyclerView.Adapter restaurantDetailAdapter;


    List<Restaurant> restaurants = new ArrayList<>();
    List<String> keys = new ArrayList<>();

    FirebaseFirestore db;


    public RecyclerView.Adapter getRestaurantDetailAdapter() {
        return restaurantDetailAdapter;
    }

    public RestaurantsFragment(List<Restaurant> restaurants, List<String> keys) {
        this.restaurants = restaurants;
        this.keys = keys;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);

        restaurantDetailRecyclerView = view.findViewById(R.id.restaurant_details_recycler_view_restaurants_fragment);
        db = FirebaseFirestore.getInstance();

        //TODO: ADD ON PRODUCING LIST OF RESTAURANTS


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