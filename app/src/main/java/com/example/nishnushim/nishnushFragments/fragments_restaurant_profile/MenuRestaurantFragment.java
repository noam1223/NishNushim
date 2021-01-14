package com.example.nishnushim.nishnushFragments.fragments_restaurant_profile;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.nishnushim.R;
import com.example.nishnushim.adapters.RestaurantMenuAdapter;
import com.example.nishnushim.adapters.SubTitleAdapter;
import com.example.nishnushim.helpclasses.Dish;
import com.example.nishnushim.helpclasses.DishChanges;
import com.example.nishnushim.helpclasses.helpInterfaces.CartListener;
import com.example.nishnushim.helpclasses.helpInterfaces.MenuItemListener;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Restaurant;
import com.google.gson.Gson;


public class MenuRestaurantFragment extends Fragment implements MenuItemListener, CartListener {


    ScrollView menuScrollView;
    RecyclerView subTitlesRecyclerView, menuRestaurantRecyclerView;
    RecyclerView.Adapter subTitleAdapter, menuRestaurantAdapter;


    Restaurant restaurant;
    String keyRestaurant;

    Classification cartClassification;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_restaurant, container, false);

        subTitlesRecyclerView = view.findViewById(R.id.sub_titles_recycler_view_menu_restaurant_fragment);
        menuRestaurantRecyclerView = view.findViewById(R.id.menu_recycler_view_restaurant_menu_fragment);
        menuScrollView = view.findViewById(R.id.scroll_view_menu_restaurant_fragment);
        menuScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {



            }
        });


        SharedPreferences sp = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String cartJsonString = sp.getString(getString(R.string.app_name), null);


        if (cartJsonString != null) {
            cartClassification = gson.fromJson(cartJsonString, Classification.class);
        } else {
            cartClassification = new Classification();
            cartClassification.setClassificationName("עגלת קניות");
        }


        if (getArguments() != null) {


            restaurant = (Restaurant) getArguments().getSerializable(getString(R.string.restaurant_detail));
            keyRestaurant = getArguments().getString("key");


            initializeMenuRestaurantRecyclerView();
            initializeSubTitlesRecyclerView();
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

        menuRestaurantRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        menuRestaurantRecyclerView.setLayoutManager(layoutManager);
        menuRestaurantAdapter = new RestaurantMenuAdapter(getContext(), restaurant.getMenu(), cartClassification, this);
        menuRestaurantRecyclerView.setAdapter(menuRestaurantAdapter);

    }


    @Override
    public void changeMenuItemPosition(int position) {

        Toast.makeText(getContext(), "MENU ITEM POSITION CHANGE", Toast.LENGTH_SHORT).show();
        menuRestaurantRecyclerView.smoothScrollToPosition(position);
    }


    @Override
    public void addDishToCart(Dish dish) {

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

        ((RestaurantMenuAdapter) menuRestaurantAdapter).updateMenuLongPressed(ADAPTER_TAG, dishPosition);

    }




    @Override
    public void addChangesDishCart(Dish dish) {

        for (int i = 0; i < cartClassification.getDishList().size(); i++) {

            if (cartClassification.getDishList().get(i).getName().equals(dish.getName())){

                if (dish.getChanges().size() > 0){


                    View popUpView = getLayoutInflater().inflate(R.layout.query_dish_changes_pop_up_window, null);
                    Dialog queryDishDialog = new Dialog(getContext());
                    queryDishDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    queryDishDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    queryDishDialog.setContentView(popUpView);

                    if (queryDishDialog.getWindow() != null)
                        queryDishDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;


                    RadioButton yesRadioBtn = popUpView.findViewById(R.id.yes_to_dish_changes_query_dish_changes_pop_up_window);
                    RadioButton noRadioBtn = popUpView.findViewById(R.id.no_to_dish_changes_query_dish_changes_pop_up_window);


                    int finalI = i;

                    yesRadioBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //TODO: OPEN CHANGES POP UP
                            View popUpView = getLayoutInflater().inflate(R.layout.dish_changes_list_pop_up_window, null);
                            Dialog changesListDialog = new Dialog(getContext());
                            changesListDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            changesListDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            changesListDialog.setContentView(popUpView);

                            if (changesListDialog.getWindow() != null)
                                changesListDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;


                            RadioGroup changesListRadioGroup = popUpView.findViewById(R.id.dish_changes_radio_group_dish_changes_list_pop_up_window);
                            RadioButton firstChangeRadioBtn = popUpView.findViewById(R.id.radio_btn_change_example_dish_changes_list_pop_up_window);

                            firstChangeRadioBtn.setTag(dish.getChanges().get(0));

                            for (int j = 1; j < dish.getChanges().size(); j++) {
                                changesListRadioGroup.addView(createRadioButton(firstChangeRadioBtn, j, 1 ,dish.getChanges().get(j).getChange(), dish.getChanges().get(j)));
                            }


                            changesListRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {

                                    if (changesListRadioGroup.getCheckedRadioButtonId() != -1){

                                        RadioButton radioButton = popUpView.findViewById(changesListRadioGroup.getCheckedRadioButtonId());

                                        if (radioButton.isChecked()){

                                            cartClassification.getDishList().get(finalI).getChanges().add((DishChanges) radioButton.getTag());

                                        }else {

                                            for (int i = 0; i < cartClassification.getDishList().get(finalI).getChanges().size(); i++) {

                                                if (cartClassification.getDishList().get(finalI).getChanges().get(i).getChange().equals(dish.getName())){
                                                    cartClassification.getDishList().get(finalI).getChanges().remove(i);
                                                    break;
                                                }

                                            }

                                        }

                                    }

                                }
                            });






                            //EDN
                            queryDishDialog.dismiss();



                            changesListDialog.show();

                            Window window = changesListDialog.getWindow();
                            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            window.setGravity(Gravity.TOP);

                        }
                    });


                    noRadioBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            queryDishDialog.dismiss();

                        }
                    });



                    queryDishDialog.show();

                    Window window = queryDishDialog.getWindow();
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.TOP);

                }

            }

        }


        menuRestaurantAdapter.notifyDataSetChanged();

    }




    @Override
    public void updateCartSum() {

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




    public void onScrollDownListener() {
        menuScrollView.scrollTo(menuScrollView.getScrollX(), menuScrollView.getScrollY() + 1);
    }




    public boolean onScrollUpListener() {

        if (menuScrollView.getScrollY() > menuScrollView.getChildAt(0).getTop()) {
            menuScrollView.scrollTo(menuScrollView.getScrollX(), menuScrollView.getScrollY() - 1);
            return true;
        }
        return false;
    }




    private RadioButton createRadioButton(RadioButton copyBtn, int size, int idNum, String text, DishChanges dishChanges) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setId(size + idNum);
        radioButton.setTag(dishChanges);
        radioButton.setLayoutParams(copyBtn.getLayoutParams());
        radioButton.setButtonDrawable(ContextCompat.getDrawable(getContext(), R.drawable.radio_button_inset_dish_changes));
        radioButton.setChecked(false);
        radioButton.setTypeface(ResourcesCompat.getFont(getContext(), R.font.assistant_regular));
        radioButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.radio_button_background_top_screen_customize));
        radioButton.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        radioButton.setPadding(copyBtn.getPaddingLeft(), copyBtn.getPaddingTop(), copyBtn.getPaddingRight(), copyBtn.getPaddingBottom());
        radioButton.setTag(String.valueOf(size));
        radioButton.setText(text);
        radioButton.setTextColor(ContextCompat.getColor(getContext(), R.color.custom_blue));
        radioButton.setTextSize(13);
        return radioButton;
    }

}