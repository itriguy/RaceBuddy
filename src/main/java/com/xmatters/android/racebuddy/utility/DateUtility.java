package com.xmatters.android.racebuddy.utility;

import java.util.Calendar;
import java.util.Date;

/**
 * Basic Date Utility used to simplify date picker.
 */
public class DateUtility {

    public static int getCurrentYear(){
        return getCalendar().get(Calendar.YEAR);
    }

    public static int getCurrentMonth(){
        return getCalendar().get(Calendar.MONTH);
    }

    public static int getCurrentDay(){
        return getCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public static int parseYear(Date date){
        return getCalendar(date).get(Calendar.YEAR);
    }

    public static int parseMonth(Date date){
        return getCalendar(date).get(Calendar.MONTH);
    }

    public static int parseDay(Date date){
        return getCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    public static Date convertToDate(int year, int month, int day){
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    public static String convertToString(int year, int month, int day){
        return year+"-"+month+"-"+day;
    }

    private static Calendar getCalendar(){
        return getCalendar(new Date());
    }

    private static Calendar getCalendar(Date date){
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
}
