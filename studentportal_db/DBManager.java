package com.example.studentportal;

import static android.content.ContentValues.TAG;
import static com.example.studentportal.Helper.KEY_ESID;
import static com.example.studentportal.Helper.KEY_ESOCID;
import static com.example.studentportal.Helper.KEY_NUMBER;
import static com.example.studentportal.Helper.KEY_SID;
import static com.example.studentportal.Helper.KEY_SNAME;
import static com.example.studentportal.Helper.KEY_SOCID;
import static com.example.studentportal.Helper.KEY_SOCNAME;
import static com.example.studentportal.Helper.TABLE_ENR;
import static com.example.studentportal.Helper.TABLE_SOCS;
import static com.example.studentportal.Helper.TABLE_USER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {


    Context context;
    private Helper helper;
    private SQLiteDatabase database;

    public DBManager(Context context)
    {
        this.context = context;

    }

    public DBManager open() throws SQLException {
        helper = new Helper(context);
        database = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }








    // -------------- USER CRUD OPERATIONS -----------------

    // add the new user
    void addUser(User user) {

        ContentValues values = new ContentValues();
        values.put(KEY_SID, user.get_id());
        values.put(KEY_SNAME, user.get_name());

        database.insert(TABLE_USER, null, values);
    }


    //get all users
    public Cursor getAllUsers() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        Cursor userList = database.rawQuery(selectQuery, null);

        return userList;
    }

    /* code to update the single task
    public int updateTask(User user) {

        ContentValues values = new ContentValues();
        values.put(KEY_SNAME, task.getName());
        values.put(KEY_DESCRIPTION, task.getDescription());

        // updating row
        return database.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getID()) });
    }*/

    // Deleting single user
    public void deleteUser(User user) {

        database.delete(TABLE_USER, KEY_SID + " = ?",
                new String[] { String.valueOf(user.get_id()) });
        database.close();
    }






    // -------------- SOCIETIES CRUD OPERATIONS --------------

    // add the new soc
    void addSoc(Soc soc) {

        ContentValues values = new ContentValues();
        values.put(KEY_SOCID, soc.get_id());
        values.put(KEY_SOCNAME, soc.get_name());

        database.insert(TABLE_SOCS, null, values);
    }



    //get all socs
    public Cursor getAllSocs(String s) {
        String selectQuery = "SELECT "+KEY_SOCID+" AS _id, "+KEY_SOCNAME+" FROM "+TABLE_SOCS+" WHERE "
                +KEY_SOCNAME+" LIKE '%"+s+"%' AND "+KEY_SOCID+" NOT IN (SELECT "+KEY_ESOCID+" FROM " +TABLE_ENR+");";

        Cursor soclist = database.rawQuery(selectQuery, null);

        return soclist;
    }

    public Cursor getSocId(String s){
        String selectQuery = "SELECT "+KEY_SOCID+" AS _id FROM "+TABLE_SOCS+" WHERE "+KEY_SOCNAME+
                " LIKE '%"+s+"%'";

        Cursor socid = database.rawQuery(selectQuery, null);
        return socid;
    }

    // Deleting single soc
    public void deleteSoc(Soc soc) {

        database.delete(TABLE_SOCS, KEY_SOCID + " = ?",
                new String[] { String.valueOf(soc.get_id()) });
        database.close();
    }






    // -------------- ENROLLMENT CRUD OPERATIONS ---------------


    public Cursor getJoinedSocs(int id) {
        String selectQuery = "SELECT "+KEY_SOCID+" AS _id, "+KEY_SOCNAME+", "+KEY_NUMBER+"  FROM "+TABLE_SOCS+" JOIN "
        +TABLE_ENR+" WHERE "+KEY_ESOCID+" = "+KEY_SOCID+";";

        Cursor soclist = database.rawQuery(selectQuery, null);

        return soclist;
    }

    // add the new user
    void addEnr(enrollment enr ) {

        ContentValues values = new ContentValues();
        values.put(KEY_ESID, enr.get_esid());
        values.put(KEY_ESOCID, enr.get_esocid());
        values.put(KEY_NUMBER, enr.get_num());

        database.insert(TABLE_ENR, null, values);
    }

    //get user
    enrollment getEnr(int id) {

        Cursor cursor = database.query(TABLE_ENR, new String[] { KEY_ESID,
                        KEY_ESOCID, KEY_NUMBER}, KEY_ESID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        enrollment enr = new enrollment(
                cursor.getString(1));
        // return task
        return enr;
    }



    // Deleting single enrollment
    public void deleteEnr(int s)
    {
        database.delete(TABLE_ENR,KEY_ESOCID +" = ? ",new String[]{String.valueOf(s)});
        database.close();
    }

    public void updateEnr(int sid, String num)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NUMBER, num);
        database.update(TABLE_ENR, cv, KEY_ESOCID+" = ?", new String[]{String.valueOf(sid)});
    }
}
