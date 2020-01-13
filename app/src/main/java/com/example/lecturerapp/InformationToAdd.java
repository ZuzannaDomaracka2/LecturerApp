package com.example.lecturerapp;

public class InformationToAdd {
   public  String body;
   public  String lecturerName;
   public String date;


public InformationToAdd(){

}

    public InformationToAdd(String body, String lecturerName, String date) {
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

}
