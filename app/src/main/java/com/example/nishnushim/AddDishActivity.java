package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nishnushim.adapters.ChangesAdapter;
import com.example.nishnushim.helpUIClass.CreateCustomBtn;
import com.example.nishnushim.helpUIClass.NonScrollListView;
import com.example.nishnushim.helpclasses.Dish;

import java.util.ArrayList;
import java.util.List;

public class AddDishActivity extends AppCompatActivity {

    TextView restaurantNameTextView, dishNameTextView, dishDetailNameTextView, dishPriceNameTextView, dishNumNameTextView;
    ImageButton backImageBtn, plusDishImgBtn, minusDishImgBtn;
    Button moveToDishBtn;
    LinearLayout dishBtnAreaLinearLayout;
    ImageView dishImageView;
    NonScrollListView changesDishListView;

    Dish dish, createDish;
    List<Dish> dishList = new ArrayList<>();
    List<Button> dishBtn = new ArrayList<>();
    int dishBtnClickedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);


        Intent intent = getIntent();
        String restaurantNameString = "נשנושים";

        if (intent != null) {

            //TODO: SEND DISH OBJECT

            if (intent.getSerializableExtra("dish") != null) {
                dish = (Dish) intent.getSerializableExtra("dish");
            }

            if (intent.getStringExtra("res_name") != null) {
                restaurantNameString = intent.getStringExtra("res_name");
            }

        }


        createDish = (Dish) dish.clone();
        createDish.setChanges(new ArrayList<>());
        dishList.add(createDish);

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
        moveToDishBtn.setTag(createDish);

        moveToDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveToDishBtn.setBackground(ContextCompat.getDrawable(AddDishActivity.this, R.drawable.btn_bottom_line_selected));

                if (dishBtn.size() > 1) {
                    for (int i = 1; i < dishBtn.size(); i++) {
                        moveToDishBtn.setBackground(ContextCompat.getDrawable(AddDishActivity.this, R.color.white));
                    }
                }

                updateUI(0);

            }
        });


        dishBtn.add(moveToDishBtn);


        dishImageView = findViewById(R.id.dish_image_view_add_dish_activity);

        changesDishListView = findViewById(R.id.dish_changes_list_view_add_dish_activity);

//        saveDishBtn = findViewById(R.id.save_dish_btn_add_dish_activity);


        restaurantNameTextView.setText(restaurantNameString);
        updateUI(0);


        plusDishImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //TODO: WORKING WITH LIST AS NUMBER OF DISHES ADDED
                //TODO: ADD NUMBER OF DISHES
                Dish dishClone = (Dish) dish.clone();
                dishClone.setChanges(new ArrayList<>());
                dishList.add(dishClone);
                Button button = CreateCustomBtn.createDishBtn(AddDishActivity.this, moveToDishBtn, dishList.size() + 1, 3, "מנה " + dishList.size(), dishClone);
                dishBtn.add(button);

                dishBtn.get(dishBtn.size() - 1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AddDishActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                for (int i = 0; i < dishBtn.size(); i++) {

                                    if (dishBtn.get(i).getId() == v.getId()) {
                                        dishBtn.get(i).setBackground(ContextCompat.getDrawable(AddDishActivity.this, R.drawable.btn_bottom_line_selected));
                                    } else dishBtn.get(i).setBackground(ContextCompat.getDrawable(AddDishActivity.this, R.color.white));



                                    updateUI(i);
                                }
                            }
                        });
                    }
                });


                dishBtnAreaLinearLayout.setWeightSum(dishBtnAreaLinearLayout.getWeightSum() + 1);
                dishBtnAreaLinearLayout.addView(button);

            }
        });


        minusDishImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dishList.size() > 1) {

                    for (int i = 0; i < dishBtn.size(); i++) {

                        if (dishBtn.get(i).isPressed()) {
                            dishList.remove(i);
                            dishBtn.remove(i);
                            dishBtnAreaLinearLayout.removeView(dishBtn.get(i));
                            dishBtnAreaLinearLayout.setWeightSum(dishBtnAreaLinearLayout.getWeightSum() - 1);
                            break;

                        }
                    }

                    updateUI(0);


                }
            }
        });


    }




    private void updateUI(int position) {


        dishNameTextView.setText(dishList.get(position).getName());
        dishDetailNameTextView.setText(dishList.get(position).getDetails());
        dishPriceNameTextView.setText(String.valueOf(dishList.get(position).getPrice()));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("מספר מנות: ").append(dishList.size());
        dishNumNameTextView.setText(stringBuilder);

        if (changesDishListView.getAdapter() != null) {
            changesDishListView.setAdapter(null);
        }

        changesDishListView.setAdapter(new ChangesAdapter(AddDishActivity.this, dish.getChanges(), dishList.get(position)));
//                changesDishListView.getAdapter().notifyAll();


    }
}