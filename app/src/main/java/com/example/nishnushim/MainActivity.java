package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.nishnushim.helpclasses.Restaurant;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

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

                SharedPreferences mPrefs = getSharedPreferences("noam" , MODE_PRIVATE);
//
//
//                //MOVING TO MENU DIRECTLY
//                Gson gson = new Gson();
//                String json = mPrefs.getString("MyObject", "");
//                String keyjson = mPrefs.getString("key", "");
//                Restaurant obj = gson.fromJson(json, Restaurant.class);
//                String objKey = gson.fromJson(keyjson, String.class);
//////
//////
//////
//                    Intent intent = new Intent(getApplicationContext(), RestaurantProfileHomeActivity.class);
////                    Intent intent = new Intent(getApplicationContext(), FirstAddressActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.putExtra(getBaseContext().getString(R.string.restaurant_detail), obj );
//                    intent.putExtra("key", objKey);
//                    startActivity(intent);




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



//                //TODO: DO NOT FORGET TO CHANGE TO USER
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);




                finish();

            }
        }, 1500);



    }
}