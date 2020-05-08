//--------------------------------------------------
// Class IEvent
//--------------------------------------------------
// Written by Kenvix <i@com.kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.event

interface EventContainer<E> : Collection<EventHandler<E>> {
    operator fun plusAssign(handler: EventHandler<E>)
    operator fun minusAssign(handler: EventHandler<E>)

    operator fun invoke(data: E)
}