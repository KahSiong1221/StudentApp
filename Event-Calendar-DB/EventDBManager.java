//Builds, need to test population

package com.example.mse_ca;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//EventDBManager Class
public class EventDBManager {
    Context context;
    private EventDBHelper dbHelper;
    protected SQLiteDatabase db;
    public EventDBManager(Context context){
        this.context = context;
    }

    public EventDBManager open() throws SQLException{
        this.dbHelper = new EventDBHelper(this.context);
        this.db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    //Insert Event function - Called when event is first created in the editEvent activity
    public long insertEvent(Event event, String user_id){
        String date = CalendarFun.formatDate(event.getDate());
        ContentValues contentValues = new ContentValues();
        contentValues.put("Entry_ID", event.getId());
        contentValues.put("Name", event.getName() );
        contentValues.put("Start_time", event.getStart_Time());
        contentValues.put("End_time",event.getEnd_Time() );
        contentValues.put("Location", event.getLocation());
        contentValues.put("Date", date);
        contentValues.put("User_id", user_id);

        return db.insert("Event_Entry", null, contentValues);
    }


   public void populateEventArray(){
        LocalDate localDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        try( Cursor result = db.rawQuery("SELECT Entry_ID, Name, Start_time, End_time, Location, Date FROM Event_Entry", null)){
            if(result.getCount() != 0){
                while(result.moveToNext()){
                    int id = result.getInt(0);
                    String Name = result.getString(1);
                    String Start = result.getString(2);
                    String End = result.getString(3);
                    String Location = result.getString(4);
                    String Date = result.getString(5);
                    localDate = LocalDate.parse(Date,formatter);
                    Event event = new Event(id, Name, Start, End, Location, localDate, null);
                    Event.eventsList.add(event);
                }
            }
        }

    }

    public void editEvent(Event event, String user_id){
        String date = CalendarFun.formatDate(event.getDate());
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", event.getName());
        contentValues.put("Start_time", event.getStart_Time() );
        contentValues.put("End_time", event.getEnd_Time());
        contentValues.put("Location", event.getLocation());
        contentValues.put("Date", date);
        contentValues.put("User_id", user_id);
         db.update("Event_Entry", contentValues, "Entry_ID =?", new String[]{String.valueOf(event.getId())});
    }

    public void deleteEvent(Event event) {
        db.delete("Event_Entry", "Entry_ID =?", new String[]{String.valueOf(event.getId())});
    }
}
