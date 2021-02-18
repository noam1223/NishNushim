package com.example.nishnushim.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.RecommendationRestaurant;

import java.util.List;

public class RecommendationAdapter extends BaseAdapter {

    Context context;
    List<RecommendationRestaurant> recommendationRestaurants;
    LayoutInflater layoutInflater;


    public RecommendationAdapter(Context context, List<RecommendationRestaurant> recommendationRestaurants) {
        this.context = context;
        this.recommendationRestaurants = recommendationRestaurants;
        this.layoutInflater = LayoutInflater.from(this.context);
    }


    @Override
    public int getCount() {
        return recommendationRestaurants.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = this.layoutInflater.inflate(R.layout.recommendation_item, null);


        ImageView profileUserImageView = convertView.findViewById(R.id.profile_user_img_view_recommendation_item);
        TextView recommendedNameTextView = convertView.findViewById(R.id.user_name_text_view_recommendation_item);
        TextView recommendedDateTextView = convertView.findViewById(R.id.date_text_view_recommendation_item);
        RatingBar recommendationRatingBar = convertView.findViewById(R.id.recommendation_rating_bar_recommendation_item);
        TextView detailRecommendationTextView = convertView.findViewById(R.id.recommendation_detail_text_view_recommendation_item);

        //TODO: ADD USER PROFILE
//        profileUserImageView.setImageURI(recommendationRestaurants.get(position).getUser());

        recommendedNameTextView.setText(recommendationRestaurants.get(position).getUser().getName());
        recommendationRatingBar.setRating(recommendationRestaurants.get(position).getCreditStar());
        detailRecommendationTextView.setText(recommendationRestaurants.get(position).getCreditLetter());
        recommendedDateTextView.setText(recommendationRestaurants.get(position).getDate());


        return convertView;
    }



}
