package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nishnushim.helpclasses.User;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {

    TextView nameTextView, phoneNumberTextView;
    Button logOutBtn;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        auth = FirebaseAuth.getInstance();

        nameTextView = findViewById(R.id.name_text_view_profile_activity);
        phoneNumberTextView = findViewById(R.id.phone_number_text_view_profile_activity);
        logOutBtn = findViewById(R.id.log_out_btn_profile_activity);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (auth.getCurrentUser() != null){

                    auth.signOut();
                    finish();

                }

            }
        });


        if (getIntent().getSerializableExtra("user") != null){

            User user = (User) getIntent().getSerializableExtra("user");

            if (user.getName() != null){
                nameTextView.setText("שם: " + user.getName());
            }else nameTextView.setText("שם: אין");


            if (user.getPhoneNumber() != null){
                phoneNumberTextView.setText("מספר טלפון: " + user.getPhoneNumber());
            }else phoneNumberTextView.setText("מספר טלפון: אין");
        }

    }
}