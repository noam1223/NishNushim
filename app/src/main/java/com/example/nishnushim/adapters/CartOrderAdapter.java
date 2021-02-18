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



                        if (cartClassification.getDishList().get(position).getChanges().get(i).getFreeSelection() > cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().size()){
                            costDishWithChanges += dish.getPrice();
                        }else if (dish.getChanges().size() > 0) {
                            for (int k = 0; k < dish.getChanges().size(); k++) {

                                if (dish.getChanges().get(k).getChangesByTypesList().size() > 0) {

                                    if (dish.getChanges().get(k).getFreeSelection() < dish.getChanges().get(k).getChangesByTypesList().size()) {

                                        costDishWithChanges += getCostSum(costDishWithChanges, j, dish.getChanges().get(k), dish.getChanges().get(k).getChangesTypesEnum());

                                    }
                                }
                            }
                        }


                        stringBuilder.append(dish.getName()).append(" - ");


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

                        if (regularChange.getNumOfAdded() > 0) {
                            costDishWithChanges = (regularChange.getPrice() * regularChange.getNumOfAdded()) + costDishWithChanges;
                        }

                        stringBuilder.append(regularChange.getChange());
                        stringBuilder.append("הוספת ").append(regularChange.getNumOfAdded());


                    } else if (changesTypesEnum == Changes.ChangesTypesEnum.PIZZA) {

                        PizzaChange pizzaChange;

                        try {
                            HashMap<String, Object> map = (HashMap<String, Object>) cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().get(j);
                            pizzaChange = new Gson().fromJson(new Gson().toJson(map), PizzaChange.class);
                        } catch (Exception e) {
                            pizzaChange = (PizzaChange) cartClassification.getDishList().get(position).getChanges().get(i).getChangesByTypesList().get(j);
                        }



                        if (pizzaChange.isBothSides()) {

                            stringBuilder.append("כל הפיצה");

                            costDishWithChanges += pizzaChange.getCost();
                            stringBuilder.append(pizzaChange.getName()).append(" ");

                        } else {

                            if (pizzaChange.isLeftSide())
                                stringBuilder.append("חצי שמאל");
                            else
                                stringBuilder.append("חצי ימין");

                            costDishWithChanges += pizzaChange.getCost();
                            stringBuilder.append(pizzaChange.getName()).append(" ");
                        }
                    }
                }

                stringBuilder.append(",").append(" ");

            }


            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(".");


        }



        if (!stringBuilder.toString().isEmpty()){
            subTitleChangeTextView.setVisibility(View.GONE);
            dishChangesDetailsTextView.setVisibility(View.GONE);
        }else {
            subTitleChangeTextView.setVisibility(View.VISIBLE);
            dishChangesDetailsTextView.setVisibility(View.VISIBLE);
            dishChangesDetailsTextView.setText(stringBuilder);
        }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        dishNameTextView.setText(cartClassification.getDishList().get(position).getName());
        dishDetailsTextView.setText(cartClassification.getDishList().get(position).getDetails());

        costDishWithChanges += cartClassification.getDishList().get(position).getPrice();
        dishCostTextView.setText(String.valueOf(costDishWithChanges));



        editDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendDataToIntent(position);

            }
        });



        addChangesToDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dish dish = (Dish) cartClassification.getDishList().get(position).clone();
                cartClassification.getDishList().add(dish);
                notifyDataSetChanged();

            }
        });



        removeDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            if (classificationCheck.getClassificationName().equals(cartClassification.getClassificationName())) {

                for (int j = 0; j < classificationCheck.getDishList().size(); j++) {

                    if (classificationCheck.getDishList().get(j).getName().equals(dish.getName())) {
                        intent.putExtra("dish", classificationCheck.getDishList().get(j));
                        intent.putExtra("classification", classificationCheck);
                        break;
                    }
                }
            }
        }

        intent.putExtra("position", position);
        intent.putExtra("dish_edit", dish);
        ((CartActivity) context).startActivityForResult(intent, 3);
    }

    private int getCostSum(int sum, int j, Changes changes, Changes.ChangesTypesEnum changesTypesEnum) {


        if (changesTypesEnum == Changes.ChangesTypesEnum.DISH_CHOICE || changesTypesEnum == Changes.ChangesTypesEnum.CLASSIFICATION_CHOICE) {

            //WORK ON UPDATE SUM OF DISH CHANGE CHOICE
            Dish dish;

            try {
                HashMap<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(j);
                dish = new Gson().fromJson(new Gson().toJson(map), Dish.class);
            } catch (Exception e) {
                dish = (Dish) changes.getChangesByTypesList().get(j);
            }


            if (changes.getFreeSelection() < changes.getChangesByTypesList().size()){
                sum += dish.getPrice();
            }


            if (dish.getChanges().size() > 0) {
                for (int k = 0; k < dish.getChanges().size(); k++) {

                    if (dish.getChanges().get(k).getChangesByTypesList().size() > 0) {

                        if (dish.getChanges().get(k).getFreeSelection() < dish.getChanges().get(k).getChangesByTypesList().size()) {

                            sum += getCostSum(sum, j, dish.getChanges().get(k), dish.getChanges().get(k).getChangesTypesEnum());
                        }

                    }
                }

            }


        } else if (changesTypesEnum == Changes.ChangesTypesEnum.ONE_CHOICE) {

            RegularChange regularChange;

            try {
                HashMap<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(j);
                regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
            } catch (Exception e) {
                regularChange = (RegularChange) changes.getChangesByTypesList().get(j);
            }

            sum += regularChange.getPrice();


        } else if (changesTypesEnum == Changes.ChangesTypesEnum.MULTIPLE) {


            RegularChange regularChange;

            try {
                HashMap<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(j);
                regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
            } catch (Exception e) {
                regularChange = (RegularChange) changes.getChangesByTypesList().get(j);
            }


            sum += regularChange.getPrice();


        } else if (changesTypesEnum == Changes.ChangesTypesEnum.VOLUME) {


            RegularChange regularChange;

            try {
                HashMap<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(j);
                regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
            } catch (Exception e) {
                regularChange = (RegularChange) changes.getChangesByTypesList().get(j);
            }

            sum += regularChange.getPrice();


        } else if (changesTypesEnum == Changes.ChangesTypesEnum.PIZZA) {

            PizzaChange pizzaChange;

            try {
                HashMap<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(j);
                pizzaChange = new Gson().fromJson(new Gson().toJson(map), PizzaChange.class);
            } catch (Exception e) {
                pizzaChange = (PizzaChange) changes.getChangesByTypesList().get(j);
            }

            if (pizzaChange.isBothSides() || pizzaChange.isLeftSide() || pizzaChange.isRightSide()) {
                sum += pizzaChange.getCost();
            }

        }
        return sum;
    }


}
