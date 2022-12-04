package com.example.libraryapp;

import static com.example.libraryapp.DatabaseHelper.DB_COMPUTER_BOOKING_TABLE;
import static com.example.libraryapp.DatabaseHelper.DB_COMPUTER_TABLE;
import static com.example.libraryapp.DatabaseHelper.DB_ROOM_BOOKING_TABLE;
import static com.example.libraryapp.DatabaseHelper.DB_ROOM_TABLE;
import static com.example.libraryapp.DatabaseHelper.KEY_BOOKING_STATUS;
import static com.example.libraryapp.DatabaseHelper.KEY_COMPUTER_BOOKING_DATE;
import static com.example.libraryapp.DatabaseHelper.KEY_COMPUTER_BOOKING_END_TIME;
import static com.example.libraryapp.DatabaseHelper.KEY_COMPUTER_BOOKING_ID;
import static com.example.libraryapp.DatabaseHelper.KEY_COMPUTER_BOOKING_NAME;
import static com.example.libraryapp.DatabaseHelper.KEY_COMPUTER_BOOKING_START_TIME;
import static com.example.libraryapp.DatabaseHelper.KEY_COMPUTER_DESC;
import static com.example.libraryapp.DatabaseHelper.KEY_COMPUTER_FLOOR;
import static com.example.libraryapp.DatabaseHelper.KEY_COMPUTER_ID;
import static com.example.libraryapp.DatabaseHelper.KEY_COMPUTER_NAME;
import static com.example.libraryapp.DatabaseHelper.KEY_ROOM_BOOKING_DATE;
import static com.example.libraryapp.DatabaseHelper.KEY_ROOM_BOOKING_END_TIME;
import static com.example.libraryapp.DatabaseHelper.KEY_ROOM_BOOKING_ID;
import static com.example.libraryapp.DatabaseHelper.KEY_ROOM_BOOKING_NAME;
import static com.example.libraryapp.DatabaseHelper.KEY_ROOM_BOOKING_START_TIME;
import static com.example.libraryapp.DatabaseHelper.KEY_ROOM_DESC;
import static com.example.libraryapp.DatabaseHelper.KEY_ROOM_FLOOR;
import static com.example.libraryapp.DatabaseHelper.KEY_ROOM_ID;
import static com.example.libraryapp.DatabaseHelper.KEY_ROOM_NAME;
import static com.example.libraryapp.DatabaseHelper.KEY_ROOM_STATUS;
import static com.example.libraryapp.DatabaseHelper.KEY_USER_ID;
import static com.example.libraryapp.SaDbHelper.DB_USER_TABLE;
import static com.example.libraryapp.SaDbHelper.KEY_COMPUTER_BOOKING_STATUS;
import static com.example.libraryapp.SaDbHelper.KEY_EMAIL;
import static com.example.libraryapp.SaDbHelper.KEY_NAME;
import static com.example.libraryapp.SaDbHelper.KEY_PHONE_NO;
import static com.example.libraryapp.SaDbHelper.KEY_ROOM_BOOKING_STATUS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.sax.StartElementListener;

public class DatabaseManager extends SaDbManager {




    public DatabaseManager(Context context) {
        super(context);
    }

    public void addUser(String Name, String Email,String Phone_no ) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Name); // get users name
        values.put(KEY_EMAIL, Email); // get users email
        values.put(KEY_PHONE_NO, Phone_no); // get users email
        // Inserting Row
        saDb.insert(DB_USER_TABLE, null, values);

    }

    public Cursor getUser(String email){


        return saDb.query(
                DB_USER_TABLE,
                new String[]{
                        KEY_USER_ID,
                        KEY_NAME,
                        KEY_EMAIL,
                        KEY_PHONE_NO
                },
                KEY_EMAIL +" = " + "'"+email+"'",
                null,
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
        values.put(KEY_BOOKING_STATUS, Status); //
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
        values.put(KEY_ROOM_STATUS, Status); //
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
                      KEY_COMPUTER_BOOKING_STATUS,
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
                        KEY_ROOM_BOOKING_STATUS,
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
