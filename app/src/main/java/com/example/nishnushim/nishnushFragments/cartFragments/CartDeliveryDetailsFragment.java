package com.example.nishnushim.nishnushFragments.cartFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nishnushim.R;
import com.example.nishnushim.helpUIClass.CreateCustomBtn;
import com.example.nishnushim.helpclasses.Order;
import com.example.nishnushim.helpclasses.Restaurant;
import com.example.nishnushim.helpclasses.User;
import com.example.nishnushim.helpclasses.UserSingleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class CartDeliveryDetailsFragment extends Fragment {

    LinearLayout wayOfPayingLinearLayout;
    List<View> wayOfPayingView = new ArrayList<>();

    ImageView userImageView;
    TextView userNameTextView, userEmailTextView, userPhoneTextView;

    RadioGroup addressRadioGroup, noteToDeliverRadioGroup;
    RadioButton gpsLocationRadioBtn;
    Button saveNoteBtn, splitWayOfPayingBtn, addWayOfPayingBtn, sendToRestaurantBtn;

    EditText extraNoteEditText;

    Order order;
    Restaurant restaurant;
    String key;

    DatabaseReference mDatabase;



    public CartDeliveryDetailsFragment(Order order, Restaurant restaurant, String key) {
        this.order = order;
        this.restaurant = restaurant;
        this.key = key;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_delivery_details, container, false);

        wayOfPayingLinearLayout = view.findViewById(R.id.linear_layout_way_of_delivery_cart_delivery_details_fragment);
//        wayOfPayingView = view.findViewById(R.id.way_of_delivery_view_include_cart_delivery_details_fragment);

        order.getCashOrCredit().add(true);

        updateWayOfDeliveryRadioGroups();


        userImageView = view.findViewById(R.id.profile_image_view_cart_delivery_details_fragment);
        userNameTextView = view.findViewById(R.id.user_name_text_view_cart_delivery_details_fragment);
        userEmailTextView = view.findViewById(R.id.user_email_cart_delivery_details_fragment);
        userPhoneTextView = view.findViewById(R.id.user_phone_number_cart_delivery_details_fragment);

        addressRadioGroup = view.findViewById(R.id.list_of_address_radio_group_cart_delivery_details_fragment);
        gpsLocationRadioBtn = view.findViewById(R.id.my_location_address_to_deliver_radio_btn_cart_delivery_details_fragment);


        noteToDeliverRadioGroup = view.findViewById(R.id.note_to_deliver_radio_group_cart_delivery_details_fragment);
        extraNoteEditText = view.findViewById(R.id.add_note_to_deliver_edit_text_cart_delivery_details_fragment);
        saveNoteBtn = view.findViewById(R.id.save_notes_btn_address_list_pop_up_window);


        splitWayOfPayingBtn = view.findViewById(R.id.split_credit_card_btn_cart_delivery_details_fragment);
        addWayOfPayingBtn = view.findViewById(R.id.add_credit_card_btn_cart_delivery_details_fragment);
        sendToRestaurantBtn = view.findViewById(R.id.send_to_restaurant_order_btn_cart_delivery_details_fragment);


        mDatabase = FirebaseDatabase.getInstance().getReference("Orders");

        ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT////////////
        ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT////////////

        SharedPreferences mPrefs = getContext().getSharedPreferences("noam", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
//

//        User user = UserSingleton.getInstance().getUser();
        User user = gson.fromJson(json, User.class);

        ////WORKING ON ADDRESS OF ORDER/////////////////////////////////////////////
        ////////////////////////////////////////////////////////////
        for (int i = 0; i < user.getAddresses().size(); i++) {

            RadioButton radioButton = CreateCustomBtn.createRadioAddressBtn(getContext(),gpsLocationRadioBtn, i, 1, R.drawable.ic_icon_placeholder_small, user.getAddresses().get(i).fullMyAddress());

            if (user.getAddresses().get(i).isChosen()) {
                radioButton.setChecked(true);
            }
            addressRadioGroup.addView(radioButton);
        }


        RadioButton radioButton = CreateCustomBtn.createRadioAddressBtn(getContext() ,gpsLocationRadioBtn, user.getAddresses().size(), 3, R.drawable.ic_icon_add_button, "אני כרגע נמצא בכתובת אחרת");
        addressRadioGroup.addView(radioButton);


        addressRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (addressRadioGroup.getCheckedRadioButtonId() != -1){

                    for (int i = 0; i < user.getAddresses().size(); i++) {

                        if (addressRadioGroup.getChildAt(i + 1).getId() == addressRadioGroup.getCheckedRadioButtonId()) {
                            user.getAddresses().get(i).setChosen(true);
                            order.setAddressToDeliver(user.getAddresses().get(i));
                        } else user.getAddresses().get(i).setChosen(false);

                    }


                }


            }
        });

        ////WORKING ON ADDRESS OF ORDER/////////////////////////////////////////////
        ////////////////////////////////////////////////////////////





        ////WORKING ON NOTES FOR DELIVERY/////////////////////////////////////////////
        ////////////////////////////////////////////////////////////

        noteToDeliverRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (noteToDeliverRadioGroup.getCheckedRadioButtonId() != -1){

                    if (noteToDeliverRadioGroup.getChildAt(noteToDeliverRadioGroup.getChildCount() - 1).getId() == noteToDeliverRadioGroup.getCheckedRadioButtonId()){
                        noteToDeliverRadioGroup.getChildAt(noteToDeliverRadioGroup.getChildCount() - 1).setVisibility(View.GONE);
                        extraNoteEditText.setVisibility(View.VISIBLE);
                        saveNoteBtn.setVisibility(View.VISIBLE);
                    }

                }

            }
        });




        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extraNoteEditText.getText().toString().length() > 0){
                    //TODO: CREATE BUTTON PROGRAMMATICALLY WITH THE EXTRA EDIT TEXT
                    order.setNoteForDelivery(extraNoteEditText.getText().toString());
                }

                extraNoteEditText.setVisibility(View.GONE);
                saveNoteBtn.setVisibility(View.GONE);
                noteToDeliverRadioGroup.getChildAt(noteToDeliverRadioGroup.getChildCount() - 1).setVisibility(View.VISIBLE);
            }
        });

        ////WORKING ON NOTES FOR DELIVERY/////////////////////////////////////////////
        ////////////////////////////////////////////////////////////





        ////WORKING ON WAY OF PAYING ABOUT DELIVERY/////////////////////////////////////////////
        ////////////////////////////////////////////////////////////


        splitWayOfPayingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                order.getCashOrCredit().add(true);
                updateWayOfDeliveryRadioGroups();

            }
        });



        addWayOfPayingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ADD ACTIVITY OF ADDING CREDIT CARD

            }
        });





        sendToRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                order.setDate(currentDate);
                order.setTime(currentTime);

                //EXAMPLE
                order.setOrderNumber(10);

                if (user.getName() != null && !user.getName().isEmpty()) {
                    order.setCostumerName(user.getName());
                }

                if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
                    order.setCostumerPhone(UserSingleton.getInstance().getUser().getPhoneNumber());
                }

                Log.i("ORDER", order.toString());


