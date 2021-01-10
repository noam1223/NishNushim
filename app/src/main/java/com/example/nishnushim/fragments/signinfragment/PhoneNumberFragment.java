package com.example.nishnushim.fragments.signinfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nishnushim.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class PhoneNumberFragment extends Fragment {


    EditText phoneEditText;
    Button signInBtn;

    ProgressBar progressBar;


    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_phone_number, container, false);

        phoneEditText = view.findViewById(R.id.phone_number_edit_text_sign_in_activity);
        signInBtn = view.findViewById(R.id.sign_in_btn_sign_in_activity);
        progressBar = view.findViewById(R.id.progress_bar_sign_in_activity);

        db = FirebaseFirestore.getInstance();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = phoneEditText.getText().toString();

                if (phoneNumber.length() == 10){


                    progressBar.setVisibility(View.VISIBLE);
                    signInBtn.setEnabled(false);

                    Fragment fragment = new VerificationCodeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", phoneNumber);
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.sign_in_frame_layout_sign_in_activity, fragment).commit();

                } else
                    Toast.makeText(getContext(), "בבקשה הכנס מספר טלפון חוקי", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }








}