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
                Intent intent;

                switch(item.getItemId())
                {
                    case R.id.home:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.food:
                        intent = new Intent(getApplicationContext(), EocActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
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
}