package com.example.studentapp_eoc_soc;

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

import java.util.Objects;

public class SocPortalActivity extends AppCompatActivity {

    // variables for visual elements and the dummy user data
    // user name and phone numbers have been hardcoded throughout my app
    // to simulate a user being logged in, since login functionality is not required
    EditText input;
    Button search;
    TextView name;
    // variables for the side menu drawer menu
    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soc_portal);

        input = (EditText) findViewById(R.id.input);
        search = (Button) findViewById(R.id.searchbut);
        //setting textview to user's name
        name = (TextView) findViewById(R.id.name);
        name.setText(String.format("Hello, %s!", MainActivity.user.getUserName()));

        // initialise side drawer menu
        loadSideMenu();

        // Reference: this particular method of passing strings with intents is based on
        // https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
        // i found that "lambda" (v ->) works better for sending a single value to another intent
        search.setOnClickListener(v ->
        {
            //get value from seacrh bar
            String str = input.getText().toString();
            //send this data to the new intent "search results" and switch to that activity
            Intent intent = new Intent(getApplicationContext(), SocSearchActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), SocJoinedActivity.class);
                startActivity(intent);
            }
        });

        //set onclicklistener for "visit the su" button
        Button location = (Button)findViewById(R.id.loc);
        location.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                /* TODO
                //switch to the map activity page when this button is clicked
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                */
            }
        });
    }

    // reference: https://www.youtube.com/watch?v=fAXeq5F-CjI
    private void loadSideMenu() {
        drawerLayout = findViewById(R.id.drawer);
        navView = findViewById(R.id.navView);

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
                    // society portal -> home page
                    case R.id.home:
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // society portal
                    case R.id.soc:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // society portal -> eating on campus
                    case R.id.food:
                        intent = new Intent(getApplicationContext(), EocActivity.class);
                        startActivity(intent);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }
}