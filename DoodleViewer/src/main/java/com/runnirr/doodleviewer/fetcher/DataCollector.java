package com.runnirr.doodleviewer.fetcher;

import com.runnirr.doodleviewer.messages.DoodleEventListener;
import com.runnirr.doodleviewer.messages.DoodleEventTransmitter;
import com.runnirr.doodleviewer.messages.SimpleDoodleEventTransmitter;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

/**
 * Created by Adam on 6/14/13.
 */
public class DataCollector extends SimpleDoodleEventTransmitter<DoodleData> {

    private static DataCollector self = null;
    private LinkedList<DoodleData> mQueue = new LinkedList<DoodleData>();

    public synchronized static DataCollector getInstance(){
        if (self == null){
            self = new DataCollector();
        }
        return self;
    }

    public boolean add(DoodleData doodleData) {
        synchronized (mQueue){
            boolean result = mQueue.add(doodleData);
            notifyListeners(doodleData);
            return result;
        }
    }

    public void notifyListeners(DoodleData dd){
        for (DoodleEventListener dListener : mListeners){
            dListener.onNewInformation(dd);
        }
    }
}
