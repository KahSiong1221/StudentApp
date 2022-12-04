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

public class bookComputerActivity extends ListActivity {

    ArrayList<libraryComputer> computerbookings;
    private final DatabaseManager dbManager = new DatabaseManager(this);
    private computerbookingAdapter myAdapter;
    private String bookingName;
    private String bookingfloor;
    private String bookingStartTime;
    private String bookingEndTime;
    private String bookingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaycomputerbooking);
        Button exitChooseBookingButton = (Button) findViewById(R.id.exitComputerBooking);
        TextView computerBookingdate = (TextView) findViewById(R.id.computerBookingDate);
        TextView computerBookingFloor = (TextView) findViewById(R.id.computerBookingFloor);

        Bundle data = getIntent().getExtras();
        bookingfloor = Integer.toString(data.getInt("floor"));
        bookingStartTime = data.getString("startTime");
        bookingEndTime = data.getString("endTime");
        bookingDate = data.getString("date");

        String myString = bookingStartTime + "-" + bookingEndTime + "  " + bookingDate;
        computerBookingdate.setText(myString);
        computerBookingFloor.setText("Floor: " + bookingfloor);


        computerbookings = createComputer(bookingStartTime, bookingEndTime, bookingDate, data.getInt("floor"));
        myAdapter = new bookComputerActivity.computerbookingAdapter(this, R.layout.booking, computerbookings);
        setListAdapter(myAdapter);


        exitChooseBookingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }

    public ArrayList<libraryComputer> createComputer(String starTime, String endTime, String Date, int floor) {
        dbManager.open();
        Cursor cursor;
        Cursor cursor2;
        ArrayList<libraryComputer> libraryComputers = new ArrayList<libraryComputer>();
        cursor = dbManager.getComputer(floor);

        if (cursor.moveToFirst()) {
            do {
                libraryComputer thisLibraryComputer = new libraryComputer(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        "Available");
                libraryComputers.add(thisLibraryComputer);
            }

            while (cursor.moveToNext());
        }

        Log.i("This  computer count", Integer.toString(cursor.getCount()));
        if (libraryComputers.size() >= 1) {
            for (libraryComputer thisLibraryComputer : libraryComputers) {
                cursor2 = dbManager.validateComputerBooking(starTime, endTime, Date, thisLibraryComputer.getComputerId());
                if (cursor2.getCount() > 1) {
                    thisLibraryComputer.setComputerStatus("Unavailable");
                }

            }
        }

        dbManager.close();
        return libraryComputers;
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        libraryComputer libraryComputerClicked = (libraryComputer) l.getItemAtPosition(position);

        // switching screens requires an intent
        if (libraryComputerClicked.getComputerStatus() == "Available") {
            Intent intent = new Intent(bookComputerActivity.this, bookConfirmLibrary.class);

            // add the data to send to the next screen onto the intent as "extras"
            intent.putExtra("bookingName", libraryComputerClicked.getComputerName());
            intent.putExtra("floor", bookingfloor);
            intent.putExtra("startTime", bookingStartTime);
            intent.putExtra("endTime", bookingEndTime);
            intent.putExtra("date", bookingDate);
            intent.putExtra("BOOKING_KEY", "LibraryComputer");
            intent.putExtra("booking_type", "Computer");

            // start the next activity
            startActivity(intent);
        }
        else{
            Log.i("Error has occured"," booking not available");
        }

    }

    class computerbookingAdapter extends ArrayAdapter<libraryComputer> {

        ArrayList<libraryComputer> libraryComputers;

        public computerbookingAdapter(Context context, int textViewresourceId, ArrayList<libraryComputer> objects) {
            super(context, textViewresourceId, objects);
            this.libraryComputers = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.booking, null);
            }

            libraryComputer currentLibraryComputer = libraryComputers.get(position);

            if (currentLibraryComputer != null) {
                Log.i("Computer in adapter", currentLibraryComputer.getComputerName());
                TextView bookingNameText = (TextView) v.findViewById(R.id.bookingNameView);
                TextView bookingDescText = (TextView) v.findViewById(R.id.bookingDescView);
                TextView bookingStatus = (TextView) v.findViewById(R.id.bookingStatusView);


                bookingNameText.setText("ID: " + currentLibraryComputer.getComputerName());
                bookingDescText.setText("OS: " + currentLibraryComputer.getComputerDesc());
                bookingStatus.setText(currentLibraryComputer.getComputerStatus());

                Bundle data = getIntent().getExtras();


            }
            return v;
        }
    }
}