package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.nishnushim.nishnushFragments.signinfragment.AddressDetailsFragment;
import com.example.nishnushim.nishnushFragments.signinfragment.ChooseAddAddressFragment;
import com.example.nishnushim.helpclasses.User;

public class FirstAddressActivity extends AppCompatActivity {


    boolean isFirstAddress = true;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_address);


        Toolbar toolbar = findViewById(R.id.tool_bar_sign_in_activity);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        isFirstAddress = intent.getBooleanExtra("is_first", true);

        Fragment fragment = null;
        Bundle bundle = new Bundle();

        if (isFirstAddress){

            fragment = new ChooseAddAddressFragment();

            if (intent.getStringExtra("phone") != null){

                String phoneNumber = getIntent().getStringExtra("phone");
                bundle.putString("phone", phoneNumber);

            }

        }else {

            if (intent.getSerializableExtra("user") != null){

                user = (User) intent.getSerializableExtra("user");
                bundle.putSerializable("user", user);

                fragment = new AddressDetailsFragment(user, false);

            }
        }


        fragment.setArguments(bundle);


        getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.sign_in_frame_layout_first_address_activity, fragment).commit();





    }
}