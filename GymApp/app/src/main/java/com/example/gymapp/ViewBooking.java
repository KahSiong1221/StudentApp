package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewBooking extends ListActivity {

    private ArrayList<DayTimeslot> BookingList = new ArrayList<>();
    ImageButton menuButton;
    ImageButton userButton;
    private GymDatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);
        menuButton = (ImageButton) findViewById(R.id.menuButton);
        userButton = (ImageButton) findViewById(R.id.userButton);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewBooking.this, Menu.class));
            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewBooking.this, UserActivity.class));
            }
        });

        GymDatabaseManager dbManager = new GymDatabaseManager(this);
        dbManager.open();


        Cursor cursor = dbManager.getUserBookings();

        BookingList.clear();
        if (cursor.moveToFirst()) {
            do {
                DayTimeslot bookingSlots= new DayTimeslot();
                bookingSlots.setGymSlot_id(Integer.parseInt(cursor.getString(0)));
                bookingSlots.setDay(cursor.getString(1));
                bookingSlots.setTime_start(cursor.getString(2));
                bookingSlots.setTime_end(cursor.getString(3));
                bookingSlots.setType(cursor.getString(4));
                // Adding task to list
                BookingList.add(bookingSlots);
            } while (cursor.moveToNext());
        }

        BookingListAdapter bookingListAdapter = new BookingListAdapter(this, R.layout.timeslots, BookingList);
        setListAdapter(bookingListAdapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id)
    {

        DayTimeslot timeClicked = (DayTimeslot) l.getItemAtPosition(position);

        // switching screens requires an intent
        Intent intent = new Intent(getApplicationContext(), BookCancel.class);

        // add the data to send to the next screen onto the intent as "extras"
        intent.putExtra("id", timeClicked.getGymSlot_id());
        intent.putExtra("type", timeClicked.getType());
        intent.putExtra("start", timeClicked.getTime_start());
        intent.putExtra("end", timeClicked.getTime_end());
        intent.putExtra("day", timeClicked.getDay());

        // start the next activity
        startActivity(intent);
    }



    class BookingListAdapter extends ArrayAdapter<DayTimeslot> {
        ArrayList<DayTimeslot> BookingList;

        public BookingListAdapter(@NonNull Context context, int textViewResourceId, ArrayList<DayTimeslot> bookingList) {
            super(context, textViewResourceId, bookingList);
            this.BookingList = bookingList;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.timeslots, null);
            }

            DayTimeslot currentBookingSlot = BookingList.get(position);

            if (currentBookingSlot != null){
                TextView startTime = (TextView) v.findViewById(R.id.startTime);
                TextView endTime = (TextView) v.findViewById(R.id.endTime);
                TextView type = (TextView) v.findViewById(R.id.type);
                TextView day = (TextView) v.findViewById(R.id.day);

                startTime.setText(currentBookingSlot.getTime_start());
                endTime.setText(currentBookingSlot.getTime_end());
                type.setText(currentBookingSlot.getType());
                day.setText(currentBookingSlot.getDay());
            }

            return v;
        }
    }

}