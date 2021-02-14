package com.example.nishnushim.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushim.AddDishActivity;
import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Menu;

public class RestaurantDishAdapter extends RecyclerView.Adapter<RestaurantDishAdapter.RestaurantDishViewHolder> {


    Context context;
    Classification classificationOfDish;
    Menu cartClassification;


    String restaurantName;

    public RestaurantDishAdapter(Context context, Classification classificationOfDish, Menu cartClassification, String restaurantName) {
        this.context = context;
        this.classificationOfDish = classificationOfDish;
        this.cartClassification = cartClassification;
        this.restaurantName = restaurantName;
    }




    @NonNull
    @Override
    public RestaurantDishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_detail_restaurant_menu_item, parent, false);
        return new RestaurantDishViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull RestaurantDishViewHolder holder, int position) {


        holder.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AddDishActivity.class);
                intent.putExtra("res_name", restaurantName);
                intent.putExtra("dish", classificationOfDish.getDishList().get(position));
                intent.putExtra("classification", classificationOfDish);
                ((Activity) context).startActivityForResult(intent, 1);


            }
        });


//                restaurantDishViewHolder.restaurantDishLongClickedLinearLayout.setVisibility(View.GONE);
        holder.restaurantDishLinearLayout.setVisibility(View.VISIBLE);
        holder.dishNameTextView.setText(classificationOfDish.getDishList().get(position).getName());
        holder.dishDetailTextView.setText(classificationOfDish.getDishList().get(position).getDetails());
        holder.dishPriceTextView.setText(String.valueOf(classificationOfDish.getDishList().get(position).getPrice()));



    }





    @Override
    public int getItemCount() {
        return classificationOfDish.getDishList().size();
    }


    public class RestaurantDishViewHolder extends RecyclerView.ViewHolder {

        TextView dishNameTextView, dishDetailTextView, dishPriceTextView;
        LinearLayout restaurantDishLinearLayout;
        LinearLayout parentLinearLayout;


        public RestaurantDishViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLinearLayout = itemView.findViewById(R.id.linear_layout_parent_dish_detail_restaurant_menu_item);
            restaurantDishLinearLayout = itemView.findViewById(R.id.linear_layout_dish_detail_restaurant_menu_item);
            dishNameTextView = itemView.findViewById(R.id.dish_name_text_view_dish_restaurant_menu_item);
            dishDetailTextView = itemView.findViewById(R.id.dish_detail_text_view_dish_restaurant_menu_item);
            dishPriceTextView = itemView.findViewById(R.id.dish_cosh_text_view_dish_restaurant_menu_item);

        }

    }


}
