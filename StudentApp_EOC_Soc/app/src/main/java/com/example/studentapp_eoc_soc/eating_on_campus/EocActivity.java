package com.example.studentapp_eoc_soc.eating_on_campus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.studentapp_eoc_soc.MainActivity;
import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.SaDbHelper;
import com.example.studentapp_eoc_soc.soc_portal.SocPortalActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

// reference: https://medium.com/@CodyEngel/4-ways-to-implement-onclicklistener-on-android-9b956cbd2928
public class EocActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EocDbManager eocDb;
    Spinner restSpinner;
    ExpandableListView menuExpListView;
    ImageButton favButton;
    public static ArrayList<Integer> favFoodIds;
    FoodMenuExpListAdapter foodMenuExpListAdapter;

    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    // eating on campus -> favourite food list button: click
    View.OnClickListener favButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(EocActivity.this, FavFoodActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eoc);

        // initialise eating on campus db manager
        eocDb = new EocDbManager(this);
        // initialise favourite food id list
        favFoodIds = new ArrayList<>();

        // restaurants drop down spinner
        restSpinner = findViewById(R.id.restSpinner);
        restSpinner.setOnItemSelectedListener(this);

        // eating on campus -> favourite food list button
        favButton = findViewById(R.id.favButton);
        favButton.setOnClickListener(favButtonOnClickListener);

        // initialise side drawer menu
        loadSideMenu();

        // initialise restaurants drop down spinner
        loadRestaurants();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // notify food menu list for data set changed
        if (foodMenuExpListAdapter != null) {
            foodMenuExpListAdapter.notifyDataSetChanged();
        }
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
                    // eating on campus -> home page
                    case R.id.home:
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // eating on campus
                    case R.id.food:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // eating on campus -> society portal
                    case R.id.soc:
                        intent = new Intent(getApplicationContext(), SocPortalActivity.class);
                        startActivity(intent);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }

    private void loadRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        // set a default value for spinner
        Restaurant defaultRest = new Restaurant();
        defaultRest.setRestName("Select Restaurant...");
        restaurants.add(0, defaultRest);

        eocDb.open();

        Cursor myCursor = eocDb.getAllRestaurants();

        if (myCursor.moveToFirst()) {
            do {
                // add restaurant to the list
                restaurants.add(new Restaurant(
                        myCursor.getInt(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_REST_ID)),
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_NAME)),
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_ADDRESS)),
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_PHONE_NO)),
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_OPEN_TIME)),
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_CLOSE_TIME))
                ));
            } while (myCursor.moveToNext());
        }

        // instantiate restaurants drop down list
        RestSpinnerAdapter restSpinnerAdapter = new RestSpinnerAdapter(this, android.R.layout.simple_spinner_item, restaurants);
        restSpinner.setAdapter(restSpinnerAdapter);
        restSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myCursor.close();
        eocDb.close();
    }

    private void loadMenus(int restId) {
        ArrayList<String> foodCategories = new ArrayList<>(Arrays.asList("Breakfast", "Lunch", "Snack", "Beverage"));
        HashMap<String, ArrayList<FoodItem>> foodMenus = new HashMap<>();

        eocDb.open();

        Cursor myCursor = eocDb.getFoodByRestId(restId);

        if (myCursor.moveToFirst()) {
            do {
                FoodItem food = new FoodItem(
                        myCursor.getInt(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_FOOD_ID)),
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_NAME)),
                        myCursor.getFloat(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_PRICE)),
                        myCursor.getInt(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_VEGAN)) == 1,
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_CATEGORY))
                );

                // add food item to food menu, if no key(food category) present -> create one
                foodMenus.computeIfAbsent(food.getCategory(), k -> new ArrayList<>()).add(food);

            } while (myCursor.moveToNext());
        }

        // initialise favourite food id list
        loadFavFoodItems();

        // instantiate food menu list
        menuExpListView = findViewById(R.id.menuExpListView);
        foodMenuExpListAdapter = new FoodMenuExpListAdapter(foodCategories, foodMenus);
        menuExpListView.setAdapter(foodMenuExpListAdapter);

        myCursor.close();
        eocDb.close();
    }

    private void loadFavFoodItems() {
        eocDb.open();

        Cursor favCursor = eocDb.getFavItemByUserId(MainActivity.user.getUserId());

        if (favCursor.moveToFirst()) {
            do {
                int thisFoodId = favCursor.getInt(favCursor.getColumnIndexOrThrow(SaDbHelper.KEY_FOOD_ID));
                // if its not existing in list, add favourite food id to list
                if (!favFoodIds.contains(thisFoodId)) {
                    favFoodIds.add(thisFoodId);
                }
            } while (favCursor.moveToNext());
        }

        favCursor.close();
        eocDb.close();
    }

    // if a restaurant is selected, show its food menu
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Restaurant selectedRest = (Restaurant) adapterView.getItemAtPosition(position);
        // ignore the default restaurant(hint)
        if (position > 0) {
            // initialise restaurant address
            TextView restAddress = findViewById(R.id.restAddress);
            restAddress.setText(String.format("   %s", selectedRest.getAddress()));
            // initialise restaurant phone number
            TextView restPhoneNo = findViewById(R.id.restPhoneNo);
            restPhoneNo.setText(String.format("   (+353) %s", selectedRest.getPhoneNo()));
            // initialise restaurant operating hour
            TextView restOperatingHour = findViewById(R.id.restOperatingHour);
            restOperatingHour.setText(String.format("   Operating Hour: %s - %s", selectedRest.getOpenTime(), selectedRest.getCloseTime()));

            // initialise food menu by the selected restaurant
            loadMenus(selectedRest.getRestId());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}