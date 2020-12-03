package com.example.mydiary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Tools {
    public static String getDateTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String dateTime = simpleDateFormat.format(new Date());

        return dateTime;
    }

}
