package com.example.nishnushim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishnushim.adapters.SearchAdapter;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Dish;
import com.example.nishnushim.helpclasses.MenuSingleton;
import com.example.nishnushim.helpclasses.Restaurant;
import com.example.nishnushim.helpclasses.helpInterfaces.OnProfileScrollChangeListener;
import com.example.nishnushim.helpclasses.helpInterfaces.OnSearchItemClicked;
import com.example.nishnushim.nishnushFragments.fragments_restaurant_profile.MenuRestaurantFragment;
import com.example.nishnushim.nishnushFragments.fragments_restaurant_profile.RestaurantProfileDetailsFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantProfileHomeActivity extends AppCompatActivity implements View.OnClickListener, OnProfileScrollChangeListener, OnSearchItemClicked {


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

    ImageView searchImageView;
    EditText searchEditText;

    Restaurant restaurant;
    String restaurantKey;
    Fragment restaurantDetailsFragment;
    Bundle bundle = new Bundle();


    List<String> searchDishList;
    Classification searchClassification = new Classification();


    Rect rect = new Rect();
    int scrollViewHeight;
    int scrollPositionLast = 0;

    List<String> searchResultsList = new ArrayList<>();
    List<Dish> dishList = new ArrayList<>();

    private Map<String, List<String>> mapSearchResultString = new HashMap<>();
    private Map<String, List<Dish>> dishListHashMap = new HashMap<>();


    @Override
    protected void onStart() {
        super.onStart();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        restaurantDetailsFragment.onActivityResult(requestCode, resultCode, data);
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
        searchImageView = findViewById(R.id.search_image_view_tool_bar_restaurant_profile_home_activity);


        Intent intent = getIntent();

        if (intent.getSerializableExtra(getBaseContext().getString(R.string.restaurant_detail)) != null) {

            restaurant = (Restaurant) intent.getSerializableExtra(getBaseContext().getString(R.string.restaurant_detail));
            restaurantKey = intent.getStringExtra("key");


            //MENU SINGLETON SET
            MenuSingleton.getInstance().setMenu(restaurant.getMenu());


            ///SAVE FOR THE MOMENT////////////
            SharedPreferences mPrefs = getSharedPreferences("noam", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(restaurant);
            prefsEditor.putString("MyObject", json);
            prefsEditor.putString("key", restaurantKey);
            prefsEditor.commit();
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            restaurantDetailsFragment = new MenuRestaurantFragment(this);
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


                    //TODO: WORKING ON SCROLLING BETWEEN FRAGMENT AND ACTIVITY

                    if (restaurantDetailsFragment instanceof MenuRestaurantFragment) {

//                        Log.i("TOP", moreDetailsRestaurantFrameLayout.getTop() + "");
//                        Log.i("SCROLL Y", restaurantProfileHomeScrollView.getScrollY() + "");

                        if (moreDetailsRestaurantFrameLayout.getTop() > restaurantProfileHomeScrollView.getScrollY()) {

                            restaurantProfileHomeScrollView.setOnTouchListener(null);
                            ((MenuRestaurantFragment) restaurantDetailsFragment).enableMenuRecyclerViewScrolling(false);


                            if (restaurantProfileHomeScrollView.getScrollY() > restaurantProfileAreaConstrainLayout.getTop() && restaurantProfileHomeScrollView.getScrollY() != 0) {

                                float newAlpha = ((float) restaurantProfileHomeScrollView.getScrollY() / (float) restaurantProfileAreaConstrainLayout.getBottom());

                                restaurantProfileAreaConstrainLayout.setAlpha(1 - newAlpha);
                                toolbar.setAlpha(newAlpha);
                                searchEditText.setAlpha(newAlpha);

                            }


                            if (restaurantProfileHomeScrollView.getScrollY() > restaurantProfileAreaConstrainLayout.getBottom()) {

                                restaurantProfileAreaConstrainLayout.setAlpha(0);
                                toolbar.setAlpha(1);
                                searchEditText.setAlpha(1);

                            }


                            if (restaurantProfileHomeScrollView.getScrollY() == restaurantProfileAreaConstrainLayout.getTop()) {
                                restaurantProfileAreaConstrainLayout.setAlpha(1);
                                toolbar.setAlpha(0);
                                searchEditText.setAlpha(0);
                            }
                        } else {

                            restaurantProfileHomeScrollView.stopNestedScroll();
                            restaurantProfileHomeScrollView.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return true;
                                }
                            });
                            ((MenuRestaurantFragment) restaurantDetailsFragment).enableMenuRecyclerViewScrolling(true);
                        }


                    }
//                    scrollPositionLast = restaurantProfileHomeScrollView.getScrollY();

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
    public void onSearchClicked(int position) {


    }


    public void searchClicked(View view) {


        dishList = new ArrayList<>();
        searchResultsList = new ArrayList<>();

        searchEditText.setFocusable(false);

        View popUpView = getLayoutInflater().inflate(R.layout.search_pop_up_window, null);
        Dialog searchDialog = new Dialog(this);
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        searchDialog.setContentView(popUpView);

        LinearLayout firstScreenLinearLayout = popUpView.findViewById(R.id.linear_layout_search_logo_area_search_pop_up_window_layout);
        firstScreenLinearLayout.setVisibility(View.GONE);


        EditText searchEditText = popUpView.findViewById(R.id.edit_text_search_restaurant_by_string_search_pop_up_window);
        LinearLayout resultsListsLinearLayout = popUpView.findViewById(R.id.results_lists_linear_layout_area_serach_pop_up_window);
        resultsListsLinearLayout.setVisibility(View.VISIBLE);

        TextView subTitledListView = popUpView.findViewById(R.id.sub_title_history_list_text_view_search_pop_up_window);
        subTitledListView.setText("מומלצים");

        ListView searchResultsListView, recommendationResultsListView;

        searchResultsListView = popUpView.findViewById(R.id.search_list_view_search_pop_up_window_layout);
        recommendationResultsListView = popUpView.findViewById(R.id.history_of_search_list_view_search_pop_up_window_layout);

        SearchAdapter searchAdapter = new SearchAdapter(this, searchResultsList, dishList, this);
        searchResultsListView.setAdapter(searchAdapter);


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s.length() > 0) {


                    if (mapSearchResultString.containsKey(s.toString()) && dishListHashMap.containsKey(s.toString())) {

                        searchResultsList.clear();
                        dishList.clear();

                        searchResultsList.addAll(mapSearchResultString.get(s.toString()));
                        dishList.addAll(dishListHashMap.get(s.toString()));

                        searchAdapter.setDishList(dishList);
                        searchAdapter.setSearchList(searchResultsList);
                        searchAdapter.notifyDataSetChanged();


                    } else {


                        for (int i = 0; i < restaurant.getMenu().getClassifications().size(); i++) {


                            for (int j = 0; j < restaurant.getMenu().getClassifications().get(i).getDishList().size(); j++) {

                                if (restaurant.getMenu().getClassifications().get(i).getDishList().get(j).getName().contains(s.toString())
                                        || restaurant.getMenu().getClassifications().get(i).getDishList().get(j).getDetails().contains(s.toString())) {
                                    addTextToSearchMap(s.toString(), restaurant.getMenu().getClassifications().get(i).getDishList().get(j).getName());
                                    addDishToDishMap(restaurant.getMenu().getClassifications().get(i).getDishList().get(j), s.toString());
                                }
                            }


                        }


                        if (mapSearchResultString.containsKey(s.toString())){

                            searchResultsList.clear();
                            dishList.clear();

                            searchResultsList.addAll(mapSearchResultString.get(s.toString()));
                            dishList.addAll(dishListHashMap.get(s.toString()));

                        }else{
                            searchResultsList.clear();
                            searchDishList.clear();
                        }


                        searchAdapter.setDishList(dishList);
                        searchAdapter.setSearchList(searchResultsList);
                        searchAdapter.notifyDataSetChanged();

                    }
                }


            }
        });


        searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (searchEditText.getRight() - searchEditText.getPaddingRight() - searchEditText.getCompoundDrawables()[2].getBounds().width())) {
                        searchDialog.dismiss();
                        return true;
                    }
                }
                return false;
            }
        });


        searchDialog.create();
        searchDialog.show();


        Window window = searchDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.TOP);

    }


    private void addTextToSearchMap(String searchText, String addText) {

        List<String> strings = new ArrayList<>();

        if (!mapSearchResultString.containsKey(searchText)) {
            strings.add(addText);
            mapSearchResultString.put(searchText, strings);
        } else if (mapSearchResultString.get(searchText).isEmpty()) {

            if (mapSearchResultString.get(searchText).contains(addText)) {
                mapSearchResultString.get(searchText).add(addText);
            }
        }
    }


    private void addDishToDishMap(Dish dish, String s) {

        List<Dish> dishList = new ArrayList<>();

        if (dishListHashMap.containsKey(s)) {
            if (dishListHashMap.get(s).isEmpty()) {
                dishList.add(dish);
                dishListHashMap.get(s).addAll(dishList);
            }
            dishListHashMap.get(s).add(dish);
        } else {
            dishList.add(dish);
            dishListHashMap.put(s, dishList);
        }

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.menu_image_view_restaurant_profile_home_activity) {

            if (!(restaurantDetailsFragment instanceof MenuRestaurantFragment)) {
                restaurantDetailsFragment = new MenuRestaurantFragment(this);
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


    @Override
    public boolean onScrollScreenUp() {

        if (restaurantProfileHomeScrollView.getScrollY() >= moreDetailsRestaurantFrameLayout.getTop() + 68) {
            return true;
        }

        return false;
    }


    @Override
    public boolean onScrollScreenDown() {


        return false;
    }


}