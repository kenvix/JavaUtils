//--------------------------------------------------
// Class Event
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.event;

import java.util.LinkedList;
import java.util.List;

public class Event<T extends EventHandler> {
    private List<T> listenerList = new LinkedList<>();

    public boolean addListener(T listener) {
        return listenerList.add(listener);
    }

    public boolean removeListener(T listener) {
        return listenerList.remove(listener);
    }

    public void emit(Object ... args) {
        for (T listener: listenerList) {
            listener.emit(args);
        }
    }
}
