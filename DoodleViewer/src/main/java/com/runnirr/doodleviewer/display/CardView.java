package com.runnirr.doodleviewer.display;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CardView extends LinearLayout implements Comparable<CardView> {

    private Long mStartTime = 0L;

    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /*package*/ Long getStart(){
        return mStartTime;
    }

    public void setStartTime(Long time){
        mStartTime = time;
    }

    @Override
    public int compareTo(CardView other) {
        return getStart().compareTo(other.getStart());
    }
}
