package com.runnirr.doodleviewer.messages;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.runnirr.doodleviewer.R;
import com.runnirr.doodleviewer.fetcher.DoodleData;

import java.text.SimpleDateFormat;

/**
 * Created by Adam on 6/14/13.
 */
public class DoodleUIUpdater implements DoodleEventListener {

    private static final String TAG = DoodleUIUpdater.class.getSimpleName();

    private final Activity mActivity;

    public DoodleUIUpdater(Activity a){
        mActivity = a;
    }

    @Override
    public void onNewDoodleLoaded(final DoodleData dd) {
        Runnable r = new Runnable() {
            public void run() {
                addView(dd);
            }
        };

        mActivity.runOnUiThread(r);

    }


    private void addView(final DoodleData dd){
        LayoutInflater vi = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.card_view, null);
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
            standaloneView.setVisibility(View.VISIBLE);
        }else{
            standaloneView.setVisibility(View.GONE);
        }

        // Image
        ImageView imageView = (ImageView) v.findViewById(R.id.doodleImage);
        imageView.setImageBitmap(dd.getImage());

        // Content
        WebView contentView = (WebView) v.findViewById(R.id.doodleContent);
        if (dd.blog_text.length() <= 0){
            contentView.setVisibility(View.GONE);
        }else{
            contentView.loadDataWithBaseURL(baseUrl, dd.blog_text, "text/html", "UTF-8", null);
            contentView.setVisibility(View.VISIBLE);
        }

        // insert into main view
        ViewGroup insertPoint = (ViewGroup) mActivity.findViewById(R.id.cardContainer);
        insertPoint.addView(v, insertPoint.getChildCount(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

}
