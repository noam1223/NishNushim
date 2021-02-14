package com.example.nishnushim.nishnushFragments.cartFragments;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.nishnushim.R;
import com.example.nishnushim.helpUIClass.CreateCustomBtn;
import com.example.nishnushim.helpclasses.CreditCard;
import com.example.nishnushim.helpclasses.MyAddress;
import com.example.nishnushim.helpclasses.Order;
import com.example.nishnushim.helpclasses.Restaurant;
import com.example.nishnushim.helpclasses.User;
import com.example.nishnushim.helpclasses.UserSingleton;
import com.example.nishnushim.helpclasses.WayOfPayment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;


public class CartDeliveryDetailsFragment extends Fragment {

    public static final int REQUEST_GPS = 1212;


    LinearLayout wayOfPayingLinearLayout;
    List<View> wayOfPayingView = new ArrayList<>();

    ImageView userImageView;
    TextView userNameTextView, userEmailTextView, userPhoneTextView;

    RadioGroup addressRadioGroup, noteToDeliverRadioGroup;
    RadioButton gpsLocationRadioBtn;
    Button saveNoteBtn, splitWayOfPayingBtn, addWayOfPayingBtn, sendToRestaurantBtn;
    ImageButton editProfileImgBtn;

    EditText extraNoteEditText;

    Order order;
    Restaurant restaurant;
    String key;

    DatabaseReference mDatabase;
    private LocationManager mLocationManager;
    Location gps_loc;
    Location network_loc;
    Location final_loc;

    double longitude;
    double latitude;




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

        userImageView = view.findViewById(R.id.profile_image_view_cart_delivery_details_fragment);
        userNameTextView = view.findViewById(R.id.user_name_text_view_cart_delivery_details_fragment);
        userEmailTextView = view.findViewById(R.id.user_email_cart_delivery_details_fragment);
        userPhoneTextView = view.findViewById(R.id.user_phone_number_cart_delivery_details_fragment);
        editProfileImgBtn = view.findViewById(R.id.edit_profile_detail_img_btn_cart_delivery_details_fragment);

        addressRadioGroup = view.findViewById(R.id.list_of_address_radio_group_cart_delivery_details_fragment);
        gpsLocationRadioBtn = view.findViewById(R.id.my_location_address_to_deliver_radio_btn_cart_delivery_details_fragment);


        noteToDeliverRadioGroup = view.findViewById(R.id.note_to_deliver_radio_group_cart_delivery_details_fragment);
        extraNoteEditText = view.findViewById(R.id.add_note_to_deliver_edit_text_cart_delivery_details_fragment);
        saveNoteBtn = view.findViewById(R.id.save_notes_btn_address_list_pop_up_window);


        splitWayOfPayingBtn = view.findViewById(R.id.split_credit_card_btn_cart_delivery_details_fragment);
        addWayOfPayingBtn = view.findViewById(R.id.add_credit_card_btn_cart_delivery_details_fragment);
        sendToRestaurantBtn = view.findViewById(R.id.send_to_restaurant_order_btn_cart_delivery_details_fragment);


        mDatabase = FirebaseDatabase.getInstance().getReference("Orders");

        order.getWayOfPayments().add(new WayOfPayment(WayOfPayment.WayOfPaymentEnum.CREDIT, new CreditCard(), order.getSumOfOrder()));

        updateWayOfDeliveryRadioGroups();

        ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT////////////
        ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT//////////// ///SAVE FOR THE MOMENT////////////

