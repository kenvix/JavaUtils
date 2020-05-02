//--------------------------------------------------
// Class EventSet
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.event

class EventList<E>(initSize: Int = 10) : ArrayList<EventHandler<E>>(initSize), EventContainer<E> {
    override fun plusAssign(handler: EventHandler<E>) {
        add(handler)
    }

    override fun minusAssign(handler: EventHandler<E>) {
        remove(handler)
    }

    override fun invoke(data: E) {
        forEach { it(data) }
    }
}