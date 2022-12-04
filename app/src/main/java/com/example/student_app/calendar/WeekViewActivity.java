package com.example.student_app.calendar;

import static com.example.student_app.calendar.CalendarFun.getDays_In_Week;
import static com.example.student_app.calendar.CalendarFun.getMonth_Year;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_app.R;
import com.example.student_app.gym_portal.SidebarMenu;

import java.time.LocalDate;
import java.util.ArrayList;


public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    ImageButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();
        setOnClickListener();

        menuButton = (ImageButton) findViewById(R.id.menuButton);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeekViewActivity.this, SidebarMenu.class));
            }
        });
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(getMonth_Year(CalendarFun.selDate));
        ArrayList<LocalDate> days = getDays_In_Week(CalendarFun.selDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
}


    public void previousWeekAction(View view)
    {
        CalendarFun.selDate = CalendarFun.selDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarFun.selDate = CalendarFun.selDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarFun.selDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdapter();
    }


    private void setEventAdapter()
    {
        ArrayList<Event> dailyEvents = Event.getEvent_Based_Date(CalendarFun.selDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EditEventActivity.class));
    }

    private void setOnClickListener()
    {
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Event selectedEvent = (Event) eventListView.getItemAtPosition(position);
                Intent editEntryIntent = new Intent(getApplicationContext(), EditEventActivity.class);
                editEntryIntent.putExtra(Event.EVENT_EDIT_EXTRA, selectedEvent.getId());
                startActivity(editEntryIntent);
            }
        });
    }

    public void startMonthView(View view) {
        startActivity(new Intent(this, EventMainActivity.class));
    }


}