        SharedPreferences mPrefs = getContext().getSharedPreferences("noam", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
//

        User user = UserSingleton.getInstance().getUser();
//        User user = gson.fromJson(json, User.class);




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

                if (addressRadioGroup.getCheckedRadioButtonId() != -1) {

                    if (addressRadioGroup.getCheckedRadioButtonId() == gpsLocationRadioBtn.getId()) {

                        mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

                        if (LocationManagerCompat.isLocationEnabled(mLocationManager)) {
                            MyAddress myAddress = getAddressByGps();

                            if (myAddress != null) {

                                gpsLocationRadioBtn.setText(myAddress.fullMyAddress());

//                                cityNameEditText.setText(myAddress.getCityName());
//                                streetNameEditText.setText(myAddress.getStreetName());
//                                houseNumberEditText.setText(myAddress.getHouseNumber());

                            }else gpsLocationRadioBtn.setText("לא נמצאה כתובת");

                        } else buildAlertMessageNoGps();


                    } else {

                        for (int i = 0; i < user.getAddresses().size(); i++) {

                            if (addressRadioGroup.getChildAt(i + 1).getId() == addressRadioGroup.getCheckedRadioButtonId()) {

                                for (int j = 0; j < restaurant.getAreasForDeliveries().size(); j++) {

                                    if (restaurant.getAreasForDeliveries().get(j).getAreaName().equals(user.getChosenAddress().getCityName())) {
                                        user.getAddresses().get(i).setChosen(true);
                                        order.setAddressToDeliver(user.getAddresses().get(i));
                                        return;
                                    }

                                }


                            } else {
                                user.getAddresses().get(i).setChosen(false);
                            }

                        }

                        Toast.makeText(getContext(), "אין אפשרות לבחור עיר אחרת", Toast.LENGTH_SHORT).show();

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
                    }else order.setNoteForDelivery(((RadioButton)noteToDeliverRadioGroup.findViewById(noteToDeliverRadioGroup.getCheckedRadioButtonId())).getText().toString());

                }

            }
        });




        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extraNoteEditText.getText().toString().length() > 0){

                    //TODO: CREATE BUTTON PROGRAMMATICALLY WITH THE EXTRA EDIT TEXT
                    order.setNoteForDelivery(extraNoteEditText.getText().toString());
                    RadioButton radioButton = CreateCustomBtn.createAddressRadioChanges(getContext(),gpsLocationRadioBtn, extraNoteEditText.getText().toString());

                    noteToDeliverRadioGroup.addView(radioButton,noteToDeliverRadioGroup.getChildCount() - 2);
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

                order.getWayOfPayments().add(new WayOfPayment(WayOfPayment.WayOfPaymentEnum.CREDIT, new CreditCard(), 0));
                updateWayOfDeliveryRadioGroups();
//                order.getCashOrCredit().add(true);
//                updateWayOfDeliveryRadioGroups();

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

//                order.setOrderNumber();
//                order.setOrder();
//                order.setWayOfDelivery();
//                order.setNoteForDelivery();
//                order.setSumOfOrder();
//                order.setNumOfCulture();
//                order.setSauceChanges();
//                order.setAddressToDeliver();
//                order.setDate();
//                order.setTime();
//                order.setCostumerName();
//                order.setCostumerPhone();
//                order.setCashOrCredit();


                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                order.setDate(currentDate);
                order.setTime(currentTime);

                //EXAMPLE
                order.setOrderNumber(10);

                if (user.getName() != null && !user.getName().isEmpty()) {
                    order.setCostumerName(user.getName());
                }else if (!userNameTextView.getText().toString().isEmpty()){
                    order.setCostumerName(userNameTextView.getText().toString());
                }

                if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
                    order.setCostumerPhone(UserSingleton.getInstance().getUser().getPhoneNumber());
                }else if (!userPhoneTextView.getText().toString().isEmpty()){
                    order.setCostumerPhone(userPhoneTextView.getText().toString());
                }

                order.setRestaurantKey(key);

                //TODO: ADD ORDER TO USER
