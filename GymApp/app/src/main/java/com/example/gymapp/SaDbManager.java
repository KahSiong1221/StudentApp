package com.example.gymapp;

import android.content.Context;
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
}