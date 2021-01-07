package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishnushim.helpclasses.User;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

                    if (LoginManager.getInstance() != null){
                        LoginManager.getInstance().logOut();
                    }

                    GoogleSignInOptions gso = new GoogleSignInOptions.
                            Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                            build();

                    GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(getApplicationContext() ,gso);

                    if (googleSignInClient != null) {
                        googleSignInClient.signOut();
                    }


                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
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