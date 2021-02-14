package com.example.nishnushim.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nishnushim.AddDishActivity;
import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Dish;
import com.example.nishnushim.helpclasses.Menu;
import com.example.nishnushim.helpclasses.MenuSingleton;
import com.example.nishnushim.helpclasses.menuChanges.Changes;
import com.example.nishnushim.helpclasses.menuChanges.RegularChange;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChangesAdapter extends BaseAdapter {


    Context context;
    List<Changes> changesList, classificationOfDishChangesList;
    LayoutInflater layoutInflater;
    Dish createDish;

    TextView changeNameTextView;
    ListView detailChangesListView;
    Button changesForClassificationDishBtn;


    final Menu menu = MenuSingleton.getInstance().getMenu();


    public ChangesAdapter(Context context, List<Changes> changesList,List<Changes> classificationOfDishChangesList ,Dish createDish) {
        this.context = context;
        this.changesList = changesList;
        this.createDish = createDish;
        this.classificationOfDishChangesList = classificationOfDishChangesList;
        this.layoutInflater = LayoutInflater.from(this.context);

    }


    @Override
    public int getCount() {
        return changesList.size() + classificationOfDishChangesList.size();
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


        changeNameTextView = convertView.findViewById(R.id.changes_title_text_view_changes_item);
        detailChangesListView = convertView.findViewById(R.id.changes_item_list_view_changes_item);
        changesForClassificationDishBtn = convertView.findViewById(R.id.add_change_classification_btn_changes_item);


        if (position < changesList.size()){
            updateUIAdapter(position, changesList, position);

            if (changesList.get(position).getChangesTypesEnum() != Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE)
                changesForClassificationDishBtn.setVisibility(View.GONE);
            else ((AddDishActivity)context).addDishBtn(changesList.get(position).getChangeName(), position);

        }else {
            updateUIAdapter(position - changesList.size(), classificationOfDishChangesList, position);

            if (classificationOfDishChangesList.get(position - changesList.size()).getChangesTypesEnum() != Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE)
                changesForClassificationDishBtn.setVisibility(View.GONE);
            else ((AddDishActivity)context).addDishBtn(changesList.get(position - changesList.size()).getChangeName(), position);
        }




        //TODO: CLASSIFICATION CHANGE ITEM

        changesForClassificationDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (position < changesList.size()){
                    sendDataToIntent(position,changesList ,position);
                }else {
                    sendDataToIntent(position - changesList.size(),classificationOfDishChangesList ,position);
                }


            }
        });



        return convertView;
    }





    private void sendDataToIntent(int position, List<Changes> changesList, int listPosition) {

        Intent intent = new Intent(context, AddDishActivity.class);
        intent.putExtra("res_name", ((AddDishActivity)context).getRestaurantNameString());

        //CLASSIFICATION CHOOSER ONLY ONE DISH - IS AT POSITION ZERO
        Dish dish = (Dish) createDish.getChanges().get(listPosition).getChangesByTypesList().get(0);

        for (int i = 0; i < menu.getClassifications().size(); i++) {

            if (menu.getClassifications().get(i).getClassificationName().equals(changesList.get(position).getChangeName())){

                Classification classificationCheck = menu.getClassifications().get(i);
                intent.putExtra("classification", classificationCheck);

                Log.i("CLassification", menu.getClassifications().get(i).getClassificationName());
                Log.i("CLassification", menu.getClassifications().size() + "");


                for (int j = 0; j < classificationCheck.getDishList().size(); j++) {

//                    if (position < changesList.size()){
//                        if (classificationCheck.getDishList().get(j).getName().equals(dish.getName())){
//                            intent.putExtra("dish", dish);
//                            break;
//                        }
//                    }else {
//                        if (classificationCheck.getDishList().get(j).getName().equals(dish.getName())){
//                            intent.putExtra("dish", dish);
//                            break;
//                        }
//                    }



                    if (classificationCheck.getDishList().get(j).getName().equals(dish.getName())){
                        intent.putExtra("dish", classificationCheck.getDishList().get(j));
                    }
                }

                intent.putExtra("position", listPosition);

            }
        }

        ((AddDishActivity) context).startActivityForResult(intent, 2);
    }





    private void updateUIAdapter(int indexOfChange, List<Changes> changesList , int listPosition) {

        changeNameTextView.setText(changesList.get(indexOfChange).getChangeName());


        if (createDish.getChanges().size() < this.changesList.size() + this.classificationOfDishChangesList.size()){
            createDish.getChanges().add(new Changes());
            createDish.getChanges().get(indexOfChange).setChangeName(changesList.get(indexOfChange).getChangeName());
            createDish.getChanges().get(indexOfChange).setFreeSelection(changesList.get(indexOfChange).getFreeSelection());
            createDish.getChanges().get(indexOfChange).setChangesTypesEnum(changesList.get(indexOfChange).getChangesTypesEnum());
        }


        if (changesList.get(indexOfChange).getChangesTypesEnum() == Changes.ChangesTypesEnum.ONE_CHOICE){
            detailChangesListView.setAdapter(new OneChoiceChangeAdapter(context, changesList.get(indexOfChange).getChangesByTypesList(), createDish.getChanges().get(indexOfChange)));
        }else if (changesList.get(indexOfChange).getChangesTypesEnum() == Changes.ChangesTypesEnum.MULTIPLE){
            detailChangesListView.setAdapter(new MultipleChoiceChangeAdapter(context, changesList.get(indexOfChange).getChangesByTypesList(), createDish.getChanges().get(indexOfChange)));
        }else if (changesList.get(indexOfChange).getChangesTypesEnum() == Changes.ChangesTypesEnum.DISH_CHOICE){


            detailChangesListView.setAdapter(new DishChoiceAdapter(context, changesList.get(indexOfChange).getChangesByTypesList(), changesList.get(indexOfChange).getChangesTypesEnum(), createDish.getChanges().get(indexOfChange), changesList.get(indexOfChange).getFreeSelection()));


        }else if (changesList.get(indexOfChange).getChangesTypesEnum() == Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE) {

            List<Object> objectList = new ArrayList<>();

            for (int i = 0; i < menu.getClassifications().size(); i++) {
                if (menu.getClassifications().get(i).getClassificationName().equals(changesList.get(indexOfChange).getChangeName())){
                    objectList.addAll(menu.getClassifications().get(i).getDishList());
                }
            }


            detailChangesListView.setAdapter(new DishChoiceAdapter(context, objectList, changesList.get(indexOfChange).getChangesTypesEnum(), createDish.getChanges().get(indexOfChange), changesList.get(indexOfChange).getFreeSelection()));


        }else if (changesList.get(indexOfChange).getChangesTypesEnum() == Changes.ChangesTypesEnum.VOLUME){
            detailChangesListView.setAdapter(new VolumeChoiceChangeAdapter(context, changesList.get(indexOfChange).getChangesByTypesList(), createDish.getChanges().get(indexOfChange)));
        }else if (changesList.get(indexOfChange).getChangesTypesEnum() == Changes.ChangesTypesEnum.PIZZA){
            //TODO: PRODUCE THE PIZZA CHANGE IMAGE

            ((AddDishActivity)context).updatePizzaUI(indexOfChange, listPosition);

        }
    }


}
