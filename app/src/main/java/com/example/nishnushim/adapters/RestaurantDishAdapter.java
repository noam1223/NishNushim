package com.example.nishnushim.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushim.R;

public class RestaurantDishAdapter extends RecyclerView.Adapter<RestaurantDishAdapter.RestaurantDishViewHolder> {



    @NonNull
    @Override
    public RestaurantDishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_detail_restaurant_menu_item, parent, false);
        return new RestaurantDishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantDishViewHolder holder, int position) {


    }






    @Override
    public int getItemCount() {
        return 0;
    }



    public class RestaurantDishViewHolder extends RecyclerView.ViewHolder{

        TextView dishNameTextView, dishDetailTextView, dishPriceTextView;
        LinearLayout restaurantDishLinearLayout;

        public RestaurantDishViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantDishLinearLayout = itemView.findViewById(R.id.linear_layout_dish_detail_restaurant_menu_item);
            dishNameTextView = itemView.findViewById(R.id.dish_name_text_view_dish_restaurant_menu_item);
            dishDetailTextView = itemView.findViewById(R.id.dish_detail_text_view_dish_restaurant_menu_item);
            dishPriceTextView = itemView.findViewById(R.id.dish_cosh_text_view_dish_restaurant_menu_item);

        }
    }
}