//                mDatabase.child(key).child(currentDate).push().setValue(order);

                //CHECKING FOR AUTO INCREMENT
//                mDatabase.child(key).child(currentDate).child("reservasion").runTransaction(new Transaction.Handler() {
//                    @NonNull
//                    @Override
//                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
//
//                        int count = 1;
//
//                        if (mutableData.getValue() == null) {
//                            mutableData.setValue(count);
//                        } else {
//                            count += mutableData.getValue(Integer.class);
//                            mutableData.setValue(count);
//                        }
//
//                        order.setOrderNumber(count);
//
//
//
//
//                        return Transaction.success(mutableData);
//                    }
//
//                    @Override
//                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
//
//                        if (committed){
//
//                        }
//
//                    }
//                });



            }
        });


        ///////////WORKING ON WAY OF PAYING ABOUT DELIVERY/////////////////////////////////////////////
        ////////////////////////////////////////////////////////////


        return view;
    }





    private void updateWayOfDeliveryRadioGroups() {

        int position = order.getCashOrCredit().size() - 1;

        while (wayOfPayingView.size() < order.getCashOrCredit().size()){

            View view = LayoutInflater.from(getContext()).inflate(R.layout.way_of_delivery_layout, null);
            wayOfPayingView.add(view);
            wayOfPayingLinearLayout.addView(view);

            RadioGroup wayOfPayingRadioGroup = view.findViewById(R.id.way_of_paying_radio_group_cart_delivery_details_fragment);

            wayOfPayingRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (wayOfPayingRadioGroup.getCheckedRadioButtonId() != -1){

                        RadioButton radioButton = wayOfPayingRadioGroup.findViewById(wayOfPayingRadioGroup.getCheckedRadioButtonId());
                        if (radioButton.getTag() == "1"){
                            order.getCashOrCredit().set(position, false);
                        }else order.getCashOrCredit().set(position, true);

                    }

                }
            });
        }


    }


    
}