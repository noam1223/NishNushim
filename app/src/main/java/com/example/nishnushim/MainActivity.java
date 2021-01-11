package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();


//                auth.signOut();
//
//                if (LoginManager.getInstance() != null){
//                    LoginManager.getInstance().logOut();
//                }
//
//                GoogleSignInOptions gso = new GoogleSignInOptions.
//                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
//                        build();
//
//                GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(getApplicationContext() ,gso);
//
//                if (googleSignInClient != null) {
//                    googleSignInClient.signOut();
//                }
//                finish();


//                if (user == null){
//
//                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//
//                } else {
//
//                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                }



                //TODO: DO NOT FORGET TO CHANGE TO USER
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


                finish();

            }
        }, 1500);



    }
}