package com.example.nishnushim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishnushim.adapters.RestaurantDetailsAdapter;
import com.example.nishnushim.adapters.RestaurantTypeAdapter;

import com.example.nishnushim.adapters.SearchAdapter;
import com.example.nishnushim.helpUIClass.CreateCustomBtn;
import com.example.nishnushim.helpclasses.Restaurant;
import com.example.nishnushim.helpclasses.UserSingleton;
import com.example.nishnushim.helpclasses.helpInterfaces.OnSearchItemClicked;
import com.example.nishnushim.helpclasses.helpInterfaces.RestaurantTypeListener;
import com.example.nishnushim.helpclasses.RestaurantTypeClass;
import com.example.nishnushim.helpclasses.User;
import com.example.nishnushim.nishnushFragments.NishnushimHomeFragment;
import com.example.nishnushim.nishnushFragments.NishnushinAnonymousUserFragment;
import com.example.nishnushim.nishnushFragments.RestaurantsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, RestaurantTypeListener , OnSearchItemClicked {

    private final static int REQUEST_PROFILE_RETURN = 1212;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    RelativeLayout toolBarRelativeLayout;

    ImageButton searchImgBtn, filterImageBtn, menuImageBtn, myAddressArrowImageBtn;
    TextView addressAppBarTextView, filterAppBarTextView;

    //    EditText searchRestaurantByStringEditTxt;
    FrameLayout frameLayout;
    Drawable frameDrawable, filterDrawable, wrapDrawable;


    User user;
    NavigationView navigationView;
    TextView navigationHeaderTextView;
    ImageView navigationHeaderImageView;

    RecyclerView typeRestaurantRecyclerView;
    RecyclerView.Adapter typeRestaurantAdapter;

    FirebaseFirestore db;
    FirebaseAuth auth;

    List<Button> filterBtnList = new ArrayList<>();
    String chosenFilterString = "זמן משלוח";
    Dialog filterDialog, searchDialog, myAddressDialog;
    Button timeDeliveryFilterBtn, distanceFilterBtn, minAmountOfMoneyForDeliverFilterBtn, deliveryAmountFilterBtn, creditsFilterBtn, recommendationFilterBtn;


    //MAIN RESTAURANTS LIST
    List<Restaurant> restaurants = new ArrayList<>();
    List<String> keys = new ArrayList<>();

    //SUB MAIN RESTAURANT LIST


    List<String> searchResultsList = new ArrayList<>();
    List<Restaurant> restaurantsResults = new ArrayList<>();
    List<String> keysResults = new ArrayList<>();

    Map<String, List<Restaurant>> mapRestaurantsSearch = new HashMap<>();
    Map<String, List<String>> mapSearchResultString = new HashMap<>();
    Map<String, List<String>> mapRestaurantKeyString = new HashMap<>();

    String[] classificationArray = {"נשנושים", "פיצריות", "המבורגרים", "תאילנדי", "בשרים", "דגים/פירות ים", "מרקים", "סנדוויצ׳ים", "קינוחים", "חומוס", "כשר", "אסייתי", "איטלקי", "מקסיקני", "בתי קפה", "טבעוני", "טבעוני", "מאפים", "גלידריות"};



    private void updateHomePageUI() {
        addressAppBarTextView.setText(user.getChosenAddress().fullMyAddress());
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_PROFILE_RETURN) {

            if (data != null) {

                if (data.getSerializableExtra("user") != null) {
                    user = (User) data.getSerializableExtra("user");
                    updateHomePageUI();
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolBarRelativeLayout = findViewById(R.id.relative_layout_tool_bar_area_home_page_activity);
//        searchRestaurantByStringEditTxt = findViewById(R.id.edit_text_search_restaurant_by_string_home_page_activity);

        searchImgBtn = findViewById(R.id.search_image_btn_tool_bar_item);
        filterImageBtn = findViewById(R.id.filter_image_btn_tool_bar_item);
        menuImageBtn = findViewById(R.id.open_side_bar_image_btn_tool_bar_item);
        myAddressArrowImageBtn = findViewById(R.id.open_my_address_pop_up_image_btn_home_page_activity);

        addressAppBarTextView = findViewById(R.id.my_address_app_bar_text_view_home_page_activity);
        filterAppBarTextView = findViewById(R.id.filter_app_bar_text_view_home_page_activity);

        frameLayout = findViewById(R.id.nav_host_fragment_home_page_activity);
        frameDrawable = frameLayout.getBackground();


        searchImgBtn.setOnClickListener(this);
        filterImageBtn.setOnClickListener(this);
        menuImageBtn.setOnClickListener(this);
        myAddressArrowImageBtn.setOnClickListener(this);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_drawer_home_page_activity);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        View headerView = navigationView.getHeaderView(0);
        navigationHeaderTextView = headerView.findViewById(R.id.header_text_view_drawer_header_item);
        navigationHeaderImageView = headerView.findViewById(R.id.header_image_view_drawer_header_item);

        actionBarDrawerToggle.syncState();
        initializeTypeRestaurantFilterRecyclerView();


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT////////////
        ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT////////////
        SharedPreferences mPrefs = getSharedPreferences("noam", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");

        User obj = gson.fromJson(json, User.class);


//        if (obj != null) {
//            user = obj;
//
//            navigationView.getMenu().clear();
//            navigationView.inflateMenu(R.menu.menu_drawer_login);
//
//            //TODO: SET ADDRESS BY MY ADDRESS CLASS //
//            updateHomePageUI();
//        } else {

            //TODO: DO NOT FORGET TO REMOVE THE STRING KEY
            //"LCl46tZA2wd53H8If1mWTb2VjGw2"
            db.collection(getString(R.string.USERS_DB)).document("LCl46tZA2wd53H8If1mWTb2VjGw2").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {

                        user = task.getResult().toObject(User.class);

                        if (user != null) {
                            navigationView.getMenu().clear();
                            navigationView.inflateMenu(R.menu.menu_drawer_login);
                            UserSingleton.getInstance().setUser(user);

                            //TODO: SET ADDRESS BY MY ADDRESS CLASS //
                            updateHomePageUI();

//                            SharedPreferences mPrefs = getSharedPreferences("noam", MODE_PRIVATE);
//                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
//                            Gson gson = new Gson();
//                            String json = gson.toJson(user);
//                            prefsEditor.putString("user", json);
//                            prefsEditor.commit();


                        db.collection(getResources().getString(R.string.RESTAURANTS_PATH)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {

                                    if (task.getResult() != null) {

                                        for (DocumentSnapshot documentSnapshot :
                                                task.getResult()) {

                                            Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);

//                                            if (isRestaurantAdded(restaurant, documentSnapshot.getId())) {
//                                                restaurant.setDistanceFromCurrentUser(distance(user.getChosenAddress().getLatitude(), user.getAddresses().get(0).getLongitude(),
//                                                        restaurant.getMyAddress().getLatitude(), restaurant.getMyAddress().getLongitude()));
//                                            }

                                            restaurants.add(restaurant);
                                            keys.add(documentSnapshot.getId());

                                        }



                                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_home_page_activity, new NishnushimHomeFragment(restaurants, keys)).commit();


                                    } else
                                        Toast.makeText(getApplicationContext(), "אין מסעדות כרגע להציג", Toast.LENGTH_SHORT).show();

                                } else if (task.isCanceled() && task.getException() != null) {

                                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_home_page_activity, new NishnushimHomeFragment()).commit();
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_home_page_activity, new NishnushimHomeFragment()).commit();
                            }
                        });


                        } else
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_home_page_activity, new NishnushinAnonymousUserFragment()).commit();


                    } else if (task.isCanceled()) {
                        Toast.makeText(HomePageActivity.this, "ישנה בעיית חיבור", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                ;


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(HomePageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_home_page_activity, new NishnushimHomeFragment()).commit();
                }
            });

