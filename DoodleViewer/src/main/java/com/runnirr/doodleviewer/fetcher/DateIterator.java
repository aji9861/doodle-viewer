package com.runnirr.doodleviewer.fetcher;

import android.content.Context;

import com.runnirr.doodleviewer.R;

import java.util.Calendar;

/**
 * Created by Adam on 6/13/13.
 *
 * Utility class for going through date one month at a time
 */
public class DateIterator {
    private final int EARLIEST_YEAR;
    private final int EARLIEST_MONTH;

    public final int year;
    public final int month;

    private final Context mContext;

    public DateIterator(final Context c){
        this(c, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH));
    }

    public DateIterator(final Context c, final int year, final int month){
        EARLIEST_MONTH = c.getResources().getInteger(R.integer.earliest_doodle_month);
        EARLIEST_YEAR = c.getResources().getInteger(R.integer.earliest_doodle_year);
        this.year = year;
        this.month = month;
        mContext = c;
    }


    /**
     * @param lYear Year to go up to (inclusive) or null for no limit
     */
    public DateIterator next(Integer lYear){
        Calendar now = Calendar.getInstance();
        if(year > now.get(Calendar.YEAR) ||
            month >= now.get(Calendar.MONTH) && year == now.get(Calendar.YEAR) ){
            // We are at the most recent date or already passed it
            return null;
        }
        else if(month == Calendar.DECEMBER){
            // Changing years
            if(lYear != null && lYear >= year + 1){
                return new DateIterator(mContext, year + 1, Calendar.JANUARY);
            }
            return null;
        }else{
            // In the middle of a year
            return new DateIterator(mContext, year, month + 1);
        }
    }

    /**
     * @param lYear Year to go back to (inclusive), or null for no limit
     */
    public DateIterator previous(Integer lYear){
        if(year < EARLIEST_YEAR ||
                year == EARLIEST_YEAR && month <= EARLIEST_MONTH){
            // No more Doodle to be found before this
            return null;
        }
        else if(month == Calendar.JANUARY){
            // Changing years
            if(lYear != null && lYear <= year){
                return new DateIterator(mContext, year - 1, Calendar.DECEMBER);
            }
            return null;
        }else{
            // In the middle of a year
            return new DateIterator(mContext, year, month - 1);
        }
    }

}
