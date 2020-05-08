//--------------------------------------------------
// Class EventSet
//--------------------------------------------------
// Written by Kenvix <i@com.kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.event

class EventList<E> constructor(initSize: Int = 10) : ArrayList<EventHandler<E>>(initSize), EventContainer<E> {
    override operator fun plusAssign(handler: EventHandler<E>) {
        add(handler)
    }

    override operator fun minusAssign(handler: EventHandler<E>) {
        remove(handler)
    }

    override operator fun invoke(data: E) {
        forEach { it(data) }
    }
}