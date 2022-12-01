package com.example.gymapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymapp.BookGymTimeslot;
import com.example.gymapp.GymDatabaseManager;
import com.example.gymapp.GymSubscription;
import com.example.gymapp.R;
import com.example.gymapp.UserActivity;

public class Menu extends AppCompatActivity {

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

        //dbManager.addUser(new User("Joshua Crispo","jcrispo@gmail.com", 0));

        Cursor cursor = dbManager.getUser();
        cursor.moveToFirst();
        welcome = (TextView) this.findViewById(R.id.welcome);


        String userName = cursor.getString(1);
        String[] fName = userName.split(" ", 2);
        String welcomeText = "Welcome, " + fName[0];
        welcome.setText(welcomeText);
        cursor.close();

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Menu.this, SidebarMenu.class));
            }
        });



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
                int mem_check = (cursor2.getInt(3));

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
}