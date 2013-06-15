package com.runnirr.doodleviewer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import com.runnirr.doodleviewer.display.CardLayout;
import com.runnirr.doodleviewer.display.SlidingLayer;
import com.runnirr.doodleviewer.fetcher.DataCollector;
import com.runnirr.doodleviewer.fetcher.LoadDoodlesAsync;
import com.runnirr.doodleviewer.messages.DoodleUIUpdater;

public class DoodleActivity extends Activity {
    public static final String TAG = DoodleActivity.class.getSimpleName();
    private SlidingLayer mSlidingLayer;
    private CardLayout mCardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        DataCollector.getInstance().registerListener(new DoodleUIUpdater(this));
        mSlidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        new LoadDoodlesAsync().execute(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doodle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_settings:
            case android.R.id.home:
                if(mSlidingLayer.isOpened()){
                    mSlidingLayer.closeLayer(true);
                }else{
                    mSlidingLayer.openLayer(true);
                }
                return true;


        }

        return false;
    }
}
