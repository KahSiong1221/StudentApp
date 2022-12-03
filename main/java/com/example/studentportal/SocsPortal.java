package com.example.studentportal;



import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class SocsPortal extends AppCompatActivity {

    // variables for visual elements and the dummy user data
    // user name and phone numbers have been hardcoded throughout my app
    // to simulate a user being logged in, since login functionality is not required
    EditText input;
    Button search;
    TextView name;
    String username = "Alex";

    // variables for the side menu drawer menu
    DrawerLayout dl;
    NavigationView nav;
    Toolbar tb;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socsportal);
        input = (EditText) findViewById(R.id.input);
        search = (Button) findViewById(R.id.searchbut);
        //setting textview to user's name
        name = (TextView) findViewById(R.id.name);
        name.setText("Hello, "+username+"!");

        //REFERENCE https://www.youtube.com/watch?v=fAXeq5F-CjI
        dl = findViewById(R.id.drawer);
        nav = findViewById(R.id.navview);
        //defining the tgoggle button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl,  R.string.menu_Open, R.string.menu_Close);
        //set listener on the drawer layout from the toggle button
        dl.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setting listener for each item in the drawer menu to be clicked

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    //case statement for each opption picked to sqitch intents
                    //for my app this just works for the societies portal button
                    case R.id.soc:
                        Intent intent = new Intent(getApplicationContext(), SocsPortal.class);
                        startActivity(intent);
                        dl.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
        //reference complete


        //opening database
        DBManager myDB = new DBManager(this);
        myDB.open();

        //creating and inserting some societies
        Soc soc1 = new Soc(12345,"English Society");
        Soc soc2 = new Soc(11111,"CS++ Society");
        Soc soc3 = new Soc(22222,"Football Society");
        Soc soc4 = new Soc(33333,"Skate Club");
        Soc soc5 = new Soc(44444,"Live Music Society");
        Soc soc6 = new Soc(55555,"Basketball Society");
        myDB.addSoc(soc1);
        myDB.addSoc(soc2);
        myDB.addSoc(soc3);
        myDB.addSoc(soc4);
        myDB.addSoc(soc5);
        myDB.addSoc(soc6);

        // Reference: this particular method of passing strings with intents is based on
        // https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
        // i found that "lambda" (v ->) works better for sending a single value to another intent

        search.setOnClickListener(v ->
        {
            //get value from seacrh bar
            String str = input.getText().toString();
            //send this data to the new intent "search results" and switch to that activity
            Intent intent = new Intent(getApplicationContext(), SearchResults.class);
            intent.putExtra("socname", str);
            startActivity(intent);
        });
        //Reference complete

        //set onclicklistener for "manage societies" button
        Button view = (Button)findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //switch to the joined societies activity when this button is clicked
                Intent intent = new Intent(getApplicationContext(), JoinedSocs.class);
                startActivity(intent);
            }
        });

        //set onclicklistener for "visit the su" button
        Button location = (Button)findViewById(R.id.loc);
        location.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //switch to the map activity page when this button is clicked
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}