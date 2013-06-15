package com.runnirr.doodleviewer.messages;

import com.runnirr.doodleviewer.fetcher.DoodleData;

/**
 * Created by Adam on 6/14/13.
 */
public interface DoodleEventListener {

    void onNewDoodleLoaded(DoodleData dd);
}
