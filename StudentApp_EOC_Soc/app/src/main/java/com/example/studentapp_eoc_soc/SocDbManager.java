package com.example.studentapp_eoc_soc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class SocDbManager extends SaDbManager {
    public SocDbManager(Context context) {
        super(context);
    }

    // -------------- SOCIETIES CRUD OPERATIONS --------------

    // add new society
    public int insertSociety(Society s) {
        ContentValues values = new ContentValues();
        values.put(SaDbHelper.KEY_NAME, s.getSocName());

        return (int) saDb.insert(SaDbHelper.DB_SOCIETY_TABLE, null, values);
    }

    //used for searching for socs - takes a string and finds all
    //societies that match this string (partial search)
    //and uses a sub query to assure the results displayed are not in the enrollment table
    public Cursor getAllSocs(String s) {
        String selectQuery = "SELECT " +
                SaDbHelper.KEY_SOC_ID + " AS _id, " +
                SaDbHelper.KEY_NAME + " FROM " +
                SaDbHelper.DB_SOCIETY_TABLE + " WHERE " +
                SaDbHelper.KEY_NAME + " LIKE '%" + s + "%' AND " +
                SaDbHelper.KEY_SOC_ID + " NOT IN (SELECT " +
                SaDbHelper.KEY_SOC_ID + " FROM " +
                SaDbHelper.DB_SOC_ENROLMENT_TABLE + ");";

        return saDb.rawQuery(selectQuery, null);
    }

    //get a society's id using the a string (soc name)
    public Cursor getSocId(String s){
        String selectQuery = "SELECT " +
                SaDbHelper.KEY_SOC_ID + " AS _id FROM " +
                SaDbHelper.DB_SOCIETY_TABLE + " WHERE " +
                SaDbHelper.KEY_NAME + " LIKE '%" + s + "%'";

        return saDb.rawQuery(selectQuery, null);
    }

    // -------------- ENROLLMENT CRUD OPERATIONS ---------------

    //get the info on the joined societies i.e the societies in the enrollment table
    //join needed here as the soc name isn't stored in the enrollment table and we need this value for displaying
    public Cursor getJoinedSocs(int id) {
        String selectQuery = "SELECT " +
                SaDbHelper.DB_SOCIETY_TABLE + "." +
                SaDbHelper.KEY_SOC_ID + " AS _id, " +
                SaDbHelper.KEY_NAME + ", " +
                SaDbHelper.KEY_ENROL_USER_CONTACT + " FROM " +
                SaDbHelper.DB_SOCIETY_TABLE + " JOIN " +
                SaDbHelper.DB_SOC_ENROLMENT_TABLE + " WHERE " +
                SaDbHelper.DB_SOCIETY_TABLE + "." +
                SaDbHelper.KEY_SOC_ID + " =" +
                SaDbHelper.DB_SOC_ENROLMENT_TABLE + "." +
                SaDbHelper.KEY_SOC_ID + ";";

        return saDb.rawQuery(selectQuery, null);
    }

    // add a new enrollment
    public void insertSocEnrolment(SocEnrolment enr) {
        ContentValues values = new ContentValues();
        values.put(SaDbHelper.KEY_USER_ID, enr.getUserId());
        values.put(SaDbHelper.KEY_SOC_ID, enr.getSocId());
        values.put(SaDbHelper.KEY_ENROL_USER_CONTACT, enr.getUserContact());

        saDb.insert(SaDbHelper.DB_SOC_ENROLMENT_TABLE, null, values);
    }

    // Deleting single enrollment
    public void deleteSocEnrolment(int s)
    {
        saDb.delete(
                SaDbHelper.DB_SOC_ENROLMENT_TABLE,
                SaDbHelper.KEY_SOC_ID +" = ?",
                new String[]{String.valueOf(s)}
        );
    }

    //updating the contact number column of a single enrollment, by matching the society id
    public void updateSocEnrolment(int sid, String num)
    {
        ContentValues cv = new ContentValues();
        cv.put(SaDbHelper.KEY_ENROL_USER_CONTACT, num);

        saDb.update(
                SaDbHelper.DB_SOC_ENROLMENT_TABLE,
                cv,
                SaDbHelper.KEY_SOC_ID+" = ?",
                new String[]{String.valueOf(sid)}
        );
    }
}
