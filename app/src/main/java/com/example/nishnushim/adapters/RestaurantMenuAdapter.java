package com.example.nishnushim.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Menu;
import com.example.nishnushim.helpclasses.helpInterfaces.CartListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.RestaurantMenuViewHolder> {

    Context context;
    Menu menu;
    List<RestaurantDishAdapter> restaurantDishAdapters = new ArrayList<>();
    Classification cartClassification;
    int highLightRawPosition = 0;
    CartListener cartListener;


    public RestaurantMenuAdapter(Context context, Menu menu, Classification cartClassification, CartListener cartListener) {
        this.context = context;
        this.menu = menu;
        this.cartClassification = cartClassification;
        this.cartListener = cartListener;


        Log.i("ADAPTER TAG", this.menu.getClassifications().size() + "");
        for (int i = 0; i < this.menu.getClassifications().size(); i++) {

            restaurantDishAdapters.add(null);

        }

        Log.i("ADAPTER TAG", this.restaurantDishAdapters.size() + "");

    }



    @NonNull
    @Override
    public RestaurantMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_menu_dish_item, parent, false);
        return new RestaurantMenuViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RestaurantMenuViewHolder holder, int position) {

        if (position == highLightRawPosition){
            holder.subTitleMenuNameTextView.setBackground(ContextCompat.getDrawable(context ,R.drawable.sub_title_text_view_menu_restaurant_fragment));
        } else holder.subTitleMenuNameTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.background_color));

        holder.subTitleMenuNameTextView.setText(menu.getClassifications().get(position).getClassificationName());

        if (restaurantDishAdapters.size() > position && restaurantDishAdapters.get(position) == null) {
            initializeDishRecyclerView(holder, position, context);
        } else if (restaurantDishAdapters.size() > 0) restaurantDishAdapters.get(position).notifyDataSetChanged();

    }



//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (recyclerView.getScrollState() == View.SCROLL_AXIS_VERTICAL) {
//
//                    int highLight = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
//
//                    if (highLightRawPosition != highLight && highLight > 0) {
//                        highLightRawPosition = highLight;
//
//                        notifyDataSetChanged();
//                    }
//
//                }
//            }
//        });
//    }



    private void initializeDishRecyclerView(RestaurantMenuViewHolder holder, int position, Context context) {

        holder.dishRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.dishRecyclerView.setLayoutManager(layoutManager);
        RestaurantDishAdapter dishMenuAdapter = new RestaurantDishAdapter(context, menu.getClassifications().get(position), cartClassification, cartListener, position);
        restaurantDishAdapters.set(position ,dishMenuAdapter);
        holder.dishRecyclerView.setAdapter(dishMenuAdapter);

    }



    public void updateMenuLongPressed(int ADAPTER_TAG, int dishPosition){


        for (int i = 0; i < restaurantDishAdapters.size(); i++) {

            if (restaurantDishAdapters.get(i).getADAPTER_POSITION() == ADAPTER_TAG){
                restaurantDishAdapters.get(i).updateLongPressedDish(dishPosition);
            }else restaurantDishAdapters.get(i).updateLongPressedDish(-1);
        }
    }


    @Override
    public int getItemCount() {
        return menu.getClassifications().size();
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
