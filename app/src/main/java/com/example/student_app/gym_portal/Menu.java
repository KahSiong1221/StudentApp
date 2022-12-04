package com.example.student_app.gym_portal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.student_app.R;
import com.example.student_app.SaDbManager;

public class Menu extends AppCompatActivity implements GestureDetector.OnGestureListener {

    // Initialising swiping variables
    // private static final String TAG = "Swipe Positon";
    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 150;
    private GestureDetector gestureDetector;


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

        // Initalise gesturedetector
        this.gestureDetector = new GestureDetector(Menu.this, this);
        SaDbManager dbManager = new SaDbManager(this);
        dbManager.open();

        dbManager.addUser(new User("Joshua Crispo","jcrispo@gmail.com", 0));

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


    // REFERENCE START
    // https://www.youtube.com/watch?v=oFl7WwEX2Co
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch(event.getAction()){
            //starting to swipe time gesture
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;

            //ending time swipe gesture
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                //getting values for horizontal swipe and vertical swipe
                float valueX = x2 - x1;
                float valueY = y2 - y1;

                if (Math.abs(valueX) > MIN_DISTANCE)
                {
                    //detect left to right
                    if (x2>x1)
                    {
                        //do nothing has toast to check
                        //Toast.makeText(this,"Right is swiped", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Menu.this, SidebarMenu.class));
                    }
                    else
                    {
                        //detect right to left swipe
                        //do nothing has toast to check
                        //Toast.makeText(this,"Left is swiped", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (Math.abs(valueY) > MIN_DISTANCE)
                {
                    //detect top to bottom swipe
                    if (y2 > y1)
                    {
                        //do nothing has toast to check
                        //Toast.makeText(this,"Bottom is swiped", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, "Bottom swipe");
                    }
                    else
                    {
                        //detect bottom to top swipe
                        //do nothing has toast to check
                        //Toast.makeText(this,"Top is swiped", Toast.LENGTH_SHORT).show();

                    }
                }
        }


        return super.onTouchEvent(event);
    }

    // REFERENCE FINISH

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

}