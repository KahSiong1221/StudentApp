package com.example.student_app;

public class User {
    private int userId;
    private String userName;
    private String email;
    private String phoneNo;

    public User(int userId, String userName, String email, String phoneNo) {
        setUserId(userId);
        setUserName(userName);
        setEmail(email);
        setPhoneNo(phoneNo);
    }

    public User(String userName, String email, String phoneNo) {
        setUserName(userName);
        setEmail(email);
        setPhoneNo(phoneNo);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
