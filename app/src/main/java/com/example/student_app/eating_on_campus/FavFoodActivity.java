package com.example.student_app.eating_on_campus;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.student_app.MainActivity;
import com.example.student_app.R;
import com.example.student_app.SaDbHelper;
import com.example.student_app.soc_portal.SocPortalActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class FavFoodActivity extends AppCompatActivity {

    ImageButton backButton;
    ListView favFoodListView;
    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    // favourite food list -> eating on campus button: click
    View.OnClickListener backButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_food);

        // favourite food list -> eating on campus button
        backButton = findViewById(R.id.backToFoodMenuButton);
        backButton.setOnClickListener(backButtonOnClickListener);

        // initialise side drawer menu
        loadSideMenu();

        // initialise favourite food list
        loadFavFoodItems();
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
                    // favourite food list -> home page
                    case R.id.home:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        // close favourite food list & eating on campus
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // favourite food list -> eating on campus
                    case R.id.food:
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // favourite food list -> society portal
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

    private void loadFavFoodItems() {
        ArrayList<FoodItem> favFoodItems = new ArrayList<>();

        // instantiate eating on campus db manager
        EocDbManager eocDb = new EocDbManager(this);
        eocDb.open();

        Cursor myCursor = eocDb.getFoodByUserId(MainActivity.user.getUserId());

        if (myCursor.moveToFirst()) {
            do {
                // add favourite food to list
                favFoodItems.add(new FoodItem(
                        myCursor.getInt(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_FOOD_ID)),
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_NAME)),
                        myCursor.getFloat(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_PRICE)),
                        myCursor.getInt(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_VEGAN)) == 1,
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_CATEGORY))
                ));
            } while (myCursor.moveToNext());
        }

        // instantiate favourite food list
        favFoodListView = findViewById(R.id.favFoodList);
        FavFoodListAdapter favFoodListAdapter = new FavFoodListAdapter(this, favFoodItems);
        favFoodListView.setAdapter(favFoodListAdapter);

        myCursor.close();
        eocDb.close();
    }
}