//        }
    }

    ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT////////////
    ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT////////////



    //ADDING RESTAURANT TO MAIN LIST RESTAURANTS
    private boolean isRestaurantAdded(Restaurant restaurant, String key) {


        //TODO: CHANGE THE ADDRESS LOGIC IN USER AND MYADDRESS
        if (restaurant != null) {

            if (user.getChosenAddress().getCityName().equals(restaurant.getMyAddress().getCityName())) {
                restaurants.add(restaurant);
                keys.add(key);
                return true;
            } else {

                for (int i = 0; i < restaurant.getAreasForDeliveries().size(); i++) {

                    if (user.getAddresses().get(0).getCityName().equals(restaurant.getAreasForDeliveries().get(i).getAreaName())) {
                        restaurant.getAreasForDeliveries().get(i).setArea(true);
                        restaurants.add(restaurant);
                        keys.add(key);
                        return true;
                    }

                }
            }

        }

        return false;
    }


    private void initializeTypeRestaurantFilterRecyclerView() {

        typeRestaurantRecyclerView = findViewById(R.id.filter_restaurant_type_recycler_view_address_activity);
        typeRestaurantRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        typeRestaurantRecyclerView.setLayoutManager(layoutManager);
        typeRestaurantAdapter = new RestaurantTypeAdapter(this, this);
        typeRestaurantRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.right = 20;
            }
        });
        typeRestaurantRecyclerView.setAdapter(typeRestaurantAdapter);

    }




    @Override
    public void onSearchClicked(int position) {


        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra(getString(R.string.restaurant_detail), (Serializable) restaurantsResults);
        intent.putExtra("key", keysResults.get(position));
        intent.putExtra("search", searchResultsList.get(position));
        startActivity(intent);


    }





    @Override
    public void onClick(View v) {


        int id = v.getId();

        if (id == R.id.search_image_btn_tool_bar_item) {

            Toast.makeText(this, "SEARCH", Toast.LENGTH_SHORT).show();

//            toolBarRelativeLayout.setVisibility(View.INVISIBLE);
//            searchRestaurantByStringEditTxt.setVisibility(View.VISIBLE);


            View popUpView = getLayoutInflater().inflate(R.layout.search_pop_up_window, null);
            searchDialog = new Dialog(this);
            searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            searchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            searchDialog.setContentView(popUpView);


            if (searchDialog.getWindow() != null)
                searchDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;


            LinearLayout firstScreenLinearLayout = popUpView.findViewById(R.id.linear_layout_search_logo_area_search_pop_up_window_layout);


            EditText searchEditText = popUpView.findViewById(R.id.edit_text_search_restaurant_by_string_search_pop_up_window);


            LinearLayout resultsListsLinearLayout = popUpView.findViewById(R.id.results_lists_linear_layout_area_serach_pop_up_window);

            ListView searchResultsListView, historyResultsListView;

            searchResultsListView = popUpView.findViewById(R.id.search_list_view_search_pop_up_window_layout);
            historyResultsListView = popUpView.findViewById(R.id.history_of_search_list_view_search_pop_up_window_layout);

            restaurantsResults = new ArrayList<>();
            keysResults = new ArrayList<>();
            SearchAdapter searchAdapter = new SearchAdapter(this, searchResultsList, restaurantsResults, keys, this);
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

                        firstScreenLinearLayout.setVisibility(View.GONE);
                        resultsListsLinearLayout.setVisibility(View.VISIBLE);

                        if (mapRestaurantsSearch.containsKey(s.toString()) && mapSearchResultString.containsKey(s.toString()) && mapRestaurantKeyString.containsKey(s.toString())) {

                            restaurantsResults.clear();
                            searchResultsList.clear();
                            keysResults.clear();

                            restaurantsResults.addAll(mapRestaurantsSearch.get(s.toString()));
                            searchResultsList.addAll(mapSearchResultString.get(s.toString()));
                            keysResults.addAll(mapRestaurantKeyString.get(s.toString()));


                            searchAdapter.setRestaurants(restaurantsResults);
                            searchAdapter.setSearchList(searchResultsList);
                            searchAdapter.setKeys(keysResults);
                            searchAdapter.notifyDataSetChanged();


                        } else {

                            restaurantsResults.addAll(searchInRestaurants(s.toString()));

                            if (mapSearchResultString.containsKey(s.toString())) {
                                searchResultsList.clear();
                                keysResults.clear();
                                searchResultsList.addAll(mapSearchResultString.get(s.toString()));
                                keysResults.addAll(mapRestaurantKeyString.get(s.toString()));
                            } else searchResultsList.clear();

                            searchAdapter.setRestaurants(restaurantsResults);
                            searchAdapter.setSearchList(searchResultsList);
                            searchAdapter.setKeys(keysResults);
                            searchAdapter.notifyDataSetChanged();

                        }

                    } else {
                        firstScreenLinearLayout.setVisibility(View.VISIBLE);
                        resultsListsLinearLayout.setVisibility(View.GONE);
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


        } else if (id == R.id.filter_image_btn_tool_bar_item) {

            Toast.makeText(this, "FILTER", Toast.LENGTH_SHORT).show();

            View popUpView = getLayoutInflater().inflate(R.layout.filter_pop_up_window, null);
            filterDialog = new Dialog(this);
            filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            filterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            filterDialog.setContentView(popUpView);


            if (filterDialog.getWindow() != null)
                filterDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;


            timeDeliveryFilterBtn = popUpView.findViewById(R.id.time_delivery_filter_pop_up_window);
            distanceFilterBtn = popUpView.findViewById(R.id.distance_filter_pop_up_window);
            minAmountOfMoneyForDeliverFilterBtn = popUpView.findViewById(R.id.min_amount_for_delivery_filter_pop_up_window);
            deliveryAmountFilterBtn = popUpView.findViewById(R.id.delivery_amount_filter_pop_up_window);
            creditsFilterBtn = popUpView.findViewById(R.id.credits_filter_pop_up_window);
            recommendationFilterBtn = popUpView.findViewById(R.id.recommendation_filter_pop_up_window);

            filterBtnList.clear();

            filterBtnList.add(timeDeliveryFilterBtn);
            filterBtnList.add(distanceFilterBtn);
            filterBtnList.add(minAmountOfMoneyForDeliverFilterBtn);
            filterBtnList.add(deliveryAmountFilterBtn);
            filterBtnList.add(creditsFilterBtn);
            filterBtnList.add(recommendationFilterBtn);


            //WORK ON FILTER RESTAURANTS //TIME DELIVERY //DISTANCE FROM USER //MINIMUM TO ORDER //DELIVERY COST //CREDITS //RECOMMENDATIONS

            for (int i = 0; i < filterBtnList.size(); i++) {
                int finalI = i;

                if (chosenFilterString.equals(filterBtnList.get(finalI).getText().toString())) {

                    filterBtnList.get(finalI).setPressed(true);
                    Drawable[] drawables = filterBtnList.get(finalI).getCompoundDrawables();
                    Drawable wrapDrawable = DrawableCompat.wrap(drawables[1]);
                    DrawableCompat.setTint(wrapDrawable, getResources().getColor(R.color.white));
//                    filterBtnList.get(finalI).setCompoundDrawables(null, wrapDrawable, null, null);

                }


                filterBtnList.get(i).setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {


                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            Drawable[] drawables = filterBtnList.get(finalI).getCompoundDrawables();
                            Drawable wrapDrawable = DrawableCompat.wrap(drawables[1]);
                            DrawableCompat.setTint(wrapDrawable, getResources().getColor(R.color.white));
//                            filterBtnList.get(finalI).setCompoundDrawables(null, wrapDrawable, null, null);

                            for (int j = 0; j < filterBtnList.size(); j++) {

                                if (j != finalI) {

                                    filterBtnList.get(j).setPressed(false);
                                    Drawable[] drawabless = filterBtnList.get(j).getCompoundDrawables();
                                    Drawable wrapDrawable1 = DrawableCompat.wrap(drawabless[1]);
                                    DrawableCompat.setTint(wrapDrawable1, getResources().getColor(R.color.custom_blue));
//                                    filterBtnList.get(finalI).setCompoundDrawables(null, wrapDrawable1, null, null);


                                }

                            }


                        } else if (event.getAction() == MotionEvent.ACTION_UP) {

//                            filterBtnList.get(finalI).setCompoundDrawables(null, filterDrawable, null, null);
//                            filterDrawable = null;
//                            DrawableCompat.setTint(wrapDrawable, getResources().getColor(R.color.custom_blue));

                            filterAppBarTextView.setText(((Button) v).getText().toString());

                            //HERE THE FUNCTION WILL BE FOR FILTER THE RESTAURANTS

                            int id = filterBtnList.get(finalI).getId();
                            chosenFilterString = filterBtnList.get(finalI).getText().toString();


                            //TIME DELIVERY
                            if (id == R.id.time_delivery_filter_pop_up_window) {

                                filterByTimeDelivery();

                                //DISTANCE FROM USER
                            } else if (id == R.id.distance_filter_pop_up_window) {

                                filterByDistance();

                                //MINIMUM TO ORDER
                            } else if (id == R.id.min_amount_for_delivery_filter_pop_up_window) {

                                filterByMinToDeliver();

                                //DELIVERY COST
                            } else if (id == R.id.delivery_amount_filter_pop_up_window) {

                                filterByDeliveryAmount();

                                //CREDITS
                            } else if (id == R.id.credits_filter_pop_up_window) {

                                filterByCredits();

                                //RECOMMENDATIONS
                            } else if (id == R.id.recommendation_filter_pop_up_window) {

                                filterByRecommendation();

                            }


                            filterDialog.dismiss();
                        }
                        return false;
                    }
                });
            }


            filterDialog.show();

            Window window = filterDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.TOP);


        } else if (id == R.id.open_side_bar_image_btn_tool_bar_item) {


            if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) drawerLayout.openDrawer(Gravity.LEFT);
            else drawerLayout.closeDrawer(Gravity.LEFT);


        } else if (id == R.id.open_my_address_pop_up_image_btn_home_page_activity) {


            Toast.makeText(this, "MY ADDRESS", Toast.LENGTH_SHORT).show();

            View popUpView = getLayoutInflater().inflate(R.layout.address_list_from_top_screen_layout, null);
            myAddressDialog = new Dialog(this);
            myAddressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            myAddressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myAddressDialog.setContentView(popUpView);

            if (myAddressDialog.getWindow() != null)
                myAddressDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;


            Button saveBtn = popUpView.findViewById(R.id.save_btn_address_list_pop_up_window);
            List<RadioButton> radioButtonList = new ArrayList<>();
            final RadioGroup wayOfDeliveryRadioGroup, myAddressListRadioGroup;
            wayOfDeliveryRadioGroup = popUpView.findViewById(R.id.way_of_delivery_radio_group_address_list_pop_up_window);
            myAddressListRadioGroup = popUpView.findViewById(R.id.list_of_address_radio_group_address_list_pop_up_window);

            RadioButton myLocationToDeliverRadioBtn = popUpView.findViewById(R.id.my_location_address_to_deliver_radio_btn_address_list_pop_up_window);
            radioButtonList.add(myLocationToDeliverRadioBtn);

            int positionAddressChosen = -1;


            for (int i = 0; i < user.getAddresses().size(); i++) {

                RadioButton radioButton = CreateCustomBtn.createRadioAddressBtn(this,myLocationToDeliverRadioBtn, i, 1, R.drawable.ic_icon_placeholder_small, user.getAddresses().get(i).fullMyAddress());

                if (user.getAddresses().get(i).isChosen()) {
                    radioButton.setChecked(true);
                    //TOOD:CONFIGURE HOW TO CHANGE POSITION CHOSEN IN MY ADDRESS LIST
                    positionAddressChosen = i;
                }

                radioButtonList.add(radioButton);
                myAddressListRadioGroup.addView(radioButton);

            }


            RadioButton radioButton = CreateCustomBtn.createRadioAddressBtn(this ,myLocationToDeliverRadioBtn, user.getAddresses().size(), 3, R.drawable.ic_icon_add_button, "אני כרגע נמצא בכתובת אחרת");
            radioButtonList.add(radioButton);

            myAddressListRadioGroup.addView(radioButton);


            wayOfDeliveryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (wayOfDeliveryRadioGroup.getCheckedRadioButtonId() != -1) {
                        RadioButton radioButton1 = popUpView.findViewById(wayOfDeliveryRadioGroup.getCheckedRadioButtonId());
                    }
                }
            });


            myAddressListRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (myAddressListRadioGroup.getCheckedRadioButtonId() != -1) {

                        RadioButton radioButton1 = popUpView.findViewById(myAddressListRadioGroup.getCheckedRadioButtonId());

                        if (myAddressListRadioGroup.getChildAt(myAddressListRadioGroup.getChildCount() - 1).getId() == radioButton1.getId()) {
                            Intent intent = new Intent(getApplicationContext(), FirstAddressActivity.class);
                            intent.putExtra("user", user);
                            intent.putExtra("is_first", false);
                            startActivityForResult(intent, REQUEST_PROFILE_RETURN);
                            myAddressDialog.dismiss();
                        } else {

                            for (int i = 0; i < user.getAddresses().size(); i++) {

                                if (myAddressListRadioGroup.getChildAt(i + 1).getId() == myAddressListRadioGroup.getCheckedRadioButtonId()) {
                                    user.getAddresses().get(i).setChosen(true);
                                } else user.getAddresses().get(i).setChosen(false);

                            }

                            updateHomePageUI();

                        }
                    }
                }
            });


            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myAddressDialog.dismiss();
                }
            });


            myAddressDialog.show();

            Window window = myAddressDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.TOP);

        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////WORKING ON FILTER POP UP WINDOW

    private void filterByRecommendation() {

        Collections.sort(restaurants, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                return Float.compare(o1.getRecommendationAvg(), o2.getRecommendationAvg());
            }
        });


    }


    private void filterByTimeDelivery() {

        Collections.sort(restaurants, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {

                int amount1 = 0, amount2 = 0;


                for (int i = 0; i < o1.getAreasForDeliveries().size(); i++) {

                    if (o1.getAreasForDeliveries().get(i).isArea()) {
                        amount1 = o1.getAreasForDeliveries().get(i).getTimeOfDelivery();
                    }

                }


                for (int i = 0; i < o2.getAreasForDeliveries().size(); i++) {
                    if (o2.getAreasForDeliveries().get(i).isArea()) {
                        amount2 = o2.getAreasForDeliveries().get(i).getTimeOfDelivery();
                    }
                }


                if (amount1 > amount2) {
                    return 1;
                } else if (amount1 == amount2) {
                    return 0;
                } else return -1;
            }
        });

    }


    private void filterByDistance() {

        Collections.sort(restaurants, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                return Double.compare(o1.getDistanceFromCurrentUser(), o2.getDistanceFromCurrentUser());
            }
        });

    }


    private void filterByMinToDeliver() {

        Collections.sort(restaurants, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {

                int amount1 = 0, amount2 = 0;


                for (int i = 0; i < o1.getAreasForDeliveries().size(); i++) {

                    if (o1.getAreasForDeliveries().get(i).isArea()) {
                        amount1 = o1.getAreasForDeliveries().get(i).getMinToDeliver();
                    }

                }


                for (int i = 0; i < o2.getAreasForDeliveries().size(); i++) {
                    if (o2.getAreasForDeliveries().get(i).isArea()) {
                        amount2 = o2.getAreasForDeliveries().get(i).getMinToDeliver();
                    }
                }


                if (amount1 > amount2) {
                    return 1;
                } else if (amount1 == amount2) {
                    return 0;
                } else return -1;
            }
        });

    }


    private void filterByDeliveryAmount() {

        Collections.sort(restaurants, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {

                int amount1 = 0, amount2 = 0;


                for (int i = 0; i < o1.getAreasForDeliveries().size(); i++) {

                    if (o1.getAreasForDeliveries().get(i).isArea()) {
                        amount1 = o1.getAreasForDeliveries().get(i).getDeliveryCost();
                    }

                }


                for (int i = 0; i < o2.getAreasForDeliveries().size(); i++) {
                    if (o2.getAreasForDeliveries().get(i).isArea()) {
                        amount2 = o2.getAreasForDeliveries().get(i).getDeliveryCost();
                    }
                }


                if (amount1 > amount2) {
                    return 1;
                } else if (amount1 == amount2) {
                    return 0;
                } else return -1;
            }
        });

    }


    private void filterByCredits() {

        Collections.sort(restaurants, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                return Integer.compare(o1.getCreditAmount(), o2.getCreditAmount());
            }
        });

    }


    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 6371; // in kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c; // output distance, in LOKOMETERS
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////WORKING ON SEARCH POP UP WINDOW


    private List<Restaurant> searchInRestaurants(String s) {

        List<Restaurant> restaurantList = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            boolean isAdded = false;

            for (int i = 0; i < restaurant.getClassificationList().size(); i++) {
                if (classificationArray[restaurant.getClassificationList().get(i)].contains(s)) {
                    addTextToSearchMap(s, classificationArray[restaurant.getClassificationList().get(i)]);
                    isAdded = true;
                }
            }

            for (int i = 0; i < restaurant.getMenu().getClassifications().size(); i++) {

                if (restaurant.getMenu().getClassifications().get(i).getClassificationName().contains(s)) {
                    addTextToSearchMap(s, restaurant.getMenu().getClassifications().get(i).getClassificationName());
                    isAdded = true;
                }


                //TODO: TAKE THIS PART TO SEARCH A INGREDIENT
                for (int j = 0; j < restaurant.getMenu().getClassifications().get(i).getDishList().size(); j++) {
                    if (restaurant.getMenu().getClassifications().get(i).getDishList().get(j).getName().contains(s)) {
                        addTextToSearchMap(s, restaurant.getMenu().getClassifications().get(i).getDishList().get(j).getName());
                        isAdded = true;
                    }
                }
            }


            if (isAdded) {
                restaurantList.add(restaurant);
                addRestaurantToRestaurantMap(restaurant, s);
            }

        }


        return restaurantList;

    }


    private void addRestaurantToRestaurantMap(Restaurant restaurant, String s) {

        List<Restaurant> restaurantList = new ArrayList<>();

        if (mapRestaurantsSearch.containsKey(s)) {
            if (mapRestaurantsSearch.get(s).isEmpty()) {
                restaurantList.add(restaurant);
                mapRestaurantsSearch.get(s).addAll(restaurantList);
            }
            mapRestaurantsSearch.get(s).add(restaurant);
        } else {
            restaurantList.add(restaurant);
            mapRestaurantsSearch.put(s, restaurantList);
        }

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


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Intent intent;

        if (id == R.id.connect_menu_guest_item) {

            Toast.makeText(this, "כניסה לאורל", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.answers_questions_menu_guest_item || id == R.id.answers_questions_drawer_menu_login) {

            Toast.makeText(this, "שאלות ותשובות", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.communicate_us_menu_guest_item || id == R.id.make_contact_us_drawer_menu_login) {

            Toast.makeText(this, "צרו קשר", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.share_with_friends_menu_guest_item || id == R.id.share_with_friends_drawer_menu_login) {

            Toast.makeText(this, "שתף עם חברים", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.rate_us_menu_guest_item || id == R.id.rate_us_drawer_menu_login) {

            Toast.makeText(this, "דרגו אותנו", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.term_and_conditions_menu_guest_item || id == R.id.terms_condition_drawer_menu_login) {

            Toast.makeText(this, "SHARE", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.general_settings_menu_guest_item || id == R.id.general_settings_drawer_menu_login) {

            Toast.makeText(this, "הגדרות כלליות", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.profile_details_menu_drawer_login) {

            Toast.makeText(this, "פרטים אישיים", Toast.LENGTH_SHORT).show();

            intent = new Intent(this, UserProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);

        } else if (id == R.id.my_address_menu_drawer_login) {

            Toast.makeText(this, "הכתובות שלי", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, UserAddressListActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);

        } else if (id == R.id.credit_cards_menu_drawer_login) {


            Toast.makeText(this, "אמצעי תשלום", Toast.LENGTH_SHORT).show();

        }else if (id == R.id.log_out_settings_drawer_menu_login){

            //LOG OUT USER AND BACK TO THE WELCOME ACTIVITY WITH NO HISTORY

        }

        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) drawerLayout.openDrawer(Gravity.LEFT);
        else drawerLayout.closeDrawer(Gravity.LEFT);


        return true;
    }


    @Override
    public void onRestaurantTypeClicked(RestaurantTypeClass restaurantTypeClass) {

        if (restaurantTypeClass.getTitle().equals(this.getString(R.string.NISHNUSHIM))) {

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_home_page_activity, new NishnushimHomeFragment()).commit();

        } else {

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_home_page_activity, new RestaurantsFragment()).commit();

        }


    }


    private void filter(String text, List<String> resultSearchList) {
        //new array list that will hold the filtered data
        ArrayList<String> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (String s : resultSearchList) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }


    }


}