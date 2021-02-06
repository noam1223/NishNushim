package com.example.nishnushim.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.Classification;

public class CartOrderAdapter extends BaseAdapter {

    //WORK ON ORDERS IN CART
    Context context;
    Classification cartClassification;
    LayoutInflater layoutInflater;

    public CartOrderAdapter(Context context, Classification cartClassification) {
        this.context = context;
        this.cartClassification = cartClassification;

        this.layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return cartClassification.getDishList().size();
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

        convertView = layoutInflater.inflate(R.layout.cart_order_item, null);

        ImageView dishImageView = convertView.findViewById(R.id.dish_image_view_menu_cart_order_item);
        TextView dishNameTextView = convertView.findViewById(R.id.dish_name_text_view_cart_order_item);
        TextView dishDetailsTextView = convertView.findViewById(R.id.dish_detail_text_view_cart_order_item);
        TextView dishCostTextView = convertView.findViewById(R.id.dish_cosh_text_view_cart_order_item);
        TextView subTitleChangeTextView = convertView.findViewById(R.id.sub_title_changes_text_view_cart_order_item);
        TextView dishChangesDetailsTextView = convertView.findViewById(R.id.cahnges_detail_text_view_cart_order_item);

        Button editDishBtn = convertView.findViewById(R.id.edit_dish_cart_order_item);
        Button addChangesToDishBtn = convertView.findViewById(R.id.add_to_dish_cart_order_item);
        Button removeDishBtn = convertView.findViewById(R.id.remove_dish_cart_order_item);


        //WORKING ON CHANGES TEXT
        if (cartClassification.getDishList().get(position).getChanges().size() > 0){

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < cartClassification.getDishList().get(position).getChanges().size(); i++) {
                stringBuilder.append(cartClassification.getDishList().get(position).getChanges().get(i).getChangeName());
                stringBuilder.append(",").append(" ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(".");

            dishChangesDetailsTextView.setText(stringBuilder.toString());

            subTitleChangeTextView.setVisibility(View.VISIBLE);
            dishChangesDetailsTextView.setVisibility(View.VISIBLE);

        }else {

            subTitleChangeTextView.setVisibility(View.GONE);
            dishChangesDetailsTextView.setVisibility(View.GONE);

        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        dishNameTextView.setText(cartClassification.getDishList().get(position).getName());
        dishDetailsTextView.setText(cartClassification.getDishList().get(position).getDetails());
        dishCostTextView.setText(String.valueOf(cartClassification.getDishList().get(position).getPrice()));


        editDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: EDIT SOMETHING ON THE DISH

            }
        });



        addChangesToDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: ADD SOMETHING TO DISH

            }
        });



        removeDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: DELETE THE POSITION DISH

            }
        });



        return convertView;
    }
}
