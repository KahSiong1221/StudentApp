package com.example.studentapp_eoc_soc.library;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.studentapp_eoc_soc.SaDbHelper;
import com.example.studentapp_eoc_soc.SaDbManager;

public class lib_DbManager extends SaDbManager {

    public lib_DbManager(Context context) {
        super(context);
    }

    public void addUser(String Name, String Email,String Phone_no ) {

        ContentValues values = new ContentValues();
        values.put(SaDbHelper.KEY_NAME, Name); // get users name
        values.put(SaDbHelper.KEY_EMAIL, Email); // get users email
        values.put(SaDbHelper.KEY_PHONE_NO, Phone_no); // get users email
        // Inserting Row
        saDb.insert(SaDbHelper.DB_USER_TABLE, null, values);

    }

    public Cursor getUser(String email){


        return saDb.query(
                SaDbHelper.DB_USER_TABLE,
                new String[]{
                        SaDbHelper.KEY_USER_ID,
                        SaDbHelper.KEY_NAME,
                        SaDbHelper.KEY_EMAIL,
                        SaDbHelper.KEY_PHONE_NO
                },
                SaDbHelper.KEY_EMAIL +" = " + "'"+email+"'",
                null,
                null,
                null,
                null
        );

    }

    public Cursor getComputerAvailability(String starTime, String endTime, String Date, int floor){
        final  String query = "SELECT "+
                "COUNT(*) " +
                "FROM " +
                SaDbHelper.DB_COMPUTER_BOOKING_TABLE +
                " WHERE " +
                SaDbHelper.KEY_COMPUTER_BOOKING_START_TIME + " = ?" +" AND "+
                SaDbHelper.KEY_COMPUTER_BOOKING_END_TIME + " = ?"  +" AND "+
                SaDbHelper.KEY_COMPUTER_BOOKING_DATE + " = ?"  +" AND "+
                SaDbHelper.KEY_COMPUTER_BOOKING_FLOOR + " = ?;";

        return saDb.rawQuery(query,new String[]{starTime,endTime,Date, String.valueOf(floor)});
    }


    public Cursor getRoomAvailability(String starTime, String endTime, String Date, int floor){
        final  String query = "SELECT "+
                "COUNT(*) " +
                "FROM " +
                SaDbHelper.DB_ROOM_BOOKING_TABLE +
                " WHERE " +
                SaDbHelper.KEY_ROOM_BOOKING_START_TIME + " = ?" +" AND "+
                SaDbHelper.KEY_ROOM_BOOKING_END_TIME + " = ?"  +" AND "+
                SaDbHelper.KEY_ROOM_BOOKING_DATE + " = ?"  +" AND "+
                SaDbHelper.KEY_ROOM_BOOKING_FLOOR + " = ?;";

        return saDb.rawQuery(query,new String[]{starTime,endTime,Date, String.valueOf(floor)});
    }


    public void addComputer(String Name, int Floor, String Desc){

        ContentValues values = new ContentValues();
        values.put(SaDbHelper.KEY_COMPUTER_NAME, Name); // get users name
        values.put(SaDbHelper.KEY_COMPUTER_FLOOR, Floor); // get users email
        values.put(SaDbHelper.KEY_COMPUTER_DESC, Desc); // get users email
        // Inserting Row
        saDb.insert(SaDbHelper.DB_COMPUTER_TABLE, null, values);

    }

    public Cursor getComputer(int floor){

        return saDb.query(
                SaDbHelper.DB_COMPUTER_TABLE, new String[]{
                        SaDbHelper.KEY_COMPUTER_ID,
                        SaDbHelper.KEY_COMPUTER_NAME,
                        SaDbHelper.KEY_COMPUTER_FLOOR,
                        SaDbHelper.KEY_COMPUTER_DESC
                },
                SaDbHelper.KEY_COMPUTER_FLOOR + " = " + floor,
                null,
                null,
                null,
                null
        );
    }


    public void addRoom(String Name, int Floor, String Desc){

        ContentValues values = new ContentValues();
        values.put(SaDbHelper.KEY_ROOM_NAME, Name); // get users name
        values.put(SaDbHelper.KEY_ROOM_FLOOR, Floor); // get users email
        values.put(SaDbHelper.KEY_ROOM_DESC, Desc); // get users email
        // Inserting Row
        saDb.insert(SaDbHelper.DB_ROOM_TABLE, null, values);

    }
    public Cursor getRoom(int room){

        return saDb.query(
                SaDbHelper.DB_ROOM_TABLE, new String[]{
                        SaDbHelper.KEY_ROOM_ID,
                        SaDbHelper.KEY_ROOM_NAME,
                        SaDbHelper.KEY_ROOM_FLOOR,
                        SaDbHelper.KEY_ROOM_DESC
                },
                SaDbHelper.KEY_ROOM_FLOOR + " = " + room,
                null,
                null,
                null,
                null
        );
    }

    public void addComputerBooking(String Name,String startTime, String endTime, String Date, String Status,int floor, int CID, int UID){

        ContentValues values = new ContentValues();
        values.put(SaDbHelper.KEY_COMPUTER_BOOKING_NAME, Name); //
        values.put(SaDbHelper.KEY_COMPUTER_BOOKING_START_TIME, startTime); //
        values.put(SaDbHelper.KEY_COMPUTER_BOOKING_END_TIME, endTime); //
        values.put(SaDbHelper.KEY_COMPUTER_BOOKING_DATE, Date); //
        values.put(SaDbHelper.KEY_COMPUTER_BOOKING_STATUS, Status); //
        values.put(SaDbHelper.KEY_COMPUTER_BOOKING_FLOOR, floor); //
        values.put(SaDbHelper.KEY_COMPUTER_ID, CID); //
        values.put(SaDbHelper.KEY_USER_ID, UID); //


        // Inserting Row
        saDb.insert(SaDbHelper.DB_COMPUTER_BOOKING_TABLE, null, values);
    }

