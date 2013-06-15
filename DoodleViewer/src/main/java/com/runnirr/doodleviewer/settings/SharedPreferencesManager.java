package com.runnirr.doodleviewer.settings;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Adam on 6/15/13.
 */
public class SharedPreferencesManager {

    private final String DATE_PREF_NAME = "MOST_RECENT_DATE";
    private final String MONTH_KEY = "RECENT_MONTH";
    private final String YEAR_KEY = "RECENT_YEAR";

    private final Context mContext;
    private final SharedPreferences mDateSharedPreferences;

    private static SharedPreferencesManager self = null;

    private SharedPreferencesManager(final Context c){
        this.mContext = c;
        mDateSharedPreferences = c.getSharedPreferences(DATE_PREF_NAME, Context.MODE_PRIVATE);
    }

    public synchronized static SharedPreferencesManager getInstance(final Context c){
        if(self == null) {
            self = new SharedPreferencesManager(c);
        }
        return self;
    }

    public int getStoredYear(){
        return mDateSharedPreferences.getInt(YEAR_KEY, SettingsManager.getCurrentYear());
    }

    public int getStoredMonth(){
        return mDateSharedPreferences.getInt(MONTH_KEY, SettingsManager.getCurrentMonth());
    }

    public void setStoredYear(int year){
        mDateSharedPreferences.edit().putInt(YEAR_KEY, year);
    }

    public void setStoredMonth(int month){
        mDateSharedPreferences.edit().putInt(MONTH_KEY, month);
    }

}