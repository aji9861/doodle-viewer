package com.runnirr.doodleviewer.settings;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.runnirr.doodleviewer.R;
import com.runnirr.doodleviewer.display.SlidingLayer;
import com.runnirr.doodleviewer.messages.SimpleDoodleEventTransmitter;

import java.util.Calendar;

/**
 * Created by Adam on 6/15/13.
 *
 * Manager for setting of the user. Notifies appropriate listeners
 */
public class SettingsManager extends SimpleDoodleEventTransmitter<Integer[]> implements SlidingLayer.OnInteractListener {
    private final Spinner mMonthSelector;
    private final Spinner mYearSelector;

    private Month[] mMonths;
    private Integer[] mYears;

    private boolean wasOpened = false;

    private final Context mContext;

    public SettingsManager(final Activity c){
        this.mMonthSelector = (Spinner) c.findViewById(R.id.monthSelector);
        this.mYearSelector = (Spinner) c.findViewById(R.id.yearSelector);
        this.mContext = c;

        ((SlidingLayer) c.findViewById(R.id.slidingLayer)).setOnInteractListener(this);

        populateMonthOptions();
        populateYearOptions();
    }

    private void populateMonthOptions(){
        mMonths = Month.values();
        ArrayAdapter<Month> monthsArrayAdapter = new ArrayAdapter<Month>(mContext,
                android.R.layout.simple_spinner_dropdown_item, mMonths);
        mMonthSelector.setAdapter(monthsArrayAdapter);
    }

    private void populateYearOptions(){
        int earliestYear = mContext.getResources().getInteger(R.integer.earliest_doodle_year);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int range = currentYear - earliestYear;
        Integer[] yearsArray = new Integer[range + 1];
        for(int i = 0; i <= range; i++){
            yearsArray[i] = earliestYear + i;
        }
        mYears = yearsArray;
        ArrayAdapter<Integer> yearsArrayAdapter = new ArrayAdapter<Integer>(mContext,
                android.R.layout.simple_spinner_dropdown_item, yearsArray);
        mYearSelector.setAdapter(yearsArrayAdapter);
    }

    private int findYearIndex(Integer[] list, int i){
        for(int j = 0; j < list.length; j++){
            if (list[j] == i){
                return j;
            }
        }
        return -1;
    }

    private int findMonthIndex(Month[] list, int i){
        for (int j = 0; j < list.length; j++){
            if (list[j].value == i){
                return j;
            }
        }
        return -1;
    }

    @Override
    public void onOpen() {
        // Update spinners with current values
        // if no values found, use today's time
        SharedPreferencesManager spm = SharedPreferencesManager.getInstance(mContext);
        int curMonth = spm.getStoredMonth();
        int curYear = spm.getStoredYear();

        mMonthSelector.setSelection(findMonthIndex(mMonths, curMonth));
        mYearSelector.setSelection(findYearIndex(mYears, curYear));
        wasOpened = true;
    }

    @Override
    public void onClose() {
        // Update storage with spinner values
        // notify listeners if they have changed
        if(!wasOpened){
            // When the app starts it triggers onClose()
            // This flag makes sure the user actually opened
            // the page so we don't save the defaults whenever
            // the app restarts
            return;
        }
        boolean toNotify = false;
        SharedPreferencesManager spm = SharedPreferencesManager.getInstance(mContext);

        int prevMonth = spm.getStoredMonth();
        int newMonth = mMonths[mMonthSelector.getSelectedItemPosition()].value;
        if(newMonth >= 0 && prevMonth != newMonth){
            spm.setStoredMonth(newMonth);
            toNotify = true;
            newMonth += 1; /* If it is a month (not all) change it to base 1 for use below */
        }

        int prevYear = spm.getStoredYear();
        int newYear = mYears[mYearSelector.getSelectedItemPosition()];
        if(prevYear >= 0 && prevYear != newYear){
            spm.setStoredYear(newYear);
            toNotify = true;
        }

        if(toNotify){
            notifyListeners(new Integer[] {newYear, newMonth});
        }

        wasOpened = false;

    }

    @Override
    public void onOpened() {
        // nothing
    }

    @Override
    public void onClosed() {
        // nothing
    }

    public enum Month {
        all(-1),
        January(Calendar.JANUARY),
        February(Calendar.FEBRUARY),
        March(Calendar.MARCH),
        April(Calendar.APRIL),
        May(Calendar.MAY),
        June(Calendar.JUNE),
        July(Calendar.JULY),
        August(Calendar.AUGUST),
        September(Calendar.SEPTEMBER),
        October(Calendar.OCTOBER),
        November(Calendar.NOVEMBER),
        December(Calendar.DECEMBER);

        public final int value;
        private Month(int v){
            value = v;
        }
    }

    /**
     * Integer for the month. January = 0
     */
    public static int getCurrentMonth(){
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static int getCurrentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}
