package com.example.nishnushim.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushim.R;
import com.example.nishnushim.helpUIClass.RestaurantTypeHelper;
import com.example.nishnushim.helpclasses.helpInterfaces.RestaurantTypeListener;

public class RestaurantTypeAdapter extends RecyclerView.Adapter<RestaurantTypeAdapter.RestaurantTypeViewHolder> {

    Context context;
    int selectedPos = 0;
    RestaurantTypeHelper restaurantTypeHelper;
    RestaurantTypeListener restaurantTypeListener;

    public RestaurantTypeAdapter(Context context, RestaurantTypeListener restaurantTypeListener) {
        this.context = context;
        this.restaurantTypeListener = restaurantTypeListener;
        this.restaurantTypeHelper =  new RestaurantTypeHelper(context);
    }

    @NonNull
    @Override
    public RestaurantTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restauran_type, parent, false);
        return new RestaurantTypeViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RestaurantTypeViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        if (selectedPos == position){

            holder.ovalImageView.setImageDrawable(ContextCompat.getDrawable(context ,R.drawable.ic_oval_fullfill));
            holder.typeNameTextView.setVisibility(View.VISIBLE);
            holder.typeNameTextView.setText(restaurantTypeHelper.getRestaurantTypeClassList().get(position).getTitle());
            holder.typeImageView.setImageDrawable(ContextCompat.getDrawable(context ,restaurantTypeHelper.getRestaurantTypeClassList().get(position).getSrcHighLightImageId()));

        } else {

            holder.ovalImageView.setImageDrawable(ContextCompat.getDrawable(context ,R.drawable.ic_oval_empty));
            holder.typeImageView.setImageDrawable(ContextCompat.getDrawable(context ,restaurantTypeHelper.getRestaurantTypeClassList().get(position).getSrcFirstImageId()));

        }


        holder.constraintLayoutType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedPos == position) {
                    return;
                }

                selectedPos = position;
                notifyDataSetChanged();
                restaurantTypeListener.onRestaurantTypeClicked(restaurantTypeHelper.getRestaurantTypeClassList().get(position));

                Toast.makeText(context, restaurantTypeHelper.getRestaurantTypeClassList().get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public int getItemCount() {
        return restaurantTypeHelper.getRestaurantTypeClassList().size();
    }



    public class RestaurantTypeViewHolder extends RecyclerView.ViewHolder{

        TextView typeNameTextView;
        ImageView ovalImageView, typeImageView;
        ConstraintLayout constraintLayoutType;


        public RestaurantTypeViewHolder(@NonNull View itemView) {
            super(itemView);

            typeNameTextView = itemView.findViewById(R.id.name_of_type_text_view_restaurant_type_item);
            ovalImageView = itemView.findViewById(R.id.oval_image_view_restaurant_type_item);
            typeImageView = itemView.findViewById(R.id.type_image_view_restaurant_type_item);
            constraintLayoutType = itemView.findViewById(R.id.type_constrain_layout_restaurant_type_item);

        }
    }
}
