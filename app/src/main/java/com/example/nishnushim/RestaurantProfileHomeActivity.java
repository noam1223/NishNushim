package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishnushim.helpclasses.Restaurant;
import com.example.nishnushim.nishnushFragments.fragments_restaurant_profile.MenuRestaurantFragment;
import com.example.nishnushim.nishnushFragments.fragments_restaurant_profile.RestaurantProfileDetailsFragment;
import com.google.android.material.appbar.AppBarLayout;

public class RestaurantProfileHomeActivity extends AppCompatActivity implements View.OnClickListener {

    ScrollView restaurantProfileHomeScrollView;
    AppBarLayout appBarLayout;
    Toolbar toolbar;

    ImageView logoProfileImageView, profilePictureImageView;
    TextView nameRestaurantTextView, openHoursRestaurantTextView, distanceFromUserTextView, deliveryCostTextView, deliveryTimeTextView, minToDeliverTextView, avgCreditsTextView;
    ImageButton addToFavoriteRestaurantListImgBtn;

    ConstraintLayout topHeadBarConstrainLayout, restaurantProfileAreaConstrainLayout, constraintLayoutFullScreen;
    FrameLayout moreDetailsRestaurantFrameLayout;


    TextView moveToMenuTextView, moveToLastOrdersTextView, moveToMoreDetailsTextView;
    ImageView moveToMenuImgBtn, moveToLastOrdersImgBtn, moveToMoreDetailsImgBtn;

    EditText searchEditText;

    Restaurant restaurant;
    String restaurantKey;
    Fragment restaurantDetailsFragment;
    Bundle bundle = new Bundle();

    Rect rect = new Rect();
    int scrollViewHeight;
    int scrollPositionLast = 0;


    @Override
    protected void onStart() {
        super.onStart();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile_home);


        topHeadBarConstrainLayout = findViewById(R.id.constrain_layout_top_title_area_profile_home_activity);
        restaurantProfileAreaConstrainLayout = findViewById(R.id.constrain_layout_profile_area_restaurant_profile_home_activity);
        constraintLayoutFullScreen = findViewById(R.id.constrain_layout_scroll_view_parent_profile_home_activity);
        moreDetailsRestaurantFrameLayout = findViewById(R.id.nav_host_fragment_restaurant_profile_home_activity);
        appBarLayout = findViewById(R.id.app_bar_restaurant_profile_home_activity);
        toolbar = findViewById(R.id.searching_tool_bar_restaurant_profile_home_activity);
        searchEditText = findViewById(R.id.edit_text_search_tool_bar_restaurant_profile_home_activity);

        Intent intent = getIntent();

