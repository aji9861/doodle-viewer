package com.runnirr.doodleviewer.display;

import android.content.Context;
import android.util.AttributeSet;
import android.util.EventLog;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by Adam on 6/15/13.
 */
public class MainLayout extends RelativeLayout {
    public MainLayout(Context context) {
        super(context);
    }

    public MainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO: don't just use hard coded index
        // TODO: this never gets called
        SlidingLayer sLayer = ((SlidingLayer) getChildAt(1));
        if(sLayer.isOpened()){
            sLayer.closeLayer(true);
        }
        return super.onTouchEvent(event);
    }
}
