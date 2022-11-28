package com.example.gymapp;

import java.util.HashSet;
import java.util.Set;

public class User {
    private int user_id;
    private String name;
    private String email;
    private int memCheck;
    Set<Membership> membership = new HashSet<>();
    Set<DayTimeslot> bookings = new HashSet<>();


    public User(String name, String email, int memCheck) {
        this.name = name;
        this.email = email;
        this.memCheck = memCheck;
    }

    public User() {

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addMembership(Membership m) {
        if (membership == null ) {
            this.membership.add(m);
        }
    }

    public void bookSession(DayTimeslot dTs) {
        if (dTs != null) {
            this.bookings.add(dTs);
        }
    }

    public int getMemCheck() {
        return memCheck;
    }

    public void setMemCheck(int memCheck) {
        this.memCheck = memCheck;
    }
}