    public void addRoomBooking(String Name,String startTime, String endTime, String Date, String Status,int floor, int CID, int UID){
        ContentValues values = new ContentValues();
        values.put(SaDbHelper.KEY_ROOM_BOOKING_NAME, Name); //
        values.put(SaDbHelper.KEY_ROOM_BOOKING_START_TIME, startTime); //
        values.put(SaDbHelper.KEY_ROOM_BOOKING_END_TIME, endTime); //
        values.put(SaDbHelper.KEY_ROOM_BOOKING_DATE, Date); //
        values.put(SaDbHelper.KEY_ROOM_BOOKING_STATUS, Status); //
        values.put(SaDbHelper.KEY_ROOM_BOOKING_FLOOR, floor); //
        values.put(SaDbHelper.KEY_ROOM_ID, CID); //
        values.put(SaDbHelper.KEY_USER_ID, UID); //


        // Inserting Row
        saDb.insert(SaDbHelper.DB_ROOM_BOOKING_TABLE, null, values);

    }

    public Cursor validateComputerBooking(String startTime, String endTime, String Date, int computerID)
    {
        final  String query = "SELECT "+
                " * " +
                " FROM " +
                SaDbHelper.DB_COMPUTER_BOOKING_TABLE +
                " WHERE " +
                SaDbHelper.KEY_COMPUTER_BOOKING_START_TIME + " = ?" +" AND "+
                SaDbHelper.KEY_COMPUTER_BOOKING_END_TIME + " = ?"  +" AND "+
                SaDbHelper.KEY_COMPUTER_BOOKING_DATE + " = ?" +" AND "+
                SaDbHelper.KEY_COMPUTER_ID+ " = ? "  +" ;";

        return saDb.rawQuery(query,new String[]{startTime,endTime,Date, String.valueOf(computerID)});
    }

    public Cursor getMyComputerBooking(int userID){

      return saDb.query(
              SaDbHelper.DB_COMPUTER_BOOKING_TABLE, new String[]{
                      SaDbHelper.KEY_COMPUTER_BOOKING_ID,
                      SaDbHelper.KEY_COMPUTER_BOOKING_NAME,
                      SaDbHelper.KEY_COMPUTER_BOOKING_START_TIME,
                      SaDbHelper.KEY_COMPUTER_BOOKING_END_TIME,
                      SaDbHelper.KEY_COMPUTER_BOOKING_DATE,
                      SaDbHelper.KEY_COMPUTER_BOOKING_STATUS,
                      SaDbHelper.KEY_COMPUTER_BOOKING_FLOOR,
                      SaDbHelper.KEY_COMPUTER_ID,
                      SaDbHelper.KEY_USER_ID
              },
              SaDbHelper.KEY_USER_ID +" = "+ userID,
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
                SaDbHelper.DB_ROOM_BOOKING_TABLE +
                " WHERE " +
                SaDbHelper.KEY_ROOM_BOOKING_START_TIME + " = ?" +" AND "+
                SaDbHelper.KEY_ROOM_BOOKING_END_TIME + " = ?"  +" AND "+
                SaDbHelper.KEY_ROOM_BOOKING_DATE + " = ?" +" AND "+
                SaDbHelper.KEY_ROOM_ID+ " = ? "  + " ;" ;

        return saDb.rawQuery(query,new String[]{startTime,endTime,Date, String.valueOf(roomID)});
    }


    public Cursor getMyRoomBooking(int userID){
        return saDb.query(
                SaDbHelper.DB_ROOM_BOOKING_TABLE, new String[]{
                        SaDbHelper.KEY_ROOM_BOOKING_ID,
                        SaDbHelper.KEY_ROOM_BOOKING_NAME,
                        SaDbHelper.KEY_ROOM_BOOKING_START_TIME,
                        SaDbHelper.KEY_ROOM_BOOKING_END_TIME,
                        SaDbHelper.KEY_ROOM_BOOKING_DATE,
                        SaDbHelper.KEY_ROOM_BOOKING_STATUS,
                        SaDbHelper.KEY_ROOM_BOOKING_FLOOR,
                        SaDbHelper.KEY_ROOM_ID,
                        SaDbHelper.KEY_USER_ID
                },
                SaDbHelper.KEY_USER_ID +" = "+ userID,
                null,
                null,
                null,
                null
        );
    }

    public boolean removeMyComputerBooking(int userID, int bookingID){
        // delete statement.  If any rows deleted (i.e. >0), returns true
        return saDb.delete(SaDbHelper.DB_COMPUTER_BOOKING_TABLE, SaDbHelper.KEY_COMPUTER_BOOKING_ID +
                " = " + bookingID +" AND " + SaDbHelper.KEY_USER_ID + " = " + userID, null) > 0;

    }

    public boolean removeMyRoomBooking(int userID, int roomID){
        // delete statement.  If any rows deleted (i.e. >0), returns true
        return saDb.delete(SaDbHelper.DB_ROOM_BOOKING_TABLE, SaDbHelper.KEY_ROOM_BOOKING_ID +
                " = " + roomID +" AND " + SaDbHelper.KEY_USER_ID + " = " + userID, null) > 0;

    }

    public void removeRoomBooking(){

    }



}
