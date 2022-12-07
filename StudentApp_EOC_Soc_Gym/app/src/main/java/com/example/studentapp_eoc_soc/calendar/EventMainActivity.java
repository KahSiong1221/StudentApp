package com.example.studentapp_eoc_soc.calendar;


import static com.example.studentapp_eoc_soc.calendar.CalendarFun.getDays_In_Month;
import static com.example.studentapp_eoc_soc.calendar.CalendarFun.getMonth_Year;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.SaDbManager;


import java.time.LocalDate;
import java.util.ArrayList;

public class EventMainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    ImageButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);
        initWidgets();
        CalendarFun.selDate = LocalDate.now();
        setMonthView();
        loadDB();
/*
        menuButton = (ImageButton) findViewById(R.id.menuButton);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventMainActivity.this, SidebarMenu.class));
            }
        });


 */



    }


    private void loadDB() {
        CalendarDbManager DB = new CalendarDbManager(this);
        DB.open();
        DB.populateEventArray();
        DB.close();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {
        monthYearText.setText(getMonth_Year(CalendarFun.selDate));
        ArrayList<LocalDate> daysInMonth = getDays_In_Month();

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view)
    {
        CalendarFun.selDate = CalendarFun.selDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        CalendarFun.selDate = CalendarFun.selDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarFun.selDate = date;
            setMonthView();
        }
    }

    public void startWeekView(View view)
    {
        startActivity(new Intent(this, WeekViewActivity.class));
    }

}



