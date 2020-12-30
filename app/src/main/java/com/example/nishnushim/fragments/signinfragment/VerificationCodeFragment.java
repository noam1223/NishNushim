package com.example.nishnushim.fragments.signinfragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nishnushim.FirstAddressActivity;
import com.example.nishnushim.R;
import com.example.nishnushim.helpUIClass.PinEntryEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class VerificationCodeFragment extends Fragment {

    ProgressBar progressBar;
    Button verifyBtn;

    private String mVerificationId;
    private String systemCode;
    private String phone;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    PhoneAuthCredential mCredential;

    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verification_code, container, false);

        progressBar = view.findViewById(R.id.progress_bar_verification_code_fragment);
        verifyBtn = view.findViewById(R.id.verify_code_btn_verification_code_fragment);

        mAuth = FirebaseAuth.getInstance();

        if (getArguments() != null) {

            phone = getArguments().getString("phone");

            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phone)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(getActivity())                 // Activity (for callback binding)
                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);





            final PinEntryEditText txtPinEntry = (PinEntryEditText) view.findViewById(R.id.pin_entry_edit_text_verification_code_fragment);





            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (txtPinEntry.getText() != null) {

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(mVerificationId, txtPinEntry.getText().toString());
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                        Toast.makeText(getContext(), txtPinEntry.getText().toString() + " WORKS", Toast.LENGTH_SHORT).show();


                    } else Toast.makeText(getContext(), "אנא הכנס מספר חוקי", Toast.LENGTH_SHORT).show();

                }
            });

        }

//            String code = getArguments().getString("code");
//            credential = getArguments().getParcelable("credential");
//
//            if (credential != null){
//
//                Toast.makeText(getContext(), "NOT NULL", Toast.LENGTH_SHORT).show();
//
//                verificationCodeEditText.setText(code);
//                verificationCodeEditText.clearFocus();
//
//
//

//
//
//            } else {
//                getFragmentManager().beginTransaction().replace(R.id.sign_in_frame_layout_sign_in_activity, new PhoneNumberFragment()).commit();
//            }
//
//        } else {
//            getFragmentManager().beginTransaction().replace(R.id.sign_in_frame_layout_sign_in_activity, new PhoneNumberFragment()).commit();
//        }


        return view;
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.


            String code = credential.getSmsCode();

//            if (code != null){
//
//                //TODO: WORKING ON CODE SMS
//
//                Toast.makeText(getContext(), "CODE NOT NULL " + code, Toast.LENGTH_SHORT).show();
//                Fragment fragment = new VerificationCodeFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("code", code);
//                bundle.putParcelable("credential", credential);
//                fragment.setArguments(bundle);
//                getFragmentManager().beginTransaction().replace(R.id.sign_in_frame_layout_sign_in_activity, new VerificationCodeFragment()).commit();
//
//            }

            systemCode = code;

        }


        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.


            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            // Show a message and update the UI
            // ...

            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();


            progressBar.setVisibility(View.GONE);
            verifyBtn.setEnabled(true);
        }


        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            mVerificationId = s;
            mResendToken = forceResendingToken;

        }


        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);

            progressBar.setVisibility(View.GONE);
            verifyBtn.setEnabled(true);
        }
    };




    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();

                            if (user != null){

                                Intent intent = new Intent(getContext(), FirstAddressActivity.class);
                                intent.putExtra("phone", phone);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }


                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() != null){

                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }


}