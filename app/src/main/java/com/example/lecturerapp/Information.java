package com.example.lecturerapp;

import android.widget.EditText;

public class Information {
   public  String text;
   public  String name;
   public String date;
   public String adate;
   public String id ;
   public String currentDate;

public Information(){

}

    public Information( String text, String name,String date,String currentDate) {
        this.id=id;
        this.text = text;
        this.name = name;
        this.date = date;
        this.adate=adate;
        this.currentDate=currentDate;

    }




    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getAdate() {
        return adate;
    }

    public String getId() {
        return id;
    }
    public String getCurrentDate() {
    return  currentDate;
    }
}
