/**
 * Copied from: http://stackoverflow.com/questions/4677269/how-to-stretch-three-images-across-the-screen-preserving-aspect-ratio
 */
package com.runnirr.doodleviewer.fetcher;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DoodleBanner extends ImageView {

    public DoodleBanner(Context context) {
        super(context);
    }

    public DoodleBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DoodleBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (widthMeasureSpec != 0 && heightMeasureSpec != 0){
            int width = MeasureSpec.getSize(widthMeasureSpec);
            Drawable d = getDrawable();
            if (d != null){
                int height = width * d.getIntrinsicHeight() / d.getIntrinsicWidth();
                setMeasuredDimension(width, height);
            }
        }
    }
}