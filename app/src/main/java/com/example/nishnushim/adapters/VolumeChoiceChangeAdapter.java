package com.example.nishnushim.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nishnushim.AddDishActivity;
import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.menuChanges.Changes;
import com.example.nishnushim.helpclasses.menuChanges.RegularChange;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VolumeChoiceChangeAdapter extends BaseAdapter {

    Context context;
    List<Object> volumeChangeList = new ArrayList<>();
    Changes createChange;
    LayoutInflater layoutInflater;
    int volumeAdded = 0;



    public VolumeChoiceChangeAdapter(Context context, List<Object> volumeChangeList, Changes createChange) {
        this.context = context;
        this.volumeChangeList = volumeChangeList;
        this.createChange = createChange;
        this.layoutInflater = LayoutInflater.from(this.context);


    }



    @Override
    public int getCount() {
        return volumeChangeList.size();
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

        convertView = layoutInflater.inflate(R.layout.volume_change_item, null);


        Log.i("CHANGE_ADAPTER", "VOLUME_CHOICE_ADAPTER");


        TextView changeNameTextView = convertView.findViewById(R.id.volume_name_text_view_volume_change_item);
        TextView costToAddTextView = convertView.findViewById(R.id.cost_of_sauce_text_view_volume_change_view);
        TextView volumeAddedNumTextView = convertView.findViewById(R.id.number_of_supplies_text_view_volume_change_item);
        ImageButton plusImgBtn = convertView.findViewById(R.id.increment_supplies_img_btn_volume_change_item);
        ImageButton minusImgBtn = convertView.findViewById(R.id.decrement_supplies_img_btn_volume_change_item);

        RegularChange regularChange;

        try {
            HashMap<String, Object> map = (HashMap<String, Object>) volumeChangeList.get(position);
            regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
        }catch (Exception e){
            regularChange = (RegularChange) volumeChangeList.get(position);
        }


        String[] split = regularChange.getChange().split("-");
        if (split.length == 2){

            changeNameTextView.setText(split[0]);
            costToAddTextView.setText(split[1] + " - " + regularChange.getPrice() + " ₪");

        }else {
            changeNameTextView.setText(regularChange.getChange());
            costToAddTextView.setText("ללא עלות");
        }

        int existPosition = -1;

        for (int i = 0; i < createChange.getChangesByTypesList().size(); i++) {



            if (((RegularChange)createChange.getChangesByTypesList().get(i)).getChange().equals(regularChange.getChange())){
                existPosition = i;
                break;
            }
        }


        if (existPosition > -1){
            volumeAddedNumTextView.setText(((RegularChange)createChange.getChangesByTypesList().get(existPosition)).getChange());
        }else volumeAddedNumTextView.setText("0");


//        createChange.getChangesByTypesList().add(regularChange);


        RegularChange finalRegularChange = regularChange;

        plusImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                HashMap<String, Object> map = (HashMap<String, Object>) createChange.getChangesByTypesList().get(position);
//                RegularChange regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);


                int existPosition = -1;

                for (int i = 0; i < createChange.getChangesByTypesList().size(); i++) {



                    if (((RegularChange)createChange.getChangesByTypesList().get(i)).getChange().equals(finalRegularChange.getChange())){
                        existPosition = i;
                        break;
                    }
                }


                if (existPosition > -1){
                    RegularChange regularChange1 = (RegularChange)createChange.getChangesByTypesList().get(existPosition);
                    regularChange1.setNumOfAdded(regularChange1.getNumOfAdded() + 1);
//                        volumeAddedNumTextView.setText(String.valueOf(finalRegularChange.getNumOfAdded()));
                    createChange.getChangesByTypesList().set(position, finalRegularChange);
                }else{
                    finalRegularChange.setNumOfAdded(1);
                    createChange.getChangesByTypesList().add(finalRegularChange);
                }

                notifyDataSetChanged();
                if (context instanceof AddDishActivity){
                    ((AddDishActivity) context).updateSum();
                }

                //TODO: UPDATE CART SUM

            }
        });




        minusImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                HashMap<String, Object> map = (HashMap<String, Object>) createChange.getChangesByTypesList().get(position);
//                RegularChange regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);

                if (finalRegularChange.getNumOfAdded() == 0){
                    return;
                }


                int existPosition = -1;

                for (int i = 0; i < createChange.getChangesByTypesList().size(); i++) {

                    if (((RegularChange)createChange.getChangesByTypesList().get(i)).getChange().equals(finalRegularChange.getChange())){
                        existPosition = i;
                        break;
                    }

                }



                if (existPosition > -1){
                    RegularChange regularChange1 = (RegularChange)createChange.getChangesByTypesList().get(existPosition);

                    regularChange1.setNumOfAdded(regularChange1.getNumOfAdded() - 1);

                    if (regularChange1.getNumOfAdded() == 0){
                        createChange.getChangesByTypesList().remove(existPosition);
                    }else createChange.getChangesByTypesList().set(position, regularChange1);

//                        volumeAddedNumTextView.setText(String.valueOf(finalRegularChange.getNumOfAdded()));
                }

                if (context instanceof AddDishActivity){
                    ((AddDishActivity) context).updateSum();
                }

            }
        });


        return convertView;
    }
}
