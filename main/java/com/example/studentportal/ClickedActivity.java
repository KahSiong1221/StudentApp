package com.example.studentportal;


import android.content.Intent;
import android.database.Cursor;
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

public class ClickedActivity extends AppCompatActivity {

    // variables for the side menu drawer menu
    DrawerLayout dl;
    NavigationView nav;
    Toolbar tb;

    //other variables for visual elements and the dummy user data
    Button joinbutton;
    int uid = 12345;
    String num = "0851111111";
    TextView name;
    String username = "Alex";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clicked_screen);
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

        //getting society name from previous intent
        Intent intent = getIntent();
        String sn = intent.getStringExtra("socname");

        //set textview as the society name selected
        TextView name = (TextView) findViewById(R.id.socname);
        name.setText(sn);

        //open dbmanager
        DBManager myDB = new DBManager(this);
        myDB.open();

        //getting the id of current soc using socname
        Cursor socids = myDB.getSocId(sn);
        socids.moveToFirst();
        int socid = socids.getInt(0);

        //on click listener for join society button
        Button joinbutton = (Button)findViewById(R.id.joinbutton);
        joinbutton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //add an enrollment for the current user and this society, using the
                // user id, society id and contact number
                enrollment enr = new enrollment(uid,socid,num);
                myDB.addEnr(enr);
                //back to menu
                Intent intent = new Intent(getApplicationContext(), SocsPortal.class);
                startActivity(intent);
            }
        });


    }
}
