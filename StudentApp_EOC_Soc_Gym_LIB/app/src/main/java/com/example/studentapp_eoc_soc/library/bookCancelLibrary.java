package com.example.studentapp_eoc_soc.library;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


public class bookCancelLibrary extends AppCompatActivity {

    public  static final String SHARED_PREFS = "shared_prefs";
    public  static final String USER_ID_KEY = "user_key";
    private SharedPreferences sharedPreferences;
    private int user_ID;


    private String BOOKINGTYPE_KEY;
    private String bookingID;
    private String bookingName;
    private String bookingStartTime;
    private String bookingEndTime;
    private String bookingDate;
    private String bookingType;

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navView;


    lib_DbManager myDbManager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_confirm_cancel);

        myDbManager = new lib_DbManager(getApplicationContext());
        // buttons and text strings
        String myFullTime;
        String myID;

        TextView bookingDateView = (TextView)findViewById(R.id.cancelDateText);
        TextView bookingIDView = (TextView) findViewById(R.id.cancelIdText);
        TextView bookingNameView = (TextView) findViewById(R.id.cancelNameText);
        TextView bookingTypeView = (TextView) findViewById(R.id.cancelBookingTypeText);
        Button exitConfirmBookingButton = (Button)findViewById(R.id.exitCancelBooking);
        Button confirmCancelButton = (Button)findViewById(R.id.confirmCancelButton);



        // Important details like username as stored in sessions
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        user_ID = sharedPreferences.getInt(USER_ID_KEY,-1);
         //bundle Data
        Bundle data = getIntent().getExtras();
        BOOKINGTYPE_KEY = data.getString("BOOKINGTYPE_KEY");
        bookingID = Integer.toString(data.getInt("BookingID"));
        bookingName = data.getString("BookingName");
        bookingStartTime = data.getString("startTime");
        bookingEndTime = data.getString("endTime");
        bookingDate = data.getString("date");
        bookingType = data.getString("BookingType");



        myFullTime = "Date: " + bookingStartTime+"-"+bookingEndTime+"  "+bookingDate;
        myID = "ID: " + bookingID;

            bookingDateView.setText(myFullTime);
            bookingIDView.setText(myID);
            bookingNameView.setText(bookingName);
            bookingTypeView.setText(bookingType);


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

            exitConfirmBookingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
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

    public void cancelComputerBooking(int user_ID, int booking_ID){


        boolean validator = myDbManager.removeMyComputerBooking(user_ID,booking_ID);

        if (!validator) {
            Toast.makeText(getApplicationContext(),"Error occured when cancelling Computer",Toast.LENGTH_SHORT).show();

        }
        Intent intent = new Intent(this, libraryMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

    public void cancelRoomBooking(int user_ID, int room_ID){


        boolean validator = myDbManager.removeMyRoomBooking(user_ID,room_ID);

        if (!validator) {
            Toast.makeText(getApplicationContext(),"Error occured when cancelling Room",Toast.LENGTH_SHORT).show();

        }
        Intent intent = new Intent(this, libraryMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }
}
