package com.example.nishnushim.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.nishnushim.AddDishActivity;
import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.menuChanges.Changes;
import com.example.nishnushim.helpclasses.menuChanges.RegularChange;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OneChoiceChangeAdapter extends BaseAdapter {

    Context context;
    List<Object> regularChangeList = new ArrayList<>();
    Changes createChange;
    LayoutInflater layoutInflater;
    int clickedPosition = 0;


    public OneChoiceChangeAdapter(Context context, List<Object> regularChangeList, Changes createChange) {
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

        convertView = layoutInflater.inflate(R.layout.one_choice_change_item, null);

        RadioButton radioButton = convertView.findViewById(R.id.first_change_radio_button_one_choice_changes_item);
        radioButton.setChecked(false);
        RegularChange regularChange;

        try {
            HashMap<String, Object> map = (HashMap<String, Object>) regularChangeList.get(position);
            regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
        }catch (Exception e){
            regularChange = (RegularChange) regularChangeList.get(position);
        }


        if (createChange.getChangesByTypesList().size() > 0){

            for (int i = 0; i < createChange.getChangesByTypesList().size(); i++) {

                if (regularChange.getChange().equals(((RegularChange) createChange.getChangesByTypesList().get(i)).getChange())){
                    radioButton.setChecked(true);
                }
            }

        }


        radioButton.setText(regularChange.getChange() + " - " + regularChange.getPrice() + " ₪ ");
        RegularChange finalRegularChange = regularChange;

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //TODO: ADD REGULAR CHANGE TO DISH
                if (isChecked){
                    createChange.getChangesByTypesList().add(finalRegularChange);
                }else createChange.getChangesByTypesList().remove(finalRegularChange);


                notifyDataSetChanged();

                if (context instanceof AddDishActivity){
                    ((AddDishActivity) context).updateSum();
                }

            }
        });


        return convertView;
    }
}
