package com.example.studentapp_eoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// reference: https://medium.com/@CodyEngel/4-ways-to-implement-onclicklistener-on-android-9b956cbd2928
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EocDbManager eocDb;
    static User user;
    Spinner restSpinner;
    ExpandableListView menuExpListView;
    ImageButton favButton;
    static ArrayList<Integer> favFoodIds;
    MenuExpListAdapter menuExpListAdapter;

    View.OnClickListener favButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            favButtonClicked();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eocDb = new EocDbManager(this);

        restSpinner = findViewById(R.id.restSpinner);
        menuExpListView = findViewById(R.id.menuExpListView);
        favButton = findViewById(R.id.favButton);

        restSpinner.setOnItemSelectedListener(this);
        favButton.setOnClickListener(favButtonOnClickListener);

        favFoodIds = new ArrayList<>();
        user = new User(1, "Robot Siong");
        // insertDummyData();
        loadRestaurants();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (menuExpListAdapter != null) {
            menuExpListAdapter.notifyDataSetChanged();
        }
    }

    private void insertDummyData() {
        eocDb.open();

        // user
        user = new User("Robot Siong");
        // restaurants
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
        // food items
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

        // insert user
        user.setUserId(eocDb.insertUser(user));
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
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_REST_NAME)),
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
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_FOOD_NAME)),
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

        Cursor favCursor = eocDb.getFavItemByUserId(user.getUserId());

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
            loadMenus(selectedRest.getRestId());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private void favButtonClicked() {
        Intent intent = new Intent(MainActivity.this, FavFoodActivity.class);
        startActivity(intent);
    }
}