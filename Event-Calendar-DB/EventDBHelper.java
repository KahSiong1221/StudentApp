package com.example.mse_ca;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "Events";

    //Events Tables
    private static final String DB_Event_Table = "Event";
    private static final String DB_Event_Entry_Table = "Event_Entry";
    private static final String DB_User_Table = "User";

    //Common Column
    private static final String Event_Entry_ID = "Entry_ID";
    private static final String User_ID = "User_ID";
    private static final String DB_ID = "DB_ID";

    //Events Columns
    private static final String Event_ID = "Event_ID";

    //User Columns
    private static final String User_Name = "name";

    //Event Entry Columns
    private static final String Event_Start_Time = "Start_Time";
    private static final String Event_End_Time = "End_Time";
    private static final String Event_Date = "Date";
    private static final String Event_Location = "Location";
    private static final String Event_Name = "Name";

    //Create Statement Strings

    private static final String DB_CREATE_EVENTS_TABLE = "CREATE TABLE " + DB_Event_Table + " (" +
            DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Event_ID + " INTEGER NOT NULL, " +
            Event_Entry_ID + " INTEGER NOT NULL, " +
            User_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY (" + Event_Entry_ID + ") REFERENCES " + DB_Event_Entry_Table + " (" + Event_Entry_ID + "), " +
            "FOREIGN KEY (" + User_ID + ") REFERENCES " + DB_User_Table + " (" + User_ID + "));";

    private static final String DB_CREATE_Event_Entry_Table = "CREATE TABLE " + DB_Event_Entry_Table + " (" +
            DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Event_Entry_ID + " INTEGER NOT NULL, " +
            Event_Name + " TEXT NOT NULL, " +
            Event_Start_Time + " TEXT NOT NULL, " +
            Event_End_Time + " TEXT NOT NULL, " +
            Event_Date + " TEXT," +
            Event_Location + " TEXT NOT NULL, " +
            User_ID + " TEXT NOT NULL, " +
            "FOREIGN KEY (" + User_ID + ") REFERENCES " + DB_User_Table + " (" + User_ID + "));";

    private static final String DB_CREATE_User_Table = "CREATE TABLE " + DB_User_Table + " (" +
            DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            User_ID + " INTEGER NOT NULL, " +
            User_Name + " TEXT NOT NULL " + ");";


    //Constructor function
    public EventDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_EVENTS_TABLE);
        db.execSQL(DB_CREATE_Event_Entry_Table);
        db.execSQL(DB_CREATE_User_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_Event_Table);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Event_Entry_Table);
        db.execSQL("DROP TABLE IF EXISTS " + DB_User_Table);
        onCreate(db);
    }
}