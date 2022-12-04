package com.example.student_app;

import static com.example.student_app.SaDbHelper.DB_GYMSLOT_TABLE;
import static com.example.student_app.SaDbHelper.DB_GYM_MEMBERSHIP_TABLE;
import static com.example.student_app.SaDbHelper.DB_USERBOOKINGS_TABLE;
import static com.example.student_app.SaDbHelper.DB_USER_MEMBERSHIP_TABLE;
import static com.example.student_app.SaDbHelper.DB_USER_TABLE;
import static com.example.student_app.SaDbHelper.KEY_DAY;
import static com.example.student_app.SaDbHelper.KEY_EMAIL;
import static com.example.student_app.SaDbHelper.KEY_END_DATE;
import static com.example.student_app.SaDbHelper.KEY_END_TIME;
import static com.example.student_app.SaDbHelper.KEY_GYMSLOT_ID;
import static com.example.student_app.SaDbHelper.KEY_GYM_MEMBERSHIP_ID;
import static com.example.student_app.SaDbHelper.KEY_MEMBERSHIP_TYPE;
import static com.example.student_app.SaDbHelper.KEY_MEMCHECK;
import static com.example.student_app.SaDbHelper.KEY_NAME;
import static com.example.student_app.SaDbHelper.KEY_PRICE;
import static com.example.student_app.SaDbHelper.KEY_START_TIME;
import static com.example.student_app.SaDbHelper.KEY_TYPE;
import static com.example.student_app.SaDbHelper.KEY_USER_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.student_app.calendar.CalendarFun;
import com.example.student_app.calendar.Event;
import com.example.student_app.gym_portal.DayTimeslot;
import com.example.student_app.gym_portal.Membership;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        return saDb.insert("Event_Entry", null, contentValues);
    }


    public void populateEventArray(){
        LocalDate localDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        try( Cursor result = saDb.rawQuery("SELECT Entry_ID, Name, Start_time, End_time, Location, Date FROM Event_Entry", null)){
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
        saDb.update("Event_Entry", contentValues, "Entry_ID =?", new String[]{String.valueOf(event.getId())});
    }

    public void deleteEvent(Event event) {
        saDb.delete("Event_Entry", "Entry_ID =?", new String[]{String.valueOf(event.getId())});
    }

    public void addUser(com.example.student_app.gym_portal.User u) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, u.getName()); // get users name
        values.put(KEY_EMAIL, u.getEmail()); // get users email
        values.put(KEY_MEMCHECK, u.getMemCheck()); //check user membership

        // Inserting Row
        saDb.insert(DB_USER_TABLE, null, values);

    }

    void addDayTimeSlots(DayTimeslot dTm) {

        ContentValues values = new ContentValues();
        values.put(KEY_DAY, dTm.getDay()); // Day
        values.put(KEY_START_TIME, dTm.getTime_start()); // Timeslot start time
        values.put(KEY_END_TIME, dTm.getTime_end()); // Timeslot end time
        values.put(KEY_TYPE, dTm.getType()); // Type (Fitness or Gym session)

        // Inserting Row
        saDb.insert(DB_GYMSLOT_TABLE, null, values);

    }


    public void addUserBooking(com.example.student_app.gym_portal.User u, DayTimeslot dTm) {
        u.bookSession(dTm);

        ContentValues initValues = new ContentValues();
        initValues.put(KEY_USER_ID, u.getUser_id());
        initValues.put(KEY_GYMSLOT_ID, dTm.getGymSlot_id());
        initValues.put(KEY_TYPE, dTm.getType());

        saDb.insert(DB_USERBOOKINGS_TABLE, null, initValues);
    }

    public long addMemberships(Membership m) {

        ContentValues initValues = new ContentValues();
        initValues.put(KEY_MEMBERSHIP_TYPE, m.getMemType());
        initValues.put(KEY_PRICE, m.getPrice());

        return saDb.insert(DB_GYM_MEMBERSHIP_TABLE, null, initValues);
    }

    public long addUserMembership(com.example.student_app.gym_portal.User u, Membership m, String day) {
        u.addMembership(m);

        ContentValues initValues = new ContentValues();
        initValues.put(KEY_USER_ID, u.getUser_id());
        initValues.put(KEY_GYM_MEMBERSHIP_ID, m.getMembership_id());
        initValues.put(KEY_END_DATE, day);

        return saDb.insert(DB_USER_MEMBERSHIP_TABLE, null, initValues);
    }


    public Cursor getMemberships(){
        return saDb.query(
                DB_GYM_MEMBERSHIP_TABLE,
                new String[] {
                        KEY_GYM_MEMBERSHIP_ID + " AS _id",
                        KEY_MEMBERSHIP_TYPE,
                        KEY_PRICE
                },
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getUserMembership() {
        String query = "SELECT  B.mem_type, A.mem_end_date FROM userMembership A " +
                "INNER JOIN " +
                "gymMembership B USING (membership_id) WHERE user_id = ?";
        String[] selectionArgs = {"1"};
        return saDb.rawQuery(query, selectionArgs);
    }


    public Cursor getUser() {
        Cursor cursor = saDb.query(
                DB_USER_TABLE,
                new String[] {
                        KEY_USER_ID + " AS _id",
                        KEY_NAME,
                        KEY_EMAIL,
                        KEY_MEMCHECK
                },
                KEY_EMAIL + " = 'jcrispo@gmail.com' ",
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor user(String userEmail){
        String query = "SELECT * FROM user WHERE email = ?";
        String[] selectionArgs = {userEmail};
        return saDb.rawQuery(query, selectionArgs);
    }

    public Cursor getMondayBookings() {
        return saDb.query(
                DB_GYMSLOT_TABLE,
                new String[] {
                        KEY_GYMSLOT_ID + " AS _id",
                        KEY_DAY,
                        KEY_START_TIME,
                        KEY_END_TIME,
                        KEY_TYPE
                },
                KEY_DAY + " = 'Monday' ",
                null,
                null,
                null,
                null
        );
    }

    public Cursor getTuesdayBookings() {
        return saDb.query(
                DB_GYMSLOT_TABLE,
                new String[] {
                        KEY_GYMSLOT_ID + " AS _id",
                        KEY_DAY,
                        KEY_START_TIME,
                        KEY_END_TIME,
                        KEY_TYPE
                },
                KEY_DAY + " = 'Tuesday' ",
                null,
                null,
                null,
                null
        );
    }

    public Cursor getWednesdayBookings() {
        return saDb.query(
                DB_GYMSLOT_TABLE,
                new String[] {
                        KEY_GYMSLOT_ID + " AS _id",
                        KEY_DAY,
                        KEY_START_TIME,
                        KEY_END_TIME,
                        KEY_TYPE
                },
                KEY_DAY + " = 'Wednesday' ",
                null,
                null,
                null,
                null
        );
    }

    public Cursor getThursdayBookings() {
        return saDb.query(
                DB_GYMSLOT_TABLE,
                new String[] {
                        KEY_GYMSLOT_ID + " AS _id",
                        KEY_DAY,
                        KEY_START_TIME,
                        KEY_END_TIME,
                        KEY_TYPE
                },
                KEY_DAY + " = 'Thursday' ",
                null,
                null,
                null,
                null
        );
    }

    public Cursor getFridayBookings() {
        return saDb.query(
                DB_GYMSLOT_TABLE,
                new String[] {
                        KEY_GYMSLOT_ID + " AS _id",
                        KEY_DAY,
                        KEY_START_TIME,
                        KEY_END_TIME,
                        KEY_TYPE
                },
                KEY_DAY + " = 'Friday' ",
                null,
                null,
                null,
                null
        );
    }

    public Cursor getUserBookings(){
        String query = "SELECT  B.gymSlot_id, B.day, B.start_time, B.end_time, B.type FROM userBookings A INNER JOIN " +
                "gymSlot B USING (gymSlot_id) WHERE user_id = ? ORDER BY gymSlot_id";
        String[] selectionArgs = {"1"};
        return saDb.rawQuery(query, selectionArgs);
    }

    public boolean updatePerson(long rowId, long num )
    {
        ContentValues args = new ContentValues();
        args.put(KEY_MEMCHECK,  num);
        return saDb.update(DB_USER_TABLE, args,
                KEY_USER_ID + "=" + rowId, null) > 0;
    }

    public boolean deleteBooking(int slot_id, int user_id)
    {
        // delete statement.  If any rows deleted (i.e. >0), returns true
        return saDb.delete(DB_USERBOOKINGS_TABLE, KEY_GYMSLOT_ID +
                " = " + slot_id +" AND " + KEY_USER_ID + " = " + user_id, null) > 0;
    }
}
