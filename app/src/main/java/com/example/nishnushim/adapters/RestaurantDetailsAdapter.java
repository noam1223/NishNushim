package com.example.nishnushim.adapters;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushim.R;
import com.example.nishnushim.RestaurantProfileHomeActivity;
import com.example.nishnushim.helpUIClass.RestaurantTypeHelper;
import com.example.nishnushim.helpclasses.RecommendationRestaurant;
import com.example.nishnushim.helpclasses.Restaurant;
import com.example.nishnushim.helpclasses.UserSingleton;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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


        holder.favoriteImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UserSingleton.getInstance().getUser().getRestaurantWishList().contains(keys.get(position))) {
                    holder.favoriteImgBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_heart_like_empty));
                } else {
                    UserSingleton.getInstance().getUser().getRestaurantWishList().add(keys.get(position));
                    holder.favoriteImgBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_heart_like_full));
                }

            }
        });


        holder.recommendationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //OPEN RECOMMENDATION POP UP

                View popUpView = LayoutInflater.from(context).inflate(R.layout.recommendation_pop_up_window, null);
                Dialog recommendationPopUp = new Dialog(context);
                recommendationPopUp.requestWindowFeature(Window.FEATURE_NO_TITLE);
                recommendationPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                recommendationPopUp.setContentView(popUpView);


                ImageButton closeWindowImgBtn = popUpView.findViewById(R.id.close_window_image_btn_recommendation_pop_up_window);
                TextView restaurantNameTextView = popUpView.findViewById(R.id.restaurant_name_text_name_recommendation_pop_up_window);
                RatingBar recommendationRatingBar = popUpView.findViewById(R.id.recommendation_rating_bar_recommendation_pop_up_window);
                EditText noteRecommendEditText = popUpView.findViewById(R.id.recommendation_note_edit_text_recommendation_pop_up_window);
                Button saveBtn = popUpView.findViewById(R.id.save_recommendation_btn_recommendation_pop_up_window);


                restaurantNameTextView.setText(restaurants.get(position).getName());


                closeWindowImgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recommendationPopUp.dismiss();
                    }
                });


                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int starNum = recommendationRatingBar.getNumStars();


                        if (starNum == 0) {
                            Toast.makeText(context, "בבקשה בחר דירוג", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        RecommendationRestaurant recommendationRestaurant = new RecommendationRestaurant();
                        recommendationRestaurant.setUser(UserSingleton.getInstance().getUser());
                        recommendationRestaurant.setCreditStar(starNum);
                        recommendationRestaurant.setDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));

                        if (!noteRecommendEditText.getText().toString().isEmpty()) {
                            recommendationRestaurant.setCreditLetter(noteRecommendEditText.getText().toString());
                        }

                        restaurants.get(position).getRecommendationRestaurants().add(recommendationRestaurant);

                        //TODO: SAVE IN DATABASE
//                        collection("recommendationRestaurants").add(recommendationRestaurant);
                        FirebaseFirestore.getInstance().collection(context.getString(R.string.RESTAURANTS_PATH)).document(keys.get(position))
                                .update("recommendationRestaurants", FieldValue.arrayUnion(recommendationRestaurant));

                        recommendationPopUp.dismiss();

                    }
                });


                recommendationPopUp.create();
                recommendationPopUp.show();

//                recommendationPopUp.getWindow().setLayout(294, 248);


            }
        });

    }


    @Override
    public int getItemCount() {
        return restaurants.size();
    }


    public class RestaurantDetailsViewHolder extends RecyclerView.ViewHolder {

        ImageView logoRestaurantImageView, mainRestaurantImageView;
        TextView restaurantNameTextView, fullAddressRestaurantTextView, distanceTextView, deliveryAmountAmountTextView,
                timeFullDeliveryTextView, minAmountToDeliveryTextView, avgCreditTextView, whenOpenHourTextView;
        CardView detailsRestaurantCardView;
        LinearLayout whenCloseBackgroundLinearLayout;
        ImageButton favoriteImgBtn;
        ImageView recommendationImageView;


        public RestaurantDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            logoRestaurantImageView = itemView.findViewById(R.id.restaurant_logo_image_view_restaurant_item);
            mainRestaurantImageView = itemView.findViewById(R.id.restaurant_main_image_view_restaurant_details_item);

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
            favoriteImgBtn = itemView.findViewById(R.id.restaurant_favorite_image_btn_restaurant_detail_item);

            recommendationImageView = itemView.findViewById(R.id.recommendation_image_view_restaurant_details_item);

        }
    }
}
