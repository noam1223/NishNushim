package com.example.nishnushim.nishnushFragments.cartFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nishnushim.R;
import com.example.nishnushim.adapters.CartOrderAdapter;
import com.example.nishnushim.helpclasses.AreasForDelivery;
import com.example.nishnushim.helpclasses.Restaurant;
import com.example.nishnushim.helpclasses.User;
import com.example.nishnushim.helpclasses.UserSingleton;
import com.example.nishnushim.helpclasses.Order;
import com.example.nishnushim.helpclasses.helpInterfaces.OrderListener;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;


public class CartDetailsFragment extends Fragment implements View.OnClickListener {


    RadioGroup wayToDeliverRadioGroup;

    TextView minToDeliverTextView, cutleryNumTextView, costOfExtrasTextView, timeToDeliveryTextView, dishesCostsTextView, deliveryCostTextView;
    TextView numOfDishesOrderedTextView, totalOrderCostTextView, minToOrderMinMsgTextView, totalOrderCostMinMsgTextView, forgetSomethingTextView;
    ListView cartDishDetailsListView;

    ImageButton addCutlerySumImgBtn, decreaseCutlerySumImgBtn;
    LinearLayout sauceLinearLayoutArea;
    RelativeLayout minOrderNotReachedRelativeLayout;

    Button continueOrderBtn;

    Order order;
    Restaurant restaurant;
    AreasForDelivery areasForDelivery;
    String key;
    OrderListener orderListener;


    public CartDetailsFragment(Order order, Restaurant restaurant, String key, OrderListener orderListener) {
        this.order = order;
        this.restaurant = restaurant;
        this.key = key;
        this.orderListener = orderListener;
    }


    //IN THIS FRAGMENT WE WORKING ON THE FOLLOWING DETAILS: WAY OF DELIVERY, MIN OF DELIVERY, CULTURE AND ETC, SAUCES,

    //WE PRODUCE TO THE USER THE FOLLOWING DETAILS: THE AMOUNT OF THE SAUCES MONEY, TIME TO DELIVERY,
    ///////////////////////////////////////////////  AMOUNT OF THE DISHES (MAYBE A AVERAGE), DELIVERY COST, AMOUNT OF DISHED MONEY
    //PRODUCE TO THE USER A SMALL WINDOW WITH THE FOLLOWING DETAILS: MIN TO ORDER, AMOUNT OF THE CART MONEY

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_details, container, false);

