package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nishnushim.helpclasses.Restaurant;
import com.example.nishnushim.nishnushFragments.NishnushinAnonymousUserFragment;
import com.example.nishnushim.nishnushFragments.fragments_restaurant_profile.MenuRestaurantFragment;

public class RestaurantProfileHomeActivity extends AppCompatActivity {

    ImageView logoProfileImageView, profilePictureImageView;
    TextView nameRestaurantTextView, openHoursRestaurantTextView, distanceFromUserTextView, deliveryCostTextView, deliveryTimeTextView, minToDeliverTextView, avgCreditsTextView;
    ImageButton addToFavoriteRestaurantListImgBtn, moveToMenuImgBtn, moveToLastOrdersImgBtn, moveToMoreDetailsImgBtn;


    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile_home);


        Intent intent = getIntent();

        if (intent.getSerializableExtra(getBaseContext().getString(R.string.restaurant_detail)) != null){

            restaurant = (Restaurant) intent.getSerializableExtra(getBaseContext().getString(R.string.restaurant_detail));

        }


        logoProfileImageView = findViewById(R.id.logo_image_view_restaurant_profile_home_activity);
        profilePictureImageView = findViewById(R.id.profile_image_view_restaurant_profile_home_activity);


        nameRestaurantTextView = findViewById(R.id.name_restaurant_text_view_restaurant_profile_home_activity);
        openHoursRestaurantTextView = findViewById(R.id.open_close_hours_text_view_restaurant_profile_home_activity);
        distanceFromUserTextView = findViewById(R.id.distance_from_user_restaurant_detail_item);
        deliveryCostTextView = findViewById(R.id.delivery_amount_restaurant_profile_home_activity);
        deliveryTimeTextView = findViewById(R.id.delivery_time_restaurant_profile_home_activity);
        minToDeliverTextView = findViewById(R.id.min_to_deliver_amount_restaurant_profile_home_activity);
        avgCreditsTextView = findViewById(R.id.avg_restaurant_feedback_text_view_restaurant_profile_home_activity);


        addToFavoriteRestaurantListImgBtn = findViewById(R.id.restaurant_favorite_image_btn_restaurant_profile_home_activity);
        moveToMenuImgBtn = findViewById(R.id.menu_btn_view_restaurant_profile_home_activity);
        moveToLastOrdersImgBtn = findViewById(R.id.last_orders_made_btn_btn_restaurant_profile_home_activity);
        moveToMoreDetailsImgBtn = findViewById(R.id.more_details_btn_view_restaurant_profile_home_activity);


        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_restaurant_profile_home_activity, new MenuRestaurantFragment()).commit();



    }
}