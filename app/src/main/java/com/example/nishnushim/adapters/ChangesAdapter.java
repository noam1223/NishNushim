package com.example.nishnushim.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Dish;
import com.example.nishnushim.helpclasses.menuChanges.Changes;
import com.example.nishnushim.helpclasses.menuChanges.RegularChange;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class ChangesAdapter extends BaseAdapter {


    Context context;
    List<Changes> changesList;
    LayoutInflater layoutInflater;
    Dish createDish;


    public ChangesAdapter(Context context, List<Changes> changesList, Dish createDish) {
        this.context = context;
        this.changesList = changesList;
        this.createDish = createDish;
        this.layoutInflater = LayoutInflater.from(this.context);
    }


    @Override
    public int getCount() {
        return changesList.size();
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

        convertView = layoutInflater.inflate(R.layout.changes_item, null);


        TextView changeNameTextView = convertView.findViewById(R.id.changes_title_text_view_changes_item);
        ListView detailChangesListView = convertView.findViewById(R.id.changes_item_list_view_changes_item);

        changeNameTextView.setText(changesList.get(position).getChangeName());


        if (createDish.getChanges().size() < changesList.size()){
            Log.i("CHANGE_ADAPTER", "CREATE_BIGGER_THAN_CHANGE_LIST");
            createDish.getChanges().add(new Changes());
            createDish.getChanges().get(position).setChangeName(changesList.get(position).getChangeName());
            createDish.getChanges().get(position).setFreeSelection(changesList.get(position).getFreeSelection());
        }


        if (changesList.get(position).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE){
            detailChangesListView.setAdapter(new OneChoiceChangeAdapter(context, changesList.get(position).getChangesByTypesList(), createDish.getChanges().get(position)));
        }else if (changesList.get(position).getChangesTypesEnum() == Changes.ChangesTypesEnum.MULTIPLE){
            detailChangesListView.setAdapter(new MultipleChoiceChangeAdapter(context, changesList.get(position).getChangesByTypesList(), createDish.getChanges().get(position)));
        }else if (changesList.get(position).getChangesTypesEnum() == Changes.ChangesTypesEnum.DISH_CHOICE
                || changesList.get(position).getChangesTypesEnum() == Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE){
            detailChangesListView.setAdapter(new DishChoiceAdapter(context, changesList.get(position).getChangesByTypesList(), changesList.get(position).getChangesTypesEnum(), createDish.getChanges().get(position)));
        }




        return convertView;
    }
}