//        User user = UserSingleton.getInstance().getUser();


        ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT////////////
        ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT////////////
        SharedPreferences mPrefs = getContext().getSharedPreferences("noam", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");

        User user = gson.fromJson(json, User.class);


        ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT////////////
        ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT////////////

//        for (int i = 0; i < restaurant.getAreasForDeliveries().size(); i++) {
//            if (user.getChosenAddress().getCityName().equals(restaurant.getAreasForDeliveries().get(i).getAreaName())){
//                areasForDelivery = restaurant.getAreasForDeliveries().get(i);
//                break;
//            }
//        }

//        for (int i = 0; i < restaurant.getAreasForDeliveries().size(); i++) {
//
//            if (restaurant.getAreasForDeliveries().get(i).getAreaName().equals(UserSingleton.getInstance().getUser().getChosenAddress().getCityName())){
//                areasForDelivery = restaurant.getAreasForDeliveries().get(i);
//                break;
//            }
//
//        }

        areasForDelivery = restaurant.getAreasForDeliveries().get(0);


        sauceLinearLayoutArea = view.findViewById(R.id.sauces_linear_layout_area_cart_details_fragment);
        minOrderNotReachedRelativeLayout = view.findViewById(R.id.min_msg_cart_area_relative_layout_cart_details_fragment);

        wayToDeliverRadioGroup = view.findViewById(R.id.way_of_delivery_radio_group_cart_details_fragment);
        minToDeliverTextView = view.findViewById(R.id.min_to_deliver_order_details_cart_details_fragment);
        cartDishDetailsListView = view.findViewById(R.id.cart_dishes_list_view_cart_details_fragment);

        cutleryNumTextView = view.findViewById(R.id.number_of_supplies_text_view_cart_details_fragment);
        addCutlerySumImgBtn = view.findViewById(R.id.increment_supplies_img_btn_cart_details_fragment);
        decreaseCutlerySumImgBtn = view.findViewById(R.id.decrement_supplies_img_btn_cart_details_fragment);


        //TODO: DO NOT FORGET WORKING ON SAUCE COSTS

        costOfExtrasTextView = view.findViewById(R.id.cost_of_extras_text_view_cart_details_fragment);
        timeToDeliveryTextView = view.findViewById(R.id.time_to_delivery_text_view_cart_details_fragment);
        dishesCostsTextView = view.findViewById(R.id.dishes_costs_text_view_cart_details_fragment);
        deliveryCostTextView = view.findViewById(R.id.delivery_cost_cart_details_fragment);
        numOfDishesOrderedTextView = view.findViewById(R.id.num_of_dishes_text_view_cart_details_fragment);
        totalOrderCostTextView = view.findViewById(R.id.sum_of_cart_cart_details_fragment);

        minToOrderMinMsgTextView = view.findViewById(R.id.min_to_order_text_view_cart_details_fragment);
        totalOrderCostMinMsgTextView = view.findViewById(R.id.sum_of_cart_min_msg_cart_details_fragment);
        forgetSomethingTextView = view.findViewById(R.id.forget_something_text_view_cart_details_fragment);

        continueOrderBtn = view.findViewById(R.id.continue_order_btn_cart_details_fragment);


        ////////////WORKING ON WAY TO DELIVER - TAKE AWAY, DELIVERY///////////////
        ///////////////////////////////////////////////////////////////////////////
        wayToDeliverRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (wayToDeliverRadioGroup.getCheckedRadioButtonId() != -1) {

                    order.setWayOfDelivery(((RadioButton) wayToDeliverRadioGroup.findViewById(wayToDeliverRadioGroup.getCheckedRadioButtonId())).getText().toString());

                }

            }
        });

        ///////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////


        minToDeliverTextView.setText("₪ " + areasForDelivery.getMinToDeliver());
        cartDishDetailsListView.setAdapter(new CartOrderAdapter(getContext(), order.getOrder()));
        cutleryNumTextView.setText(String.valueOf(order.getNumOfCulture()));


        //TODO: SET TIME TO TIME AND NOT ONLY ONE INT (15 - 70 MINUTES)
        timeToDeliveryTextView.setText(String.valueOf(areasForDelivery.getTimeOfDelivery()));
        ////////////////////////////////////////////


        //TODO: ASK ORLY WHAT THE MEANING OF THIS
        numOfDishesOrderedTextView.setText("סה״כ מנות (" + order.getOrder().getDishList().size() + ")");

        deliveryCostTextView.setText(String.valueOf(areasForDelivery.getDeliveryCost()));

        if (order.getSumOfOrder() >= areasForDelivery.getMinToDeliver())
            minOrderNotReachedRelativeLayout.setVisibility(View.GONE);
        else
            minOrderNotReachedRelativeLayout.setVisibility(View.VISIBLE);


        totalOrderCostTextView.setText(order.getSumOfOrder() + "₪");
        minToOrderMinMsgTextView.setText("לא הגעת למינימום הזמנה" + areasForDelivery.getMinToDeliver() + "₪");
        totalOrderCostTextView.setText(String.valueOf(order.getSumOfOrder()));
        totalOrderCostMinMsgTextView.setText(String.valueOf(order.getSumOfOrder()));

        addCutlerySumImgBtn.setOnClickListener(this);
        decreaseCutlerySumImgBtn.setOnClickListener(this);
        forgetSomethingTextView.setOnClickListener(this);
        continueOrderBtn.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();


        //ADD CULTURE SUM
        if (id == R.id.increment_supplies_img_btn_cart_details_fragment) {


            order.setNumOfCulture(order.getNumOfCulture() + 1);
            cutleryNumTextView.setText(String.valueOf(order.getNumOfCulture()));


            //DEC CULTURE SUM
        } else if (id == R.id.decrement_supplies_img_btn_cart_details_fragment) {


            if (order.getNumOfCulture() == 0) {
                order.setNumOfCulture(0);
            }else order.setNumOfCulture(order.getNumOfCulture() - 1);

            cutleryNumTextView.setText(String.valueOf(order.getNumOfCulture()));


            //CLOSE ACTIVITY
        } else if (id == R.id.forget_something_text_view_cart_details_fragment) {

            if (getActivity() != null) {
                getActivity().finish();
            }

            //CHECK IF THE ORDER DETAILS OKAY AND CONTINUE WITH THE EXTRA DETAILS ORDER
        } else if (id == R.id.continue_order_btn_cart_details_fragment) {

            if (order.getSumOfOrder() >= areasForDelivery.getMinToDeliver()) {
                orderListener.onContinueWithOrder(order);
            }

        }


    }
}