package com.example.nishnushim.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.AreasForDelivery;

import java.util.Calendar;
import java.util.List;

public class OpenCloseRestaurantHoursAdapter extends BaseAdapter {

    Context context;
    List<String> openHours, closeHours;
    String[] daysString = {"ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"};
    LayoutInflater layoutInflater;
    int positionDay = 0;

    public OpenCloseRestaurantHoursAdapter(Context context, List<String> openHours, List<String> closeHours) {
        this.context = context;
        this.openHours = openHours;
        this.closeHours = closeHours;

        this.layoutInflater = LayoutInflater.from(this.context);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                positionDay = 0;
                break;
            case Calendar.MONDAY:
                positionDay = 1;
                break;
            case Calendar.TUESDAY:
                positionDay = 2;
                break;

            case Calendar.WEDNESDAY:
                positionDay = 3;
                break;

            case Calendar.THURSDAY:
                positionDay = 4;
                break;


            case Calendar.FRIDAY:
                positionDay = 5;
                break;

            case Calendar.SATURDAY:
                positionDay = 6;
                break;
        }
    }


    @Override
    public int getCount() {
        return openHours.size();
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

        convertView = layoutInflater.inflate(R.layout.open_close_restaurant_hour_item, null);

        TextView dayTextView = convertView.findViewById(R.id.day_name_text_view_open_close_restaurant_hour_item);
        TextView openCloseHourTextView = convertView.findViewById(R.id.open_close_hours_text_view_open_close_restaurant_hour_item);


        dayTextView.setText(daysString[position]);
        openCloseHourTextView.setText(openHours.get(position) + "-" + closeHours.get(position));

        if (position == positionDay){

            dayTextView.setTextColor(ContextCompat.getColor(context,R.color.custom_red));
            openCloseHourTextView.setTextColor(ContextCompat.getColor(context,R.color.custom_red));
        }

        return convertView;

    }


}
