package com.example.nishnushim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nishnushim.adapters.ChangesAdapter;
import com.example.nishnushim.adapters.PizzaChangeItem;
import com.example.nishnushim.helpUIClass.ChangePizzaHelper;
import com.example.nishnushim.helpUIClass.CreateCustomBtn;
import com.example.nishnushim.helpUIClass.NonScrollListView;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Dish;
import com.example.nishnushim.helpclasses.menuChanges.Changes;
import com.example.nishnushim.helpclasses.menuChanges.PizzaChange;
import com.example.nishnushim.helpclasses.menuChanges.RegularChange;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AddDishActivity extends AppCompatActivity {

    View cartPopUpView;
    TextView restaurantNameTextView, dishNameTextView, dishDetailNameTextView, dishPriceNameTextView, dishNumNameTextView, sumCartPopUpTextView;
    ImageButton backImageBtn, plusDishImgBtn, minusDishImgBtn;
    Button moveToDishBtn;
    LinearLayout dishBtnAreaLinearLayout;
    ImageView dishImageView;
    ListView changesDishListView;

    //PIZZA CHANGE
    ConstraintLayout pizzaImgAreaConstrainLayout;
    RelativeLayout pizzaChangeImagesRelativeLayout;
    List<View> pizzaChangesImagesIncludeViewList = new ArrayList<>();
    Button openPizzaChangesBtn;


    Classification classificationOfDish;
    Dish dish, createDish;
    Classification dishList;
    //    List<Dish> dishList = new ArrayList<>();
    List<Button> dishBtn = new ArrayList<>();
    int dishBtnClickedPosition = 0;
    public String restaurantNameString = "נשנושים";
    int positionChanged = 0;
    Dialog pizzaChangesDialog;


    int orderPosition;

    public String getRestaurantNameString() {
        return restaurantNameString;
    }


    public Classification getClassificationOfDish() {
        return classificationOfDish;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 2) {

                if (data != null && data.getSerializableExtra("dish_list") != null) {

                    int position = data.getIntExtra("position", 0);
                    @SuppressWarnings("unchecked")
                    Classification createdDihList = (Classification) data.getSerializableExtra("dish_list");

                    if (createdDihList.getDishList().size() > 0) {
                        createDish.getChanges().get(position).getChangesByTypesList().addAll(createdDihList.getDishList());
                    }
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);


        //TODO: DESIGN CLASSIFICATION CHOSEN, ADD ROUND BOX BUTTON
        // NEED TO SEND ALL MENU OR CREATE INSTANCE TO IT


        Intent intent = getIntent();

        if (intent != null) {

            //TODO: SEND DISH OBJECT

            if (intent.getSerializableExtra("dish") != null) {
                dish = (Dish) intent.getSerializableExtra("dish");
            }

            if (intent.getSerializableExtra("dish_edit") != null) {
                createDish = (Dish) intent.getSerializableExtra("dish_edit");
            }

            if (intent.getStringExtra("res_name") != null) {
                restaurantNameString = intent.getStringExtra("res_name");
            }


            //WORKING ON CHANGES OF THIS DISH
            if (intent.getSerializableExtra("classification") != null) {
                classificationOfDish = (Classification) intent.getSerializableExtra("classification");
            }

            orderPosition = intent.getIntExtra("position", 0);

        }


        cartPopUpView = findViewById(R.id.cart_pop_up_add_dish_activity);
        sumCartPopUpTextView = cartPopUpView.findViewById(R.id.sum_of_cart_text_view_cart_pop_up_window);
        backImageBtn = findViewById(R.id.back_img_btn_add_dish_activity);
        restaurantNameTextView = findViewById(R.id.restaurant_name_text_view_add_dish_activity);
        dishNameTextView = findViewById(R.id.dish_name_text_view_add_dish_activity);
        dishDetailNameTextView = findViewById(R.id.dish_details_text_view_add_dish_activity);
        dishPriceNameTextView = findViewById(R.id.dish_price_text_view_add_dish_activity);
        dishNumNameTextView = findViewById(R.id.number_of_dishes_text_view_add_dish_activity);

        plusDishImgBtn = findViewById(R.id.add_dish_btn_add_dish_activity);
        minusDishImgBtn = findViewById(R.id.minus_dish_btn_add_dish_activity);

        dishBtnAreaLinearLayout = findViewById(R.id.linear_layout_btn_add_dish_activity);
        moveToDishBtn = findViewById(R.id.first_dish_btn_add_dish_activity);
        moveToDishBtn.setSelected(true);
        moveToDishBtn.setPressed(true);
        moveToDishBtn.setTag(createDish);


        //PIZZA CHANGE
        pizzaImgAreaConstrainLayout = findViewById(R.id.pizza_change_image_constrain_area_add_dish_activity);
        pizzaChangeImagesRelativeLayout = findViewById(R.id.relative_layout_pizza_image_area_add_dish_activity);
        openPizzaChangesBtn = findViewById(R.id.pizza_add_changes_btn_detail_add_dish_activity);


        moveToDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveToDishBtn.setBackground(ContextCompat.getDrawable(AddDishActivity.this, R.drawable.btn_bottom_line_selected));

                if (dishBtn.size() > 1) {
                    for (int i = 1; i < dishBtn.size(); i++) {
                        dishBtn.get(i).setBackground(ContextCompat.getDrawable(AddDishActivity.this, R.color.white));
                    }
                }

                positionChanged = 0;
                updateUI(positionChanged);
            }
        });


        dishBtn.add(moveToDishBtn);


        dishImageView = findViewById(R.id.dish_image_view_add_dish_activity);

        changesDishListView = findViewById(R.id.dish_changes_list_view_add_dish_activity);

