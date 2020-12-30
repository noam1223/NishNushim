package com.example.nishnushim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishnushim.adapters.RestaurantTypeAdapter;

import com.example.nishnushim.helpUIClass.RestaurantTypeListener;
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

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, RestaurantTypeListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    RelativeLayout toolBarRelativeLayout;

    ImageButton searchImgBtn, filterImageBtn, menuImageBtn, myAddressArrowImageBtn;
    TextView addressAppBarTextView;

    //    EditText searchRestaurantByStringEditTxt;
    FrameLayout frameLayout;
    Drawable frameDrawable, filterDrawable, wrapDrawable;


    User user;
    NavigationView navigationView;

    RecyclerView typeRestaurantRecyclerView;
    RecyclerView.Adapter typeRestaurantAdapter;

    FirebaseFirestore db;
    FirebaseAuth auth;

    List<Button> filterBtnList = new ArrayList<>();
    View searchPopUpView;
    Dialog filterDialog, searchDialog, myAddressDialog;
    Button timeDeliveryFilterBtn, distanceFilterBtn, minAmountOfMoneyForDeliverFilterBtn, deliveryAmountFilterBtn, creditsFilterBtn, recommendationFilterBtn;

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
        frameLayout = findViewById(R.id.nav_host_fragment_home_page_activity);
        frameDrawable = frameLayout.getBackground();


        searchImgBtn.setOnClickListener(this);
        filterImageBtn.setOnClickListener(this);
        menuImageBtn.setOnClickListener(this);
        myAddressArrowImageBtn.setOnClickListener(this);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        initializeUserPicturesRecyclerView();


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        //TODO: DO NOT FORGET TO REMOVE THE STRING KEY
        //"LCl46tZA2wd53H8If1mWTb2VjGw2"
        db.collection(getString(R.string.USERS_DB)).document("LCl46tZA2wd53H8If1mWTb2VjGw2").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    user = task.getResult().toObject(User.class);

                    if (user != null)
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_home_page_activity, new NishnushimHomeFragment()).commit();
                    else
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_home_page_activity, new NishnushinAnonymousUserFragment()).commit();


                    addressAppBarTextView.setText(user.getChosenAddressString());


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

            }
        });


    }


    private void initializeUserPicturesRecyclerView() {

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
//            Animation animate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_y_direction);
//            animate.setDuration(500);
//            areaSearchLinearLayout.startAnimation(animate);

            EditText searchEditText = popUpView.findViewById(R.id.edit_text_search_restaurant_by_string_search_pop_up_window);


            LinearLayout resultsListsLinearLayout = popUpView.findViewById(R.id.results_lists_linear_layout_area_serach_pop_up_window);

            ListView searchResultsListView, historyResultsListView;

            searchResultsListView = popUpView.findViewById(R.id.search_list_view_search_pop_up_window_layout);
            historyResultsListView = popUpView.findViewById(R.id.history_of_search_list_view_search_pop_up_window_layout);


//            toolBarRelativeLayout.setVisibility(View.INVISIBLE);
//            searchRestaurantByStringEditTxt.setVisibility(View.VISIBLE);
//            searchRestaurantByStringEditTxt.setClickable(true);
//
//
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

            filterBtnList.add(timeDeliveryFilterBtn);
            filterBtnList.add(distanceFilterBtn);
            filterBtnList.add(minAmountOfMoneyForDeliverFilterBtn);
            filterBtnList.add(deliveryAmountFilterBtn);
            filterBtnList.add(creditsFilterBtn);
            filterBtnList.add(recommendationFilterBtn);


            for (int i = 0; i < filterBtnList.size(); i++) {
                int finalI = i;
                filterBtnList.get(i).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            Drawable[] drawables = filterBtnList.get(finalI).getCompoundDrawables();
                            wrapDrawable = DrawableCompat.wrap(drawables[1]).mutate();
                            filterDrawable = wrapDrawable;
                            DrawableCompat.setTint(wrapDrawable, getResources().getColor(R.color.white));
                            filterBtnList.get(finalI).setCompoundDrawables(null, wrapDrawable, null, null);
                            wrapDrawable = null;

                        } else if (event.getAction() == MotionEvent.ACTION_UP) {

                            filterBtnList.get(finalI).setCompoundDrawables(null, filterDrawable, null, null);
                            filterDrawable = null;

                            Toast.makeText(getBaseContext(), ((Button) v).getText().toString(), Toast.LENGTH_SHORT).show();


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

            List<RadioButton> radioButtonList = new ArrayList<>();
            final RadioGroup wayOfDeliveryRadioGroup, myAddressListRadioGroup;
            wayOfDeliveryRadioGroup = popUpView.findViewById(R.id.way_of_delivery_radio_group_address_list_pop_up_window);
            myAddressListRadioGroup = popUpView.findViewById(R.id.list_of_address_radio_group_address_list_pop_up_window);

            RadioButton wayOfDeliveryRadioBtn;
            final RadioButton myAddressListRadioBtn;

            RadioButton myLocationToDeliverRadioBtn = popUpView.findViewById(R.id.my_location_address_to_deliver_radio_btn_address_list_pop_up_window);
            radioButtonList.add(myLocationToDeliverRadioBtn);


            for (int i = 0; i < user.getAddresses().size(); i++) {

                RadioButton radioButton = createRadioButton(myLocationToDeliverRadioBtn, i, 1, R.drawable.ic_icon_placeholder_small, user.getChosenAddressString());

                radioButtonList.add(radioButton);
                myAddressListRadioGroup.addView(radioButton);

            }

            RadioButton radioButton = createRadioButton(myLocationToDeliverRadioBtn, user.getAddresses().size(), 3, R.drawable.ic_icon_add_button, "אני כרגע נמצא בכתובת אחרת");
            radioButtonList.add(radioButton);
            myAddressListRadioGroup.addView(radioButton);


            wayOfDeliveryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (wayOfDeliveryRadioGroup.getCheckedRadioButtonId() != -1) {
                        RadioButton radioButton1 = popUpView.findViewById(wayOfDeliveryRadioGroup.getCheckedRadioButtonId());
                        Toast.makeText(HomePageActivity.this, radioButton1.getText().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });


            myAddressListRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (myAddressListRadioGroup.getCheckedRadioButtonId() != -1) {
                        RadioButton radioButton1 = popUpView.findViewById(myAddressListRadioGroup.getCheckedRadioButtonId());
                        Toast.makeText(HomePageActivity.this, radioButton1.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            myAddressDialog.show();

            Window window = myAddressDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.TOP);

        }
    }

    private RadioButton createRadioButton(RadioButton myLocationToDeliverRadioBtn, int size, int idNum, int drawableResource, String text) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setId(size + idNum);
        radioButton.setLayoutParams(myLocationToDeliverRadioBtn.getLayoutParams());
        radioButton.setButtonDrawable(ContextCompat.getDrawable(this, R.drawable.radio_button_inset));
        radioButton.setChecked(false);
        radioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, drawableResource), null);
        radioButton.setCompoundDrawablePadding(24);
        radioButton.setTypeface(ResourcesCompat.getFont(this, R.font.assistant_regular));
        radioButton.setBackground(ContextCompat.getDrawable(this, R.drawable.radio_button_background_top_screen_customize));
        radioButton.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        radioButton.setPadding(myLocationToDeliverRadioBtn.getPaddingLeft(), myLocationToDeliverRadioBtn.getPaddingTop(), myLocationToDeliverRadioBtn.getPaddingRight(), myLocationToDeliverRadioBtn.getPaddingBottom());

        radioButton.setText(text);
        radioButton.setTextColor(ContextCompat.getColor(this, R.color.custom_blue));
        radioButton.setTextSize(13);
        return radioButton;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Intent intent;

        if (id == R.id.profile_detail_menu_item) {

            Toast.makeText(this, "PROFILE", Toast.LENGTH_SHORT).show();

            intent = new Intent(HomePageActivity.this, ProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);

        } else if (id == R.id.address_list_menu_item) {

            Toast.makeText(this, "ADDRESS", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.credit_cards_list_menu_item) {

            Toast.makeText(this, "CREDIT", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.question_answers_menu_item) {

            Toast.makeText(this, "QUESTIONS", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.contact_us_menu_item) {

            Toast.makeText(this, "CONTACT", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.app_share_menu_item) {

            Toast.makeText(this, "SHARE", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.rate_menu_item) {

            Toast.makeText(this, "RATE", Toast.LENGTH_SHORT).show();


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


}