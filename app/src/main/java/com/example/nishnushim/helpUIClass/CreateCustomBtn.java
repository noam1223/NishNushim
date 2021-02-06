package com.example.nishnushim.helpUIClass;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.Dish;
import com.example.nishnushim.helpclasses.DishChanges;

public class CreateCustomBtn {


    public static RadioButton createRadioAddressBtn(Context context, RadioButton myLocationToDeliverRadioBtn, int size, int idNum, int drawableResource, String text) {

        RadioButton radioButton = new RadioButton(context);
        radioButton.setId(size + idNum);
        radioButton.setLayoutParams(myLocationToDeliverRadioBtn.getLayoutParams());
        radioButton.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.radio_button_inset));
        radioButton.setChecked(false);
        radioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, drawableResource), null);
        radioButton.setCompoundDrawablePadding(24);
        radioButton.setTypeface(ResourcesCompat.getFont(context, R.font.assistant_regular));
        radioButton.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_button_background_top_screen_customize));
        radioButton.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        radioButton.setPadding(myLocationToDeliverRadioBtn.getPaddingLeft(), myLocationToDeliverRadioBtn.getPaddingTop(), myLocationToDeliverRadioBtn.getPaddingRight(), myLocationToDeliverRadioBtn.getPaddingBottom());
        radioButton.setTag(String.valueOf(size));
        radioButton.setText(text);
        radioButton.setTextColor(ContextCompat.getColor(context, R.color.custom_blue));
        radioButton.setTextSize(13);
        return radioButton;
    }


    public static RadioButton createRadioChanges(Context context, RadioButton myLocationToDeliverRadioBtn, int size, int idNum, int drawableResource, String text) {

        RadioButton radioButton = new RadioButton(context);
        radioButton.setId(size + idNum);
        radioButton.setLayoutParams(myLocationToDeliverRadioBtn.getLayoutParams());
        radioButton.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.radio_button_inset_changes));
        radioButton.setChecked(false);
        radioButton.setCompoundDrawablePadding(24);
        radioButton.setTypeface(ResourcesCompat.getFont(context, R.font.assistant_regular));
        radioButton.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_button_background_top_screen_customize_opposite));
        radioButton.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        radioButton.setPadding(myLocationToDeliverRadioBtn.getPaddingLeft(), myLocationToDeliverRadioBtn.getPaddingTop(), myLocationToDeliverRadioBtn.getPaddingRight(), myLocationToDeliverRadioBtn.getPaddingBottom());
        radioButton.setTag(String.valueOf(size));
        radioButton.setText(text);
        radioButton.setTextColor(ContextCompat.getColor(context, R.color.custom_blue));
        radioButton.setTextSize(13);
        return radioButton;
    }








    public static CheckBox createCheckBox(Context context,CheckBox copyBtn, int size, int idNum, String text, DishChanges dishChanges) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setId(size + idNum);
        checkBox.setTag(dishChanges);
        checkBox.setLayoutParams(copyBtn.getLayoutParams());
        checkBox.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.radio_button_inset_dish_changes));
        checkBox.setChecked(false);
        checkBox.setTypeface(ResourcesCompat.getFont(context, R.font.assistant_regular));
        checkBox.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_button_background_top_screen_customize));
        checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        checkBox.setPadding(copyBtn.getPaddingLeft(), copyBtn.getPaddingTop(), copyBtn.getPaddingRight(), copyBtn.getPaddingBottom());
        checkBox.setTag(String.valueOf(size));
        checkBox.setText(text);
        checkBox.setTextColor(ContextCompat.getColor(context, R.color.custom_blue));
        checkBox.setTextSize(13);
        return checkBox;
    }



    public static CheckBox createChangesCheckBox(Context context,CheckBox copyBtn, int size, int idNum, String text, DishChanges dishChanges) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setId(size + idNum);
        checkBox.setTag(dishChanges);
        checkBox.setLayoutParams(copyBtn.getLayoutParams());
        checkBox.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.radio_button_inset_dish_changes));
        checkBox.setChecked(false);
        checkBox.setTypeface(ResourcesCompat.getFont(context, R.font.assistant_regular));
        checkBox.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_button_background_top_screen_customize_opposite));
        checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        checkBox.setPadding(copyBtn.getPaddingLeft(), copyBtn.getPaddingTop(), copyBtn.getPaddingRight(), copyBtn.getPaddingBottom());
        checkBox.setTag(String.valueOf(size));
        checkBox.setText(text);
        checkBox.setTextColor(ContextCompat.getColor(context, R.color.custom_blue));
        checkBox.setTextSize(13);
        return checkBox;
    }



    public static Button createDishBtn(Context context, Button copyBtn, int size, int idNum, String text, Dish dish) {
        Button button = new Button(context);
        button.setId(size + idNum);
        button.setTag(dish);
        button.setLayoutParams(copyBtn.getLayoutParams());
        button.setBackground(ContextCompat.getDrawable(context, R.drawable.pizza_dish_item_clicked));
        button.setTypeface(ResourcesCompat.getFont(context, R.font.assistant_semibold));
        button.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        button.setPadding(copyBtn.getPaddingLeft(), copyBtn.getPaddingTop(), copyBtn.getPaddingRight(), copyBtn.getPaddingBottom());
        button.setText(text);
        button.setTextColor(copyBtn.getTextColors());
        button.setTextSize(14);
        return button;
    }

}
