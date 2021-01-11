package com.example.nishnushim.fragments.signinfragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nishnushim.HomePageActivity;
import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.MyAddress;
import com.example.nishnushim.helpclasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;


public class AddressDetailsFragment extends Fragment {

    private static final int REQUEST = 1111;

    EditText cityNameEditText, streetNameEditText, houseNumberEditText;
    Button signInAddressBtn;
    private LocationManager mLocationManager;

    boolean setGps = false;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    User user;

    Location gps_loc;
    Location network_loc;
    Location final_loc;

    double longitude;
    double latitude;

    MyAddress myAddress;


    public AddressDetailsFragment() {


    }

    public AddressDetailsFragment(boolean setGps) {
        this.setGps = setGps;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address_details, container, false);

        cityNameEditText = view.findViewById(R.id.city_address_edit_text_address_details_fragment);
        streetNameEditText = view.findViewById(R.id.street_address_edit_text_address_details_fragment);
        houseNumberEditText = view.findViewById(R.id.number_street_address_edit_text_address_details_fragment);
        signInAddressBtn = view.findViewById(R.id.save_first_address_btn_address_manually_activity);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        if (setGps) {

            mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

            if (LocationManagerCompat.isLocationEnabled(mLocationManager)) {
                myAddress = getAddressByGps();

                if (myAddress != null) {

                    cityNameEditText.setText(myAddress.getCityName());
                    streetNameEditText.setText(myAddress.getStreetName());
                    houseNumberEditText.setText(myAddress.getHouseNumber());

                }

            } else buildAlertMessageNoGps();

        }


        signInAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String city = cityNameEditText.getText().toString();
                String street = streetNameEditText.getText().toString();
                String houseNumber = houseNumberEditText.getText().toString();

                User user = new User();

                if (!city.isEmpty() && !street.isEmpty() && !houseNumber.isEmpty()) {


                    if (myAddress == null) {
                        MyAddress myAddress = findCoordinateByAddress(city, street, houseNumber);

                        if (myAddress != null) {
                            user.getAddresses().add(myAddress);
                        }

                    }else {
                        user.getAddresses().add(myAddress);
                    }


                    if (getActivity().getIntent().getStringExtra("phone") != null) {

                        user.setPhoneNumber(getActivity().getIntent().getStringExtra("phone"));
                    }


                    db.collection(getString(R.string.USERS_DB)).document("LCl46tZA2wd53H8If1mWTb2VjGw2").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Intent intent = new Intent(getContext(), HomePageActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("user", user);
                                startActivity(intent);

                            } else {

                                if (task.isCanceled())
                                    Toast.makeText(getContext(), "ישנה בעיית התחברות עם השרת", Toast.LENGTH_SHORT).show();


                                if (task.getException() != null) {
                                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }

                        }
                    });


                    //EDIT TEXTS ARE EMPTY
                } else
                    Toast.makeText(getContext(), "אנא הכנס נתונים מתאימים", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
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
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST);
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


    private MyAddress findCoordinateByAddress(String city, String street, String streetNumber) {


        MyAddress myAddress = new MyAddress(city, street, streetNumber);

        Geocoder geocoder = new Geocoder(getContext());
        String address = "ישראל, " + myAddress.getCityName() + "," +
                myAddress.getStreetName() + " " + myAddress.getHouseNumber();

        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);

            if (addresses.size() > 0) {


                myAddress.setLatitude(addresses.get(0).getLatitude());
                myAddress.setLongitude(addresses.get(0).getLongitude());


            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return myAddress;

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
