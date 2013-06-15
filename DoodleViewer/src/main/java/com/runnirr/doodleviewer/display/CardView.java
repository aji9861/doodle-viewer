package com.runnirr.doodleviewer.display;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Adam on 6/15/13.
 */
public class CardView extends LinearLayout implements Comparable<CardView> {
    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int compareTo(CardView other) {
        if (this == other){
            return 0;
        } else{
            return 1;
        }
    }
}
