package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishnushim.helpclasses.User;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {

    EditText nameEditText,emailEditText ,phoneNumberEditText;

    ImageButton backImgBtn;

    Button saveProfileBtn;
    FirebaseAuth auth;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        auth = FirebaseAuth.getInstance();

        nameEditText = findViewById(R.id.name_edit_text_user_profile_activity);
        phoneNumberEditText = findViewById(R.id.telephone_edit_text_user_profile_activity);
        emailEditText = findViewById(R.id.email_edit_text_user_profile_activity);

        saveProfileBtn = findViewById(R.id.save_profile_user_btn_user_profile_activity);
        saveProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneNumberEditText.getText().toString();

                boolean changed = false;

                if (!name.isEmpty() && !name.equals(user.getName())){
                    changed = true;
                }

//                if (!email.isEmpty() && !email.equals(user.em())){
//                    changed = true;
//                }

                if (!phone.isEmpty() && !phone.equals(user.getPhoneNumber())){
                    changed = true;
                }



                if (changed){

                    //TODO: SAVE NEW USER DETAILS

                }
            }
        });

        backImgBtn = findViewById(R.id.back_img_btn_user_profile_activity);
        backImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






//        logOutBtn = findViewById(R.id.log_out_btn_profile_activity);
//        logOutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (auth.getCurrentUser() != null){
//
//                    auth.signOut();
//
//                    if (LoginManager.getInstance() != null){
//                        LoginManager.getInstance().logOut();
//                    }
//
//                    GoogleSignInOptions gso = new GoogleSignInOptions.
//                            Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
//                            build();
//
//                    GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(getApplicationContext() ,gso);
//
//                    if (googleSignInClient != null) {
//                        googleSignInClient.signOut();
//                    }
//
//
//                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                    startActivity(intent);
//                    finish();
//
//                }
//
//            }
//        });


        if (getIntent().getSerializableExtra("user") != null){

            user = (User) getIntent().getSerializableExtra("user");

            if (user.getName() != null){
                nameEditText.setText("שם: " + user.getName());
            }


            if (user.getPhoneNumber() != null){
                phoneNumberEditText.setText("מספר טלפון: " + user.getPhoneNumber());
            }
        }

    }



}