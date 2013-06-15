package com.runnirr.doodleviewer.fetcher;

import com.runnirr.doodleviewer.messages.DoodleEventListener;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

/**
 * Created by Adam on 6/14/13.
 */
public class DataCollector implements Queue<DoodleData> {

    private static DataCollector self = null;
    private LinkedList<DoodleData> mQueue = new LinkedList<DoodleData>();
    private LinkedList<DoodleEventListener> mListeners = new LinkedList<DoodleEventListener>();

    private DataCollector(){

    }

    public synchronized static DataCollector getInstance(){
        if (self == null){
            self = new DataCollector();
        }
        return self;
    }


    @Override
    public boolean add(DoodleData doodleData) {
        synchronized (mQueue){
            boolean result = mQueue.add(doodleData);
            notifyListeners(doodleData);
            return result;
        }
    }

    @Override
    public boolean addAll(Collection<? extends DoodleData> collection) {
        throw new UnsupportedOperationException("AddAll is not supported");
    }

    @Override
    public void clear() {
        synchronized (mQueue){
            mQueue.clear();
        }
    }

    @Override
    public boolean contains(Object object) {
        throw new UnsupportedOperationException("Contains is not supported");
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        throw new UnsupportedOperationException("ContainsAll is not supported");
    }

    @Override
    public boolean isEmpty() {
        return mQueue.isEmpty();
    }

    @Override
    public Iterator<DoodleData> iterator() {
        throw new UnsupportedOperationException("Iterator is not supported");
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException("Remove is not supported");
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException("RemoveAll is not supported");
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException("RetainAll is not supported");
    }

    @Override
    public int size() {
        return mQueue.size();
    }

    @Override
    public Object[] toArray() {
        return mQueue.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return mQueue.toArray(array);
    }

    @Override
    public boolean offer(DoodleData doodleData) {
        throw new UnsupportedOperationException("Offer is not supported");
    }

    @Override
    public DoodleData remove() {
        synchronized (mQueue){
            return mQueue.remove();
        }
    }

    @Override
    public DoodleData poll() {
        return mQueue.poll();
    }

    @Override
    public DoodleData element() {
        return mQueue.element();
    }

    @Override
    public DoodleData peek() {
        return mQueue.peek();
    }

    public boolean registerListener(DoodleEventListener o){
        return mListeners.add(o);
    }

    private void notifyListeners(DoodleData dd){
        for (DoodleEventListener dListener : mListeners){
            dListener.onNewDoodleLoaded(dd);
        }
    }
}
