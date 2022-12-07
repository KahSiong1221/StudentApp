package com.example.studentapp_eoc_soc.library;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.SaDbManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.Locale;

public class roomSlotsActivity extends ListActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private  SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
    private  SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyy");
    private SaDbManager dbManager = new SaDbManager(this);
    private  Calendar myCal = Calendar.getInstance(Locale.UK);
    private  Calendar currentCal =  Calendar.getInstance(Locale.UK);

    public String thisDate = sdf.format(myCal.getTime() );
    public String selectDate = sdf2.format(myCal.getTime() );

    public int currFloor = 0;
    private  slotAdapter myAdapter;
    private  int slotLimit = 5;

    public String[] timeSchedule = {"08:00","09:00","10:00","11:00",
            "12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00"};
    private  String[] buttonDate = new String[7];
    private ArrayList<timeslot> timeslots;
    private  Button days[] = new Button[7];






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayslots);
        Button exitComputerSlotsButton = (Button)findViewById(R.id.exitSlotsButton);
        Button incrementDate = (Button)findViewById(R.id.incrementDate);
        Button decrementDate = (Button)findViewById(R.id.decrementDate);
        TextView viewDate = (TextView)findViewById(R.id.viewDate);

        viewDate.setText(thisDate);

        days[0] = (Button)findViewById(R.id.mon);
        days[1] = (Button)findViewById(R.id.tue);
        days[2] = (Button)findViewById(R.id.wed);
        days[3] = (Button)findViewById(R.id.thur);
        days[4] = (Button)findViewById(R.id.fri);
        days[5] = (Button)findViewById(R.id.sat);
        days[6] = (Button)findViewById(R.id.sun);

        days[0].setOnClickListener(this);
        days[1].setOnClickListener(this);
        days[2].setOnClickListener(this);
        days[3].setOnClickListener(this);
        days[4].setOnClickListener(this);
        days[5].setOnClickListener(this);
        days[6].setOnClickListener(this);


        displayDays(days);

        //slots adapter
        timeslots = createTimeSlot(currFloor,selectDate,timeSchedule);
        myAdapter = new slotAdapter(this,R.layout.lib_timeslots,timeslots);
        setListAdapter(myAdapter);


        //spinner
        Spinner spinner = (Spinner)findViewById(R.id.spinner5);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.floorList, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
        spinnerAdapter.notifyDataSetChanged();


        incrementDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                thisDate = sdf.format(myCal.getTime() );
                displayDays(days);
                viewDate.setText(thisDate);


            }
        });

        decrementDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myCal.add(Calendar.WEEK_OF_YEAR,-2);
                thisDate = sdf.format(myCal.getTime() );
                displayDays(days);
                viewDate.setText(thisDate);

            }
        });

        exitComputerSlotsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();

            }
        });


    }

    protected void onListItemClick(ListView l, View v, int position, long id)
    {

        timeslot slotClicked = (timeslot) l.getItemAtPosition(position);
        slotClicked.setDate(selectDate);
        slotClicked.setFloor(currFloor);
        // switching screens requires an intent
        if(slotClicked.getStatus() == "Available") {
            Intent intent = new Intent(roomSlotsActivity.this, bookRoomActivity.class);

            // add the data to send to the next screen onto the intent as "extras"
            intent.putExtra("floor", slotClicked.getFloor());
            intent.putExtra("startTime", slotClicked.getStartTime());
            intent.putExtra("endTime", slotClicked.getEndTime());
            intent.putExtra("date", slotClicked.getDate());

            // start the next activity
            startActivity(intent);
        }
    }

    public void displayDays(Button days[]){

        //Calendar is set to the start of each week and iterates through each day
        SimpleDateFormat sdfbutton = new SimpleDateFormat("dd");
        myCal.set(Calendar.DAY_OF_WEEK, myCal.getFirstDayOfWeek());
        for(int counter = 0; counter < 7; counter++)
        {

            String thisDay = sdfbutton.format(myCal.getTime());
            String thisDate = sdf2.format(myCal.getTime() );
            days[counter].setText(thisDay);
            buttonDate[counter] = thisDate;

            // if the day of the week is already passed make button unclickable
            if(currentCal.compareTo(myCal) > 0) {
                if(myCal.get(Calendar.DAY_OF_YEAR) < currentCal.get(Calendar.DAY_OF_YEAR)) {
                    days[counter].setTextColor(0xFFFF0000);
                    days[counter].setClickable(false);
                }

            }
            //else not
            else{
                days[counter].setTextColor(0xff00ffff);
                days[counter].setClickable(true);
            }

            // iterate through each day
            myCal.add(Calendar.DAY_OF_WEEK,1);
        }

    }


    private ArrayList<timeslot> createTimeSlot(int floor,String date, String[] Schedule)
    {
        String validationString = "Available";
        String myHour = "";
        int myHourInt;
        ArrayList<timeslot> timeslots = new ArrayList<timeslot>();
        dbManager.open();
        Cursor cursor;


        for (int counter = 0; counter < Schedule.length -1; counter++)
        {
            //
            SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yy");
            myHour = Schedule[counter + 1];
            myHour = myHour.substring(0,1);
            myHourInt = Integer.parseInt(myHour);
            cursor = dbManager.getRoomAvailability(Schedule[counter],Schedule[counter +1],date);
            cursor.moveToFirst();

            try {
                Date strDate = sdf3.parse(date);
                if(currentCal.get(Calendar.HOUR_OF_DAY) > myHourInt && (new Date().after(strDate)) ){

                    validationString = "Unavailable";
                }
                else{
                    validationString = "Available";
                }
            }catch (ParseException except){
                except.printStackTrace();
            }


            // checks if space is full
            if(cursor.getInt(0) >= slotLimit) {
                validationString ="Unavailable";
            }
            else{
                validationString ="Available";
            }

            //compares the hour of the end time of the booking and the current date to see if its available
            // add to array
            timeslot thisTimeslot = new timeslot(Schedule[counter],Schedule[counter +1],date,validationString,cursor.getInt(0),floor);
            timeslots.add(thisTimeslot);
        }

        dbManager.close();

        return timeslots;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);
        currFloor = pos;
        timeslots = createTimeSlot(currFloor,selectDate,timeSchedule);
        myAdapter.timeslots = timeslots;
        myAdapter.notifyDataSetChanged();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.mon:
                selectDate = buttonDate[0];
                break;

            case R.id.tue:
                selectDate = buttonDate[1];
                break;

            case R.id.wed:
                selectDate = buttonDate[2];
                break;

            case R.id.thur:
                selectDate = buttonDate[3];
                break;

            case R.id.fri:
                selectDate = buttonDate[4];
                break;

            case R.id.sat:
                selectDate = buttonDate[5];
                break;

            case R.id.sun:
                selectDate = buttonDate[6];
                break;
        }

        timeslots = createTimeSlot(currFloor,selectDate,timeSchedule);
        myAdapter.timeslots = timeslots;
        myAdapter.notifyDataSetChanged();
        String myInt = Integer.toString(currFloor);
        Log.i("This is the currentfloor:",myInt);
    }


    class slotAdapter extends ArrayAdapter<timeslot> {

        ArrayList<timeslot> timeslots;

        public slotAdapter(Context context, int textViewresourceId, ArrayList<timeslot> objects )
        {
            super(context,textViewresourceId,objects);
            this.timeslots = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;

            if(v == null) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.lib_timeslots, null);
            }

            timeslot currentTimeSlot = timeslots.get(position);

            if(currentTimeSlot != null) {
                String thisCount = Integer.toString(currentTimeSlot.getAvailableSpace());
                TextView timeText = (TextView) v.findViewById(R.id.slotTimeView);
                TextView availabilitytext = (TextView) v.findViewById(R.id.slotAvailabiltyView);
                TextView spaceText = (TextView) v.findViewById(R.id.slotSpaceView);
                TextView roomView = (TextView) v.findViewById(R.id.floorView);

                timeText.setText(currentTimeSlot.getStartTime()+"-"+currentTimeSlot.getEndTime() +"  "+currentTimeSlot.getDate());
                availabilitytext.setText(currentTimeSlot.getStatus());
                spaceText.setText(thisCount+"/"+slotLimit);
                String myfloor = Integer.toString(currentTimeSlot.getFloor());
                roomView.setText("floor " +myfloor);

            }
            return v;
        }
    }


}
