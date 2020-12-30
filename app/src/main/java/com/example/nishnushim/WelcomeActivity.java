package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button signInPhoneBtn;
    TextView getInByGuestTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        signInPhoneBtn = findViewById(R.id.sign_in_by_phone_btn_welcome_activity);
        getInByGuestTextView = findViewById(R.id.sign_in_by_guest_text_view_welcome_activity);

        signInPhoneBtn.setOnClickListener(this);
        getInByGuestTextView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.sign_in_by_phone_btn_welcome_activity){

            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);



        } else if (id == R.id.sign_in_by_guest_text_view_welcome_activity){


            Intent intent = new Intent(getApplicationContext(), FirstAddressActivity.class);
            startActivity(intent);

        }


    }
}