package com.example.student_app.gym_portal;

public class UserBookings {
    private int user_book_id;
    private int user_id;
    private int gymSlot_id;
    private String type;

    public UserBookings(int user_id, int gymSlot_id, String type) {
        this.user_id = user_id;
        this.gymSlot_id = gymSlot_id;
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGymSlot_id() {
        return gymSlot_id;
    }

    public void setGymSlot_id(int gymSlot_id) {
        this.gymSlot_id = gymSlot_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUser_book_id() {
        return user_book_id;
    }

    public void setUser_book_id(int user_book_id) {
        this.user_book_id = user_book_id;
    }
}
