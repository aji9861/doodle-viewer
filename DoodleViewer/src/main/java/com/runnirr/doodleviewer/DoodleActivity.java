package com.runnirr.doodleviewer;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import com.runnirr.doodleviewer.display.SlidingLayer;
import com.runnirr.doodleviewer.fetcher.DataCollector;
import com.runnirr.doodleviewer.fetcher.LoadDoodlesAsync;
import com.runnirr.doodleviewer.messages.DoodleEventListener;
import com.runnirr.doodleviewer.messages.DoodleUIUpdater;
import com.runnirr.doodleviewer.settings.SettingsManager;
import com.runnirr.doodleviewer.settings.SharedPreferencesManager;

public class DoodleActivity extends Activity implements DoodleEventListener<Integer[]>{
    public static final String TAG = DoodleActivity.class.getSimpleName();
    private SlidingLayer mSlidingLayer;
    private DoodleUIUpdater mUIUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        mUIUpdater = new DoodleUIUpdater(this);
        DataCollector.getInstance().registerListener(mUIUpdater);

        mSlidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer);

        ActionBar ab = getActionBar();
        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }

        SettingsManager settingsManager = new SettingsManager(this);

        SharedPreferencesManager preferencesManager = SharedPreferencesManager.getInstance(this);
        LoadDoodlesAsync lda = new LoadDoodlesAsync(mUIUpdater);
        settingsManager.registerListener(this);
        lda.execute(preferencesManager.getStoredYear(), preferencesManager.getStoredMonth() + 1); /* convert from Jan = 0 to Jan = 1 */
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

    @Override
    public void onNewInformation(Integer[] data) {
        new LoadDoodlesAsync(mUIUpdater).execute(data);
    }

    public void layoutOnClick(){
        if(mSlidingLayer.isOpened()){
            mSlidingLayer.closeLayer(true);
        }
    }
}
