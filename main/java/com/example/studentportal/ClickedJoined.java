package com.example.studentportal;


import android.content.Intent;
import android.database.Cursor;
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

public class ClickedJoined extends AppCompatActivity {

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
        setContentView(R.layout.clicked_joined);
        //setting textview to user's name
        name =  findViewById(R.id.name);
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

        //getting society name and society id from previous intent
        Intent intent = getIntent();
        String sn = intent.getStringExtra("socname");


        //set textview as the society name selected
        TextView name = findViewById(R.id.socname);
        name.setText(sn);

        //open new dbmanager
        DBManager myDB = new DBManager(this);
        myDB.open();

        //get society id of selected society using getSocId
        Cursor socids = myDB.getSocId(sn);
        socids.moveToFirst();
        int socid = socids.getInt(0);

        //set listener on leave soc button
        Button leave = findViewById(R.id.leavebutton);
        leave.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //run delete enrollment query using the society id
                myDB.deleteEnr(socid);
                //back to menu
                Intent intent = new Intent(getApplicationContext(), SocsPortal.class);
                startActivity(intent);
            }
        });

        //defining visual elements
        EditText edit =  findViewById(R.id.updateedit);
        Button update = findViewById(R.id.updatebutton);
        //set listener on update button
        update.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //run the update query using society id and the new number
                String str = edit.getText().toString();
                myDB.updateEnr(socid, str);
                //back to menu
                Intent intent = new Intent(getApplicationContext(), SocsPortal.class);
                startActivity(intent);

            }
        });


    }
}
