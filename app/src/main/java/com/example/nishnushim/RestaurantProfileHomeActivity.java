package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nishnushim.helpclasses.Restaurant;
import com.example.nishnushim.nishnushFragments.NishnushinAnonymousUserFragment;
import com.example.nishnushim.nishnushFragments.fragments_restaurant_profile.MenuRestaurantFragment;
import com.example.nishnushim.nishnushFragments.fragments_restaurant_profile.RestaurantProfileDetailsFragment;

public class RestaurantProfileHomeActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView logoProfileImageView, profilePictureImageView;
    TextView nameRestaurantTextView, openHoursRestaurantTextView, distanceFromUserTextView, deliveryCostTextView, deliveryTimeTextView, minToDeliverTextView, avgCreditsTextView;
    ImageButton addToFavoriteRestaurantListImgBtn;

    TextView moveToMenuTextView, moveToLastOrdersTextView, moveToMoreDetailsTextView;
    ImageView moveToMenuImgBtn, moveToLastOrdersImgBtn, moveToMoreDetailsImgBtn;


    Restaurant restaurant;
    String restaurantKey;
    Fragment restaurantDetailsFragment;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile_home);


        Intent intent = getIntent();

        if (intent.getSerializableExtra(getBaseContext().getString(R.string.restaurant_detail)) != null) {

            restaurant = (Restaurant) intent.getSerializableExtra(getBaseContext().getString(R.string.restaurant_detail));
            restaurantKey = intent.getStringExtra("key");

            restaurantDetailsFragment = new MenuRestaurantFragment();
            bundle.putSerializable(getString(R.string.restaurant_detail), restaurant);
            bundle.putString("key", restaurantKey);
            restaurantDetailsFragment.setArguments(bundle);

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


            moveToMenuTextView = findViewById(R.id.sub_title_menu_text_view_restaurant_profile_home_activity);
            moveToLastOrdersTextView = findViewById(R.id.last_orders_title_menu_text_view_restaurant_profile_home_activity);
            moveToMoreDetailsTextView = findViewById(R.id.more_details_title_menu_text_view_restaurant_profile_home_activity);


            moveToMenuImgBtn = findViewById(R.id.menu_image_view_restaurant_profile_home_activity);
            moveToLastOrdersImgBtn = findViewById(R.id.last_orders_image_view_restaurant_profile_home_activity);
            moveToMoreDetailsImgBtn = findViewById(R.id.more_details_image_view_restaurant_profile_home_activity);

            moveToMenuImgBtn.setOnClickListener(this);
            moveToLastOrdersImgBtn.setOnClickListener(this);
            moveToMoreDetailsImgBtn.setOnClickListener(this);



            nameRestaurantTextView.setText(restaurant.getName());

            //TODO: SET HOUR BY DAY
            openHoursRestaurantTextView.setText(restaurant.getOpenHour().get(0) + "-" + restaurant.getCloseHour().get(0));


            //TODO: CALCULATE THE DISTANCE FROM CURRENT USER
            distanceFromUserTextView.setText("2");


            //TODO: SET THE DELIVERY COST BY THE USER ADDRESS
            deliveryCostTextView.setText(String.valueOf(restaurant.getAreasForDeliveries().get(0).getDeliveryCost()));


            //TODO: SET THE TIME OF DELIVERY BY THE USER ADDRESS
            deliveryTimeTextView.setText(String.valueOf(restaurant.getAreasForDeliveries().get(0).getTimeOfDelivery()));


            //TODO: SET THE MIN TO DELIVERY BY THE USER ADDRESS
            minToDeliverTextView.setText(String.valueOf(restaurant.getAreasForDeliveries().get(0).getMinToDeliver()));


            //TODO: SET THE AVG OF RECOMMENDATION
//            avgCreditsTextView.setText(restaurant.getCreditsRestaurants().get(0).getCreditStar());




            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_restaurant_profile_home_activity, restaurantDetailsFragment).commit();

        }

    }




    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.menu_image_view_restaurant_profile_home_activity){

            if (!(restaurantDetailsFragment instanceof MenuRestaurantFragment)) {
                restaurantDetailsFragment = new MenuRestaurantFragment();
                bundle.putSerializable(getString(R.string.restaurant_detail), restaurant);
                bundle.putString("key", restaurantKey);
                restaurantDetailsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_restaurant_profile_home_activity, restaurantDetailsFragment).commit();


                moveToMenuImgBtn.setImageDrawable(ContextCompat.getDrawable(this ,R.drawable.ic_oval_fullfill));
                moveToMenuTextView.setVisibility(View.VISIBLE);

                moveToLastOrdersImgBtn.setImageDrawable(ContextCompat.getDrawable(this ,R.drawable.ic_oval_empty));
                moveToLastOrdersTextView.setVisibility(View.INVISIBLE);

                moveToMoreDetailsImgBtn.setImageDrawable(ContextCompat.getDrawable(this ,R.drawable.ic_oval_empty));
                moveToMoreDetailsTextView.setVisibility(View.INVISIBLE);
            }

        } else if (id == R.id.last_orders_image_view_restaurant_profile_home_activity){

            //TODO: CREATE LAST ORDERS FRAGMENT
//            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_restaurant_profile_home_activity, new MenuRestaurantFragment()).commit();


            moveToMenuImgBtn.setImageDrawable(ContextCompat.getDrawable(this ,R.drawable.ic_oval_empty));
            moveToMenuTextView.setVisibility(View.INVISIBLE);

            moveToLastOrdersImgBtn.setImageDrawable(ContextCompat.getDrawable(this ,R.drawable.ic_oval_fullfill));
            moveToLastOrdersTextView.setVisibility(View.VISIBLE);

            moveToMoreDetailsImgBtn.setImageDrawable(ContextCompat.getDrawable(this ,R.drawable.ic_oval_empty));
            moveToMoreDetailsTextView.setVisibility(View.INVISIBLE);


        } else if (id == R.id.more_details_image_view_restaurant_profile_home_activity){

            //TODO: CREATE MORE DETAILS FRAGMENT
            if (!(restaurantDetailsFragment instanceof RestaurantProfileDetailsFragment)) {
                restaurantDetailsFragment = new RestaurantProfileDetailsFragment();
                bundle.putSerializable(getString(R.string.restaurant_detail), restaurant);
                bundle.putString("key", restaurantKey);
                restaurantDetailsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_restaurant_profile_home_activity, restaurantDetailsFragment).commit();


                moveToMenuImgBtn.setImageDrawable(ContextCompat.getDrawable(this ,R.drawable.ic_oval_empty));
                moveToMenuTextView.setVisibility(View.INVISIBLE);

                moveToLastOrdersImgBtn.setImageDrawable(ContextCompat.getDrawable(this ,R.drawable.ic_oval_empty));
                moveToLastOrdersTextView.setVisibility(View.INVISIBLE);

                moveToMoreDetailsImgBtn.setImageDrawable(ContextCompat.getDrawable(this ,R.drawable.ic_oval_fullfill));
                moveToMoreDetailsTextView.setVisibility(View.VISIBLE);
            }

        }





    }




}