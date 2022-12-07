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
    // Event tables
    private static final String DB_Event_Entry_Table = "Event_Entry";
    //Gym tables
    public static final String DB_GYMSLOT_TABLE = "gymSlot";
    public static final String DB_USERBOOKINGS_TABLE = "userBookings";
    public static final String DB_GYM_MEMBERSHIP_TABLE = "gymMembership";
    public static final String DB_USER_MEMBERSHIP_TABLE = "userMembership";
    //Library tables
    public static final String DB_COMPUTER_BOOKING_TABLE = "libraryComputerBooking";
    public static final String DB_ROOM_BOOKING_TABLE = "libraryRooomBooking";
    public static final String DB_COMPUTER_TABLE = "libraryComputer";
    public static final String DB_ROOM_TABLE = "libraryRoom";

    // common columns
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE_NO = "phone_no";
    public static final String KEY_PRICE = "price";
    // user columns
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MEMCHECK = "mem_check";
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
    public static final String KEY_GYMSLOT_ID = "gymSlot_id";
    public static final String KEY_DAY = "day";
    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_TYPE = "type";
    // gym membership columns
    public static final String KEY_GYM_MEMBERSHIP_ID = "membership_id";
    public static final String KEY_MEMBERSHIP_TYPE = "mem_type";
    protected static final String KEY_MEMBERSHIP_PRICE = "mem_price";
    // user membership table uncommon columns
    public static final String KEY_END_DATE = "mem_end_date";

    //

    // computerBooking columns
    public static final String KEY_COMPUTER_BOOKING_ID = "computerBooking_id";
    public static final String KEY_COMPUTER_BOOKING_NAME = "computerBookingName";
    public static final String KEY_COMPUTER_BOOKING_START_TIME = "computerBookingStartTime";
    public static final String KEY_COMPUTER_BOOKING_END_TIME = "computerBookingEndTime";
    public static final String KEY_COMPUTER_BOOKING_DATE = "computerBookingDate";
    public static final String KEY_COMPUTER_BOOKING_STATUS = "computerBookingStatus";

    // roomBooking columns
    public static final String KEY_ROOM_BOOKING_ID = "roomBooking_id";
    public static final String KEY_ROOM_BOOKING_NAME = "roomBookingName";
    public static final String KEY_ROOM_BOOKING_START_TIME = "roomBookingStartTime";
    public static final String KEY_ROOM_BOOKING_END_TIME = "roomBookingEndTime";
    public static final String KEY_ROOM_BOOKING_DATE = "roomBookingDate";
    public static final String KEY_ROOM_BOOKING_STATUS = "roomBookingStatus";

    //Computer columns
    public static final String KEY_COMPUTER_ID = "computer_id"; // foreign key to computerbooking
    public static final String KEY_COMPUTER_NAME = "computerName";
    public static final String KEY_COMPUTER_FLOOR = "computerFloor";
    public static final String KEY_COMPUTER_DESC = "computerDesc";

    //room columnS
    public static final String KEY_ROOM_ID = "room_id"; // foreign key to computerbooking
    public static final String KEY_ROOM_NAME = "roomName";
    public static final String KEY_ROOM_FLOOR = "roomFloor";
    public static final String KEY_ROOM_DESC = "roomDesc"; // computerBooking columns



    // create tables queries
    private static final String DB_CREATE_USER_TABLE = "CREATE TABLE " + DB_USER_TABLE + " (" +
            KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_EMAIL + " TEXT NOT NULL, " +
            KEY_PHONE_NO + " TEXT NOT NULL, " +
            KEY_MEMCHECK + " INTEGER NOT NULL);";
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
            KEY_COMPUTER_BOOKING_STATUS + " TEXT NOT NULL, " +
            KEY_COMPUTER_ID + " INTEGER NOT NULL, " +
            KEY_USER_ID + " INTEGER NOT NULL, " +
            " FOREIGN KEY (" + KEY_COMPUTER_ID + ") REFERENCES " + DB_COMPUTER_TABLE + " (" + KEY_COMPUTER_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
            " FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + DB_USER_TABLE + " ( " + KEY_USER_ID + " ) ON DELETE CASCADE ON UPDATE CASCADE );";

    private static final String DB_CREATE_ROOM_BOOKING_TABLE = " CREATE TABLE " + DB_ROOM_BOOKING_TABLE + " ( " +
            KEY_ROOM_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_ROOM_BOOKING_NAME + " TEXT NOT NULL, " +
            KEY_ROOM_BOOKING_START_TIME + " TEXT NOT NULL, " +
            KEY_ROOM_BOOKING_END_TIME + " TEXT NOT NULL, " +
            KEY_ROOM_BOOKING_DATE + " TEXT NOT NULL, " +
            KEY_ROOM_BOOKING_STATUS + " TEXT NOT NULL, " +
            KEY_ROOM_ID + " INTEGER NOT NULL, " +
            KEY_USER_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY (" + KEY_ROOM_ID + ") REFERENCES " + DB_ROOM_TABLE + " (" + KEY_ROOM_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
            "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + DB_USER_TABLE + " (" + KEY_USER_ID + ") ON DELETE CASCADE ON UPDATE CASCADE );";



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

        db.execSQL(DB_CREATE_COMPUTER_TABLE);
        db.execSQL(DB_CREATE_ROOM_TABLE);
        db.execSQL(DB_CREATE_COMPUTER_BOOKING_TABLE);
        db.execSQL(DB_CREATE_ROOM_BOOKING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
