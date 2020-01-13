package com.example.lecturerapp;

public class Information {
    public  String body;
    public  String lecturerName;
    public String date;
    public String id ;


    public Information(){

    }

    public Information(String id, String body, String lecturerName, String date) {
        this.id=id;
        this.body = body;
        this.lecturerName = lecturerName;
        this.date = date;
    }




    public String getBody() {
        return body;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }


}