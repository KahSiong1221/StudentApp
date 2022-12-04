package com.example.libraryapp;

public class roombooking {

    private int bookingID;
    private String computerName;
    private String startTime;
    private String endTime;
    private String Date;
    private String status;
    private int roomID;


    public roombooking(int bookingID, String computerName, String startTime, String endTime,  String date,String status, int roomID) {
        this.bookingID = bookingID;
        this.computerName = computerName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.Date = date;
        this.status = status;
        this.roomID = roomID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
}
