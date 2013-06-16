package com.runnirr.doodleviewer.fetcher;

import com.runnirr.doodleviewer.messages.DoodleEventListener;
import com.runnirr.doodleviewer.messages.SimpleDoodleEventTransmitter;

import java.util.LinkedList;

/**
 * Created by Adam on 6/14/13.
 *
 * When new Doodles are loaded, they get add to this. It then
 * notifies all listeners of the new DoodleData
 */
public class DataCollector extends SimpleDoodleEventTransmitter<DoodleData> {

    private static DataCollector self = null;
    private final LinkedList<DoodleData> mQueue = new LinkedList<DoodleData>();

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
        for (DoodleEventListener<DoodleData> dListener : mListeners){
            dListener.onNewInformation(dd);
        }
    }
}
