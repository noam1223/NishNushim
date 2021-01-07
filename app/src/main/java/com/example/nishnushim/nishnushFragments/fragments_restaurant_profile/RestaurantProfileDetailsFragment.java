package com.example.nishnushim.nishnushFragments.fragments_restaurant_profile;

import android.graphics.Rect;
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
import com.example.nishnushim.helpUIClass.NonScrollListView;
import com.example.nishnushim.helpclasses.Restaurant;


public class RestaurantProfileDetailsFragment extends Fragment implements View.OnClickListener {

    LinearLayout nonRecommendationLinearLayout, profileDetailsAreaLinearLayout, openCloseHoursAreaLinearLayout, recommendationAreaLinearLayout;
    TextView deliveriesHighLightTextView, hoursHighLightTextView, recommendationHighLightTextView,  restaurantNameTextView, fullAddressTextView, phoneTextView;
    ImageButton callToRestaurantImgBtn, waseToRestaurantImgBtn;


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



        if (getArguments() != null) {


            restaurant = (Restaurant) getArguments().getSerializable(getContext().getString(R.string.restaurant_detail));
            key = getArguments().getString("key");

//            restaurantProfileDetailsScrollView = view.findViewById(R.id.scroll_view_restaurant_profile_details_fragment);
//            restaurantProfileDetailsScrollView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//                @Override
//                public void onViewAttachedToWindow(View v) {
//
//                    String tag = (String) v.getTag();
//
//                    if (tag != null && !tag.isEmpty()){
//
//                        if (tag.equals("1") && v.isShown()){
//
//                            deliveriesHighLightTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sub_title_menu_background_clicked));
//                            hoursHighLightTextView.setBackground(null);
//                            recommendationHighLightTextView.setBackground(null);
//
//                        } else if (tag.equals("2") && v.isShown()){
//
//                            deliveriesHighLightTextView.setBackground(null);
//                            hoursHighLightTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sub_title_menu_background_clicked));
//                            recommendationHighLightTextView.setBackground(null);
//
//
//                        } else if (tag.equals("3") && v.isShown()){
//
//                            deliveriesHighLightTextView.setBackground(null);
//                            hoursHighLightTextView.setBackground(null);
//                            recommendationHighLightTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sub_title_menu_background_clicked));
//
//                        }
//
//
//                    }
//
//                }
//
//                @Override
//                public void onViewDetachedFromWindow(View v) {
//
//                }
//            });

            nonRecommendationLinearLayout = view.findViewById(R.id.linear_layout_non_recommendation_area_restaurant_profile_details_fragment);
            nonRecommendationLinearLayout.setVisibility(View.VISIBLE);

            deliveriesHighLightTextView = view.findViewById(R.id.deliveries_high_light_text_view_restaurant_profile_details_fragment);
            hoursHighLightTextView = view.findViewById(R.id.hours_high_light_text_view_restaurant_profile_details_fragment);
            recommendationHighLightTextView = view.findViewById(R.id.recommendation_high_light_text_view_restaurant_profile_details_fragment);


            restaurantNameTextView = view.findViewById(R.id.restaurant_name_text_view_restaurant_profile_details_fragment);
            fullAddressTextView = view.findViewById(R.id.full_address_text_view_restaurant_profile_details_fragment);
            phoneTextView = view.findViewById(R.id.phone_text_view_restaurant_profile_details_fragment);


            callToRestaurantImgBtn = view.findViewById(R.id.call_to_restaurant_img_btn_restaurant_profile_details_fragment);
            waseToRestaurantImgBtn = view.findViewById(R.id.wase_restaurant_img_btn_restaurant_profile_detauls_fragment);



            areasForDeliveryListView = view.findViewById(R.id.area_for_delivery_list_view_restaurant_profile_details_fragment);
            Toast.makeText(getContext(), String.valueOf(restaurant.getAreasForDeliveries().size()), Toast.LENGTH_SHORT).show();
            areasForDeliveryListView.setAdapter(new AreasForDeliveryAdapter(getContext(), restaurant.getAreasForDeliveries()));

            openCloseHoursListView = view.findViewById(R.id.open_close_hours_list_view_restaurant_profile_details_fragment);
            openCloseHoursListView.setAdapter(new OpenCloseRestaurantHoursAdapter(getContext(), restaurant.getOpenHour(), restaurant.getCloseHour()));


            recommendationListView = view.findViewById(R.id.recommendation_list_view_restaurant_profile_details_fragment);
            recommendationListView.setVisibility(View.GONE);




            restaurantNameTextView.setText(restaurant.getName());

            fullAddressTextView.setText(restaurant.getMyAddress().fullMyAddress());

            phoneTextView.setText(restaurant.getPhoneNumber());


        }

        return view;

    }





    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.call_to_restaurant_img_btn_restaurant_profile_details_fragment){

            //TODO: WORKING ON CALL BUTTON



        }else if (id == R.id.wase_restaurant_img_btn_restaurant_profile_detauls_fragment){

            //TODO: WORKING ON WAZE BUTTON



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