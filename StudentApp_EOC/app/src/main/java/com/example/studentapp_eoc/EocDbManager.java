package com.example.studentapp_eoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class EocDbManager extends SaDbManager {
    public EocDbManager(Context context) {
        super(context);
    }

    public int insertRestaurant(Restaurant r) {
        ContentValues initValues = new ContentValues();
        initValues.put(SaDbHelper.KEY_REST_NAME, r.getRestName());
        initValues.put(SaDbHelper.KEY_ADDRESS, r.getAddress());
        initValues.put(SaDbHelper.KEY_PHONE_NO, r.getPhoneNo());
        initValues.put(SaDbHelper.KEY_OPEN_TIME, r.getOpenTime());
        initValues.put(SaDbHelper.KEY_CLOSE_TIME, r.getCloseTime());

        return (int) saDb.insert(SaDbHelper.DB_RESTAURANT_TABLE, null, initValues);
    }

    public int insertUser(User u) {
        ContentValues initValues = new ContentValues();
        initValues.put(SaDbHelper.KEY_USER_NAME, u.getUserName());

        return (int) saDb.insert(SaDbHelper.DB_USER_TABLE, null, initValues);
    }

    public int insertFoodItem(FoodItem f) {
        ContentValues initValues = new ContentValues();
        initValues.put(SaDbHelper.KEY_FOOD_NAME, f.getFoodName());
        initValues.put(SaDbHelper.KEY_PRICE, f.getPrice());
        initValues.put(SaDbHelper.KEY_VEGAN, f.isVegan());
        initValues.put(SaDbHelper.KEY_CATEGORY, f.getCategory());

        return (int) saDb.insert(SaDbHelper.DB_FOODITEM_TABLE, null, initValues);
    }

    public void insertFavouriteItem(int user_id, int food_id) {
        ContentValues initValues = new ContentValues();
        initValues.put(SaDbHelper.KEY_USER_ID, user_id);
        initValues.put(SaDbHelper.KEY_FOOD_ID, food_id);

        saDb.insert(SaDbHelper.DB_FAVITEM_TABLE, null, initValues);
    }

    public void insertMenu(int rest_id, int food_id) {
        ContentValues initValues = new ContentValues();
        initValues.put(SaDbHelper.KEY_FOOD_ID, food_id);
        initValues.put(SaDbHelper.KEY_REST_ID, rest_id);

        saDb.insert(SaDbHelper.DB_MENU_TABLE, null, initValues);
    }

    public Cursor getAllRestaurants() {
        return saDb.query(
                SaDbHelper.DB_RESTAURANT_TABLE,
                new String[] {
                        SaDbHelper.KEY_REST_ID,
                        SaDbHelper.KEY_REST_NAME,
                        SaDbHelper.KEY_ADDRESS,
                        SaDbHelper.KEY_PHONE_NO,
                        SaDbHelper.KEY_OPEN_TIME,
                        SaDbHelper.KEY_CLOSE_TIME
                },
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getFoodByRestId(int restId) {
        final String query = "SELECT " +
                SaDbHelper.KEY_FOOD_ID + ", " +
                SaDbHelper.KEY_FOOD_NAME + ", " +
                SaDbHelper.KEY_VEGAN + ", " +
                SaDbHelper.KEY_PRICE + ", " +
                SaDbHelper.KEY_CATEGORY + " FROM " +
                SaDbHelper.DB_MENU_TABLE + " INNER JOIN " +
                SaDbHelper.DB_FOODITEM_TABLE + " USING (" +
                SaDbHelper.KEY_FOOD_ID + ") WHERE " +
                SaDbHelper.KEY_REST_ID + " = ?";

        return saDb.rawQuery(query, new String[]{String.valueOf(restId)});
    }

    public Cursor getFavItemByUserId(int userId) {
        final String query = "SELECT " +
                SaDbHelper.KEY_FOOD_ID + " FROM " +
                SaDbHelper.DB_FAVITEM_TABLE + " WHERE " +
                SaDbHelper.KEY_USER_ID + " = ?";

        return saDb.rawQuery(query, new String[]{String.valueOf(userId)});
    }

    public void removeFavouriteItem(int userId, int foodId) {
        saDb.delete(
                SaDbHelper.DB_FAVITEM_TABLE,
                SaDbHelper.KEY_USER_ID + " = ? AND " + SaDbHelper.KEY_FOOD_ID + " = ?",
                new String[]{String.valueOf(userId), String.valueOf(foodId)}
        );
    }

    public Cursor getFoodByUserId(int userId) {
        final String query = "SELECT " +
                SaDbHelper.KEY_FOOD_ID + ", " +
                SaDbHelper.KEY_FOOD_NAME + ", " +
                SaDbHelper.KEY_VEGAN + ", " +
                SaDbHelper.KEY_PRICE + ", " +
                SaDbHelper.KEY_CATEGORY + " FROM " +
                SaDbHelper.DB_FAVITEM_TABLE + " INNER JOIN " +
                SaDbHelper.DB_FOODITEM_TABLE + " USING (" +
                SaDbHelper.KEY_FOOD_ID + ") WHERE " +
                SaDbHelper.KEY_USER_ID + " = ?";

        return saDb.rawQuery(query, new String[]{String.valueOf(userId)});
    }
}
