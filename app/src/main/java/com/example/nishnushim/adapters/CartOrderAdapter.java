package com.example.nishnushim.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nishnushim.AddDishActivity;
import com.example.nishnushim.CartActivity;
import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Dish;
import com.example.nishnushim.helpclasses.Menu;
import com.example.nishnushim.helpclasses.MenuSingleton;
import com.example.nishnushim.helpclasses.menuChanges.Changes;
import com.example.nishnushim.helpclasses.menuChanges.PizzaChange;
import com.example.nishnushim.helpclasses.menuChanges.RegularChange;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class CartOrderAdapter extends BaseAdapter {

    //WORK ON ORDERS IN CART
    Context context;
    Classification cartClassification;
    LayoutInflater layoutInflater;


    public CartOrderAdapter(Context context, Classification cartClassification) {
        this.context = context;
        this.cartClassification = cartClassification;

        this.layoutInflater = LayoutInflater.from(this.context);
    }


    @Override
    public int getCount() {
        return cartClassification.getDishList().size();
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

        convertView = layoutInflater.inflate(R.layout.cart_order_item, null);

        ImageView dishImageView = convertView.findViewById(R.id.dish_image_view_menu_cart_order_item);
        TextView dishNameTextView = convertView.findViewById(R.id.dish_name_text_view_cart_order_item);
        TextView dishDetailsTextView = convertView.findViewById(R.id.dish_detail_text_view_cart_order_item);
        TextView dishCostTextView = convertView.findViewById(R.id.dish_cosh_text_view_cart_order_item);
        TextView subTitleChangeTextView = convertView.findViewById(R.id.sub_title_changes_text_view_cart_order_item);
        TextView dishChangesDetailsTextView = convertView.findViewById(R.id.cahnges_detail_text_view_cart_order_item);

        Button editDishBtn = convertView.findViewById(R.id.edit_dish_cart_order_item);
        Button addChangesToDishBtn = convertView.findViewById(R.id.add_to_dish_cart_order_item);
        Button removeDishBtn = convertView.findViewById(R.id.remove_dish_cart_order_item);

        StringBuilder stringBuilder = new StringBuilder();

        int costDishWithChanges = 0;

        //WORKING ON CHANGES TEXT
        if (cartClassification.getDishList().get(position).getChanges().size() > 0) {


            for (int i = 0; i < cartClassification.getDishList().get(position).getChanges().size(); i++) {

                Changes.ChangesTypesEnum changesTypesEnum = cartClassification.getDishList().get(position).getChanges().get(i).getChangesTypesEnum();

                for (int j = 0; j < cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().size(); j++) {


                    if (changesTypesEnum == Changes.ChangesTypesEnum.DISH_CHOICE || changesTypesEnum == Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE) {

                        Dish dish;

                        try {
                            HashMap<String, Object> map = (HashMap<String, Object>) cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().get(j);
                            dish = new Gson().fromJson(new Gson().toJson(map), Dish.class);
                        } catch (Exception e) {
                            dish = (Dish) cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().get(j);
                        }


                        stringBuilder.append(dish.getName()).append(" - ");

                        if (dish.getChanges().size() > 0) {
                            for (int k = 0; k < dish.getChanges().size(); k++) {

                                if (dish.getChanges().get(k).getChangesByTypesList().size() > 0) {

                                    if (cartClassification.getDishList().get(position).getChanges().get(i).getFreeSelection() == cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().size()) {

                                        //NO NEED TO ADD CHANGE COST
                                        continue;
                                    }

                                    //GET COST OF ALL CHANGES
//                                    sum += getCostSum(sum, j, dish.getChanges().get(k), dish.getChanges().get(k).getChangesTypesEnum());
                                    costDishWithChanges += dish.getPrice();
                                }
                            }

                        }


                    } else if (changesTypesEnum == Changes.ChangesTypesEnum.ONE_CHOICE) {

                        RegularChange regularChange;

                        try {
                            HashMap<String, Object> map = (HashMap<String, Object>) cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().get(j);
                            regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
                        } catch (Exception e) {
                            regularChange = (RegularChange) cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().get(j);
                        }


                        stringBuilder.append(regularChange.getChange());
                        costDishWithChanges += regularChange.getPrice();


                    } else if (changesTypesEnum == Changes.ChangesTypesEnum.MULTIPLE) {


                        RegularChange regularChange;

                        try {
                            HashMap<String, Object> map = (HashMap<String, Object>) cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().get(j);
                            regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
                        } catch (Exception e) {
                            regularChange = (RegularChange) cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().get(j);
                        }


                        stringBuilder.append(regularChange.getChange());
                        costDishWithChanges += regularChange.getPrice();


                    } else if (changesTypesEnum == Changes.ChangesTypesEnum.VOLUME) {


                        RegularChange regularChange;

                        try {
                            HashMap<String, Object> map = (HashMap<String, Object>) cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().get(j);
                            regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
                        } catch (Exception e) {
                            regularChange = (RegularChange) cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().get(j);
                        }

                        stringBuilder.append(regularChange.getChange());


                        stringBuilder.append("הוספת ").append(regularChange.getNumOfAdded());
                        costDishWithChanges += (regularChange.getPrice() * regularChange.getNumOfAdded());


                    } else if (changesTypesEnum == Changes.ChangesTypesEnum.PIZZA) {

                        PizzaChange pizzaChange;

                        try {
                            HashMap<String, Object> map = (HashMap<String, Object>) cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().get(j);
                            pizzaChange = new Gson().fromJson(new Gson().toJson(map), PizzaChange.class);
                        } catch (Exception e) {
                            pizzaChange = (PizzaChange) cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().get(j);
                        }


                        stringBuilder.append(pizzaChange.getName()).append(" ");

                        if (pizzaChange.isBothSides()) {

                            stringBuilder.append("כל הפיצה");

                        } else {

                            if (pizzaChange.isLeftSide())
                                stringBuilder.append("חצי שמאל");
                            else
                                stringBuilder.append("חצי ימין");
                        }


                        costDishWithChanges += pizzaChange.getCost();
                    }

                }

                stringBuilder.append(",").append(" ");

            }


            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(".");


//            for (int i = 0; i < cartClassification.getDishList().get(position).getChanges().size(); i++) {
//                stringBuilder.append(cartClassification.getDishList().get(position).getChanges().get(i).getChangeName());
//                stringBuilder.append(",").append(" ");
//            }
//            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
//            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
//            stringBuilder.append(".");
//
//            dishChangesDetailsTextView.setText(stringBuilder.toString());
//
//            subTitleChangeTextView.setVisibility(View.VISIBLE);
//            dishChangesDetailsTextView.setVisibility(View.VISIBLE);


        } else {

            subTitleChangeTextView.setVisibility(View.GONE);
            dishChangesDetailsTextView.setVisibility(View.GONE);

        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        dishNameTextView.setText(cartClassification.getDishList().get(position).getName());
        dishDetailsTextView.setText(cartClassification.getDishList().get(position).getDetails());

        costDishWithChanges += cartClassification.getDishList().get(position).getPrice();
        dishCostTextView.setText(String.valueOf(costDishWithChanges));


        editDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: EDIT SOMETHING ON THE DISH
                sendDataToIntent(position);

            }
        });


        addChangesToDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: ADD SOMETHING TO DISH
                Dish dish = (Dish) cartClassification.getDishList().get(position).clone();
                cartClassification.getDishList().add(dish);
                notifyDataSetChanged();

            }
        });



        removeDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: DELETE THE POSITION DISH
                cartClassification.getDishList().remove(position);
                notifyDataSetChanged();


            }
        });


        return convertView;
    }




    private void sendDataToIntent(int position) {

        Intent intent = new Intent(context, AddDishActivity.class);
        intent.putExtra("res_name", "NAME");

        //CLASSIFICATION CHOOSER ONLY ONE DISH - IS AT POSITION ZERO
        Dish dish = (Dish) cartClassification.getDishList().get(position);


        Menu menu = MenuSingleton.getInstance().getMenu();


        for (int i = 0; i < menu.getClassifications().size(); i++) {

            //find classification
            Classification classificationCheck = menu.getClassifications().get(i);

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


                if (classificationCheck.getDishList().get(j).getName().equals(dish.getName())) {
                    Log.i("CLASSIFICATION", classificationCheck.getClassificationName());
                    intent.putExtra("dish", classificationCheck.getDishList().get(j));
                    intent.putExtra("classification", classificationCheck);
                    intent.putExtra("position", position);
                    break;
                }
            }
        }

        intent.putExtra("dish_edit", dish);
        ((CartActivity) context).startActivityForResult(intent, 3);
    }

}
