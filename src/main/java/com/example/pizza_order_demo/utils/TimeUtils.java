package com.example.pizza_order_demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {





    public static String translateTimeToString(long timeStamp){
        return translateTimeToString(timeStamp,"yyyy-MM-dd HH:mm");
    }

    public static String translateTimeToString(long timeStamp,String format){
        return new SimpleDateFormat(format).format(timeStamp);
    }


}
