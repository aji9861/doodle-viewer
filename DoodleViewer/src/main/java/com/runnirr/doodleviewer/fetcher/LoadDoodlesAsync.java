package com.runnirr.doodleviewer.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.runnirr.doodleviewer.DoodleActivity;

public class LoadDoodlesAsync extends AsyncTask<DoodleActivity, Void, Void> {

    private DoodleActivity mActivity;

	@Override
	protected Void doInBackground(DoodleActivity... params) {
        Log.i(DoodleActivity.TAG, "Inside the background");
        mActivity = params[0];
        DateIterator di = new DateIterator();

		Log.i(DoodleActivity.TAG, "Fetching doodles from the internet");
				
		
		JSONArray doodleItems = getDoodleJsonArray(di.getYear(), di.getMonth());

		
		createDoodleItems(doodleItems);

		return null;
	}


	
	private JSONArray getDoodleJsonArray(int year, int month){
		String url = "http://www.google.com/doodles/json/" + year + "/" + month;
		JSONArray doodleJsonArray = null;
		
		try {
		    URL gDoodle = new URL(url);
		    URLConnection doodleCon = gDoodle.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader(doodleCon.getInputStream()));
		    String line;
		    String strData = "";
		    while ((line = in.readLine()) != null) {   
		        strData += line;
		    }
		    doodleJsonArray = new JSONArray(strData);
		}catch(JSONException e){
			// TODO: Too lazy to do this
		}catch(IOException e){
            // TODO: Too lazy to do this
        }
		return doodleJsonArray;
	}
	
	private void createDoodleItems(JSONArray doodleItems){		
		for (int i = 0; i < doodleItems.length(); i++){
			DoodleData dd = null;
			try {
				JSONObject jo = doodleItems.getJSONObject(i);
				dd = getDoodleData(jo);
			} catch (JSONException e) {
				Log.e(DoodleActivity.TAG, "CreateDoodleItems Exception", e);
			}
			if(dd != null){
                AsyncTask<DoodleData, Void, Bitmap> imageTask = new LoadDoodleImageAsync().execute(dd);
			}else{
				Log.e(DoodleActivity.TAG, "CreateDoodleItems: Error getting Doodle data");
			}
		}
	}
	
	private DoodleData getDoodleData(JSONObject doodleObject) throws JSONException{
        return new Gson().fromJson(doodleObject.toString(), DoodleData.class);
	}
	

}
