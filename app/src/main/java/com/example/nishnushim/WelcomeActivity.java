package com.example.nishnushim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 12345;
    private static final String TAG = "WELCOME_EXCEPTION";
    Button signInPhoneBtn;
    TextView getInByGuestTextView;

    ImageView googleSignInImgView, facebookSignInBtn;

    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth auth;
    private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        FacebookSdk.fullyInitialize();
        AppEventsLogger.activateApp(getApplication());


//        Toast.makeText(this, FirebaseAnalytics.getInstance(this).getFirebaseInstanceId(), Toast.LENGTH_SHORT).show();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        auth = FirebaseAuth.getInstance();


        signInPhoneBtn = findViewById(R.id.sign_in_by_phone_btn_welcome_activity);
        getInByGuestTextView = findViewById(R.id.sign_in_by_guest_text_view_welcome_activity);

        googleSignInImgView = findViewById(R.id.google_sign_in_anishnushim_welcome_activity);
        facebookSignInBtn = findViewById(R.id.facebook_sign_in_anishnushim_welcome_activity);


        facebookSignInBtn.setOnClickListener(this);
        googleSignInImgView.setOnClickListener(this);
        signInPhoneBtn.setOnClickListener(this);
        getInByGuestTextView.setOnClickListener(this);


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }

        } else mCallbackManager.onActivityResult(requestCode, resultCode, data);


    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null){

                                moveUserToAddressFirst();

                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(WelcomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.sign_in_by_phone_btn_welcome_activity) {

            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);


        } else if (id == R.id.sign_in_by_guest_text_view_welcome_activity) {


            moveUserToAddressFirst();


        } else if (id == R.id.google_sign_in_anishnushim_welcome_activity) {


            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);


        } else if (id == R.id.facebook_sign_in_anishnushim_welcome_activity){


            Toast.makeText(this, "FACEBOOK", Toast.LENGTH_SHORT).show();
            mCallbackManager = CallbackManager.Factory.create();

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos", "email", "public_profile", "user_posts"));
            LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    AuthCredential authCredential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());

                    auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                user = auth.getCurrentUser();
                                AccessToken accessToken = AccessToken.getCurrentAccessToken();

                                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


                                if (user != null) {

                                    moveUserToAddressFirst();

                                }


                            } else {

                                Toast.makeText(WelcomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    });


                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(WelcomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


    }


    private void moveUserToAddressFirst() {
        Intent intent = new Intent(getApplicationContext(), FirstAddressActivity.class);
        startActivity(intent);
    }
}