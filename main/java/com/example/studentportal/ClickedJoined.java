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

    DrawerLayout dl;
    NavigationView nav;
    Toolbar tb;

    TextView name;
    String username = "Alex";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clicked_joined);
        name =  findViewById(R.id.name);
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


        Intent intent = getIntent();
        String sn = intent.getStringExtra("socname");
        int sid = intent.getIntExtra("socid",0);

        TextView name = findViewById(R.id.socname);
        name.setText(sn);

        DBManager myDB = new DBManager(this);
        myDB.open();

        Cursor socids = myDB.getSocId(sn);
        socids.moveToFirst();
        int socid = socids.getInt(0);

        Button leave = findViewById(R.id.leavebutton);
        leave.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                myDB.deleteEnr(socid);
                Intent intent = new Intent(getApplicationContext(), SocsPortal.class);
                startActivity(intent);
            }
        });


        EditText edit =  findViewById(R.id.updateedit);
        Button update = findViewById(R.id.updatebutton);
        update.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String str = edit.getText().toString();
                myDB.updateEnr(sid, str);
                Intent intent = new Intent(getApplicationContext(), SocsPortal.class);
                startActivity(intent);

            }
        });


    }
}
