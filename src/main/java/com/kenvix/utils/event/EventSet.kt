//--------------------------------------------------
// Class EventSet
//--------------------------------------------------
// Written by Kenvix <i@com.kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.event

open class EventSet<E> : HashSet<EventHandler<E>>(), EventContainer<E> {
    override operator fun plusAssign(handler: EventHandler<E>) {
        add(handler)
    }

    override operator fun minusAssign(handler: EventHandler<E>) {
        remove(handler)
    }
}