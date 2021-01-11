package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import android.widget.TextView;

import com.example.nishnushim.adapters.RestaurantDetailsAdapter;
import com.example.nishnushim.helpclasses.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    TextView resultTextView;
    ImageButton backImgBtn;

    RecyclerView resultsRestaurantsDetailsListView;
    RecyclerView.Adapter resultRestaurantAdapter;

    List<Restaurant> restaurants = new ArrayList<>();
    List<String> keys = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        resultTextView = findViewById(R.id.result_text_view_search_result_activity);
        backImgBtn = findViewById(R.id.back_img_btn_search_result_activity);
        resultsRestaurantsDetailsListView = findViewById(R.id.restaurant_details_results_recycler_view_search_result_activity);

        initializeRestaurantsDetailsRecyclerView();

    }



    private void initializeRestaurantsDetailsRecyclerView() {

        resultsRestaurantsDetailsListView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        resultsRestaurantsDetailsListView.setLayoutManager(layoutManager);
        resultRestaurantAdapter = new RestaurantDetailsAdapter(this, restaurants, keys);
        resultsRestaurantsDetailsListView.setAdapter(resultRestaurantAdapter);

    }
}