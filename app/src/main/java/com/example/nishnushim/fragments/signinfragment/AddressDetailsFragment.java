package com.example.nishnushim.fragments.signinfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nishnushim.HomePageActivity;
import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.MyAddress;
import com.example.nishnushim.helpclasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class AddressDetailsFragment extends Fragment {


    EditText cityNameEditText, streetNameEditText, houseNumberEditText;
    Button signInAddressBtn;

    boolean setGps = false;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    User user;



    public AddressDetailsFragment() {


    }




    public AddressDetailsFragment(boolean setGps) {
        this.setGps = setGps;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address_details, container, false);

        cityNameEditText = view.findViewById(R.id.city_address_edit_text_address_details_fragment);
        streetNameEditText = view.findViewById(R.id.street_address_edit_text_address_details_fragment);
        houseNumberEditText = view.findViewById(R.id.number_street_address_edit_text_address_details_fragment);
        signInAddressBtn = view.findViewById(R.id.save_first_address_btn_address_manually_activity);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        if (setGps) {

            if (getArguments() != null) {

                user = (User) getArguments().getSerializable("user");

                cityNameEditText.setText(user.getAddresses().get(0).getCityName());
                streetNameEditText.setText(user.getAddresses().get(0).getStreetName());

                if (user.getAddresses().get(0).getHouseNumber() != null) {
                    houseNumberEditText.setText(user.getAddresses().get(0).getHouseNumber());
                }
            } else
                Toast.makeText(getContext(), "הכתובת לא נמצאה, עמכם הסליחה!", Toast.LENGTH_SHORT).show();

        }



        if (mAuth.getCurrentUser() != null) {


            signInAddressBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String city = cityNameEditText.getText().toString();
                    String street = streetNameEditText.getText().toString();
                    String houseNumber = houseNumberEditText.getText().toString();

                    User user = new User();

                    if (!city.isEmpty() && !street.isEmpty() && !houseNumber.isEmpty()) {


                        user.getAddresses().add(new MyAddress(city, street, houseNumber));

                        if (getActivity() != null) {

                            if (getActivity().getIntent().getStringExtra("phone") != null) {

                                user.setPhoneNumber(getActivity().getIntent().getStringExtra("phone"));
                            }
                        }



                        db.collection(getString(R.string.USERS_DB)).document(mAuth.getCurrentUser().getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    Intent intent = new Intent(getContext(), HomePageActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("user", user);
                                    startActivity(intent);

                                } else {

                                    if (task.isCanceled())
                                        Toast.makeText(getContext(), "ישנה בעיית התחברות עם השרת", Toast.LENGTH_SHORT).show();


                                    if (task.getException() != null) {
                                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                        });


                    }

                }
            });
        }

        return view;
    }
}