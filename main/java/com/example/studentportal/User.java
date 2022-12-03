package com.example.studentportal;

public class User {
    int _id;
    String _name;

    public User(int id, String name){
        this._id = id;
        this._name = name;
    }

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
