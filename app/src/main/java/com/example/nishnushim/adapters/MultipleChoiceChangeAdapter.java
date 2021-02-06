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

        Log.i("CHANGE_ADAPTER", "MULTIPLE_CHOICE_ADAPTER");

        HashMap<String, Object> map = (HashMap<String, Object>) regularChangeList.get(position);
        RegularChange regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);


        CheckBox checkBox = convertView.findViewById(R.id.first_change_check_box_multiple_choice_changes_item);
        checkBox.setText(regularChange.getChange() + " - " + regularChange.getPrice() + " ₪");


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    //TODO: ADD TO DISH CHANGES ORDER
                    createChange.getChangesByTypesList().add(regularChange);
                }else {
                    //TODO: REMOVE TO DISH CHANGES ORDER
                    createChange.getChangesByTypesList().remove(regularChange);
                }


            }
        });



        return convertView;
    }
}
