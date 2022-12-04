package com.example.libraryapp;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
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

    // common columns
    protected static final String KEY_NAME = "name";
    protected static final String KEY_PHONE_NO = "phone_no";
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
    protected static final String KEY_PRICE = "price";
    protected static final String KEY_VEGAN = "vegan";
    protected static final String KEY_CATEGORY = "category";
    // society columns
    protected static final String KEY_SOC_ID = "soc_id";
    // soc enrolment columns
    protected static final String KEY_ENROL_USER_CONTACT = "user_contact";


    protected static final String DB_COMPUTER_BOOKING_TABLE = "libraryComputerBooking";
    protected static final String DB_ROOM_BOOKING_TABLE = "libraryRooomBooking";
    protected static final String DB_COMPUTER_TABLE = "libraryComputer";
    protected static final String DB_ROOM_TABLE = "libraryRoom";


    // PRAGMA foreign_keys = OFF;

    // User table

    // computerBooking columns
    protected static final String KEY_COMPUTER_BOOKING_ID = "computerBooking_id";
    protected static final String KEY_COMPUTER_BOOKING_NAME = "computerBookingName";
    protected static final String KEY_COMPUTER_BOOKING_START_TIME = "computerBookingStartTime";
    protected static final String KEY_COMPUTER_BOOKING_END_TIME = "computerBookingEndTime";
    protected static final String KEY_COMPUTER_BOOKING_DATE = "computerBookingDate";
    protected static final String KEY_BOOKING_STATUS = "computerBookingStatus";

    // roomBooking columns
    protected static final String KEY_ROOM_BOOKING_ID = "roomBooking_id";
    protected static final String KEY_ROOM_BOOKING_NAME = "roomBookingName";
    protected static final String KEY_ROOM_BOOKING_START_TIME = "roomBookingStartTime";
    protected static final String KEY_ROOM_BOOKING_END_TIME = "roomBookingEndTime";
    protected static final String KEY_ROOM_BOOKING_DATE = "roomBookingDate";
    protected static final String KEY_ROOM_STATUS = "roomBookingStatus";

    //Computer columns
    protected static final String KEY_COMPUTER_ID = "computer_id"; // foreign key to computerbooking
    protected static final String KEY_COMPUTER_NAME = "computerName";
    protected static final String KEY_COMPUTER_FLOOR = "computerFloor";
    protected static final String KEY_COMPUTER_DESC = "computerDesc";

    //room columnS
    protected static final String KEY_ROOM_ID = "room_id"; // foreign key to computerbooking
    protected static final String KEY_ROOM_NAME = "roomName";
    protected static final String KEY_ROOM_FLOOR = "roomFloor";
    protected static final String KEY_ROOM_DESC = "roomDesc";



    // create tables queries
    /*
    private static final String DB_CREATE_USER_TABLE = "CREATE TABLE " + DB_USER_TABLE + " (" +
            KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_EMAIL + " TEXT NOT NULL, " +
            KEY_PHONE_NO + " TEXT NOT NULL );";
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


    private static final String DB_CREATE_COMPUTER_TABLE = "CREATE TABLE " + DB_COMPUTER_TABLE + " (" +
            KEY_COMPUTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_COMPUTER_NAME + " TEXT NOT NULL, " +
            KEY_COMPUTER_FLOOR + " INTEGER NOT NULL, " +
            KEY_COMPUTER_DESC + " TEXT NOT NULL );";

    private static final String DB_CREATE_ROOM_TABLE = "CREATE TABLE " + DB_ROOM_TABLE + " (" +
            KEY_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_ROOM_NAME + " TEXT NOT NULL, " +
            KEY_ROOM_FLOOR + " INTEGER NOT NULL, " +
            KEY_ROOM_DESC + " TEXT NOT NULL );";


    private static final String DB_CREATE_COMPUTER_BOOKING_TABLE = " CREATE TABLE " + DB_COMPUTER_BOOKING_TABLE + " (" +
            KEY_COMPUTER_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_COMPUTER_BOOKING_NAME+ " TEXT NOT NULL, " +
            KEY_COMPUTER_BOOKING_START_TIME + " TEXT NOT NULL, " +
            KEY_COMPUTER_BOOKING_END_TIME + " TEXT NOT NULL, " +
            KEY_COMPUTER_BOOKING_DATE + " TEXT NOT NULL, " +
            KEY_BOOKING_STATUS + " TEXT NOT NULL, " +
            KEY_COMPUTER_ID + " INTEGER NOT NULL, " +
            KEY_USER_ID + " INTEGER NOT NULL, " +
            " FOREIGN KEY (" + KEY_COMPUTER_ID + ") REFERENCES " + DB_COMPUTER_TABLE + " (" + KEY_COMPUTER_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
            " FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + DB_USER_TABLE + " ( " + KEY_USER_ID + " ) ON DELETE CASCADE ON UPDATE CASCADE );";

    private static final String DB_CREATE_ROOM_BOOKING_TABLE = " CREATE TABLE " + DB_ROOM_BOOKING_TABLE + " ( " +
            KEY_ROOM_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_ROOM_BOOKING_NAME+ " TEXT NOT NULL, " +
            KEY_ROOM_BOOKING_START_TIME + " TEXT NOT NULL, " +
            KEY_ROOM_BOOKING_END_TIME + " TEXT NOT NULL, " +
            KEY_ROOM_BOOKING_DATE + " TEXT NOT NULL, " +
            KEY_ROOM_STATUS + " TEXT NOT NULL, " +
            KEY_ROOM_ID + " INTEGER NOT NULL, " +
            KEY_USER_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY (" + KEY_ROOM_ID + ") REFERENCES " + DB_ROOM_TABLE + " (" + KEY_ROOM_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
            "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + DB_USER_TABLE + " (" + KEY_USER_ID + ") ON DELETE CASCADE ON UPDATE CASCADE );";



    // constructor
    public DatabaseHelper(Context context) {
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

        db.execSQL(DB_CREATE_COMPUTER_TABLE);
        db.execSQL(DB_CREATE_ROOM_TABLE);
        db.execSQL(DB_CREATE_COMPUTER_BOOKING_TABLE);
        db.execSQL(DB_CREATE_ROOM_BOOKING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

