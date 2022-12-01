package com.example.gymapp;

public class DayTimeslot {
    private int gymSlot_id;
    private String day;
    private String time_start;
    private String time_end;
    private String type;

    public DayTimeslot(String day, String time_start, String time_end, String type) {
        this.day = day;
        this.time_start = time_start;
        this.time_end = time_end;
        this.type = type;
    }

    public DayTimeslot() {

    }

    //Getters and Setters
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGymSlot_id() {
        return gymSlot_id;
    }

    public void setGymSlot_id(int gymSlot_id) {
        this.gymSlot_id = gymSlot_id;
    }
}
