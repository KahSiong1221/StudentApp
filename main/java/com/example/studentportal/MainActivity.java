package com.example.studentportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    // variables for the side menu drawer menu
    DrawerLayout dl;
    NavigationView nav;
    Toolbar tb;

    //other variables for visual elements and the dummy user data
    TextView name;
    String username = "Alex";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
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
                    //case statement for each option picked to switch intents
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

        //for the purpose of my standalone app, just the societies button works
        //define this button and set an OnClickListener on it
        Button socsbutton = (Button)findViewById(R.id.socsbutt);
        socsbutton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //switch to the socsportal when this button is clicked
                Intent intent = new Intent(getApplicationContext(), SocsPortal.class);
                startActivity(intent);
            }
        });
    }

}
