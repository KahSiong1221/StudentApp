package com.example.studentapp_eoc;

import android.content.Context;
import android.graphics.Color;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = (TextView) super.getView(position, convertView, parent);
        tv.setText(restaurants.get(position).getRestName());

        if (position == 0) {
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }

        return tv;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
        tv.setText(restaurants.get(position).getRestName());

        if (position == 0) {
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }

        return tv;
    }
}