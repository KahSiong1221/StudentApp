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

    EditText input;
    Button search;
    TextView name;
    String username = "Alex";
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
        name = (TextView) findViewById(R.id.name);
        name.setText("Hello, "+username+"!");

        //REFERENCE https://www.youtube.com/watch?v=fAXeq5F-CjI
        dl = findViewById(R.id.drawer);
        nav = findViewById(R.id.navview);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl,  R.string.menu_Open, R.string.menu_Close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.soc:
                        Intent intent = new Intent(getApplicationContext(), SocsPortal.class);
                        startActivity(intent);
                        dl.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
       //reference complete

        DBManager myDB = new DBManager(this);
        myDB.open();

        Soc soc1 = new Soc(12345,"English Society");
        Soc soc2 = new Soc(11111,"CS++ Society");
        Soc soc3 = new Soc(22222,"Football Society");
        Soc soc4 = new Soc(33333,"Skate Club");
        myDB.addSoc(soc1);
        myDB.addSoc(soc2);
        myDB.addSoc(soc3);
        myDB.addSoc(soc4);

        // Reference: this particular method of passing strings with intents is based on
        // https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/

        search.setOnClickListener(v ->
        {
            String str = input.getText().toString();
            Intent intent = new Intent(getApplicationContext(), SearchResults.class);
            intent.putExtra("socname", str);
            startActivity(intent);
        });
        //Reference complete


        Button view = (Button)findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), JoinedSocs.class);
                startActivity(intent);
            }
        });


        Button location = (Button)findViewById(R.id.loc);
        location.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}