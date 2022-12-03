package com.example.studentportal;

import static android.content.ContentValues.TAG;


import static com.example.studentportal.SaDbHelper.DB_SOCIETY_TABLE;
import static com.example.studentportal.SaDbHelper.DB_SOC_ENROLMENT_TABLE;
import static com.example.studentportal.SaDbHelper.KEY_ENROL_USER_CONTACT;
import static com.example.studentportal.SaDbHelper.KEY_NAME;
import static com.example.studentportal.SaDbHelper.KEY_SOC_ID;
import static com.example.studentportal.SaDbHelper.KEY_USER_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {


    Context context;
    private SaDbHelper helper;
    private SQLiteDatabase database;

    public DBManager(Context context)
    {
        this.context = context;

    }

    public DBManager open() throws SQLException {
        helper = new SaDbHelper(context);
        database = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }






    // -------------- SOCIETIES CRUD OPERATIONS --------------

    // add the new soc
    void addSoc(Soc soc) {

        ContentValues values = new ContentValues();
        values.put(KEY_SOC_ID, soc.get_id());
        values.put(KEY_NAME, soc.get_name());

        database.insert(DB_SOCIETY_TABLE, null, values);
    }


    //get all socs
    public Cursor getAllSocs(String s) {
        String selectQuery = "SELECT "+KEY_SOC_ID+" AS _id, "+KEY_NAME+" FROM "+DB_SOCIETY_TABLE+" WHERE "
                +KEY_NAME+" LIKE '%"+s+"%' AND "+KEY_SOC_ID+" NOT IN (SELECT "+KEY_SOC_ID+" FROM " +DB_SOC_ENROLMENT_TABLE+");";

        Cursor soclist = database.rawQuery(selectQuery, null);

        return soclist;
    }

    public Cursor getSocId(String s){
        String selectQuery = "SELECT "+KEY_SOC_ID+" AS _id FROM "+DB_SOCIETY_TABLE+" WHERE "+KEY_NAME+
                " LIKE '%"+s+"%'";

        Cursor socid = database.rawQuery(selectQuery, null);
        return socid;
    }





    // -------------- ENROLLMENT CRUD OPERATIONS ---------------


    public Cursor getJoinedSocs(int id) {
        String selectQuery = "SELECT "+DB_SOCIETY_TABLE+"."+KEY_SOC_ID+" AS _id, "+KEY_NAME+", "+KEY_ENROL_USER_CONTACT+" FROM "+DB_SOCIETY_TABLE+" JOIN "
        +DB_SOC_ENROLMENT_TABLE+" WHERE "+DB_SOCIETY_TABLE+"."+KEY_SOC_ID+" ="+DB_SOC_ENROLMENT_TABLE+"."+KEY_SOC_ID+";";

        Cursor soclist = database.rawQuery(selectQuery, null);

        return soclist;
    }

    // add the new user
    void addEnr(enrollment enr ) {

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, enr.get_esid());
        values.put(KEY_SOC_ID, enr.get_esocid());
        values.put(KEY_ENROL_USER_CONTACT, enr.get_num());

        database.insert(DB_SOC_ENROLMENT_TABLE, null, values);
    }

    // Deleting single enrollment
    public void deleteEnr(int s)
    {
        database.delete(DB_SOC_ENROLMENT_TABLE,KEY_SOC_ID +" = ? ",new String[]{String.valueOf(s)});
        database.close();
    }

    public void updateEnr(int sid, String num)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ENROL_USER_CONTACT, num);
        database.update(DB_SOC_ENROLMENT_TABLE, cv, KEY_SOC_ID+" = ?", new String[]{String.valueOf(sid)});
    }
}
