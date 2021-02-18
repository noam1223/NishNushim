package com.example.nishnushim.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.nishnushim.AddDishActivity;
import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Dish;
import com.example.nishnushim.helpclasses.menuChanges.Changes;
import com.example.nishnushim.helpclasses.menuChanges.RegularChange;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DishChoiceAdapter extends BaseAdapter {


    Context context;
    List<Object> dishChangeList = new ArrayList<>();
    Changes createChange;
    LayoutInflater layoutInflater;
    Changes.ChangesTypesEnum changesTypesEnum;
    int numToChoose, positionChecked = -1;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////DISH ADAPTER WILL WORK ON DISHES WITHOUT CHANGES, LIKE DRINKS, SIDE DIHSES (POTATOES, SALADS, HOME'S BREAD)

    public DishChoiceAdapter(Context context, List<Object> dishChangeList, Changes.ChangesTypesEnum changesTypesEnum, Changes createChange, int numToChoose) {
        this.context = context;
        this.dishChangeList = dishChangeList;
        this.changesTypesEnum = changesTypesEnum;
        this.createChange = createChange;
        this. numToChoose = numToChoose;
        this.layoutInflater = LayoutInflater.from(this.context);


        if (this.numToChoose > 0){

            //TODO: ADD BTN IN ADD DISH ACTIVITY

        }

    }




    @Override
    public int getCount() {
        return dishChangeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.dish_choice_change_item, null);

        Log.i("CHANGE_ADAPTER", "DISH_CHOICE_ADAPTER");

        LinearLayout dishLinearLayout = convertView.findViewById(R.id.linear_layout_dish_choice_change_item);
        ImageView dishImageView = convertView.findViewById(R.id.dish_image_view_dish_choice_change_item);
        TextView dishNameTextView = convertView.findViewById(R.id.dish_name_text_view_dish_choice_change_item);
        TextView dishDetailTextView = convertView.findViewById(R.id.dish_detail_text_view_dish_choice_change_item);
        TextView dishPriceTextView = convertView.findViewById(R.id.dish_cosh_text_view_dish_choice_change_item);

        CheckBox dishCheckBox = convertView.findViewById(R.id.img_dish_check_box_dish_choice_change_item);
        dishCheckBox.setChecked(false);

        Dish dish;

//        HashMap<String, Object> map = (HashMap<String, Object>) dishChangeList.get(position);
//        Dish dish = new Gson().fromJson(new Gson().toJson(map), Dish.class);



        try {
            HashMap<String, Object> map = (HashMap<String, Object>) dishChangeList.get(position);
            dish = new Gson().fromJson(new Gson().toJson(map), Dish.class);
        }catch (Exception e){
            dish = (Dish) dishChangeList.get(position);
        }



        dishNameTextView.setText(dish.getName());
        dishDetailTextView.setText(dish.getDetails());
        dishPriceTextView.setText(dish.getPrice() + " ₪");


        if (numToChoose > 0){
//            for (int i = 0; i < createChange.getChangesByTypesList().size(); i++) {
//
//                if (createChange.getChangesByTypesList().get(i).equals(dish)){
//                    dishCheckBox.setChecked(true);
//                    dishLinearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.dish_change_background));
//                }
//
//            }

            if (positionChecked == position){
                dishCheckBox.setChecked(true);
                dishLinearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.dish_change_background));
            }else {
                dishCheckBox.setChecked(false);
                dishLinearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.profile_cart_background));
            }

        } else {
            dishLinearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.profile_cart_background));
            dishCheckBox.setVisibility(View.GONE);
        }




        Dish finalDish = dish;

        dishLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (numToChoose > 0){

//                    if (numToChoose == createChange.getChangesByTypesList().size()){
//
//                        for (int i = 0; i < createChange.getChangesByTypesList().size(); i++) {
//
//                            if (createChange.getChangesByTypesList().get(i).equals(finalDish)){
//                                dishCheckBox.setChecked(false);
//                                createChange.getChangesByTypesList().remove(i);
//                                dishLinearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.profile_cart_background));
//                                return;
//                            }
//                        }
//
//                    }else Toast.makeText(context, "אין אפשרות לבחור יותר מ-" + numToChoose + " מנות ", Toast.LENGTH_SHORT).show();

                    if (positionChecked == position){
                        createChange.getChangesByTypesList().clear();
                    }else if (createChange.getChangesByTypesList().size() == 0){
                        createChange.getChangesByTypesList().add(finalDish);
                        positionChecked = position;
                    }else {
                        createChange.getChangesByTypesList().set(0, finalDish);
                        positionChecked = position;
                    }

                }else {

                    createChange.getChangesByTypesList().add(finalDish);
                    dishLinearLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.add_dish_change_animation));

                }

                notifyDataSetChanged();

                if (context instanceof AddDishActivity){
                    ((AddDishActivity) context).updateSum();
                }

                //TODO: ADD ANIMATION THAT DISH IS ADDED

            }
        });




        return convertView;
    }
}
