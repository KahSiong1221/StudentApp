package com.example.studentapp_eoc_soc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import com.example.studentapp_eoc_soc.calendar.CalendarFun;
import com.example.studentapp_eoc_soc.calendar.Event;
//import com.example.studentapp_eoc_soc.gym_portal.DayTimeslot;
//import com.example.studentapp_eoc_soc.gym_portal.Membership;

import static com.example.studentapp_eoc_soc.SaDbHelper.DB_GYMSLOT_TABLE;
import static com.example.studentapp_eoc_soc.SaDbHelper.DB_GYM_MEMBERSHIP_TABLE;
import static com.example.studentapp_eoc_soc.SaDbHelper.DB_USERBOOKINGS_TABLE;
import static com.example.studentapp_eoc_soc.SaDbHelper.DB_USER_MEMBERSHIP_TABLE;
import static com.example.studentapp_eoc_soc.SaDbHelper.DB_USER_TABLE;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_DAY;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_EMAIL;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_END_DATE;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_END_TIME;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_GYMSLOT_ID;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_GYM_MEMBERSHIP_ID;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_MEMBERSHIP_TYPE;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_MEMCHECK;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_NAME;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_PRICE;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_START_TIME;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_TYPE;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_USER_ID;

import static com.example.studentapp_eoc_soc.SaDbHelper.DB_COMPUTER_BOOKING_TABLE;
import static com.example.studentapp_eoc_soc.SaDbHelper.DB_COMPUTER_TABLE;
import static com.example.studentapp_eoc_soc.SaDbHelper.DB_ROOM_BOOKING_TABLE;
import static com.example.studentapp_eoc_soc.SaDbHelper.DB_ROOM_TABLE;
//import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_BOOKING_STATUS;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_COMPUTER_BOOKING_DATE;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_COMPUTER_BOOKING_END_TIME;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_COMPUTER_BOOKING_ID;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_COMPUTER_BOOKING_NAME;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_COMPUTER_BOOKING_START_TIME;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_COMPUTER_DESC;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_COMPUTER_FLOOR;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_COMPUTER_ID;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_COMPUTER_NAME;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_ROOM_BOOKING_DATE;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_ROOM_BOOKING_END_TIME;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_ROOM_BOOKING_ID;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_ROOM_BOOKING_NAME;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_ROOM_BOOKING_START_TIME;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_ROOM_DESC;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_ROOM_FLOOR;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_ROOM_ID;
import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_ROOM_NAME;
//import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_ROOM_STATUS;
//import static com.example.studentapp_eoc_soc.SaDbHelper.KEY_ROOM_BOOKING_STATUS;


public class SaDbManager {
    Context context;
    private SaDbHelper saDbHelper;
    protected SQLiteDatabase saDb;

    public SaDbManager(Context context) {
        this.context = context;
    }

    public SaDbManager open() throws SQLException {
        this.saDbHelper = new SaDbHelper(this.context);
        this.saDb = saDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        saDbHelper.close();
    }

    public int insertUser(User u) {
        ContentValues initValues = new ContentValues();
        initValues.put(SaDbHelper.KEY_NAME, u.getUserName());
        initValues.put(SaDbHelper.KEY_EMAIL, u.getEmail());
        initValues.put(SaDbHelper.KEY_PHONE_NO, u.getPhoneNo());
        initValues.put(KEY_MEMCHECK, u.getMemCheck());

        return (int) saDb.insert(SaDbHelper.DB_USER_TABLE, null, initValues);
    }

    public Cursor findUserByUserName(String userName) {
        return saDb.query(
                SaDbHelper.DB_USER_TABLE,
                new String[] {
                        SaDbHelper.KEY_USER_ID,
                        SaDbHelper.KEY_NAME,
                        SaDbHelper.KEY_EMAIL,
                        SaDbHelper.KEY_PHONE_NO,
                        SaDbHelper.KEY_MEMCHECK
                },
                SaDbHelper.KEY_NAME + " = ?",
                new String[]{userName},
                null,
                null,
                null
        );
    }

