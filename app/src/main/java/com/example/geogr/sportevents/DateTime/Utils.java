package com.example.geogr.sportevents.DateTime;

import java.text.SimpleDateFormat;

/**
 * Created by geogr on 22.11.2017.
 */

public class Utils {
    public static String getDate (long date){
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd.MM.yy");
        return  dateFormat.format(date);
    }
    public static String getTime (long time){
        SimpleDateFormat timeFormat=new SimpleDateFormat("HH.mm");
        return timeFormat.format(time) ;
    }
    public static String getFullDate(long date){
        SimpleDateFormat fulltimeFormat=new SimpleDateFormat("dd.MM.yy  HH.mm");
        return fulltimeFormat.format(date) ;
    }
}
