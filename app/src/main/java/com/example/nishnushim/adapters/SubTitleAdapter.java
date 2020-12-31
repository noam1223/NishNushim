package com.example.nishnushim.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushim.R;

public class SubTitleAdapter extends RecyclerView.Adapter<SubTitleAdapter.RestaurantMenuViewHolder> {




    @NonNull
    @Override
    public RestaurantMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_title_menu_classification_item, parent, false);
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

        TextView subTitleTextView;


        public RestaurantMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            subTitleTextView = itemView.findViewById(R.id.sub_title_text_view_menu_classification_item);

        }
    }
}
