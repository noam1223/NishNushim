package com.example.nishnushim.nishnushFragments.fragments_restaurant_profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nishnushim.R;
import com.example.nishnushim.adapters.RestaurantDetailsAdapter;
import com.example.nishnushim.adapters.RestaurantMenuAdapter;
import com.example.nishnushim.adapters.SubTitleAdapter;


public class MenuRestaurantFragment extends Fragment {


    RecyclerView subTitlesRecyclerView, menuRestaurantRecyclerView;
    RecyclerView.Adapter subTitleAdapter, menuRestaurantAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_restaurant, container, false);




        initializeMenuRestaurantRecyclerView();
        initializeSubTitlesRecyclerView();

        return view;
    }



    private void initializeSubTitlesRecyclerView() {

        subTitlesRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, true);
        subTitlesRecyclerView.setLayoutManager(layoutManager);
        subTitleAdapter = new SubTitleAdapter();
        subTitlesRecyclerView.setAdapter(subTitleAdapter);

    }




    private void initializeMenuRestaurantRecyclerView() {

        menuRestaurantRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, true);
        menuRestaurantRecyclerView.setLayoutManager(layoutManager);
        subTitleAdapter = new RestaurantMenuAdapter();
        subTitlesRecyclerView.setAdapter(menuRestaurantAdapter);

    }


}