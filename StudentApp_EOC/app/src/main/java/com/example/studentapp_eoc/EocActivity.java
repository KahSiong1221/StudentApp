package com.example.studentapp_eoc;

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
    static ArrayList<Integer> favFoodIds;
    MenuExpListAdapter menuExpListAdapter;

    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    View.OnClickListener favButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            favButtonClicked();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eoc);

        eocDb = new EocDbManager(this);

        restSpinner = findViewById(R.id.restSpinner);
        menuExpListView = findViewById(R.id.menuExpListView);
        favButton = findViewById(R.id.favButton);

        restSpinner.setOnItemSelectedListener(this);
        favButton.setOnClickListener(favButtonOnClickListener);

        favFoodIds = new ArrayList<>();

        loadSideMenu();
        loadRestaurants();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (menuExpListAdapter != null) {
            menuExpListAdapter.notifyDataSetChanged();
        }
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
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.food:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
        // reference end
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

        RestSpinnerAdapter restSpinnerAdapter = new RestSpinnerAdapter(this, android.R.layout.simple_spinner_item, restaurants);

        restSpinner.setAdapter(restSpinnerAdapter);
        restSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myCursor.close();
        eocDb.close();
    }

    private void loadMenus(int restId) {
        ArrayList<String> foodCategories = new ArrayList<>(Arrays.asList("Breakfast", "Lunch", "Snack", "Beverage"));
        HashMap<String, ArrayList<FoodItem>> menus = new HashMap<>();

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

                menus.computeIfAbsent(food.getCategory(), k -> new ArrayList<>()).add(food);

            } while (myCursor.moveToNext());
        }

        loadFavFoodItems();

        menuExpListAdapter = new MenuExpListAdapter(foodCategories, menus);
        menuExpListView.setAdapter(menuExpListAdapter);

        myCursor.close();
        eocDb.close();
    }

    private void loadFavFoodItems() {
        eocDb.open();

        Cursor favCursor = eocDb.getFavItemByUserId(MainActivity.user.getUserId());

        if (favCursor.moveToFirst()) {
            do {
                int thisFoodId = favCursor.getInt(favCursor.getColumnIndexOrThrow(SaDbHelper.KEY_FOOD_ID));
                if (!favFoodIds.contains(thisFoodId)) {
                    favFoodIds.add(thisFoodId);
                }
            } while (favCursor.moveToNext());
        }

        favCursor.close();
        eocDb.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Restaurant selectedRest = (Restaurant) adapterView.getItemAtPosition(position);
        if (position > 0) {
            TextView restAddress = findViewById(R.id.restAddress);
            restAddress.setText(String.format("   %s", selectedRest.getAddress()));

            TextView restPhoneNo = findViewById(R.id.restPhoneNo);
            restPhoneNo.setText(String.format("   (+353) %s", selectedRest.getPhoneNo()));

            TextView restOperatingHour = findViewById(R.id.restOperatingHour);
            restOperatingHour.setText(String.format("   Operating Hour: %s - %s", selectedRest.getOpenTime(), selectedRest.getCloseTime()));

            loadMenus(selectedRest.getRestId());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private void favButtonClicked() {
        Intent intent = new Intent(EocActivity.this, FavFoodActivity.class);
        startActivity(intent);
    }
}