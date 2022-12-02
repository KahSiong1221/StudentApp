package com.example.studentportal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SaDbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "studentAppDb";

    // common tables
    protected static final String DB_USER_TABLE = "user";
    // EatingOnCampus tables
    protected static final String DB_RESTAURANT_TABLE = "restaurant";
    protected static final String DB_FOODITEM_TABLE = "foodItem";
    protected static final String DB_MENU_TABLE = "menu";
    protected static final String DB_FAVITEM_TABLE = "favItem";
    // SocietiesPortal tables
    protected static final String DB_SOCIETY_TABLE = "society";
    protected static final String DB_SOC_ENROLMENT_TABLE = "socEnrolment";
    // Gym tables
    protected static final String DB_GYM_SLOT_TABLE = "gymSlot";
    protected static final String DB_GYM_MEMBERSHIP_TABLE = "gymMembership";
    protected static final String DB_GYM_MEMBER_TABLE = "gymMember";
    /* TODO: Calendar tables
    protected static final String DB_EVENT_TABLE = "event";
    protected static final String DB_EVENT_ENTRY_TABLE = "eventEntry";
    */

    // common columns
    protected static final String KEY_NAME = "name";
    protected static final String KEY_PHONE_NO = "phone_no";
    protected static final String KEY_DATE = "date";
    protected static final String KEY_START_TIME = "start_time";
    protected static final String KEY_END_TIME = "end_time";
    protected static final String KEY_PRICE = "price";
    protected static final String KEY_TYPE = "type";
    // user columns
    protected static final String KEY_USER_ID = "user_id";
    protected static final String KEY_EMAIL = "email";
    // restaurant columns
    protected static final String KEY_REST_ID = "rest_id";
    protected static final String KEY_ADDRESS = "address";
    protected static final String KEY_OPEN_TIME = "open_time";
    protected static final String KEY_CLOSE_TIME = "close_time";
    // foodItem columns
    protected static final String KEY_FOOD_ID = "food_id";
    protected static final String KEY_VEGAN = "vegan";  // boolean: is the food vegan friendly
    protected static final String KEY_CATEGORY = "category";    // enum {Breakfast, Lunch, Snack, Beverage}
    // society columns
    protected static final String KEY_SOC_ID = "soc_id";
    // soc enrolment columns
    protected static final String KEY_ENROL_USER_CONTACT = "user_contact";
    // gym slot columns
    protected static final String KEY_GYM_SLOT_ID = "gym_slot_id";
    // gym membership columns
    protected static final String KEY_GYM_MEMBERSHIP_ID = "gym_mem_id";
    // gym member columns
    protected static final String KEY_END_DATE = "end_date";

    /* TODO: event columns
    protected static final String KEY_EVENT_ID = "event_id";
    protected static final String KEY_LOCATION = "location";
    protected static final String KEY_SOURCE = "source";    // enum {Gym, Library, SocietyEvent, ...}
    TODO: event entry columns
    */


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
    private static final String DB_CREATE_GYM_SLOT_TABLE = "CREATE TABLE " + DB_GYM_SLOT_TABLE + " (" +
            KEY_GYM_SLOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE + " TEXT NOT NULL, " +
            KEY_START_TIME + " TEXT NOT NULL, " +
            KEY_END_TIME + " TEXT NOT NULL, " +
            KEY_TYPE + " TEXT NOT NULL);";
    private static final String DB_CREATE_GYM_MEMBERSHIP_TABLE = "CREATE TABLE " + DB_GYM_MEMBERSHIP_TABLE + " (" +
            KEY_GYM_MEMBERSHIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_TYPE + " TEXT NOT NULL, " +
            KEY_PRICE + " FLOAT NOT NULL);";
    private static final String DB_CREATE_GYM_MEMBER_TABLE = "CREATE TABLE " + DB_GYM_MEMBER_TABLE + " (" +
            KEY_USER_ID + " INTEGER NOT NULL, " +
            KEY_GYM_MEMBERSHIP_ID + " INTEGER NOT NULL, " +
            KEY_END_DATE + " TEXT NOT NULL, " +
            "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + DB_USER_TABLE + " (" + KEY_USER_ID + "), " +
            "FOREIGN KEY (" + KEY_GYM_MEMBERSHIP_ID + ") REFERENCES " + DB_GYM_MEMBERSHIP_TABLE + " (" + KEY_GYM_MEMBERSHIP_ID + "), " +
            "PRIMARY KEY (" + KEY_USER_ID + ", " + KEY_GYM_MEMBERSHIP_ID + "));";

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

        db.execSQL(DB_CREATE_GYM_SLOT_TABLE);
        db.execSQL(DB_CREATE_GYM_MEMBERSHIP_TABLE);
        db.execSQL(DB_CREATE_GYM_MEMBER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}