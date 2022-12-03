package com.example.studentportal;

public class User {

    //User fields
    int _id;
    String _name;

    //constructor
    public User(int id, String name){
        this._id = id;
        this._name = name;
    }

    //getters and setters
    public User(String name){
        this._name = _name;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String name) {
        this._name = name;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        this._id = id;
    }
}
