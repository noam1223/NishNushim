package com.example.nishnushim.nishnushFragments.fragments_restaurant_profile;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishnushim.R;
import com.example.nishnushim.adapters.AreasForDeliveryAdapter;
import com.example.nishnushim.adapters.OpenCloseRestaurantHoursAdapter;
import com.example.nishnushim.adapters.RecommendationAdapter;
import com.example.nishnushim.helpUIClass.NonScrollListView;
import com.example.nishnushim.helpclasses.Restaurant;


public class RestaurantProfileDetailsFragment extends Fragment implements View.OnClickListener {

    LinearLayout nonRecommendationLinearLayout, profileDetailsAreaLinearLayout, openCloseHoursAreaLinearLayout, recommendationAreaLinearLayout, callRestaurantLinearLayout;
    TextView deliveriesHighLightTextView, hoursHighLightTextView, recommendationHighLightTextView,  restaurantNameTextView, fullAddressTextView;
    ImageButton waseToRestaurantImgBtn;


    NonScrollListView areasForDeliveryListView, openCloseHoursListView, recommendationListView;


    Restaurant restaurant;
    String key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_profile_details, container, false);

        profileDetailsAreaLinearLayout = view.findViewById(R.id.linear_layout_profile_details_area_profile_details_fragment);
        openCloseHoursAreaLinearLayout = view.findViewById(R.id.linear_layout_open_close_hours_profile_details_fragment);
        recommendationAreaLinearLayout = view.findViewById(R.id.linear_layout_recommendation_area_profile_detail_fragment);
        callRestaurantLinearLayout = view.findViewById(R.id.call_restaurant_linear_layout_area_restaurant_profile_details_fragment);
        callRestaurantLinearLayout.setOnClickListener(this);



        if (getArguments() != null) {


            restaurant = (Restaurant) getArguments().getSerializable(getContext().getString(R.string.restaurant_detail));
            key = getArguments().getString("key");



            nonRecommendationLinearLayout = view.findViewById(R.id.linear_layout_non_recommendation_area_restaurant_profile_details_fragment);
            recommendationListView = view.findViewById(R.id.recommendation_list_view_restaurant_profile_details_fragment);

            deliveriesHighLightTextView = view.findViewById(R.id.deliveries_high_light_text_view_restaurant_profile_details_fragment);
            hoursHighLightTextView = view.findViewById(R.id.hours_high_light_text_view_restaurant_profile_details_fragment);
            recommendationHighLightTextView = view.findViewById(R.id.recommendation_high_light_text_view_restaurant_profile_details_fragment);


            restaurantNameTextView = view.findViewById(R.id.restaurant_name_text_view_restaurant_profile_details_fragment);
            fullAddressTextView = view.findViewById(R.id.full_address_text_view_restaurant_profile_details_fragment);
//            phoneTextView = view.findViewById(R.id.phone_text_view_restaurant_profile_details_fragment);


//            callToRestaurantImgBtn = view.findViewById(R.id.call_to_restaurant_img_btn_restaurant_profile_details_fragment);
            waseToRestaurantImgBtn = view.findViewById(R.id.wase_restaurant_img_btn_restaurant_profile_detauls_fragment);
            waseToRestaurantImgBtn.setOnClickListener(this);


            areasForDeliveryListView = view.findViewById(R.id.area_for_delivery_list_view_restaurant_profile_details_fragment);
            areasForDeliveryListView.setAdapter(new AreasForDeliveryAdapter(getContext(), restaurant.getAreasForDeliveries()));

            openCloseHoursListView = view.findViewById(R.id.open_close_hours_list_view_restaurant_profile_details_fragment);
            openCloseHoursListView.setAdapter(new OpenCloseRestaurantHoursAdapter(getContext(), restaurant.getOpenHour(), restaurant.getCloseHour()));


            if (restaurant.getRecommendationRestaurants().size() > 0) {
                recommendationListView.setAdapter(new RecommendationAdapter(getContext(), restaurant.getRecommendationRestaurants()));
                recommendationListView.setVisibility(View.VISIBLE);
                nonRecommendationLinearLayout.setVisibility(View.GONE);

            }else {
                nonRecommendationLinearLayout.setVisibility(View.VISIBLE);
                recommendationListView.setVisibility(View.GONE);
            }




            restaurantNameTextView.setText(restaurant.getName());

            fullAddressTextView.setText(restaurant.getMyAddress().fullMyAddress());


        }

        return view;

    }





    @Override
    public void onClick(View v) {

        int id = v.getId();


        if (id == R.id.call_restaurant_linear_layout_area_restaurant_profile_details_fragment){

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel",restaurant.getPhoneNumber(), null));
            startActivity(intent);


        }else if (id == R.id.wase_restaurant_img_btn_restaurant_profile_detauls_fragment){

            String uri = "waze://?ll=" + restaurant.getMyAddress().getLatitude() + ", " + restaurant.getMyAddress().getLongitude() + "&navigate=yes";
            startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));

        }

    }




    public void onScrollListenerCheck(ScrollView scrollView){

        if (isViewVisible(scrollView, profileDetailsAreaLinearLayout)){

            deliveriesHighLightTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sub_title_menu_background_clicked));
            hoursHighLightTextView.setBackground(null);
            recommendationHighLightTextView.setBackground(null);

        }else if (isViewVisible(scrollView, openCloseHoursAreaLinearLayout)){

            deliveriesHighLightTextView.setBackground(null);
            hoursHighLightTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sub_title_menu_background_clicked));
            recommendationHighLightTextView.setBackground(null);

        } else if (isViewVisible(scrollView, recommendationAreaLinearLayout)){

            deliveriesHighLightTextView.setBackground(null);
            hoursHighLightTextView.setBackground(null);
            recommendationHighLightTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sub_title_menu_background_clicked));

        }

    }



    private boolean isViewVisible(ScrollView scrollView, View view) {
        Rect scrollBounds = new Rect();
        scrollView.getDrawingRect(scrollBounds);

        float top = view.getY();
        float bottom = top + view.getHeight();

        if (scrollBounds.top < top && scrollBounds.bottom > bottom) {
            return true;
        } else {
            return false;
        }
    }
}