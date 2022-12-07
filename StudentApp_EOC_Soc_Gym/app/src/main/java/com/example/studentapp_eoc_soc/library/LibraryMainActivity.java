package com.example.studentapp_eoc_soc.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.SaDbManager;

public class LibraryMainActivity extends AppCompatActivity {

    //public static User user;

    public  static final String SHARED_PREFS = "shared_prefs";

    public  static final String USER_ID_KEY = "user_key";

    private SaDbManager dbManager = new SaDbManager(this);
    private int user_id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.library_activity_main);
        Button bookSlotButton = (Button)findViewById(R.id.bookSlotButton);
        Button viewBookingButton = (Button)findViewById(R.id.viewBookingButton);

        //insertDummyData();

        bookSlotButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent ChooseBookingintent = new Intent(LibraryMainActivity.this, ChooseBookingActivity.class);
                startActivity(ChooseBookingintent);
            }
        });

        viewBookingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent ChooseBookingintent = new Intent(LibraryMainActivity.this, viewMyBookingActivity.class);
                startActivity(ChooseBookingintent);
            }
        });


    }

    public void insertDummyData(){

        dbManager.open();
        Cursor cursor;
        //dbManager.addUser("Ron Liquit","ronliquit@gmail.com", "0");
        //cursor = dbManager.getUser("ronliquit@gmail.com");
        //cursor.moveToFirst();
        //user = new User(cursor.getInt(0),cursor.getString(1), cursor.getString(2),cursor.getString(3));

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        user_id = sharedPreferences.getInt(USER_ID_KEY,0);
        Log.i("New User ID is :", Integer.toString(user_id));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putInt(USER_ID_KEY,user.getId());
        editor.apply();

        dbManager.addComputer("LG-001",0,"Windows");
        dbManager.addComputer("LG-002",0,"Windows");
        dbManager.addComputer("LG-003",0,"Windows");
        dbManager.addComputer("LG-004",0,"Mac");
        dbManager.addComputer("LG-005",0,"Mac");

        dbManager.addComputer("CQ-101",1,"Windows");
        dbManager.addComputer("CQ-102",1,"Windows");
        dbManager.addComputer("CQ-103",1,"Windows");
        dbManager.addComputer("CQ-104",1,"Mac");
        dbManager.addComputer("CQ-105",1,"Mac");

        dbManager.addComputer("CQ-201",2,"Windows");
        dbManager.addComputer("CQ-202",2,"Windows");
        dbManager.addComputer("CQ-203",2,"Windows");
        dbManager.addComputer("CQ-204",2,"Mac");
        dbManager.addComputer("CQ-205",2,"Mac");

        dbManager.addRoom("EQ-001",0,"Narrow");
        dbManager.addRoom("EQ-002",0,"Wide");
        dbManager.addRoom("EQ-003",0,"Wide");
        dbManager.addRoom("EQ-004",0,"Narrow");
        dbManager.addRoom("EQ-005",0,"Narrow");

        dbManager.addRoom("EQ-101",1,"Spacious");
        dbManager.addRoom("EQ-102",1,"Spacious");
        dbManager.addRoom("EQ-103",1,"Wide");
        dbManager.addRoom("EQ-104",1,"Spacious");
        dbManager.addRoom("EQ-105",1,"Wide");

        dbManager.addRoom("EQ-201",2,"Spacious");
        dbManager.addRoom("EQ-202",2,"Narrow");
        dbManager.addRoom("EQ-203",2,"Wide");
        dbManager.addRoom("EQ-204",2,"Wide");
        dbManager.addRoom("EQ-205",2,"Spacious");

    }
}