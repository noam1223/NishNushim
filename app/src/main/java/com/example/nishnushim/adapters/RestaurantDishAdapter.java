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
import com.example.nishnushim.helpUIClass.NonScrollListView;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.helpInterfaces.CartListener;

public class RestaurantDishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PIZZA_LAYOUT = 420;
    private static final int REGULAR_LAYOUT = 314;


    Context context;
    Classification classificationOfDish, cartClassification;
    CartListener cartListener;
    int longPressedPosition = -1;
    final int ADAPTER_POSITION;
    String restaurantName;

    public int getADAPTER_POSITION() {
        return ADAPTER_POSITION;
    }


    public RestaurantDishAdapter(Context context, Classification classificationOfDish, Classification cartClassification, CartListener cartListener, int adapter_position, String restaurantName) {
        this.context = context;
        this.classificationOfDish = classificationOfDish;
        this.cartClassification = cartClassification;
        this.cartListener = cartListener;
        this.ADAPTER_POSITION = adapter_position;
        this.restaurantName = restaurantName;
    }


    public int getLongPressedPosition() {
        return longPressedPosition;
    }

    public void setLongPressedPosition(int longPressedPosition) {
        this.longPressedPosition = longPressedPosition;
    }




    @Override
    public int getItemViewType(int position) {
        if (classificationOfDish.getClassificationName().contains("פיצה") && longPressedPosition == position){
            return PIZZA_LAYOUT;
        }else return REGULAR_LAYOUT;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;

        if (viewType == REGULAR_LAYOUT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_detail_restaurant_menu_item, parent, false);
            viewHolder = new RestaurantDishViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza_dish_detail_restaurant_menu_item, parent, false);
            viewHolder = new PizzaDishViewHolder(view);
        }

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (getItemViewType(position) == REGULAR_LAYOUT){
            RestaurantDishViewHolder restaurantDishViewHolder = (RestaurantDishViewHolder) holder;


            //MAIN LAYOUT DETECT LONG CLICKED // CHANGE LAYOUT BY LONG CLICK
            restaurantDishViewHolder.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, AddDishActivity.class);
                    intent.putExtra("res_name", restaurantName);
                    intent.putExtra("dish", classificationOfDish.getDishList().get(position));
                    context.startActivity(intent);


                }
            });



            if (longPressedPosition == position){

                updateRegularViewLongPressed(restaurantDishViewHolder, position);

            } else {


//                restaurantDishViewHolder.restaurantDishLongClickedLinearLayout.setVisibility(View.GONE);
                restaurantDishViewHolder.restaurantDishLinearLayout.setVisibility(View.VISIBLE);
                restaurantDishViewHolder.dishNameTextView.setText(classificationOfDish.getDishList().get(position).getName());
                restaurantDishViewHolder.dishDetailTextView.setText(classificationOfDish.getDishList().get(position).getDetails());
                restaurantDishViewHolder.dishPriceTextView.setText(String.valueOf(classificationOfDish.getDishList().get(position).getPrice()));

            }



        }else {
            PizzaDishViewHolder pizzaDishViewHolder = (PizzaDishViewHolder) holder;


            pizzaDishViewHolder.pizzaNameTextView.setText(classificationOfDish.getDishList().get(position).getName());
            pizzaDishViewHolder.pizzaDetailsTextView.setText(classificationOfDish.getDishList().get(position).getDetails());
            pizzaDishViewHolder.pizzaPriceTextView.setText(String.valueOf(classificationOfDish.getDishList().get(position).getPrice()));


            pizzaDishViewHolder.addChangesToPizzaBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    View popUpView = LayoutInflater.from(context).inflate(R.layout.search_pop_up_window, null);
                    Dialog pizzaChangesDialog = new Dialog(context);
                    pizzaChangesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    pizzaChangesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    pizzaChangesDialog.setContentView(popUpView);


                    if (pizzaChangesDialog.getWindow() != null)
                        pizzaChangesDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;


                    TextView pizzaChangeHeadTextView = popUpView.findViewById(R.id.pizza_dish_name_text_view_dish_changes_list_pop_up_window);
                    TextView pizzaChangeDetailsTextView = popUpView.findViewById(R.id.pizza_details_for_changes_text_view_dish_changes_list_pop_up_window);

                    ListView pizzaChangesListView = popUpView.findViewById(R.id.pizza_changes_list_view_pizza_changes_list_pop_up_window);

                    Button saveChangesBtn = popUpView.findViewById(R.id.pizza_save_dish_changes_btn_changes_list_pop_up_window);


//                    pizzaChangesListView.setAdapter(new PizzaChangeItem(context, classificationOfDish.getDishList().get(position).getChanges()));

                    ///NEED TO SPEAK WITH ORLY AND LIRON
                    ///NEED TO ADD ALL CHANGES THAT HAS TO PIZZAS

                }
            });


        }










    }






    private void updateRegularViewLongPressed(@NonNull RestaurantDishViewHolder holder, int position) {
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


        holder.addDishImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartListener.addDishToCart(classificationOfDish.getDishList().get(position));
                cartListener.updateCartSum();
                notifyDataSetChanged();

            }
        });



        holder.removeDishImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartListener.removeDishFromCart(classificationOfDish.getDishList().get(position));
                cartListener.updateCartSum();
                notifyDataSetChanged();

            }
        });



        holder.openChangesWindowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //OPEN CHANGES OF DISH
                if (classificationOfDish.getDishList().get(position).getChanges().size() > 0){
                    cartListener.addChangesDishCart(classificationOfDish.getDishList().get(position));
                }

            }
        });
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

        Button openChangesWindowBtn;
        ImageButton addDishImageBtn, removeDishImageBtn;

        public RestaurantDishViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLinearLayout = itemView.findViewById(R.id.linear_layout_parent_dish_detail_restaurant_menu_item);
            restaurantDishLinearLayout = itemView.findViewById(R.id.linear_layout_dish_detail_restaurant_menu_item);

            dishNameTextView = itemView.findViewById(R.id.dish_name_text_view_dish_restaurant_menu_item);
            dishDetailTextView = itemView.findViewById(R.id.dish_detail_text_view_dish_restaurant_menu_item);
            dishPriceTextView = itemView.findViewById(R.id.dish_cosh_text_view_dish_restaurant_menu_item);


