package com.example.gymapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SaDbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "student_app";

    // eoc tables
    protected static final String DB_GYMSLOT_TABLE = "gymSlot";
    protected static final String DB_USER_TABLE = "user";
    protected static final String DB_USERBOOKINGS_TABLE = "userBookings";
    protected static final String DB_GYM_MEMBERSHIP_TABLE = "gymMembership";
    protected static final String DB_USER_MEMBERSHIP_TABLE = "userMembership";

    // gymSlot columns
    protected static final String KEY_GYMSLOT_ID = "gymSlot_id";
    protected static final String KEY_DAY = "day";
    protected static final String KEY_START_TIME = "start_time";
    protected static final String KEY_END_TIME = "end_time";
    protected static final String KEY_TYPE = "type";

    // user columns
    protected static final String KEY_USER_ID = "user_id";
    protected static final String KEY_NAME = "name";
    protected static final String KEY_EMAIL = "email";
    protected static final String KEY_MEMCHECK = "mem_check";

    // gym membership columns
    protected static final String KEY_GYM_MEMBERSHIP_ID = "membership_id";
    protected static final String KEY_MEMBERSHIP_TYPE = "mem_type";
    protected static final String KEY_PRICE = "mem_price";

    // user membership table uncommon columns
    protected static final String KEY_END_DATE = "mem_end_date";

    // create tables queries
    private static final String DB_CREATE_GYMSLOT_TABLE = "CREATE TABLE " + DB_GYMSLOT_TABLE + " (" +
            KEY_GYMSLOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DAY + " TEXT NOT NULL, " +
            KEY_START_TIME + " TEXT NOT NULL, " +
            KEY_END_TIME + " TEXT NOT NULL, " +
            KEY_TYPE + " TEXT NOT NULL);";

    private static final String DB_CREATE_USER_TABLE = "CREATE TABLE " + DB_USER_TABLE + " (" +
            KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_EMAIL + " TEXT NOT NULL UNIQUE, " +
            KEY_MEMCHECK + " INTEGER NOT NULL);";

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
        db.execSQL(DB_CREATE_GYMSLOT_TABLE);
        db.execSQL(DB_CREATE_USER_TABLE);
        db.execSQL(DB_CREATE_GYM_MEMBERSHIP_TABLE);
        db.execSQL(DB_CREATE_USERBOOKINGS_TABLE);
        db.execSQL(DB_CREATE_USER_MEMBERSHIP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}