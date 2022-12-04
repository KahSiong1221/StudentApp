package com.example.student_app;

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
    // Event tables
    private static final String DB_Event_Entry_Table = "Event_Entry";
    //Gym tables
    protected static final String DB_GYMSLOT_TABLE = "gymSlot";
    protected static final String DB_USERBOOKINGS_TABLE = "userBookings";
    protected static final String DB_GYM_MEMBERSHIP_TABLE = "gymMembership";
    protected static final String DB_USER_MEMBERSHIP_TABLE = "userMembership";

    // common columns
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE_NO = "phone_no";
    public static final String KEY_PRICE = "price";
    // user columns
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_EMAIL = "email";
    protected static final String KEY_MEMCHECK = "mem_check";
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
    //event columns
    private static final String Event_Entry_ID = "Entry_ID";
    private static final String DB_ID = "DB_ID";
    private static final String Event_Start_Time = "Start_Time";
    private static final String Event_End_Time = "End_Time";
    private static final String Event_Date = "Date";
    private static final String Event_Location = "Location";
    private static final String Event_Name = "Name";
    // gymSlot columns
    protected static final String KEY_GYMSLOT_ID = "gymSlot_id";
    protected static final String KEY_DAY = "day";
    protected static final String KEY_START_TIME = "start_time";
    protected static final String KEY_END_TIME = "end_time";
    protected static final String KEY_TYPE = "type";
    // gym membership columns
    protected static final String KEY_GYM_MEMBERSHIP_ID = "membership_id";
    protected static final String KEY_MEMBERSHIP_TYPE = "mem_type";
    protected static final String KEY_MEMBERSHIP_PRICE = "mem_price";
    // user membership table uncommon columns
    protected static final String KEY_END_DATE = "mem_end_date";

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

    private static final String DB_CREATE_Event_Entry_Table = "CREATE TABLE " + DB_Event_Entry_Table + " (" +
            DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Event_Entry_ID + " INTEGER NOT NULL, " +
            Event_Name + " TEXT NOT NULL, " +
            Event_Start_Time + " TEXT NOT NULL, " +
            Event_End_Time + " TEXT NOT NULL, " +
            Event_Date + " TEXT," +
            Event_Location + " TEXT NOT NULL, " +
            KEY_USER_ID + " TEXT NOT NULL, " +
            "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + DB_USER_TABLE + " (" + KEY_USER_ID + "));";

    private static final String DB_CREATE_GYMSLOT_TABLE = "CREATE TABLE " + DB_GYMSLOT_TABLE + " (" +
            KEY_GYMSLOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DAY + " TEXT NOT NULL, " +
            KEY_START_TIME + " TEXT NOT NULL, " +
            KEY_END_TIME + " TEXT NOT NULL, " +
            KEY_TYPE + " TEXT NOT NULL);";

    private static final String DB_CREATE_USERBOOKINGS_TABLE = "CREATE TABLE " + DB_USERBOOKINGS_TABLE + " (" +
            KEY_GYMSLOT_ID + " INTEGER NOT NULL, " +
            KEY_USER_ID + " INTEGER NOT NULL, " +
            KEY_TYPE + " TEXT NOT NULL);";

    private static final String DB_CREATE_GYM_MEMBERSHIP_TABLE = "CREATE TABLE " + DB_GYM_MEMBERSHIP_TABLE + " (" +
            KEY_GYM_MEMBERSHIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_MEMBERSHIP_TYPE + " TEXT NOT NULL, " +
            KEY_PRICE + " TEXT NOT NULL);";

    private static final String DB_CREATE_USER_MEMBERSHIP_TABLE = "CREATE TABLE " + DB_USER_MEMBERSHIP_TABLE + " (" +
            KEY_USER_ID + " INTEGER NOT NULL, " +
            KEY_GYM_MEMBERSHIP_ID + " INTEGER NOT NULL, " +
            KEY_END_DATE + " TEXT NOT NULL, " +
            "FOREIGN KEY (" + KEY_GYM_MEMBERSHIP_ID + ") REFERENCES " + DB_GYM_MEMBERSHIP_TABLE + " (" + KEY_GYM_MEMBERSHIP_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
            "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + DB_USER_TABLE + " (" + KEY_USER_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
            "PRIMARY KEY (" + KEY_USER_ID + "));";

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

        db.execSQL(DB_CREATE_Event_Entry_Table);

        db.execSQL(DB_CREATE_GYMSLOT_TABLE);
        db.execSQL(DB_CREATE_GYM_MEMBERSHIP_TABLE);
        db.execSQL(DB_CREATE_USERBOOKINGS_TABLE);
        db.execSQL(DB_CREATE_USER_MEMBERSHIP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
