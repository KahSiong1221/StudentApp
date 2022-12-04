package com.example.studentapp_eoc_soc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SaDbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "studentAppDb";

    // common tables
    public static final String DB_USER_TABLE = "user";
    // EatingOnCampus tables
    public static final String DB_RESTAURANT_TABLE = "restaurant";
    public static final String DB_FOODITEM_TABLE = "foodItem";
    public static final String DB_MENU_TABLE = "menu";
    public static final String DB_FAVITEM_TABLE = "favItem";
    // SocietiesPortal tables
    public static final String DB_SOCIETY_TABLE = "society";
    public static final String DB_SOC_ENROLMENT_TABLE = "socEnrolment";

    // common columns
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE_NO = "phone_no";
    public static final String KEY_PRICE = "price";
    // user columns
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_EMAIL = "email";
    // restaurant columns
    public static final String KEY_REST_ID = "rest_id";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_OPEN_TIME = "open_time";
    public static final String KEY_CLOSE_TIME = "close_time";
    // foodItem columns
    public static final String KEY_FOOD_ID = "food_id";
    public static final String KEY_VEGAN = "vegan";  // boolean: is the food vegan friendly
    public static final String KEY_CATEGORY = "category";    // enum {Breakfast, Lunch, Snack, Beverage}
    // society columns
    public static final String KEY_SOC_ID = "soc_id";
    // soc enrolment columns
    public static final String KEY_ENROL_USER_CONTACT = "user_contact";

    // create tables queries
    private static final String DB_CREATE_USER_TABLE = "CREATE TABLE " + DB_USER_TABLE + " (" +
            KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_EMAIL + " TEXT NOT NULL, " +
            KEY_PHONE_NO + " TEXT NOT NULL);";
    private static final String DB_CREATE_RESTAURANT_TABLE = "CREATE TABLE " + DB_RESTAURANT_TABLE + " (" +
            KEY_REST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_ADDRESS + " TEXT NOT NULL, " +
            KEY_PHONE_NO + " TEXT NOT NULL, " +
            KEY_OPEN_TIME + " TEXT NOT NULL, " +
            KEY_CLOSE_TIME + " TEXT NOT NULL);";
    private static final String DB_CREATE_FOOD_ITEM_TABLE = "CREATE TABLE " + DB_FOODITEM_TABLE + " (" +
            KEY_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_PRICE + " FLOAT NOT NULL, " +
            KEY_VEGAN + " BOOLEAN NOT NULL, " +
            KEY_CATEGORY + " TEXT NOT NULL);";
    private static final String DB_CREATE_MENU_TABLE = "CREATE TABLE " + DB_MENU_TABLE + " (" +
            KEY_FOOD_ID + " INTEGER NOT NULL, " +
            KEY_REST_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY (" + KEY_FOOD_ID + ") REFERENCES " + DB_FOODITEM_TABLE + " (" + KEY_FOOD_ID + "), " +
            "FOREIGN KEY (" + KEY_REST_ID + ") REFERENCES " + DB_RESTAURANT_TABLE + " (" + KEY_REST_ID + "), " +
            "PRIMARY KEY (" + KEY_FOOD_ID + ", " + KEY_REST_ID + "));";
    private static final String DB_CREATE_FAVITEM_TABLE = "CREATE TABLE " + DB_FAVITEM_TABLE + " (" +
            KEY_USER_ID + " INTEGER NOT NULL, " +
            KEY_FOOD_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + DB_USER_TABLE + " (" + KEY_USER_ID + "), " +
            "FOREIGN KEY (" + KEY_FOOD_ID + ") REFERENCES " + DB_FOODITEM_TABLE + " (" + KEY_FOOD_ID + "), " +
            "PRIMARY KEY (" + KEY_USER_ID + ", " + KEY_FOOD_ID + "));";
    private static final String DB_CREATE_SOCIETY_TABLE = "CREATE TABLE " + DB_SOCIETY_TABLE + " (" +
            KEY_SOC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL);";
    private static final String DB_CREATE_SOC_ENROLMENT_TABLE = "CREATE TABLE " + DB_SOC_ENROLMENT_TABLE + " (" +
            KEY_USER_ID + " INTEGER NOT NULL, " +
            KEY_SOC_ID + " INTEGER NOT NULL, " +
            KEY_ENROL_USER_CONTACT + " TEXT NOT NULL, " +
            "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + DB_USER_TABLE + " (" + KEY_USER_ID + "), " +
            "FOREIGN KEY (" + KEY_SOC_ID + ") REFERENCES " + DB_SOCIETY_TABLE + " (" + KEY_SOC_ID + "), " +
            "PRIMARY KEY (" + KEY_USER_ID + ", " + KEY_SOC_ID + "));";

    // constructor
    public SaDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_USER_TABLE);

        db.execSQL(DB_CREATE_RESTAURANT_TABLE);
        db.execSQL(DB_CREATE_FOOD_ITEM_TABLE);
        db.execSQL(DB_CREATE_MENU_TABLE);
        db.execSQL(DB_CREATE_FAVITEM_TABLE);

        db.execSQL(DB_CREATE_SOCIETY_TABLE);
        db.execSQL(DB_CREATE_SOC_ENROLMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
