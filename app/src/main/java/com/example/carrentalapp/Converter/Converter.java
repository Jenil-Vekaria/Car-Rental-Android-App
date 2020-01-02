package com.example.carrentalapp.Converter;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class Converter {

    @TypeConverter
    public static Calendar timestampToCalendar(Long value){
        Calendar calendar = Calendar.getInstance();
        if(value == null)
            return null;
        else{
            calendar.setTimeInMillis(value);
            return calendar;
        }
    }

    @TypeConverter
    public static long CalendarToTimestamp(Calendar calendar){
        return calendar == null? null : calendar.getTimeInMillis();
    }

}
