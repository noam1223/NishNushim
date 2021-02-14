package com.example.nishnushim.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.nishnushim.AddDishActivity;
import com.example.nishnushim.R;
import com.example.nishnushim.helpUIClass.ChangePizzaHelper;
import com.example.nishnushim.helpclasses.DishChanges;
import com.example.nishnushim.helpclasses.menuChanges.Changes;
import com.example.nishnushim.helpclasses.menuChanges.PizzaChange;
import com.example.nishnushim.helpclasses.menuChanges.RegularChange;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class PizzaChangeItem extends BaseAdapter {

    Context context;
    Changes changes, createChange;
    ChangePizzaHelper changePizzaHelper;
    LayoutInflater layoutInflater;
    int listPosition;


    public PizzaChangeItem(Context context, Changes changes, Changes createChange, int listPosition) {
        this.context = context;
        this.changes = changes;
        this.changePizzaHelper = new ChangePizzaHelper();
        this.createChange = createChange;
        this.listPosition = listPosition;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return changes.getChangesByTypesList().size();
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

        convertView = layoutInflater.inflate(R.layout.pizza_change_item, null);

        ImageView changeImageView = convertView.findViewById(R.id.pizza_change_image_view_pizza_change_item);
        TextView changeNameTextView = convertView.findViewById(R.id.pizza_change_name_text_view_pizza_dish_change_item);

        LinearLayout addPlusBtnAreaLinearLayout = convertView.findViewById(R.id.add_change_pizza_to_pizza_linear_layout_area_pizza_change_item);
        LinearLayout addToWhichSideLinearLayout = convertView.findViewById(R.id.add_change_side_to_pizza_area_linear_layout_pizza_change_item);

        ImageButton addPlusImgBtn = convertView.findViewById(R.id.add_change_pizza_to_pizza_image_btn_pizza_change_item);

        ImageButton fullPizzaImgBtn, leftPizzaImgBtn, rightPizzaSideImgBtn;

        fullPizzaImgBtn = convertView.findViewById(R.id.add_to_all_pizza_img_btn_pizza_change_item);
        leftPizzaImgBtn = convertView.findViewById(R.id.add_to_left_half_pizza_img_btn_pizza_change_item);
        rightPizzaSideImgBtn = convertView.findViewById(R.id.add_to_right_half_pizza_img_btn_pizza_change_item);


        PizzaChange pizzaChange;


        try {
            HashMap<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(position);
            pizzaChange = new Gson().fromJson(new Gson().toJson(map), PizzaChange.class);
        } catch (Exception e) {
            pizzaChange = (PizzaChange) changes.getChangesByTypesList().get(position);
        }


        for (int i = 0; i < changePizzaHelper.getChangePizzaTypes().size(); i++) {

            if (changePizzaHelper.getChangePizzaTypes().get(i).getId() == pizzaChange.getId()) {
                changeImageView.setImageDrawable(ContextCompat.getDrawable(context, changePizzaHelper.getChangePizzaTypes().get(i).getSrcImg()));
                break;
            }
        }


        for (int i = 0; i < createChange.getChangesByTypesList().size(); i++) {

            PizzaChange pizzaChangeDish = ((PizzaChange) createChange.getChangesByTypesList().get(i));

            if (pizzaChange.getId() == pizzaChangeDish.getId()) {

                addPlusBtnAreaLinearLayout.setVisibility(View.GONE);
                addToWhichSideLinearLayout.setVisibility(View.VISIBLE);

                if (pizzaChangeDish.isBothSides()) {

                    fullPizzaImgBtn.setPressed(true);
                    fullPizzaImgBtn.setSelected(true);
                    leftPizzaImgBtn.setPressed(false);
                    leftPizzaImgBtn.setSelected(false);
                    rightPizzaSideImgBtn.setPressed(false);
                    rightPizzaSideImgBtn.setSelected(false);

                } else {

                    fullPizzaImgBtn.setPressed(false);
                    fullPizzaImgBtn.setSelected(false);

                    if (pizzaChangeDish.isLeftSide()) {
                        leftPizzaImgBtn.setPressed(true);
                        leftPizzaImgBtn.setSelected(true);
                    }

                    if (pizzaChangeDish.isRightSide()) {
                        rightPizzaSideImgBtn.setPressed(true);
                        rightPizzaSideImgBtn.setSelected(true);
                    }

                }

            }

        }


        PizzaChange finalPizzaChange = pizzaChange;
        addPlusImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPlusBtnAreaLinearLayout.setVisibility(View.GONE);
                addToWhichSideLinearLayout.setVisibility(View.VISIBLE);

                PizzaChange pizzaChange1 = new PizzaChange();
                pizzaChange1.setCost(finalPizzaChange.getCost());
                pizzaChange1.setId(finalPizzaChange.getId());
                pizzaChange1.setName(finalPizzaChange.getName());
                pizzaChange1.setRightSide(false);
                pizzaChange1.setLeftSide(false);
                pizzaChange1.setBothSides(false);

                createChange.getChangesByTypesList().add(pizzaChange1);


            }
        });







        fullPizzaImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                PizzaChange pizzaChangeDish = (PizzaChange) changes.getChangesByTypesList().get(position);
                int itemPosition = -1;
                int imgSrc = -1;

                for (int i = 0; i < createChange.getChangesByTypesList().size(); i++) {

                    if (finalPizzaChange.getId() == ((PizzaChange)createChange.getChangesByTypesList().get(i)).getId()){
                        itemPosition = i;
                        break;
                    }

                }


                for (int i = 0; i < changePizzaHelper.getChangePizzaTypes().size(); i++) {

                    if (finalPizzaChange.getId() == changePizzaHelper.getChangePizzaTypes().get(i).getId()){
                        imgSrc = changePizzaHelper.getChangePizzaTypes().get(i).getSrcImg();
                    }

                }


                PizzaChange pizzaChange1 = (PizzaChange)createChange.getChangesByTypesList().get(itemPosition);


                if (pizzaChange1.isBothSides()) {
                    pizzaChange1.setBothSides(false);
                    ((AddDishActivity)context).updateSidesOfPizzas(position);

                } else {

                    pizzaChange1.setBothSides(true);
                    pizzaChange1.setLeftSide(false);
                    pizzaChange1.setRightSide(false);
                    ((AddDishActivity)context).updatePizzaChangesOnImage(listPosition, itemPosition, imgSrc);

                }

                createChange.getChangesByTypesList().set(itemPosition, pizzaChange1);
                notifyDataSetChanged();
                //DATA SET CHANGE
            }
        });







        leftPizzaImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                PizzaChange pizzaChangeDish = (PizzaChange) changes.getChangesByTypesList().get(position);
                int itemPosition = -1;
                int imgSrc = -1;

                for (int i = 0; i < createChange.getChangesByTypesList().size(); i++) {

                    if (finalPizzaChange.getId() == ((PizzaChange)createChange.getChangesByTypesList().get(i)).getId()){
                        itemPosition = i;
                        break;
                    }

                }


                for (int i = 0; i < changePizzaHelper.getChangePizzaTypes().size(); i++) {

                    if (finalPizzaChange.getId() == changePizzaHelper.getChangePizzaTypes().get(i).getId()){
                        imgSrc = changePizzaHelper.getChangePizzaTypes().get(i).getSrcImg();
                    }

                }


                PizzaChange pizzaChange1 = (PizzaChange)createChange.getChangesByTypesList().get(itemPosition);


                if (pizzaChange1.isLeftSide()) {
                    pizzaChange1.setLeftSide(false);
                    ((AddDishActivity)context).updateSidesOfPizzas(position);
                } else {

                    pizzaChange1.setBothSides(false);
                    pizzaChange1.setLeftSide(true);
                    ((AddDishActivity)context).updatePizzaChangesOnImage(listPosition, itemPosition, imgSrc);

                }

                createChange.getChangesByTypesList().set(itemPosition, pizzaChange1);
                notifyDataSetChanged();
                //DATA SET CHANGE
            }
        });







        rightPizzaSideImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                PizzaChange pizzaChangeDish = (PizzaChange) changes.getChangesByTypesList().get(position);
                int itemPosition = -1;
                int imgSrc = -1;

                for (int i = 0; i < createChange.getChangesByTypesList().size(); i++) {

                    if (finalPizzaChange.getId() == ((PizzaChange)createChange.getChangesByTypesList().get(i)).getId()){
                        itemPosition = i;
                        break;
                    }

                }


                for (int i = 0; i < changePizzaHelper.getChangePizzaTypes().size(); i++) {

                    if (finalPizzaChange.getId() == changePizzaHelper.getChangePizzaTypes().get(i).getId()){
                        imgSrc = changePizzaHelper.getChangePizzaTypes().get(i).getSrcImg();
                    }

                }


                PizzaChange pizzaChange1 = (PizzaChange)createChange.getChangesByTypesList().get(itemPosition);



                if (pizzaChange1.isRightSide()) {
                    pizzaChange1.setRightSide(false);
                    ((AddDishActivity)context).updateSidesOfPizzas(position);
                } else {

                    pizzaChange1.setBothSides(false);
                    pizzaChange1.setRightSide(true);
                    ((AddDishActivity)context).updatePizzaChangesOnImage(listPosition, itemPosition, imgSrc);

                }


                createChange.getChangesByTypesList().set(itemPosition, pizzaChange1);
                notifyDataSetChanged();
                //DATA SET CHANGE
            }
        });



        changeNameTextView.setText(pizzaChange.getName());


        return convertView;
    }


}
