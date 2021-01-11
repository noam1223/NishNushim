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


    Button findMeByGpsBtn, addAddressManuallyBtn;



    Fragment fragment;
    Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_add_address, container, false);


        findMeByGpsBtn = view.findViewById(R.id.find_my_address_by_gps_btn_address_manually_fragment);
        addAddressManuallyBtn = view.findViewById(R.id.manually_address_btn_address_manually_fragment);


        findMeByGpsBtn.setOnClickListener(this);
        addAddressManuallyBtn.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();


        if (id == R.id.find_my_address_by_gps_btn_address_manually_fragment) {

            fragment = new AddressDetailsFragment(true);

        } else if (id == R.id.manually_address_btn_address_manually_fragment) {

            fragment = new AddressDetailsFragment(false);

        }

        getFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.sign_in_frame_layout_first_address_activity, fragment).commit();


    }








}