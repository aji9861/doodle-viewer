package com.runnirr.doodleviewer.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.runnirr.doodleviewer.DoodleActivity;
import com.runnirr.doodleviewer.messages.DoodleUIUpdater;
import com.runnirr.doodleviewer.settings.SettingsManager;

public class LoadDoodlesAsync extends AsyncTask<Integer, Void, Void> {

    private static final String TAG = LoadDoodlesAsync.class.getSimpleName();

    private final DoodleUIUpdater mUIUpdater;

    public LoadDoodlesAsync(final DoodleUIUpdater ui){
        mUIUpdater = ui;
    }

	@Override
	protected Void doInBackground(Integer... params) {
		Log.i(DoodleActivity.TAG, "Fetching doodles from the internet");
        int year = params[0];
        int month = params[1];

        initializeUI();
        if(month == SettingsManager.Month.all.value){
            for (int i = Calendar.DECEMBER; i >= Calendar.JANUARY; i--){
                // TODO: This runs out of memory for large years
                int cMonth = i + 1; /* Make January 1 instead of 0 */
                JSONArray doodleItems = getDoodleJsonArray(year, cMonth);
                createDoodleItems(doodleItems);
            }
        }else{
            JSONArray doodleItems = getDoodleJsonArray(year, month);
            createDoodleItems(doodleItems);
        }

		return null;
	}

    private void initializeUI(){
        mUIUpdater.resetView();
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
            Log.e(TAG, e.getMessage(), e);
		}catch(IOException e){
            Log.e(TAG, e.getMessage(), e);
        }
		return doodleJsonArray;
	}
	
	private void createDoodleItems(JSONArray doodleItems){
        if(doodleItems.length() == 0){
            new LoadDoodleImageAsync().execute(new DoodleData());
            return;
        }
		for (int i = 0; i < doodleItems.length(); i++){
			DoodleData dd = null;
			try {
				JSONObject jo = doodleItems.getJSONObject(i);
				dd = getDoodleData(jo);
			} catch (JSONException e) {
				Log.e(DoodleActivity.TAG, "CreateDoodleItems Exception", e);
			}
			if(dd != null){
                new LoadDoodleImageAsync().execute(dd);
			}else{
				Log.e(DoodleActivity.TAG, "CreateDoodleItems: Error getting Doodle data");
			}
		}
	}
	
	private DoodleData getDoodleData(JSONObject doodleObject) throws JSONException{
        return new Gson().fromJson(doodleObject.toString(), DoodleData.class);
	}

}
