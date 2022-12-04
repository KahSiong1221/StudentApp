package com.example.libraryapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class bookRoomActivity extends ListActivity {

    ArrayList<libraryRoom> roombookings;
    private final DatabaseManager dbManager = new DatabaseManager(this);
    private roombookingAdapter myAdapter;
    private String bookingName;
    private String bookingfloor;
    private String bookingStartTime;
    private String bookingEndTime;
    private String bookingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayroombooking);
        Button exitChooseBookingButton = (Button) findViewById(R.id.exitRoomBooking);
        TextView roomBookingdate = (TextView) findViewById(R.id.roomBookingDate);
        TextView roomBookingFloor = (TextView) findViewById(R.id.roomBookingFloor);

        Bundle data = getIntent().getExtras();
        bookingfloor = Integer.toString(data.getInt("floor"));
        bookingStartTime = data.getString("startTime");
        bookingEndTime = data.getString("endTime");
        bookingDate = data.getString("date");

        String myString = bookingStartTime + "-" + bookingEndTime + "  " + bookingDate;
        roomBookingdate.setText(myString);
        roomBookingFloor.setText("Floor: " + bookingfloor);


        roombookings = createRoom(bookingStartTime, bookingEndTime, bookingDate, data.getInt("floor"));
        myAdapter = new roombookingAdapter(this, R.layout.booking, roombookings);
        setListAdapter(myAdapter);


        exitChooseBookingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }

    public ArrayList<libraryRoom> createRoom(String starTime, String endTime, String Date, int floor) {
        dbManager.open();
        Cursor cursor;
        Cursor cursor2;
        ArrayList<libraryRoom> libraryRoom = new ArrayList<libraryRoom>();
        cursor = dbManager.getRoom(floor);

        if (cursor.moveToFirst()) {
            do {
                libraryRoom thisLibraryRoom = new libraryRoom(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        "Available");
                libraryRoom.add(thisLibraryRoom);
            }

            while (cursor.moveToNext());
        }

        Log.i("This  computer count", Integer.toString(cursor.getCount()));
        if (libraryRoom.size() >= 1) {
            for (libraryRoom thisLibraryRoom : libraryRoom) {
                cursor2 = dbManager.validateRoomBooking(starTime, endTime, Date, thisLibraryRoom.getRoomId());
                if (cursor2.getCount() > 1) {
                    thisLibraryRoom.setRoomStatus("Unavailable");
                }

            }
        }

        dbManager.close();
        return libraryRoom;
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        libraryRoom libraryRoomClicked = (libraryRoom) l.getItemAtPosition(position);

        // switching screens requires an intent
        if (libraryRoomClicked.getRoomStatus() == "Available") {
            Intent intent = new Intent(bookRoomActivity.this, bookConfirmLibrary.class);

            // add the data to send to the next screen onto the intent as "extras"
            intent.putExtra("bookingName", libraryRoomClicked.getRoomName());
            intent.putExtra("floor", bookingfloor);
            intent.putExtra("startTime", bookingStartTime);
            intent.putExtra("endTime", bookingEndTime);
            intent.putExtra("date", bookingDate);
            intent.putExtra("BOOKING_KEY", "LibraryRoom");
            intent.putExtra("booking_type", "Room");

            // start the next activity
            startActivity(intent);
        }
        else{
            Log.i("Error has occured"," booking key not available");
        }
    }

    class roombookingAdapter extends ArrayAdapter<libraryRoom> {

        ArrayList<libraryRoom> libraryRoom;

        public roombookingAdapter(Context context, int textViewresourceId, ArrayList<libraryRoom> objects) {
            super(context, textViewresourceId, objects);
            this.libraryRoom = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.booking, null);
            }

            libraryRoom currentLibraryRoom = libraryRoom.get(position);

            if (currentLibraryRoom != null) {
                Log.i("Computer in adapter", currentLibraryRoom.getRoomName());
                TextView bookingNameText = (TextView) v.findViewById(R.id.bookingNameView);
                TextView bookingDescText = (TextView) v.findViewById(R.id.bookingDescView);
                TextView bookingStatus = (TextView) v.findViewById(R.id.bookingStatusView);


                bookingNameText.setText("ID: " + currentLibraryRoom.getRoomName());
                bookingDescText.setText("OS: " + currentLibraryRoom.getRoomDesc());
                bookingStatus.setText(currentLibraryRoom.getRoomStatus());

                Bundle data = getIntent().getExtras();


            }
            return v;
        }
    }
}