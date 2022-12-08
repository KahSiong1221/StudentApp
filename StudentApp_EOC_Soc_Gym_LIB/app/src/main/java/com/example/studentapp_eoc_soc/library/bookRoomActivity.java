package com.example.studentapp_eoc_soc.library;
import android.content.Context;
import android.content.Intent;
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
import com.example.studentapp_eoc_soc.R;

public class bookRoomActivity extends AppCompatActivity {

    ArrayList<libraryRoom> roombookings;
    private final lib_DbManager dbManager = new lib_DbManager(this);
    private roombookingAdapter myAdapter;
    private String bookingName;
    private String bookingfloor;
    private String bookingStartTime;
    private String bookingEndTime;
    private String bookingDate;
    private ListView lv;

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_display_room_booking);
        Button exitChooseBookingButton = (Button) findViewById(R.id.exitRoomBooking);
        TextView roomBookingdate = (TextView) findViewById(R.id.roomBookingDate);
        TextView roomBookingFloor = (TextView) findViewById(R.id.roomBookingFloor);
        lv = (ListView)findViewById(android.R.id.list);
        int bookingfloorInt;


        Bundle data = getIntent().getExtras();
        bookingfloor = data.getString("floor");
        bookingStartTime = data.getString("startTime");
        bookingEndTime = data.getString("endTime");
        bookingDate = data.getString("date");

        bookingfloorInt = Integer.valueOf(bookingfloor);

        String myString = bookingStartTime + "-" + bookingEndTime + "  " + bookingDate;
        roomBookingdate.setText(myString);
        roomBookingFloor.setText("Floor: " + bookingfloor);


        roombookings = createRoom(bookingStartTime, bookingEndTime, bookingDate, bookingfloorInt);
        myAdapter = new roombookingAdapter(this, R.layout.library_booking, roombookings);
        lv.setAdapter(myAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                libraryRoom libraryRoomClicked = (libraryRoom) adapterView.getItemAtPosition(i);

                // switching screens requires an intent
                if (libraryRoomClicked.getRoomStatus() == "Available") {
                    Intent intent = new Intent(bookRoomActivity.this, bookConfirmLibrary.class);

                    // add the data to send to the next screen onto the intent as "extras"
                    intent.putExtra("bookingName", libraryRoomClicked.getRoomName());
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
        });


        exitChooseBookingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    loadSideMenu();
    }

    public ArrayList<libraryRoom> createRoom(String starTime, String endTime, String Date, int floor) {
        dbManager.open();
        Cursor cursor;
        Cursor cursor2;
        ArrayList<libraryRoom> libraryRoom = new ArrayList<libraryRoom>();
        cursor = dbManager.getRoom(floor);

        if (cursor.moveToFirst()) {
            do {
                libraryRoom thisLibraryRoom = new libraryRoom(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        "Available");
                libraryRoom.add(thisLibraryRoom);
            }

            while (cursor.moveToNext());
        }

        Log.i("This  computer count", Integer.toString(cursor.getCount()));
        if (libraryRoom.size() >= 1) {
            for (libraryRoom thisLibraryRoom : libraryRoom) {
                cursor2 = dbManager.validateRoomBooking(starTime, endTime, Date, thisLibraryRoom.getRoomId());
                if (cursor2.getCount() > 1) {
                    thisLibraryRoom.setRoomStatus("Unavailable");
                }

            }
        }

        dbManager.close();
        return libraryRoom;
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



    class roombookingAdapter extends ArrayAdapter<libraryRoom> {

        ArrayList<libraryRoom> libraryRoom;

        public roombookingAdapter(Context context, int textViewresourceId, ArrayList<libraryRoom> objects) {
            super(context, textViewresourceId, objects);
            this.libraryRoom = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.library_booking, null);
            }

            libraryRoom currentLibraryRoom = libraryRoom.get(position);

            if (currentLibraryRoom != null) {
                Log.i("Computer in adapter", currentLibraryRoom.getRoomName());
                TextView bookingNameText = (TextView) v.findViewById(R.id.bookingNameView);
                TextView bookingDescText = (TextView) v.findViewById(R.id.bookingDescView);
                TextView bookingStatus = (TextView) v.findViewById(R.id.bookingStatusView);


                bookingNameText.setText("ID: " + currentLibraryRoom.getRoomName());
                bookingDescText.setText("OS: " + currentLibraryRoom.getRoomDesc());
                bookingStatus.setText(currentLibraryRoom.getRoomStatus());

                Bundle data = getIntent().getExtras();


            }
            return v;
        }
    }
}