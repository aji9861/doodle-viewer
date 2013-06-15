package com.runnirr.doodleviewer.fetcher;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class LoadDoodleImageAsync extends AsyncTask<DoodleData, Void, Bitmap> {

    private DoodleData data;

	@Override
	protected Bitmap doInBackground(DoodleData... params) {
        Bitmap doodleImage = null;
        data = params[0];
		
		try {
			URL imageURL = new URL("http:" + data.url);
			doodleImage = BitmapFactory.decodeStream((InputStream) imageURL.getContent() );	
		} catch(Exception e){ 
		}
		return doodleImage;

    }

    @Override
    protected void onPostExecute(Bitmap b){
        data.setImage(b);
        DataCollector.getInstance().add(data);
    }




}
