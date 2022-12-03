package com.example.studentportal;

public class enrollment {

    //enrollment fields
    int _esid;
    int _esocid;
    String _num;

    //constructor
    public enrollment(int esid, int esocid, String num){
        this._esid = esid;
        this._esocid = esocid;
        this._num = num;
    }

    //getters and setters
    public enrollment(String _num) {
        this._num = _num;
    }

    public int get_esid() {
        return _esid;
    }

    public void set_sid(int esid) {
        this._esid = esid;
    }


    public int get_esocid() {
        return _esocid;
    }

    public void set_esocid(int esocid) {
        this._esocid = esocid;
    }


    public String get_num() {
        return _num;
    }

    public void set_num(String num) {
        this._num = num;
    }

}