        if (intent.getSerializableExtra(getBaseContext().getString(R.string.restaurant_detail)) != null) {

            restaurant = (Restaurant) intent.getSerializableExtra(getBaseContext().getString(R.string.restaurant_detail));
            restaurantKey = intent.getStringExtra("key");

            restaurantDetailsFragment = new MenuRestaurantFragment();
            bundle.putSerializable(getString(R.string.restaurant_detail), restaurant);
            bundle.putString("key", restaurantKey);
            restaurantDetailsFragment.setArguments(bundle);

            restaurantProfileHomeScrollView = findViewById(R.id.scroll_view_restaurant_profile_home_activity);
            scrollViewHeight = restaurantProfileHomeScrollView.getChildAt(0).getHeight();
            restaurantProfileHomeScrollView.getHitRect(rect);
//            restaurantProfileHomeScrollView.setTop(moreDetailsRestaurantFrameLayout.getTop() - 6 - 65);
//            restaurantProfileHomeScrollView.onStopNestedScroll(moreDetailsRestaurantFrameLayout);
            restaurantProfileHomeScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {


                    //TODO: WORKING ON SCROLLLING BETWEEN FRAGMENT AND ACTIVITY

                    //WORKING ON COMMUNICATION BETWEEN PROFILE HOME SCROLLING TO THE FRAGMENTS SCROLLING (MENU AT THE MEANTIME)
                    if (scrollPositionLast == moreDetailsRestaurantFrameLayout.getTop()){
//                        if (scrollPositionLast == moreDetailsRestaurantFrameLayout.getTop() - 100){
////                            if (restaurantDetailsFragment instanceof MenuRestaurantFragment){
////                                ((MenuRestaurantFragment)restaurantDetailsFragment).onScrollDownListener();
////                            }
                            restaurantProfileHomeScrollView.setScrollY(scrollPositionLast);
                            return;
//                        }
                    }
//                    else{
//
//                        if (scrollPositionLast == moreDetailsRestaurantFrameLayout.getTop() + 12){
//
//                            if (restaurantDetailsFragment instanceof MenuRestaurantFragment){
//
//                               if (((MenuRestaurantFragment)restaurantDetailsFragment).onScrollUpListener()){
//                                   return;
//                               }
//
//                            }
//
//                        }
//
//                    }


                    if (restaurantProfileHomeScrollView.getScrollY() > restaurantProfileAreaConstrainLayout.getTop()  && restaurantProfileHomeScrollView.getScrollY() != 0) {

                        float newAlpha = ((float) restaurantProfileHomeScrollView.getScrollY() / (float) restaurantProfileAreaConstrainLayout.getBottom());

                        restaurantProfileAreaConstrainLayout.setAlpha(1 - newAlpha);
                        toolbar.setAlpha(newAlpha);
                        searchEditText.setAlpha(newAlpha);

                    }


                    if (restaurantProfileHomeScrollView.getScrollY() > restaurantProfileAreaConstrainLayout.getBottom()){

                        restaurantProfileAreaConstrainLayout.setAlpha(0);
                        toolbar.setAlpha(1);
                        searchEditText.setAlpha(1);

                    }


                    if (restaurantProfileHomeScrollView.getScrollY() == restaurantProfileAreaConstrainLayout.getTop()){
                        restaurantProfileAreaConstrainLayout.setAlpha(1);
                        toolbar.setAlpha(0);
                        searchEditText.setAlpha(0);
                    }




                    if (restaurantDetailsFragment instanceof MenuRestaurantFragment) {


                    } else if (restaurantDetailsFragment instanceof RestaurantProfileDetailsFragment) {

                        ((RestaurantProfileDetailsFragment) restaurantDetailsFragment).onScrollListenerCheck(restaurantProfileHomeScrollView);

                    }


                    scrollPositionLast = restaurantProfileHomeScrollView.getScrollY();

                }
            });


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


            getSupportFragmentManager().beginTransaction().replace(moreDetailsRestaurantFrameLayout.getId(), restaurantDetailsFragment).commit();

        }

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.menu_image_view_restaurant_profile_home_activity) {

            if (!(restaurantDetailsFragment instanceof MenuRestaurantFragment)) {
                restaurantDetailsFragment = new MenuRestaurantFragment();
                bundle.putSerializable(getString(R.string.restaurant_detail), restaurant);
                bundle.putString("key", restaurantKey);
                restaurantDetailsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(moreDetailsRestaurantFrameLayout.getId(), restaurantDetailsFragment).commit();


                moveToMenuImgBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_oval_fullfill));
                moveToMenuTextView.setVisibility(View.VISIBLE);

                moveToLastOrdersImgBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_oval_empty));
                moveToLastOrdersTextView.setVisibility(View.INVISIBLE);

                moveToMoreDetailsImgBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_oval_empty));
                moveToMoreDetailsTextView.setVisibility(View.INVISIBLE);
            }

        } else if (id == R.id.last_orders_image_view_restaurant_profile_home_activity) {

            //TODO: CREATE LAST ORDERS FRAGMENT
//            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_restaurant_profile_home_activity, new MenuRestaurantFragment()).commit();


            moveToMenuImgBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_oval_empty));
            moveToMenuTextView.setVisibility(View.INVISIBLE);

            moveToLastOrdersImgBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_oval_fullfill));
            moveToLastOrdersTextView.setVisibility(View.VISIBLE);

            moveToMoreDetailsImgBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_oval_empty));
            moveToMoreDetailsTextView.setVisibility(View.INVISIBLE);


        } else if (id == R.id.more_details_image_view_restaurant_profile_home_activity) {


            //TODO: CREATE MORE DETAILS FRAGMENT
            if (!(restaurantDetailsFragment instanceof RestaurantProfileDetailsFragment)) {
                restaurantDetailsFragment = new RestaurantProfileDetailsFragment();
                bundle.putSerializable(getString(R.string.restaurant_detail), restaurant);
                bundle.putString("key", restaurantKey);
                restaurantDetailsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(moreDetailsRestaurantFrameLayout.getId(), restaurantDetailsFragment).commit();


                moveToMenuImgBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_oval_empty));
                moveToMenuTextView.setVisibility(View.INVISIBLE);

                moveToLastOrdersImgBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_oval_empty));
                moveToLastOrdersTextView.setVisibility(View.INVISIBLE);

                moveToMoreDetailsImgBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_oval_fullfill));
                moveToMoreDetailsTextView.setVisibility(View.VISIBLE);
            }

        }

    }

    private void findCompletelyVisibleChildren() {
        for (int i = 0; i < constraintLayoutFullScreen.getChildCount(); i++) {
            View view = constraintLayoutFullScreen.getChildAt(i);
            if (view != null && view.getTag() == "10") {
                if (!view.getLocalVisibleRect(rect) || rect.height() < view.getHeight()) {
//                    Log.d(TAG, "View " + view.getTag() + " is Partially Visible");

                    Toast.makeText(this, "PARTIAL VISIBLE", Toast.LENGTH_SHORT).show();

                } else {
//                    Log.d(TAG, "View " + view.getTag() + " is Completely Visible");
                    Toast.makeText(this, "VISIBLE", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}