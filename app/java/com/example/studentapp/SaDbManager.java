package com.example.studentapp_eoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
        initValues.put(SaDbHelper.KEY_NAME, u.getEmail());
        initValues.put(SaDbHelper.KEY_NAME, u.getPhoneNo());

        return (int) saDb.insert(SaDbHelper.DB_USER_TABLE, null, initValues);
    }

    public Cursor findUserByUserName(String userName) {
        return saDb.query(
                SaDbHelper.DB_USER_TABLE,
                new String[] {
                        SaDbHelper.KEY_USER_ID,
                        SaDbHelper.KEY_NAME,
                        SaDbHelper.KEY_EMAIL,
                        SaDbHelper.KEY_PHONE_NO
                },
                SaDbHelper.KEY_NAME + " = ?",
                new String[]{userName},
                null,
                null,
                null
        );
    }
}