//            restaurantDishLongClickedLinearLayout = itemView.findViewById(R.id.linear_layout_dish_long_clicked_detail_restaurant_menu_item);
//            dishLongClickedNameTextView = itemView.findViewById(R.id.dish_long_clicked_name_text_view_dish_restaurant_menu_item);
//            dishLongClickedDetailTextView = itemView.findViewById(R.id.dish_long_clicked_detail_text_view_dish_restaurant_menu_item);
//            dishLongClickedPriceTextView = itemView.findViewById(R.id.dish_long_clicked_cosh_text_view_dish_restaurant_menu_item);
//            numOfDishesAddedTextView = itemView.findViewById(R.id.num_of_dishs_added_long_clicked_text_view_dish_restaurant_menu_item);
//            openChangesWindowBtn = itemView.findViewById(R.id.changes_list_btn_dish_detail_restaurant_menu_item);
//            addDishImageBtn = itemView.findViewById(R.id.add_dish_btn_dish_detail_restaurant_menu_item);
//            removeDishImageBtn = itemView.findViewById(R.id.minus_dish_btn_dish_detail_restaurant_menu_item);
        }


    }




    public class PizzaDishViewHolder extends RecyclerView.ViewHolder{

        LinearLayout pizzaBtnsByPizzaLinearLayout;
        TextView pizzaNameTextView, pizzaDetailsTextView, pizzaPriceTextView;
        Button changeToPizzaNumberBtn, addChangesToPizzaBtn;
        ImageView pizzaImageView;


        public PizzaDishViewHolder(@NonNull View itemView) {
            super(itemView);


            pizzaBtnsByPizzaLinearLayout = itemView.findViewById(R.id.linear_layout_btn_pizza_change_detail_restaurant_menu_item);
            pizzaNameTextView = itemView.findViewById(R.id.pizza_dish_name_text_view_detail_restaurant_menu_item);
            pizzaDetailsTextView = itemView.findViewById(R.id.pizza_dish_detail_text_view_detail_restaurant_menu_item);
            pizzaPriceTextView = itemView.findViewById(R.id.pizza_dish_price_text_view_detail_restaurant_menu_item);


            changeToPizzaNumberBtn = itemView.findViewById(R.id.pizza_first_btn_pizza_dish_detail_restaurant_menu_item);
            addChangesToPizzaBtn = itemView.findViewById(R.id.pizza_add_changes_btn_detail_restaurant_menu_item);

            pizzaImageView = itemView.findViewById(R.id.pizza_dish_image_view_pizza_dish_detail_restaurant_menu_item);
        }
    }
}
