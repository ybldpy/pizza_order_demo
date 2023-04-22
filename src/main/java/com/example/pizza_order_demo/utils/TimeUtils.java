package com.example.pizza_order_demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {



    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String translateTimeToString(long timeStamp){
        return sdf.format(timeStamp);
    }


}
