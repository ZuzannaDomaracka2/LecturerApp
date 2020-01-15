package com.example.lecturerapp;

public class NameHelper {

    public static String getFullNamefromEmail(String email){
        String[] arrOfStr = email.split("@", 2);
        String username = arrOfStr[0];
        String[] arrOfStr1 = username.split("\\.", 2);

        String name = arrOfStr1[0].substring(0, 1).toUpperCase() + arrOfStr1[0].substring(1);
        String surname = arrOfStr1[1].substring(0, 1).toUpperCase() + arrOfStr1[1].substring(1);
        return name + " " + surname;
    }
    }

