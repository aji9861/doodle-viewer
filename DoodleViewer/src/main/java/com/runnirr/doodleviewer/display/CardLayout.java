package com.runnirr.doodleviewer.display;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.runnirr.doodleviewer.R;

public class CardLayout extends LinearLayout implements OnGlobalLayoutListener {

    private static final String TAG = CardLayout.class.getSimpleName();

	public CardLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayoutObserver();

	}

	public CardLayout(Context context) {
		super(context);
		initLayoutObserver();
	}

	private void initLayoutObserver() {
		setOrientation(LinearLayout.VERTICAL);
		ViewTreeObserver vto = getViewTreeObserver();
        if (vto != null){
            vto.addOnGlobalLayoutListener(this);
        }
	}

	@Override
	public void onGlobalLayout() {
        ViewTreeObserver vto = getViewTreeObserver();
        if (vto != null){
            vto.removeGlobalOnLayoutListener(this);
        }

        final Context c = getContext();
        if (c == null) {
            return;
        }

		final int heightPx = c.getResources().getDisplayMetrics().heightPixels;

		boolean inversed = false;
		final int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
            if(child == null){
                continue;
            }

			int[] location = new int[2];

			child.getLocationOnScreen(location);

			if (location[1] > heightPx) {
				break;
			}


			if (!inversed) {
                Animation animation = AnimationUtils.loadAnimation(c, R.anim.slide_up_left);
                if(animation != null){
				    child.startAnimation(animation);
                }
			} else {
                Animation animation = AnimationUtils.loadAnimation(c, R.anim.slide_up_right);
                if(animation != null){
				    child.startAnimation(animation);
                }
			}

			inversed = !inversed;
		}

	}

}
