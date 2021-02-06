package com.example.nishnushim.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

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
    int dishPosition = -1;


    public DishChoiceAdapter(Context context, List<Object> dishChangeList, Changes.ChangesTypesEnum changesTypesEnum, Changes createChange) {
        this.context = context;
        this.dishChangeList = dishChangeList;
        this.changesTypesEnum = changesTypesEnum;
        this.createChange = createChange;
        this.layoutInflater = LayoutInflater.from(this.context);



        if (this.changesTypesEnum == Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE){
            HashMap<String, Object> map = (HashMap<String, Object>) dishChangeList;
            Classification classification = new Gson().fromJson(new Gson().toJson(map), Classification.class);

            this.dishChangeList = Collections.singletonList((Object) classification.getDishList());
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



        HashMap<String, Object> map = (HashMap<String, Object>) dishChangeList.get(position);
        Dish dish = new Gson().fromJson(new Gson().toJson(map), Dish.class);


        dishNameTextView.setText(dish.getName());
        dishDetailTextView.setText(dish.getDetails());
        dishPriceTextView.setText(dish.getPrice() + " ₪");

        if (dishPosition == position){
            dishLinearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.dish_change_background));
        }else dishLinearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.profile_cart_background));



        dishLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dishPosition == position){
                    createChange.getChangesByTypesList().remove(dishChangeList.get(position));
                    dishPosition = -1;
                    notifyDataSetChanged();
                    return;
                }


                //TODO: ADD DISH CHOICE TO DISH ORDER
                if (createChange.getChangesByTypesList().size() > 0) {
                    createChange.getChangesByTypesList().set(0, dishChangeList.get(position));
                }else createChange.getChangesByTypesList().add(dishChangeList.get(position));

                dishPosition = position;

            }
        });




        return convertView;
    }
}