    public Cursor getComputerAvailability(String starTime, String endTime, String Date){
        final  String query = "SELECT "+
                "COUNT(*) " +
                "FROM " +
                DB_COMPUTER_BOOKING_TABLE +
                " WHERE " +
                KEY_COMPUTER_BOOKING_START_TIME + " = ?" +" AND "+
                KEY_COMPUTER_BOOKING_END_TIME + " = ?"  +" AND "+
                KEY_COMPUTER_BOOKING_DATE + " = ?;";

        return saDb.rawQuery(query,new String[]{starTime,endTime,Date});
    }


    public Cursor getRoomAvailability(String starTime, String endTime, String Date){
        final  String query = "SELECT "+
                "COUNT(*) " +
                "FROM " +
                DB_ROOM_BOOKING_TABLE +
                " WHERE " +
                KEY_ROOM_BOOKING_START_TIME + " = ?" +" AND "+
                KEY_ROOM_BOOKING_END_TIME + " = ?"  +" AND "+
                KEY_ROOM_BOOKING_DATE + " = ?;";

        return saDb.rawQuery(query,new String[]{starTime,endTime,Date});
    }


    public void addComputer(String Name, int Floor, String Desc){

        ContentValues values = new ContentValues();
        values.put(KEY_COMPUTER_NAME, Name); // get users name
        values.put(KEY_COMPUTER_FLOOR, Floor); // get users email
        values.put(KEY_COMPUTER_DESC, Desc); // get users email
        // Inserting Row
        saDb.insert(DB_COMPUTER_TABLE, null, values);

    }

    public Cursor getComputer(int floor){

        return saDb.query(
                DB_COMPUTER_TABLE, new String[]{
                        KEY_COMPUTER_ID,
                        KEY_COMPUTER_NAME,
                        KEY_COMPUTER_FLOOR,
                        KEY_COMPUTER_DESC
                },
                KEY_COMPUTER_FLOOR + " = " + floor,
                null,
                null,
                null,
                null
        );
    }


    public void addRoom(String Name, int Floor, String Desc){

        ContentValues values = new ContentValues();
        values.put(KEY_ROOM_NAME, Name); // get users name
        values.put(KEY_ROOM_FLOOR, Floor); // get users email
        values.put(KEY_ROOM_DESC, Desc); // get users email
        // Inserting Row
        saDb.insert(DB_ROOM_TABLE, null, values);

    }
    public Cursor getRoom(int room){

        return saDb.query(
                DB_ROOM_TABLE, new String[]{
                        KEY_ROOM_ID,
                        KEY_ROOM_NAME,
                        KEY_ROOM_FLOOR,
                        KEY_ROOM_DESC
                },
                KEY_ROOM_FLOOR + " = " + room,
                null,
                null,
                null,
                null
        );
    }

    public void addComputerBooking(String Name,String startTime, String endTime, String Date, String Status, int CID, int UID){

        ContentValues values = new ContentValues();
        values.put( KEY_COMPUTER_BOOKING_NAME, Name); //
        values.put( KEY_COMPUTER_BOOKING_START_TIME, startTime); //
        values.put(KEY_COMPUTER_BOOKING_END_TIME, endTime); //
        values.put(KEY_COMPUTER_BOOKING_DATE, Date); //
       // values.put(KEY_BOOKING_STATUS, Status); //
        values.put(KEY_COMPUTER_ID, CID); //
        values.put(KEY_USER_ID, UID); //


        // Inserting Row
        saDb.insert(DB_COMPUTER_BOOKING_TABLE, null, values);
    }

