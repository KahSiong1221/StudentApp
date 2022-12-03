package com.example.studentapp_eoc;

public class User {
    private int userId;
    private String userName;

    public User(int userId, String userName) {
        setUserId(userId);
        setUserName(userName);
    }

    public User(String userName) {
        setUserName(userName);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
