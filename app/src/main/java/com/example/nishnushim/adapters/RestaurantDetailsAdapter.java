package com.example.nishnushim.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushim.R;
import com.example.nishnushim.RestaurantProfileHomeActivity;
import com.example.nishnushim.helpUIClass.RestaurantTypeHelper;
import com.example.nishnushim.helpclasses.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailsAdapter extends RecyclerView.Adapter<RestaurantDetailsAdapter.RestaurantDetailsViewHolder> {

    Context context;
    List<Restaurant> restaurants;
    List<String> keys;

    //USED IN RESTAURANT FRAGMENT


    public RestaurantDetailsAdapter(Context context, List<Restaurant> restaurants, List<String> keys) {
        this.context = context;
        this.restaurants = restaurants;
        this.keys = keys;
    }

    @NonNull
    @Override
    public RestaurantDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restauran_details, parent, false);
        return new RestaurantDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantDetailsViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        
        holder.detailsRestaurantCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RestaurantProfileHomeActivity.class);
                intent.putExtra(context.getString(R.string.restaurant_detail), restaurants.get(position));
                intent.putExtra("key", keys.get(position));
                context.startActivity(intent);
                
                
            }
        });

//        holder.restaurantNameTextView.setText(restaurants.get(position).getName());
//        holder.fullAddressRestaurantTextView.setText(restaurants.get(position).getMyAddress().getCityName() + ", " +
//                restaurants.get(position).getMyAddress().getStreetName() + " " + restaurants.get(position).getMyAddress().getHouseNumber());
//
//
//        holder.distanceTextView.setText("3");
////        holder.deliveryAmountAmountTextView.setText(String.valueOf(restaurants.get(position).getAmountOfMoney()));
//        holder.timeFullDeliveryTextView.setText(restaurants.get(position).getDeliveryTime());
//        holder.minAmountToDeliveryTextView.setText("3");
//        holder.avgCreditTextView.setText("3");


        holder.restaurantNameTextView.setText(restaurants.get(position).getName());
        holder.fullAddressRestaurantTextView.setText(restaurants.get(position).getMyAddress().fullMyAddress());

        //TODO: SET THE CALCULATED DISTANCE
        holder.distanceTextView.setText("2");


        holder.deliveryAmountAmountTextView.setText(String.valueOf(restaurants.get(position).getAreasForDeliveries().get(0).getDeliveryCost()));

//        holder.timeFullDeliveryTextView.setText(restaurants.get(position.getDeliveryTime());
        holder.minAmountToDeliveryTextView.setText(String.valueOf(restaurants.get(position).getAreasForDeliveries().get(0).getMinToDeliver()));

        //TODO: CALCULATE THE AVERAGE OF CREDITS
        holder.avgCreditTextView.setText("5");


        holder.whenOpenHourTextView.setText(restaurants.get(0).getOpenHour().get(0));


    }






    @Override
    public int getItemCount() {
        return restaurants.size();
    }



    public class RestaurantDetailsViewHolder extends RecyclerView.ViewHolder{

        ImageView logoRestaurantImageView, mainRestaurantImageView;
        TextView restaurantNameTextView, fullAddressRestaurantTextView, distanceTextView, deliveryAmountAmountTextView,
                timeFullDeliveryTextView, minAmountToDeliveryTextView, avgCreditTextView, whenOpenHourTextView;
        CardView detailsRestaurantCardView;
        LinearLayout whenCloseBackgroundLinearLayout;


        public RestaurantDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            logoRestaurantImageView  = itemView.findViewById(R.id.restaurant_logo_image_view_restaurant_item);
            mainRestaurantImageView  = itemView.findViewById(R.id.restaurant_main_image_view_restaurant_details_item);

            restaurantNameTextView = itemView.findViewById(R.id.restaurant_name_text_view_restaurant_detail_item);
            fullAddressRestaurantTextView = itemView.findViewById(R.id.full_address_text_view_restaurant_detail_item);
            distanceTextView = itemView.findViewById(R.id.distance_from_user_restaurant_detail_item);
            whenOpenHourTextView = itemView.findViewById(R.id.when_open_hour_text_view_restaurant_details_item);
            deliveryAmountAmountTextView = itemView.findViewById(R.id.delivery_amount_restaurant_detail_item);
            timeFullDeliveryTextView = itemView.findViewById(R.id.delivery_time_restaurant_detail_item);
            minAmountToDeliveryTextView = itemView.findViewById(R.id.min_to_deliver_amount_restaurant_detail_item);
            avgCreditTextView = itemView.findViewById(R.id.avg_restaurant_feedback_text_view_restaurant_detail_item);
            detailsRestaurantCardView = itemView.findViewById(R.id.card_view_restaurant_details_item);
            whenCloseBackgroundLinearLayout = itemView.findViewById(R.id.linear_layout_close_restaurant_background_restaurant_details_item);


        }
    }
}
