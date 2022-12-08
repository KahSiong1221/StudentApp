package com.example.studentapp_eoc_soc.library;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.eating_on_campus.EocActivity;
import com.example.studentapp_eoc_soc.gym_portal.Menu;
import com.example.studentapp_eoc_soc.soc_portal.SocPortalActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class bookConfirmLibrary extends AppCompatActivity {

    public  static final String SHARED_PREFS = "shared_prefs";
    public  static final String USER_ID_KEY = "user_key";
    private SharedPreferences sharedPreferences;
    private int user_ID;

    private lib_DbManager mydbManager = new lib_DbManager(this);
    private String bookingName;
    private String bookingStartTime;
    private String bookingEndTime;
    private String bookingDate;
    private String bookingType;
    private String bookingFloor;
    private String BOOKING_KEY;

    private int bookingTypeID;


    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_confirm_book);
        String myFullTime = " ";
        String myFloor;
        TextView bookingDateView = (TextView)findViewById(R.id.bookingDateText);
        TextView bookingNameView = (TextView) findViewById(R.id.bookingNameText);
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
        bookingTypeID = data.getInt("booking_type_id");
        bookingFloor = data.getString("floor");
        BOOKING_KEY  = data.getString("BOOKING_KEY");




        myFullTime = bookingStartTime + "-" + bookingEndTime  + " " + bookingDate;
        myFloor = "Floor: " + bookingFloor;
        bookingDateView.setText(myFullTime);
        bookingNameView.setText(bookingName);
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
                            Integer.valueOf(bookingFloor),
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
                            Integer.valueOf(bookingFloor),
                            bookingTypeID,
                            user_ID);
                }
                mydbManager.close();

                Intent intent = new Intent(bookConfirmLibrary.this, libraryMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });


    loadSideMenu();
    }


    private void loadSideMenu() {
        drawerLayout = findViewById(R.id.drawer);
        navView = findViewById(R.id.navView);

        // initialise side menu toggle button
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,  R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // return one level up rather than to the top level of app when selecting home (close menu)
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;

                switch(item.getItemId())
                {
                    // home page

                    case R.id.home:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // home page -> eating on campus
                    case R.id.food:
                        intent = new Intent(getApplicationContext(), EocActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // home page -> society portal
                    case R.id.soc:
                        intent = new Intent(getApplicationContext(), SocPortalActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.gym:
                        intent = new Intent(getApplicationContext(), Menu.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.library:
                        intent = new Intent(getApplicationContext(), libraryMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
                return true;
            }
        });
    }

}
