package com.example.nishnushim.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.Restaurant;

import java.util.List;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.RestaurantMenuViewHolder> {



    @NonNull
    @Override
    public RestaurantMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_menu_dish_item, parent, false);
        return new RestaurantMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantMenuViewHolder holder, int position) {


    }






    @Override
    public int getItemCount() {
        return 0;
    }



    public class RestaurantMenuViewHolder extends RecyclerView.ViewHolder{

        RecyclerView dishRecyclerView;
        TextView subTitleMenuNameTextView;


        public RestaurantMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            subTitleMenuNameTextView = itemView.findViewById(R.id.title_text_view_restaurant_menu_dish_item);
            dishRecyclerView = itemView.findViewById(R.id.dish_detail_recycler_view_restaurant_menu_dish_item);

        }
    }
}
