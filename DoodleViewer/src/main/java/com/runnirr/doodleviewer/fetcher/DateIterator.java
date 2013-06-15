package com.runnirr.doodleviewer.fetcher;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Adam on 6/13/13.
 */
public class DateIterator {

    // Earliest Doodle is October, 1999
    private final int EARLIEST_YEAR = 1999;
    private final int EARLIEST_MONTH = Calendar.OCTOBER;

    private final int year;
    private final int month;

    public DateIterator(){
        this.year = Calendar.getInstance().get(Calendar.YEAR);
        this.month = Calendar.getInstance().get(Calendar.MONTH);
    }

    public DateIterator(final int year, final int month){
        this.year = year;
        this.month = month;
    }


    public DateIterator next(){
        Calendar now = Calendar.getInstance();
        if(year > now.get(Calendar.YEAR) ||
            month >= now.get(Calendar.MONTH) && year == now.get(Calendar.YEAR) ){
            // We are at the most recent date or already passed it
            return null;
        }
        else if(month == Calendar.DECEMBER){
            // Changing years
            return new DateIterator(year + 1, Calendar.JANUARY);
        }else{
            // In the middle of a year
            return new DateIterator(year, month + 1);
        }
    }

    public DateIterator previous(){
        if(year < EARLIEST_YEAR ||
                year == EARLIEST_YEAR && month <= EARLIEST_MONTH){
            // No more Doodle to be found before this
            return null;
        }
        else if(month == Calendar.JANUARY){
            // Changing years
            return new DateIterator(year - 1, Calendar.DECEMBER);
        }else{
            // In the middle of a year
            return new DateIterator(year, month - 1);
        }
    }

    public int getMonth(){
        return month + 1;
    }

    public int getYear(){
        return year;
    }
}
