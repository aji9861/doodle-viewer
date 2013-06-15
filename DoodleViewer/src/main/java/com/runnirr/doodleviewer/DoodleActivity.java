package com.runnirr.doodleviewer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.runnirr.doodleviewer.fetcher.DataCollector;
import com.runnirr.doodleviewer.fetcher.LoadDoodlesAsync;
import com.runnirr.doodleviewer.messages.DoodleUIUpdater;

public class DoodleActivity extends Activity {
    public static final String TAG = "GoogleDoodle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view_layout);

        DataCollector.getInstance().registerListener(new DoodleUIUpdater(this));

        new LoadDoodlesAsync().execute(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doodle, menu);
        return true;
    }

}
