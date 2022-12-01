package com.example.studentportal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SocPortalDB";
    public static final String TABLE_USER = "users" ;
    public static final String KEY_SID = "sid";
    public static final String KEY_SNAME = "sname";
    public static final String TABLE_SOCS = "socs";
    public static final String KEY_SOCID = "socid";
    public static final String KEY_SOCNAME = "socname";
    public static final String TABLE_ENR = "enr";
    public static final String KEY_ESID = "esid";
    public static final String KEY_ESOCID = "esocid";
    public static final String KEY_NUMBER = "num";

    public Helper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_SID + " INTEGER PRIMARY KEY," + KEY_SNAME + " TEXT" + ")";

        String CREATE_SOCS_TABLE = "CREATE TABLE " + TABLE_SOCS + "("
                + KEY_SOCID + " INTEGER PRIMARY KEY," + KEY_SOCNAME + " TEXT" + ")";

        String CREATE_ENR_TABLE = "CREATE TABLE " + TABLE_ENR + "("
                + KEY_ESOCID + " INTEGER PRIMARY KEY, "
                + KEY_ESID + " INTEGER, "
                +KEY_NUMBER+" TEXT "+")";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_SOCS_TABLE);
        db.execSQL(CREATE_ENR_TABLE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOCS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENR);

        // Create tables again
        onCreate(db);
    }
}
