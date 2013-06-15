package com.runnirr.doodleviewer.messages;

import com.runnirr.doodleviewer.fetcher.DoodleData;

/**
 * Created by Adam on 6/15/13.
 */
public interface DoodleEventTransmitter<T> {
    public boolean registerListener(DoodleEventListener<T> o);

    void notifyListeners(T o);

}
