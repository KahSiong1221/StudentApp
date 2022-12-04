package com.example.student_app.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//Class for general use utility functions
public class CalendarFun {
    //LocalDate variable for storing the users selected date
    public static LocalDate selDate;

    //Localdate Formatter - Takes localdate as an input and changes the format to day - month - year
    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    //Takes local date as an input and returns the month & year
    public static String getMonth_Year(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    //Returns an array list of days in a month - used for monthly calender display
    public static ArrayList<LocalDate> getDays_In_Month() {
        //Initialise variables
        ArrayList<LocalDate> Day_In_Month_Arr = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(selDate);

        int daysInMonth = yearMonth.lengthOfMonth(); //calculate days in a month

        //Variables for previous and next months
        LocalDate prevMonth = selDate.minusMonths(1);
        LocalDate nextMonth = selDate.plusMonths(1);

        //Variable to store the length of previous month
        YearMonth prevYearMonth = YearMonth.from(prevMonth);
        int prevDaysInMonth = prevYearMonth.lengthOfMonth();

        //Variable to find the first day of the week
        LocalDate firstOfMonth = CalendarFun.selDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        //For loop for adding values to array list - ran 42 times (Amount is for the month + greyed out dates ~+/- 4 days of it, will always equal 42)
        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek)
                Day_In_Month_Arr.add(LocalDate.of(prevMonth.getYear(), prevMonth.getMonth(), prevDaysInMonth + i - dayOfWeek));
            else if (i > daysInMonth + dayOfWeek)
                Day_In_Month_Arr.add(LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), i - dayOfWeek - daysInMonth));
            else
                Day_In_Month_Arr.add(LocalDate.of(selDate.getYear(), selDate.getMonth(), i - dayOfWeek));
        }
        return Day_In_Month_Arr;
    }

    //Similar to above in functionality - returns days in a week instead
    public static ArrayList<LocalDate> getDays_In_Week(LocalDate selectedDate) {
        //Initialise variables
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = getSunday(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        //While loop to add days to arraylist
        while (current.isBefore(endDate)) {
            days.add(current);
            current = current.plusDays(1);
        }
        return days;
    }

    //Function to find what day in that month is sunday
    private static LocalDate getSunday(LocalDate currDate) {
        //Initialise variable
        LocalDate prevWeek = currDate.minusWeeks(1);

        //While loop to validate whether selected day of the week == "SUNDAY"
        while (currDate.isAfter(prevWeek)) {
            if (currDate.getDayOfWeek() == DayOfWeek.SUNDAY)
                return currDate;

            currDate = currDate.minusDays(1);
        }

        return null;
    }


}