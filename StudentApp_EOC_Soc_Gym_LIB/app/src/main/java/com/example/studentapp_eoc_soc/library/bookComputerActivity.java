package com.example.studentapp_eoc_soc.library;

import com.example.studentapp_eoc_soc.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.studentapp_eoc_soc.eating_on_campus.EocActivity;
import com.example.studentapp_eoc_soc.gym_portal.Menu;
import com.example.studentapp_eoc_soc.soc_portal.SocPortalActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;



public class bookComputerActivity extends AppCompatActivity {

    public  static final String SHARED_PREFS = "shared_prefs";
    public  static final String USER_ID_KEY = "user_key";
    private SharedPreferences sharedPreferences;
    private int user_ID;

    ArrayList<libraryComputer> computerbookings;
    private final lib_DbManager dbManager = new lib_DbManager(this);
    private computerbookingAdapter myAdapter;
    private String bookingName;
    private String bookingfloor;
    private String bookingStartTime;
    private String bookingEndTime;
    private String bookingDate;

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_display_computer_booking);
        Button exitChooseBookingButton = (Button) findViewById(R.id.exitComputerBooking);
        TextView computerBookingdate = (TextView) findViewById(R.id.computerBookingDate);
        TextView computerBookingFloor = (TextView) findViewById(R.id.computerBookingFloor);
        ListView lv = (ListView)findViewById(android.R.id.list);
        int bookingfloorInt;

        Bundle data = getIntent().getExtras();
        bookingfloor = data.getString("floor");
        bookingStartTime = data.getString("startTime");
        bookingEndTime = data.getString("endTime");
        bookingDate = data.getString("date");

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        user_ID = sharedPreferences.getInt(USER_ID_KEY,-1);

        bookingfloorInt = Integer.valueOf(bookingfloor);

        String myString = bookingStartTime + "-" + bookingEndTime + "  " + bookingDate;
        computerBookingdate.setText(myString);
        computerBookingFloor.setText("Floor: " + bookingfloor);


        computerbookings = createComputer(bookingStartTime, bookingEndTime, bookingDate, bookingfloorInt);
        myAdapter = new bookComputerActivity.computerbookingAdapter(this, R.layout.library_booking, computerbookings);
        myAdapter.notifyDataSetChanged();
        lv.setAdapter(myAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                libraryComputer libraryComputerClicked = (libraryComputer) adapterView.getItemAtPosition(i);

                // switching screens requires an intent
                if (libraryComputerClicked.getComputerStatus() == "Available") {
                    Intent intent = new Intent(bookComputerActivity.this, bookConfirmLibrary.class);

                    // add the data to send to the next screen onto the intent as "extras"
                    intent.putExtra("bookingName", libraryComputerClicked.getComputerName());
                    intent.putExtra("floor", bookingfloor);
                    intent.putExtra("startTime", bookingStartTime);
                    intent.putExtra("endTime", bookingEndTime);
                    intent.putExtra("date", bookingDate);
                    intent.putExtra("booking_type_id", libraryComputerClicked.getComputerId());
                    intent.putExtra("BOOKING_KEY", "LibraryComputer");
                    intent.putExtra("booking_type", "Computer");

                    // start the next activity
                    startActivity(intent);
                }
                else{
                    Log.i("Error has occured"," booking not available");
                }

            }
        });


        exitChooseBookingButton.setOnClickListener(new View.OnClickListener() {
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


    public ArrayList<libraryComputer> createComputer(String starTime, String endTime, String Date, int floor) {
        dbManager.open();
        Cursor cursor;
        Cursor cursor2;
        ArrayList<libraryComputer> libraryComputers = new ArrayList<libraryComputer>();
        cursor = dbManager.getComputer(floor);
        String validationString ="Unavailable";

        if (cursor.moveToFirst()) {
            do {
                cursor2 = dbManager.validateComputerBooking(starTime, endTime, Date, cursor.getInt(0));

                if (cursor2.getCount() >= 1) {
                    cursor2.moveToFirst();

                    Log.i("This computer booked found id is ", Integer.toString(cursor.getInt(0)));
                    validationString = "Unavailable";
                } else
                {
                    validationString = "Available";
                }

                libraryComputer thisLibraryComputer = new libraryComputer(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        validationString);
                libraryComputers.add(thisLibraryComputer);
            }

            while (cursor.moveToNext());
        }


        dbManager.close();


        return libraryComputers;
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
                v = inflater.inflate(R.layout.library_booking, null);
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
            else{
                Log.i("Computer in adapter", "is null");
            }
            return v;
        }
    }
}