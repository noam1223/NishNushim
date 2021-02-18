package com.example.nishnushim.nishnushFragments.fragments_restaurant_profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.nishnushim.helpclasses.Menu;
import com.example.nishnushim.helpclasses.helpInterfaces.MenuItemListener;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Restaurant;
import com.example.nishnushim.helpclasses.helpInterfaces.OnProfileScrollChangeListener;
import com.example.nishnushim.helpclasses.menuChanges.Changes;
import com.example.nishnushim.helpclasses.menuChanges.PizzaChange;
import com.example.nishnushim.helpclasses.menuChanges.RegularChange;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class MenuRestaurantFragment extends Fragment implements MenuItemListener {

    View cartPopUpView;
    TextView sumCartPopUpTextView;


    RecyclerView subTitlesRecyclerView, menuRestaurantRecyclerView;
    RecyclerView.Adapter subTitleAdapter, menuRestaurantAdapter;
    NonScrolledRecyclerView layoutManager;

    Restaurant restaurant;
    String keyRestaurant;

    private static final int RETURN_DISH_CHANGE = 10001;


    //CART WILL BE MENU BECAUSE : BREAKFASTS, SPECIAL, DRINKS, DESSERTS
    Menu cartClassification;

    OnProfileScrollChangeListener onProfileScrollChangeListener;


    public MenuRestaurantFragment(OnProfileScrollChangeListener onProfileScrollChangeListener) {
        this.onProfileScrollChangeListener = onProfileScrollChangeListener;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                if (data != null && data.getSerializableExtra("dish_list") != null) {

                    @SuppressWarnings("unchecked")
                    Classification createdDihList = (Classification) data.getSerializableExtra("dish_list");

                    if (createdDihList.getDishList().size() > 0) {

                        if (cartClassification.getClassifications().size() == 0) {

                            cartClassification.getClassifications().add(createdDihList);

                        } else {

                            for (int i = 0; i < cartClassification.getClassifications().size(); i++) {

                                if (cartClassification.getClassifications().get(i).getClassificationName().equals(createdDihList.getClassificationName())) {
                                    cartClassification.getClassifications().get(i).getDishList().addAll(createdDihList.getDishList());
                                }

                            }
                        }
                        updatePopUpCart();
                    }
                }
            }
        }
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


        //TODO: working of pop up cart cost of order
        sumCartPopUpTextView = cartPopUpView.findViewById(R.id.sum_of_cart_text_view_cart_pop_up_window);

        subTitlesRecyclerView = view.findViewById(R.id.sub_titles_recycler_view_menu_restaurant_fragment);
        menuRestaurantRecyclerView = view.findViewById(R.id.menu_recycler_view_restaurant_menu_fragment);


        SharedPreferences sp = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String cartJsonString = sp.getString(getString(R.string.app_name), null);


        if (cartJsonString != null) {
            cartClassification = gson.fromJson(cartJsonString, Menu.class);
        } else {
            cartClassification = new Menu();
        }



        if (getArguments() != null) {

            restaurant = (Restaurant) getArguments().getSerializable(getString(R.string.restaurant_detail));
            keyRestaurant = getArguments().getString("key");


            initializeMenuRestaurantRecyclerView();
            initializeSubTitlesRecyclerView();
        }


        updatePopUpCart();

        if (menuRestaurantAdapter != null) {

            while (menuRestaurantAdapter.getItemCount() < restaurant.getMenu().getClassifications().size()) {
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

        layoutManager = (NonScrolledRecyclerView) new NonScrolledRecyclerView(getContext(), RecyclerView.VERTICAL, false);
        layoutManager.canScrollVertically();
        menuRestaurantRecyclerView.setLayoutManager(layoutManager);
        menuRestaurantAdapter = new RestaurantMenuAdapter(getContext(), restaurant.getMenu(), cartClassification, onProfileScrollChangeListener, restaurant.getName());
        menuRestaurantRecyclerView.setAdapter(menuRestaurantAdapter);


    }



    public void enableMenuRecyclerViewScrolling(boolean enabled) {


//        layoutManager.enableVersticleScroll(enabled);
//        layoutManager.canScrollVertically();
    }



    @Override
    public void changeMenuItemPosition(int position) {

        menuRestaurantRecyclerView.smoothScrollToPosition(position);

    }




    public void updatePopUpCart() {

        int sum = updateSum();

        if (sum == 0) {
            cartPopUpView.setVisibility(View.GONE);
        } else {

            cartPopUpView.setVisibility(View.VISIBLE);
            sumCartPopUpTextView.setText(sum + " ₪ ");
        }
    }





    public int updateSum() {

        int sum = 0;

        for (int k = 0; k < cartClassification.getClassifications().size(); k++) {

            Classification classification = cartClassification.getClassifications().get(k);

            for (int i = 0; i < classification.getDishList().size(); i++) {

                sum += classification.getDishList().get(i).getPrice();

                for (int j = 0; j < classification.getDishList().get(i).getChanges().size(); j++) {

                    if (classification.getDishList().get(i).getChanges().get(j).getChangesByTypesList().size() > 0) {

                        Changes changes = classification.getDishList().get(i).getChanges().get(j);
                        Changes.ChangesTypesEnum changesTypesEnum = changes.getChangesTypesEnum();

                        if (classification.getDishList().get(i).getChanges().get(j).getChangesByTypesList().size() > 0) {

                            for (int l = 0; l < classification.getDishList().get(i).getChanges().get(j).getChangesByTypesList().size(); l++) {

                                if (changes.getFreeSelection() < changes.getChangesByTypesList().size()) {

                                    sum += getCostSum(sum, l, changes, changesTypesEnum);

                                }
                            }
                        }
                    }
                }
            }
        }

        return sum;
    }




    private int getCostSum(int sum, int j, Changes changes, Changes.ChangesTypesEnum changesTypesEnum) {


        if (changesTypesEnum == Changes.ChangesTypesEnum.DISH_CHOICE || changesTypesEnum == Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE) {

            //WORK ON UPDATE SUM OF DISH CHANGE CHOICE
            Dish dish;

            try {
                HashMap<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(j);
                dish = new Gson().fromJson(new Gson().toJson(map), Dish.class);
            } catch (Exception e) {
                dish = (Dish) changes.getChangesByTypesList().get(j);
            }


            if (dish.getChanges().size() > 0) {
                for (int k = 0; k < dish.getChanges().size(); k++) {

                    if (dish.getChanges().get(k).getChangesByTypesList().size() > 0) {

                        if (changes.getFreeSelection() == changes.getChangesByTypesList().size()) {

                            //NO NEED TO ADD CHANGE COST
                            continue;
                        }

                        //GET COST OF ALL CHANGES
                        sum += getCostSum(sum, j, dish.getChanges().get(k), dish.getChanges().get(k).getChangesTypesEnum());
                    }
                }

            }


        } else if (changesTypesEnum == Changes.ChangesTypesEnum.ONE_CHOICE) {

            RegularChange regularChange;

            try {
                HashMap<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(j);
                regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
            } catch (Exception e) {
                regularChange = (RegularChange) changes.getChangesByTypesList().get(j);
            }

            sum += regularChange.getPrice();


        } else if (changesTypesEnum == Changes.ChangesTypesEnum.MULTIPLE) {


            RegularChange regularChange;

            try {
                HashMap<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(j);
                regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
            } catch (Exception e) {
                regularChange = (RegularChange) changes.getChangesByTypesList().get(j);
            }


            sum += regularChange.getPrice();


        } else if (changesTypesEnum == Changes.ChangesTypesEnum.VOLUME) {


            RegularChange regularChange;

            try {
                HashMap<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(j);
                regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
            } catch (Exception e) {
                regularChange = (RegularChange) changes.getChangesByTypesList().get(j);
            }

            sum += regularChange.getPrice();


        } else if (changesTypesEnum == Changes.ChangesTypesEnum.PIZZA) {

            PizzaChange pizzaChange;

            try {
                HashMap<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(j);
                pizzaChange = new Gson().fromJson(new Gson().toJson(map), PizzaChange.class);
            } catch (Exception e) {
                pizzaChange = (PizzaChange) changes.getChangesByTypesList().get(j);
            }

            if (pizzaChange.isBothSides() || pizzaChange.isLeftSide() || pizzaChange.isRightSide()) {
                sum += pizzaChange.getCost();
            }

        }
        return sum;
    }

}