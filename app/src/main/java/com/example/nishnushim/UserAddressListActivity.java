package com.example.nishnushim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.nishnushim.adapters.UserAddressAdapter;
import com.example.nishnushim.helpclasses.MyAddress;
import com.example.nishnushim.helpclasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAddressListActivity extends AppCompatActivity {

    ImageButton backImgBtn;
    List<MyAddress> myAddressList = new ArrayList<>();
    ListView userAddressListView;


    MyAddress chosenAddress;
    int positionAddressChosen = -1, myAddressSizeFirst;

    FirebaseFirestore db;
    FirebaseAuth auth;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        if (intent != null){

            if (intent.getSerializableExtra("user") != null) {
                user = (User) intent.getSerializableExtra("user");

                myAddressList.addAll(user.getAddresses());
                myAddressSizeFirst = myAddressList.size();

                for (int i = 0; i < myAddressList.size(); i++) {

                    if (myAddressList.get(i).isChosen()){
                        chosenAddress = myAddressList.get(i);
                        positionAddressChosen = i;
                        break;
                    }

                }

            }
        }



        backImgBtn = findViewById(R.id.back_img_btn_address_list_activity);
        userAddressListView = findViewById(R.id.user_address_list_view_address_list_activity);



        backImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        userAddressListView.setAdapter(new UserAddressAdapter(this, myAddressList, positionAddressChosen));
        userAddressListView.setDividerHeight(0);



    }


    @Override
    protected void onDestroy() {

        for (int i = 0; i < myAddressList.size(); i++) {

            if (myAddressList.get(i).isChosen()){

                if (positionAddressChosen != i){
                    db.collection(getString(R.string.USERS_DB)).document("LCl46tZA2wd53H8If1mWTb2VjGw2").set(user);
                }

            }

        }


        if (myAddressSizeFirst != myAddressList.size()){
            db.collection(getString(R.string.USERS_DB)).document(auth.getCurrentUser().getUid()).set(user);
        }


        super.onDestroy();
    }
}