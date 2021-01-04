package com.example.nishnushim.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nishnushim.R;
import com.example.nishnushim.helpclasses.AreasForDelivery;

import java.util.List;

public class OpenCloseRestaurantHoursAdapter extends BaseAdapter {

    Context context;
    List<String> openHours, closeHours;
    String[] daysString = {"ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"};
    LayoutInflater layoutInflater;

    public OpenCloseRestaurantHoursAdapter(Context context, List<String> openHours, List<String> closeHours) {
        this.context = context;
        this.openHours = openHours;
        this.closeHours = closeHours;

        this.layoutInflater = LayoutInflater.from(this.context);
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

        return convertView;

    }



}
