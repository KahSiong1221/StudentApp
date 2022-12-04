package com.example.studentapp_eoc_soc;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

// reference: https://instinctcoder.com/android-studio-spinner-populate-data-sqlite/
public class RestSpinnerAdapter extends ArrayAdapter<Restaurant> {
    private final List<Restaurant> restaurants;

    public RestSpinnerAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Restaurant> objects) {
        super(context, textViewResourceId, objects);
        this.restaurants = objects;
    }

    @Override
    public boolean isEnabled(int position) {
        // disable first item of spinner that will be used as hint
        return position != 0;
    }

    // instantiate selected restaurant
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // initialise selected restaurant name
        TextView tv = (TextView) super.getView(position, convertView, parent);
        tv.setText(restaurants.get(position).getRestName());
        // text style
        tv.setTypeface(null, Typeface.BOLD);

        // make the selection hint to grey colour
        if (position == 0) {
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }

        return tv;
    }

    // instantiate restaurants in drop down list
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        // initialise restaurant names
        TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
        tv.setText(restaurants.get(position).getRestName());
        // text style
        tv.setTypeface(null, Typeface.BOLD);

        // make the selection hint to grey colour
        if (position == 0) {
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }

        return tv;
    }
}
