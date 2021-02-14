package com.example.nishnushim.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.menuChanges.Changes;
import com.example.nishnushim.helpclasses.menuChanges.RegularChange;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultipleChoiceChangeAdapter extends BaseAdapter {


    Context context;
    List<Object> regularChangeList = new ArrayList<>();
    LayoutInflater layoutInflater;
    Changes createChange;
    int clickedPosition = 0;



    public MultipleChoiceChangeAdapter(Context context, List<Object> regularChangeList, Changes createChange) {
        this.context = context;
        this.regularChangeList = regularChangeList;
        this.createChange = createChange;
        this.layoutInflater = LayoutInflater.from(this.context);
    }



    @Override
    public int getCount() {
        return regularChangeList.size();
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

        convertView = layoutInflater.inflate(R.layout.multiple_choice_change_item, null);
        RegularChange regularChange;


        try {
            HashMap<String, Object> map = (HashMap<String, Object>) regularChangeList.get(position);
            regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
        }catch (Exception e){
            regularChange = (RegularChange) regularChangeList.get(position);
        }



        CheckBox checkBox = convertView.findViewById(R.id.first_change_check_box_multiple_choice_changes_item);
        checkBox.setText(regularChange.getChange() + " - " + regularChange.getPrice() + " ₪");



        if (createChange.getChangesByTypesList().size() > 0){

            for (int i = 0; i < createChange.getChangesByTypesList().size(); i++) {

                if (regularChange.getChange().equals(((RegularChange) createChange.getChangesByTypesList().get(i)).getChange())){
                    checkBox.setChecked(true);

                    //WHY DID I ADD IT TO THE CREATE CHANGE?
//                    createChange.getChangesByTypesList().add(regularChange);
                }
            }

        }


        RegularChange finalRegularChange = regularChange;

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    //TODO: ADD TO DISH CHANGES ORDER
                    createChange.getChangesByTypesList().add(finalRegularChange);
                }else {
                    //TODO: REMOVE TO DISH CHANGES ORDER
                    checkBox.setChecked(false);
                    createChange.getChangesByTypesList().remove(finalRegularChange);
                }

                Log.i("CHANGE_ADAPTER", "MULTIPLE_CHOICE_ADAPTER ---- NUMBER OF CREATE CHANGE " + createChange.getChangesByTypesList().size());

            }
        });



        return convertView;
    }
}