//                UserSingleton.getInstance().getUser().getOrderList().add(order);
//                FirebaseFirestore.getInstance().collection(getString(R.string.USERS_DB)).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("orderList").add(order);


                mDatabase.child(key).child(currentDate).push().setValue(order);


                Toast.makeText(getContext(), "הזמנה נשלחה בהצלחה", Toast.LENGTH_SHORT).show();
                getActivity().finish();


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



        ///////////WORKING ON EDIT PROFILE/////////////////////////////////////////////
        ////////////////////////////////////////////////////////////

        editProfileImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: OPEN POP UO TO EDIT PROFILE DETAILS

            }
        });


        ///////UPDATE UI////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////

        updateUserDetailUI(user);






        return view;
    }




    private void updateUserDetailUI(User user) {


        //TODO: SET USER IMAGE
//        userImageView


        userNameTextView.setText(user.getName());

        //TODO: ADD EMAIL TO USER OBJECT
//        userEmailTextView


        userPhoneTextView.setText(user.getPhoneNumber());



    }


    private void updateWayOfDeliveryRadioGroups() {

        //TODO: UPDATE SLITE WAY OF PAYMENT

        wayOfPayingView.clear();
        wayOfPayingLinearLayout.removeAllViews();

        int position = 0;
//
        while (wayOfPayingView.size() < order.getWayOfPayments().size()){

            int currentIndex = position;

            View view = LayoutInflater.from(getContext()).inflate(R.layout.way_of_delivery_layout, null);
            wayOfPayingView.add(view);
            wayOfPayingLinearLayout.addView(view);


            RadioGroup wayOfPayingRadioGroup = view.findViewById(R.id.way_of_paying_radio_group_cart_delivery_details_fragment);
            LinearLayout editCostLinearLayout = view.findViewById(R.id.edit_cost_linear_layout_area_way_of_delivery_layout);
            TextView editCostTextView = view.findViewById(R.id.edit_cost_text_view_way_of_delivery_layout);
            ImageButton editCostImgBtn = view.findViewById(R.id.edit_cost_img_btn_way_of_delivery_layout);
            ImageButton removeImageBtn = view.findViewById(R.id.remove_cost_img_btn_way_of_delivery_layout);


            int costDivider = order.getSumOfOrder() / order.getWayOfPayments().size();
            int costDividerRest = 0;


            if (order.getWayOfPayments().size() == position + 1){
                costDividerRest = order.getSumOfOrder() % order.getWayOfPayments().size();
            }


            editCostTextView.setText((costDivider + costDividerRest) + " ₪ ");


            if (order.getWayOfPayments().size() > 1){

                editCostImgBtn.setVisibility(View.VISIBLE);
                removeImageBtn.setVisibility(View.VISIBLE);

                editCostImgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //TODO: ADD POP UP TO SET PRICE
                        View popUpView = getLayoutInflater().inflate(R.layout.cost_way_of_paying_pop_up_window, null);
                        Dialog wayOfDeliveryDialog = new Dialog(getContext());
                        wayOfDeliveryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        wayOfDeliveryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        wayOfDeliveryDialog.setContentView(popUpView);

                        ImageButton exitImgBtn = popUpView.findViewById(R.id.close_window_image_btn_cost_way_of_paying_pop_up_window);
                        EditText costEditText = popUpView.findViewById(R.id.cost_edit_text_cost_way_of_paying_pop_up_window);
                        TextView approveEditCostTextView = popUpView.findViewById(R.id.edit_text_view_cost_way_of_paying_pop_up_window);
                        TextView cancelTextView = popUpView.findViewById(R.id.cancel_text_view_cost_way_of_paying_pop_up_window);


                        exitImgBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                wayOfDeliveryDialog.dismiss();
                            }
                        });



                        cancelTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                wayOfDeliveryDialog.dismiss();
                            }
                        });


                        approveEditCostTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String cost = costEditText.getText().toString();

                                if (!cost.isEmpty()){
                                    order.getWayOfPayments().get(currentIndex).setAmountToPay(Integer.parseInt(cost));
                                    editCostTextView.setText(cost);
                                    wayOfDeliveryDialog.dismiss();
                                }else
                                    Toast.makeText(getContext(), "אנא הכנס סכום", Toast.LENGTH_SHORT).show();

                            }
                        });





                        wayOfDeliveryDialog.create();
                        wayOfDeliveryDialog.show();

                        Window window = wayOfDeliveryDialog.getWindow();
                        window.setLayout(125, 186);

                    }
                });

            }else {

                editCostImgBtn.setVisibility(View.GONE);
                removeImageBtn.setVisibility(View.GONE);

            }




            removeImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    order.getWayOfPayments().remove(currentIndex);
                    updateWayOfDeliveryRadioGroups();

                }
            });



            wayOfPayingRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (wayOfPayingRadioGroup.getCheckedRadioButtonId() != -1){

                        RadioButton radioButton = wayOfPayingRadioGroup.findViewById(wayOfPayingRadioGroup.getCheckedRadioButtonId());
                        if (radioButton.getTag() == "1"){
                            order.getWayOfPayments().get(currentIndex).setWayOfPaymentEnum(WayOfPayment.WayOfPaymentEnum.CASH);
                        }else order.getWayOfPayments().get(currentIndex).setWayOfPaymentEnum(WayOfPayment.WayOfPaymentEnum.CREDIT);

                    }

                }
            });




            position++;
        }
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_GPS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //call get location here

                    if (LocationManagerCompat.isLocationEnabled(mLocationManager)) {

                        getAddressByGps();

                    }

                } else {
                    Toast.makeText(getContext(), "לאפליקצייה אין אפשרות גישה למיקום שלך", Toast.LENGTH_LONG).show();
                }
            }
        }
    }






    private MyAddress getAddressByGps() {

        try {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_GPS);
                return null;
            }

            gps_loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        } catch (Exception e) {
            e.printStackTrace();
        }


        if (gps_loc != null) {
            final_loc = gps_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        } else if (network_loc != null) {
            final_loc = network_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        } else {
            latitude = 0.0;
            longitude = 0.0;
        }


        Address address = findAddressByCoordinates(latitude, longitude);

        if (address != null) {

            MyAddress myAddress = new MyAddress();
            myAddress.setCityName(address.getLocality());
            myAddress.setStreetName(address.getThoroughfare());
            myAddress.setHouseNumber(address.getSubThoroughfare());
            myAddress.setLatitude(latitude);
            myAddress.setLongitude(longitude);

            return myAddress;
        }

        return null;
    }



    private Address findAddressByCoordinates(double latitude, double longitude) {
        try {
            Locale lHebrew = new Locale("he");
            Geocoder geocoder = new Geocoder(getContext(), lHebrew);
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                return addresses.get(0);
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }




    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("מצא מיקום אינו פעיל, באפשרותך להפעיל זאת")
                .setCancelable(false)
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    
}