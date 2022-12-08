package com.example.studentapp_eoc_soc.gym_portal;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.SaDbManager;

import java.util.ArrayList;

public class BookGymTimeslot extends ListActivity implements AdapterView.OnItemSelectedListener{

    ImageButton menuButton;
    ImageButton userButton;
    Spinner weekDay;
    private ArrayList<DayTimeslot> MondaySlots = new ArrayList<>();
    private ArrayList<DayTimeslot> TuesdaySlots = new ArrayList<>();
    private ArrayList<DayTimeslot> WednesdaySlots = new ArrayList<>();
    private ArrayList<DayTimeslot> ThursdaySlots = new ArrayList<>();
    private ArrayList<DayTimeslot> FridaySlots = new ArrayList<>();
    private GymDatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_gym_timeslot);
        menuButton = (ImageButton) findViewById(R.id.menuButton);
        userButton = (ImageButton) findViewById(R.id.userButton);
        weekDay = (Spinner) findViewById(R.id.daySpinner);

        //Drop down menu
        ArrayAdapter<CharSequence>weekDayAdapter=ArrayAdapter.createFromResource(this, R.array.weekDays, android.R.layout.simple_spinner_item);

        //setting adapters with dropdown
        weekDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        weekDay.setAdapter(weekDayAdapter);

        //drop down onclicks
        weekDay.setOnItemSelectedListener(this);


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookGymTimeslot.this, Menu.class));
            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookGymTimeslot.this, UserActivity.class));
            }
        });
    }

    protected void onListItemClick(ListView l, View v, int position, long id)
    {

        DayTimeslot timeClicked = (DayTimeslot) l.getItemAtPosition(position);

        // switching screens requires an intent
        Intent intent = new Intent(getApplicationContext(), BookConfirm.class);

        // add the data to send to the next screen onto the intent as "extras"
        intent.putExtra("id", timeClicked.getGymSlot_id());
        intent.putExtra("type", timeClicked.getType());
        intent.putExtra("start", timeClicked.getTime_start());
        intent.putExtra("end", timeClicked.getTime_end());
        intent.putExtra("day", timeClicked.getDay());

        // start the next activity
        startActivity(intent);
    }

    //REFERENCE THIS SPINNER DROP DOWN CODE
    // https://stackoverflow.com/users/1692590/jakob
    // User JAKOB in stackover answers this question on how to work a drop down menu
    // https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
    @Override
    public void onItemSelected(AdapterView<?> parent , View v, int position, long id) {


        GymDatabaseManager dbManager = new GymDatabaseManager(this);
        dbManager.open();



        Cursor cursor;


        switch (position) {
            case 0:
                cursor = dbManager.getMondayBookings();
                MondaySlots.clear();
                if (cursor.moveToFirst()) {
                    do {
                        DayTimeslot monSlot= new DayTimeslot();
                        monSlot.setGymSlot_id(Integer.parseInt(cursor.getString(0)));
                        monSlot.setDay(cursor.getString(1));
                        monSlot.setTime_start(cursor.getString(2));
                        monSlot.setTime_end(cursor.getString(3));
                        monSlot.setType(cursor.getString(4));
                        // Adding task to list
                        MondaySlots.add(monSlot);
                    } while (cursor.moveToNext());
                }

                MondayAdapter mondayAdapter = new MondayAdapter(this, R.layout.timeslots, MondaySlots);
                setListAdapter(mondayAdapter);

                break;
            case 1:
                TuesdaySlots.clear();
                cursor = dbManager.getTuesdayBookings();
                if (cursor.moveToFirst()) {
                    do {
                        DayTimeslot tueSlot= new DayTimeslot();
                        tueSlot.setGymSlot_id(Integer.parseInt(cursor.getString(0)));
                        tueSlot.setDay(cursor.getString(1));
                        tueSlot.setTime_start(cursor.getString(2));
                        tueSlot.setTime_end(cursor.getString(3));
                        tueSlot.setType(cursor.getString(4));
                        // Adding task to list
                        TuesdaySlots.add(tueSlot);
                    } while (cursor.moveToNext());
                }

                TuesdayAdapter tuesdayAdapter = new TuesdayAdapter(this, R.layout.timeslots, TuesdaySlots);
                setListAdapter(tuesdayAdapter);

                break;
            case 2:
                WednesdaySlots.clear();
                cursor = dbManager.getWednesdayBookings();
                if (cursor.moveToFirst()) {
                    do {
                        DayTimeslot wedSlot= new DayTimeslot();
                        wedSlot.setGymSlot_id(Integer.parseInt(cursor.getString(0)));
                        wedSlot.setDay(cursor.getString(1));
                        wedSlot.setTime_start(cursor.getString(2));
                        wedSlot.setTime_end(cursor.getString(3));
                        wedSlot.setType(cursor.getString(4));
                        // Adding task to list
                        WednesdaySlots.add(wedSlot);
                    } while (cursor.moveToNext());
                }

                WednesdayAdapter wednesdayAdapter = new WednesdayAdapter(this, R.layout.timeslots, WednesdaySlots);
                setListAdapter(wednesdayAdapter);

                break;
            case 3:
                ThursdaySlots.clear();
                cursor = dbManager.getThursdayBookings();
                if (cursor.moveToFirst()) {
                    do {
                        DayTimeslot thuSlot= new DayTimeslot();
                        thuSlot.setGymSlot_id(Integer.parseInt(cursor.getString(0)));
                        thuSlot.setDay(cursor.getString(1));
                        thuSlot.setTime_start(cursor.getString(2));
                        thuSlot.setTime_end(cursor.getString(3));
                        thuSlot.setType(cursor.getString(4));
                        // Adding task to list
                        ThursdaySlots.add(thuSlot);
                    } while (cursor.moveToNext());
                }

                ThursdayAdapter thursdayAdapter = new ThursdayAdapter(this, R.layout.timeslots, ThursdaySlots);
                setListAdapter(thursdayAdapter);

                break;
            case 4:
                FridaySlots.clear();
                /* Adding Timeslots for thursday */
                cursor = dbManager.getFridayBookings();
                if (cursor.moveToFirst()) {
                    do {
                        DayTimeslot friSlot= new DayTimeslot();
                        friSlot.setGymSlot_id(Integer.parseInt(cursor.getString(0)));
                        friSlot.setDay(cursor.getString(1));
                        friSlot.setTime_start(cursor.getString(2));
                        friSlot.setTime_end(cursor.getString(3));
                        friSlot.setType(cursor.getString(4));
                        // Adding task to list
                        FridaySlots.add(friSlot);
                    } while (cursor.moveToNext());
                }

                FridayAdapter fridayAdapter = new FridayAdapter(this, R.layout.timeslots, FridaySlots);
                setListAdapter(fridayAdapter);
        }

    }
    //REFERENCE FINISHED FOR DROP DOWN MENU

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class MondayAdapter extends ArrayAdapter<DayTimeslot>{
        ArrayList<DayTimeslot> MondaySlots;

        public MondayAdapter(@NonNull Context context, int textViewResourceId, ArrayList<DayTimeslot> mondaySlots) {
            super(context, textViewResourceId, mondaySlots);
            this.MondaySlots = mondaySlots;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.lib_timeslots, null);
            }

            DayTimeslot currentMondaySlot = MondaySlots.get(position);

            if (currentMondaySlot != null){
                TextView startTime = (TextView) v.findViewById(R.id.startTime);
                TextView endTime = (TextView) v.findViewById(R.id.endTime);
                TextView type = (TextView) v.findViewById(R.id.type);
                TextView day = (TextView) v.findViewById(R.id.day);

                startTime.setText(currentMondaySlot.getTime_start());
                endTime.setText(currentMondaySlot.getTime_end());
                type.setText(currentMondaySlot.getType());
                day.setText(currentMondaySlot.getDay());
            }

            return v;
        }
    }

    class TuesdayAdapter extends ArrayAdapter<DayTimeslot>{
        ArrayList<DayTimeslot> TuesdaySlots;

        public TuesdayAdapter(@NonNull Context context, int textViewResourceId, ArrayList<DayTimeslot> tuesdaySlots) {
            super(context, textViewResourceId, tuesdaySlots);
            this.TuesdaySlots = tuesdaySlots;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.lib_timeslots, null);
            }

            DayTimeslot currentTuesdaySlot = TuesdaySlots.get(position);

            if (currentTuesdaySlot != null){
                TextView startTime = (TextView) v.findViewById(R.id.startTime);
                TextView endTime = (TextView) v.findViewById(R.id.endTime);
                TextView type = (TextView) v.findViewById(R.id.type);
                TextView day = (TextView) v.findViewById(R.id.day);

                startTime.setText(currentTuesdaySlot.getTime_start());
                endTime.setText(currentTuesdaySlot.getTime_end());
                type.setText(currentTuesdaySlot.getType());
                day.setText(currentTuesdaySlot.getDay());
            }

            return v;
        }
    }

    class WednesdayAdapter extends ArrayAdapter<DayTimeslot> {
        ArrayList<DayTimeslot> WednesdaySlots;

        public WednesdayAdapter(@NonNull Context context, int textViewResourceId, ArrayList<DayTimeslot> wednesdaySlots) {
            super(context, textViewResourceId, wednesdaySlots);
            this.WednesdaySlots = wednesdaySlots;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.lib_timeslots, null);
            }

            DayTimeslot currentWednesdaySlot = WednesdaySlots.get(position);

            if (currentWednesdaySlot != null) {
                TextView startTime = (TextView) v.findViewById(R.id.startTime);
                TextView endTime = (TextView) v.findViewById(R.id.endTime);
                TextView type = (TextView) v.findViewById(R.id.type);
                TextView day = (TextView) v.findViewById(R.id.day);

                startTime.setText(currentWednesdaySlot.getTime_start());
                endTime.setText(currentWednesdaySlot.getTime_end());
                type.setText(currentWednesdaySlot.getType());
                day.setText(currentWednesdaySlot.getDay());
            }

            return v;
        }
    }

    class ThursdayAdapter extends ArrayAdapter<DayTimeslot>{
        ArrayList<DayTimeslot> ThursdaySlots;

        public ThursdayAdapter(@NonNull Context context, int textViewResourceId, ArrayList<DayTimeslot> thursdaySlots) {
            super(context, textViewResourceId, thursdaySlots);
            this.ThursdaySlots = thursdaySlots;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.lib_timeslots, null);
            }

            DayTimeslot currentThursdaySlot = ThursdaySlots.get(position);

            if (currentThursdaySlot != null){
                TextView startTime = (TextView) v.findViewById(R.id.startTime);
                TextView endTime = (TextView) v.findViewById(R.id.endTime);
                TextView type = (TextView) v.findViewById(R.id.type);
                TextView day = (TextView) v.findViewById(R.id.day);

                startTime.setText(currentThursdaySlot.getTime_start());
                endTime.setText(currentThursdaySlot.getTime_end());
                type.setText(currentThursdaySlot.getType());
                day.setText(currentThursdaySlot.getDay());
            }

            return v;
        }
    }

    class FridayAdapter extends ArrayAdapter<DayTimeslot>{
        ArrayList<DayTimeslot> FridaySlots;

        public FridayAdapter(@NonNull Context context, int textViewResourceId, ArrayList<DayTimeslot> fridaySlots) {
            super(context, textViewResourceId, fridaySlots);
            this.FridaySlots = fridaySlots;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.lib_timeslots, null);
            }

            DayTimeslot currentFridaySlot = FridaySlots.get(position);

            if (currentFridaySlot != null) {
                TextView startTime = (TextView) v.findViewById(R.id.startTime);
                TextView endTime = (TextView) v.findViewById(R.id.endTime);
                TextView type = (TextView) v.findViewById(R.id.type);
                TextView day = (TextView) v.findViewById(R.id.day);

                startTime.setText(currentFridaySlot.getTime_start());
                endTime.setText(currentFridaySlot.getTime_end());
                type.setText(currentFridaySlot.getType());
                day.setText(currentFridaySlot.getDay());
            }

            return v;
        }
    }
}