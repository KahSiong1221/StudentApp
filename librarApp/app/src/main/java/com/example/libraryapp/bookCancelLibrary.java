package com.example.libraryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class bookCancelLibrary extends Activity {

    public  static final String SHARED_PREFS = "shared_prefs";
    public  static final String USER_ID_KEY = "user_key";
    private SharedPreferences sharedPreferences;
    private int user_ID;


    private String BOOKINGTYPE_KEY;

    DatabaseManager myDbManager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmcancellibrary);

        myDbManager = new DatabaseManager(getApplicationContext());
        // buttons and text strings
        String myFullTime = " ";
        String myFloor = "Floor: ";
        TextView bookingDateView = (TextView)findViewById(R.id.cancelDateText);
        TextView bookingFloorView = (TextView) findViewById(R.id.cancelFLoorText);
        TextView bookingTypeView = (TextView) findViewById(R.id.cancelBookingTypeText);
        Button exitConfirmBookingButton = (Button)findViewById(R.id.exitCancelBooking);
        Button confirmCancelButton = (Button)findViewById(R.id.confirmCancelButton);


        // Important details like username as stored in sessions
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        user_ID = sharedPreferences.getInt(USER_ID_KEY,-1);
         //bundle Data
        Bundle data = getIntent().getExtras();
        BOOKINGTYPE_KEY = data.getString("BOOKINGTYPE_KEY");



            bookingDateView.setText(myFullTime);


            confirmCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(BOOKINGTYPE_KEY.equals("LibraryComputer")){
                    myDbManager.open();
                    cancelComputerBooking(user_ID,data.getInt("BookingID"));
                    myDbManager.close();
                    }

                    if(BOOKINGTYPE_KEY.equals("LibraryRoom")){
                        myDbManager.open();
                        cancelRoomBooking(user_ID,data.getInt("BookingID"));
                        myDbManager.close();
                    }




                }
            });




    }
    public void cancelComputerBooking(int user_ID, int booking_ID){


        boolean validator = myDbManager.removeMyComputerBooking(user_ID,booking_ID);

        if (!validator) {
            Toast.makeText(getApplicationContext(),"Error occured when cancelling Computer",Toast.LENGTH_SHORT).show();

        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

    public void cancelRoomBooking(int user_ID, int room_ID){


        boolean validator = myDbManager.removeMyRoomBooking(user_ID,room_ID);

        if (!validator) {
            Toast.makeText(getApplicationContext(),"Error occured when cancelling Room",Toast.LENGTH_SHORT).show();

        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }
}
