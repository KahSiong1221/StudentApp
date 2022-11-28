package com.example.gymapp;

public class Membership {
    private int membership_id;
    private String memType;
    private String price;

    public Membership(String memType, String price) {
        this.memType = memType;
        this.price = price;
    }

    public Membership() {

    }

    public int getMembership_id() {
        return membership_id;
    }

    public void setMembership_id(int membership_id) {
        this.membership_id = membership_id;
    }

    public String getMemType() {
        return memType;
    }

    public void setMemType(String memType) {
        this.memType = memType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
