package com.example.nishnushim.nishnushFragments.fragments_restaurant_profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.nishnushim.CartActivity;
import com.example.nishnushim.R;
import com.example.nishnushim.adapters.RestaurantMenuAdapter;
import com.example.nishnushim.adapters.SubTitleAdapter;
import com.example.nishnushim.helpUIClass.CreateCustomBtn;
import com.example.nishnushim.helpUIClass.MyRecycler;
import com.example.nishnushim.helpUIClass.NonScrolledRecyclerChild;
import com.example.nishnushim.helpUIClass.NonScrolledRecyclerView;
import com.example.nishnushim.helpclasses.Dish;
import com.example.nishnushim.helpclasses.DishChanges;
import com.example.nishnushim.helpclasses.helpInterfaces.CartListener;
import com.example.nishnushim.helpclasses.helpInterfaces.MenuItemListener;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Restaurant;
import com.example.nishnushim.helpclasses.helpInterfaces.OnProfileScrollChangeListener;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MenuRestaurantFragment extends Fragment implements MenuItemListener, CartListener {

    View cartPopUpView;
    TextView sumCartPopUpTextView;

    private static final String TAG = "RECYCLER TOUCH TAG";
    RecyclerView subTitlesRecyclerView, menuRestaurantRecyclerView;
    RecyclerView.Adapter subTitleAdapter, menuRestaurantAdapter;
    NonScrolledRecyclerView layoutManager;

    Restaurant restaurant;
    String keyRestaurant;

    //CART WILL BE MENU BECAUSE : BREAKFASTS, SPECIAL, DRINKS, DESSERTS
    Classification cartClassification;

    OnProfileScrollChangeListener onProfileScrollChangeListener;


    public MenuRestaurantFragment(OnProfileScrollChangeListener onProfileScrollChangeListener) {
        this.onProfileScrollChangeListener = onProfileScrollChangeListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_restaurant, container, false);

        cartPopUpView = view.findViewById(R.id.cart_pop_up_menu_restaurant_fragment);


        cartPopUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CartActivity.class);
                intent.putExtra("cart", cartClassification);
                intent.putExtra(getString(R.string.restaurant_detail), restaurant);
                intent.putExtra("key", keyRestaurant);
                startActivity(intent);
            }
        });


        sumCartPopUpTextView = cartPopUpView.findViewById(R.id.sum_of_cart_text_view_cart_pop_up_window);

        subTitlesRecyclerView = view.findViewById(R.id.sub_titles_recycler_view_menu_restaurant_fragment);
        menuRestaurantRecyclerView = view.findViewById(R.id.menu_recycler_view_restaurant_menu_fragment);


        SharedPreferences sp = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String cartJsonString = sp.getString(getString(R.string.app_name), null);


        if (cartJsonString != null) {
            cartClassification = gson.fromJson(cartJsonString, Classification.class);
        } else {
            cartClassification = new Classification();
            cartClassification.setClassificationName("קופה");
        }


        if (getArguments() != null) {


            restaurant = (Restaurant) getArguments().getSerializable(getString(R.string.restaurant_detail));
            keyRestaurant = getArguments().getString("key");


            initializeMenuRestaurantRecyclerView();
            initializeSubTitlesRecyclerView();
        }


        updateCartSum();

        if (menuRestaurantAdapter != null) {

            while (menuRestaurantAdapter.getItemCount() < restaurant.getMenu().getClassifications().size()){
                menuRestaurantRecyclerView.smoothScrollToPosition(menuRestaurantAdapter.getItemCount() - 1);
            }
            menuRestaurantRecyclerView.smoothScrollToPosition(0);
        }

        return view;
    }




    private void initializeSubTitlesRecyclerView() {

        subTitlesRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        subTitlesRecyclerView.setLayoutManager(layoutManager);
        subTitleAdapter = new SubTitleAdapter(getContext(), restaurant.getMenu().getClassifications(), this);
        subTitlesRecyclerView.setAdapter(subTitleAdapter);

    }




    private void initializeMenuRestaurantRecyclerView() {

//        menuRestaurantRecyclerView.setHasFixedSize(false);
//        menuRestaurantRecyclerView.setNestedScrollingEnabled(false);
        layoutManager = (NonScrolledRecyclerView) new NonScrolledRecyclerView(getContext(), RecyclerView.VERTICAL, false);
        layoutManager.canScrollVertically();
//        layoutManager.enableVersticleScroll(false);
        menuRestaurantRecyclerView.setLayoutManager(layoutManager);
        menuRestaurantAdapter = new RestaurantMenuAdapter(getContext(), restaurant.getMenu(), cartClassification, this, onProfileScrollChangeListener, restaurant.getName());
        menuRestaurantRecyclerView.setAdapter(menuRestaurantAdapter);

//        menuRestaurantRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//
//                if (dy > 0) {
//                    //scroll up
//                    //when user want to scroll down the menu the screen needs to scroll up
//
//                    Log.i("SCROLL UP", "dy: " + dy);
//                    Log.i("SCROLLED", onProfileScrollChangeListener.onScrollScreenUp() + "");
//
//                    ///NEED TO ENABLE RECYCLER VIEW FROM RESTAURANT PROFILE ACTIVITY
//
////                    if (onProfileScrollChangeListener.onScrollScreenUp()) {
////                        layoutManager.enableVersticleScroll(true);
////                    }else layoutManager.enableVersticleScroll(false);
////
////
////                    layoutManager.canScrollVertically();
//
//
//                } else {
//                    //scroll down
//
//
//                    Log.i("SCROLL DOWN", "dy: " + dy);
//
//                }
//
//
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });

    }


    public void enableMenuRecyclerViewScrolling(boolean enabled) {


//        layoutManager.enableVersticleScroll(enabled);
//        layoutManager.canScrollVertically();
    }


    @Override
    public void changeMenuItemPosition(int position) {

        menuRestaurantRecyclerView.smoothScrollToPosition(position);

    }


    @Override
    public void addDishToCart(Dish dish) {

//        dish.setChanges(new ArrayList<>());
        cartClassification.getDishList().add(dish);

    }


    @Override
    public void removeDishFromCart(Dish dish) {

        for (int i = 0; i < cartClassification.getDishList().size(); i++) {

            if (cartClassification.getDishList().get(i).getName().equals(dish.getName())) {

                cartClassification.getDishList().remove(i);
                return;

            }

        }

    }


    @Override
    public void updateDishLongClicked(int ADAPTER_TAG, int dishPosition) {

//        ((RestaurantMenuAdapter) menuRestaurantAdapter).updateMenuLongPressed(ADAPTER_TAG, dishPosition);

    }


    @Override
    public void addChangesDishCart(Dish dish) {
//
//        Log.i("DISH", dish.getChanges().size() + "");
//
//        for (int i = 0; i < cartClassification.getDishList().size(); i++) {
//
//            if (cartClassification.getDishList().get(i).getName().equals(dish.getName())) {
//
//
//                if (dish.getChanges().size() > 0) {
//
//
//                    View popUpView = getLayoutInflater().inflate(R.layout.query_dish_changes_pop_up_window, null);
//                    Dialog queryDishDialog = new Dialog(getContext());
//                    queryDishDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    queryDishDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    queryDishDialog.setContentView(popUpView);
//
//                    if (queryDishDialog.getWindow() != null)
//                        queryDishDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
//
//
//                    RadioButton yesRadioBtn = popUpView.findViewById(R.id.yes_to_dish_changes_query_dish_changes_pop_up_window);
//                    RadioButton noRadioBtn = popUpView.findViewById(R.id.no_to_dish_changes_query_dish_changes_pop_up_window);
//
//
//                    int finalI = i;
//
//
//                    yesRadioBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            //TODO: OPEN CHANGES POP UP
//                            View popUpView = getLayoutInflater().inflate(R.layout.dish_changes_list_pop_up_window, null);
//                            Dialog changesListDialog = new Dialog(getContext());
//                            changesListDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            changesListDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                            changesListDialog.setContentView(popUpView);
//
//                            if (changesListDialog.getWindow() != null)
//                                changesListDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
//
//
//                            LinearLayout changesLinearLayoutArea = popUpView.findViewById(R.id.linear_layout_checkboxes_area_dish_changes_list_pop_up_window);
//                            CheckBox firstChangeCheckBox = popUpView.findViewById(R.id.radio_btn_change_example_dish_changes_list_pop_up_window);
//
//                            firstChangeCheckBox.setTag(dish.getChanges().get(0));
//                            firstChangeCheckBox.setText(dish.getChanges().get(0).getChange());
//
//
//                            //TODO: WORKING WITH CHECK BOX ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                            ////////////////////////////////////////////////////////////// NOT FINISHED //////////////////////////////////////////////////////////////
//                            for (int j = 1; j < dish.getChanges().size(); j++) {
//                                CheckBox checkBox = CreateCustomBtn.createCheckBox(getContext(), firstChangeCheckBox, j, 1, dish.getChanges().get(j).getChange(), dish.getChanges().get(j));
//
//                                int finalJ = j;
//                                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                                    @Override
//                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                                        if (isChecked) {
//                                            cartClassification.getDishList().get(finalI).getChangesOrder().add(dish.getChanges().get(finalJ));
//                                        } else
//                                            cartClassification.getDishList().get(finalI).getChangesOrder().remove(dish.getChanges().get(finalJ));
//
//                                    }
//                                });
//
//                                changesLinearLayoutArea.addView(checkBox);
//                            }
//
//
//                            queryDishDialog.dismiss();
//
//
//                            changesListDialog.show();
//
//                            Window window = changesListDialog.getWindow();
//                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                            window.setGravity(Gravity.TOP);
//
//                        }
//                    });
//
//
//                    noRadioBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            queryDishDialog.dismiss();
//
//                        }
//                    });
//
//
//                    queryDishDialog.show();
//
//                    Window window = queryDishDialog.getWindow();
//                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 180);
//                    window.setGravity(Gravity.TOP);
//
//                }
//
//            }
//
//        }
//
//
//        menuRestaurantAdapter.notifyDataSetChanged();
//
    }


    @Override
    public void updateCartSum() {

        if (cartClassification.getDishList().size() == 0) {
            cartPopUpView.setVisibility(View.INVISIBLE);
        } else {

            int sum = 0;

            for (int i = 0; i < cartClassification.getDishList().size(); i++) {
                sum += cartClassification.getDishList().get(i).getPrice();
            }

            cartPopUpView.setVisibility(View.VISIBLE);
            sumCartPopUpTextView.setText(sum + "₪");
        }

    }


    @Override
    public void onDestroy() {

        if (!cartClassification.getDishList().isEmpty()) {
            SharedPreferences sp = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            Gson gson = new Gson();
            String cartJson = gson.toJson(cartClassification);
            editor.putString("cart", cartJson);
            editor.apply();
        }

        super.onDestroy();
    }


}