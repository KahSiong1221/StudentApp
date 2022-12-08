package com.example.studentapp_eoc_soc.gym_portal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.studentapp_eoc_soc.MainActivity;
import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.SaDbManager;
import com.example.studentapp_eoc_soc.calendar.EventMainActivity;
import com.example.studentapp_eoc_soc.eating_on_campus.EocActivity;
import com.example.studentapp_eoc_soc.library.libraryMainActivity;
import com.example.studentapp_eoc_soc.soc_portal.SocPortalActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class Menu extends AppCompatActivity{

    // Initialising swiping variables
    // private static final String TAG = "Swipe Positon";
    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 150;
    private GestureDetector gestureDetector;

    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageButton menuButton;
    ImageButton userButton;
    Button bookingButton;
    Button memOptionButton;
    Button viewBooking;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bookingButton = (Button) findViewById(R.id.buttonBook);
        memOptionButton = (Button) findViewById(R.id.buttonBuy);
        viewBooking = (Button) findViewById(R.id.buttonBookings);
        menuButton = (ImageButton) findViewById(R.id.menuButton);
        userButton = (ImageButton) findViewById(R.id.userButton);

        GymDatabaseManager dbManager = new GymDatabaseManager(this);
        dbManager.open();


        /*
        //Adding timeslots
        dbManager.addDayTimeSlots(new DayTimeslot("Monday", "07:00","08:30","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Monday", "8:45", "10:15","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Monday", "10:30", "11:45","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Monday", "11:30", "12:30", "Fitness Class"));
        dbManager.addDayTimeSlots(new DayTimeslot("Monday", "12:00", "13:30", "Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Monday", "13:45", "15:15", "Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Monday", "15:00", "16:00", "Fitness Class"));
        dbManager.addDayTimeSlots(new DayTimeslot("Monday", "15:30", "16:45", "Gym Session"));

        dbManager.addDayTimeSlots(new DayTimeslot("Tuesday", "07:00","08:30","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Tuesday", "8:45", "10:15","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Tuesday", "10:30", "11:45","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Tuesday", "11:30", "12:30", "Fitness Class"));
        dbManager.addDayTimeSlots(new DayTimeslot("Tuesday", "12:00", "13:30", "Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Tuesday", "13:45", "15:15", "Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Tuesday", "15:00", "16:00", "Fitness Class"));
        dbManager.addDayTimeSlots(new DayTimeslot("Tuesday", "15:30", "16:45", "Gym Session"));

        dbManager.addDayTimeSlots(new DayTimeslot("Wednesday", "07:00","08:30","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Wednesday", "8:45", "10:15","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Wednesday", "10:30", "11:45","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Wednesday", "11:30", "12:30", "Fitness Class"));
        dbManager.addDayTimeSlots(new DayTimeslot("Wednesday", "12:00", "13:30", "Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Wednesday", "13:45", "15:15", "Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Wednesday", "15:00", "16:00", "Fitness Class"));
        dbManager.addDayTimeSlots(new DayTimeslot("Wednesday", "15:30", "16:45", "Gym Session"));

        dbManager.addDayTimeSlots(new DayTimeslot("Thursday", "07:00","08:30","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Thursday", "8:45", "10:15","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Thursday", "10:30", "11:45","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Thursday", "11:30", "12:30", "Fitness Class"));
        dbManager.addDayTimeSlots(new DayTimeslot("Thursday", "12:00", "13:30", "Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Thursday", "13:45", "15:15", "Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Thursday", "15:00", "16:00", "Fitness Class"));
        dbManager.addDayTimeSlots(new DayTimeslot("Thursday", "15:30", "16:45", "Gym Session"));

        dbManager.addDayTimeSlots(new DayTimeslot("Friday", "07:00","08:30","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Friday", "8:45", "10:15","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Friday", "10:30", "11:45","Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Friday", "11:30", "12:30", "Fitness Class"));
        dbManager.addDayTimeSlots(new DayTimeslot("Friday", "12:00", "13:30", "Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Friday", "13:45", "15:15", "Gym Session"));
        dbManager.addDayTimeSlots(new DayTimeslot("Friday", "15:00", "16:00", "Fitness Class"));
        dbManager.addDayTimeSlots(new DayTimeslot("Friday", "15:30", "16:45", "Gym Session"));

        //Adding memberships

        dbManager.addMemberships(new Membership("30 Day Membership", "$30"));
        dbManager.addMemberships(new Membership("90 Day Membership", "$70"));
        dbManager.addMemberships(new Membership("180 Day Membership", "$120"));
        dbManager.addMemberships(new Membership("360 Day Membership", "$180"));
        */

        Cursor cursor = dbManager.getUser();
        cursor.moveToFirst();
        welcome = (TextView) this.findViewById(R.id.welcome);


        String userName = cursor.getString(1);
        String[] fName = userName.split(" ", 2);
        String welcomeText = "Welcome, " + fName[0];
        welcome.setText(welcomeText);
        cursor.close();
        loadSideMenu();

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, UserActivity.class));
            }
        });

        memOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, GymSubscription.class));
            }
        });

        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor2 = dbManager.getUser();
                cursor2.moveToFirst();
                int mem_check = (cursor2.getInt(4));

                if (mem_check == 1) {
                    startActivity(new Intent(Menu.this, BookGymTimeslot.class));
                }
                else if (mem_check == 0) {
                    Toast.makeText(Menu.this, "You are not a member", Toast.LENGTH_LONG).show();
                }
            }
        });

        viewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, ViewBooking.class));
            }
        });


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
                    // home page -> gym home
                    case R.id.gym:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // home page -> Gym info
                    case R.id.info:
                        intent = new Intent(getApplicationContext(), GymInfo.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // home page -> society portal
                    case R.id.home:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.soc:
                        intent = new Intent(getApplicationContext(), SocPortalActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.food:
                        intent = new Intent(getApplicationContext(), EocActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.library:
                        intent = new Intent(getApplicationContext(), libraryMainActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.timetable:
                        intent = new Intent(getApplicationContext(), EventMainActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }

}