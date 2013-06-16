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

/**
 * Created by Adam on 6/14/13.
 *
 * Responsible for updating the UI when various events occurr
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
     * @param dd DoodleData with information to add to the display
     */
    private void addCardView(final DoodleData dd){
        LayoutInflater vi = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView v = (CardView) vi.inflate(R.layout.card_view, null);
        if(v == null){
            return;
        }
        final String baseUrl = mActivity.getResources().getString(R.string.google_base);

        // fill in any details dynamically here
        // Title
        TextView titleView = (TextView) v.findViewById(R.id.doodleTitle);
        if(dd.title != null){
            titleView.setText(dd.title);
            titleView.setVisibility(View.VISIBLE);
        }else{
            titleView.setVisibility(View.GONE);
        }

        // Date
        TextView dateView = (TextView) v.findViewById(R.id.doodleDate);
        if(dd.getDateString() != null){
            dateView.setText(dd.getDateString());
            dateView.setVisibility(View.VISIBLE);
        }else{
            dateView.setVisibility(View.GONE);
        }

        // Start
        if(dd.getStartMilli() != null){
            v.setStartTime(dd.getStartMilli());
        }

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
        if(dd.getImage() != null){
            imageView.setImageBitmap(dd.getImage());
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.GONE);
        }

        // Content

        WebView contentView = (WebView) v.findViewById(R.id.doodleContent);
        if (dd.blog_text != null &&!dd.blog_text.isEmpty()){
            contentView.loadDataWithBaseURL(baseUrl, dd.blog_text, "text/html", "UTF-8", null);
            contentView.getSettings().setJavaScriptEnabled(true);
            contentView.setVisibility(View.VISIBLE);
        }else{
            contentView.setVisibility(View.GONE);
            contentView.getSettings().setJavaScriptEnabled(false);
        }


        if(mLoadingSpinner.getVisibility() == View.VISIBLE){
            mLoadingSpinner.setVisibility(View.GONE);
        }
        // insert into main view
        addViewInStartOrder(v);
    }


    //TODO: insert  in date order
    private void addViewInStartOrder(CardView cv){
        int insertIndex;
        for( insertIndex = 0; insertIndex < mCardContainer.getChildCount(); insertIndex++){
            View cChild = mCardContainer.getChildAt(insertIndex);

            if(cChild instanceof CardView && cv.compareTo((CardView) cChild) >= 0){
                break;
            }
        }


        mCardContainer.addView(cv, insertIndex,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
