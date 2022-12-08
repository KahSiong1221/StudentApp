package com.example.studentapp_eoc_soc.calendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapp_eoc_soc.R;

import java.time.LocalDate;
import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    //Array list to store the amt of days
    private final ArrayList<LocalDate> Calendar_days;
    private final OnItemListener onItemListener;

    //Calendar Constructor
    public CalendarAdapter(ArrayList<LocalDate> Calendar_days, OnItemListener onItemListener) {
        this.Calendar_days = Calendar_days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());//Define layout inflater to create calendar
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        //Conditional Statements to dictate which calendar view to display
        if (Calendar_days.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        else // week view
            layoutParams.height = (int) parent.getHeight();

        return new CalendarViewHolder(view, onItemListener, Calendar_days);
    }


    @Override
    /*
        Stylises the calendar, sets the selected date to a different colour and greys out subsequent month's days
     */
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        final LocalDate date = Calendar_days.get(position); //Local date variable

        holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

        if (date.equals(CalendarFun.selDate))
            holder.parentView.setBackgroundColor(Color.LTGRAY);

        if (date.getMonth().equals(CalendarFun.selDate.getMonth()))
            holder.dayOfMonth.setTextColor(Color.BLACK);
        else
            holder.dayOfMonth.setTextColor(Color.LTGRAY);
    }

    @Override
    public int getItemCount() {
        return Calendar_days.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, LocalDate date);
    }
}