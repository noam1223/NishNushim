package com.example.nishnushim.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.helpInterfaces.MenuItemListener;
import com.example.nishnushim.helpclasses.Classification;

import java.util.List;

public class SubTitleAdapter extends RecyclerView.Adapter<SubTitleAdapter.RestaurantMenuViewHolder> {
    
    
    Context context;
    List<Classification> classifications;
    int selectedPosition = 0;
    MenuItemListener menuItemListener;


    public SubTitleAdapter(Context context, List<Classification> classifications, MenuItemListener menuItemListener) {
        this.context = context;
        this.classifications = classifications;
        this.menuItemListener = menuItemListener;
    }


    @NonNull
    @Override
    public RestaurantMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_title_menu_classification_item, parent, false);
        return new RestaurantMenuViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RestaurantMenuViewHolder holder, int position) {

        if (selectedPosition == position){
            holder.subTitleLinearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.sub_title_menu_background_clicked));
        }else holder.subTitleLinearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.background_color));


        holder.subTitleTextView.setText(classifications.get(position).getClassificationName());

        holder.subTitleLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedPosition == position) {
                    return;
                }

                selectedPosition = position;
                notifyDataSetChanged();

                menuItemListener.changeMenuItemPosition(position);

            }
        });


    }






    @Override
    public int getItemCount() {
        return classifications.size();
    }



    public class RestaurantMenuViewHolder extends RecyclerView.ViewHolder{

        TextView subTitleTextView;
        LinearLayout subTitleLinearLayout;

        public RestaurantMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            subTitleTextView = itemView.findViewById(R.id.sub_title_text_view_menu_classification_item);
            subTitleLinearLayout = itemView.findViewById(R.id.linear_layout_sub_title_menu_classification_item);

        }
    }
}
