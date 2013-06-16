package com.runnirr.doodleviewer.messages;

/**
 * Created by Adam on 6/15/13.
 *
 * Interface for transmitting doodle events
 */
public interface DoodleEventTransmitter<T> {
    public boolean registerListener(DoodleEventListener<T> o);

    void notifyListeners(T o);

}
