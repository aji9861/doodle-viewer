package com.runnirr.doodleviewer.messages;

import java.util.LinkedList;

/**
 * Created by Adam on 6/15/13.
 */
public class SimpleDoodleEventTransmitter<T> implements DoodleEventTransmitter<T> {

    protected LinkedList<DoodleEventListener<T>> mListeners = new LinkedList<DoodleEventListener<T>>();

    @Override
    public boolean registerListener(DoodleEventListener<T> o) {
        return mListeners.add(o);
    }

    @Override
    public void notifyListeners(T o) {
        for(DoodleEventListener<T> e : mListeners){
            e.onNewInformation(o);
        }
    }
}