    public void addRoomBooking(String Name,String startTime, String endTime, String Date, String Status, int CID, int UID){
        ContentValues values = new ContentValues();
        values.put( KEY_ROOM_BOOKING_NAME, Name); //
        values.put( KEY_ROOM_BOOKING_START_TIME, startTime); //
        values.put(KEY_ROOM_BOOKING_END_TIME, endTime); //
        values.put(KEY_ROOM_BOOKING_DATE, Date); //
      //  values.put(KEY_ROOM_STATUS, Status); //
        values.put(KEY_ROOM_ID, CID); //
        values.put(KEY_USER_ID, UID); //


        // Inserting Row
        saDb.insert(DB_ROOM_BOOKING_TABLE, null, values);

    }

    public Cursor validateComputerBooking(String startTime, String endTime, String Date, int computerID)
    {
        final  String query = "SELECT "+
                " * " +
                "FROM " +
                DB_COMPUTER_BOOKING_TABLE +
                " WHERE " +
                KEY_COMPUTER_BOOKING_START_TIME + " = ?" +" AND "+
                KEY_COMPUTER_BOOKING_END_TIME + " = ?"  +" AND "+
                KEY_COMPUTER_BOOKING_DATE + " = ?" +" AND "+
                KEY_COMPUTER_ID+ " = " +computerID +";";

        return saDb.rawQuery(query,new String[]{startTime,endTime,Date});
    }

    public Cursor getMyComputerBooking(int userID){

        return saDb.query(
                DB_COMPUTER_BOOKING_TABLE, new String[]{
                        KEY_COMPUTER_BOOKING_ID,
                        KEY_COMPUTER_BOOKING_NAME,
                        KEY_COMPUTER_BOOKING_START_TIME,
                        KEY_COMPUTER_BOOKING_END_TIME,
                        KEY_COMPUTER_BOOKING_DATE,
                       // KEY_COMPUTER_BOOKING_STATUS,
                        KEY_COMPUTER_ID,
                        KEY_USER_ID
                },
                KEY_USER_ID +" = "+ userID,
                null,
                null,
                null,
                null
        );
    }

    public Cursor validateRoomBooking(String startTime, String endTime, String Date, int roomID)
    {
        final  String query = "SELECT "+
                " * " +
                "FROM " +
                DB_ROOM_BOOKING_TABLE +
                " WHERE " +
                KEY_ROOM_BOOKING_START_TIME + " = ?" +" AND "+
                KEY_ROOM_BOOKING_END_TIME + " = ?"  +" AND "+
                KEY_ROOM_BOOKING_DATE + " = ?" +" AND "+
                KEY_ROOM_ID+ " = " +roomID +";";

        return saDb.rawQuery(query,new String[]{startTime,endTime,Date});
    }


    public Cursor getMyRoomBooking(int userID){
        return saDb.query(
                DB_ROOM_BOOKING_TABLE, new String[]{
                        KEY_ROOM_BOOKING_ID,
                        KEY_ROOM_BOOKING_NAME,
                        KEY_ROOM_BOOKING_START_TIME,
                        KEY_ROOM_BOOKING_END_TIME,
                        KEY_ROOM_BOOKING_DATE,
                        //KEY_ROOM_BOOKING_STATUS,
                        KEY_ROOM_ID,
                        KEY_USER_ID
                },
                KEY_USER_ID +" = "+ userID,
                null,
                null,
                null,
                null
        );
    }

    public boolean removeMyComputerBooking(int userID, int bookingID){
        // delete statement.  If any rows deleted (i.e. >0), returns true
        return saDb.delete(DB_COMPUTER_BOOKING_TABLE, KEY_COMPUTER_BOOKING_ID +
                " = " + bookingID +" AND " + KEY_USER_ID + " = " + userID, null) > 0;

    }

    public boolean removeMyRoomBooking(int userID, int roomID){
        // delete statement.  If any rows deleted (i.e. >0), returns true
        return saDb.delete(DB_ROOM_BOOKING_TABLE, KEY_ROOM_BOOKING_ID +
                " = " + roomID +" AND " + KEY_USER_ID + " = " + userID, null) > 0;

    }

    public void removeRoomBooking(){

    }


}
