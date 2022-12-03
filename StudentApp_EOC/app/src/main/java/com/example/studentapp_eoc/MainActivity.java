package com.example.studentapp_eoc;

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

public class MainActivity extends AppCompatActivity {

    static User user;
    SaDbManager saDb;

    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView greetingText;
    Button eocButton;

    View.OnClickListener eocButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getApplicationContext(), EocActivity.class);
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
        // initialise the side menu
        loadSideMenu();

        // greeting text
        greetingText = findViewById(R.id.greetingText);
        greetingText.setText(String.format("Hello, %s!", user.getUserName()));

        eocButton = findViewById(R.id.eocButton);
        eocButton.setOnClickListener(eocButtonOnClickListener);

    }

    private void loadSideMenu() {
        // side drawer menu
        // reference: https://www.youtube.com/watch?v=fAXeq5F-CjI
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

                switch(item.getItemId())
                {
                    case R.id.food:
                        Intent intent = new Intent(getApplicationContext(), EocActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
        // reference end
    }

    private void initDummyDB() {
        saDb = new SaDbManager(this);
        saDb.open();
        // find the only dummy user in database
        Cursor myCursor = saDb.findUserByUserName("Robot Siong");

        // if user found, the database was initialised
        if (myCursor.moveToFirst()) {
            // initialise the only dummy user
            user = new User(
                    myCursor.getInt(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_USER_ID)),
                    myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_NAME)),
                    myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_EMAIL)),
                    myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_PHONE_NO))
            );
        } else {
            // insert dummy data
            insertDummyUser();
            insertDummyDataForEOC();
        }

        myCursor.close();
        saDb.close();
    }


    private void insertDummyUser() {
        saDb.open();

        // insert dummy user and retrieve auto generated id
        user = new User("Robot Siong", "1010101@freeuni.ie", "0837778888");
        user.setUserId(saDb.insertUser(user));

        saDb.close();
    }

    private void insertDummyDataForEOC() {
        // instantiate eating on campus database manager
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
        FoodItem fi_s1 = new FoodItem(
                "Cadbury Crunchie 40g",
                (float) 0.99,
                true,
                "Snack"
        );
        FoodItem fi_d1 = new FoodItem(
                "Monster Ultra Zero 500ml",
                (float) 1.7,
                true,
                "Beverage"
        );

        // insert restaurants
        r1.setRestId(eocDb.insertRestaurant(r1));
        r2.setRestId(eocDb.insertRestaurant(r2));
        r3.setRestId(eocDb.insertRestaurant(r3));
        // insert food items
        fi_b1.setFoodId(eocDb.insertFoodItem(fi_b1));
        fi_l1.setFoodId(eocDb.insertFoodItem(fi_l1));
        fi_l2.setFoodId(eocDb.insertFoodItem(fi_l2));
        fi_s1.setFoodId(eocDb.insertFoodItem(fi_s1));
        fi_d1.setFoodId(eocDb.insertFoodItem(fi_d1));
        // insert menus
        eocDb.insertMenu(r1.getRestId(), fi_b1.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_l1.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_l2.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_s1.getFoodId());
        eocDb.insertMenu(r1.getRestId(), fi_d1.getFoodId());
        // insert fav items
        eocDb.insertFavouriteItem(user.getUserId(), fi_b1.getFoodId());
        eocDb.insertFavouriteItem(user.getUserId(), fi_d1.getFoodId());

        eocDb.close();
    }
}