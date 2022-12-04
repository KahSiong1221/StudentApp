package com.example.student_app.calendar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Event {
    //Global Event Variables
    public static ArrayList<Event> eventsList = new ArrayList<>();
    public static String EVENT_EDIT_EXTRA = "eventEdit";


    //Event Object Variables
    private int id;
    private String name;
    private String start_time;
    private String end_time;
    private String location;
    private LocalDate date;
    private String deleted;

    //Event Constructor
    public Event(int id, String name, String start_time, String end_time, String location, LocalDate date, String deleted) {
        this.id = id;
        this.name = name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.location = location;
        this.date = date;
        this.deleted = deleted; //Event delete flag
    }

    //Event Getters & Setters
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStart_Time() {
        return start_time;
    }

    public String getEnd_Time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }


    /* Event Functions:
       These provide a variety of different uses, specifics of which I have outlined in each
     */

    public static ArrayList<Event> getEvent_Based_Date(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        date.format(formatter);
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : getCurr_Events()) {
            if (event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }

    /*
       Facilitates selection of event to be edited.
       User activates an onclick method in the weekview activity that passes the event id to this function
       It then finds the event that this corresponds to in the EventArray
     */
    public static Event getEvent_Based_Id(int passedEventID) {
        for (Event event : eventsList) {
            if (event.getId() == passedEventID) {
                return event;
            }
        }
        return null;
    }


    /*
        Returns an array list of events where the deleted flag is not true.
        This repopulates the event list view after the delete function has been called.
     */
    public static ArrayList<Event> getCurr_Events() {
        ArrayList<Event> nonDeleted = new ArrayList<>();
        for (Event note : eventsList) {
            if (note.getDeleted() == null)
                nonDeleted.add(note);
        }

        return nonDeleted;
    }

}

