package com.example.studentapp_eoc_soc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.studentapp_eoc_soc.calendar.EventMainActivity;
import com.example.studentapp_eoc_soc.eating_on_campus.EocActivity;
import com.example.studentapp_eoc_soc.eating_on_campus.EocDbManager;
import com.example.studentapp_eoc_soc.eating_on_campus.FoodItem;
import com.example.studentapp_eoc_soc.eating_on_campus.Restaurant;
//import com.example.studentapp_eoc_soc.gym_portal.GymMain;
import com.example.studentapp_eoc_soc.gym_portal.GymInfo;
import com.example.studentapp_eoc_soc.gym_portal.Menu;
import com.example.studentapp_eoc_soc.library.lib_DbManager;
import com.example.studentapp_eoc_soc.library.libraryMainActivity;
import com.example.studentapp_eoc_soc.soc_portal.SocDbManager;
import com.example.studentapp_eoc_soc.soc_portal.SocPortalActivity;
import com.example.studentapp_eoc_soc.soc_portal.Society;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public  static final String SHARED_PREFS = "shared_prefs";

    public  static final String USER_ID_KEY = "user_key";

    private int user_id;
    SharedPreferences sharedPreferences;

    public static User user;
    SaDbManager saDb;
    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView greetingText;
    Button eocButton;
    Button socButton;
    Button gymButton;
    Button calButton;
    Button libButton;

    // home page -> eating on campus button: click
    View.OnClickListener eocButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getApplicationContext(), EocActivity.class);
            startActivity(intent);
        }
    };
    // home page -> society portal button: click
    View.OnClickListener socButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getApplicationContext(), SocPortalActivity.class);
            startActivity(intent);
        }
    };

    // home page -> calendar button: click
    View.OnClickListener calButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getApplicationContext(), EventMainActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener gymButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Menu.class);
            startActivity(intent);
        }
    };

    // home page -> gym button: click
    View.OnClickListener libButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), libraryMainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check if the dummy db is initialise, insert dummy data when first time running app
        initDummyDB();

        // initialise greeting text
        greetingText = findViewById(R.id.greetingText);
        greetingText.setText(String.format("Hello, %s!", user.getUserName()));

        // home page -> eating on campus button
        eocButton = findViewById(R.id.eocButton);
        eocButton.setOnClickListener(eocButtonOnClickListener);
        // home page -> society portal button
        socButton = findViewById(R.id.socButton);
        socButton.setOnClickListener(socButtonOnClickListener);
        //home page -> calendar
        calButton = findViewById(R.id.calButton);
        calButton.setOnClickListener(calButtonListener);
        //home page -> gym
        gymButton = findViewById(R.id.gymButton);
        gymButton.setOnClickListener(gymButtonListener);

        //home page -> library
        libButton = findViewById(R.id.libButton);
        libButton.setOnClickListener(libButtonListener);


        // initialise side drawer menu
        loadSideMenu();
    }

    // reference: https://www.youtube.com/watch?v=fAXeq5F-CjI
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

                    case R.id.library:
                        intent = new Intent(getApplicationContext(), libraryMainActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.gym:
                        intent = new Intent(getApplicationContext(), Menu.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }

    private void initDummyDB() {
        // initialise student app db manager
        saDb = new SaDbManager(this);
        saDb.open();
        // find the only dummy user in database
        Cursor myCursor = saDb.findUserByUserName("Robot AlexSiong");

        // if user found, the database was initialised
        if (myCursor.moveToFirst()) {
            // initialise the only dummy user
            user = new User(
                    myCursor.getInt(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_USER_ID)),
                    myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_NAME)),
                    myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_EMAIL)),
                    myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_PHONE_NO)),
                    myCursor.getInt(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_MEMCHECK))
            );
        } else {
            // insert dummy data
            insertDummyUser();
            insertDummyDataForEOC();
            insertDummyDataForSoc();
            insertDummyDataForLibrary();
        }

        myCursor.close();
        saDb.close();
    }

    private void insertDummyUser() {
        saDb.open();

        // insert dummy user and retrieve auto generated id
        user = new User("Robot AlexSiong", "1010101@freeuni.ie", "0837778888", 0);
        user.setUserId(saDb.insertUser(user));

        saDb.close();
    }

    private void insertDummyDataForEOC() {
        // instantiate eating on campus db manager
        EocDbManager eocDb = new EocDbManager(this);
        eocDb.open();

        // dummy restaurants
        Restaurant r1 = new Restaurant(
                "Rathdown House Restaurant",
                "Rathdown House, TU Dublin, Grangegorman, Dublin",
                "012205000",
                "08:00",
                "15:00"
        );
        Restaurant r2 = new Restaurant(
                "1814 Restaurant",
                "Lower House, TU Dublin, Grangegorman, Dublin",
                "012205000",
                "10:30",
                "17:30"
        );
        Restaurant r3 = new Restaurant(
                "Aspretto Café",
                "Central Quad, TU Dublin, Grangegorman, Dublin",
                "012205000",
                "08:00",
                "16:00"
        );
        // dummy food items
        FoodItem fi_b1 = new FoodItem(
                "Croissant",
                (float) 1.00,
                true,
                "Breakfast"
        );
        FoodItem fi_b2 = new FoodItem(
                "Pain Au Chocolat",
                (float) 1.20,
                true,
                "Breakfast"
        );
        FoodItem fi_b3 = new FoodItem(
                "Scone",
                (float) 1.00,
                true,
                "Breakfast"
        );
        FoodItem fi_l1 = new FoodItem(
                "Fish & Chips",
                (float) 5.99,
                false,
                "Lunch"
        );
        FoodItem fi_l2 = new FoodItem(
                "Lasagne",
                (float) 5.99,
                false,
                "Lunch"
        );
        FoodItem fi_l3 = new FoodItem(
                "Chicken Fillet Roll",
                (float) 3.99,
                false,
                "Lunch"
        );
        FoodItem fi_l4 = new FoodItem(
                "Mixed Veg Roll",
                (float) 3.99,
                true,
                "Lunch"
        );
        FoodItem fi_s1 = new FoodItem(
                "Cadbury Crunchie 40g",
                (float) 0.99,
                true,
                "Snack"
        );
        FoodItem fi_s2 = new FoodItem(
                "Snikers Bar 40g",
                (float) 0.99,
                true,
                "Snack"
        );
        FoodItem fi_s3 = new FoodItem(
                "Cadbury Timeout Wafer 40g",
                (float) 0.79,
                true,
                "Snack"
        );
        FoodItem fi_d1 = new FoodItem(
                "Monster Ultra Zero 500ml",
                (float) 1.7,
                true,
                "Beverage"
        );
        FoodItem fi_d2 = new FoodItem(
                "Latte 16oz",
                (float) 2.5,
                true,
                "Beverage"
        );
        FoodItem fi_d3 = new FoodItem(
                "Americano 16oz",
                (float) 2.5,
                true,
                "Beverage"
        );

        // insert restaurants
        r1.setRestId(eocDb.insertRestaurant(r1));
        r2.setRestId(eocDb.insertRestaurant(r2));
        r3.setRestId(eocDb.insertRestaurant(r3));
        // insert food items
        fi_b1.setFoodId(eocDb.insertFoodItem(fi_b1));
        fi_b2.setFoodId(eocDb.insertFoodItem(fi_b2));
        fi_b3.setFoodId(eocDb.insertFoodItem(fi_b3));
        fi_l1.setFoodId(eocDb.insertFoodItem(fi_l1));
        fi_l2.setFoodId(eocDb.insertFoodItem(fi_l2));
        fi_l3.setFoodId(eocDb.insertFoodItem(fi_l3));
        fi_l4.setFoodId(eocDb.insertFoodItem(fi_l4));
        fi_s1.setFoodId(eocDb.insertFoodItem(fi_s1));
        fi_s2.setFoodId(eocDb.insertFoodItem(fi_s2));
        fi_s3.setFoodId(eocDb.insertFoodItem(fi_s3));
        fi_d1.setFoodId(eocDb.insertFoodItem(fi_d1));
        fi_d2.setFoodId(eocDb.insertFoodItem(fi_d2));
        fi_d3.setFoodId(eocDb.insertFoodItem(fi_d3));
        // insert menus
        eocDb.insertMenu(r1.getRestId(), fi_b1.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_b2.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_b3.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_l1.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_l2.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_l3.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_l4.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_s1.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_s2.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_s3.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_d1.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_d2.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_d3.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_b1.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_b2.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_b3.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_l1.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_l2.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_l3.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_l4.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_s1.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_s2.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_s3.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_d1.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_d2.getFoodId());
        eocDb.insertMenu(r2.getRestId(), fi_d3.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_b1.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_b2.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_b3.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_l1.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_l2.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_l3.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_l4.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_s1.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_s2.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_s3.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_d1.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_d2.getFoodId());
        eocDb.insertMenu(r3.getRestId(), fi_d3.getFoodId());
        // insert fav items
        eocDb.insertFavouriteItem(user.getUserId(), fi_b1.getFoodId());
        eocDb.insertFavouriteItem(user.getUserId(), fi_d1.getFoodId());

        eocDb.close();
    }

    private void insertDummyDataForSoc() {
        // instantiate eating on campus db manager
        SocDbManager socDb = new SocDbManager(this);
        socDb.open();

        // dummy societies
        Society soc1 = new Society("English Society");
        Society soc2 = new Society("CS++ Society");
        Society soc3 = new Society("Football Society");
        Society soc4 = new Society("Skate Club");
        Society soc5 = new Society("Live Music Society");
        Society soc6 = new Society("Basketball Society");

        // insert societies
        soc1.setSocId(socDb.insertSociety(soc1));
        soc2.setSocId(socDb.insertSociety(soc2));
        soc3.setSocId(socDb.insertSociety(soc3));
        soc4.setSocId(socDb.insertSociety(soc4));
        soc5.setSocId(socDb.insertSociety(soc5));
        soc6.setSocId(socDb.insertSociety(soc6));

        socDb.close();
    }

    private void insertDummyDataForLibrary(){

        lib_DbManager dbManager = new lib_DbManager(this);

        dbManager.open();


            sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

            user_id = sharedPreferences.getInt(USER_ID_KEY,0);
            Log.i("New User ID is :", Integer.toString(user_id));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(USER_ID_KEY,user.getUserId());
            editor.apply();

            dbManager.addComputer("LG-001",0,"Windows");
            dbManager.addComputer("LG-002",0,"Windows");
            dbManager.addComputer("LG-003",0,"Windows");
            dbManager.addComputer("LG-004",0,"Mac");
            dbManager.addComputer("LG-005",0,"Mac");

            dbManager.addComputer("CQ-101",1,"Windows");
            dbManager.addComputer("CQ-102",1,"Windows");
            dbManager.addComputer("CQ-103",1,"Windows");
            dbManager.addComputer("CQ-104",1,"Mac");
            dbManager.addComputer("CQ-105",1,"Mac");

            dbManager.addComputer("CQ-201",2,"Windows");
            dbManager.addComputer("CQ-202",2,"Windows");
            dbManager.addComputer("CQ-203",2,"Windows");
            dbManager.addComputer("CQ-204",2,"Mac");
            dbManager.addComputer("CQ-205",2,"Mac");

            dbManager.addRoom("EQ-001",0,"Narrow");
            dbManager.addRoom("EQ-002",0,"Wide");
            dbManager.addRoom("EQ-003",0,"Wide");
            dbManager.addRoom("EQ-004",0,"Narrow");
            dbManager.addRoom("EQ-005",0,"Narrow");

            dbManager.addRoom("EQ-101",1,"Spacious");
            dbManager.addRoom("EQ-102",1,"Spacious");
            dbManager.addRoom("EQ-103",1,"Wide");
            dbManager.addRoom("EQ-104",1,"Spacious");
            dbManager.addRoom("EQ-105",1,"Wide");

            dbManager.addRoom("EQ-201",2,"Spacious");
            dbManager.addRoom("EQ-202",2,"Narrow");
            dbManager.addRoom("EQ-203",2,"Wide");
            dbManager.addRoom("EQ-204",2,"Wide");
            dbManager.addRoom("EQ-205",2,"Spacious");


        dbManager.close();
    }
}