package com.example.studentapp_eoc_soc;

public class User {
    private int userId;
    private String userName;
    private String email;
    private String phoneNo;
    private int memCheck;

    public User() {

    }

    public User(String userName, String email, String phoneNo, int memCheck) {
        setUserName(userName);
        setEmail(email);
        setPhoneNo(phoneNo);
        setMemCheck(memCheck);
    }

    public User(int userId, String userName, String email, String phoneNo, int memCheck) {
        setUserId(userId);
        setUserName(userName);
        setEmail(email);
        setPhoneNo(phoneNo);
        setMemCheck(memCheck);
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

    public int getMemCheck() {
        return memCheck;
    }

    public void setMemCheck(int memCheck) {
        this.memCheck = memCheck;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
