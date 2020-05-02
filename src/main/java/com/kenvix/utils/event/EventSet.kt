//--------------------------------------------------
// Class EventSet
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.event

open class EventSet<E> : HashSet<EventHandler<E>>(), EventContainer<E> {
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