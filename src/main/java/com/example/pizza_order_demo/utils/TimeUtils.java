package com.example.pizza_order_demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat SDF2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    public static String translateTimeToString(long timeStamp){
        return SDF2.format(timeStamp);
    }

    public static String translateTimeToString(long timeStamp,String format){
        return simpleDateFormat.format(timeStamp);
    }


}
