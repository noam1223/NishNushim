package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

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


//                if (user == null){
//
//                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//
//                } else {
//
////                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                    startActivity(intent);
////                    finish();
//
////                    auth.signOut();
//
//                }

                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();


            }
        }, 1500);



    }
}