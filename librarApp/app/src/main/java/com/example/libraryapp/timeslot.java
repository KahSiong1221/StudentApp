package com.example.libraryapp;

public class timeslot {
    private String startTime;
    private String endTime;
    private String status;
    private String Date;
    private int availableSpace;
    private int floor;

    public timeslot(String startTime, String endTime, String Date, String status, int availableSpace, int floor) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.Date = Date;
        this.status = status;
        this.availableSpace = availableSpace;
        this.floor = floor;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAvailableSpace() {
        return availableSpace;
    }

    public void setAvailableSpace(int availableSpace) {
        this.availableSpace = availableSpace;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

}
