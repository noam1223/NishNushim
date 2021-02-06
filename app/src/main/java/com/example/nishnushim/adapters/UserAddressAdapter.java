package com.example.nishnushim.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.MyAddress;

import java.util.List;

public class UserAddressAdapter extends BaseAdapter {

    Context context;
    List<MyAddress> myAddressList;
    LayoutInflater layoutInflater;

    int positionChosen;



    public UserAddressAdapter(Context context, List<MyAddress> myAddressList, int positionChosen) {
        this.context = context;
        this.myAddressList = myAddressList;
        this.positionChosen = positionChosen;

        this.layoutInflater = LayoutInflater.from(this.context);
    }



    @Override
    public int getCount() {
        return myAddressList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.user_address_item, null);



        ConstraintLayout mainConstraintLayoutItem = convertView.findViewById(R.id.constrain_layout_main_user_address_item);
        mainConstraintLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (positionChosen != position) {
                    positionChosen = position;
                    notifyDataSetChanged();
                }

            }
        });



        TextView addressDetailTextView = convertView.findViewById(R.id.address_detail_text_view_user_address_item);
        addressDetailTextView.setText(myAddressList.get(position).fullMyAddress());

        ImageView checkAddressImageView = convertView.findViewById(R.id.check_image_view_user_address_item);


        if (positionChosen == position) {
            if (!myAddressList.get(position).isChosen()) {
                myAddressList.get(position).setChosen(true);
            }
            checkAddressImageView.setVisibility(View.VISIBLE);

        } else checkAddressImageView.setVisibility(View.INVISIBLE);



        ImageView trashCanAddress = convertView.findViewById(R.id.can_trash_image_view_user_address_item);

        trashCanAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: OPEN POP UP WINDOW TO INSURE DELETE ADDRESS

                View popUpView = layoutInflater.inflate(R.layout.delete_pop_up_window, null);
                Dialog deleteDialog = new Dialog(context);
                deleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                deleteDialog.setContentView(popUpView);


                TextView addressDetailTextView, deleteAddressTextView, cancelDeleteTextView;
                ImageButton closeWindowImgBtn;

                addressDetailTextView = popUpView.findViewById(R.id.address_detail_text_view_delete_pop_up_window);
                addressDetailTextView.setText(myAddressList.get(position).fullMyAddress());

                deleteAddressTextView = popUpView.findViewById(R.id.delete_text_view_delete_pop_up_window);
                cancelDeleteTextView = popUpView.findViewById(R.id.cancel_text_view_delete_pop_up_window);
                closeWindowImgBtn = popUpView.findViewById(R.id.close_window_image_btn_delete_pop_up_window);

                deleteAddressTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        myAddressList.remove(position);
                        notifyDataSetChanged();
                        deleteDialog.dismiss();

                    }
                });


                cancelDeleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteDialog.dismiss();

                    }
                });


                closeWindowImgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteDialog.dismiss();

                    }
                });




                deleteDialog.create();
                deleteDialog.show();
            }
        });


        return convertView;
    }


}
