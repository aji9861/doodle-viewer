package com.runnirr.doodleviewer.messages;

/**
 * Created by Adam on 6/14/13.
 *
 * Interface for receiving doodle events
 */
public interface DoodleEventListener<T> {

    void onNewInformation(final T data);
}
