package com.example.nishnushim.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.helpInterfaces.CartListener;

public class RestaurantDishAdapter extends RecyclerView.Adapter<RestaurantDishAdapter.RestaurantDishViewHolder> {

    Context context;
    Classification classificationOfDish, cartClassification;
    CartListener cartListener;
    int longPressedPosition = -1;
    final int ADAPTER_POSITION;

    public int getADAPTER_POSITION() {
        return ADAPTER_POSITION;
    }

    public RestaurantDishAdapter(Context context, Classification classificationOfDish, Classification cartClassification, CartListener cartListener, int adapter_position) {
        this.context = context;
        this.classificationOfDish = classificationOfDish;
        this.cartClassification = cartClassification;
        this.cartListener = cartListener;
        this.ADAPTER_POSITION = adapter_position;
    }


    public int getLongPressedPosition() {
        return longPressedPosition;
    }

    public void setLongPressedPosition(int longPressedPosition) {
        this.longPressedPosition = longPressedPosition;
    }


    @NonNull
    @Override
    public RestaurantDishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_detail_restaurant_menu_item, parent, false);
        return new RestaurantDishViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull RestaurantDishViewHolder holder, int position) {


        //MAIN LAYOUT DETECT LONG CLICKED // CHANGE LAYOUT BY LONG CLICK
        holder.parentLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (longPressedPosition != position){
                    cartListener.updateDishLongClicked(ADAPTER_POSITION, position);
                    return true;
                }


                if (longPressedPosition == position){
                    longPressedPosition = -1;
                    notifyDataSetChanged();
                    return true;
                }

                return false;
            }
        });




        if (longPressedPosition == position){
            //TODO: HANDLE THE LAYOUT HERE
            holder.restaurantDishLongClickedLinearLayout.setVisibility(View.VISIBLE);
            holder.restaurantDishLinearLayout.setVisibility(View.GONE);
            holder.dishLongClickedNameTextView.setText(classificationOfDish.getDishList().get(position).getName());
            holder.dishLongClickedDetailTextView.setText(classificationOfDish.getDishList().get(position).getDetails());
            holder.dishLongClickedPriceTextView.setText(String.valueOf(classificationOfDish.getDishList().get(position).getPrice()));

            int numOfTimeDish = 0;
            for (int i = 0; i < cartClassification.getDishList().size(); i++) {

                if (cartClassification.getDishList().get(i).getName().equals(classificationOfDish.getDishList().get(position).getName())){
                    numOfTimeDish++;
                }

            }

            holder.numOfDishesAddedTextView.setText("מס׳ מנות : " + numOfTimeDish);


            if (numOfTimeDish > 0){
                holder.addDishLongClickedBtn.setVisibility(View.GONE);
                holder.addDishImageBtn.setVisibility(View.VISIBLE);
                holder.removeDishImageBtn.setVisibility(View.VISIBLE);
            } else {

                holder.addDishLongClickedBtn.setVisibility(View.VISIBLE);
                holder.addDishImageBtn.setVisibility(View.GONE);
                holder.removeDishImageBtn.setVisibility(View.GONE);

            }


            
            holder.addDishImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartListener.addDishToCart(classificationOfDish.getDishList().get(position));
                    notifyDataSetChanged();

                }
            });



            holder.removeDishImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    cartListener.removeDishFromCart(classificationOfDish.getDishList().get(position));
                    notifyDataSetChanged();

                }
            });




            holder.addDishLongClickedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    cartListener.addDishToCart(classificationOfDish.getDishList().get(position));
                    notifyDataSetChanged();

                }
            });



        } else {


            holder.restaurantDishLongClickedLinearLayout.setVisibility(View.GONE);
            holder.restaurantDishLinearLayout.setVisibility(View.VISIBLE);
            holder.dishNameTextView.setText(classificationOfDish.getDishList().get(position).getName());
            holder.dishDetailTextView.setText(classificationOfDish.getDishList().get(position).getDetails());
            holder.dishPriceTextView.setText(String.valueOf(classificationOfDish.getDishList().get(position).getPrice()));

        }

    }
    

    public void updateLongPressedDish(int longPressedPosition){

        this.longPressedPosition = longPressedPosition;
        notifyDataSetChanged();

    }





    @Override
    public int getItemCount() {
        return classificationOfDish.getDishList().size();
    }



    public class RestaurantDishViewHolder extends RecyclerView.ViewHolder{

        TextView dishNameTextView, dishDetailTextView, dishPriceTextView;
        TextView dishLongClickedNameTextView, dishLongClickedDetailTextView, dishLongClickedPriceTextView, numOfDishesAddedTextView;
        LinearLayout restaurantDishLinearLayout;
        LinearLayout restaurantDishLongClickedLinearLayout;
        LinearLayout parentLinearLayout;

        Button addDishLongClickedBtn;
        ImageButton addDishImageBtn, removeDishImageBtn;

        public RestaurantDishViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantDishLinearLayout = itemView.findViewById(R.id.linear_layout_dish_detail_restaurant_menu_item);

            dishNameTextView = itemView.findViewById(R.id.dish_name_text_view_dish_restaurant_menu_item);
            dishDetailTextView = itemView.findViewById(R.id.dish_detail_text_view_dish_restaurant_menu_item);
            dishPriceTextView = itemView.findViewById(R.id.dish_cosh_text_view_dish_restaurant_menu_item);


            restaurantDishLongClickedLinearLayout = itemView.findViewById(R.id.linear_layout_dish_long_clicked_detail_restaurant_menu_item);
            parentLinearLayout = itemView.findViewById(R.id.linear_layout_parent_dish_detail_restaurant_menu_item);

            dishLongClickedNameTextView = itemView.findViewById(R.id.dish_long_clicked_name_text_view_dish_restaurant_menu_item);
            dishLongClickedDetailTextView = itemView.findViewById(R.id.dish_long_clicked_detail_text_view_dish_restaurant_menu_item);
            dishLongClickedPriceTextView = itemView.findViewById(R.id.dish_long_clicked_cosh_text_view_dish_restaurant_menu_item);
            numOfDishesAddedTextView = itemView.findViewById(R.id.num_of_dishs_added_long_clicked_text_view_dish_restaurant_menu_item);



            addDishLongClickedBtn = itemView.findViewById(R.id.add_dish_long_clicked_to_order_btn_dish_restaurant_menu_item);
            addDishImageBtn = itemView.findViewById(R.id.add_dish_btn_dish_detail_restaurant_menu_item);
            removeDishImageBtn = itemView.findViewById(R.id.minus_dish_btn_dish_detail_restaurant_menu_item);
        }


    }
}
