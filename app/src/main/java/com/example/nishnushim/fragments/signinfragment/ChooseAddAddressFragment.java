package com.example.nishnushim.fragments.signinfragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
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
import android.widget.Toast;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.MyAddress;
import com.example.nishnushim.helpclasses.User;

import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;


public class ChooseAddAddressFragment extends Fragment implements View.OnClickListener {


    private static final int REQUEST = 1111;
    Button findMeByGpsBtn, addAddressManuallyBtn;
    private LocationManager mLocationManager;

    Location gps_loc;
    Location network_loc;
    Location final_loc;

    double longitude;
    double latitude;

    Fragment fragment;
    Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_add_address, container, false);

        mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        findMeByGpsBtn = view.findViewById(R.id.find_my_address_by_gps_btn_address_manually_fragment);
        addAddressManuallyBtn = view.findViewById(R.id.manually_address_btn_address_manually_fragment);


        findMeByGpsBtn.setOnClickListener(this);
        addAddressManuallyBtn.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        fragment = new AddressDetailsFragment();


        if (id == R.id.find_my_address_by_gps_btn_address_manually_fragment) {

            if (LocationManagerCompat.isLocationEnabled(mLocationManager)){

                getAddressByGps();

            } else buildAlertMessageNoGps();


        } else if (id == R.id.manually_address_btn_address_manually_fragment) {

            getFragmentManager().beginTransaction().setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
            ).replace(R.id.sign_in_frame_layout_first_address_activity, fragment).commit();

        }


    }



    private void getAddressByGps() {

        try {


            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST);
                return;
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
        }
        else if (network_loc != null) {
            final_loc = network_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        }
        else {
            latitude = 0.0;
            longitude = 0.0;
        }


        try {

            User user = new User();
            Locale lHebrew = new Locale("he");
            Geocoder geocoder = new Geocoder(getContext(), lHebrew);
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {

                Toast.makeText(getContext(), addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                user.getAddresses().add(new MyAddress(addresses.get(0).getLocality(), addresses.get(0).getThoroughfare(), addresses.get(0).getSubThoroughfare()));
                bundle.putSerializable("user", user);


//                    userCountry = addresses.get(0).getCountryName();
//                    userAddress = addresses.get(0).getAddressLine(0);
//                    tv.setText(userCountry + ", " + userAddress);
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        fragment = new AddressDetailsFragment(true);
        fragment.setArguments(bundle);

        getFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.sign_in_frame_layout_first_address_activity, fragment).commit();
    }






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //call get location here

                    if (LocationManagerCompat.isLocationEnabled(mLocationManager)){

                        getAddressByGps();

                    }

                } else {
                    Toast.makeText(getContext(), "לאפליקצייה אין אפשרות גישה למיקום שלך", Toast.LENGTH_LONG).show();
                }
            }
        }
    }




    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("מצא מיקום אינו פעיל, באפשרותך להפעיל זאת")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,  final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


}