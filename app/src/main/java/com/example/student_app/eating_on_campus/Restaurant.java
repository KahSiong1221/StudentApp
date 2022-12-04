package com.example.student_app.eating_on_campus;

public class Restaurant {
    private int restId;
    private String restName;
    private String address;
    private String phoneNo;
    private String openTime;
    private String closeTime;

    public Restaurant() {}

    public Restaurant(String restName, String address, String phoneNo, String openTime, String closeTime) {
        setRestName(restName);
        setAddress(address);
        setPhoneNo(phoneNo);
        setOpenTime(openTime);
        setCloseTime(closeTime);
    }

    public Restaurant(int restId, String restName, String address, String phoneNo, String openTime, String closeTime) {
        setRestId(restId);
        setRestName(restName);
        setAddress(address);
        setPhoneNo(phoneNo);
        setOpenTime(openTime);
        setCloseTime(closeTime);
    }

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }
}
