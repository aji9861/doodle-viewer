package com.runnirr.doodleviewer.fetcher;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class LoadDoodleImageAsync extends AsyncTask<DoodleData, Void, Bitmap> {

    private static final String TAG  = LoadDoodleImageAsync.class.getSimpleName();

    private DoodleData data;

	@Override
	protected Bitmap doInBackground(DoodleData... params) {
        Bitmap doodleImage = null;
        data = params[0];
		if(data.url != null){
            try {
                URL imageURL = new URL("http:" + data.url);
                doodleImage = BitmapFactory.decodeStream((InputStream) imageURL.getContent() );
            } catch(Exception e){
                Log.e(TAG, e.getMessage(), e);
            }
        }
		return doodleImage;

    }

    @Override
    protected void onPostExecute(Bitmap b){
        data.setImage(b);
        DataCollector.getInstance().add(data);
    }




}
