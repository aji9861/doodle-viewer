package com.runnirr.doodleviewer.messages;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.runnirr.doodleviewer.R;
import com.runnirr.doodleviewer.display.CardView;
import com.runnirr.doodleviewer.fetcher.DoodleData;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Adam on 6/14/13.
 */
public class DoodleUIUpdater implements DoodleEventListener<DoodleData> {

    private static final String TAG = DoodleUIUpdater.class.getSimpleName();

    private final Activity mActivity;
    private final View mLoadingSpinner;
    private final ViewGroup mCardContainer;
    // Semaphore for updating the UI
    private final Semaphore mUIFlag = new Semaphore(1);

    public DoodleUIUpdater(Activity a){
        mActivity = a;
        mLoadingSpinner = mActivity.findViewById(R.id.loadingSpinnerContainer);
        mCardContainer = (ViewGroup) mActivity.findViewById(R.id.cardContainer);
    }

    @Override
    public void onNewInformation(final DoodleData dd) {
        Runnable r = new Runnable() {
            public void run() {
                addCardView(dd);
                mUIFlag.release();
            }
        };

        try{
            mUIFlag.acquire();
            mActivity.runOnUiThread(r);
        } catch (InterruptedException e){
            Log.e(TAG, e.getMessage(), e);
        }

    }

    public void resetView(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                mLoadingSpinner.setVisibility(View.VISIBLE);
                mCardContainer.removeViews(1, mCardContainer.getChildCount() - 1); /* loadingSpinner is element 0 */
                mUIFlag.release();
            }
        };

        try{
            mUIFlag.acquire();
            mActivity.runOnUiThread(r);
        } catch (InterruptedException e){
            Log.e(TAG, e.getMessage(), e);
        }


    }

    /**
     * This should only get called from the onNewInformation method
     * @param dd
     */
    private void addCardView(final DoodleData dd){
        LayoutInflater vi = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView v = (CardView) vi.inflate(R.layout.card_view, null);
        final String baseUrl = mActivity.getResources().getString(R.string.google_base);

        // fill in any details dynamically here
        // Title
        TextView titleView = (TextView) v.findViewById(R.id.doodleTitle);
        titleView.setText(dd.title);

        // Date
        TextView dateView = (TextView) v.findViewById(R.id.doodleDate);
        dateView.setText(dd.getDateString());

        // Standalone
        WebView standaloneView = (WebView) v.findViewById(R.id.doodleStandalone);
        if(dd.standalone_html != null && !dd.standalone_html.isEmpty()){
            standaloneView.loadUrl(baseUrl + dd.standalone_html);
            standaloneView.getSettings().setJavaScriptEnabled(true);
            standaloneView.setVisibility(View.VISIBLE);
        }else{
            standaloneView.setVisibility(View.GONE);
            standaloneView.getSettings().setJavaScriptEnabled(false);
        }

        // Image
        ImageView imageView = (ImageView) v.findViewById(R.id.doodleImage);
        imageView.setImageBitmap(dd.getImage());

        // Content
        WebView contentView = (WebView) v.findViewById(R.id.doodleContent);
        if (!dd.blog_text.isEmpty()){
            contentView.loadDataWithBaseURL(baseUrl, dd.blog_text, "text/html", "UTF-8", null);
            standaloneView.getSettings().setJavaScriptEnabled(true);
            contentView.setVisibility(View.VISIBLE);
        }else{
            contentView.setVisibility(View.GONE);
            standaloneView.getSettings().setJavaScriptEnabled(false);
        }

        if(mLoadingSpinner.getVisibility() == View.VISIBLE){
            mLoadingSpinner.setVisibility(View.GONE);
        }
        // insert into main view
        addViewInStartOrder(v);
    }


    //TODO: insert  in date order
    private void addViewInStartOrder(CardView cv){
        int insertIndex = mCardContainer.getChildCount();

        mCardContainer.addView(cv, insertIndex,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
