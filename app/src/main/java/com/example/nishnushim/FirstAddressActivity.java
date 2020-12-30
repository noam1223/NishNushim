package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.nishnushim.fragments.signinfragment.ChooseAddAddressFragment;
import com.example.nishnushim.fragments.signinfragment.PhoneNumberFragment;
import com.example.nishnushim.helpclasses.Address;
import com.example.nishnushim.helpclasses.User;

public class FirstAddressActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_address);


        Toolbar toolbar = findViewById(R.id.tool_bar_sign_in_activity);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if (getIntent().getStringExtra("phone") != null){

            String phoneNumber = getIntent().getStringExtra("phone");
            Fragment fragment = new ChooseAddAddressFragment();
            Bundle bundle = new Bundle();
            bundle.putString("phone", phoneNumber);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
            ).replace(R.id.sign_in_frame_layout_first_address_activity, fragment).commit();

        }











    }
}