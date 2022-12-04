package com.example.libraryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class bookConfirmLibrary extends Activity {

    public  static final String SHARED_PREFS = "shared_prefs";
    public  static final String USER_ID_KEY = "user_key";
    private SharedPreferences sharedPreferences;
    private int user_ID;

    private DatabaseManager mydbManager = new DatabaseManager(this);
    private String bookingName;
    private String bookingfloor;
    private String bookingStartTime;
    private String bookingEndTime;
    private String bookingDate;
    private String bookingType;
    private String BOOKING_KEY;
    private int bookingTypeID = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmbooklibrary);
        String myFullTime = " ";
        String myFloor = "Floor: ";
        TextView bookingDateView = (TextView)findViewById(R.id.bookingDateText);
        TextView bookingFloorView = (TextView) findViewById(R.id.bookingFloorText);
        TextView bookingTypeView = (TextView) findViewById(R.id.bookingTypeText);
        Button exitConfirmBookingButton = (Button)findViewById(R.id.exitConfrimBookingButton);
        Button confirmBookingButton = (Button)findViewById(R.id.confirmBookingButton);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        user_ID = sharedPreferences.getInt(USER_ID_KEY,-1);

        Bundle data  = getIntent().getExtras();
        bookingName   = data.getString("bookingName");

        bookingStartTime  = data.getString("startTime");
        bookingEndTime   = data.getString("endTime");
        bookingDate  = data.getString("date");
        bookingType  = data.getString("booking_type");
        BOOKING_KEY  = data.getString("BOOKING_KEY");
        bookingTypeID = data.getInt("booking_type_id");


        myFullTime = bookingStartTime + "-" + bookingEndTime  + " " + bookingDate;
        myFloor = myFloor + bookingfloor;
        bookingDateView.setText(myFullTime);
        bookingFloorView.setText(myFloor);
        bookingTypeView.setText(bookingType);


        exitConfirmBookingButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
            finish();
            }
        });



        confirmBookingButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                String Status ="Active";

                mydbManager.open();

                if(BOOKING_KEY.equals("LibraryComputer")) {
                    Log.i("Booking key", BOOKING_KEY);
                    mydbManager.addComputerBooking(
                            bookingName,
                            bookingStartTime,
                            bookingEndTime,
                            bookingDate,
                            Status,
                            bookingTypeID,
                            user_ID);
                }

                if (BOOKING_KEY.equals("LibraryRoom")) {
                    Log.i("Booking key", BOOKING_KEY);
                    mydbManager.addRoomBooking(
                            bookingName,
                            bookingStartTime,
                            bookingEndTime,
                            bookingDate,
                            Status,
                            bookingTypeID,
                            user_ID);
                }
                mydbManager.close();

                Intent intent = new Intent(bookConfirmLibrary.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });



    }
}
