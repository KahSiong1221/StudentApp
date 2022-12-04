package com.example.studentapp_eoc_soc;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class JoinSocActivity extends AppCompatActivity {

    // variables for the side menu drawer menu
    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //other variables for visual elements
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_soc);
        //setting textview to user's name
        name = (TextView) findViewById(R.id.name);
        name.setText(String.format("Hello, %s!", MainActivity.user.getUserName()));

        // initialise side drawer menu
        loadSideMenu();

        //getting society name from previous intent
        Intent intent = getIntent();
        String sn = intent.getStringExtra("socname");

        //set textview as the society name selected
        TextView name = (TextView) findViewById(R.id.socname);
        name.setText(sn);

        //open dbmanager
        SocDbManager myDB = new SocDbManager(this);
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
                SocEnrolment enr = new SocEnrolment(MainActivity.user.getUserId(),socid,MainActivity.user.getPhoneNo());
                myDB.insertSocEnrolment(enr);
                //back to menu
                finish();
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
                    // join society page -> home page
                    case R.id.home:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        // close join society page & society portal
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // join society page -> society portal
                    case R.id.soc:
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // join society page -> eating on campus
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
