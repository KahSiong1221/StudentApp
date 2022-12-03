package com.example.studentapp_eoc;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class FavFoodActivity extends Activity {
    ImageButton backButton;
    ListView favFoodListView;

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

        favFoodListView = findViewById(R.id.favFoodList);

        backButton = findViewById(R.id.backToFoodMenuButton);
        backButton.setOnClickListener(backButtonOnClickListener);

        loadFavFoodItems();
    }

    private void loadFavFoodItems() {
        ArrayList<FoodItem> favFoodItems = new ArrayList<>();

        EocDbManager eocDb = new EocDbManager(this);
        eocDb.open();

        Cursor myCursor = eocDb.getFoodByUserId(EocActivity.user.getUserId());

        if (myCursor.moveToFirst()) {
            do {
                favFoodItems.add(new FoodItem(
                        myCursor.getInt(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_FOOD_ID)),
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_NAME)),
                        myCursor.getFloat(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_PRICE)),
                        myCursor.getInt(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_VEGAN)) == 1,
                        myCursor.getString(myCursor.getColumnIndexOrThrow(SaDbHelper.KEY_CATEGORY))
                ));
            } while (myCursor.moveToNext());
        }

        FavFoodListAdapter favFoodListAdapter = new FavFoodListAdapter(this, favFoodItems);
        favFoodListView.setAdapter(favFoodListAdapter);

        myCursor.close();
        eocDb.close();
    }
}