//        saveDishBtn = findViewById(R.id.save_dish_btn_add_dish_activity);


        if (createDish == null) {
            createDish = (Dish) dish.clone();
            createDish.setChanges(new ArrayList<>());

        } else {

            plusDishImgBtn.setVisibility(View.INVISIBLE);
            minusDishImgBtn.setVisibility(View.INVISIBLE);
            moveToDishBtn.setVisibility(View.GONE);

        }

        dishList = new Classification();
        dishList.setClassificationName(classificationOfDish.getClassificationName());
        dishList.getDishList().add(createDish);

        restaurantNameTextView.setText(restaurantNameString);
        updateUI(0);
        updateSum();


        plusDishImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //TODO: WORKING WITH LIST AS NUMBER OF DISHES ADDED
                //TODO: ADD NUMBER OF DISHES

                Dish dishClone = (Dish) dish.clone();
                dishClone.setChanges(new ArrayList<>());
                dishList.getDishList().add(dishClone);
                Button button = CreateCustomBtn.createDishBtn(AddDishActivity.this, moveToDishBtn, dishList.getDishList().size() + 1, 3, "מנה " + dishList.getDishList().size(), dishClone);
                positionChanged = dishList.getDishList().size() - 1;
                updateUI(positionChanged);


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        positionChanged = 0;

                        for (int i = 0; i < dishBtn.size(); i++) {

                            if (dishBtn.get(i).getText().toString().equals(((Button) v).getText().toString())) {
                                dishBtn.get(i).setBackground(ContextCompat.getDrawable(AddDishActivity.this, R.drawable.btn_bottom_line_selected));
                                positionChanged = i;

                            } else
                                dishBtn.get(i).setBackground(ContextCompat.getDrawable(AddDishActivity.this, R.color.white));

                        }

                        updateUI(positionChanged);

                    }
                });

                dishBtn.add(button);


                dishBtnAreaLinearLayout.setWeightSum(dishBtnAreaLinearLayout.getWeightSum() + 1);
                dishBtnAreaLinearLayout.addView(button);
                updateSum();

            }
        });


        minusDishImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dishList.getDishList().size() > 1) {

                    for (int i = 0; i < dishBtn.size(); i++) {

                        if (dishBtn.get(i).isPressed()) {
                            dishList.getDishList().remove(i);
                            dishBtn.remove(i);
                            dishBtnAreaLinearLayout.removeView(dishBtn.get(i));
                            dishBtnAreaLinearLayout.setWeightSum(dishBtnAreaLinearLayout.getWeightSum() - 1);
                            break;

                        }
                    }

//                    updateUI(0);
                    moveToDishBtn.performClick();
                    updateSum();

                }
            }
        });


        backImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("dish_list", (Serializable) dishList);
                intent.putExtra("position", orderPosition);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }


    private void updateUI(int position) {


        dishNameTextView.setText(dishList.getDishList().get(position).getName());
        dishDetailNameTextView.setText(dishList.getDishList().get(position).getDetails());
        dishPriceNameTextView.setText(String.valueOf(dishList.getDishList().get(position).getPrice()));


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("מספר מנות: ").append(dishList.getDishList().size());
        dishNumNameTextView.setText(stringBuilder);


        if (changesDishListView.getAdapter() != null) {
            changesDishListView.setAdapter(null);
        }


        changesDishListView.setAdapter(new ChangesAdapter(AddDishActivity.this, dish.getChanges(), classificationOfDish.getChangesList(), dishList.getDishList().get(position)));
        setListViewHeightBasedOnChildren(changesDishListView);

        updateSum();

    }


    public void updatePizzaChangesOnImage(int listPosition, int itemPosition, int imgSrc) {

        View pizzaChangeInclude;
        PizzaChange pizzaChange = ((PizzaChange) dishList.getDishList().get(positionChanged).getChanges().get(listPosition).getChangesByTypesList().get(itemPosition));


        if (pizzaChange.isBothSides()) {

            pizzaChangeInclude = getLayoutInflater().inflate(R.layout.pizza_changes_both_sides_include_layout, null);
            pizzaChangeInclude.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ConstraintLayout rightSidesConstrainLayout = pizzaChangeInclude.findViewById(R.id.constrain_layout_right_side_pizza_changes_both_sides_include_layout);
            ConstraintLayout leftSidesConstrainLayout = pizzaChangeInclude.findViewById(R.id.constrain_layout_left_side_pizza_changes_both_sides_include_layout);


            for (int i = 0; i < rightSidesConstrainLayout.getChildCount(); i++) {

                for (int j = 0; j < rightSidesConstrainLayout.getChildCount(); j++) {
                    ((ImageView) rightSidesConstrainLayout.getChildAt(j)).setImageDrawable(ContextCompat.getDrawable(AddDishActivity.this, imgSrc));
                }

            }


            for (int i = 0; i < leftSidesConstrainLayout.getChildCount(); i++) {
                for (int j = 0; j < leftSidesConstrainLayout.getChildCount(); j++) {
                    ((ImageView) leftSidesConstrainLayout.getChildAt(j)).setImageDrawable(ContextCompat.getDrawable(AddDishActivity.this, imgSrc));
                }

            }


            pizzaChangeInclude.setId(View.generateViewId());
            pizzaChangeInclude.setVisibility(View.VISIBLE);

            pizzaChangeImagesRelativeLayout.addView(pizzaChangeInclude);
            pizzaChangesImagesIncludeViewList.add(pizzaChangeInclude);


        } else {

            pizzaChangeInclude = getLayoutInflater().inflate(R.layout.pizza_changes_include_layout, null);


            ConstraintLayout bothSidesConstrainLayout = pizzaChangeInclude.findViewById(R.id.constrain_layout_pizza_changes_include_layout);

            pizzaChangeInclude.setId(View.generateViewId());
            pizzaChangeInclude.setVisibility(View.VISIBLE);

            pizzaChangeImagesRelativeLayout.addView(pizzaChangeInclude);
            pizzaChangesImagesIncludeViewList.add(pizzaChangeInclude);


            for (int i = 0; i < bothSidesConstrainLayout.getChildCount(); i++) {
                ((ImageView) bothSidesConstrainLayout.getChildAt(i)).setImageDrawable(ContextCompat.getDrawable(AddDishActivity.this, imgSrc));
            }


            if (pizzaChange.isRightSide()) {
                ((RelativeLayout.LayoutParams) pizzaChangeInclude.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }


            if (pizzaChange.isLeftSide()) {
                ((RelativeLayout.LayoutParams) pizzaChangeInclude.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                pizzaChangeInclude.setRotation(180);
            }

        }

    }


    public void updateSidesOfPizzas(int position) {

        pizzaChangeImagesRelativeLayout.removeView(pizzaChangesImagesIncludeViewList.get(position));
        pizzaChangesImagesIncludeViewList.remove(position);

    }


    public void updatePizzaUI(int pizzaChangePosition, int listPosition) {

        pizzaImgAreaConstrainLayout.setVisibility(View.VISIBLE);
        dishImageView.setVisibility(View.GONE);


        openPizzaChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //OPEN PIZZA DISH POP UP
                View popUpView = LayoutInflater.from(AddDishActivity.this).inflate(R.layout.pizza_dish_changes_list_pop_up_window, null);
                pizzaChangesDialog = new Dialog(AddDishActivity.this);
                pizzaChangesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                pizzaChangesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pizzaChangesDialog.setContentView(popUpView);


                if (pizzaChangesDialog.getWindow() != null)
                    pizzaChangesDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;


                TextView pizzaChangeHeadTextView = popUpView.findViewById(R.id.pizza_dish_name_text_view_dish_changes_list_pop_up_window);
                TextView pizzaChangeDetailsTextView = popUpView.findViewById(R.id.pizza_details_for_changes_text_view_dish_changes_list_pop_up_window);

                ListView pizzaChangesListView = popUpView.findViewById(R.id.pizza_changes_list_view_pizza_changes_list_pop_up_window);

                Button saveChangesBtn = popUpView.findViewById(R.id.pizza_save_dish_changes_btn_changes_list_pop_up_window);


                pizzaChangeHeadTextView.setText("בחירת תוספות לפיצה " + positionChanged);


                //DO NOT FORGET CHANGE IT LATER
                pizzaChangeDetailsTextView.setVisibility(View.GONE);


                pizzaChangesListView.setAdapter(new PizzaChangeItem(AddDishActivity.this, classificationOfDish.getChangesList().get(pizzaChangePosition), dishList.getDishList().get(positionChanged).getChanges().get(listPosition), listPosition));


                pizzaChangesDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        dishList.getDishList().get(positionChanged).getChanges().get(listPosition).getChangesByTypesList().clear();

                        for (int i = 0; i < pizzaChangesImagesIncludeViewList.size(); i++) {
                            pizzaChangeImagesRelativeLayout.removeView(pizzaChangesImagesIncludeViewList.get(i));
                        }

                        pizzaChangesImagesIncludeViewList.clear();
                        pizzaChangesListView.setAdapter(null);
                        pizzaChangesListView.setAdapter(new PizzaChangeItem(AddDishActivity.this, classificationOfDish.getChangesList().get(pizzaChangePosition), dishList.getDishList().get(positionChanged).getChanges().get(listPosition), listPosition));
                    }
                });


                saveChangesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //SAVE THE CHANGES FOR THIS PIZZA
                        pizzaChangesDialog.dismiss();

                        updateSum();
                    }
                });


                pizzaChangesDialog.show();

                Window window = pizzaChangesDialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.TOP);


            }
        });

    }


    public void addDishBtn(String classificationName, int listPosition) {


        Button button = CreateCustomBtn.createDishBtn(AddDishActivity.this, moveToDishBtn, dishList.getDishList().size() + 1, 3, classificationName + " " + dishList.getDishList().size(), null);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: SEND THE LIST VIEW TO THIS POSITION
                if (changesDishListView.getAdapter() != null) {
                    changesDishListView.smoothScrollToPosition(listPosition);
                }
            }
        });

        dishBtn.add(button);


        dishBtnAreaLinearLayout.setWeightSum(dishBtnAreaLinearLayout.getWeightSum() + 1);
        dishBtnAreaLinearLayout.addView(button);


    }


    public void updateSum() {

        int sum = 0;

        for (int i = 0; i < dishList.getDishList().size(); i++) {

            sum += dishList.getDishList().get(i).getPrice();

            for (int j = 0; j < dishList.getDishList().get(i).getChanges().size(); j++) {

                if (dishList.getDishList().get(i).getChanges().get(j).getChangesByTypesList().size() > 0) {

                    Changes changes = dishList.getDishList().get(i).getChanges().get(j);
                    Changes.ChangesTypesEnum changesTypesEnum = changes.getChangesTypesEnum();

                    if (dishList.getDishList().get(i).getChanges().get(j).getChangesByTypesList().size() > 0) {

                        if (changes.getFreeSelection() > changes.getChangesByTypesList().size()) {

                            sum += getCostSum(sum, j, changes, changesTypesEnum);

                        }

                    }
                }
            }

        }

        sumCartPopUpTextView.setText(sum + " ₪ ");
    }


    private int getCostSum(int sum, int j, Changes changes, Changes.ChangesTypesEnum changesTypesEnum) {


        //TODO: CHECK AUTENTIC INFORMATION

        if (changesTypesEnum == Changes.ChangesTypesEnum.DISH_CHOICE || changesTypesEnum == Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE) {

            //WORK ON UPDATE SUM OF DISH CHANGE CHOICE
            Dish dish;

            try {
                HashMap<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(j);
                dish = new Gson().fromJson(new Gson().toJson(map), Dish.class);
            } catch (Exception e) {
                dish = (Dish) changes.getChangesByTypesList().get(j);
            }


            if (changes.getFreeSelection() < changes.getChangesByTypesList().size()) {
                sum += dish.getPrice();

            } else if (dish.getChanges().size() > 0) {
                for (int k = 0; k < dish.getChanges().size(); k++) {

                    if (dish.getChanges().get(k).getChangesByTypesList().size() > 0) {

                        if (dish.getChanges().get(k).getFreeSelection() < dish.getChanges().get(k).getChangesByTypesList().size()) {

                            sum += getCostSum(sum, j, dish.getChanges().get(k), dish.getChanges().get(k).getChangesTypesEnum());
                        }
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

            if (regularChange.getNumOfAdded() > 0) {
                sum += (regularChange.getPrice() * regularChange.getNumOfAdded());
            }

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


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


}