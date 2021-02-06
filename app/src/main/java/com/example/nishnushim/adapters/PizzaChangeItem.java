package com.example.nishnushim.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.DishChanges;

import java.util.List;

public class PizzaChangeItem extends BaseAdapter implements View.OnClickListener {

    Context context;
    List<DishChanges> dishChangesList;
    LayoutInflater layoutInflater;


    public PizzaChangeItem(Context context, List<DishChanges> dishChangesList) {
        this.context = context;
        this.dishChangesList = dishChangesList;

        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dishChangesList.size();
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
        convertView = layoutInflater.inflate(R.layout.pizza_change_item, null);

        ImageView changeImageView = convertView.findViewById(R.id.pizza_change_image_view_pizza_change_item);
        TextView changeNameTextView = convertView.findViewById(R.id.pizza_change_name_text_view_pizza_dish_change_item);
        changeNameTextView.setText(dishChangesList.get(position).getChange());


        LinearLayout addPlusBtnAreaLinearLayout = convertView.findViewById(R.id.add_change_pizza_to_pizza_linear_layout_area_pizza_change_item);
        LinearLayout addToWhichSideLinearLayout = convertView.findViewById(R.id.add_change_side_to_pizza_area_linear_layout_pizza_change_item);

        ImageButton addPlusImgBtn = convertView.findViewById(R.id.add_change_pizza_to_pizza_image_btn_pizza_change_item);

        ImageButton fullPizzaImgBtn, leftPizzaImgBtn, rightPizzaSideImgBtn;

        fullPizzaImgBtn = convertView.findViewById(R.id.add_to_all_pizza_img_btn_pizza_change_item);
        leftPizzaImgBtn = convertView.findViewById(R.id.add_to_left_half_pizza_img_btn_pizza_change_item);
        rightPizzaSideImgBtn = convertView.findViewById(R.id.add_to_right_half_pizza_img_btn_pizza_change_item);


        addPlusImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPlusBtnAreaLinearLayout.setVisibility(View.GONE);
                addToWhichSideLinearLayout.setVisibility(View.VISIBLE);

            }
        });








        return null;
    }




    @Override
    public void onClick(View v) {




    }
}
