package com.example.studentapp_eoc_soc.calendar;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.calendar.CalendarDbManager;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

//Activity for editing events - Inserting & Updating
public class EditEventActivity extends AppCompatActivity {

    //Declare Variables
    private EditText eventNameET;
    private EditText eventLocationET;
    private Button eventStartTimeButton;
    private Button eventEndTimeButton;
    private Button eventDeleteButton;
    private TextView eventDateTV;
    private Event selectedEvent;
    int starthour, startminute, endhour, endminute;
    String user_id = "C20424096";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initEditField(); //Initialise elements in xml
        eventDateTV.setText(CalendarFun.formatDate(CalendarFun.selDate)); //Set event text view
        CalendarDbManager DB = new CalendarDbManager(this);
        DB.open();
        checkEventEdit();
    }

    private void initEditField() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventStartTimeButton = findViewById(R.id.eventStartTimeButton);
        eventEndTimeButton = findViewById(R.id.eventEndTimeButton);
        eventLocationET = findViewById(R.id.eventLocationET);
        eventDeleteButton = findViewById(R.id.eventDeleteButton);

    }

    //Function to check whether the event is being created for the first time or being edited
    private void checkEventEdit() {
        Intent previousIntent = getIntent(); //Fetch previous intent

        //If the user clicks the event contained in the event list view, pass that events id - i.e. edit that event
        // Start reference https://developer.android.com/reference/android/content/Intent
        int prevEventId = previousIntent.getIntExtra(Event.EVENT_EDIT_EXTRA, -1);
        selectedEvent = Event.getEvent_Based_Id(prevEventId);
        //End reference

        //If an id is not passed, hide delete button, else set xml elements to appropriate values
        if (selectedEvent == null) {
            eventDeleteButton.setVisibility(View.INVISIBLE);
        } else {
            eventNameET.setText(selectedEvent.getName());
            eventDateTV.setText(CalendarFun.formatDate(selectedEvent.getDate()));
            eventStartTimeButton.setText(selectedEvent.getStart_Time());
            eventEndTimeButton.setText(selectedEvent.getEnd_Time());
            eventLocationET.setText(selectedEvent.getLocation());

        }
    }




    public void showStartTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            starthour = selectedHour;
            startminute = selectedMinute;
            eventStartTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", starthour, startminute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,  onTimeSetListener, starthour, startminute, true);

        timePickerDialog.setTitle("Select Start Time");
        timePickerDialog.show();
    }

    public void showEndTimePicker(View view) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            endhour = selectedHour;
            endminute = selectedMinute;
            eventEndTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", endhour, endminute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, endhour, endminute, true);

        timePickerDialog.setTitle("Select End Time");
        timePickerDialog.show();
    }

    //Function to save Event locally and add its data to SQlite database
    public void saveEvent(View view) {

        CalendarDbManager DB = new CalendarDbManager(this);
        DB.open();

        int id = ThreadLocalRandom.current().nextInt(0, 9999 + 1);
        String eventName = eventNameET.getText().toString();
        String eventStartTime = eventStartTimeButton.getText().toString();
        String eventEndTime = eventEndTimeButton.getText().toString();
        String eventLocation = eventLocationET.getText().toString();


        if (selectedEvent == null) {
            Event newEvent = new Event(id, eventName, eventStartTime, eventEndTime, eventLocation, CalendarFun.selDate, null);

            DB.insertEvent(newEvent, user_id);

            Event.eventsList.add(newEvent);
        } else {
            selectedEvent.setName(eventName);
            selectedEvent.setStart_time(eventStartTime);
            selectedEvent.setEnd_time(eventEndTime);
            selectedEvent.setLocation(eventLocation);
            selectedEvent.setDate(CalendarFun.selDate);

            DB.editEvent(selectedEvent, user_id);
        }
        DB.close();
        finish();


    }

    //Function to delete selected event
    public void deleteEvent(View view) {
        selectedEvent.setDeleted("True"); //Set delete flag to true
        CalendarDbManager DB = new CalendarDbManager(this); //Creates an instance of EventDBManager
        DB.open();
        DB.deleteEvent(selectedEvent); //Passes selectedEvent to the delete function
        DB.close();
        finish();
    }


}