package com.example.libraryapp;

public class User {
    private int Id;
    private String Name;
    private String Email;
    private String phoneNo;

    public User(int Id,String name, String email, String phoneNo) {
        this.Id = Id;
        this.Name = name;
        this.Email = email;
        this.phoneNo = phoneNo;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
